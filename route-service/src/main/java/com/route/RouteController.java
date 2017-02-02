package com.route;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestOperations;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.jar.Attributes;
import java.util.jar.Manifest;

import javax.annotation.PostConstruct;
import javax.servlet.ServletContext;

/**
 * @author Davor Sauer
 */
@RestController
public class RouteController {

    @Value("${spring.application.name:${cloud.application.name:unknown}}")
    private String serviceName;

    @Autowired
    private ServletContext servletContext;

    private static Map<String, String> info = new LinkedHashMap<>();

    static final String FORWARDED_URL = "X-CF-Forwarded-Url";

    static final String PROXY_METADATA = "X-CF-Proxy-Metadata";

    static final String PROXY_SIGNATURE = "X-CF-Proxy-Signature";

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final RestOperations restOperations;

    @Autowired
    RouteController(RestOperations restOperations) {
        this.restOperations = restOperations;
    }

    @RequestMapping(headers = {FORWARDED_URL, PROXY_METADATA, PROXY_SIGNATURE})
    ResponseEntity<?> service(RequestEntity<byte[]> incoming) {
        this.logger.info("Incoming Request: {}", incoming);

        RequestEntity<?> outgoing = getOutgoingRequest(incoming);
        this.logger.info("Outgoing Request: {}", outgoing);

        return this.restOperations.exchange(outgoing, byte[].class);
    }

    @RequestMapping(value = "route_info")
    @ResponseBody
    public Map<String, String> route_info(RequestEntity<byte[]> incoming) {
        this.logger.info("Incoming Request: {}", incoming);

        return info;
    }

    private static RequestEntity<?> getOutgoingRequest(RequestEntity<?> incoming) {
        HttpHeaders headers = new HttpHeaders();
        headers.putAll(incoming.getHeaders());

        URI uri = headers.remove(FORWARDED_URL).stream()
                .findFirst()
                .map(URI::create)
                .orElseThrow(() -> new IllegalStateException(String.format("No %s header present", FORWARDED_URL)));

        return new RequestEntity<>(incoming.getBody(), headers, incoming.getMethod(), uri);
    }


    @PostConstruct
    public void postConstruct() {
        info.put("serviceName", serviceName);
        loadManifestData();
    }

    private void loadManifestData() {
        try {
            InputStream is = servletContext.getResourceAsStream("/META-INF/MANIFEST.MF");
            if (is == null) {
                is = Thread.currentThread().getContextClassLoader().getResourceAsStream("/META-INF/MANIFEST.MF");
            }
            if (is != null) {
                Manifest manifest = new Manifest(is);
                Attributes attributes = manifest.getMainAttributes();

                attributes.entrySet().forEach(entry -> info.put(entry.getKey().toString(), entry.getValue().toString()));
            }
        } catch (IOException e) {
            logger.warn("META-INF/MANIFEST.MF not available");
        }
    }

}

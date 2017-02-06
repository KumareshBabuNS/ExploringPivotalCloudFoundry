package com.gateway.route;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestOperations;

import java.net.URI;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

/**
 * @author Davor Sauer
 */
@RestController
public class RouteController {

    static final String FORWARDED_URL = "X-CF-Forwarded-Url";

    static final String PROXY_METADATA = "X-CF-Proxy-Metadata";

    static final String PROXY_SIGNATURE = "X-CF-Proxy-Signature";

    private static final Logger logger = LoggerFactory.getLogger(RouteController.class);

    private final RestOperations restOperations;

    private final Map<String, String> redirectMapping;

    private final List<String> services;

    @Autowired
    RouteController(RestOperations restOperations, RouteProperties routeProperties) {
        this.restOperations = restOperations;
        this.redirectMapping = routeProperties.getRedirect();
        this.services = routeProperties.getService();
    }

    @RequestMapping(headers = {FORWARDED_URL, PROXY_METADATA, PROXY_SIGNATURE})
    ResponseEntity<?> route(RequestEntity<byte[]> incoming) {
        this.logger.info("Incoming Request: {}", incoming);

        RequestEntity<?> outgoing = getOutgoingRequest(incoming);
        this.logger.info("Outgoing Request: {}", outgoing);

        return this.restOperations.exchange(outgoing, byte[].class);
    }

    private RequestEntity<?> getOutgoingRequest(RequestEntity<?> incoming) {
        HttpHeaders headers = new HttpHeaders();
        headers.putAll(incoming.getHeaders());

        URI uri = headers.remove(FORWARDED_URL).stream()
                .findFirst()
                .map(this::redirect)
                .orElseThrow(() -> new IllegalStateException(String.format("No %s header present", FORWARDED_URL)));

        return new RequestEntity<>(incoming.getBody(), headers, incoming.getMethod(), uri);
    }

    private URI redirect(String forwardUri) {
        String redirectUrl = getRedirectUrl(forwardUri).orElse(forwardUri);

        if (!Objects.equals(forwardUri, redirectUrl)) {
            logger.info("Redirect URI from: '{}' to '{}'", forwardUri, redirectUrl);
        } else {
            logger.debug("Forward URI: {}", forwardUri);
        }

        return URI.create(redirectUrl);
    }

    private Optional<String> getRedirectUrl(String forwardUri) {
        return Optional.ofNullable(redirectMapping.get(forwardUri));
    }

}

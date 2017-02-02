package com.servicea.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.jar.Attributes;
import java.util.jar.Manifest;

import javax.annotation.PostConstruct;
import javax.servlet.ServletContext;

/**
 * @author Davor Sauer
 */
@Controller
public class IndexController {

    private static final Logger logger = LoggerFactory.getLogger(IndexController.class);

    @Value("${spring.application.name:${cloud.application.name:unknown}}")
    private String serviceName;

    @Autowired
    private ServletContext servletContext;

    private static Map<String, String> info = new LinkedHashMap<>();

    @RequestMapping(value = "")
    @ResponseBody
    public Map<String, String> index() {
        logger.debug("get index");
        return info;
    }

    @RequestMapping(value = "time")
    @ResponseBody
    public String time() {
        logger.debug("get current time");
        return LocalDateTime.now().toString();
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

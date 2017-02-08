package com.servicea.controller;

import com.backbase.buildingblocks.registry.client.api.DiscoveryClient;
import com.backbase.buildingblocks.registry.client.api.ServiceInstance;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

/**
 * @author Davor Sauer
 */
@Controller
public class RemoteCallController {

    private static final Logger logger = LoggerFactory.getLogger(RemoteCallController.class);

    private static final String TEST_URL = "http://gturnquist-quoters.cfapps.io/api/random";

    private RestTemplate restTemplate = new RestTemplate();

    @Autowired
    private DiscoveryClient discoveryClient;

    @RequestMapping("remote")
    @ResponseBody
    public ResponseEntity<?> callRemoteService() {
        logger.info("Remote call: {}", TEST_URL);
        Object responseData = restTemplate.getForObject(TEST_URL, Object.class);
        logger.info("Remote call to: {} done => {}", TEST_URL, responseData);
        return new ResponseEntity(responseData, HttpStatus.OK);
    }

    @RequestMapping("services")
    @ResponseBody
    public ResponseEntity<?> registryServices() {
        Set<String> services = discoveryClient.getServices();
        logger.info("Available services: {}", services);

        Map<String, List<ServiceInstance>> registeredServices = new HashMap<>();

        services.forEach(serviceName -> {
            List<ServiceInstance> instances = discoveryClient.getInstances(serviceName);
            registeredServices.put(serviceName, instances);
        });


        return new ResponseEntity(registeredServices, HttpStatus.OK);
    }

    @RequestMapping("service/{serviceName}/{url}")
    @ResponseBody
    public ResponseEntity<?> registryServices(@PathVariable("serviceName") String serviceName, @PathVariable("url") String url) {
        logger.info("Lookup for service: {} with URL: {}", serviceName, url);

        List<ServiceInstance> instances = discoveryClient.getInstances(serviceName);
        Optional<ServiceInstance> service = instances.stream().findFirst();

        if (!service.isPresent()) {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }

        ServiceInstance serviceInstance = service.get();
        URI serviceURI = serviceInstance.getUri();
        logger.info("Service {} URI: {}", serviceName, serviceURI);

        String callURI = serviceURI.toASCIIString() + "/" + url;

        logger.info("Remote call: {}", callURI);
        Object responseData = restTemplate.getForObject(callURI, Object.class);
        logger.info("Remote call to: {} done => {}", TEST_URL, responseData);
        return new ResponseEntity(responseData, HttpStatus.OK);
    }
}
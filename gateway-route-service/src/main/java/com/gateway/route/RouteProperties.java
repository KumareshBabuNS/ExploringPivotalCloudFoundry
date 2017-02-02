package com.gateway.route;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Davor Sauer
 */
@Component
@ConfigurationProperties(prefix = "gateway.route")
public class RouteProperties {

    Map<String, String> redirect = new HashMap<>();

    List<String> service = new ArrayList<>();

    public Map<String, String> getRedirect() {
        return redirect;
    }

    public void setRedirect(Map<String, String> redirect) {
        this.redirect = redirect;
    }

    public List<String> getService() {
        return service;
    }

    public void setService(List<String> service) {
        this.service = service;
    }
}

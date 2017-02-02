package com.gateway.app.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Davor Sauer
 */
@RestController
public class RoutesController {


    @RequestMapping("routes")
    @ResponseBody
    public Map<String, String> routes() {
        return new HashMap<>();
    }

}

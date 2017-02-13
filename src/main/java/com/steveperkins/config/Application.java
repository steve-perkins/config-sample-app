package com.steveperkins.config;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@SpringBootApplication
public class Application {

    final static Map<String, String> properties = new ConcurrentHashMap<>();

    public static void main(String[] args) {
        properties.putAll(PropertiesClient.loadProperties());
        SpringApplication.run(Application.class, args);
    }

}

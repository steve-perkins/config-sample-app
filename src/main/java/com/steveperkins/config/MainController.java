package com.steveperkins.config;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/")
public class MainController {

    /**
     * Displays all of the key/value properties loaded from Consul and Vault (or
     * the "local.properties" file.
     *
     * @return
     */
    @GetMapping
    public ModelAndView view() {
        final Map<String, Object> model = new HashMap<>();
        model.put("allProperties", Application.properties);
        return new ModelAndView("index", model);
    }

    /**
     * Updates the config properties with the latest values.
     *
     * @return
     */
    @GetMapping("/refresh")
    public ModelAndView refresh() {
        final Map<String, String> properties = PropertiesClient.loadProperties();
        Application.properties.clear();
        Application.properties.putAll(properties);
        return new ModelAndView("redirect:/");
    }
}

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

    @GetMapping
    public ModelAndView view() {
        final Map<String, Object> model = new HashMap<>();
        model.put("allProperties", Application.properties);
        return new ModelAndView("index", model);
    }
}

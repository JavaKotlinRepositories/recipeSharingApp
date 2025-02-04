package com.vishal.recipeBackend.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/backend")
public class Home {

    @GetMapping("/home")
    public String home() {
        return "Hello World";
    }

}

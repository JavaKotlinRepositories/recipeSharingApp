package com.vishal.recipeBackend.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class KuberbetesHealthController {
    @GetMapping("/")
    public ResponseEntity index() {
        return ResponseEntity.ok("Hello World");
    }
    @GetMapping("/healthz")
    public ResponseEntity healthz() {
        return ResponseEntity.ok("Hello World");
    }
}

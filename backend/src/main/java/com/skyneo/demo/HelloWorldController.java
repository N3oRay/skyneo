package com.skyneo.demo;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloWorldController {
    @GetMapping("/api/hello")
    public String hello() {
        return "Dovahkiin says: Hello, World!";
    }
}

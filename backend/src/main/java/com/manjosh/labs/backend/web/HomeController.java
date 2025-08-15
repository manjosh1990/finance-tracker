package com.manjosh.labs.backend.web;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping({"/status", "/health"})
class HomeController {

    @GetMapping
    String healthCheck() {
        return "Application is running";
    }
}

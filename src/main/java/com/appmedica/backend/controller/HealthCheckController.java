package com.appmedica.backend.controller;

import jakarta.annotation.PostConstruct;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HealthCheckController {

    @GetMapping("/")
    public String home() {
        System.out.println(">>> HOME ENDPOINT LLAMADO <<<");
        return "Backend activo y funcionando.";
    }
    @PostConstruct
    public void init() {
        System.out.println(">>> HEALTHCHECK CONTROLLER INICIADO <<<");
    }

}

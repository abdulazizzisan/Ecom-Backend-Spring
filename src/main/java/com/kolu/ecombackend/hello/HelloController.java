package com.kolu.ecombackend.hello;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/hello")
@PreAuthorize("hasRole('USER')")
public class HelloController {
    @GetMapping
    public String hello() {
        return "Hello, World!";
    }
}

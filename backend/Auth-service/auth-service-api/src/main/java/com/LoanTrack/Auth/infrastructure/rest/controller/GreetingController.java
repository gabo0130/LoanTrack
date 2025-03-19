package com.LoanTrack.Backend.infrastructure.rest.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/greeting")
public class GreetingController {

    @GetMapping
    public ResponseEntity<String> getGreeting(){
        return ResponseEntity.ok("Basic Archetype saids hello");
    }

    @GetMapping("/test2/inca/parama")
    public ResponseEntity<String> getGreeting2(){
        return ResponseEntity.ok("Basic Archetype saids hello");
    }
}

package com.numbank.app.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;

@RestController
@RequestMapping("/ping")
@CrossOrigin(origins = "http://localhost:3000")
public class PingPongController {
    
    @GetMapping({"", "/"})
    public String getPingPong() {
        return "pong";
    }
    
}

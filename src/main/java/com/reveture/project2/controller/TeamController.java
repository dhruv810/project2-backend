package com.reveture.project2.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TeamController {

    @GetMapping("/team")
    public ResponseEntity<?> test() {
        return ResponseEntity.ok().body("Hello team");
    }
}

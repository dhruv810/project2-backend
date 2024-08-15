package com.reveture.project2.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SponsorController {

    @GetMapping("/sponsor")
    public ResponseEntity<?> test() {
        return ResponseEntity.ok().body("Hello sponsor");
    }
}

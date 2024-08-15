package com.reveture.project2.controller;

import com.reveture.project2.DTO.UserDTO;
import com.reveture.project2.entities.User;
import com.reveture.project2.repository.UserRepository;
import com.reveture.project2.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
@AllArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping("/user")
    public ResponseEntity<?> test() {
        return ResponseEntity.ok().body("Hello user");
    }

    @PostMapping("/create")
    public ResponseEntity<?> createUser(@RequestBody User u ){
        return userService.addNewUser(u);
    }


}

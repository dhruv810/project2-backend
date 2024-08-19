package com.reveture.project2.controller;

import com.reveture.project2.DTO.UserDTO;
import com.reveture.project2.entities.User;
import com.reveture.project2.exception.CustomException;
import com.reveture.project2.service.UserService;
import jakarta.servlet.http.HttpSession;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;
import org.springframework.web.client.HttpClientErrorException;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/user")
@AllArgsConstructor
public class UserController {
    private final UserService userService;

    // view all users on team
    @GetMapping("/user")
    public ResponseEntity<?> viewUsers(HttpSession session) {
        try {
            User user = (User) session.getAttribute("user");

            //user checks
            if (user == null) {
                return ResponseEntity.status(400).body("Login first");
            } else if (!user.getRole().equals("Manager")) {
                return ResponseEntity.status(400).body("You are not a manager. Only managers can view all team members");
            }

            List<User> users = this.userService.getAllUsers();
            List<UserDTO> res = new ArrayList<>();
            users.forEach(u -> {
                res.add(new UserDTO(u));
            });
            return ResponseEntity.ok().body(res);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PostMapping("/create")
    public ResponseEntity<?> createUser(@RequestBody User u ){
        try{
            User user = userService.addNewUser(u);
            return ResponseEntity.ok().body(new UserDTO(user));
        } catch (CustomException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception ex){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
        }

    }

    @PatchMapping("/role/{newRole}")
    public ResponseEntity<String> updateUserRole(@PathVariable String newRole, @RequestParam UUID player_id, HttpSession session){
        try {
            User user = (User) session.getAttribute("user");
            if (user == null) {
                return ResponseEntity.status(400).body("Login first");
            }
            else if (user.getRole().equals("Player")) {
                return ResponseEntity.status(400).body("You are not manager. Only manager can change role");
            }
            else if (user.getUserId().equals(player_id)) {
                return ResponseEntity.status(400).body("You cannot change your own role");
            }

            userService.updateRole(player_id, newRole, user.getUserId());
            String s = String.format("User with ID %s role has been updated to role %s", player_id, newRole);
            return ResponseEntity.ok(s);
        } catch(CustomException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception ex){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
        }
    }

    //remove user from team
    @PatchMapping("/users/{userId}")
    public ResponseEntity removeUser(@PathVariable UUID userId, HttpSession session) {
        try {
            User user = (User) session.getAttribute("user");

            //user checks
            if (user == null) {
                return ResponseEntity.status(400).body("Login first");
            } else if (!user.getRole().equals("Manager")) {
                return ResponseEntity.status(400).body("You are not a manager. Only managers can remove team members");
            }

            userService.removeUser(userId);
            String s = String.format("User with ID %s has been removed from your team", userId);
            return ResponseEntity.ok(s);

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }






}

package com.reveture.project2.controller;

import com.reveture.project2.DTO.UserDTO;
import com.reveture.project2.entities.User;
import com.reveture.project2.exception.CustomException;
import com.reveture.project2.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/user")
@AllArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping("/user")
    public ResponseEntity<?> test() {
        List<User> users = this.userService.getAllUsers();
        List<UserDTO> res = new ArrayList<>();
        users.forEach(u -> {
            res.add(new UserDTO(u));
        });
        return ResponseEntity.ok().body(res);
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
    public ResponseEntity<String> updateUserRole(@PathVariable String newRole){
        String StringUUID = "1ce2f045-8d7c-4341-bfe4-47cd393cbc8b";
        String non_existient_UUID = "b065e857-9770-4c9e-bbb9-90a4d3dbd047"; // for testing reasons, i made a non-existent user
        UUID dummmyID = UUID.fromString(StringUUID);
        try {
            User u = userService.getUserByUUID(dummmyID);

            userService.updateRole(u,newRole);
            String s = String.format("User with ID %s role has been updated to role %s",StringUUID,newRole);
            return ResponseEntity.ok(s);
        } catch(CustomException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());

        } catch (Exception ex){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
        }



    }


}

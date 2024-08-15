package com.reveture.project2.service;

import com.reveture.project2.DTO.UserDTO;
import com.reveture.project2.entities.User;
import com.reveture.project2.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import java.util.List;

/*
Create user
This will create new user. done

Request:
url = POST : "/create"

body = {
    username: String
    password: String
    name: String
    role: String
}
Response
body = {
    id: UUID
    username: String
    name: String
    role: String
    team: UUID (Team.id)
    amount: Double
}
 */

@Service
@AllArgsConstructor
public class UserService {

    private final UserRepository userRepository;



    //TODO: this works fine when a Team is not passed as param, perhaps we should check if
    // it still works when a team is passed as param in HTTP request.
    // it is a little tricky to consider passing param w Team object as Team object
    // requires info such as "balance", which is unrealistic for client to have
    // thus, we encounter more complications if a team isn' passed. Something to discuss
    // as a group
    public ResponseEntity<?> addNewUser(User u){
        System.out.println("we are inside addNewUser!");
        try {
            System.out.println("printing user: " + u);
            userRepository.saveAndFlush(u);
            return ResponseEntity.ok(u);
        }
        catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("User Could Not be Inserted");

        }

    }


}

package com.reveture.project2.controller;

import com.reveture.project2.DTO.UserDTO;
import com.reveture.project2.entities.Team;
import com.reveture.project2.entities.User;
import com.reveture.project2.exception.CustomException;
import com.reveture.project2.repository.UserRepository;
import com.reveture.project2.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.UUID;

/*

View sponsors
This will allow user to see all sponsors for their team. Only manager will be able to see amount of sponsorship.

Request:
url = GET : "/sponsors"

Response
For Player:

body = [{
    id: UUID
    username: String
    category: String
    name: String
}, ...]
For Manager:

body = [{
    id: UUID
    username: String
    category: String
    name: String
    amount: Double
}, ...]
 */

@RestController
@RequestMapping("/user")
@AllArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping("/sponsors")
    public ResponseEntity<?> seeSponsorships(){
        String StringUUID = "b065e857-9770-4c9e-bbb9-90a4d3dbd048";
        String non_existient_UUID = "b065e857-9770-4c9e-bbb9-90a4d3dbd047"; // for testing reasons, i made a non-existent user
        UUID dummmyID = UUID.fromString(StringUUID);
        try {
            User u = userService.getUserByUUID(dummmyID);
            Team userTeam = userService.getTeamFromUser(u);


        } catch (CustomException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());

        } catch (Exception ex){

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());

        }


        return ResponseEntity.ok("hi");
    }

    @GetMapping("/user")
    public ResponseEntity<?> test() {
        return ResponseEntity.ok().body("Hello user");
    }

    @PostMapping("/create")
    public ResponseEntity<?> createUser(@RequestBody User u ){
        try{
            return ResponseEntity.ok(userService.addNewUser(u));
        } catch (CustomException e){

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());

        }catch (Exception ex){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
        }

    }

    @PatchMapping("/role/{newRole}")
    public ResponseEntity<String> updateUserRole(@PathVariable String newRole){
        String StringUUID = "b065e857-9770-4c9e-bbb9-90a4d3dbd048";
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

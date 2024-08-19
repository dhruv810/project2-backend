package com.reveture.project2.DTO;
import com.reveture.project2.entities.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.*;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {

    private UUID userId;
    private String username;
    private String role;
    private String teamName;
    private Double salary;


    public UserDTO(User user) {
        this.userId = user.getUserId();
        this.username = user.getUsername();
        this.role = user.getRole();
        if (user.getTeam() != null)
            this.teamName = user.getTeam().getTeamName();
        else {
            this.teamName = "None";
        }
        this.salary = user.getSalary();
    }




}

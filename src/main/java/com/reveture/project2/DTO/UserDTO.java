package com.reveture.project2.DTO;
import java.util.*;

public class UserDTO {

    private UUID userId;
    private String username;
    private String role;
    private UUID team_Id;
    private Double salary;


    public UserDTO() {

    }

    public UserDTO(UUID userID, String username, String role, UUID team_Id, Double salary)
    {
        this.userId = userID;
        this.username = username;
        this.role = role;
        this.team_Id = team_Id;
        this.salary = salary;

    }

    public UUID getUserId() {
        return userId;
    }

    public void setUserId(UUID userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public UUID getTeam_Id() {
        return team_Id;
    }

    public void setTeam_Id(UUID team_Id) {
        this.team_Id = team_Id;
    }

    public double getSalary() {
        return salary;
    }

    public void setSalary(Double salary) {
        this.salary = salary;
    }
    @Override
    public String toString() {
        return "UserDTO{" +
                "userId=" + userId +
                ", username='" + username + '\'' +
                ", role='" + role + '\'' +
                ", teamId='" + team_Id + '\'' +
                ", salary='" + salary + '\'' +

                '}';
    }





}

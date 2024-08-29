package com.reveture.project2.controller;

import com.reveture.project2.DTO.TeamDTO;
import com.reveture.project2.entities.Team;
import com.reveture.project2.entities.User;
import com.reveture.project2.exception.CustomException;
import com.reveture.project2.service.TeamService;
import com.reveture.project2.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
public class TeamController {

    private static final Logger logger = LoggerFactory.getLogger(TeamService.class);
    final private TeamService teamService;
    final private UserService userService;

    @Autowired
    public TeamController(TeamService teamService, UserService userService) {
        this.teamService = teamService;
        this.userService = userService;
    }

    @GetMapping("/allteams")
    public ResponseEntity<?> getAllTeams() {
        logger.info("Received request to get all teams");
        List<Team> teamList = this.teamService.getAllTeams();
        List<TeamDTO> res = new ArrayList<>();
        teamList.forEach(team -> {
            res.add(new TeamDTO(team));
        });

        return ResponseEntity.ok().body(res);
    }

    @PostMapping("/team")
    public ResponseEntity<?> createTeam(@RequestBody Team team) {
        logger.info("Received request to create a new team with name: {}", team.getTeamName());
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            User user = (User) authentication.getPrincipal();
            user = this.userService.getUserByUUID(user.getUserId());
            if (user == null) {
                return ResponseEntity.status(400).body("Login first");
            }
            if (! user.getRole().equals("Manager")) {
                return ResponseEntity.status(400).body("You are not manager");
            }
            Team newTeam = this.teamService.createTeam(team);
            TeamDTO t = new TeamDTO(newTeam);
            this.userService.updateTeam(user, newTeam);
            logger.info("Team created successfully with name: {}", team.getTeamName());
            return ResponseEntity.ok().body(t);
        }
        catch (CustomException e) {
            logger.error("Failed to create team: {}", e.getMessage());
            return ResponseEntity.status(400).body(e.getMessage());
        }
    }

    @PatchMapping("/team/name/{newTeamName}")
    public ResponseEntity<?> updateTeamName(@PathVariable String newTeamName) {

        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            User user = (User) authentication.getPrincipal();
            user = this.userService.getUserByUUID(user.getUserId());
            if (user == null) {
                return ResponseEntity.status(400).body("Login first");
            }
            if (! user.getRole().equals("Manager")) {
                return ResponseEntity.status(400).body("You are not manager");
            }
            if (user.getTeam() == null) {
                return ResponseEntity.status(400).body("You are not part of any team");
            }

            Team team = this.teamService.updateTeamName(user.getTeam().getTeamId(), newTeamName);
            logger.info("Team name changed successfully from {} to {}", user.getTeam().getTeamId(), team.getTeamName());
            return ResponseEntity.ok().body("Team name changed successfully from " + user.getTeam().getTeamId() + " to " + team.getTeamName());
        }
        catch (CustomException e) {
            logger.error("Failed to change team name because of: {}", e.getMessage());
            return ResponseEntity.status(400).body(e.getMessage());
        }
    }

}

package com.reveture.project2.controller;

import com.reveture.project2.DTO.SponsorDTO;
import com.reveture.project2.DTO.TeamDTO;
import com.reveture.project2.entities.Sponsor;
import com.reveture.project2.entities.Team;
import com.reveture.project2.exception.CustomException;
import com.reveture.project2.service.SponsorService;
import com.reveture.project2.service.TeamService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RestController
public class TeamController {
    private static final Logger logger = LoggerFactory.getLogger(TeamService.class);
    @Autowired
    final private TeamService teamService;

    public TeamController(TeamService teamService) {
        this.teamService = teamService;
    }

    @GetMapping("/team")
    public ResponseEntity<?> getAllTeams() {
        logger.info("Received request to get all teams");
        List<Team> teamList = this.teamService.getAllTeams();
        List<TeamDTO> res = new ArrayList<>();
        teamList.forEach(team -> {
            res.add(new TeamDTO(team));
        });

        return ResponseEntity.ok().body(teamList);
    }

    @PostMapping("/team")
    public ResponseEntity<?> createTeam(@RequestBody Team team) {
        logger.info("Received request to create a new team with name: {}", team.getTeamName());
        try {
            Team newTeam = this.teamService.createTeam(team);
            TeamDTO t = new TeamDTO(newTeam);
            logger.info("Team created successfully with name: {}", team.getTeamName());
            return ResponseEntity.ok().body(t);
        }
        catch (CustomException e) {
            logger.error("Failed to create team: {}", e.getMessage());
            return ResponseEntity.status(400).body(e.getMessage());
        }
    }

    @PatchMapping("/team/name/{newTeamName}")
    public ResponseEntity<?> updateTeamName(String Id, @PathVariable String newTeamName) {
        //TODO:
        // change id to be able to get the actual team id from logged in user
        // change every instance of teamId to the actual id upon change

        UUID teamId = UUID.fromString("6d410d08-08e8-4b0d-928b-27be7d8ba572");

        logger.info("Received request to change team name from {} to {}", teamId, newTeamName);
        try {
            Team team = this.teamService.updateTeamName(teamId, newTeamName);
            logger.info("Team name changed successfully from {} to {}", teamId, team.getTeamName());
            return ResponseEntity.ok().body("Team name changed successfully from " + teamId + " to " + team.getTeamName());
        }
        catch (CustomException e) {
            logger.error("Failed to change team name from {} to {}: {}", teamId, newTeamName, e.getMessage());
            return ResponseEntity.status(400).body(e.getMessage());
        }
    }

}

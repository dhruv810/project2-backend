package com.reveture.project2.controller;

import com.reveture.project2.DTO.SponsorDTO;
import com.reveture.project2.entities.Sponsor;
import com.reveture.project2.entities.Team;
import com.reveture.project2.exception.CustomException;
import com.reveture.project2.service.SponsorService;
import com.reveture.project2.service.TeamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TeamController {

    @Autowired
    final private TeamService teamService;

    public TeamController(TeamService teamService) {
        this.teamService = teamService;
    }
    @GetMapping("/team")
    public ResponseEntity<?> test() {
        return ResponseEntity.ok().body("Hello team");
    }

    @PostMapping("/team")
    public ResponseEntity<?> createTeam(@RequestBody String teamName) {
        try {
            Team team = this.teamService.createTeam(teamName);
            SponsorDTO s = new SponsorDTO(newSponsor);
            return ResponseEntity.ok().body(s);
        }
        catch (CustomException e) {
            return ResponseEntity.status(400).body(e.getMessage());
        }
    }

}

package com.reveture.project2.controller;

import com.reveture.project2.DTO.TeamInviteDTO;
import org.apache.coyote.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import com.reveture.project2.service.*;

@RestController
public class TeamInviteController {

    @GetMapping("/team-invite")
    public ResponseEntity<?> test() {
        return ResponseEntity.ok().body("Hello team");
    }


    private final TeamInviteService teamInviteService;

    public TeamInviteController(TeamInviteService teamInviteService) {
        this.teamInviteService = teamInviteService;
    }

    @PostMapping("/send")
    public ResponseEntity<String> sendTeamInvite(@RequestBody TeamInviteDTO teamInviteDTO) {
        try {
            teamInviteService.sendTeamInvite(teamInviteDTO);
            return ResponseEntity.ok("Team invite sent successfully!");
        } catch (Exception e) {

            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An error occurred while sending the team invite.");
        }
    }





}

package com.reveture.project2.controller;

import com.reveture.project2.DTO.LoginDTO;
import com.reveture.project2.DTO.SponsorDTO;
import com.reveture.project2.DTO.UserDTO;
import com.reveture.project2.entities.Sponsor;
import com.reveture.project2.entities.User;
import com.reveture.project2.service.AuthService;
import com.reveture.project2.service.TeamService;
import com.reveture.project2.utils.JwtTokenUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    final private AuthService authService;
    private static final Logger logger = LoggerFactory.getLogger(TeamService.class);
    private JwtTokenUtil jwtTokenUtil;
    private AuthenticationManager authenticationManager;

    @Autowired
    public AuthController(AuthService authService, JwtTokenUtil jwtTokenUtil, AuthenticationManager authenticationManager) {
        this.authService = authService;
        this.jwtTokenUtil = jwtTokenUtil;
        this.authenticationManager = authenticationManager;
    }

    @PostMapping("/user/login")
    public ResponseEntity<?> login(@RequestBody LoginDTO lDTO) {
        try {
            String usernameAndRole = lDTO.getUsername() + " USER";
            Authentication auth = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(usernameAndRole, lDTO.getPassword())
            );

            //build up the user based on the validation above
            User user = (User) auth.getPrincipal();

            //finally, generate a JWT!
            String accessToken = jwtTokenUtil.generateAccessToken(user);

            //create the OutgoingUserDTO with JWT, and send it back to the front end
            UserDTO outUser = new UserDTO(user, accessToken);
            logger.info("User: {} just logged in", user.getUsername());
            return ResponseEntity.ok(outUser);

        } catch (Exception e) {
            return ResponseEntity.status(400).body(e.getMessage());
        }

    }

    @PostMapping("/sponsor/login")
    public ResponseEntity<?> loginSponsor(@RequestBody LoginDTO lDTO) {
        try {
            String usernameAndRole = lDTO.getUsername() + " SPONSOR";
            Authentication auth = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(usernameAndRole, lDTO.getPassword())
            );

            //build up the user based on the validation above
            Sponsor sponsor = (Sponsor) auth.getPrincipal();

            //finally, generate a JWT!
            String accessToken = jwtTokenUtil.generateAccessToken(sponsor);

            //create the OutgoingUserDTO with JWT, and send it back to the front end
            SponsorDTO outUser = new SponsorDTO(sponsor, accessToken);
            logger.info("Sponsor: {} just logged in", sponsor.getName());
            return ResponseEntity.ok(outUser);

        } catch (Exception e) {
            return ResponseEntity.status(400).body(e.getMessage());
        }

    }
}

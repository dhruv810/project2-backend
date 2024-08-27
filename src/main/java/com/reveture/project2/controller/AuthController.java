package com.reveture.project2.controller;

import ch.qos.logback.core.encoder.EchoEncoder;
import com.reveture.project2.DTO.LoginDTO;
import com.reveture.project2.DTO.SponsorDTO;
import com.reveture.project2.DTO.UserDTO;
import com.reveture.project2.entities.Sponsor;
import com.reveture.project2.entities.User;
import com.reveture.project2.exception.CustomException;
import com.reveture.project2.service.AuthService;
import com.reveture.project2.service.TeamService;
import com.reveture.project2.utils.JwtTokenUtil;
import jakarta.servlet.http.HttpSession;
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
    public static HttpSession ses;
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
    public ResponseEntity<?> login(@RequestBody LoginDTO lDTO, HttpSession httpSession) {
        try {
            Authentication auth = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(lDTO.getUsername(), lDTO.getPassword())
            );

            //build up the user based on the validation above
            User user = (User) auth.getPrincipal();

            //finally, generate a JWT!
            String accessToken = jwtTokenUtil.generateAccessToken(user);

            //create the OutgoingUserDTO with JWT, and send it back to the front end
            UserDTO outUser = new UserDTO(user, accessToken);

            return ResponseEntity.ok(outUser);

        } catch (Exception e) {
            return ResponseEntity.status(400).body(e.getMessage());
        }

    }

    @PostMapping("/sponsor/login")
    public ResponseEntity<?> loginSponsor(@RequestBody LoginDTO loginDTO, HttpSession httpSession) {
        try {
            Sponsor loggedInSponsor = authService.loginSponsor(loginDTO);

            if (loggedInSponsor != null) {
                httpSession.setAttribute("sponsor", loggedInSponsor);
                ses = httpSession;
                logger.info("Sponsor: {} just logged in,", loggedInSponsor.getUsername());
                return ResponseEntity.ok().body(new SponsorDTO(loggedInSponsor));
            }
            else {
                return ResponseEntity.status(401).body("Invalid credentials");
            }

        } catch (CustomException e) {
            return ResponseEntity.status(400).body(e.getMessage());
        }

    }

    @GetMapping("/user/logout")
    public ResponseEntity<?> logout(HttpSession httpSession) {
        httpSession.invalidate();
        logger.info("Logged out everyone.");
        return ResponseEntity.ok().body("Logged out");
    }

    @GetMapping("/user")
    public ResponseEntity<?> getUser(HttpSession httpSession) {
        User currentUser = (User) httpSession.getAttribute("user");
        if (currentUser == null) {
            return ResponseEntity.status(400).body("Login first");
        }
        return ResponseEntity.ok().body(new UserDTO(currentUser));
    }

    @GetMapping("/sponsor")
    public ResponseEntity<?> getSponsor(HttpSession httpSession) {
        Sponsor currentSponsor = (Sponsor) httpSession.getAttribute("sponsor");
        if (currentSponsor == null) {
            return ResponseEntity.status(400).body("Login first");
        }
        return ResponseEntity.ok().body(new SponsorDTO(currentSponsor));
    }

}

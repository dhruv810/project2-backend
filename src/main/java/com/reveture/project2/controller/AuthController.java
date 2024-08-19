package com.reveture.project2.controller;

import com.reveture.project2.DTO.LoginDTO;
import com.reveture.project2.DTO.SponsorDTO;
import com.reveture.project2.DTO.UserDTO;
import com.reveture.project2.entities.Sponsor;
import com.reveture.project2.entities.User;
import com.reveture.project2.exception.CustomException;
import com.reveture.project2.repository.SponsorRepository;
import com.reveture.project2.service.AuthService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    final private AuthService authService;
    public static HttpSession ses;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/user/login")
    public ResponseEntity<?> login(@RequestBody LoginDTO loginDTO, HttpSession httpSession) {
        try {
            User loggedInUser = authService.login(loginDTO);

            if (loggedInUser != null) {
                httpSession.setAttribute("user", loggedInUser);
                ses = httpSession;
                return ResponseEntity.ok().body(new UserDTO(loggedInUser));
            }
            else {
                return ResponseEntity.status(401).body("Invalid credentials");
            }

        } catch (CustomException e) {
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

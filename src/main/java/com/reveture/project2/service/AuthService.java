package com.reveture.project2.service;

import com.reveture.project2.DTO.LoginDTO;
import com.reveture.project2.entities.Sponsor;
import com.reveture.project2.entities.User;
import com.reveture.project2.exception.CustomException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    final private UserService userService;
    final private SponsorService sponsorService;
    final private PasswordEncoder passwordEncoder;

    @Autowired
    public AuthService(UserService userService, SponsorService sponsorService, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.sponsorService = sponsorService;
        this.passwordEncoder = passwordEncoder;
    }

    private static final Logger logger = LoggerFactory.getLogger(AuthService.class);

    public User login(LoginDTO lDTO) throws CustomException {
        if (lDTO.getUsername().isEmpty() || lDTO.getPassword().isEmpty()) {
            throw new CustomException("username and Password cannot be empty");
        }
        logger.info("User: {} just logged in.", lDTO.getUsername());

        return this.userService.getUserByUsernameAndPassword(lDTO.getUsername(), lDTO.getPassword());
    }

    public Sponsor loginSponsor(LoginDTO lDTO) throws CustomException {
        if (lDTO.getUsername().isEmpty() || lDTO.getPassword().isEmpty()) {
            throw new CustomException("username and Password cannot be empty");
        }
        logger.info("Sponsor: {} just logged in.", lDTO.getUsername());

        return this.sponsorService.getSponsorByUsernameAndPassword(lDTO.getUsername(), passwordEncoder.encode(lDTO.getPassword()));
    }
}

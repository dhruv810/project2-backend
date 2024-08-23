package com.reveture.project2.service;

import com.reveture.project2.entities.Sponsor;
import com.reveture.project2.exception.CustomException;
import com.reveture.project2.repository.SponsorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class SponsorService {

    final private SponsorRepository sponsorRepository;

    @Autowired
    public SponsorService(SponsorRepository sponsorRepository) {
        this.sponsorRepository = sponsorRepository;
    }

    public Sponsor createSponsor(Sponsor sponsor) throws CustomException {
        if (sponsor.getUsername().length() < 5 || sponsor.getPassword().length() < 5 ) {
            throw new CustomException("Username and password must be at least 5 digit long");
        } else if (sponsor.getName().isEmpty()) {
            throw new CustomException("Name cannot be empty");
        } else if (sponsor.getBudget() < 0) {
            throw new CustomException("Budget cannot be less than 0");
        }

        this.doesUserNameExists(sponsor.getUsername());

        return this.sponsorRepository.save(sponsor);
    }

    public void doesUserNameExists(String username) throws CustomException {
        Sponsor s = this.sponsorRepository.findByUsername(username);
        if (s != null) {
            throw new CustomException("Username already exists");
        }
    }

    public Sponsor findSponsorIdIfExists(UUID sponsorId) throws CustomException {
        Optional<Sponsor> s = this.sponsorRepository.findById(sponsorId);
        if (s.isEmpty()) {
            throw new CustomException("Sponsor does not exist");
        }
        return s.get();
    }

    public List<Sponsor> getAllSponsors() {
        return this.sponsorRepository.findAll();
    }

    public Sponsor updateBudget(UUID sponsorid, Double newBudget) throws CustomException {
        if (newBudget < 0) {
            throw new CustomException("Budget cannot be < 0");
        }
        Sponsor s = this.findSponsorIdIfExists(sponsorid);
        s.setBudget(newBudget);
        return this.sponsorRepository.save(s);
    }

    public Sponsor getSponsorByUsernameAndPassword(String username, String password) throws CustomException {
        Optional<Sponsor> sponsor = this.sponsorRepository.findByUsernameAndPassword(username, password);
        if (sponsor.isEmpty()) {
            throw new CustomException("Invalid username and password");
        }
        return sponsor.get();
    }
}

package com.reveture.project2.controller;

import com.reveture.project2.DTO.SponsorDTO;
import com.reveture.project2.entities.Sponsor;
import com.reveture.project2.entities.TeamProposal;
import com.reveture.project2.exception.CustomException;
import com.reveture.project2.service.SponsorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/sponsor")
public class SponsorController {

    @Autowired
    final private SponsorService sponsorService;

    public SponsorController(SponsorService sponsorService) {
        this.sponsorService = sponsorService;
    }

    // TODO: delete this in deployment
    @GetMapping("/all")
    public ResponseEntity<?> test() {
        List<Sponsor> sponsors = this.sponsorService.getAllSponsors();
        return ResponseEntity.ok().body(sponsors);
    }

    @PostMapping("/create")
    public ResponseEntity<?> createSponsor(@RequestBody Sponsor sponsor) {

        try {
            Sponsor newSponsor = this.sponsorService.createSponsor(sponsor);
            SponsorDTO s = new SponsorDTO(newSponsor);
            return ResponseEntity.ok().body(s);
        }
        catch (CustomException e) {
            return ResponseEntity.status(400).body(e.getMessage());
        }

    }

    @PatchMapping("/budget/{newBudget}")
    public ResponseEntity<?> updateBudget(@PathVariable Double newBudget) {
        // TODO: get sponsor id from logged in sponsor
        UUID sponsorid = UUID.fromString("ac418df2-f95c-4452-a8b3-1aca202bb294");
        try {
            Sponsor newSponsor = this.sponsorService.updateBudget(sponsorid, newBudget);
//            SponsorDTO s = new SponsorDTO(newSponsor);
            return ResponseEntity.ok().body("Successfully updated budget to " + newSponsor.getBudget());
        } catch (CustomException e) {
            return ResponseEntity.status(400).body(e.getMessage());
        }
    }

}

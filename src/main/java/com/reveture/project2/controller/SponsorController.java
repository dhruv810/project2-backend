package com.reveture.project2.controller;

import com.reveture.project2.DTO.SponsorDTO;
import com.reveture.project2.DTO.TeamProposalDTO;
import com.reveture.project2.entities.Sponsor;
import com.reveture.project2.entities.TeamProposal;
import com.reveture.project2.exception.CustomException;
import com.reveture.project2.service.SponsorService;
import com.reveture.project2.service.TeamProposalService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/sponsor")
public class SponsorController {

    @Autowired
    final private SponsorService sponsorService;

    @Autowired
    final private TeamProposalService teamProposalService;

    public SponsorController(SponsorService sponsorService, TeamProposalService teamProposalService) {
        this.sponsorService = sponsorService;
        this.teamProposalService = teamProposalService;
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
        } catch (CustomException e) {
            return ResponseEntity.status(400).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(500).body(e.getMessage());
        }

    }

    @PatchMapping("/budget/{newBudget}")
    public ResponseEntity<?> updateBudget(@PathVariable Double newBudget, HttpSession session) {
        try {
            Sponsor sponsor = (Sponsor) session.getAttribute("sponsor");
            if (sponsor == null) {
                return ResponseEntity.status(400).body("Login first");
            }
            Sponsor newSponsor = this.sponsorService.updateBudget(sponsor.getSponsorId(), newBudget);
//            SponsorDTO s = new SponsorDTO(newSponsor);
            return ResponseEntity.ok().body("Successfully updated budget to " + newSponsor.getBudget());
        } catch (CustomException e) {
            return ResponseEntity.status(400).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(500).body(e.getMessage());
        }
    }


    /* TODO:: Test this functionality once Create Team functionality is done.
    This gives error that cant add because team do not exist.
    */
    @PostMapping("/proposal")
    public ResponseEntity<?> sendProposal(@RequestBody TeamProposal teamProposal, HttpSession session) {
        try {
            Sponsor sponsor = (Sponsor) session.getAttribute("sponsor");
            if (sponsor == null) {
                return ResponseEntity.status(400).body("Login first");
            }
            teamProposal.setSenderSponsor(sponsor);
            teamProposal.setStatus("Pending");
            TeamProposal tp = this.teamProposalService.createProposal(teamProposal);
            return ResponseEntity.ok().body(new TeamProposalDTO(tp));
        } catch (CustomException e) {
            return ResponseEntity.status(400).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(500).body(e.getMessage());
        }
    }

    @GetMapping("/teams")
    public ResponseEntity<?> getAllTeamsSponsorInvestedIn(HttpSession session) {
        try {
            Sponsor sponsor = (Sponsor) session.getAttribute("sponsor");
            if (sponsor == null) {
                return ResponseEntity.status(400).body("Login first");
            }
            List<TeamProposal> sponsoredTeams = this.teamProposalService.getAllAcceptedProposalsBySponsor(sponsor.getSponsorId());
            List<TeamProposalDTO> res = new ArrayList<>();
            sponsoredTeams.forEach(teamProposal -> {
                res.add(new TeamProposalDTO(teamProposal));
            });
            return ResponseEntity.ok().body(res);
        } catch (CustomException e) {
            return ResponseEntity.status(400).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(500).body(e.getMessage());
        }
    }

    @GetMapping("/proposals/{status}")
    public ResponseEntity<?> getAllProposalsBySponsorByStatus(@PathVariable String status, HttpSession session) {
        try {
            Sponsor sponsor = (Sponsor) session.getAttribute("sponsor");
            if (sponsor == null) {
                return ResponseEntity.status(400).body("Login first");
            }
            List<TeamProposal> proposals = this.teamProposalService.getAllProposalsBySponsor(sponsor.getSponsorId(), status);
            List<TeamProposalDTO> res = new ArrayList<>();
            proposals.forEach(teamProposal -> {
                res.add(new TeamProposalDTO(teamProposal));
            });
            return ResponseEntity.ok().body(res);
        } catch (CustomException e) {
            return ResponseEntity.status(400).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(500).body(e.getMessage());
        }
    }


}

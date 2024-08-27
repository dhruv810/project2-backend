package com.reveture.project2.controller;

import com.reveture.project2.DTO.SponsorDTO;
import com.reveture.project2.DTO.TeamProposalDTO;
import com.reveture.project2.entities.Sponsor;
import com.reveture.project2.entities.TeamProposal;
import com.reveture.project2.exception.CustomException;
import com.reveture.project2.service.SponsorService;
import com.reveture.project2.service.TeamProposalService;
import com.reveture.project2.service.TeamService;
import jakarta.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/sponsor")
public class SponsorController {

    final private SponsorService sponsorService;
    final private TeamProposalService teamProposalService;
    private PasswordEncoder passwordEncoder;

    private static final Logger logger = LoggerFactory.getLogger(TeamService.class);

    @Autowired
    public SponsorController(PasswordEncoder passwordEncoder, SponsorService sponsorService, TeamProposalService teamProposalService) {
        this.sponsorService = sponsorService;
        this.teamProposalService = teamProposalService;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping("/all")
    public ResponseEntity<?> test() {
        List<Sponsor> sponsors = this.sponsorService.getAllSponsors();
        logger.info("someone just accessed all sponsor info.");
        return ResponseEntity.ok().body(sponsors);
    }

    @PostMapping("/create")
    public ResponseEntity<?> createSponsor(@RequestBody Sponsor sponsor) {

        try {
            sponsor.setPassword(passwordEncoder.encode(sponsor.getPassword()));
            Sponsor newSponsor = this.sponsorService.createSponsor(sponsor);
            SponsorDTO s = new SponsorDTO(newSponsor);
            logger.info("New sponsor created with name: {}", newSponsor.getName());
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
            logger.info("Sponsor: {} just changed budget to {}", sponsor.getName(), newSponsor.getBudget());
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
            logger.info("{} send proposal to {} team", teamProposal.getSenderSponsor().getName(), teamProposal.getReceiverTeam().getTeamName());
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
            logger.info("Accessing all sponsored team for {}", sponsor.getName());
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
            List<TeamProposal> proposals = new ArrayList<>();
            if (status.equalsIgnoreCase("ALL")) {
                proposals = this.teamProposalService.getAllProposalsBySponsor(sponsor.getSponsorId());
            }
            else {
                proposals = this.teamProposalService.getAllProposalsBySponsorByStatus(sponsor.getSponsorId(), status);
            }

            List<TeamProposalDTO> res = new ArrayList<>();
            proposals.forEach(teamProposal -> {
                res.add(new TeamProposalDTO(teamProposal));
            });
            logger.info("Accessing all {} proposal for {}", status, sponsor.getName());
            return ResponseEntity.ok().body(res);
        } catch (CustomException e) {
            return ResponseEntity.status(400).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(500).body(e.getMessage());
        }
    }

    @GetMapping("/proposals")
    public ResponseEntity<?> getAllProposals( HttpSession session) {
        try {
            List<TeamProposal> proposals = this.teamProposalService.getAllProposals();
            List<TeamProposalDTO> res = new ArrayList<>();
            proposals.forEach(teamProposal -> {
                res.add(new TeamProposalDTO(teamProposal));
            });
            logger.info("Accessing all proposals");
            return ResponseEntity.ok().body(res);
        } catch (Exception e) {
            return ResponseEntity.status(500).body(e.getMessage());
        }
    }

}

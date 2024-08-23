package com.reveture.project2.service;

import com.reveture.project2.entities.Sponsor;
import com.reveture.project2.entities.TeamProposal;
import com.reveture.project2.exception.CustomException;
import com.reveture.project2.repository.TeamProposalRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service

public class TeamProposalService {


    final private TeamProposalRepository teamProposalRepository;


    final private SponsorService sponsorService;

    // constructor injection is better
    @Autowired
    public TeamProposalService(TeamProposalRepository repository, SponsorService sponsorService) {
        this.teamProposalRepository = repository;
        this.sponsorService = sponsorService;
    }

    public TeamProposal createProposal(TeamProposal teamProposal) throws CustomException {
        Sponsor s = this.sponsorService.findSponsorIdIfExists(teamProposal.getSenderSponsor().getSponsorId());
        // TODO:: Check that team exists, if not throw error
        if (teamProposal.getAmount() < 0) {
            throw new CustomException("Amount cannot be < 0");
        } else if (teamProposal.getAmount() > s.getBudget()) {
            throw new CustomException("You cannot send proposal for " + teamProposal.getAmount() + "  while you only have " + s.getBudget());
        }
        this.sponsorService.updateBudget(s.getSponsorId(), s.getBudget() - teamProposal.getAmount());
        return this.teamProposalRepository.save(teamProposal);
    }

    public List<TeamProposal> getAllAcceptedProposalsBySponsor(UUID sponsorid) throws CustomException {
        Sponsor s = this.sponsorService.findSponsorIdIfExists(sponsorid);

        return this.teamProposalRepository.findAllBySenderSponsorAndStatus(s, "Accepted");

    }

    public List<TeamProposal> getAllProposalsBySponsor(UUID sponsorid, String status) throws CustomException {
        Sponsor s = this.sponsorService.findSponsorIdIfExists(sponsorid);
        return this.teamProposalRepository.findAllBySenderSponsorAndStatus(s, status);

    }
    public TeamProposal getProposalByID(UUID teamProposalID) throws CustomException{
        Optional<TeamProposal> t_P = this.teamProposalRepository.findById(teamProposalID);
        if (t_P.isEmpty()){
            String s = String.format("Proposal ID with UUID %s does not exist",teamProposalID.toString());
            throw  new CustomException(s);
        }
        return t_P.get();
    }
    public void changeProposalStatus(TeamProposal prop, String status) throws CustomException {
        if (status.equals("accepted")) {
            this.teamProposalRepository.save(prop);
        }
        this.sponsorService.updateBudget(prop.getSenderSponsor().getSponsorId(), prop.getSenderSponsor().getBudget() + prop.getAmount());
        this.teamProposalRepository.deleteById(prop.getProposalId());
    }

}

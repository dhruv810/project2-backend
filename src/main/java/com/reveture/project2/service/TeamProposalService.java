package com.reveture.project2.service;

import com.reveture.project2.entities.Sponsor;
import com.reveture.project2.entities.Team;
import com.reveture.project2.entities.TeamProposal;
import com.reveture.project2.exception.CustomException;
import com.reveture.project2.repository.TeamProposalRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service

public class TeamProposalService {

    final private TeamProposalRepository teamProposalRepository;
    final private SponsorService sponsorService;
    final private TeamService teamService;

    // constructor injection is better
    @Autowired
    public TeamProposalService(TeamProposalRepository repository, SponsorService sponsorService, @Lazy TeamService teamService) {
        this.teamProposalRepository = repository;
        this.sponsorService = sponsorService;
        this.teamService = teamService;
    }

    public TeamProposal createProposal(TeamProposal teamProposal) throws CustomException {
        Sponsor s = this.sponsorService.findSponsorIdIfExists(teamProposal.getSenderSponsor().getSponsorId());
        if (teamProposal.getAmount() < 0) {
            throw new CustomException("Amount cannot be < 0");
        } else if (teamProposal.getAmount() > s.getBudget()) {
            throw new CustomException("You cannot send proposal for " + teamProposal.getAmount() + "  while you only have " + s.getBudget());
        } else if (teamProposal.getReceiverTeam() == null) {
            throw new CustomException("You did not mentioned what team you want to sponsor");
        }
        teamProposal.setReceiverTeam(this.teamService.findTeamByIdIfExists(teamProposal.getReceiverTeam().getTeamId()));
        this.sponsorService.updateBudget(s.getSponsorId(), s.getBudget() - teamProposal.getAmount());
        return this.teamProposalRepository.save(teamProposal);
    }

    public List<TeamProposal> getAllAcceptedProposalsBySponsor(UUID sponsorid) throws CustomException {
        Sponsor s = this.sponsorService.findSponsorIdIfExists(sponsorid);

        return this.teamProposalRepository.findAllBySenderSponsorAndStatus(s, "Accepted");

    }

    public List<TeamProposal> getAllProposalsBySponsorByStatus(UUID sponsorid, String status) throws CustomException {
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
        if (status.equalsIgnoreCase("accepted")) {
            prop.setStatus("Accepted");
        } else {
            this.sponsorService.updateBudget(prop.getSenderSponsor().getSponsorId(), prop.getSenderSponsor().getBudget() + prop.getAmount());
            prop.setStatus("Rejected");
        }
        this.teamProposalRepository.save(prop);
    }

    public List<TeamProposal> getProposalByStatusByTeam(Team t, String status) {
        return this.teamProposalRepository.findAllByReceiverTeamAndStatus(t, status);
    }

    public List<TeamProposal> getAllProposals() {
        return this.teamProposalRepository.findAll();
    }

    public List<TeamProposal> getAllProposalsBySponsor(UUID sponsorId) throws CustomException {
        Sponsor s = this.sponsorService.findSponsorIdIfExists(sponsorId);
        return this.teamProposalRepository.findAllBySenderSponsor(s);
    }
}

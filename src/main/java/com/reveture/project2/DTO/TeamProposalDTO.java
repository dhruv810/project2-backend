package com.reveture.project2.DTO;

import com.reveture.project2.entities.TeamProposal;
import lombok.Data;

import java.util.UUID;

@Data
public class TeamProposalDTO {

    private UUID proposalId;
    private Double amount;
    private String status;
    private String sponsor_name;
    private String team_name;

    public TeamProposalDTO(TeamProposal teamProposal) {
        this.proposalId = teamProposal.getProposalId();
        this.amount = teamProposal.getAmount();
        this.team_name = teamProposal.getReceiverTeam().getTeamName();
        this.status = teamProposal.getStatus();
        this.sponsor_name = teamProposal.getSenderSponsor().getName();
    }


}

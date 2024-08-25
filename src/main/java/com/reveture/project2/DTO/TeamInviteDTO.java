package com.reveture.project2.DTO;

import com.reveture.project2.entities.TeamInvite;
import com.reveture.project2.entities.User;
import lombok.Data;

import java.util.UUID;

@Data
public class TeamInviteDTO {

    private UUID proposalId;
    private String status;
    private String teamName;
    private String receiverUsername;
    private String senderUsername;
    private Double amount;

    public TeamInviteDTO(TeamInvite teamInvite) {
        this.proposalId = teamInvite.getProposalId();
        this.status = teamInvite.getStatus();
        this.teamName = teamInvite.getSenderManager().getTeam().getTeamName();
        this.receiverUsername = teamInvite.getReceiverPlayer().getUsername();
        this.senderUsername = teamInvite.getSenderManager().getUsername();
        this.amount = teamInvite.getAmount();
    }
}

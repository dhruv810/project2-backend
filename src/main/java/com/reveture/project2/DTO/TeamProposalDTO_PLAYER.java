package com.reveture.project2.DTO;


/*
The object of this class is so that we are able to send Team proposals to the front end when PLAYERS request to see team proposals.

The reason for this is because PLAYERS are not allowed to see the AMOUNT of each team proposal. That privilege is reserved for MANAGERS.

As such, we need a different DTO class, depending on whether we are sending Team Proposals to PLAYERS or to MANAGERS.

To be clear, this class is made such that we can send PLAYERS team proposal information without revealing the AMOUNT attribute of each proposal....
 */

import com.reveture.project2.entities.TeamProposal;
import lombok.Data;

@Data
public class TeamProposalDTO_PLAYER {

    private String status;
    private String sponsor_name;
    private String team_name;

    public TeamProposalDTO_PLAYER(TeamProposal t){
        this.status = t.getStatus();
        this.sponsor_name = t.getSenderSponsor().getName();
        this.team_name = t.getReceiverTeam().getTeamName();
    }
}

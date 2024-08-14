package com.reveture.project2.entities;

import jakarta.persistence.Entity;

import java.util.List;
import java.util.UUID;

/*
Team stores list of player and team sponsors.

Only Manger can create team. and perform tasks behalf of team.
Manager can view, and Accept/Reject Sponsorship proposal.
Manager can Send and team invite to player by using username.
Balance represents sum of all amount provided to team by sponsors.
Team invite with amount greater than balance cannot be accepted.

teamMembers and playerSponsors are One-To-Many relationship.

One team can only have up to 3 managers.

Player who receives team invite can view other team members and List of sponsors,
but not the amount that sponsors give to the team.

 */

@Entity
public class Team {

    private UUID teamId;
    private String teamName;
    private List<Sponsorship> playerSponsors;
    private List<User> teamMembers;
    private Double balance;
}

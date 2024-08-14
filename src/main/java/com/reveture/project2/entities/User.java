package com.reveture.project2.entities;

import jakarta.persistence.Entity;

import java.util.List;
import java.util.UUID;

/*
This class represents table User.
it has Many-to-Many relation with sponsor.
it has Many-to-One relation with team.
Role can be either "Player" or "Manager". it is case-sensitive.
playerSponsors represents accepted sponsorships by player.

Salary is how much team is paying the Player. It is offered in Proposal.

When player accepts new proposal, change amount and Team or playerSponsors depending on type of Proposal.
When player or sponsor wants to
 */

// Added image to profile

@Entity
public class User {

    private UUID userId;
    private String username;
    private String password;
    private String role;
    private List<Sponsorship> playerSponsors;
    private Team team;
    private Double salary;

}


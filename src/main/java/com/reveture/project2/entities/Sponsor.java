package com.reveture.project2.entities;

import jakarta.persistence.Entity;

import java.util.List;
import java.util.UUID;

/*
This class represents Sponsor in our application.
Sponsor has its own table, and authorization service.
Sponsor has name such as Nike, Adidas which cannot be edited once created.
Sponsor category is also immutable once created.
Sponsor budget is mutable. It cannot be negative.
When team/user accepts sponsorship, the amount in proposal must be greater than Budget, and
amount will be deducted from budget.

Proposals represents all the proposal made by Sponsor.
proposals have One-To-Many relationship.
When requested all sponsored teams/player, return accepted proposals.
 */

@Entity
public class Sponsor {

    private UUID SponsorId;
    private String username;
    private String password;
    private String category;
    private String name;
    private Double budget;
    private List<Proposal> proposals;

}

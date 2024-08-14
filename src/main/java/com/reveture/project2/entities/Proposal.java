package com.reveture.project2.entities;

import jakarta.persistence.Entity;

import java.util.UUID;

/*
Proposal can be of two types. "TeamProposal" or "PlayerProposal". it is case-sensitive.
When proposal is accepted, it will be deleted and new Sponsorship will be created.
if Proposal is rejected, only delete proposal.

Sender can be either team Manager or Sponsor depending on proposal type.
receiver can be either team of user.

We are using sender and receiver id and not object because object type can be different. but their ID's have same datatype.

 */

@Entity
public class Proposal {

    private UUID proposalId;
    private String type;
    private String status;
    private UUID sender;
    private UUID receiver;
    private Double amount;

}
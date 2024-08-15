package com.reveture.project2.entities;

import jakarta.persistence.Entity;
import lombok.*;
import org.springframework.stereotype.Component;
import jakarta.persistence.*;



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
@Table(name = "Proposals")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Proposal {

    @Id
    @Column(name="proposal_id", nullable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID proposalId;

    @Column(name="type", nullable = false)
    private String type;

    @Column(name="status", nullable = false)
    private String status;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "sponsor", nullable = true)
    private Sponsor senderSponsor;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="team", nullable = true)
    private Team receiverTeam;

    @Column(name="amount", nullable = false)
    private Double amount;

}
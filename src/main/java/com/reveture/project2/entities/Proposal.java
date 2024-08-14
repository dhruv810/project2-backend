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
@Component
public class Proposal {

    @Id
    @Column(name="proposal_id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private UUID proposalId;

    @Column(name="type", nullable = false)
    private String type;

    @Column(name="status", nullable = false)
    private String status;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "sponsor_id", nullable = true, insertable=false, updatable=false)
    private Sponsor sender_sponsor;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id", nullable = true, insertable=false, updatable=false)
    private User sender_manager;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="team_id", nullable = true, insertable=false, updatable=false)
    private Team receiver_team;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id", nullable = true, insertable=false, updatable=false)
    private User receiver_player;

    @Column(name="amount", nullable = false)
    private Double amount;

}
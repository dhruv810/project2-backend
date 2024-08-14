package com.reveture.project2.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

/*
This class represents accepted proposals.
Based on actions such as accepting and rejecting proposal will add or remove Sponsorships from table
Proposal will be deleted once taken any action on it.

Sponsorships can be of 2 types.
Player sponsorship or Team sponsorship.

Sender can be either Sponsor or Manger, depending on type.
Receiver can be either User or Team, depending on type.

Amount is agreed number that sender decided to give to receiver.
 */

@Entity
@Table(name = "sponsorships")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Sponsorship {

    @Id
    @Column(name = "sponsorship_id", nullable = false)
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID sponsorshipId;

    @Column(name = "type", nullable = false)
    private String type;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "sponsor_id", nullable = false)
    private Sponsor sender_sponsor;

    @ManyToOne(fetch = FetchType.EAGER)
    @Column(insertable=false, updatable=false)
    @JoinColumn(name = "user_id", nullable = false)
    private User sender_manager;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "team_id", nullable = false)
    private Team receiver_team;

    @ManyToOne(fetch = FetchType.EAGER)
//    @Column(insertable=false, updatable=false)
    @JoinColumn(name = "user_id", nullable = false)
    private User receiver_player;

    @Column(name = "amount", nullable = false)
    private Double amount;

}

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
    @Column(name = "sponsorship_id", nullable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID sponsorshipId;

    @Column(name = "type", nullable = false)
    private String type;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "sponsor", nullable = false)
    private Sponsor sender;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "team", nullable = false)
    private Team receiver;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "player", nullable = false)
    private User player;

    @Column(name = "amount", nullable = false)
    private Double amount;

}

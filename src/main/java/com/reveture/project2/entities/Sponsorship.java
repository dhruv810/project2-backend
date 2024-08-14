package com.reveture.project2.entities;

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
public class Sponsorship {

    private UUID sponsorshipId;
    private String type;
    private Sponsor sender;
    private UUID receiver;
    private Double amount;

}

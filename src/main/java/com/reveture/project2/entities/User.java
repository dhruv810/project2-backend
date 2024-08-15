package com.reveture.project2.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

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
@Component
@Table(name = "users")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class User {

    @Id
    @Column(name = "user_id", updatable = false, nullable = false)
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID userId;

    @Column(name = "username", nullable = false, unique = true)
    private String username;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "role", nullable = false)
    private String role;

    @ManyToMany(mappedBy = "player")
    private List<Sponsorship> playerSponsors;

    @ManyToOne
    @JoinColumn(name = "team")
    private Team team;

    @Column(name = "salary", nullable = false)
    private Double salary;

    @JsonIgnore
    @OneToMany(mappedBy = "receiverPlayer", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<TeamInvite> team_invites;


}


package com.reveture.project2.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
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


    /*
    I changed the generateValue annotation to GenerationType.AUTO from GenerationType.UUID,
    as the latter was causing errors when i sent the requests ...
     */
    @Id
    @Column(name = "user_id", updatable = false, nullable = false)
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID userId;

    @Column(name = "username", nullable = false, unique = true)
    private String username;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Column(name = "last_name", nullable = false)
    private String lastName;

    @Column(name = "role", nullable = false)
    private String role;
//
//    @ManyToMany(mappedBy = "receiverPlayer")
//    private List<Proposal> playerSponsors;

    @ManyToOne
    @JoinColumn(name = "team")
    private Team team;

    @Column(name = "salary", nullable = false)
    private Double salary;

    @JsonIgnore
    @OneToMany(mappedBy = "receiverPlayer", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<TeamInvite> team_invites;


}


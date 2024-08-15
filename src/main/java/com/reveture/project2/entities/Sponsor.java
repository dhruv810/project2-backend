package com.reveture.project2.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;
import com.fasterxml.jackson.annotation.JsonIgnore;


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
@Table(name = "Sponsors")
@Component
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Sponsor {
    @Id
    @Column(name="sponsor_id", nullable = false)
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID SponsorId;

    @Column(name="username", nullable = false)
    private String username;

    @Column(name="password", nullable = false)
    private String password;

    @Column(name="category", nullable = false)
    private String category;

    @Column(name="name", nullable = false)
    private String name;

    @Column(name="budget", nullable = false)
    private Double budget;

    @Column(name = "proposals", nullable = false)
    @JsonIgnore
    @OneToMany(mappedBy = "senderSponsor", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<TeamProposal> proposals;

}

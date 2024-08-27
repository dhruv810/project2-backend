package com.reveture.project2.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import com.fasterxml.jackson.annotation.JsonIgnore;


import java.util.Collection;
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
@Table(name = "sponsors")
@Component
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Sponsor implements UserDetails {
    @Id
    @Column(name="sponsor_id", nullable = false)
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID sponsorId;

    @Column(name="username", nullable = false)
    private String username;

    @Column(name="password", nullable = false)
    private String password;

    @Column(name="category", nullable = false)
    private String category;

    @Column(name="name", unique = true, nullable = false)
    private String name;

    @Column(name="budget", nullable = false)
    private Double budget;

    @Column(name = "proposals", nullable = false)
    @JsonIgnore
    @OneToMany(mappedBy = "senderSponsor", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<TeamProposal> proposals;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of();
    }

    @Override
    public boolean isAccountNonExpired() {
        return UserDetails.super.isAccountNonExpired();
    }

    @Override
    public boolean isAccountNonLocked() {
        return UserDetails.super.isAccountNonLocked();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return UserDetails.super.isCredentialsNonExpired();
    }

    @Override
    public boolean isEnabled() {
        return UserDetails.super.isEnabled();
    }
}

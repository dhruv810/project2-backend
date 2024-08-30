package com.reveture.project2.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

/*
Team stores list of player and team sponsors.

Only Manger can create team. and perform tasks behalf of team.
Manager can view, and Accept/Reject Sponsorship proposal.
Manager can Send and team invite to player by using username.
Balance represents sum of all amount provided to team by sponsors.
Team invite with amount greater than balance cannot be accepted.

teamMembers and playerSponsors are One-To-Many relationship.

One team can only have up to 3 managers.

Player who receives team invite can view other team members and List of sponsors,
but not the amount that sponsors give to the team.

 */

@Entity
@Component
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "team")
@Setter
@Getter
public class Team {

    @Id
    @Column(name = "team_id", nullable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID teamId;

    @Column(name = "team_name", nullable = false, unique = true)
    private String teamName;

    @JsonIgnore
    @ManyToMany(mappedBy = "receiverTeam")
    private List<TeamProposal> TeamSponsors;

    @JsonIgnore
    @OneToMany(mappedBy = "team", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<User> teamMembers;

//    @JsonIgnore
    @Column(name = "balance", nullable = false)
    private Double balance;

    public void addTeamSponsor(TeamProposal t){
        this.TeamSponsors.add(t);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Team team = (Team) o;
        return Objects.equals(teamName, team.teamName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(teamName);
    }
}

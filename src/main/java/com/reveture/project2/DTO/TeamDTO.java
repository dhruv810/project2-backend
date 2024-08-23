package com.reveture.project2.DTO;

import com.reveture.project2.entities.Team;
import lombok.Data;
import java.util.UUID;

@Data
public class TeamDTO {

    private UUID teamId;
    private String teamName;
    private Double balance;

    public TeamDTO(Team team) {
        this.teamId = team.getTeamId();
        this.teamName = team.getTeamName();
        this.balance = team.getBalance();
    }


}

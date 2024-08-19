package com.reveture.project2.DTO;

import com.reveture.project2.entities.Team;
import com.reveture.project2.entities.TeamProposal;
import com.reveture.project2.entities.User;
import lombok.Data;
import org.apache.tomcat.util.codec.binary.StringUtils;

import java.util.List;
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

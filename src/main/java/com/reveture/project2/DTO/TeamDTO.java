package com.reveture.project2.DTO;

import com.reveture.project2.entities.TeamProposal;
import com.reveture.project2.entities.User;
import org.apache.tomcat.util.codec.binary.StringUtils;

import java.util.List;
import java.util.UUID;
public class TeamDTO {

    private UUID teamId;
    private String teamName;
    private List<User> teamMembers;
    private List<User> managers;

    public TeamDTO() {
    }

    public TeamDTO(String teamName) {
        this.teamName = teamName;
    }

    public TeamDTO(UUID teamId, String teamName, List<User> teamMembers, List<User> managers) {
        this.teamId = teamId;
        this.teamName = teamName;
        this.teamMembers = teamMembers;
        this.managers = managers;
    }

    public UUID getTeamId() {
        return teamId;
    }

    public void setTeamId(UUID teamId) {
        this.teamId = teamId;
    }

    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    public List<User> getTeamMembers() {
        return teamMembers;
    }

    public void setTeamMembers(List<User> teamMembers) {
        this.teamMembers = teamMembers;
    }

    public List<User> getManagers() {
        return managers;
    }

    public void setManagers(List<User> managers) {
        this.managers = managers;
    }

    @Override
    public String toString() {
        return "TeamDTO{" +
                "teamId=" + teamId +
                ", teamName='" + teamName + '\'' +
                ", teamMembers=" + teamMembers +
                ", managers=" + managers +
                '}';
    }
}

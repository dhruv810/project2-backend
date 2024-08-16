package com.reveture.project2.service;

import com.reveture.project2.entities.Team;
import com.reveture.project2.exception.CustomException;
import com.reveture.project2.repository.TeamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TeamService {

    @Autowired
    final private TeamRepository teamRepository;

    public TeamService(TeamRepository teamRepository) {
        this.teamRepository = teamRepository;
    }

    // team creation only done by manager's who are not part of a team
    // returns list of player and list of managers
    public Team createTeam(String teamName) throws CustomException {
        // checks for name
        if (teamName.length() < 5) {
            throw new CustomException("Team name must be at least 5 digit long");
        } else if (teamName.isEmpty()) {
            throw new CustomException("Name cannot be empty");
        }

        Team team = new Team();
        team.setTeamName(teamName);

        //TODO: add check for duplicate team name?

        return this.teamRepository.save(team);
    }






}

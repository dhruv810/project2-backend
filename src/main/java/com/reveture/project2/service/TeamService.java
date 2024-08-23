package com.reveture.project2.service;

import com.reveture.project2.entities.Sponsor;
import com.reveture.project2.entities.Team;
import com.reveture.project2.entities.User;
import com.reveture.project2.exception.CustomException;
import com.reveture.project2.repository.TeamRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class TeamService {

    final private TeamRepository teamRepository;
    final private UserService userService;

    @Autowired
    public TeamService(TeamRepository teamRepository, UserService userService) {
        this.teamRepository = teamRepository;
        this.userService = userService;
    }

    // team creation only done by manager's who are not part of a team
    // returns list of uuid, teamname, list of players, and list of managers
    public Team createTeam(Team team) throws CustomException {
        // name checks

        if (team.getTeamName().isEmpty()) {
            throw new CustomException("Team name must be at least 5 digit long");
        } else if (team.getTeamName().length() < 5) {
            throw new CustomException("Name cannot be empty");
        }
        doesTeamNameExist(team.getTeamName());

        team.setBalance(0.0);

        return this.teamRepository.save(team);
    }


    // team editing only done by managers who are part of the team
    // returns list of uuid, teamname, list of players, and list of managers
    public Team updateTeamName(UUID teamId, String newTeamName) throws CustomException {
        // name checks
        if (newTeamName.length() < 5) {
            throw new CustomException("Team name must be at least 5 digit long");
        } else if (newTeamName.isEmpty()) {
            throw new CustomException("Name cannot be empty");
        }
        doesTeamNameExist(newTeamName);

        // save
        Team team = this.findTeamByIdIfExists(teamId);
        team.setTeamName(newTeamName);
        return this.teamRepository.save(team);
    }

    public Team findTeamByIdIfExists(UUID teamId) throws CustomException {
        Optional<Team> t = this.teamRepository.findById(teamId);
        if (t.isEmpty()) {
            throw new CustomException("Team does not exist");
        }
        return t.get();
    }

    // team name check
    private void doesTeamNameExist(String teamName) throws CustomException {
        Team t = this.teamRepository.findByTeamName(teamName);
        if (t != null) {
            throw new CustomException("Team name already exists");
        }
    }


    // get all teams
    public List<Team> getAllTeams()  {
        List<Team> teamList = this.teamRepository.findAll();
        return teamList;
    }


    public void deleteTeam(Team team) {
        List<User> teamPlayers = this.userService.getAllUsersByTeam(team);
        teamPlayers.forEach(player -> {
            player.setTeam(null);
            player.setSalary(0.0);
            this.userService.updatePlayerTeamAndSalary(player);
        });
        this.teamRepository.deleteById(team.getTeamId());
    }
}

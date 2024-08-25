package com.reveture.project2.service;

import com.reveture.project2.DTO.TeamInviteDTO;
import com.reveture.project2.entities.Team;
import com.reveture.project2.entities.TeamInvite;
import com.reveture.project2.entities.User;
import com.reveture.project2.exception.CustomException;
import com.reveture.project2.repository.TeamInviteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class TeamInviteService {

    final private TeamInviteRepository teamInviteRepository;
    final private UserService userService;
    final private TeamService teamService;

    @Autowired
    public TeamInviteService(TeamInviteRepository teamInviteRepository, UserService userService, TeamService teamService) {
        this.teamInviteRepository = teamInviteRepository;
        this.userService = userService;
        this.teamService = teamService;
    }

    public TeamInvite createTeamInvite(TeamInvite teamInvite, User user) throws CustomException {
        teamInvite.setStatus("Pending");
        teamInvite.setSenderManager(this.userService.getUserByUUID(user.getUserId()));
        if (teamInvite.getReceiverPlayer() == null) {
            throw new CustomException("Receiver player cannot be null");
        }
        teamInvite.setReceiverPlayer(this.userService.getUserByUUID(teamInvite.getReceiverPlayer().getUserId()));
        Team t = teamInvite.getSenderManager().getTeam();
        if (t.getBalance() < teamInvite.getAmount()) {
            throw new CustomException("You don't have enough balance to send this proposal");
        }
        this.teamService.changeTeamBalance(t.getTeamId(), t.getBalance() - teamInvite.getAmount());
        return this.teamInviteRepository.save(teamInvite);
    }

    public List<TeamInvite> getAllTeamInvites(User user) {
        return this.teamInviteRepository.findByReceiverPlayer(user);
    }

    public List<TeamInvite> getAllSentTeamInvites(User user) {
        return this.teamInviteRepository.findBySenderManager(user);
    }

    public void updateTeamInviteStatus(UUID teamInviteId, String newStatus, User user) throws CustomException {
        Optional<TeamInvite> t = this.teamInviteRepository.findById(teamInviteId);
        if (t.isEmpty()) {
            throw new CustomException("TeamInvite Id doesn't exists");
        }
        TeamInvite teamInvite = t.get();

        if (! teamInvite.getStatus().equals("Pending")) {
            throw new CustomException("You cannot edit already accepted/rejected team invites");
        }
        if (! teamInvite.getReceiverPlayer().getUserId().equals(user.getUserId())) {
            throw new CustomException("This team invite is not for you, you cannot Accept/Reject it");
        }
        if (newStatus.equalsIgnoreCase("accepted")) {
            newStatus = "Accepted";
            this.userService.updatePlayerTeamAndSalary(user, teamInvite.getSenderManager().getTeam(), teamInvite.getAmount());
        }
        else {
            newStatus = "Rejected";
            Team tt = teamInvite.getSenderManager().getTeam();
            this.teamService.updateBalance(tt, tt.getBalance() + teamInvite.getAmount());
        }

        teamInvite.setStatus(newStatus);
        this.teamInviteRepository.save(teamInvite);

    }
}

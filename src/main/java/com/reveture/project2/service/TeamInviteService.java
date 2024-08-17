package com.reveture.project2.service;

import com.reveture.project2.DTO.TeamInviteDTO;
import com.reveture.project2.entities.Team;
import com.reveture.project2.entities.TeamInvite;
import com.reveture.project2.entities.User;
import com.reveture.project2.repository.TeamInviteRepository;
import com.reveture.project2.repository.TeamRepository;
import com.reveture.project2.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class TeamInviteService {


    @Autowired
    final private TeamInviteRepository teamInviteRepository;
    final private TeamRepository teamRepository;
    final private UserRepository userRepository;

    public TeamInviteService(TeamInviteRepository teamInviteRepository, TeamRepository teamRepository, UserRepository userRepository) {
        this.teamInviteRepository = teamInviteRepository;
        this.teamRepository = teamRepository;
        this.userRepository = userRepository;
    }

    public void sendTeamInvite(TeamInviteDTO teamInviteDTO) {
       

        try {
         
            User senderManager = userRepository.findById(teamInviteDTO.getSenderManager().getUserId())
                    .orElseThrow(() -> new RuntimeException("Sender not found"));
            User receiverPlayer = userRepository.findById(teamInviteDTO.getReceiverPlayer().getUserId())
                    .orElseThrow(() -> new RuntimeException("Receiver not found"));

         
            TeamInvite teamInvite = new TeamInvite();
            teamInvite.setInviteId(teamInviteDTO.getInviteId());
            teamInvite.setAmount(teamInviteDTO.getAmount());
            teamInvite.setStatus(teamInviteDTO.getStatus());
            teamInvite.setSenderManager(senderManager);
            teamInvite.setReceiverPlayer(receiverPlayer);

            teamInviteRepository.save(teamInvite);

        } catch (Exception e) {
     
            e.printStackTrace();
            throw e;  
        }
    }






}

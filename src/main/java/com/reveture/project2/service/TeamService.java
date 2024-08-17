package com.reveture.project2.service;

import com.reveture.project2.repository.TeamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.reveture.project2.entities.*;

import java.util.UUID;

@Service
public class TeamService {


    @Autowired
    final private TeamRepository teamRepository;

    public TeamService(TeamRepository teamRepository) {
        this.teamRepository = teamRepository;
    }





}

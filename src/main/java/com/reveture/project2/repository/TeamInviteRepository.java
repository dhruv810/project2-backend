package com.reveture.project2.repository;

import com.reveture.project2.entities.Team;
import com.reveture.project2.entities.Team;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface TeamRepository extends JpaRepository<Team, UUID> {




}

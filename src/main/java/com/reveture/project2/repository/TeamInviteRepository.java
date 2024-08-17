package com.reveture.project2.repository;

import com.reveture.project2.entities.TeamInvite;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface TeamInviteRepository extends JpaRepository<TeamInvite, UUID> {

    Optional<TeamInvite> findBySenderManager_Team_TeamId(UUID teamId);


}

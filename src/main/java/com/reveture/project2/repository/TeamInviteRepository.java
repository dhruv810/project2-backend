package com.reveture.project2.repository;

import com.reveture.project2.entities.TeamInvite;
import com.reveture.project2.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface TeamInviteRepository extends JpaRepository<TeamInvite, UUID> {

    List<TeamInvite> findByReceiverPlayer(User user);

    List<TeamInvite> findBySenderManager(User user);
}

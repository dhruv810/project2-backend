package com.reveture.project2.repository;

import com.reveture.project2.entities.Team;
import com.reveture.project2.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {
    Optional<User> findByUsernameAndPassword(String username, String password);

    List<User> findByTeam(Team team);

    User findByUsername(String username);

}

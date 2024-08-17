package com.reveture.project2.repository;

import com.reveture.project2.entities.Sponsor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface SponsorRepository extends JpaRepository<Sponsor, UUID> {
    Sponsor findByUsername(String username);

    Optional<Sponsor> findByUsernameAndPassword(String username, String password);
}

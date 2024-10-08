package com.reveture.project2.repository;

import com.reveture.project2.entities.Sponsor;
import com.reveture.project2.entities.Team;
import com.reveture.project2.entities.TeamProposal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface TeamProposalRepository extends JpaRepository<TeamProposal, UUID> {

    List<TeamProposal> findAllBySenderSponsorAndStatus(Sponsor s, String accepted);

    List<TeamProposal> findAllByReceiverTeamAndStatus(Team t, String status);

    List<TeamProposal> findAllBySenderSponsor(Sponsor s);
}

package com.reveture.project2.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Entity
@Table(name = "team_invites")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class TeamInvite {

    @Id
    @Column(name="invite_id", nullable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID inviteId;

    @Column(name="status", nullable = false)
    private String status;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "sender_manager_id", nullable = false)
    private User senderManager;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "receiver_player_id", nullable = false)
    private User receiverPlayer;

    @Column(name="amount", nullable = false)
    private Double amount;
}

package com.reveture.project2.DTO;

import com.reveture.project2.entities.*;

import java.util.UUID;

public class TeamInviteDTO {

    private UUID inviteId;
    private Double amount;
    private String status;
    private User senderManager;
    private User receiverPlayer;

    public TeamInviteDTO(UUID inviteId, Double amount, String status, User senderManager, User receiverPlayer) {
        this.inviteId = inviteId;
        this.amount = amount;
        this.status = status;
        this.senderManager = senderManager;
        this.receiverPlayer = receiverPlayer;
    }


    public TeamInviteDTO(TeamInvite ti) {
        this.inviteId = ti.getInviteId();
        this.amount = ti.getAmount();
        this.status = ti.getStatus();
        this.senderManager = ti.getSenderManager();
        this.receiverPlayer = ti.getReceiverPlayer();
    }

    public UUID getInviteId() {
        return inviteId;
    }

    public void setInviteId(UUID inviteId) {
        this.inviteId = inviteId;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public User getSenderManager() {
        return senderManager;
    }

    public void setSenderManager(User senderManager) {
        this.senderManager = senderManager;
    }

    public User getReceiverPlayer() {
        return receiverPlayer;
    }

    public void setReceiverPlayer(User receiverPlayer) {
        this.receiverPlayer = receiverPlayer;
    }

    @Override
    public String toString() {
        return "TeamInviteDTO{" +
                "inviteId=" + inviteId +
                ", amount=" + amount +
                ", status='" + status + '\'' +
                ", senderManager=" + senderManager +
                ", receiverPlayer=" + receiverPlayer +
                '}';
    }
}

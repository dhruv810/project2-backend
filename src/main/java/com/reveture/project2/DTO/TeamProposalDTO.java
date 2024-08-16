package com.reveture.project2.DTO;

import com.reveture.project2.entities.Sponsor;
import com.reveture.project2.entities.Team;
import com.reveture.project2.entities.TeamProposal;

public class TeamProposalDTO {

    private Double amount;
    private String status;
    private Sponsor senderSponsor;
    private Team receiverTeam;


    public TeamProposalDTO(Double amount, String status, Sponsor senderSponsor, Team receiverTeam) {
        this.amount = amount;
        this.status = status;
        this.senderSponsor = senderSponsor;
        this.receiverTeam = receiverTeam;
    }

    public TeamProposalDTO(TeamProposal tp) {
        this.amount = tp.getAmount();
        this.status = tp.getStatus();
        this.senderSponsor = tp.getSenderSponsor();
        this.receiverTeam = tp.getReceiverTeam();
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

    public Sponsor getSenderSponsor() {
        return senderSponsor;
    }

    public void setSenderSponsor(Sponsor senderSponsor) {
        this.senderSponsor = senderSponsor;
    }

    public Team getReceiverTeam() {
        return receiverTeam;
    }

    public void setReceiverTeam(Team receiverTeam) {
        this.receiverTeam = receiverTeam;
    }

    @Override
    public String toString() {
        return "TeamProposalDTO{" +
                ", senderSponsor=" + senderSponsor +
                ", receiverTeam=" + receiverTeam +
                "amount=" + amount +
                ", status='" + status + '\'' +
                '}';
    }
}

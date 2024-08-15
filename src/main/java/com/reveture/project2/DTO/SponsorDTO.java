package com.reveture.project2.DTO;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.reveture.project2.entities.Sponsor;
import com.reveture.project2.entities.TeamProposal;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToMany;

import java.util.List;
import java.util.UUID;

public class SponsorDTO {

    private UUID sponsorId;
    private String username;
    private String category;
    private String name;
    private Double budget;

    public SponsorDTO(Sponsor sponsor) {
        this.sponsorId = sponsor.getSponsorId();
        this.username = sponsor.getUsername();
        this.category = sponsor.getCategory();
        this.name = sponsor.getName();
        this.budget = sponsor.getBudget();
    }

    public UUID getSponsorId() {
        return sponsorId;
    }

    public void setSponsorId(UUID sponsorId) {
        this.sponsorId = sponsorId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getBudget() {
        return budget;
    }

    public void setBudget(Double budget) {
        this.budget = budget;
    }

    @Override
    public String toString() {
        return "SponsorDTO{" +
                "sponsorId=" + sponsorId +
                ", username='" + username + '\'' +
                ", category='" + category + '\'' +
                ", name='" + name + '\'' +
                ", budget=" + budget +
                '}';
    }
}

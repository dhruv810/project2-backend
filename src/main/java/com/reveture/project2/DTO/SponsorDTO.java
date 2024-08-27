package com.reveture.project2.DTO;

import com.reveture.project2.entities.Sponsor;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SponsorDTO {

    private UUID sponsorId;
    private String username;
    private String category;
    private String name;
    private Double budget;
    private String jwt;

    public SponsorDTO(Sponsor sponsor) {
        this.sponsorId = sponsor.getSponsorId();
        this.username = sponsor.getUsername();
        this.category = sponsor.getCategory();
        this.name = sponsor.getName();
        this.budget = sponsor.getBudget();
    }

    public SponsorDTO(Sponsor sponsor, String jwt) {
        this.sponsorId = sponsor.getSponsorId();
        this.username = sponsor.getUsername();
        this.category = sponsor.getCategory();
        this.name = sponsor.getName();
        this.budget = sponsor.getBudget();
        this.jwt = jwt;
    }

}

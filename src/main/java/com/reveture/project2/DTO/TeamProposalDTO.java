package com.reveture.project2.DTO;

import lombok.Data;

import java.util.UUID;

@Data
public class TeamProposalDTO {

    private Double amount;
    private String status;
    private String sponsor_name;
    private String team_name;

}

package com.uahb.m1info.common.service.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CompteDto {

    private Long id;
    private Long clientId;
    private int solde;
    private Date dateCreation;

}

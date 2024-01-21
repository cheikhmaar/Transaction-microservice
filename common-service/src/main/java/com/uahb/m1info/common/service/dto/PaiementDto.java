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
public class PaiementDto {

    private Long id;
    private Long clientId;
    private Long serviceId;
    private Long transactionId;
    private Date datePaiement;
    private int montantService;
    private String paiement_status;
    private String compteStatus;
}

package com.uahb.m1info.common.service.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ClientDto {

    private Long id;
    private String nomComplet;
    private String code;
    private int depotInitiale;
    private String client_status;
    private String compteStatus;

}


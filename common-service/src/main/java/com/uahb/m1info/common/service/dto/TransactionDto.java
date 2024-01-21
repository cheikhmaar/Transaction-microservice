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
public class TransactionDto {



    private Long id;
    private Date date;
    private Long compteId;
    private int montant;
    private String type;

}

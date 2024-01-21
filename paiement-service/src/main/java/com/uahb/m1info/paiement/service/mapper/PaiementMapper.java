package com.uahb.m1info.paiement.service.mapper;

import com.uahb.m1info.common.service.dto.ClientDto;
import com.uahb.m1info.common.service.dto.PaiementDto;
import com.uahb.m1info.common.service.event.ClientStatus;
import com.uahb.m1info.common.service.event.CompteStatus;
import com.uahb.m1info.common.service.event.PaiementStatus;
import com.uahb.m1info.paiement.service.entity.Client;
import com.uahb.m1info.paiement.service.entity.Paiement;
import org.springframework.stereotype.Component;

import java.text.ParseException;

@Component
public class PaiementMapper {

    public Paiement paiementtDtoToPaiementEntity(PaiementDto paiementDto) throws ParseException {
        return Paiement.builder()
                .id(paiementDto.getId())
                .montantService(paiementDto.getMontantService())
                .clientId(paiementDto.getClientId())
                .serviceId(paiementDto.getServiceId())
                .datePaiement(paiementDto.getDatePaiement())
                .paiementStatus(PaiementStatus.CREATED.name())
                .compteStatus(CompteStatus.INIT.name())
                .build();
    }

    public PaiementDto paiementEntityToPaiementDto(Paiement paiement) {
        return PaiementDto.builder()
                .id(paiement.getId())
                .montantService(paiement.getMontantService())
                .datePaiement(paiement.getDatePaiement())
                .paiement_status(paiement.getPaiementStatus())
                .compteStatus(paiement.getCompteStatus())
                .build();
    }
}

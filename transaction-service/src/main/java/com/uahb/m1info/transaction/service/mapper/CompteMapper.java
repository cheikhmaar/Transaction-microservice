package com.uahb.m1info.transaction.service.mapper;

import com.uahb.m1info.common.service.dto.CompteDto;
import com.uahb.m1info.transaction.service.entity.Compte;
import org.springframework.stereotype.Component;


@Component
public class CompteMapper {

    public CompteDto CompteEntityToCompteDto(Compte compte){
        return CompteDto.builder()
                .id(compte.getId())
                .clientId(compte.getClientId())
                .dateCreation(compte.getDateCreation())
                .solde(compte.getSolde())
                .build();
    }

    public Compte compteDtoToCompteEntity(CompteDto compteDto) {
        Compte compte = new Compte();
        compte.setId(compteDto.getId());
        compte.setClientId(compteDto.getClientId());
        compte.setSolde(compteDto.getSolde());
        compte.setDateCreation(compteDto.getDateCreation());
        return compte;
    }

}

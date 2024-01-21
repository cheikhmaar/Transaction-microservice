package com.uahb.m1info.paiement.service.mapper;

import com.uahb.m1info.common.service.dto.ClientDto;
import com.uahb.m1info.common.service.event.ClientStatus;
import com.uahb.m1info.common.service.event.CompteStatus;
import com.uahb.m1info.paiement.service.entity.Client;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.text.SimpleDateFormat;

@Component
public class ClientMapper {
    public Client clientDtoToClientEntity(ClientDto clientDto) throws ParseException {
        return Client.builder()
                .id(clientDto.getId())
                .nomComplet(clientDto.getNomComplet())
                .code(clientDto.getCode())
                .depotInitiale(clientDto.getDepotInitiale())
                .clientStatus(ClientStatus.CREATED.name())
                .compteStatus(CompteStatus.INIT.name())
                .build();
    }

    public ClientDto clientEntityToClientDto(Client client){
        return ClientDto.builder()
                .id(client.getId())
                .nomComplet(client.getNomComplet())
                .code(client.getCode())
                .depotInitiale(client.getDepotInitiale())
                .client_status(client.getClientStatus())
                .compteStatus(client.getCompteStatus())
                .build();
    }
}

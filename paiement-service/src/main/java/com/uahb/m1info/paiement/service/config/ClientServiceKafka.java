package com.uahb.m1info.paiement.service.config;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.uahb.m1info.common.service.event.CompteEvent;
import com.uahb.m1info.paiement.service.service.ClientService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class ClientServiceKafka {
    private final ClientService clientService;

    public ClientServiceKafka(ClientService clientService) {
        this.clientService = clientService;
    }


    @KafkaListener(topics = "compte-event-topic", groupId = "default", containerFactory
            = "CompteEventListner")
    public void getResponse(CompteEvent compteEvent) throws JsonProcessingException {

        clientService.updateClient(compteEvent);

    }
}

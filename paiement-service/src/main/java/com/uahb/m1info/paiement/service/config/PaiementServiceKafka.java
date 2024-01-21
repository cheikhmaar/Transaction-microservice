package com.uahb.m1info.paiement.service.config;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.uahb.m1info.common.service.event.CompteEvent;
import com.uahb.m1info.common.service.event.TransactionEvent;
import com.uahb.m1info.paiement.service.service.ClientService;
import com.uahb.m1info.paiement.service.service.PaiementService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class PaiementServiceKafka {

    private final PaiementService paiementService;

    public PaiementServiceKafka(PaiementService paiementService) {
        this.paiementService = paiementService;
    }


    @KafkaListener(topics = "compte-event-topic", groupId = "default", containerFactory
            = "CompteEventListner")
    public void getResponse(CompteEvent compteEvent) throws JsonProcessingException {

        paiementService.updatePaiement(compteEvent);

    }

    /*
    @KafkaListener(topics = "transaction-event-topic", groupId = "default", containerFactory
            = "TransactionEventListner")
    public void getResponses(TransactionEvent transactionEvent) throws JsonProcessingException {

        paiementService.updateTransaction(transactionEvent);

    }

     */

}

package com.uahb.m1info.transaction.service.config;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.uahb.m1info.common.service.event.PaiementEvent;
import com.uahb.m1info.common.service.event.TransactionEvent;
import com.uahb.m1info.transaction.service.service.TransactionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import java.text.ParseException;

@Slf4j
@Component
public class TransactionServiceKafka {

    /*
    private static final String transactionTopic = "transaction-event-topic";

    private final TransactionService transactionService;
    private final KafkaTemplate<String, TransactionEvent> kafkaTemplate;

    public TransactionServiceKafka(TransactionService transactionService, KafkaTemplate<String, TransactionEvent> kafkaTemplate) {
        this.transactionService = transactionService;
        this.kafkaTemplate = kafkaTemplate;
    }


    @KafkaListener(topics = "paiement-event-topic", groupId = "default", containerFactory = "PaiementEventListner")
    public void handlePaiementTransaction(PaiementEvent paiementEvent) throws JsonProcessingException, ParseException {
        log.info("message consumed {}", paiementEvent.getEventId());

        // Utilisez la méthode de service pour enregistrer la transaction
        TransactionEvent transactionEvent = transactionService.saveTransaction(paiementEvent);

        // Envoyez l'événement de transaction vers Kafka
        kafkaTemplate.send(transactionTopic, transactionEvent);
    }

     */
}

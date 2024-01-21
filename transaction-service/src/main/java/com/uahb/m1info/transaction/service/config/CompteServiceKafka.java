package com.uahb.m1info.transaction.service.config;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.uahb.m1info.common.service.event.ClientEvent;
import com.uahb.m1info.common.service.event.CompteEvent;
import com.uahb.m1info.common.service.event.PaiementEvent;
import com.uahb.m1info.transaction.service.service.CompteService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import java.text.ParseException;

@Slf4j
@Component
public class CompteServiceKafka {
    private static final String compteTopic = "compte-event-topic";

    private final CompteService compteService;
    private final KafkaTemplate<String, CompteEvent> kafkaTemplate;

    public CompteServiceKafka(CompteService compteService, KafkaTemplate<String, CompteEvent> kafkaTemplate) {
        this.compteService = compteService;
        this.kafkaTemplate = kafkaTemplate;
    }

    @KafkaListener(topics = "client-event-topic", groupId = "default", containerFactory
            = "ClientEventListner")
    public void consumeMessage(ClientEvent clientEvent) throws JsonProcessingException, ParseException {
        log.info("message consumed {}", clientEvent.getEventId());
        CompteEvent compteEvent = compteService.saveCompte(clientEvent);
        kafkaTemplate.send(compteTopic, compteEvent);
    }


    @KafkaListener(topics = "paiement-event-topic", groupId = "default", containerFactory = "PaiementEventListner")
    public void handlePaiementEvent(PaiementEvent paiementEvent) throws JsonProcessingException, ParseException {
        log.info("message consumed {}", paiementEvent.getEventId());

        try {
            // Utilisez la méthode de service pour mettre à jour le compte
            CompteEvent compteEvent = compteService.updateCompte(paiementEvent);

            // Envoyez l'événement de compte mis à jour vers Kafka
            kafkaTemplate.send(compteTopic, compteEvent);
        } catch (RuntimeException e) {
            // Gérez l'exception ici (par exemple, en journalisant un message d'erreur ou en effectuant une autre action appropriée)
            log.error("Une exception s'est produite lors de la gestion du paiement : {}", e.getMessage());
            // Vous pouvez également choisir de ne pas envoyer l'événement de compte mis à jour vers Kafka dans ce cas.
        }
    }






}

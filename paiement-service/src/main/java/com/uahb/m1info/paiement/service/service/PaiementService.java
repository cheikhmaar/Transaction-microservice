package com.uahb.m1info.paiement.service.service;

import com.uahb.m1info.common.service.dto.PaiementDto;
import com.uahb.m1info.common.service.event.*;
import com.uahb.m1info.paiement.service.entity.Paiement;
import com.uahb.m1info.paiement.service.mapper.PaiementMapper;
import com.uahb.m1info.paiement.service.repository.PaiementRepository;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
import java.util.Optional;

@Service
public class PaiementService {

    private static final String paiementTopic = "paiement-event-topic";

    private final PaiementMapper paiementMapper;
    private final PaiementRepository paiementRepository;
    private final KafkaTemplate<String, PaiementEvent> kafkaTemplate;

    public PaiementService(PaiementMapper paiementMapper, PaiementRepository paiementRepository, KafkaTemplate<String, PaiementEvent> kafkaTemplate) {
        this.paiementMapper = paiementMapper;
        this.paiementRepository = paiementRepository;
        this.kafkaTemplate = kafkaTemplate;
    }

    @Transactional
    public Paiement savePaiement(PaiementDto paiementDto) throws ParseException {

        // Enregistrez le paiement dans la base de données
        Paiement paiement = paiementRepository.save(paiementMapper.paiementtDtoToPaiementEntity(paiementDto));
        paiement.setId(paiement.getId());

        // Créez un événement PaiementEvent
        PaiementEvent paiementEvent = new PaiementEvent(paiementDto, PaiementStatus.CREATED);

        // Envoyez l'événement vers Kafka
        kafkaTemplate.send(paiementTopic, paiementEvent);

        return paiement;
    }


    @Transactional
    public void updatePaiement(CompteEvent compteEvent) {
        Optional<Paiement> optionalPaiement = paiementRepository.findById(compteEvent.getCompteDto().getClientId());
        if (optionalPaiement.isPresent()) {
            Paiement paiement = optionalPaiement.get();
            boolean isCompteSaved = CompteStatus.UPDATED.equals(compteEvent.getCompteStatus());

            if (!isCompteSaved) {
                // Le compte a été mis à jour avec succès
                paiement.setPaiementStatus(PaiementStatus.COMPLETED.name());
            } else {
                // Le compte n'a pas été sauvegardé en raison d'un solde insuffisant
                paiement.setPaiementStatus(PaiementStatus.ERROR_SOLDE.name());
            }

            paiement.setCompteStatus(CompteStatus.UPDATED.name());
            // Enregistrez les modifications dans la base de données
            paiementRepository.save(paiement);
        }
    }


}

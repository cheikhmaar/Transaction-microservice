package com.uahb.m1info.transaction.service.service;

import com.uahb.m1info.common.service.dto.ClientDto;
import com.uahb.m1info.common.service.dto.CompteDto;
import com.uahb.m1info.common.service.dto.PaiementDto;
import com.uahb.m1info.common.service.dto.TransactionDto;
import com.uahb.m1info.common.service.event.*;
import com.uahb.m1info.transaction.service.entity.Compte;
import com.uahb.m1info.transaction.service.entity.Transaction;
import com.uahb.m1info.transaction.service.mapper.CompteMapper;
import com.uahb.m1info.transaction.service.mapper.TransactionMapper;
import com.uahb.m1info.transaction.service.repository.CompteRepository;
import com.uahb.m1info.transaction.service.repository.TransactionRepository;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.uahb.m1info.common.service.event.TransactionEvent;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


import java.text.ParseException;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class TransactionService {


    private final TransactionRepository transactionRepository;
    private final TransactionMapper transactionMapper;
    private final KafkaTemplate<String, TransactionEvent> kafkaTemplate;
    private static final String transactionTopic = "transaction-event-topic";

    public TransactionService(TransactionRepository transactionRepository, TransactionMapper transactionMapper, KafkaTemplate<String, TransactionEvent> kafkaTemplate) {
        this.transactionRepository = transactionRepository;
        this.transactionMapper = transactionMapper;
        this.kafkaTemplate = kafkaTemplate;
    }

    public TransactionDto find(Long compteId){
        Optional<Transaction> transaction =  transactionRepository.findById(compteId);
        if(transaction.isEmpty()){
            return null;
        }
        return transactionMapper.TransactionEntityToTransactionDto(transaction.get());
    }



    /*

    @Transactional
    public TransactionEvent saveTransaction(PaiementEvent paiementEvent) throws ParseException {
        PaiementDto paiementDto = paiementEvent.getPaiementDto();

        // Créez un objet TransactionDto à partir des informations du paiement
        TransactionDto transactionDto = TransactionDto.builder()
                .paiementId(paiementDto.getId())
                .date(new Date())  // Utilisez la date actuelle
                .montant(paiementDto.getMontantService())
                .type("Retraite")
                .build();

        // Enregistrez la transaction dans la base de données
        Transaction transaction = transactionRepository.save(transactionMapper.TransactionDtoToTransactionEntity(transactionDto));

        // Créez un événement TransactionEvent
        TransactionEvent transactionEvent = new TransactionEvent(transactionMapper.TransactionEntityToTransactionDto(transaction), TransactionStatus.UPDATED);

        // Envoyez l'événement vers Kafka
        kafkaTemplate.send(transactionTopic, transactionEvent);

        return transactionEvent;
    }




    public TransactionDto find(Long transactionId){
        Optional<Transaction> transaction =  transactionRepository.findById(transactionId);
        if(transaction.isEmpty()){
            return null;
        }
        return transactionMapper.TransactionEntityToTransactionDto(transaction.get());
    }

     */
}

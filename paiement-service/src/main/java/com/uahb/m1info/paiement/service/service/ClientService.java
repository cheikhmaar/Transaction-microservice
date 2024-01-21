package com.uahb.m1info.paiement.service.service;

import com.uahb.m1info.common.service.dto.ClientDto;
import com.uahb.m1info.common.service.event.ClientEvent;
import com.uahb.m1info.common.service.event.ClientStatus;
import com.uahb.m1info.common.service.event.CompteEvent;
import com.uahb.m1info.common.service.event.CompteStatus;
import com.uahb.m1info.paiement.service.entity.Client;
import com.uahb.m1info.paiement.service.mapper.ClientMapper;
import com.uahb.m1info.paiement.service.repository.ClientRepository;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class ClientService {

    private static final String clientTopic = "client-event-topic";

    private final ClientMapper clientMapper;
    private final ClientRepository clientRepository;
    private final KafkaTemplate<String, ClientEvent> kafkaTemplate;

    public ClientService(ClientMapper clientMapper, ClientRepository clientRepository, KafkaTemplate<String, ClientEvent> kafkaTemplate) {
        this.clientMapper = clientMapper;
        this.clientRepository = clientRepository;
        this.kafkaTemplate = kafkaTemplate;
    }

    @Transactional
    public Client saveClient(ClientDto clientDto) throws ParseException {
        // Vérifiez d'abord si un client avec le même code existe déjà
        Optional<Client> existingClientOptional = clientRepository.findByCode(clientDto.getCode());

        if (existingClientOptional.isPresent()) {
            // Le client existe déjà avec le même code, vous pouvez gérer cette situation comme vous le souhaitez
            // Par exemple, vous pouvez générer une exception ou ne rien faire.
            // Dans cet exemple, nous générons une exception pour indiquer que le client existe déjà.
            throw new RuntimeException("Client with code " + clientDto.getCode() + " already exists.");
        } else if (clientDto.getDepotInitiale() >= 1000) {
            // Le client n'existe pas encore avec le même code et le dépôt initial est suffisant, créez le client
            Client client = clientRepository.save(clientMapper.clientDtoToClientEntity(clientDto));
            clientDto.setId(client.getId());
            ClientEvent clientEvent = new ClientEvent(clientDto, ClientStatus.CREATED);

            // Envoyez l'événement au service transaction-service
            kafkaTemplate.send(clientTopic, clientEvent);

            return client;
        } else {
            // Le dépôt initial est inférieur ou égal à 1000, ne créez pas le client
            return null;
        }
    }


    @Transactional
    public void updateClient(CompteEvent compteEvent) {
        Optional<Client> clientOptional = clientRepository.findById(compteEvent.getCompteDto().getClientId());
        if (clientOptional.isPresent()) {
            Client client = clientOptional.get();
            boolean isCompteSaved = CompteStatus.UPDATED.equals(compteEvent.getCompteStatus());

            if (!isCompteSaved) {
                // Le compte a été mis à jour avec succès
                client.setClientStatus(ClientStatus.COMPLETED.name());
            } else {
                // Le compte n'a pas été sauvegardé en raison d'un solde insuffisant
                client.setClientStatus(ClientStatus.ERROR_SOLDE.name());
            }

            client.setCompteStatus(CompteStatus.UPDATED.name());
        }
    }

    public boolean clientExists(String code) {
        // Utilisez le repository pour rechercher le client par son code
        Optional<Client> clientOptional = clientRepository.findByCode(code);

        // Retournez true si le client existe, sinon retournez false
        return clientOptional.isPresent();
    }

}

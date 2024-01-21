package com.uahb.m1info.transaction.service.service;

import com.uahb.m1info.common.service.dto.ClientDto;
import com.uahb.m1info.common.service.dto.CompteDto;
import com.uahb.m1info.common.service.dto.PaiementDto;
import com.uahb.m1info.common.service.event.ClientEvent;
import com.uahb.m1info.common.service.event.CompteEvent;
import com.uahb.m1info.common.service.event.CompteStatus;
import com.uahb.m1info.common.service.event.PaiementEvent;
import com.uahb.m1info.transaction.service.entity.Compte;
import com.uahb.m1info.transaction.service.entity.Transaction;
import com.uahb.m1info.transaction.service.mapper.CompteMapper;
import com.uahb.m1info.transaction.service.repository.CompteRepository;
import com.uahb.m1info.transaction.service.repository.TransactionRepository;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class CompteService {

    private final CompteRepository compteRepository;
    private final CompteMapper compteMapper;
    private final TransactionRepository transactionRepository;
    private final KafkaTemplate<String, CompteEvent> kafkaTemplate;
    private static final String compteTopic = "compte-event-topic";

    public CompteService(CompteRepository compteRepository, CompteMapper compteMapper,TransactionRepository transactionRepositor, KafkaTemplate<String, CompteEvent> kafkaTemplate) {
        this.compteRepository = compteRepository;
        this.compteMapper = compteMapper;
        this.transactionRepository = transactionRepositor;
        this.kafkaTemplate = kafkaTemplate;
    }

    @Transactional
    public CompteEvent saveCompte(ClientEvent clientEvent) throws ParseException {
        ClientDto clientDto = clientEvent.getCLientDto();

        if (clientDto.getDepotInitiale() >= 1000) {
            // Créez un compte avec le dépôt initial
            CompteDto compteDto = CompteDto.builder()
                    .clientId(clientDto.getId())
                    .solde(clientDto.getDepotInitiale())
                    .dateCreation(new Date())
                    .build();

            // Enregistrez le compte dans la base de données
            Compte compte = compteRepository.save(compteMapper.compteDtoToCompteEntity(compteDto));

            // Créez un événement CompteEvent
            CompteEvent compteEvent = new CompteEvent(compteMapper.CompteEntityToCompteDto(compte), CompteStatus.INIT);

            // Envoyez l'événement
            kafkaTemplate.send(compteTopic, compteEvent);

            return compteEvent;
        } else {
            // Le dépôt initial est inférieur ou égal à 1000, ne créez pas de compte
            return null;
        }
    }

    @Transactional
    public CompteEvent updateCompte(PaiementEvent paiementEvent) throws ParseException {
        // Extraire les informations du paiement de l'événement
        PaiementDto paiementDto = paiementEvent.getPaiementDto();

        // Mettre à jour le solde du compte du client en conséquence
        Long clientId = paiementDto.getClientId();
        int montant = paiementDto.getMontantService();
        Compte compte = compteRepository.findByClientId(clientId);

        Date date = new Date();
        String type = "Retraite";

        if (compte != null) {
            // Vérifier si le montant du paiement est inférieur ou égal au solde du compte
            if (montant <= compte.getSolde()) {
                // Mettez à jour le solde du compte

                compte.setSolde(compte.getSolde() - montant);

                // Créez un événement CompteEvent pour refléter la mise à jour
                CompteEvent compteEvent = new CompteEvent(compteMapper.CompteEntityToCompteDto(compte), CompteStatus.UPDATED);

                // Définissez le paiementId dans la transaction
                Transaction transaction = new Transaction(null, paiementDto.getClientId(), date, paiementDto.getMontantService(), type);

                // Enregistrez la mise à jour du compte
                compteRepository.save(compte);

                // Enregistrez la transaction dans la base de données
                transactionRepository.save(transaction);

                // Envoyez l'événement vers Kafka
                kafkaTemplate.send(compteTopic, compteEvent);


                return compteEvent;
            } else {
                // Le montant du paiement est supérieur au solde du compte, vous pouvez gérer cette situation comme vous le souhaitez.
                // Par exemple, vous pouvez générer une exception ou ignorer le paiement.
                // Dans cet exemple, nous générons une exception.
                throw new RuntimeException("Montant du paiement supérieur au solde du compte pour clientId : " + clientId);
            }
        } else {
            // Gérez le cas où le compte n'est pas trouvé (peut-être retourner null ou générer une exception)
            // Vous pouvez personnaliser cette partie en fonction de vos besoins.
            // Par exemple, vous pouvez générer une exception pour indiquer que le compte n'a pas été trouvé.
            throw new RuntimeException("Compte non trouvé pour clientId : " + clientId);
        }
    }

    @Transactional
    public CompteEvent deposer(Long clientId, int montantDepot) {
        Compte compte = compteRepository.findByClientId(clientId);

        Date date = new Date();
        String type = "Dépôt";

        if (compte != null) {
            if (montantDepot > 0) {
                // Mettez à jour le solde du compte
                compte.setSolde(compte.getSolde() + montantDepot);

                // Créez un événement CompteEvent pour refléter la mise à jour
                CompteEvent compteEvent = new CompteEvent(compteMapper.CompteEntityToCompteDto(compte), CompteStatus.UPDATED);

                // Enregistrez la mise à jour du compte
                compteRepository.save(compte);

                // Enregistrez la transaction dans la base de données
                Transaction transaction = new Transaction();
                transaction.setCompteId(clientId);
                transaction.setDate(date);
                transaction.setMontant(montantDepot);
                transaction.setType(type);

                transactionRepository.save(transaction);

                // Envoyez l'événement vers Kafka
                kafkaTemplate.send(compteTopic, compteEvent);

                return compteEvent;
            } else {
                throw new RuntimeException("Le montant du dépôt doit être supérieur à zéro.");
            }
        } else {
            throw new RuntimeException("Compte non trouvé pour clientId : " + clientId);
        }
    }


    public CompteDto find(Long compteId){
        Optional<Compte> compte =  compteRepository.findById(compteId);
        if(compte.isEmpty()){
            return null;
        }
        return compteMapper.CompteEntityToCompteDto(compte.get());
    }
}
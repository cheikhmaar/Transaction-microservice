package com.uahb.m1info.paiement.service.controller;

import com.uahb.m1info.common.service.dto.ClientDto;
import com.uahb.m1info.common.service.dto.PaiementDto;
import com.uahb.m1info.paiement.service.entity.Client;
import com.uahb.m1info.paiement.service.entity.Paiement;
import com.uahb.m1info.paiement.service.entity.Service;
import com.uahb.m1info.paiement.service.repository.ClientRepository;
import com.uahb.m1info.paiement.service.repository.ServiceRepository;
import com.uahb.m1info.paiement.service.service.PaiementService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.text.ParseException;
import java.util.Optional;

@RestController
public class PaiementController {

    private final PaiementService paiementService;
    private final ClientRepository clientRepository;
    private final ServiceRepository serviceRepository;


    public PaiementController(PaiementService paiementService, ClientRepository clientRepository, ServiceRepository serviceRepository) {
        this.paiementService = paiementService;
        this.clientRepository = clientRepository;
        this.serviceRepository = serviceRepository;
    }

    @PostMapping("/paiement/add")
    public ResponseEntity<Object> save(@RequestBody PaiementDto paiementDto) throws ParseException {
        // Vérifiez si le client existe
        if (!clientExists(paiementDto.getClientId())) {
            return ResponseEntity.badRequest().body("Paiement refusé car le client n'existe pas");
        }

        // Vérifiez si le service existe
        if (!serviceExists(paiementDto.getServiceId())) {
            return ResponseEntity.badRequest().body("Paiement refusé car le service n'existe pas");
        }

        // Si le client et le service existent, enregistrez le paiement
        Paiement paiement = paiementService.savePaiement(paiementDto);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}").buildAndExpand(paiement.getId()).toUri();
        return ResponseEntity.created(location).build();
    }


    private boolean clientExists(Long clientId) {
        // Utilisez le repository pour rechercher le client par son ID
        Optional<Client> clientOptional = clientRepository.findById(clientId);

        // Retournez true si le client existe, sinon retournez false
        return clientOptional.isPresent();
    }

    private boolean serviceExists(Long serviceId) {
        // Utilisez le repository pour rechercher le service par son ID
        Optional<Service> serviceOptional = serviceRepository.findById(serviceId);

        // Retournez true si le service existe, sinon retournez false
        return serviceOptional.isPresent();
    }


}


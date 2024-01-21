package com.uahb.m1info.paiement.service.controller;

import com.uahb.m1info.common.service.dto.ClientDto;
import com.uahb.m1info.paiement.service.entity.Client;
import com.uahb.m1info.paiement.service.repository.ClientRepository;
import com.uahb.m1info.paiement.service.service.ClientService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.text.ParseException;
import java.util.List;
import java.util.Optional;

@RestController
public class ClientController {

    private final ClientService clientService;
    private final ClientRepository clientRepository;

    public ClientController(ClientService clientService, ClientRepository clientRepository) {
        this.clientService = clientService;
        this.clientRepository = clientRepository;
    }

    @PostMapping("/client/add")
    public ResponseEntity<Object> save(@RequestBody ClientDto clientDto) throws ParseException {
        // Vérifiez si le client existe
        if (clientService.clientExists(clientDto.getCode())) {
            return ResponseEntity.badRequest().body("Le client avec le code '" + clientDto.getCode() + "' existe déjà, veuillez en créer un autre.");
        }

        if (clientDto.getDepotInitiale() < 1000) {
            // Le dépôt initial est inférieur à 1000, ne créez pas le client ni le compte.
            return ResponseEntity.badRequest().body("Le dépôt initial doit être supérieur ou égal à 1000.");
        }

        Client client = clientService.saveClient(clientDto);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}").buildAndExpand(client.getId()).toUri();
        return ResponseEntity.created(location).build();
    }





}

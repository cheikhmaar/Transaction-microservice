package com.uahb.m1info.transaction.service.controller;

import com.uahb.m1info.common.service.dto.CompteDto;
import com.uahb.m1info.common.service.event.CompteEvent;
import com.uahb.m1info.transaction.service.entity.MontantDepotRequest;
import com.uahb.m1info.transaction.service.service.CompteService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class CompteController {

    private final CompteService compteService;

    public CompteController(CompteService compteService) {
        this.compteService = compteService;
    }

    @PostMapping("/compte/deposer/{clientId}")
    public ResponseEntity<Object> deposerMontant(@PathVariable Long clientId, @RequestBody MontantDepotRequest montantDepotRequest) {
        try {
            int montant = montantDepotRequest.getMontantDepot(); // Obtenir le montant de dépôt à partir de l'objet MontantDepotRequest
            CompteEvent compteEvent = compteService.deposer(clientId, montant);
            return ResponseEntity.ok("Dépôt réussi. Nouveau solde : " + compteEvent.getCompteDto().getSolde());
        } catch (NumberFormatException e) {
            return ResponseEntity.badRequest().body("Montant de dépôt invalide : " + montantDepotRequest.getMontantDepot());
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body("Échec du dépôt : " + e.getMessage());
        }
    }


    /*@GetMapping("/compte/list")
    @ResponseBody
    public ResponseEntity findAll(){
        List<CompteDto> ages = compteService.findAll();
        return new ResponseEntity(ages, HttpStatus.OK);
    }*/

    @GetMapping("/compte/{clientId}")
    @ResponseBody
    public ResponseEntity find(@PathVariable Long clientId){
        CompteDto compteDto = compteService.find(clientId);
        return new ResponseEntity(compteDto, HttpStatus.OK);
    }

    @GetMapping("/{clientId}")
    public ResponseEntity<CompteDto> getCompteByClientId(@PathVariable Long clientId) {
        CompteDto compteDto = compteService.find(clientId);
        if (compteDto != null) {
            return new ResponseEntity<>(compteDto, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}

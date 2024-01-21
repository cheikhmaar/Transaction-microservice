package com.uahb.m1info.transaction.service.controller;

import com.uahb.m1info.common.service.dto.CompteDto;
import com.uahb.m1info.common.service.dto.TransactionDto;
import com.uahb.m1info.transaction.service.entity.Transaction;
import com.uahb.m1info.transaction.service.repository.TransactionRepository;
import com.uahb.m1info.transaction.service.service.CompteService;
import com.uahb.m1info.transaction.service.service.TransactionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class TransactionController {


    private final TransactionService transactionService;

    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }



    @GetMapping("/transaction/{transactionId}")
    @ResponseBody
    public ResponseEntity find(@PathVariable Long transactionId){
        TransactionDto  transactionDto = transactionService.find(transactionId);
        return new ResponseEntity(transactionDto, HttpStatus.OK);
    }

    /*
    @GetMapping("afficherTransactionParClient")
    public List<Transaction> afficherTransactionParClient(@PathVariable Long idTransac)
    {
        List<Transaction> transactions = TransactionRepository.findAll();
        List<Transaction> transactionList = new ArrayList<>();
        for (Transaction tr: transactions){

        }

    }

    */

    /*
    @GetMapping("/{transactionId}")
    public ResponseEntity<TransactionDto> getTransactionByClientId(@PathVariable Long transactionId) {
        TransactionDto transactionDto = transactionService.find(transactionId);
        if (transactionDto != null) {
            return new ResponseEntity<>(transactionDto, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

     */

}

package com.uahb.m1info.transaction.service.mapper;

import com.uahb.m1info.common.service.dto.CompteDto;
import com.uahb.m1info.common.service.dto.TransactionDto;
import com.uahb.m1info.transaction.service.entity.Compte;
import com.uahb.m1info.transaction.service.entity.Transaction;
import org.springframework.stereotype.Component;

@Component
public class TransactionMapper {

    public TransactionDto TransactionEntityToTransactionDto(Transaction transaction){
        return TransactionDto.builder()
                .id(transaction.getId())
                .date(transaction.getDate())
                .montant(transaction.getMontant())
                .type(transaction.getType())
                .build();
    }

    public Transaction TransactionDtoToTransactionEntity(TransactionDto transactionDto) {
        Transaction transaction = new Transaction();
        transaction.setId(transactionDto.getId());
        transaction.setDate(transactionDto.getDate());
        transaction.setMontant(transactionDto.getMontant());
        transaction.setType(transactionDto.getType());
        return transaction;
    }
}

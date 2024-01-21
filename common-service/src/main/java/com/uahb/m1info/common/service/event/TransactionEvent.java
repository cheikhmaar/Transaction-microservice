package com.uahb.m1info.common.service.event;

import com.uahb.m1info.common.service.dto.CompteDto;
import com.uahb.m1info.common.service.dto.TransactionDto;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.Random;

@NoArgsConstructor
@Data
public class TransactionEvent implements Event{

    private Long eventId = new Random().nextLong();
    private Date eventDate = new Date();
    private TransactionDto transactionDto;
    private TransactionStatus transactionStatus;

    public TransactionEvent(TransactionDto transactionDto, TransactionStatus transactionStatus) {
        this.transactionDto = transactionDto;
        this.transactionStatus = transactionStatus;
    }

    @Override
    public Long getEventId() {
        return eventId;
    }

    @Override
    public Date getEventDate() {
        return eventDate;
    }
}

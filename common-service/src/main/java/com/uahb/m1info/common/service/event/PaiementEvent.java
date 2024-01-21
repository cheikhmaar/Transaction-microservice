package com.uahb.m1info.common.service.event;

import com.uahb.m1info.common.service.dto.ClientDto;
import com.uahb.m1info.common.service.dto.PaiementDto;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.Random;

@NoArgsConstructor
@Data
public class PaiementEvent implements Event {
    private Long eventId = new Random().nextLong();
    private Date eventDate = new Date();
    private PaiementDto paiementDto;
    private PaiementStatus paiementStatus;

    public PaiementEvent(PaiementDto paiementDto, PaiementStatus paiementStatus) {
        this.paiementDto = paiementDto;
        this.paiementStatus = paiementStatus;
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

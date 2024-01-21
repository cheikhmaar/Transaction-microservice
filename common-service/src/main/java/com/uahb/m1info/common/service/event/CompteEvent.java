package com.uahb.m1info.common.service.event;

import com.uahb.m1info.common.service.dto.CompteDto;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.Random;

@NoArgsConstructor
@Data
public class CompteEvent implements Event {

    private Long eventId = new Random().nextLong();
    private Date eventDate = new Date();
    private CompteDto compteDto;
    private CompteStatus compteStatus;

    public CompteEvent(CompteDto compteDto, CompteStatus compteStatus) {
        this.compteDto = compteDto;
        this.compteStatus = compteStatus;
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

package com.uahb.m1info.common.service.event;


import com.uahb.m1info.common.service.dto.ClientDto;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.Random;

@NoArgsConstructor
@Data
public class ClientEvent implements Event {

    private Long eventId = new Random().nextLong();
    private Date eventDate = new Date();
    private ClientDto cLientDto;
    private ClientStatus clientStatus;

    public ClientEvent(ClientDto cLientDto, ClientStatus clientStatus) {
        this.cLientDto = cLientDto;
        this.clientStatus = clientStatus;
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

package com.uahb.m1info.paiement.service.mapper;

import com.uahb.m1info.common.service.dto.ClientDto;
import com.uahb.m1info.common.service.dto.ServiceDto;
import com.uahb.m1info.common.service.event.ClientStatus;
import com.uahb.m1info.common.service.event.CompteStatus;
import com.uahb.m1info.paiement.service.entity.Client;
import com.uahb.m1info.paiement.service.entity.Service;
import org.springframework.stereotype.Component;

import java.text.ParseException;
@Component

public class ServiceMapper {

    public Service serviceDtoToServiceEntity(ServiceDto serviceDto) throws ParseException {
        return Service.builder()
                .id(serviceDto.getId())
                .nomService(serviceDto.getNomService())
                .build();
    }

    public ServiceDto serviceEntityToServiceDto(Service service){
        return ServiceDto.builder()
                .id(service.getId())
                .nomService(service.getNomService())
                .build();
    }
}

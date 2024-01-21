package com.uahb.m1info.paiement.service.service;

import com.uahb.m1info.common.service.dto.ServiceDto;
import com.uahb.m1info.paiement.service.entity.Service;
import com.uahb.m1info.paiement.service.mapper.ServiceMapper;
import com.uahb.m1info.paiement.service.repository.ServiceRepository;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
@org.springframework.stereotype.Service

public class ServiceService {

    private final ServiceMapper serviceMapper;
    private final ServiceRepository serviceRepository;

    public ServiceService(ServiceMapper serviceMapper, ServiceRepository serviceRepository) {
        this.serviceMapper = serviceMapper;
        this.serviceRepository = serviceRepository;
    }
    @Transactional
    public Service saveService(ServiceDto serviceDto) throws ParseException {

        Service service = serviceRepository.save(serviceMapper.serviceDtoToServiceEntity(serviceDto));
        serviceDto.setId(service.getId());
        serviceDto.setNomService(service.getNomService());
        return service;
    }
}

package com.uahb.m1info.paiement.service.controller;

import com.uahb.m1info.common.service.dto.ServiceDto;
import com.uahb.m1info.paiement.service.entity.Service;
import com.uahb.m1info.paiement.service.service.ServiceService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.text.ParseException;

@RestController
public class ServiceController {

    private final ServiceService serviceService;

    public ServiceController(ServiceService serviceService) {
        this.serviceService = serviceService;
    }

    @PostMapping("/service/add")
    public ResponseEntity<Object> save(@RequestBody ServiceDto serviceDto) throws ParseException {
        Service service = serviceService.saveService(serviceDto);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}").buildAndExpand(service.getId()).toUri();
        return ResponseEntity.created(location).build();
    }
}

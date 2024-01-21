package com.uahb.m1info.paiement.service.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
public class Paiement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name="montantService")
    private int montantService;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date datePaiement;
    @Column(name = "client_id")
    private Long clientId;
    @Column(name = "service_id")
    private Long serviceId;
    @Column(name = "paiement_status")
    private String paiementStatus;
    @Column(name = "compte_status")
    private String compteStatus;

}


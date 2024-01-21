package com.uahb.m1info.paiement.service.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
public class Client {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(length = 120)
    private String nomComplet;
    @Column(length = 120)
    private String code;
    @Column(name="depotInitiale")
    private int depotInitiale;
    @Column(name = "client_status")
    private String clientStatus;
    @Column(name = "compte_status")
    private String compteStatus;
}

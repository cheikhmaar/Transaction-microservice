package com.uahb.m1info.paiement.service.repository;

import com.uahb.m1info.paiement.service.entity.Paiement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PaiementRepository extends JpaRepository<Paiement, Long> {
}

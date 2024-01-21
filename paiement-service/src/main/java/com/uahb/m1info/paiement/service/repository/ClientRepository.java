package com.uahb.m1info.paiement.service.repository;

import com.uahb.m1info.paiement.service.entity.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ClientRepository extends JpaRepository<Client, Long> {
    Optional<Client> findByCode(String code);
}

package com.uahb.m1info.transaction.service.repository;

import com.uahb.m1info.transaction.service.entity.Compte;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CompteRepository extends JpaRepository<Compte, Long> {
    Compte findByClientId(Long id);

}

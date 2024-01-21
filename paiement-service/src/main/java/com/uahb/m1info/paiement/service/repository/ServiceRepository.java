package com.uahb.m1info.paiement.service.repository;

import com.uahb.m1info.paiement.service.entity.Client;
import com.uahb.m1info.paiement.service.entity.Service;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ServiceRepository extends JpaRepository<Service, Long>  {
}

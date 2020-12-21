package com.gbm.challenge.services;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.gbm.challenge.domains.GBMOrder;

@Repository
public interface GBMOrderRepository extends JpaRepository<GBMOrder, Long>{

}

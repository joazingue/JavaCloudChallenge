
package com.gbm.challenge.services;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.gbm.challenge.domains.Issuer;

@Repository
public interface IssuerRepository extends JpaRepository<Issuer, Long>{

}

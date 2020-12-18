
package com.gbm.challenge.services;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.gbm.challenge.domains.InvestmentAccount;

@Repository
public interface InvestmentAccountRespository extends JpaRepository<InvestmentAccount, Long> {

}

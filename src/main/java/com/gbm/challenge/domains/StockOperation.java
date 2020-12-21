
package com.gbm.challenge.domains;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.gbm.challenge.services.rules.Validation;

@Entity
public class StockOperation {
	/*
	 * This Class validates the Account's current balance and sets business errors
	 * Can be saved to log the transactions 
	 */

	@Id
	@GeneratedValue
	@JsonIgnore
	private Long idStock;
	@OneToOne
	private InvestmentAccount currentBalance;
	@ElementCollection
	private List<Validation> businessErrors = new ArrayList<>();
	
	public StockOperation() {
	}
	public InvestmentAccount getCurrentBalance() {
		return currentBalance;
	}
	public void setCurrentBalance(InvestmentAccount currentBalance) {
		this.currentBalance = currentBalance;
	}
	public List<Validation> addBusinessError(Validation be){
		businessErrors.add(be);
		return businessErrors;
	}
	public List<Validation> getBusinessErrors() {
		return businessErrors;
	}
	public boolean IsValid() {
		return businessErrors.isEmpty();
	}
}

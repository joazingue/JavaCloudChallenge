
package com.gbm.challenge.domains;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;

@Entity
public class Stock {

	@Id
	@GeneratedValue
	private Long idStock;
	@OneToOne
	private InvestmentAccount currentBalance;
	@ElementCollection
	private List<String> businessErrors = new ArrayList<>();
	
	public Stock() {
	}
	public InvestmentAccount getCurrentBalance() {
		return currentBalance;
	}
	public void setCurrentBalance(InvestmentAccount currentBalance) {
		this.currentBalance = currentBalance;
	}
	public List<String> addBusinessError(String be){
		businessErrors.add(be);
		return businessErrors;
	}
	public List<String> getBusinessErrors() {
		return businessErrors;
	}
}

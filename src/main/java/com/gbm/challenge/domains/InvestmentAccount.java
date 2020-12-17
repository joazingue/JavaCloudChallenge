
package com.gbm.challenge.domains;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class InvestmentAccount {
	
	@Id
	@GeneratedValue
	private Long idInvestmentAccount;
	private Double cash;
	@OneToMany(mappedBy = "issuer", fetch = FetchType.LAZY, orphanRemoval = true)
	private List<Issuer> issuers = new ArrayList<>();
	
	public InvestmentAccount() {
	}
	public Long getIdInvestmentAccount() {
		return idInvestmentAccount;
	}
	public Double getCash() {
		return cash;
	}
	public void setCash(Double cash) {
		this.cash = cash;
	}
	public List<Issuer> getIssuers() {
		return issuers;
	}
	public List<Issuer> addIssuer(Issuer issu){
		issuers.add(issu);
		return issuers;
	}
	public List<Issuer> removeIssuer(Issuer issu){
		issuers.remove(issu.getIdIssuer());
		return issuers;
	}
}

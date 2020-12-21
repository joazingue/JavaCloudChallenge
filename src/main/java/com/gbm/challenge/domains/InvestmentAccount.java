
package com.gbm.challenge.domains;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class InvestmentAccount {
	/*
	 * Individual accounts. Save cash and issuers (Portfolio)
	 */
	@Id
	@GeneratedValue
	private Long idInvestmentAccount;
	private Double cash;
	@OneToMany(fetch = FetchType.LAZY, orphanRemoval = true)
	private List<Issuer> issuers = new ArrayList<>();
	@OneToMany(fetch = FetchType.LAZY, orphanRemoval = true)
	@JsonIgnore
	private List<GBMOrder> orders = new ArrayList<GBMOrder>();
	
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
	public void removeAllIssuers() {
		issuers = new ArrayList<Issuer>();
	}
	public void removeIssuerByIndex(int index) {
		issuers.remove(index);
	}
	public void removeIssuer(Issuer issuer) {
		issuers.remove(issuer);
	}
	public void addOrder(GBMOrder order) {
		orders.add(order);
	}
	/**
	 * @return the orders
	 */
	public List<GBMOrder> getOrders() {
		return orders;
	}
	@Override
	public String toString() {
		return "InvestmentAccount [idInvestmentAccount=" + idInvestmentAccount + ", cash=" + cash + ", issuers="
				+ issuers + "]";
	}
}

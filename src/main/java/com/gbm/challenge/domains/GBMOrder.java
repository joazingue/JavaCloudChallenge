
package com.gbm.challenge.domains;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import com.gbm.challenge.services.rules.Operation;

@Entity
public class GBMOrder {

	@Id
	@GeneratedValue
	private Long idOrder;
	private Long timestamp;
	private Operation operation;
	private String IssuerName;
	private Long TotalShares;
	private Double SharePrice;
	
	public GBMOrder() {
	}
	public Long getIdOrder() {
		return idOrder;
	}
	public Long getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(Long timestamp) {
		this.timestamp = timestamp;
	}
	public Operation getOperation() {
		return operation;
	}
	public void setOperation(Operation oper) {
		operation = oper;
	}
	public String getIssuerName() {
		return IssuerName;
	}
	public void setIssuerName(String issuerName) {
		IssuerName = issuerName;
	}
	public Long getTotalShares() {
		return TotalShares;
	}
	public void setTotalShares(Long totalShares) {
		TotalShares = totalShares;
	}
	public Double getSharePrice() {
		return SharePrice;
	}
	public void setSharePrice(Double sharePrice) {
		SharePrice = sharePrice;
	}
	public Double getTotalSharesPrice() {
		return SharePrice * TotalShares;
	}
	@Override
	public String toString() {
		return "GBMOrder [idOrder=" + idOrder + ", timestamp=" + timestamp + ", Operation=" + operation
				+ ", IssuerName=" + IssuerName + ", TotalShares=" + TotalShares + ", SharePrice=" + SharePrice + "]";
	}
}


package com.gbm.challenge.domains;

import java.sql.Timestamp;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class GBMOrder {

	@Id
	@GeneratedValue
	private Long idOrder;
	private Timestamp timestamp;
	private String Operation;
	private String IssuerName;
	private Long TotalShares;
	private Double SharePrice;
	
	public GBMOrder() {
	}
	public Long getIdOrder() {
		return idOrder;
	}
	public Timestamp getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(Timestamp timestamp) {
		this.timestamp = timestamp;
	}
	public String getOperation() {
		return Operation;
	}
	public void setOperation(String operation) {
		Operation = operation;
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
	@Override
	public String toString() {
		return "GBMOrder [idOrder=" + idOrder + ", timestamp=" + timestamp + ", Operation=" + Operation
				+ ", IssuerName=" + IssuerName + ", TotalShares=" + TotalShares + ", SharePrice=" + SharePrice + "]";
	}
}

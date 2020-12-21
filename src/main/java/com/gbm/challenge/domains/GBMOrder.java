
package com.gbm.challenge.domains;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.gbm.challenge.services.rules.Operation;

@Entity
public class GBMOrder {

	@Id
	@GeneratedValue
	@JsonIgnore
	private Long idOrder;
	private Long timestamp;
	private Operation operation;
	private String issuer_name;
	private Long total_shares;
	private Double share_price;
	
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
	/**
	 * @return the issuer_name
	 */
	public String getIssuer_name() {
		return issuer_name;
	}
	/**
	 * @param issuer_name the issuer_name to set
	 */
	public void setIssuer_name(String issuer_name) {
		this.issuer_name = issuer_name;
	}
	/**
	 * @return the total_shares
	 */
	public Long getTotal_shares() {
		return total_shares;
	}
	/**
	 * @param total_shares the total_shares to set
	 */
	public void setTotal_shares(Long total_shares) {
		this.total_shares = total_shares;
	}
	/**
	 * @return the share_price
	 */
	public Double getShare_price() {
		return share_price;
	}
	/**
	 * @param share_price the share_price to set
	 */
	public void setShare_price(Double share_price) {
		this.share_price = share_price;
	}
	public Double getTotalSharesPrice() {
		return share_price * total_shares;
	}
	@Override
	public String toString() {
		return "GBMOrder [idOrder=" + idOrder + ", timestamp=" + timestamp + ", Operation=" + operation
				+ ", IssuerName=" + issuer_name + ", TotalShares=" + total_shares + ", SharePrice=" + share_price + "]";
	}
}

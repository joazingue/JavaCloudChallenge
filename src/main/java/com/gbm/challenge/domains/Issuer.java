
package com.gbm.challenge.domains;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Issuer {

	@Id
	@GeneratedValue
	private Long idIssuer;
	private String IssuerName;
	private Long TotalShares;
	private Double SharePrice;
	
	public Issuer() {
	}
	
	public Long getIdIssuer() {
		return idIssuer;
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
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Issuer other = (Issuer) obj;
		if (IssuerName == null) {
			if (other.IssuerName != null)
				return false;
		} else if (!IssuerName.equals(other.IssuerName))
			return false;
		return true;
	}
	
}

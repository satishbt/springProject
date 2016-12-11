package com.app.stock.model;


import java.util.Date;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;



@Entity
@Table(name = "portfoliostock")
public class PortfolioStock {
	
	@Id
	@GeneratedValue(generator="uuid")
	@GenericGenerator(name="uuid",  strategy = "uuid2")
	@Column(name = "id", unique = true, nullable = false)
	@Type(type="pg-uuid")
	private UUID id ;
	
	@Column(name = "foliodate")
	private Date foliodate;
	
	@Column(name = "fundname")
	private String fundName;
	
	@Column(name = "stockcode")
	private String stockCode;
	
	@Column(name = "quantity")
	private String quantity;
	
	@Column(name = "amount")
	private Double amount;
	
	@Column(name = "holdpercentage")
	private Double percentage;

	public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
		this.id = id;
	}

	public Date getFoliodate() {
		return foliodate;
	}

	public void setFoliodate(Date foliodate) {
		this.foliodate = foliodate;
	}

	public String getFundName() {
		return fundName;
	}

	public void setFundName(String fundName) {
		this.fundName = fundName;
	}

	public String getStockCode() {
		return stockCode;
	}

	public void setStockCode(String stockCode) {
		this.stockCode = stockCode;
	}

	public String getQuantity() {
		return quantity;
	}

	public void setQuantity(String quantity) {
		this.quantity = quantity;
	}

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	public Double getPercentage() {
		return percentage;
	}

	public void setPercentage(Double percentage) {
		this.percentage = percentage;
	}
	
}

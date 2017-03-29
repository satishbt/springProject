package com.app.stock.model;

import java.util.Date;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

@Entity
@Table(name = "portfoliostock", uniqueConstraints = @UniqueConstraint(columnNames = { "foliodate", "fundname",
	"stockcode", "amount", "quantities", "holdpercentage" }))

public class PortfolioStock {

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    @Column(name = "id", unique = true, nullable = false)
    @Type(type = "pg-uuid")

    private UUID id;

    @Column(name = "foliodate")
    private Date foliodate;

    @Column(name = "fundname")
    private String fundName;

    @Column(name = "stockcode")
    private String stockCode;

    @Column(name = "quantities")
    private Double quantity;

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

    public double getQuantity() {
	return quantity;
    }

    public void setQuantity(String quantity) {
	if (quantity.equals("-"))
	    this.quantity = new Double(0.0);
	else {
	    String qty = quantity.replace(",", "");
	    this.quantity = Double.parseDouble(qty);
	}

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

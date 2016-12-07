package com.app.stock.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "STOCKS")
public class Stock {

	public String getStockCode() {
		return stockCode;
	}

	public void setStockCode(String stockCode) {
		this.stockCode = stockCode;
	}

	public String getStockName() {
		return stockName;
	}

	public void setStockName(String stockName) {
		this.stockName = stockName;
	}
	
	public String getStockCategory() {
		return stockCategory;
	}

	public void setStockCategory(String stockCategory) {
		this.stockCategory = stockCategory;
	}
	
	@Id
	@Column(name = "stockcode")
	private String stockCode ;
	
	@NotNull
	@Column(name = "stockname")
	private String stockName ;
	
	@Column(name ="category")
	private String stockCategory ;
	
	@Override
	public String toString() {
		 
		return "Stock Name :"+this.stockName +" stockName : " +this.stockName;
	}
	
	@Override
	public boolean equals(Object obj) {
		if( obj == null)
			return false;
		if(obj.getClass() == getClass()){
			Stock s = (Stock) obj;
			if( s.stockCode == this.stockCode){
				return true;
			}
		} 
		return false;
	}
	
	@Override
	public int hashCode(){
		final int prime = 31;
		int result = 1;
		result = prime * result +  stockCode.hashCode();
		return result;
	}
}

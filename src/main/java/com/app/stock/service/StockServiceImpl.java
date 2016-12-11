package com.app.stock.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.app.stock.dao.StockDAO;
import com.app.stock.model.PortfolioStock;
import com.app.stock.model.Stock;
@Component
public class StockServiceImpl implements StockService {
	
	@Autowired
	private StockDAO stockDAO;
	
	public void setPhoneDAO(StockDAO stockDAO) {
		this.stockDAO = stockDAO;
	}
	
	@Override
	public void addStock(Stock stock) {
		this.stockDAO.addStock(stock);

	}

	@Override
	public List<Stock> stockList() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void addPortfolio(PortfolioStock portfolio) {
		this.stockDAO.addPortfolio(portfolio);
		
	}

}

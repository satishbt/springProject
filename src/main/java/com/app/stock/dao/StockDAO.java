package com.app.stock.dao;

import java.util.List;

import com.app.stock.model.PortfolioStock;
import com.app.stock.model.Stock;

public interface StockDAO {
	public void addStock(Stock stock);
	public List<Stock> listStocks();
	public void addPortfolio(PortfolioStock portfolio);
}

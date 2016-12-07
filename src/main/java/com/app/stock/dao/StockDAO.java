package com.app.stock.dao;

import java.util.List;

import com.app.stock.model.Stock;

public interface StockDAO {
	public void addStock(Stock stock);
	public List<Stock> listStocks();
}

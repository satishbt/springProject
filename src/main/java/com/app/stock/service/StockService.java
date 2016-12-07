package com.app.stock.service;

import java.util.List;

import com.app.stock.model.Stock;

public interface StockService {
	public void addStock(Stock stock);
	public List<Stock> stockList();

}

package com.app.stock.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.app.stock.dao.IStockDAO;
import com.app.stock.model.PortfolioStock;
import com.app.stock.model.ScrawlerHistory;
import com.app.stock.model.Stock;

@Component
public class StockServiceImpl implements StockService {

    @Autowired
    private IStockDAO stockDAO;

    public void setStockDAO(IStockDAO stockDAO) {
	this.stockDAO = stockDAO;
    }

    @Override
    public void addStock(Stock stock) {
	this.stockDAO.addStock(stock);

    }

    @Override
    public List<Stock> stockList() {
	// TODO Auto-generated method stub
	return this.stockDAO.listStocks();
    }

    @Override
    public void addPortfolio(PortfolioStock portfolio) {
	this.stockDAO.addPortfolio(portfolio);

    }

    @Override
    public void updateProcessedMF(ScrawlerHistory history) {

	this.stockDAO.updateProcessedMFData(history);

    }

    @Override
    public List<ScrawlerHistory> getScralwerHistory() {

	return this.stockDAO.getScralwerHistor();
    }

    @Override
    public boolean isFundAlreadyProcessed(String fundName, Date folioDate) {

	return stockDAO.portfolioExist(fundName, folioDate);
    }

}

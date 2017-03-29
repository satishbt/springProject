package com.app.stock.service;

import java.util.Date;
import java.util.List;

import com.app.stock.model.PortfolioStock;
import com.app.stock.model.ScrawlerHistory;
import com.app.stock.model.Stock;

public interface StockService {
    public void addStock(Stock stock);

    public List<Stock> stockList();

    public void addPortfolio(PortfolioStock portfolio);

    public void updateProcessedMF(ScrawlerHistory history);

    public List<ScrawlerHistory> getScralwerHistory();

    public boolean isFundAlreadyProcessed(String fundName, Date folioDate);

}

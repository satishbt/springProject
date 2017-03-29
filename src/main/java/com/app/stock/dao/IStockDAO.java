package com.app.stock.dao;

import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Service;

import com.app.stock.model.PortfolioStock;
import com.app.stock.model.ScrawlerHistory;
import com.app.stock.model.Stock;

@Service
public interface IStockDAO {
    public void addStock(Stock stock);

    public List<Stock> listStocks();

    public void addPortfolio(PortfolioStock portfolio);

    public void updateProcessedMFData(ScrawlerHistory history);

    public List<ScrawlerHistory> getScralwerHistor();

    public boolean portfolioExist(String fundName, Date folioDate);

}

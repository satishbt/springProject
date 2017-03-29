package com.app.stock.data;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.app.stock.model.Stock;

public interface StockRepository extends CrudRepository<Stock, String> {

    public List<Stock> findByStockName(String stockName);

}

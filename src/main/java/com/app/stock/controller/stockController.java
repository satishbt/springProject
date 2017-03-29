package com.app.stock.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.app.stock.data.StockRepository;
import com.app.stock.model.Stock;
import com.app.stock.service.StockService;

@RestController
public class StockController {
    private StockService stockService;

    @Autowired
    public StockRepository stockRepository;

    @Autowired(required = true)
    public void SetStockService(StockService stockService) {
	this.stockService = stockService;
    }

    @RequestMapping(value = "/stocks", method = { RequestMethod.GET }, produces = { MediaType.APPLICATION_JSON_VALUE })
    @ResponseBody
    public List<Stock> getAllStocks() {
	List<Stock> stocks = (List<Stock>) stockRepository.findAll();
	return stocks;

    }

    @RequestMapping(value = "/stocks/count", method = { RequestMethod.GET })
    @ResponseBody
    public long getStockCount() {
	return stockRepository.count();
    }

    @RequestMapping(value = "/stocks", method = RequestMethod.POST, consumes = {
	    MediaType.APPLICATION_JSON_VALUE }, produces = { MediaType.APPLICATION_JSON_VALUE })
    public void addStock(@RequestBody Stock s) {

	stockRepository.save(s);

    }

    @RequestMapping(value = "/stocks/{stockCode}", method = RequestMethod.DELETE, consumes = {
	    MediaType.APPLICATION_JSON_VALUE })
    public void deleteStock(@PathVariable String stockCode) {
	System.out.println(" value received " + stockCode);
	stockRepository.delete(stockCode);

    }
}

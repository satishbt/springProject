package com.app.stock.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import com.app.stock.model.Stock;
import com.app.stock.service.StockService;

@RestController
public class stockController {
	private StockService stockService;
	
	@Autowired(required = true)
	public void SetStockService(StockService stockService){
		this.stockService = stockService;
	}
 
	@RequestMapping(value="/stocks")
	public String listStocks(Model model){
		System.out.println("List");
		return "Working";
//		return this.stockService.stockList();
		
	}
	
//	@RequestMapping(value = "/stocks/add",method = RequestMethod.POST)
//	public void addStock(@ModelAttribute("Stock") Stock s){
//		
//		if(s.getId() == 0){
//			this.stockService.addStock(s);
//		}
//	}
}

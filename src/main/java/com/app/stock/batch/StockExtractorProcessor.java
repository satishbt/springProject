package com.app.stock.batch;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;

import com.app.stock.model.Fund;
import com.app.stock.model.PortfolioStock;
import com.app.stock.model.Stock;
import com.app.stock.service.StockService;

public class StockExtractorProcessor implements ItemProcessor<Fund, Fund> {

	@Autowired
	public StockService service;
	
	@Override
	public Fund process(Fund item) throws Exception {
		
		try {
			System.out.println("Link :" +item.getportFolioLink());
			Document doc = Jsoup.connect(item.getportFolioLink()).timeout(20000).get();
			Elements portFolioElem = doc.getElementsByAttributeValue("class", "tblporhd");
			if(portFolioElem.size() > 0){
				Element portFolioTable = portFolioElem.get(0);
				Elements portFolios = portFolioTable.getElementsByTag("tr");
				Elements folioDateElem = doc.getElementsByAttributeValue("class", "mainCont port_hold");
				String date = folioDateElem.get(0).getElementsByTag("h3").get(0).getElementsByTag("span").text();
				String foliodate = date.substring(1, date.length()-1);
				
				SimpleDateFormat formatter = new SimpleDateFormat("MMM dd, yyyy");
				Date convertedDate = null;
				try {
					convertedDate = formatter.parse(foliodate);
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				System.out.println(" Date :"+foliodate + " converted To "+convertedDate);
//				Date date = new Date
				Stock stockDetails ;
				PortfolioStock portfolio;
				
				for(Element portFolio:portFolios){
//					System.out.println("portFolio :"+portFolio);
					Elements portFolioDetails = portFolio.getElementsByTag("td");
					if(portFolioDetails.size() > 0 ){
						stockDetails = new Stock();
						portfolio = new PortfolioStock();
						Elements folioLink = portFolioDetails.get(0).select("a[href]");
						String moneyControlLink = folioLink.attr("abs:href");
						String[] stockCodes = moneyControlLink.split("/");
						String stockCode = stockCodes[stockCodes.length - 1];
						String stockName = folioLink.text();
						String stockCategory = portFolioDetails.get(1).text();
						String folioQuantity = portFolioDetails.get(2).text();
						String folioAmountIncrore = portFolioDetails.get(3).text();
						String folioPercentage = portFolioDetails.get(4).text();
						print("* PortFolio:[%s] <%s>  [%s] <%s> {%s} [%s]", stockCode,moneyControlLink, stockCategory,folioQuantity,folioAmountIncrore,folioPercentage);
						stockDetails.setStockName(stockName);
						stockDetails.setStockCode(stockCode);
						stockDetails.setStockCategory(stockCategory);
						stockDetails.setStockLink(moneyControlLink);
						if("-".equals(folioPercentage) )
							folioPercentage = "0.0";
						
						portfolio.setPercentage(Double.parseDouble(folioPercentage));
						portfolio.setFoliodate(convertedDate);					
						portfolio.setStockCode(stockCode);
						portfolio.setQuantity(folioQuantity);
						portfolio.setFundName(item.getName());
						
						if("-".equals(folioAmountIncrore) )
							folioAmountIncrore = "0.0";
						portfolio.setAmount(Double.parseDouble(folioAmountIncrore));
						
						service.addStock(stockDetails);
						service.addPortfolio(portfolio);
					}
					
			}
			
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	private static void print(String msg, Object... args) {
	        System.out.println(String.format(msg, args));
	}

}

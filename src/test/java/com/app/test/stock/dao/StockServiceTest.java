package com.app.test.stock.dao;

import static org.junit.Assert.assertEquals;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.app.stock.MoneycontrolApplication;
import com.app.stock.model.ScrawlerHistory;
import com.app.stock.model.Stock;
import com.app.stock.service.StockService;
import com.app.stock.service.StockServiceImpl;

@TestPropertySource(locations = "classpath:apptest.properties")

@RunWith(SpringJUnit4ClassRunner.class)
// @ContextConfiguration
// @EnableAutoConfiguration
@SpringBootTest(classes = MoneycontrolApplication.class)
// @EnableTransactionManagement
// @ContextConfiguration(classes = { StockImpl.class })
// @ComponentScan(basePackageClasses ={ ScrawlerHistory.class})
public class StockServiceTest {

    @Configuration
    static class ContextConfiguration {

	@Bean
	public StockService getStockService() {
	    return new StockServiceImpl();
	}
    }

    @Autowired
    private StockService stockService;

    @Test
    @Transactional
    @Rollback(false)
    public void test() {
	ScrawlerHistory history = new ScrawlerHistory();

	Date convertedDate = null;

	String portfolioStr = "Jan 31, 2017";
	SimpleDateFormat formatter = new SimpleDateFormat("MMM dd, yyyy");

	try {
	    convertedDate = formatter.parse(portfolioStr);
	} catch (ParseException e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	}
	history.setPublishDate(convertedDate);
	history.setType("PORTFOLIO");
	history.setCounter(1);
	stockService.updateProcessedMF(history);
	List<ScrawlerHistory> list = stockService.getScralwerHistory();
	System.out.println("Hello " + list.size());
	assertEquals(3, list.size());

    }

    @Test
    @Transactional
    @Rollback(false)
    public void addStock() {
	Stock stock = new Stock();
	stock.setStockName("INFY");
	stock.setStockCode("INF");
	stock.setStockLink("Http://infosys.com");
	stock.setStockCategory("IT");
	stockService.addStock(stock);
	List<Stock> stockList = stockService.stockList();
	assertEquals(1, stockList.size());

    }

}

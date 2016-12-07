package com.app.stock;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;

import java.io.IOException;
import java.util.List;

import javax.persistence.EntityManager;

import org.hibernate.SessionFactory;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.stereotype.Component;

import com.app.stock.model.Fund;
import com.app.stock.model.Stock;
import com.app.stock.service.FundService;
import com.app.stock.service.StockService;

@SpringBootApplication
public class MoneycontrolApplication {
	
	
	public static void main(String[] args) {
		ApplicationContext context = SpringApplication.run(MoneycontrolApplication.class, args);
		getSmallMFList(context);
		FundService fundservice = context.getBean(FundService.class);
		List<Fund> fundList = fundservice.getAllMFLinks();
		for(Fund fund : fundList){
			
				getPortfolioLink(fund);
				fundservice.addFund(fund);
				try {
					Thread.sleep(200);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			
			
		}
		
		
//		getPortfolioData(context);
		
		
		
	}

	private static void getPortfolioData(ApplicationContext context ) {
		String folioUrl = "http://www.moneycontrol.com/india/mutualfunds/mfinfo/portfolio_holdings/MBS1873";
		try {
			Document doc = Jsoup.connect(folioUrl).get();
			Elements portFolioElem = doc.getElementsByAttributeValue("class", "tblporhd");
			Element portFolioTable = portFolioElem.get(0);
			Elements portFolios = portFolioTable.getElementsByTag("tr");
			Stock stockDetails ;
			StockService service = context.getBean(StockService.class);
			for(Element portFolio:portFolios){
//				System.out.println("portFolio :"+portFolio);
				Elements portFolioDetails = portFolio.getElementsByTag("td");
				if(portFolioDetails.size() > 0 ){
					stockDetails = new Stock();
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
//					stockDetails.setFolioCategory(folioCategory);
//					stockDetails.setFolioQuantity(folioQuantity);
//					stockDetails.setFolioAmountIncrore(folioAmountIncrore));
//					stockDetails.setFolioPercentage(folioPercentage);
					service.addStock(stockDetails);
				}
				
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private static void getPortfolioLink(Fund fund) {
//		String navUrl = "http://www.moneycontrol.com/mutual-funds/nav/birla-sl-emer-leaders-sr-3-dp/MBS1873";
		try {
			Document doc =  Jsoup.connect(fund.getlink()).get() ;
			Elements hrefElement = doc.select("a[href]");
			for(Element e : hrefElement){
				String text = trim(e.text(), 35);
				if("Full Portfolio".equalsIgnoreCase(text)){
					print(" * a: <%s>  [%s]", e.attr("abs:href"), trim(e.text(), 35));
					fund.setportFolioLink(e.attr("abs:href"));
					 
					
				}
				
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private static void getSmallMFList(ApplicationContext context) {
		String url = context.getBean(MyClass.class).url;
		System.out.println("url"+url);
		try {
			Document doc =  Jsoup.connect(url).get() ;

			Elements tables = doc.getElementsByTag("table");
			Elements foundTables = doc.getElementsByAttributeValue("class", "gry_t thdata");

			Element table = foundTables.get(0);
			Elements trTags = table.getElementsByTag("tr");
			Fund fund ;
			FundService fundservice = context.getBean(FundService.class);
			for(Element trTag :trTags){
				Elements tdTags = trTag.getElementsByTag("td");
				Element firstTag = tdTags.first();
				
				if(null != firstTag ){
					fund = new Fund();
					print("#######################################");
					print("P- <%s>",firstTag.toString());
					Elements innerTrTags = firstTag.getElementsByTag("tr");
					print("[Inner tag %s]",innerTrTags.first());
					Element sponsoredTRTag = innerTrTags.first();
					
					if(null == sponsoredTRTag){
						Elements links = firstTag.select("a[href]");
						for (Element link : links) {
				        	if(link.attr("abs:href").contains("mutual-funds/nav")){
				        		String fundName = trim(link.text(), 35);
		 	        		
		 	        		
				 	       		print(" TR <%s>",trTag);
								print(" *a :< %s> ",firstTag.toString());
									print(" *a :< %s> ",trTag.child(0).select("a[href]").text());
									fund.setName(fundName);
									fund.setlink(link.attr("abs:href"));
									fund.setCrisilrank(trTag.child(1).text());
									fund.setAum(trTag.child(2).text());
									fund.setOneMonthReturn(trTag.child(3).text());
									fund.setThreeMonthReturn(trTag.child(4).text());
									fund.setSixMonthReturn(trTag.child(5).text());
									fund.setOneYearReturn(trTag.child(6).text());
									fund.setTwoYearReturn(trTag.child(7).text());
									fund.setThreeYearReturn(trTag.child(8).text());
									fund.setFiveYearReturn(trTag.child(9).text());
									
								
				        	}	
				        }
				        
				        print(" * a: <%s>  [%s]", fund.getAum(),fund.getName());
					}else{
						/*
						 * Child 0 - link and fundname
						 * Child 1 - crisil rank
						 * Child 2 - Aum
						 * Child 3 - one month
						 * Child 4 - three months
						 * Child 5 - six months
						 * Child 6 - 1 year
						 * Child 7 - 2 years
						 * Child 8 - 3 years
						 * Child 9 - 5 years
						 */
						 
						Elements innertdTags = sponsoredTRTag.getElementsByTag("td");
						fund.setName(sponsoredTRTag.child(0).select("span[href]").text());
						fund.setlink(sponsoredTRTag.child(0).select("span[href]").attr("span:href"));
						fund.setCrisilrank(sponsoredTRTag.child(1).text());
						fund.setAum(sponsoredTRTag.child(2).text());
						fund.setOneMonthReturn(sponsoredTRTag.child(3).text());
						fund.setThreeMonthReturn(sponsoredTRTag.child(4).text());
						fund.setSixMonthReturn(sponsoredTRTag.child(5).text());
						fund.setOneYearReturn(sponsoredTRTag.child(6).text());
						fund.setTwoYearReturn(sponsoredTRTag.child(7).text());
						fund.setThreeYearReturn(sponsoredTRTag.child(8).text());
						fund.setFiveYearReturn(sponsoredTRTag.child(9).text());
						
						print("[TD] [%s] ",sponsoredTRTag.child(0).select("span[href]").text());
						print("[TD] [%s] ",sponsoredTRTag.child(2).text());
			
					}
					if(fund.getName() != null)
						fundservice.addFund(fund);
		         	
		        	print("#######################################");
		        	
		        	
			        
				}
					
				
				
			}

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private static void print(String msg, Object... args) {
        System.out.println(String.format(msg, args));
    }

	 private static String trim(String s, int width) {
	        if (s.length() > width)
	            return s.substring(0, width-1) + ".";
	        else
	            return s;
	    }
	 
	@Component
	public class MyClass{
	
		@Value("${url}")
		public String url;
		
			
	}
}

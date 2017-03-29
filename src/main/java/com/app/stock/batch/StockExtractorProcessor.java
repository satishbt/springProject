package com.app.stock.batch;

import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Pattern;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;

import com.app.stock.model.Fund;
import com.app.stock.model.PortFolioPage;
import com.app.stock.model.PortfolioStock;
import com.app.stock.model.ScrawlerHistory;
import com.app.stock.model.Stock;
import com.app.stock.service.StockService;

public class StockExtractorProcessor implements ItemProcessor<Fund, Fund> {
    private static final Logger LOG = LoggerFactory.getLogger(StockExtractorProcessor.class);

    @Autowired
    public StockService service;

    @Override
    public Fund process(Fund item) throws Exception {
	String fundName = item.getName();
	String regex = ".*(dp|DP|Direct|direct)+";
	Pattern pattern = Pattern.compile(regex);
	
	// Don't process Direct fund porfolio as they are similar to Regular
	// fund portfolio
	// Done to improve the processing timings

	if (!pattern.matcher(fundName).find()) {
	    LOG.debug("Filter : FundName :" + fundName + " is about to Processed ");
	    try {
		LOG.debug("Link :" + item.getportFolioLink());
		Document doc = Jsoup.connect(item.getportFolioLink()).timeout(20000).get();
		Elements portFolioElem = doc.getElementsByAttributeValue("class", "tblporhd");
		Elements folioDateElem = doc.getElementsByAttributeValue("class", "mainCont port_hold");
		String date = folioDateElem.get(0).getElementsByTag("h3").get(0).getElementsByTag("span").text();
		String foliodate = date.substring(1, date.length() - 1);

		SimpleDateFormat formatter = new SimpleDateFormat("MMM dd, yyyy");
		Date convertedDate = null;
		try {
		    convertedDate = formatter.parse(foliodate);
		} catch (ParseException e) {

		    e.printStackTrace();
		}
		LOG.debug(" Date :" + foliodate + " converted To " + convertedDate);

		if (portFolioElem.size() > 0 && !service.isFundAlreadyProcessed(item.getName(), convertedDate)) {
		    Element portFolioTable = portFolioElem.get(0);
		    Elements portFolios = portFolioTable.getElementsByTag("tr");

		    ScrawlerHistory portfolioHistory = new ScrawlerHistory();
		    portfolioHistory.setPublishDate(convertedDate);
		    portfolioHistory.setType("PORTFOLIO");
		    service.updateProcessedMF(portfolioHistory);
		    // Store the Document for the given fundname and date
		    PortFolioPage pp = new PortFolioPage();
		    pp.setFundName(item.getName());
		    pp.setPortFolioDate(convertedDate);
		    pp.setHtmlPage(doc.outerHtml());

		    Elements crisilRank = doc.getElementsByAttributeValue("class", "toplft_cl2");
		    LOG.debug("crisilRank :" + crisilRank);
		    Elements rank = crisilRank.select("a[href]");
		    LOG.debug("rank :" + rank.html());

		    String rankData = rank.html();
		    java.util.regex.Matcher m = Pattern.compile("starO").matcher(rankData);
		    int numberrank = 0;
		    while (m.find()) {
			numberrank++;
		    }
		    LOG.debug(" Number of Rank " + numberrank);
		    // Date date = new Date
		    Stock stockDetails;
		    PortfolioStock portfolio;

		    for (Element portFolio : portFolios) {
			// LOG.debug("portFolio :"+portFolio);
			Elements portFolioDetails = portFolio.getElementsByTag("td");
			if (portFolioDetails.size() > 0) {
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
			    print("* PortFolio:[%s] <%s>  [%s] <%s> {%s} [%s]", stockCode, moneyControlLink,
				    stockCategory, folioQuantity, folioAmountIncrore, folioPercentage);
			    stockDetails.setStockName(stockName);
			    stockDetails.setStockCode(stockCode);
			    stockDetails.setStockCategory(stockCategory);
			    stockDetails.setStockLink(moneyControlLink);
			    if ("-".equals(folioPercentage))
				folioPercentage = "0.0";

			    portfolio.setPercentage(Double.parseDouble(folioPercentage));
			    portfolio.setFoliodate(convertedDate);
			    portfolio.setStockCode(stockCode);
			    portfolio.setQuantity(folioQuantity);
			    portfolio.setFundName(item.getName());

			    if ("-".equals(folioAmountIncrore))
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

	} else {
	    LOG.debug("Filter : FundName :" + fundName + " is  NOT  Processed ");
	}

	return null;
    }

    private static void print(String msg, Object... args) {
	LOG.debug(String.format(msg, args));
    }

}

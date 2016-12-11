
package com.app.stock.batch;

import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.batch.item.ItemProcessor;

import com.app.stock.model.Fund;

public class FundItemProcessor implements ItemProcessor<Fund, Fund> {

	@Override
	public Fund process(Fund fund) throws Exception {
		System.out.println("Processor"+fund.getName());
		try {
			Document doc =  Jsoup.connect(fund.getlink()).timeout(20000).get() ;
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
		return fund;
	}
	 private static String trim(String s, int width) {
	        if (s.length() > width)
	            return s.substring(0, width-1) + ".";
	        else
	            return s;
	    }
	 private static void print(String msg, Object... args) {
	        System.out.println(String.format(msg, args));
	    }
}

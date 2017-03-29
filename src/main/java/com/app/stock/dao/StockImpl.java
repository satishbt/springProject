package com.app.stock.dao;

import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManagerFactory;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.app.stock.model.PortfolioStock;
import com.app.stock.model.ScrawlerHistory;
import com.app.stock.model.Stock;

@Repository
public class StockImpl implements IStockDAO {
    private static final Logger LOG = LoggerFactory.getLogger(StockImpl.class);

    @Autowired
    public EntityManagerFactory em;

    private SessionFactory sessionFactory;

    @PostConstruct
    public void init() {
	sessionFactory = em.unwrap(SessionFactory.class);
    }

    @Override
    @Transactional
    public void addStock(Stock stock) {
	System.out.println("BEFORE:");
	Session session = this.sessionFactory.getCurrentSession();
	System.out.println("session:" + session);
	System.out.println(stock.getStockCode());
	session.merge(stock);
	// List<Stock> s = listStocks();
	// System.out.println("WONDERFUL :"+s.size());

    }

    @Override
    @Transactional
    public List<Stock> listStocks() {
	Session session = this.sessionFactory.getCurrentSession();
	Query query = session.createQuery("FROM Stock");
	List<Stock> stockList = query.list();
	for (Stock s : stockList) {
	    System.out.println("Phone inf :" + s.toString());
	}
	return stockList;
    }

    @Override
    @Transactional
    public void addPortfolio(PortfolioStock portfolio) {
	Session session = this.sessionFactory.getCurrentSession();
	session.save(portfolio);
	// session.merge(portfolio);

    }

    @Override
    @Transactional
    public void updateProcessedMFData(ScrawlerHistory history) {
	Session session = this.sessionFactory.getCurrentSession();
	String query = "WITH upsert AS (UPDATE scrawlerhistory SET counter=counter+1 WHERE scrawled_date= :publishdate AND pagetype='"
		+ history.getType()
		+ "' RETURNING *) INSERT INTO scrawlerhistory (scrawled_date,pagetype, counter) SELECT :publishdate,'"
		+ history.getType() + "', 1 WHERE NOT EXISTS (SELECT * FROM upsert)";
	session.createSQLQuery(query).setParameter("publishdate", history.getPublishDate()).executeUpdate();

	// executionQuery.executeUpdate();

    }

    @Override
    @Transactional
    public List<ScrawlerHistory> getScralwerHistor() {

	Session session = this.sessionFactory.openSession();
	Query query = session.createQuery("FROM ScrawlerHistory");
	List<ScrawlerHistory> stockList = query.list();
	return stockList;
    }

    @Override
    @Transactional
    public boolean portfolioExist(String fundName, Date folioDate) {
	Session session = this.sessionFactory.getCurrentSession();
	Criteria crit = session.createCriteria(PortfolioStock.class).add(Restrictions.eq("foliodate", folioDate))
		.add(Restrictions.eq("fundName", fundName));
	List<PortfolioStock> stockList = crit.list();
	int count = stockList.size();
	// String query = " select * from portfoliostock where foliodate =
	// :folioDate AND fundname = '"+fundName+"'";
	//
	// Query sqlQquery =
	// session.createSQLQuery(query).setParameter("folioDate", folioDate);
	System.out.println(" PROCESSED :" + count);
	return (count > 0) ? true : false;
    }

}

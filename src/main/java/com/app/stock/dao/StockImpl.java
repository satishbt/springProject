package com.app.stock.dao;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.app.stock.model.Stock;
@Repository
public class StockImpl implements StockDAO {
	
	@Autowired
	private EntityManagerFactory em;
	
	private SessionFactory sessionFactory;
	
	@PostConstruct
	public void init(){
		System.out.println("EM :"+em);
		sessionFactory = em.unwrap(SessionFactory.class);
	}
	
	@Override
	@Transactional
	public void addStock(Stock stock) {
		System.out.println("BEFORE:");
		 Session session = this.sessionFactory.getCurrentSession();
		 System.out.println("session:"+session);
		 System.out.println(stock.getStockCode());
//		 session.persist(stock);
		 session.saveOrUpdate(stock);

	}

	@Override
	@Transactional
	public List<Stock> listStocks() {
		 Session session = this.sessionFactory.getCurrentSession();
		 Query query = session.createQuery("FROM STOCK");
		 List<Stock> stockList =  query.list();
		 for(Stock s : stockList){
			 System.out.println("Phone inf :"+ s.toString());
		 }
		 return stockList;
	}

}

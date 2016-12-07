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

import com.app.stock.model.Fund;

@Repository
public class FundImpl implements FundDAO{
	
	@Autowired
	private EntityManagerFactory em ;
	
	private SessionFactory sessionFactory;
	
	@PostConstruct
	public void init(){
		sessionFactory = em.unwrap(SessionFactory.class);
	}
	
	@Override
	@Transactional
	public void addFund(Fund fund) {
		Session session = sessionFactory.getCurrentSession();
		session.saveOrUpdate(fund);
				
	}

	@Override
	@Transactional
	public List<Fund> getAllLinks() {
		Session session = sessionFactory.getCurrentSession();
		return (List<Fund>) session.createQuery("from Fund").list();
		
	}

}

package com.app.stock.batch;

import javax.sql.DataSource;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManagerFactory;

import org.hibernate.SessionFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParametersIncrementer;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;
import org.springframework.batch.item.database.HibernateCursorItemReader;
import org.springframework.batch.item.database.HibernateItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.app.stock.model.Fund;

//@Configuration
//@EnableBatchProcessing
public class BatchProcessConfiguration {

	@Autowired
	public StepBuilderFactory stepBuilderFactory;
	
	@Autowired
	public JobBuilderFactory jobBuilderFactory;
	
	@Autowired
	public DataSource datasource;
	
	@Autowired
	private EntityManagerFactory em ;
	
	private SessionFactory sessionFactory;
	
	@PostConstruct
	public void init(){
		sessionFactory = em.unwrap(SessionFactory.class);
	}
	
	
	@Bean
	public Job extractPortfolioLink(JobCompletionNotificationListener listener){
		return jobBuilderFactory.get("extractPortfolioLink")
					.incrementer(new RunIdIncrementer())
					.listener(listener)
					.flow(stepOne())
					.end()
					.build();
		
	}

	@Bean
	public HibernateCursorItemReader<Fund> reader() {
		HibernateCursorItemReader<Fund> reader = new HibernateCursorItemReader<>();
		System.out.println("Reader");
		reader.setQueryString("from Fund");
		//For simplicity sake, assume sessionFactory already obtained.
		reader.setSessionFactory(sessionFactory);
		reader.setUseStatelessSession(true);
//		int counter = 0;
//		ExecutionContext executionContext = new ExecutionContext();
//		reader.open(executionContext);
//		Object customerCredit = new Object();
//		while(customerCredit != null){
//		    try {
//				customerCredit = reader.read();
//			} catch (Exception e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//		    System.out.println("Counter :"+counter);
//		    counter++;
//		}
//		reader.close();
		return reader;
		
	}
	
	@Bean
	public FundItemProcessor processor(){
		System.out.println("Processor");
		return new FundItemProcessor();
	}
	
	@Bean
	public HibernateItemWriter<Fund> writer(){
		HibernateItemWriter<Fund> writer = new HibernateItemWriter<>();
		writer.setSessionFactory(sessionFactory);
		return writer;
	}
	
	@Bean
	public Step stepOne(){
		return stepBuilderFactory.get("stepOne")
					.<Fund,Fund>chunk(1)
					.reader(reader())
					.processor(processor())
					.writer(writer())
					.build();
	}
}

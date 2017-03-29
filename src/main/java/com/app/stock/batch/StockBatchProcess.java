package com.app.stock.batch;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.database.HibernateCursorItemReader;
import org.springframework.batch.item.database.HibernateItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.app.stock.model.Fund;

@Configuration
@EnableBatchProcessing
public class StockBatchProcess {
    private static final Logger LOG = LoggerFactory.getLogger(StockBatchProcess.class);

    @Autowired
    public StepBuilderFactory stepBuilderFactory;

    @Autowired
    public JobBuilderFactory jobBuilderFactory;

    @Autowired
    public DataSource datasource;

    @Autowired
    private EntityManagerFactory em;

    private SessionFactory sessionFactory;

    @PostConstruct
    public void init() {
	sessionFactory = em.unwrap(SessionFactory.class);
    }

    @Bean
    public Job extractStockDetails(JobCompletionNotificationListener listener) {
	return jobBuilderFactory.get("extractPortfolioLink").incrementer(new RunIdIncrementer()).listener(listener)
		.flow(stepOne()).end().build();

    }

    @Bean
    public HibernateCursorItemReader<Fund> fundreader() {
	HibernateCursorItemReader<Fund> reader = new HibernateCursorItemReader<>();
	LOG.debug("Reader");
	reader.setQueryString("from Fund");
	// For simplicity sake, assume sessionFactory already obtained.
	reader.setSessionFactory(sessionFactory);
	reader.setUseStatelessSession(true);
	return reader;

    }

    @Bean
    public StockExtractorProcessor stockDetailprocessor() {
	LOG.debug("Processor");
	return new StockExtractorProcessor();
    }

    @Bean
    public HibernateItemWriter<Fund> stockDetailwriter() {
	HibernateItemWriter<Fund> writer = new HibernateItemWriter<>();
	writer.setSessionFactory(sessionFactory);
	return writer;
    }

    @Bean
    public Step stepOne() {
	return stepBuilderFactory.get("stepOne").<Fund, Fund>chunk(1).reader(fundreader())
		.processor(stockDetailprocessor()).writer(stockDetailwriter()).build();
    }
}

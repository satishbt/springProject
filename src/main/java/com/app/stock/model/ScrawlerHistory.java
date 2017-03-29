package com.app.stock.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "scrawlerhistory")
public class ScrawlerHistory implements Serializable {

    /**
    * 
    */
    private static final long serialVersionUID = 6769732751273886448L;

    @Id
    @Column(name = "scrawled_date")
    private Date publishdate;

    public Date getPublishDate() {
	return publishdate;
    }

    public void setPublishDate(Date publishdate) {
	this.publishdate = publishdate;
    }

    @Column(name = "pagetype")
    private String type;

    public String getType() {
	return type;
    }

    public void setType(String type) {
	this.type = type;
    }

    @Column(name = "counter")
    private int counter;

    public int getCounter() {
	return counter;
    }

    public void setCounter(int counter) {
	this.counter = counter;
    }
}

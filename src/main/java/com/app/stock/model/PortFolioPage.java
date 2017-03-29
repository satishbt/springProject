package com.app.stock.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;

@Entity
@Table(name = "Pages")
public class PortFolioPage implements Serializable {
    /**
    * 
    */
    private static final long serialVersionUID = 7938665358159678207L;

    @Id
    @Column(name = "pagedate")
    private Date portFolioDate;
    @Id
    @Column(name = "fundname")
    private String fundName;
    @Lob
    private String htmlPage;

    public Date getPortFolioDate() {
	return portFolioDate;
    }

    public void setPortFolioDate(Date portFolioDate) {
	this.portFolioDate = portFolioDate;
    }

    public String getFundName() {
	return fundName;
    }

    public void setFundName(String fundName) {
	this.fundName = fundName;
    }

    public String getHtmlPage() {
	return htmlPage;
    }

    public void setHtmlPage(String htmlPage) {
	this.htmlPage = htmlPage;
    }
}

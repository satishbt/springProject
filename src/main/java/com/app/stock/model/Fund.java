package com.app.stock.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "funds")
public class Fund {

    @Id
    @Column(name = "name")
    private String name;

    public String getName() {
	return name;
    }

    public void setName(String name) {
	this.name = name;
    }

    public String getlink() {
	return link;
    }

    public void setlink(String link) {
	this.link = link;
    }

    public String getCategory() {
	return category;
    }

    public void setCategory(String category) {
	this.category = category;
    }

    public String getCrisilrank() {
	return crisilrank;
    }

    public void setCrisilrank(String crisilrank) {
	this.crisilrank = crisilrank;
    }

    public String getAum() {
	return aum;
    }

    public void setAum(String aum) {
	this.aum = aum;
    }

    public String getOneMonthReturn() {
	return oneMonthReturn;
    }

    public void setOneMonthReturn(String oneMonthReturn) {
	this.oneMonthReturn = oneMonthReturn;
    }

    public String getThreeMonthReturn() {
	return threeMonthReturn;
    }

    public void setThreeMonthReturn(String threeMonthReturn) {
	this.threeMonthReturn = threeMonthReturn;
    }

    public String getSixMonthReturn() {
	return sixMonthReturn;
    }

    public void setSixMonthReturn(String sixMonthReturn) {
	this.sixMonthReturn = sixMonthReturn;
    }

    public String getOneYearReturn() {
	return oneYearReturn;
    }

    public void setOneYearReturn(String oneYearReturn) {
	this.oneYearReturn = oneYearReturn;
    }

    public String getTwoYearReturn() {
	return twoYearReturn;
    }

    public void setTwoYearReturn(String twoYearReturn) {
	this.twoYearReturn = twoYearReturn;
    }

    public String getThreeYearReturn() {
	return threeYearReturn;
    }

    public void setThreeYearReturn(String threeYearReturn) {
	this.threeYearReturn = threeYearReturn;
    }

    public String getFiveYearReturn() {
	return fiveYearReturn;
    }

    public void setFiveYearReturn(String fiveYearReturn) {
	this.fiveYearReturn = fiveYearReturn;
    }

    public void setportFolioLink(String portfolioLink) {
	this.portFolioLink = portfolioLink;
    }

    public String getportFolioLink() {
	return this.portFolioLink;
    }

    @Column(name = "link")
    private String link;

    @Column(name = "category")
    private String category;

    @Column(name = "crisilrank")
    private String crisilrank;

    @Column(name = "aum")
    private String aum;

    @Column(name = "oneMonthReturn")
    private String oneMonthReturn;

    @Column(name = "threeMonthReturn")
    private String threeMonthReturn;

    @Column(name = "sixMonthReturn")
    private String sixMonthReturn;

    @Column(name = "oneYearReturn")
    private String oneYearReturn;

    @Column(name = "twoYearReturn")
    private String twoYearReturn;

    @Column(name = "threeYearReturn")
    private String threeYearReturn;

    @Column(name = "fiveYearReturn")
    private String fiveYearReturn;

    @Column(name = "portfolioLink")
    private String portFolioLink;
}

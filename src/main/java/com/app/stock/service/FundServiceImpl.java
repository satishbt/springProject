package com.app.stock.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.app.stock.dao.FundDAO;
import com.app.stock.model.Fund;

@Component
public class FundServiceImpl implements FundService {

    @Autowired
    private FundDAO fundDAO;

    public void setFundDAO(FundDAO fundDAO) {
	this.fundDAO = fundDAO;
    }

    @Override
    public void addFund(Fund fund) {
	fundDAO.addFund(fund);
    }

    @Override
    public List<Fund> getAllMFLinks() {
	return fundDAO.getAllLinks();
    }

}

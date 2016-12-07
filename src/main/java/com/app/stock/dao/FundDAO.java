package com.app.stock.dao;

import java.util.List;

import com.app.stock.model.Fund;

public interface FundDAO {
	public void addFund(Fund fund);
	public List<Fund> getAllLinks();
}

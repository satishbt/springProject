package com.app.stock.service;

import java.util.List;

import com.app.stock.model.Fund;

public interface FundService {
    public void addFund(Fund fund);

    public List<Fund> getAllMFLinks();
}

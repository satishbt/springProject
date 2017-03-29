package com.app.stock;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import com.app.stock.controller.StockController;

@TestPropertySource(locations = "classpath:apptest.properties")
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = MoneycontrolApplication.class)

public class StockControllerTest {

    @Autowired
    private StockController stockController;

    private MockMvc mockMvc;

    @Before
    public void setUp() throws Exception {
	mockMvc = MockMvcBuilders.standaloneSetup(this.stockController).build();
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void stockCount() throws Exception {
	mockMvc.perform(get("/stocks/count")).andExpect(status().isOk()).andExpect(content().string("1"));
    }

    @Test
    public void addStock() throws Exception {
	MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
	map.add("stockName", "Automotive Axle");
	map.add("stockCode", "AA06");
	map.add("stockCategory", "Automotive");
	map.add("stockLink", "http://www.moneycontrol.com/india/stockpricequote/autoancillaries/automotiveaxle/AA06");
	mockMvc.perform(post("/stocks").contentType(MediaType.APPLICATION_JSON_VALUE).params(map))
		.andExpect(status().isOk());

    }

}

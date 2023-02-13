package com.sof8.service;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sof8.dto.DetailOrder;
import com.sof8.frame.Sof8Service;
import com.sof8.mapper.DetailOrderMapper;

@Service
public class DetailOrderService implements Sof8Service<Integer, DetailOrder>{

	@Autowired
	DetailOrderMapper mapper;

	@Override
	public void register(DetailOrder v) throws Exception {
		mapper.insert(v);
	}

	@Override
	public void remove(Integer k) throws Exception {
		mapper.delete(k);
	}

	@Override
	public void modify(DetailOrder v) throws Exception {
		mapper.update(v);
	}

	@Override
	public DetailOrder get(Integer k) throws Exception {
		return mapper.select(k);
	}

	@Override
	public List<DetailOrder> get() throws Exception {
		return mapper.selectall();
	}
	
	public int getTodayProducts() throws Exception {
		return mapper.selectTodayProducts();
	}
	
	public int getWeekProducts() throws Exception {
		return mapper.selectWeekProducts();
	}
	
	public int getMonthProducts() throws Exception {
		return mapper.selectMonthProducts();
	}
	
	public int getYearProducts() throws Exception {
		return mapper.selectYearProducts();
	}

	public HashMap<String, Object> getTodayCategoryProducts() throws Exception {
		return mapper.selectTodayCategoryProducts();
	}
	
	public HashMap<String, Object> getWeekCategoryProducts() throws Exception {
		return mapper.selectWeekCategoryProducts();
	}
	
	public HashMap<String, Object> getMonthCategoryProducts() throws Exception {
		return mapper.selectMonthCategoryProducts();
	}
	
	public HashMap<String, Object> getYearCategoryProducts() throws Exception {
		return mapper.selectYearCategoryProducts();
	}
}

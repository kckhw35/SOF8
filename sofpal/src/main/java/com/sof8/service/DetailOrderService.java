package com.sof8.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sof8.dto.DetailOrder;
import com.sof8.dto.Order;
import com.sof8.dto.OrderForm;
import com.sof8.frame.Sof8Service;
import com.sof8.mapper.DetailOrderMapper;
import com.sof8.mapper.OrderMapper;

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

}

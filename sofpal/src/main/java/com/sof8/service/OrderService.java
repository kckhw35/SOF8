package com.sof8.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sof8.dto.Order;
import com.sof8.frame.Sof8Service;
import com.sof8.mapper.OrderMapper;

@Service
public class OrderService implements Sof8Service<Integer, Order>{

	@Autowired
	OrderMapper mapper;

	@Override
	public void register(Order v) throws Exception {
		mapper.insert(v);
	}

	@Override
	public void remove(Integer k) throws Exception {
		mapper.delete(k);
	}

	@Override
	public void modify(Order v) throws Exception {
		mapper.update(v);
	}

	@Override
	public Order get(Integer k) throws Exception {
		return mapper.select(k);
	}

	@Override
	public List<Order> get() throws Exception {
		return mapper.selectall();
	}
}

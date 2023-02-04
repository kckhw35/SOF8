package com.sof8.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sof8.dto.Delivery;
import com.sof8.frame.Sof8Service;
import com.sof8.mapper.DeliveryMapper;

@Service
public class DeliveryService implements Sof8Service<Integer, Delivery>{

	@Autowired
	DeliveryMapper mapper;

	@Override
	public void register(Delivery v) throws Exception {
		mapper.insert(v);
	}

	@Override
	public void remove(Integer k) throws Exception {
		mapper.delete(k);
	}

	@Override
	public void modify(Delivery v) throws Exception {
		mapper.update(v);
	}

	@Override
	public Delivery get(Integer k) throws Exception {
		return mapper.select(k);
	}

	@Override
	public List<Delivery> get() throws Exception {
		return mapper.selectall();
	}
	
	public int deliverymancnt() throws Exception{
		return mapper.deliverymancnt();
	};
	
}

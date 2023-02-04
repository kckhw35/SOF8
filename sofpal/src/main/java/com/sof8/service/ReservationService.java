package com.sof8.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sof8.dto.OrderForm;
import com.sof8.dto.Reservation;
import com.sof8.frame.Sof8Service;
import com.sof8.mapper.ReservationMapper;

@Service
public class ReservationService implements Sof8Service<Integer, Reservation>{

	@Autowired
	ReservationMapper mapper;

	@Override
	public void register(Reservation v) throws Exception {
		mapper.insert(v);
	}

	@Override
	public void remove(Integer k) throws Exception {
		mapper.delete(k);
	}

	@Override
	public void modify(Reservation v) throws Exception {
		mapper.update(v);
	}

	@Override
	public Reservation get(Integer k) throws Exception {
		return mapper.select(k);
	}

	@Override
	public List<Reservation> get() throws Exception {
		return mapper.selectall();
	}
	
}

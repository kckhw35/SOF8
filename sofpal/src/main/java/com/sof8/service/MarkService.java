package com.sof8.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sof8.dto.Mark;
import com.sof8.frame.Sof8Service;
import com.sof8.mapper.MarkMapper;

@Service
public class MarkService implements Sof8Service<Integer, Mark>{

	@Autowired
	MarkMapper mapper;

	@Override
	public void register(Mark v) throws Exception {
		mapper.insert(v);
	}

	@Override
	public void remove(Integer k) throws Exception {
		mapper.delete(k);
	}

	@Override
	public void modify(Mark v) throws Exception {
		mapper.update(v);
	}

	@Override
	public Mark get(Integer k) throws Exception {
		return mapper.select(k);
	}

	@Override
	public List<Mark> get() throws Exception {
		return mapper.selectall();
	}
}

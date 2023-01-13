package com.sof8.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.sof8.dto.Test;
import com.sof8.frame.Sof8Service;
import com.sof8.mapper.TestMapper;

public class TestService implements Sof8Service<String, Test>{

	@Autowired
	TestMapper mapper;
	
	@Override
	public void register(Test v) throws Exception {
		mapper.insert(v);	
	}

	@Override
	public void remove(String k) throws Exception {
		mapper.delete(k);
	}

	@Override
	public void modify(Test v) throws Exception {
		mapper.update(v);
	}

	@Override
	public Test get(String k) throws Exception {
		return mapper.select(k);
	}

	@Override
	public List<Test> get() throws Exception {
		return mapper.selectall();
	}

}

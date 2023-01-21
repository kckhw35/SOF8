package com.sof8.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sof8.dto.Admin;
import com.sof8.frame.Sof8Service;
import com.sof8.mapper.AdminMapper;

@Service
public class AdminService implements Sof8Service<String, Admin>{

	@Autowired
	AdminMapper mapper;

	@Override
	public void register(Admin v) throws Exception {
		mapper.insert(v);
	}

	@Override
	public void remove(String k) throws Exception {
		mapper.delete(k);
	}

	@Override
	public void modify(Admin v) throws Exception {
		mapper.update(v);
	}

	@Override
	public Admin get(String k) throws Exception {
		return mapper.select(k);
	}

	@Override
	public List<Admin> get() throws Exception {
		return mapper.selectall();
	}

	public void modifyEnable(String k) throws Exception {
		mapper.updateEnable(k);
	}

}

package com.sof8.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sof8.dto.Category;
import com.sof8.frame.Sof8Service;
import com.sof8.mapper.CategoryMapper;

@Service
public class CategoryService implements Sof8Service<Integer, Category>{

	@Autowired
	CategoryMapper mapper;

	@Override
	public void register(Category v) throws Exception {
		mapper.insert(v);
	}

	@Override
	public void remove(Integer k) throws Exception {
		mapper.delete(k);
	}

	@Override
	public void modify(Category v) throws Exception {
		mapper.update(v);
	}

	@Override
	public Category get(Integer k) throws Exception {
		return mapper.select(k);
	}

	@Override
	public List<Category> get() throws Exception {
		return mapper.selectall();
	}
}

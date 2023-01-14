package com.sof8.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.sof8.dto.Product;
import com.sof8.frame.Sof8Service;
import com.sof8.mapper.ProductMapper;

public class ProductService implements Sof8Service<Integer, Product>{

	@Autowired
	ProductMapper mapper;

	@Override
	public void register(Product v) throws Exception {
		mapper.insert(v);
	}

	@Override
	public void remove(Integer k) throws Exception {
		mapper.delete(k);
	}

	@Override
	public void modify(Product v) throws Exception {
		mapper.update(v);
	}

	@Override
	public Product get(Integer k) throws Exception {
		return mapper.select(k);
	}

	@Override
	public List<Product> get() throws Exception {
		return mapper.selectall();
	}
}

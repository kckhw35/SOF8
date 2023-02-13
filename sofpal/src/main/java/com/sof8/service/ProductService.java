package com.sof8.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sof8.dto.Paging;
import com.sof8.dto.Product;
import com.sof8.frame.Sof8Service;
import com.sof8.mapper.ProductMapper;

@Service
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
	
	public int getmaincat(Integer k) throws Exception{
		return mapper.selectmaincat(k);
	}
	
	public int selectcategory(Product v) throws Exception{
		return mapper.selectcategory(v);
	}
	
	public int getcnt(Integer k) throws Exception {
		return mapper.selectcnt(k);
	}
	
	public int updatecnt(Integer k) throws Exception{
		return mapper.updatecnt(k);
	}
	
	public List<Product> selectold() throws Exception{
		return mapper.selectold();
	}
	
	public List<Product> selectnew() throws Exception{
		return mapper.selectnew();
	}
	
	public int selecttotal(Map<String, Object> map) throws Exception{
		return mapper.selecttotal(map);
	}
	
	public int selecttotaladmin(Map<String, Object> map) throws Exception{
		return mapper.selecttotaladmin(map);
	}
	
	public List<Product> selectlist(Paging p) throws Exception{
		return mapper.selectlist(p);
	}
	
	public List<Product> selectlistadmin(Paging p) throws Exception{
		return mapper.selectlistadmin(p);
	}
}

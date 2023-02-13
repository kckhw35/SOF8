package com.sof8.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sof8.dto.Cart;
import com.sof8.frame.Sof8Service;
import com.sof8.mapper.CartMapper;

@Service
public class CartService implements Sof8Service<Integer, Cart>{

	@Autowired
	CartMapper mapper;

	@Override
	public void register(Cart v) throws Exception {
		mapper.insert(v);
	}

	@Override
	public void remove(Integer k) throws Exception {
		mapper.delete(k);
	}

	@Override
	public void modify(Cart v) throws Exception {
		mapper.update(v);
	}

	@Override
	public Cart get(Integer k) throws Exception {
		return mapper.select(k);
	}

	@Override
	public List<Cart> get() throws Exception {
		return mapper.selectall();
	}
	
	public List<Cart> selectusercart(String user_id) throws Exception{
		return mapper.selectusercart(user_id);
	}
	
	public int selectcartpid(int c_id) throws Exception{
		return mapper.selectcartpid(c_id);
	};
	
	public Cart selectedproduct(int c_id) throws Exception{
		return mapper.selectedproduct(c_id);
	}
}

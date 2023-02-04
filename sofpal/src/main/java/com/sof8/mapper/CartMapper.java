package com.sof8.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import com.sof8.dto.Cart;
import com.sof8.frame.Sof8Mapper;

@Repository
@Mapper
public interface CartMapper extends Sof8Mapper<Integer, Cart>{
	public List<Cart> selectusercart(String user_id) throws Exception;
	public int selectcartpid(int c_id) throws Exception;
	public Cart selectedproduct(int c_id) throws Exception;
}

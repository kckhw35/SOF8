package com.sof8.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import com.sof8.dto.Order;
import com.sof8.dto.Product;
import com.sof8.frame.Sof8Mapper;

@Repository
@Mapper
public interface OrderMapper extends Sof8Mapper<Integer, Order>{
	public int selectoid() throws Exception;
	public int selectpid(int k) throws Exception;
	public int selectcnt(int k) throws Exception;
	public List<Product> selectorderd(int k) throws Exception;
}

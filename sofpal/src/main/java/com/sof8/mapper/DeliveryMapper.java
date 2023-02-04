package com.sof8.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import com.sof8.dto.Delivery;
import com.sof8.frame.Sof8Mapper;

@Repository
@Mapper
public interface DeliveryMapper extends Sof8Mapper<Integer, Delivery>{
	public int deliverymancnt() throws Exception;
}

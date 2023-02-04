package com.sof8.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import com.sof8.dto.OrderForm;

@Repository
@Mapper
public interface OrderFormMapper{
	public void insertorder(OrderForm of) throws Exception;
	public void insertdetailorder(OrderForm of) throws Exception;
	public void insertreservation(OrderForm of) throws Exception;
}

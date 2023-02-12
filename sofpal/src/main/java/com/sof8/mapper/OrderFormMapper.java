package com.sof8.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import com.sof8.dto.DetailOrder;
import com.sof8.dto.OrderForm;

@Repository
@Mapper
public interface OrderFormMapper{
	public void insertorder(OrderForm v) throws Exception;
	public void insertdetailorder(DetailOrder v) throws Exception;
	public void insertreservation(OrderForm v) throws Exception;
	public void insertschedule(OrderForm v) throws Exception;
	public String getpname(int v) throws Exception;
	public String getpimg(int v) throws Exception;
}

package com.sof8.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import com.sof8.dto.Coupon;
import com.sof8.dto.Paging;
import com.sof8.frame.Sof8Mapper;

@Repository
@Mapper
public interface CouponMapper extends Sof8Mapper<Integer, Coupon>{

	public void insertCoupon(Map<String, Object> map) throws Exception;

	public int selectTotal(Map<String, Object> map) throws Exception;

	public List<Coupon> selectList(Paging paging) throws Exception;
	
	public Coupon selectCoupon(Map<String, Object> map) throws Exception;
	
	public List<Coupon> couponlist(String k) throws Exception;
	
	public int coupondiscount(Integer k) throws Exception;
	
	public void usecoupon(Integer k) throws Exception;
	
	public int getcoid(Integer k) throws Exception;
}

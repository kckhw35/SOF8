package com.sof8.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sof8.dto.Coupon;
import com.sof8.dto.Paging;
import com.sof8.frame.Sof8Service;
import com.sof8.mapper.CouponMapper;


@Service
public class CouponService implements Sof8Service<Integer, Coupon>{
    @Autowired
    CouponMapper mapper ;
     
    
    
	@Override
	public void register(Coupon v) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void remove(Integer k) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void modify(Coupon v) throws Exception {
		// TODO Auto-generated method stub
	}

	@Override
	public Coupon get(Integer k) throws Exception {
		// TODO Auto-generated method stub
		return mapper.select(k);
	}

	@Override
	public List<Coupon> get() throws Exception {
		// TODO Auto-generated method stub
		return mapper.selectall();
	}

	public void registerCoupon(Map<String,Object> map) throws Exception{
	     mapper.insertCoupon(map);
	}

	public int getTotal(Map<String, Object> map) throws Exception {

		return mapper.selectTotal(map);
	}

	public List<Coupon> getList(Paging paging) throws Exception {
		return mapper.selectList(paging); 
	}

	public Coupon getCoupon(Map<String, Object> map) throws Exception {
		// TODO Auto-generated method stub
		return mapper.selectCoupon(map);
	}
	
	public List<Coupon> couponlist(String k) throws Exception{
		return mapper.couponlist(k);
	}

	public int coupondiscount(int k) throws Exception{
		return mapper.coupondiscount(k);
	}

	public void usecoupon(int k) throws Exception{
		mapper.usecoupon(k);
	}

	public int getcoid(Integer k) throws Exception{
		return mapper.getcoid(k);
	}

}

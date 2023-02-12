package com.sof8.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sof8.dto.Coupon;
import com.sof8.dto.Member;
import com.sof8.service.CouponService;

@Controller
@RequestMapping("/event")
public class EventController {
	@Autowired
	CouponService service;
	
	String dir = "event/";
	
	@RequestMapping("/event")
	public String event(Model model) {
		model.addAttribute("content", dir+"event");
		return "index";
	}
	
	@RequestMapping("/coupon")
	public String coupon(Model model) {
		
		try {
			
		   List<Coupon> coupons = service.get();
		   System.out.println(coupons);
		   model.addAttribute("coupons",coupons);
		} catch (Exception e) { 
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		model.addAttribute("content", dir+"coupon");
		return "index";
	} 
	
	@ResponseBody    //데이터를 주고 받음 
	@RequestMapping("/addcoupon")
	public int addcoupon(HttpSession session, Model model, @RequestParam int co_id) {
		System.out.println("Here is addcoupon");
		int result = 0;
		Member member = (Member)session.getAttribute("member");
		
		// 로그인 중이 아니라면 쿠폰 발급 거부
		if(member == null) {
			result = -1;
			return result;
		}
		
		Map<String,Object> map= new HashMap<String,Object>();
		map.put("user_id", member.getUser_id());
		map.put("co_id", co_id);
		System.out.println("Map: "+map);
		
		try {
			

			Coupon coupon =service.getCoupon(map);
			
			if(coupon == null) { // 쿠폰이 없어요
				service.registerCoupon(map);
				result = 1;
			} else { // 쿠폰이 이미 있어요
				result = 2;
			}
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		return result;
		
	}
	
	
	

}
 
	
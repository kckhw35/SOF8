package com.sof8.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.sof8.dto.Admin;
import com.sof8.dto.OrderForm;
import com.sof8.service.DetailOrderService;
import com.sof8.service.OrderService;
import com.sof8.service.ReservationService;

@Controller
@RequestMapping("/console")
public class ConsoleController {

	@Autowired
	OrderService oservice;
	@Autowired
	DetailOrderService doservice;
	@Autowired
	ReservationService rservice;
	
	String dir = "console/";

	// 127.0.0.1/console - 대쉬보드
	@RequestMapping("")
	public String console(HttpSession session, Model model, Admin admin) {
		admin = (Admin) session.getAttribute("admin");
		if(admin != null) {
			int dayTarget = 5;
			int weekTarget = dayTarget*7;
			int monthTarget = weekTarget*4;
			int yearTarget = monthTarget*12;
			
			Map<String, Integer> map = new HashMap<String, Integer>();
			map.put("sales", getTodaySales());
			map.put("orders", getTodayOrders());
			map.put("products", getTodayProducts());
			map.put("deliverys", getTodayDeliverys());
			
			map.put("dayProducts", dayProducts());
			map.put("weekProducts", weekProducts());
			map.put("monthProducts", monthProducts());
			map.put("yearProducts", yearProducts());
			
			map.put("dayTarget", dayTarget);
			map.put("weekTarget", weekTarget);
			map.put("monthTarget", monthTarget);
			map.put("yearTarget", yearTarget);
			
			
			model.addAttribute("map", map);
			model.addAttribute("admin", admin);
			
			return dir+"console";	
		} else	return "redirect:/";
	}
	
	// 금일매출
	public int getTodaySales() {
		int sales = 0;
		
		try {
			sales = oservice.getTodaySales();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return sales;
	}
	
	// 금일주문수
	public int getTodayOrders() {
		int orders = 0;
		
		try {
			orders = oservice.getTodayOrders();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return orders;
	}
	// 금일 상품판매수
	public int getTodayProducts() {
		int products = 0;
		try {
			products = doservice.getTodayProducts();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return products;
	}
	
	// 금일배송수
	public int getTodayDeliverys() {
		int deliverys = 0;
		try {
			deliverys = rservice.getTodayDeliverys();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return deliverys;
	}
	// 금년 목표판매량
	public int yearProducts() {
		int year = 0;
		try {
			year = doservice.getYearProducts();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return year;
	}
	// 금월 목표판매량
	public int monthProducts() {
		int month = 0;
		try {
			month = doservice.getMonthProducts();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return month;
	}
	// 금주 목표판매량
	public int weekProducts() {
		int week = 0;
		try {
			week = doservice.getWeekProducts();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return week;
	}
	// 금일 목표판매량
	public int dayProducts() {
		int day = 0;
		try {
			day = doservice.getTodayProducts();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return day;
	}
	// 127.0.0.1/console/category
	@RequestMapping("/category")
	public String category(HttpSession session, Model model, Admin admin) {
		admin = (Admin) session.getAttribute("admin");
		if(admin != null) {
			
			Map<String, Object> map =  new HashMap<String, Object>();
			map.put("day", dayCategoryProducts());
			map.put("week", weekCategoryProducts());
			map.put("month", monthCategoryProducts());
			map.put("year", yearCategoryProducts());

			model.addAttribute("map", map);
			model.addAttribute("admin", admin);
			model.addAttribute("content", dir + "category");
			return dir+"console";
		} else	return "redirect:/";
	}

	// 카테고리별 금일 목표판매량
	public Map<String, Object> dayCategoryProducts() {
		Map<String, Object> day = new HashMap<String, Object>();
		try {
			day = doservice.getTodayCategoryProducts();
		} catch(Exception e) {
			e.printStackTrace();
		}
		System.out.println(day);
		return day;
	}
	
	// 카테고리별 금일 목표판매량
	public Map<String, Object> weekCategoryProducts() {
		Map<String, Object> week = new HashMap<String, Object>();
		try {
			week = doservice.getWeekCategoryProducts();
		} catch(Exception e) {
			e.printStackTrace();
		}
		System.out.println(week);

		return week;
	}
	
	// 카테고리별 금일 목표판매량
	public Map<String, Object> monthCategoryProducts() {
		Map<String, Object> month = new HashMap<String, Object>();
		try {
			month = doservice.getMonthCategoryProducts();
		} catch(Exception e) {
			e.printStackTrace();
		}	
		System.out.println(month);

		return month;
	}
	
	// 카테고리별 금일 목표판매량
	public Map<String, Object> yearCategoryProducts() {
		Map<String, Object> year = new HashMap<String, Object>();
		try {
			year = doservice.getYearCategoryProducts();
		} catch(Exception e) {
			e.printStackTrace();
		}	
		System.out.println(year);

		return year;
	}
	
	// 127.0.0.1/console/order
	// 주문
	@RequestMapping("/order")
	public String order(HttpSession session, Model model, Admin admin) {
		admin = (Admin) session.getAttribute("admin");
		if(admin != null) {
			List<OrderForm> olist = null;	// 전체 주문 내역
			List<OrderForm> tolist = null;	// 금일 주문 내역
			List<OrderForm> tcolist = null;	// 금일 주문 확정 내역
			List<OrderForm> colist = null;	// 주문 취소 내역
			List<OrderForm> mlist = null;	// 금월 최고 고객
			
			try {
				olist = oservice.getorderlist();
				tolist = oservice.gettodayorder();
				tcolist = oservice.gettodayconfrim();
				colist = oservice.getcancelorder();
				mlist = oservice.getmonth();
						
			} catch (Exception e) {
				e.printStackTrace();
			}
			model.addAttribute("olist", olist);
			model.addAttribute("tolist", tolist);
			model.addAttribute("tcolist", tcolist);
			model.addAttribute("colist", colist);
			model.addAttribute("mlist", mlist);
			model.addAttribute("admin", admin);
			model.addAttribute("content", dir + "order");
			return dir+"console";
		} else	return "redirect:/";
	}
	
	// 127.0.0.1/console/reservation
	// 예약
	@RequestMapping("/reservation")
	public String reservation(HttpSession session, Model model, Admin admin) {
		admin = (Admin) session.getAttribute("admin");
		if(admin != null) {
			model.addAttribute("admin", admin);
			model.addAttribute("content", dir + "reservation");
			return dir+"console";
		} else	return "redirect:/";
	}
	
	// 127.0.0.1/console/order
	// 배송
	@RequestMapping("/delivery")
	public String delivery(HttpSession session, Model model, Admin admin) {
		admin = (Admin) session.getAttribute("admin");
		if(admin != null) {
			model.addAttribute("admin", admin);
			model.addAttribute("content", dir + "delivery");
			return dir+"console";
		} else	return "redirect:/";
	}	
	
}

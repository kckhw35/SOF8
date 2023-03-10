package com.sof8.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.sof8.dto.Admin;
import com.sof8.dto.Delivery;
import com.sof8.dto.Order;
import com.sof8.dto.OrderForm;
import com.sof8.dto.Reservation;
import com.sof8.service.DeliveryService;
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
	
	@Autowired
	DeliveryService dservice;
	
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

	// 127.0.0.1/console/chart
	// 주문
	@RequestMapping("/chart")
	public String chart(HttpSession session, Model model, Admin admin) {
		admin = (Admin) session.getAttribute("admin");
		model.addAttribute("admin", admin);
		model.addAttribute("content", dir + "chart");
		return dir+"console";
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
	
	// 주문, 취소, 내역 상태 변경
	@RequestMapping("/updateorderstatus")
	public String updateorderstatus(HttpSession session, Model model, Admin admin, String o_status, int o_id) {
		Order o = null;
		admin = (Admin) session.getAttribute("admin");
		if(admin != null) {
			try {
				o = oservice.get(o_id);
				o.setO_status(o_status);
				oservice.modify(o);
			} catch (Exception e) {
				e.printStackTrace();
			}
			model.addAttribute("admin", admin);
			model.addAttribute("content", dir + "order");
			return "redirect:/console/order";
		} else	return "redirect:/";
	}
	
	// 127.0.0.1/console/reservation
	// 예약 및 배송
	@RequestMapping("/reservation")
	public String reservation(HttpSession session, Model model, Admin admin) {
		List<Delivery> dlist = null;	// 배송기사 정보
		List<Reservation> tlist = null;	// 오늘 배송되야하는 예약 리스트
		List<Reservation> rlist = null; // 아직 배송전인 예약 리스트
		List<Reservation> mlist = null; // 이번달 예약 리스트
		
		admin = (Admin) session.getAttribute("admin");
		if(admin != null) {
			
			try {
				dlist = dservice.get();
				tlist = rservice.gettodaydelivery();
				rlist = rservice.getreservelist();
				mlist = rservice.getmonthdelivery();
				
			} catch (Exception e) {
				e.printStackTrace();
			}
			model.addAttribute("dlist", dlist);
			model.addAttribute("tlist", tlist);
			model.addAttribute("rlist", rlist);
			model.addAttribute("mlist", mlist);
			model.addAttribute("admin", admin);
			model.addAttribute("content", dir + "reservation");
			return dir+"console";
		} else	return "redirect:/";
	}
	
	@RequestMapping("/updatereservestatus")
	public String updatereservestatus(HttpSession session, Model model, Admin admin, String o_status, int o_id) {
		Order o = null;
		admin = (Admin) session.getAttribute("admin");
		if(admin != null) {
			try {
				o = oservice.get(o_id);
				o.setO_status(o_status);
				oservice.modify(o);
			} catch (Exception e) {
				e.printStackTrace();
			}
			model.addAttribute("admin", admin);
			model.addAttribute("content", dir + "reservation");
			return "redirect:/console/reservation";
		} else	return "redirect:/";
	}
	
}

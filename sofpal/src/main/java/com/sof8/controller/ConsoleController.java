package com.sof8.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.sof8.dto.Admin;
import com.sof8.dto.Delivery;
import com.sof8.dto.Order;
import com.sof8.dto.OrderForm;
import com.sof8.service.DeliveryService;
import com.sof8.service.OrderService;

@Controller
@RequestMapping("/console")
public class ConsoleController {

	@Autowired
	OrderService oservice;
	
	@Autowired
	DeliveryService dservice;
	
	String dir = "console/";

	// 127.0.0.1/console - 대쉬보드
	@RequestMapping("")
	public String console(HttpSession session, Model model, Admin admin) {
		admin = (Admin) session.getAttribute("admin");
		if(admin != null) {
			model.addAttribute("sales", getTodaySales());
			model.addAttribute("orders", getTodayOrders());
			model.addAttribute("products", getTodayProducts());
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
			products = oservice.getTodayProducts();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return products;
	}
	
	// 금일배송수
	
	// 금년 목표판매량
	
	// 금월 목표판매량
	
	// 금주 목표판매량
	
	// 금일 목표판매량
	// 127.0.0.1/console/chart
	@RequestMapping("/chart")
	public String chart(HttpSession session, Model model, Admin admin) {
		admin = (Admin) session.getAttribute("admin");
		if(admin != null) {
			model.addAttribute("admin", admin);
			model.addAttribute("content", dir + "chart");
			return dir+"console";
		} else	return "redirect:/";
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
	
	// 취소 내역 상태 변경
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
		admin = (Admin) session.getAttribute("admin");
		if(admin != null) {
			List<Delivery> dlist = null;	// 배송기사 정보
			
			try {
				dlist = dservice.get();
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			model.addAttribute("dlist", dlist);
			model.addAttribute("admin", admin);
			model.addAttribute("content", dir + "reservation");
			return dir+"console";
		} else	return "redirect:/";
	}

	
}

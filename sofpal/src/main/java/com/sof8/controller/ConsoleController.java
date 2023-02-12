package com.sof8.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.sof8.dto.Admin;
import com.sof8.service.OrderService;

@Controller
@RequestMapping("/console")
public class ConsoleController {

	@Autowired
	OrderService oservice;
	
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

	
	
	// 127.0.0.1/console/table
	@RequestMapping("/table")
	public String table(HttpSession session, Model model, Admin admin) {
		admin = (Admin) session.getAttribute("admin");
		if(admin != null) {
			model.addAttribute("admin", admin);
			model.addAttribute("content", dir + "table");
			return dir+"console";
		} else	return "redirect:/";
	}
	
}

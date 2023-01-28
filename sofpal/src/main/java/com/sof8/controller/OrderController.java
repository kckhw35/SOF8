package com.sof8.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.sof8.dto.Cart;
import com.sof8.dto.Product;
import com.sof8.service.ProductService;

@Controller
@RequestMapping("/order")
public class OrderController {

	String dir = "order/";
	ProductService pservice;
	
	// 127.0.0.1/order/cart
	@RequestMapping("/cart")
	public String cart(Model model, Cart cart) {
		model.addAttribute("content", dir + "cart");
		System.out.println("[SUCCESS] : OrderController/cart - 장바구니폼");
		return "index";
	}
	
	// 주문 폼
	@RequestMapping("/order")
	public String order(Model model, int p_id) {
		Product p = null;
		
		try {
			p = pservice.get(p_id);
			model.addAttribute("p", p);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		model.addAttribute("content", dir+"order");
		return "index";
	}
}
package com.sof8.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.sof8.dto.Cart;

@Controller
@RequestMapping("/order")
public class OrderController {

	String dir = "order/";

	// 127.0.0.1/order/cart
	@RequestMapping("/cart")
	public String cart(Model model, Cart cart) {
		model.addAttribute("content", dir + "cart");
		System.out.println("[SUCCESS] : OrderController/cart - 장바구니폼");
		return "index";
	}
}

package com.sof8.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.sof8.dto.Product;
import com.sof8.service.ProductService;

@Controller
@RequestMapping("/admin")
public class AdminController {
	
	String dir = "admin/";
	
	@Autowired
	ProductService pservice;
	
	@RequestMapping("/")
	public String admin() {
		return "admin";
	}
	
	// 상품등록
	@RequestMapping("/registerproduct")
	public String registerproduct(Model model) {
		model.addAttribute("content", dir+"registerproduct");
		return "index";
	}
	
	@RequestMapping("/updateproduct")
	public String updateproduct(Model model, int p_id) {
		Product p = null;
		
		try {
			p = pservice.get(p_id);
			model.addAttribute("p", p);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		model.addAttribute("content", dir+"updateproduct");
		return "index";
	}
}

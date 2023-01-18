package com.sof8.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
public class AdminController {
	
	String dir = "admin/";
	
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
}

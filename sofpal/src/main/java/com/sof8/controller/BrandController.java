package com.sof8.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/brand")
public class BrandController {
	
	String dir = "brand/";
	
	@RequestMapping("/company")
	public String company(Model model) {
		model.addAttribute("content", dir+"company");
		return "index";
	}
	
	@RequestMapping("/products")
	public String products(Model model) {
		model.addAttribute("content", dir+"products");
		return "index";
	}
	
	@RequestMapping("/showroom")
	public String showroom(Model model) {
		model.addAttribute("content", dir+"showroom");
		return "index";
	}


}

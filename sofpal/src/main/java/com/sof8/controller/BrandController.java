package com.sof8.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.sof8.dto.Product;

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
	@RequestMapping("/showroom_pangyo")
	public String showroom_pangyo(Model model) {
		model.addAttribute("content", dir+"showroom_pangyo");
		return "index";
	}
	@RequestMapping("/showroom_busan")
	public String showroom_busan(Model model) {
		model.addAttribute("content", dir+"showroom_busan");
		return "index";
	}


}

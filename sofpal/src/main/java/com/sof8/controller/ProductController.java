package com.sof8.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.sof8.dto.Product;
import com.sof8.service.ProductService;

@Controller
@RequestMapping("/product")
public class ProductController {
	
	String dir = "product/";
	
	@Autowired
	ProductService pservice;
	
	// 상품보기
	@RequestMapping("/productall")
	public String productall(Model model) {
		List<Product> list = null;
		
		try {
			list = pservice.get();
		} catch (Exception e) {
			e.printStackTrace();
		}
		model.addAttribute("plist", list);
		model.addAttribute("content", dir+"productall");
		return "index";
	}
	
	@RequestMapping("/fabric")
	public String fabric(Model model) {
		model.addAttribute("content", dir+"fabric");
		return "index";
	}
	
	@RequestMapping("/leather")
	public String leather(Model model) {
		model.addAttribute("content", dir+"leather");
		return "index";
	}
	
	@RequestMapping("/pet")
	public String pet(Model model) {
		model.addAttribute("content", dir+"pet");
		return "index";
	}
	
	@RequestMapping("/livingroom")
	public String livingroom(Model model) {
		model.addAttribute("content", dir+"livingroom");
		return "index";
	}
	
	// 상품 상세보기
	@RequestMapping("/productdetail")
	public String productdetail(Model model) {
		model.addAttribute("content", dir+"productdetail");
		return "index";
	}
	
	// 상품 등록하기
	@RequestMapping("/register")
	public String register(Model model, Product product) {
		try {
			pservice.register(product);
			model.addAttribute("obj", product);
			model.addAttribute("content", dir+"productdetail");
		} catch (Exception e) {
			model.addAttribute("content", "redirect:/product/registerproduct");
			e.printStackTrace();
		}
		return "index";
	}
}

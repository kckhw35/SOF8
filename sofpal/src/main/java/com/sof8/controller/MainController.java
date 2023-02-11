package com.sof8.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.sof8.dto.Product;
import com.sof8.service.ProductService;

@Controller
public class MainController {
	@Autowired
	ProductService pservice;
	
	@RequestMapping("/")
	public String main(Model model) {
		List<Product> list = null;
		
		try {
			list = pservice.selectnew();
		} catch (Exception e) {
			e.printStackTrace();
		}
		model.addAttribute("plist", list);
		return "index";
	}

}

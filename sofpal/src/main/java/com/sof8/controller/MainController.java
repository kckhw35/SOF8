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
		List<Product> oldlist = null;
		List<Product> newlist = null;
		try {
			oldlist = pservice.selectold();
			newlist = pservice.selectnew();
		} catch (Exception e) {
			e.printStackTrace();
		}
		model.addAttribute("oldlist", oldlist);
		model.addAttribute("newlist", newlist);
		return "index";
	}

}

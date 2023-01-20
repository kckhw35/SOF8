package com.sof8.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.sof8.service.CategoryService;

@Controller
@RequestMapping("/category")
public class CategoryController {
	
	String dir = "category/";
	
	@Autowired
	CategoryService cservice;

	@RequestMapping("/")
	public String category(Model model) {
		return "index";
	}
}
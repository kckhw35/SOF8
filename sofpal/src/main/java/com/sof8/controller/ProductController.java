package com.sof8.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/product")
public class ProductController {
	
	String dir = "product/";
	
	@RequestMapping("/productall")
	public String productall(Model model) {
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
}

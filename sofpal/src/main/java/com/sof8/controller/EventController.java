package com.sof8.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/event")
public class EventController {
	
	String dir = "event/";
	
	@RequestMapping("/event")
	public String event(Model model) {
		model.addAttribute("content", dir+"event");
		return "index";
	}
	
	@RequestMapping("/coupon")
	public String coupon(Model model) {
		model.addAttribute("content", dir+"coupon");
		return "index";
	}
	
	

}

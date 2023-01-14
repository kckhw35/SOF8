package com.sof8.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/member")
public class CustomController {
	
	String dir = "member/";
	
	@RequestMapping("/join")
	public String join(Model model) {
		model.addAttribute("content", dir+"join");
		return "index";
	}
	
	@RequestMapping("/joinok")
	public String joinok(Model model) {
		model.addAttribute("content", dir+"joinok");
		return "index";
	}
}

package com.sof8.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/notice")
public class NoticeController {

	String dir ="notice/";
	
	
	@RequestMapping("")
	public String NoticeMain(Model model) {
		model.addAttribute("content", dir+"main");
		
		return "index";
	}
}

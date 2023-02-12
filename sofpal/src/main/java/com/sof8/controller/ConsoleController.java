package com.sof8.controller;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.sof8.dto.Admin;

@Controller
@RequestMapping("/console")
public class ConsoleController {

	String dir = "console/";

	// 127.0.0.1/console
	@RequestMapping("")
	public String console(HttpSession session, Model model, Admin admin) {
		admin = (Admin) session.getAttribute("admin");
		if(admin != null) {
			model.addAttribute("admin", admin);
			
			return dir+"console";	
		} else	return "redirect:/";
	}

	// 127.0.0.1/console/chart
	@RequestMapping("/chart")
	public String chart(HttpSession session, Model model, Admin admin) {
		admin = (Admin) session.getAttribute("admin");
		if(admin != null) {
			model.addAttribute("admin", admin);
			model.addAttribute("content", dir + "chart");
			return dir+"console";
		} else	return "redirect:/";
	}
	
	// 127.0.0.1/console/table
	@RequestMapping("/table")
	public String table(HttpSession session, Model model, Admin admin) {
		admin = (Admin) session.getAttribute("admin");
		if(admin != null) {
			model.addAttribute("admin", admin);
			model.addAttribute("content", dir + "table");
			return dir+"console";
		} else	return "redirect:/";
	}
	
}

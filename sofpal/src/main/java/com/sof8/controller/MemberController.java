package com.sof8.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.sof8.dto.Member;
import com.sof8.service.MemberService;

@Controller
@RequestMapping("/member")
public class MemberController {
	
	@Autowired
	MemberService service;
	
	String dir = "member/";
	
	// 127.0.0.1/member/join
	@RequestMapping("/join")
	public String join(Model model) {
		model.addAttribute("content", dir+"join");
		return "index";
	}
	// 127.0.0.1/member/joinok
	@RequestMapping("/joinok")
	public String joinok(Model model, Member member) {
		try {
			service.register(member);
			model.addAttribute("content", dir+"joinok");
		} catch (Exception e) {
			model.addAttribute("content", dir+"joinfail");
			e.printStackTrace();
		}
		return "index";
	}
	
	// 127.0.0.1/member/login
	@RequestMapping("/login")
	public String login(Model model) {
		model.addAttribute("content", dir+"login");
		return "index";
	}
}

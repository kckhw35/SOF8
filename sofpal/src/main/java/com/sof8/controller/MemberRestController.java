package com.sof8.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sof8.dto.Member;
import com.sof8.service.MemberService;

@RestController
@RequestMapping("/member")
public class MemberRestController {
	@Autowired
	MemberService service;
	
	// 127.0.0.1/member/checkid
	@RequestMapping("/checkid")
	public Object checkid(String user_id) {
		int result = 0;
		try {
			// 가입된 아이디 검색
			Member member = service.get(user_id);
			
			// 가입된 아이디 존재함
			if(member != null) 	result = 1;
			
			System.out.println("[SUCCESS] : MemberController/checkid");
		} catch (Exception e) {
			System.out.println("[ERROR] : MemberController/checkid");			
			e.printStackTrace();
		}
		return result; 
	}
}

package com.sof8.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sof8.dto.Admin;
import com.sof8.dto.Member;
import com.sof8.service.AdminService;
import com.sof8.service.MemberService;

@RestController
@RequestMapping("/")
public class MainRestController {
	@Autowired
	MemberService mservice;
	
	@Autowired
	AdminService aservice;
	
	// 127.0.0.1/checkid
	@RequestMapping("/checkid")
	public Object checkid(String user_id) {
		int result = 0;
		try {
			// 가입된 아이디 검색
			Member member = mservice.get(user_id);
			
			// 가입된 아이디 존재함
			if(member != null) 	result = 1;
			
			System.out.println("[SUCCESS] : MainController/checkid");
		} catch (Exception e) {
			System.out.println("[ERROR] : MainController/checkid");			
			e.printStackTrace();
		}
		return result; 
	}
	
	// 127.0.0.1/check_adminid
	@RequestMapping("/check_adminid")
	public Object check_adminid(String admin_id) {
		int result = 0;
		try {
			// 가입된 아이디 검색
			Admin admin = aservice.get(admin_id);
			
			// 가입된 아이디 존재함
			if(admin != null) 	result = 1;
			
			System.out.println("[SUCCESS] : MainController/check_adminid");
		} catch (Exception e) {
			System.out.println("[ERROR] : MainController/check_adminid");			
			e.printStackTrace();
		}
		return result; 
	}
	
	/*
	 * // 127.0.0.1/find_id
	 * 
	 * @RequestMapping("/find_id") public Object find_id(Model model, String name,
	 * String email) { String error = null; if (name != "" && email != "") { try {
	 * Member user = new Member(name, email); Member member = mservice.getId(user);
	 * System.out.println(member); if (member != null) {
	 * System.out.println("[SUCCESS] : MemberController/find_id - 아이디 찾기 성공");
	 * return model.addAttribute("member", member); } } catch (Exception e) { //
	 * TODO Auto-generated catch block e.printStackTrace();
	 * System.out.println("[ERROR] : MemberController/find_id - 아이디 찾기 실패"); return
	 * error = "이름 또는 이메일을 잘못 입력하셨습니다."; } } return error; }
	 */
}

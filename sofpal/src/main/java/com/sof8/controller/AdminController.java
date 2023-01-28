package com.sof8.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.sof8.dto.Admin;
import com.sof8.dto.Product;
import com.sof8.service.AdminService;
import com.sof8.service.ProductService;

@Controller
@RequestMapping("/admin")
public class AdminController {
	
	String dir = "admin/";
	
	@Autowired
	ProductService pservice;

	@Autowired
	AdminService aservice;
	
	// 관리자 로그인
	// 127.0.0.1/admin
	@RequestMapping("")
	public String login(Model model, Admin Admin) {
		model.addAttribute("content", dir+"login");
		System.out.println("[SUCCESS] : AdminController/ - 관리자 로그인 화면 성공");
		return "index";
	}
	 
	// 관리자 회원가입
	// 127.0.0.1/admin/account
	@RequestMapping("/account")
	public String admin(Model model, Admin Admin) {
		model.addAttribute("content", dir+"account");
		System.out.println("[SUCCESS] : AdminController/account - 관리자 회원가입 화면 성공");
		return "index";
	}
	
	// 관리자 회원가입 성공
	// 127.0.0.1/admin/accountok
	@RequestMapping("/accountok")
	public String accountok(Model model, Admin admin) {
		try {
			// 회원가입 기능 실행
			aservice.register(admin);
			// 회원가입 성공화면 출력
			model.addAttribute("content", dir + "accountok");
			// 성공화면에 출력할 이름
			model.addAttribute("name", admin.getAdmin_name());
			System.out.println("[SUCCESS] : AdminController/accountok - 관리자 회원가입 성공");
			//throw new Exception(); 
		} catch (Exception e) {
			// 회원가입 실패화면 출력
			model.addAttribute("content", dir + "accountfail");

			System.out.println("[ERROR] : AdminController/accountok - 관리자 회원가입 실패");
			e.printStackTrace();
		}
		return "index";
	}
	
	// 127.0.0.1/admin/loginok
	@RequestMapping("/loginok")
	public String loginok(HttpSession session, Model model, Admin admin) {
		String error = null;
		try {
			// 가입된 아이디 조회
			Admin a = aservice.get(admin.getAdmin_id());
			// 가입된 아이디라면
			if (a != null) {
				// 가입된 아이디의 비밀번호가 일치한다면
				if (a.getAdmin_pwd().equals(admin.getAdmin_pwd())) {
					// 세션에 로그인 유저정보 저장
					session.setAttribute("admin", a);
					// 홈 화면으로 이동
					System.out.println("[SUCCESS] : AdminController/loginok - 관리자 로그인 성공");
					return "redirect:/";                   
				}else {
					error = "아이디 또는 비밀번호가 일치하지 않습니다.";
					System.out.println("[ERROR] : AdminController/accountok - 비밀번호 로그인 실패");				
				}
			} else {
				error = "아이디 또는 비밀번호가 일치하지 않습니다.";
				System.out.println("[ERROR] : AdminController/accountok - 아이디 로그인 실패");
			}
			model.addAttribute("error_login", error);
			model.addAttribute("content", dir + "login");
		} catch (Exception e) {
			model.addAttribute("content", dir + "loginfail");
			System.out.println("[ERROR] : AdminController/accountok - 관리자 로그인 실패");
			e.printStackTrace();
		}

		return "index";
	}	
	
	// 관리자 로그아웃
	// 127.0.0.1/admin/logout
	@RequestMapping("/logout")
	public String logout(HttpSession session, Model model) {
		// 세션이 존재할 시 세션을 삭제하여 로그아웃
		if(session != null)	session.removeAttribute("admin");
		
		model.addAttribute("content", null);
		System.out.println("[SUCCESS] : MemberController/logout - 로그아웃 성공");
		
		return "redirect:/";                   
	}
	
	
	// 상품등록
	@RequestMapping("/registerproduct")
	public String registerproduct(Model model) {
		model.addAttribute("content", dir+"registerproduct");
		return "index";
	}
	
	@RequestMapping("/updateproduct")
	public String updateproduct(Model model, int p_id) {
		Product p = null;
		
		try {
			p = pservice.get(p_id);
			model.addAttribute("p", p);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		model.addAttribute("content", dir+"updateproduct");
		return "index";
	}
}

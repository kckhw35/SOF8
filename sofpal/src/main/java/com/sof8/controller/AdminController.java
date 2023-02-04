package com.sof8.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sof8.dto.Admin;
import com.sof8.dto.Member;
import com.sof8.dto.Paging;
import com.sof8.dto.Product;
import com.sof8.service.AdminService;
import com.sof8.service.MemberService;
import com.sof8.service.ProductService;

@Controller
@RequestMapping("/admin")
public class AdminController {

	String dir = "admin/";

	@Autowired
	AdminService aservice;

	@Autowired
	MemberService mservice;

	@Autowired
	ProductService pservice;

	// 관리자 로그인
	// 127.0.0.1/admin
	@RequestMapping("")
	public String login(Model model, Admin Admin) {
		model.addAttribute("content", dir + "login");
		System.out.println("[SUCCESS] : AdminController/ - 관리자 로그인 화면 성공");
		return "index";
	}

	// 로그인 기능
	// 127.0.0.1/admin/loginok
	@ResponseBody
	@RequestMapping("/loginok")
	public String loginok(HttpSession session, Admin admin) {
		String result = "1";
		System.out.println("Admin: " + admin);
		try {
			// 가입된 아이디 조회
			Admin a = aservice.get(admin.getAdmin_id());
			System.out.println("a: " + a);

			// 가입된 아이디라면
			if (a != null) {
				// 가입된 아이디의 비밀번호가 일치한다면
				if (a.getAdmin_pwd().equals(admin.getAdmin_pwd())) {
					// 세션에 로그인 유저정보 저장
					session.setAttribute("admin", a);
					// 홈 화면으로 이동
					System.out.println("[SUCCESS] : AdminController/loginok - 관리자 로그인 성공");
					return "redirect:/";
				} else {
					// error = "아이디 또는 비밀번호가 일치하지 않습니다.";
					result = "0";
				}
			} else {
				// error = "아이디 또는 비밀번호가 일치하지 않습니다.";
				result = "0";
				System.out.println("[ERROR] : AdminController/accountok - 아이디 로그인 실패");
			}

		} catch (Exception e) {
			System.out.println("[ERROR] : AdminController/accountok - 관리자 로그인 실패");
			e.printStackTrace();
		}

		return result;
	}

	// 관리자 로그아웃
	// 127.0.0.1/admin/logout
	@RequestMapping("/logout")
	public String logout(HttpSession session, Model model) {
		// 세션이 존재할 시 세션을 삭제하여 로그아웃
		if (session != null)
			session.removeAttribute("admin");

		model.addAttribute("content", null);
		System.out.println("[SUCCESS] : MemberController/logout - 로그아웃 성공");

		return "redirect:/";
	}

	// 관리자 회원가입 화면
	// 127.0.0.1/admin/account
	@RequestMapping("/account")
	public String admin(Model model, Admin Admin) {
		model.addAttribute("content", dir + "account");
		System.out.println("[SUCCESS] : AdminController/account - 관리자 회원가입 화면 성공");
		return "index";
	}

	// 관리자 회원가입 기능
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
			// throw new Exception();
		} catch (Exception e) {
			// 회원가입 실패화면 출력
			model.addAttribute("content", dir + "accountfail");

			System.out.println("[ERROR] : AdminController/accountok - 관리자 회원가입 실패");
			e.printStackTrace();
		}
		return "index";
	}

	// 관리자 아이디 중복 체크
	// 127.0.0.1/check_adminid
	@ResponseBody
	@RequestMapping("/check_adminid")
	public Object check_adminid(String admin_id) {
		int result = 0;
		try {
			// 가입된 아이디 검색
			Admin admin = aservice.get(admin_id);

			// 가입된 아이디 존재함
			if (admin != null)
				result = 1;

			System.out.println("[SUCCESS] : MainController/check_adminid");
		} catch (Exception e) {
			System.out.println("[ERROR] : MainController/check_adminid");
			e.printStackTrace();
		}
		return result;
	}

	// 회원 리스트 화면
	// 127.0.0.1/admin/memberlist
	@RequestMapping("/memberlist")
	public String memberlist(HttpSession session, Model model,
			@RequestParam(defaultValue = "1") int page,
			@RequestParam(required = false) String keyword,
			@RequestParam(value="type", defaultValue = "user_id") String type
			) {
		System.out.println(keyword);
		System.out.println(type);
		if (session.getAttribute("admin") != null) {
			try {
				int totalRow = mservice.getTotal(keyword, type);
				Paging paging = new Paging(2, 2, totalRow, page, keyword, type);
				List<Member> members = mservice.getList(paging); 

				model.addAttribute("members", members);
				model.addAttribute("page", page);
				model.addAttribute("paging", paging);
				model.addAttribute("content", dir + "memberlist");
				
				System.out.println("totalRow: " + totalRow);
				System.out.println("paging: " + paging);
				System.out.println("members: " + members);				
				System.out.println("[SUCCESS] : AdminController/memberlist - 회원리스트 화면출력");
			} catch (Exception e) {
				e.printStackTrace();
				System.out.println("[ERROR] : AdminController/memberlist - 회원리스트 로딩 실패");
			}
		}

		return "index";
	}
	
	// 상품등록 폼
	@RequestMapping("/registerproduct")
	public String registerproduct(Model model) {
		model.addAttribute("content", dir + "registerproduct");
		return "index";
	}
	
	// 상품수정 폼
	@RequestMapping("/updateproduct")
	public String updateproduct(Model model, int p_id) {
		Product p = null;

		try {
			p = pservice.get(p_id);
			model.addAttribute("p", p);
		} catch (Exception e) {
			e.printStackTrace();
		}

		model.addAttribute("content", dir + "updateproduct");
		return "index";
		}
}

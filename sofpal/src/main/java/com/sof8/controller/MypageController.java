package com.sof8.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.sof8.dto.Member;
import com.sof8.service.MemberService;

@Controller
@RequestMapping("/mypage")
public class MypageController {

	@Autowired
	MemberService service;

	String dir = "mypage/";

	// 127.0.0.1/mypage/info
	@RequestMapping("/info")
	public String info(HttpSession session, Model model) {
		// 세션이 있다면(로그인 중이라면)
		if(session != null) {
			// 세션 아이디를 다운캐스팅하여 Member 변수에 초기화
			Member member = (Member) session.getAttribute("member");
			try {
				// 회원 정보 로딩
				member = service.get(member.getUser_id());
				System.out.println("[DATA] user: " + member);
				
				model.addAttribute("member", member);
				model.addAttribute("content", dir + "info");
				System.out.println("[SUCCESS] : MemberController/mypage - 회원정보 로딩 성공");
				return "index";

			} catch (Exception e) {
				System.out.println("[ERROR] : MemberController/mypage - 회원정보 로딩 실패");
				e.printStackTrace();
			}	
		}
		return "redirect:/";
	}
	
	// 127.0.0.1/mypage/edit
	@RequestMapping("/edit")
	public String edit(HttpSession session, Model model) {
		// 세션이 있다면(로그인 중이라면)
		if(session != null) {
			// 세션 아이디를 다운캐스팅하여 Member 변수에 초기화
			Member member = (Member) session.getAttribute("member");
			try {
				// 회원 정보 로딩
				member = service.get(member.getUser_id());
				System.out.println("[DATA] user: " + member);
				
				model.addAttribute("member", member);
				model.addAttribute("content", dir + "edit");
				
				System.out.println("[SUCCESS] : MemberController/edit - 회원정보 수정 로딩 성공");
				return "index";

			} catch (Exception e) {
				System.out.println("[ERROR] : MemberController/edit - 회원정보 수정 로딩 실패");
				e.printStackTrace();
			}
		} 
		return "redirect:/";
	}
	
	// 127.0.0.1/mypage/editok
	@RequestMapping("/editok")
	public String editok(HttpSession session, Member member, Model model) {
		// 세션이 있다면(로그인 중이라면)
		if(session != null) {
			try {
				// 회원정보 수정
				service.modify(member);
				System.out.println("[SUCCESS] : MemberController/editok - 회원정보 수정 성공");
				
				session.setAttribute("login_user", member);
				
				return "redirect:/mypage/info";
			} catch (Exception e) {
				System.out.println("[ERROR] : MemberController/editok - 회원정보 수정 실패");
				e.printStackTrace();
			}
		} 
		return "redirect:/";
	}
	
	// 127.0.0.1/mypage/cancel
	@RequestMapping("/cancel")
	public String cancel(HttpSession session, Member member, Model model) {
		// 세션이 있다면(로그인 중이라면)
		if(session != null) {
			try {
				model.addAttribute("content", dir + "cancel");
				System.out.println("[SUCCESS] : MemberController/cancel - 회원탈퇴 로딩 성공");
				
				return "index";
			} catch (Exception e) {
				System.out.println("[ERROR] : MemberController/cancel - 회원탈퇴 로딩 실패");
				e.printStackTrace();
			}
		}
		return "redirect:/";
	}
	
	// 127.0.0.1/mypage/cancelok
	@RequestMapping("/cancelok")
	public String cancelok(HttpSession session, String pwd, Model model) {
		// 세션이 있다면(로그인 중이라면)
		if(session != null) {
			try {
				Member member = (Member) session.getAttribute("member");
				if(member.getPwd().equals(pwd)) {
					service.modifyEnable(member.getUser_id());
					session.invalidate();
					System.out.println("[SUCCESS] : MemberController/cancelok - 회원탈퇴 성공");
					return "redirect:/";
				}				
			} catch (Exception e) {
				System.out.println("[ERROR] : MemberController/cancelok - 회원탈퇴 실패");
				e.printStackTrace();
			}
		}
		return "redirect:/";
	}
	
}

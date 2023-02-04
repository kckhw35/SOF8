package com.sof8.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sof8.dto.Mark;
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
		if (session.getAttribute("member") != null) {
			// 세션 아이디를 다운캐스팅하여 Member 변수에 초기화
			Member member = (Member) session.getAttribute("member");
			try {
				// 회원 정보 로딩
				member = service.get(member.getUser_id());
				System.out.println("[DATA] user: " + member);

				model.addAttribute("member", member);
				model.addAttribute("content", dir + "info");
				System.out.println("[SUCCESS] : MypageController/info - 회원정보 로딩 성공");
				return "index";

			} catch (Exception e) {
				System.out.println("[ERROR] : MypageController/info - 회원정보 로딩 실패");
				e.printStackTrace();
			}
		}
		return "redirect:/";
	}

	// 127.0.0.1/mypage/edit
	@RequestMapping("/edit")
	public String edit(HttpSession session, Model model, Member member) {
		// 세션이 있다면(로그인 중이라면)
		if (session.getAttribute("member") != null) {
			// 세션 아이디를 다운캐스팅하여 Member 변수에 초기화
			Member m = (Member) session.getAttribute("member");
			try {
				// 회원 정보 로딩
				m = service.get(m.getUser_id());
				System.out.println("[DATA] user: " + m);

				model.addAttribute("member", m);
				model.addAttribute("content", dir + "edit");

				System.out.println("[SUCCESS] : MypageController/edit - 회원정보 수정 로딩 성공");
				return "index";

			} catch (Exception e) {
				System.out.println("[ERROR] : MypageController/edit - 회원정보 수정 로딩 실패");
				e.printStackTrace();
			}
		}
		return "redirect:/";
	}

	// 127.0.0.1/mypage/editok
	@RequestMapping("/editok")
	public String editok(HttpSession session, Member member, Model model) {
		// 세션이 있다면(로그인 중이라면)
		if (session.getAttribute("member") != null) {
			try {
				// 회원정보 수정
				service.modify(member);
				System.out.println("[SUCCESS] : MypageController/editok - 회원정보 수정 성공");

				session.setAttribute("login_user", member);

				return "redirect:/mypage/info";
			} catch (Exception e) {
				System.out.println("[ERROR] : MypageController/editok - 회원정보 수정 실패");
				e.printStackTrace();
			}
		}
		return "redirect:/";
	}

	// 127.0.0.1/mypage/cancel
	@RequestMapping("/cancel")
	public String cancel(HttpSession session, Member member, Model model) {
		// 세션이 있다면(로그인 중이라면)
		if (session.getAttribute("member") != null) {
			try {
				model.addAttribute("content", dir + "cancel");
				System.out.println("[SUCCESS] : MypageController/cancel - 회원탈퇴 로딩 성공");

				return "index";
			} catch (Exception e) {
				System.out.println("[ERROR] : MypageController/cancel - 회원탈퇴 로딩 실패");
				e.printStackTrace();
			}
		}
		return "redirect:/";
	}

	// 127.0.0.1/mypage/cancelok
	@ResponseBody
	@RequestMapping("/mypage/cancelok")
	public Boolean cancelok(HttpSession session, Model model, Member member) {
		Boolean result = false;
		// 세션이 있다면(로그인 중이라면)
		if (session.getAttribute("member") != null) {
			try {
				Member m = (Member) session.getAttribute("member");
				System.out.println("member: " + member);
				System.out.println("m: " + m);
				if (m.getPwd().equals(member.getPwd())) {

					service.modifyEnable(m.getUser_id());
					session.invalidate();
					System.out.println("[SUCCESS] : MypageController/cancelok - 회원탈퇴 성공");
					result = true;
				}
			} catch (Exception e) {
				System.out.println("[ERROR] : MypageController/cancelok - 회원탈퇴 실패");
				e.printStackTrace();
			}
		}
		return result;
	}

	// 127.0.0.1/mypage/orderlist
	@RequestMapping("/orderlist")
	public String orderlist(HttpSession session, Model model, Member member) {
		// 세션이 있다면(로그인 중이라면)
		if (session.getAttribute("member") != null) {
			// 찜목록 화면이동
			model.addAttribute("content", dir + "orderlist");
			System.out.println("[SUCCESS] : MypageController/orderlist - 주문목록 화면 출력");
		}
		return "index";
	}
	
	// 127.0.0.1/mypage/mark
	@RequestMapping("/mark")
	public String mark(HttpSession session, Model model, Member member) {
		// 세션이 있다면(로그인 중이라면)
		if (session.getAttribute("member") != null) {
			// 찜목록 화면이동
			model.addAttribute("content", dir + "mark");
			System.out.println("[SUCCESS] : MypageController/mark - 찜목록 화면 출력");
		}
		return "index";
	}
	
	@RequestMapping("/addmark")
	public String addmark(HttpSession session, Model model, Mark mark) {
		
		// 로그인이 안 되어 있을 경우 로그인 페이지로 이동
		if(session == null) {
			model.addAttribute("content", "/member/login");
		}
		
		return "";
	}
}

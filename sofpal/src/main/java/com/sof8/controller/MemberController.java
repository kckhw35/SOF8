package com.sof8.controller;

import java.util.HashMap;
import java.util.Random;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sof8.dto.Mail;
import com.sof8.dto.Member;
import com.sof8.service.EmailService;
import com.sof8.service.MemberService;

@Controller
@RequestMapping("/member")
public class MemberController {

	@Autowired
	MemberService service;

	@Autowired
	EmailService eservice;

	String dir = "member/";

	// 127.0.0.1/member/account
	@RequestMapping("/account")
	public String account(HttpSession session, Model model, Member member) {
		if (!checkNullSession(session))
			return "redirect:/";
		// 회원가입폼 출력
		model.addAttribute("content", dir + "account");
		System.out.println("[SUCCESS] : MemberController/account - 회원가입폼");
		return "index";
	}

	// 127.0.0.1/member/accountok
	@RequestMapping("/accountok")
	public String accountok(Model model, Member member) {
		try {
			// 회원가입 기능 실행
			service.register(member);
			// 회원가입 성공화면 출력
			model.addAttribute("content", dir + "accountok");
			// 성공화면에 출력할 이름
			model.addAttribute("name", member.getName());
			System.out.println("[SUCCESS] : MemberController/accountok - 회원가입 성공");
			// throw new Exception();

		} catch (Exception e) {
			// 회원가입 실패화면 출력
			model.addAttribute("content", dir + "accountfail");

			System.out.println("[ERROR] : MemberController/accountok - 회원가입 실패");
			e.printStackTrace();
		}
		return "index";
	}

	// 127.0.0.1/checkid
	@ResponseBody
	@RequestMapping("/checkid")
	public Object checkid(String user_id) {
		int result = 0;
		try {
			// 가입된 아이디 검색
			Member member = service.get(user_id);

			// 가입된 아이디 존재함
			if (member != null)
				result = 1;

			System.out.println("[SUCCESS] : MemberController/checkid");
		} catch (Exception e) {
			System.out.println("[ERROR] : MemberController/checkid");
			e.printStackTrace();
		}
		return result;
	}

	// 127.0.0.1/member/login
	@RequestMapping("/login")
	public String login(HttpSession session, Model model, Member member) {
		// 세션이 널이 아니라면(로그인 중이라면)
		if (!checkNullSession(session))
			return "redirect:/";
		else {
			model.addAttribute("content", dir + "login");
			System.out.println("[SUCCESS] : MemberController/login - 로그인 화면출력");
			return "index";
		}
	}

	// 127.0.0.1/member/loginok
	@ResponseBody
	@RequestMapping("/loginok")
	public String loginok(HttpSession session, Member member) {
		String result = "1";
		System.out.println("Member: " + member);
		try {
			// 가입된 아이디 조회
			Member m = service.get(member.getUser_id());
			System.out.println("M : " + m);
			// 가입된 아이디라면
			if (m != null) {
				// 가입된 아이디가 활동가능하다면
				if (m.getEnable() == true) {
					// 가입된 아이디의 비밀번호가 일치한다면
					if (m.getPwd().equals(member.getPwd())) {
						// 세션에 로그인 유저정보 저장
						session.setAttribute("member", m);
						// 홈 화면으로 이동
						System.out.println("[SUCCESS] : MemberController/loginok - 로그인 성공");
						return "redirect:/";
						// 가입된 아이디의 비밀번호가 불일치한다면
					} else {
						result = "0";
						System.out.println("[ERROR] : MemberController/loginok - 비밀번호 로그인 실패");
					}
					// 가입된 아이디가 활동불가능하다면
				} else {
					result = "-1";
				}
				// 미가입된 아이디라면
			} else {
				result = "0";
				System.out.println("[ERROR] : MemberController/loginok - 아이디 로그인 실패");
			}
		} catch (Exception e) {
			System.out.println("[ERROR] : MemberController/loginok - 로그인 실패");
			e.printStackTrace();
		}
		return result;
	}

	// 127.0.0.1/member/logout
	@RequestMapping("/logout")
	public String logout(HttpSession session, Model model) {
		// 회원 세션이 존재할 시 세션을 삭제하여 로그아웃
		if (session.getAttribute("member") != null)
			session.removeAttribute("member");

		model.addAttribute("content", null);
		System.out.println("[SUCCESS] : MemberController/logout - 로그아웃 성공");

		return "redirect:/";
	}

	// 127.0.0.1/member/find_id
	@GetMapping("/find_id")
	public String find_id(HttpSession session, Model model, Member member) {
		// 세션이 널이 아니라면(로그인 중이라면)
		if (!checkNullSession(session))
			return "redirect:/";
		else {
			model.addAttribute("content", dir + "find_id");
			System.out.println("[SUCCESS] : MemberController/find_id - 아이디찾기 화면출력");
			return "index";
		}
	}

	// 127.0.0.1/find_id
	@PostMapping("/found_id")
	public Object found_id(HttpSession session, Model model, Member member) {
		// 세션이 널이 아니라면(로그인 중이라면)
		if (!checkNullSession(session))
			return "redirect:/";
		else {
			String error = null;
			Member m = null;
			// 이름과 이메일을 입력받았다면
			if (member.getName() != "" && member.getEmail() != "") {
				try {
					// 이름과 이메일이 일치하는 아이디 찾기
					m = service.getId(member);

					// 찾은 아이디가 있다면
					if (m != null) {
						// 찾은 아이디의 회원정보를 모델에 저장
						model.addAttribute("member", m);
						System.out.println("model: " + model);
						System.out.println("[SUCCESS] : MemberController/found_id - 아이디 찾기 성공");

						// 찾은 아이디가 없다면
					} else {
						error = "이름 또는 이메일을 잘못 입력하셨습니다.";
					}
				} catch (Exception e) {
					e.printStackTrace();
					System.out.println("[ERROR] : MemberController/found_id - 아이디 찾기 실패");
					error = "이름 또는 이메일을 잘못 입력하셨습니다.";
				}
			}
			model.addAttribute("error", error);
			return "member/find_id :: #form_findid";
		}
	}

	// 127.0.0.1/member/find_password
	@RequestMapping("/find_password")
	public String find_password(HttpSession session, Model model, Member member) {
		// 세션이 널이 아니라면(로그인 중이라면)
		if (!checkNullSession(session))
			return "redirect:/";
		else {
			model.addAttribute("content", dir + "find_password");
			System.out.println("[SUCCESS] : MemberController/find_password - 비밀번호찾기 화면출력");
			return "index";
		}

	}

	// 127.0.0.1/member/user_auth
	@RequestMapping("/user_auth")
	public String user_auth(HttpSession session, Model model, Member member) {
		// 세션이 널이 아니라면(로그인 중이라면)
		if (!checkNullSession(session))
			return "redirect:/";
		else {
			try {
				// 아이디와 일치하는 회원정보 저장
				Member m = service.get(member.getUser_id());
				System.out.println("member: " + member); 
				System.out.println("m: " + m);
				model.addAttribute("name", m.getName());
				model.addAttribute("email", m.getEmail());
				model.addAttribute("member", m);
				model.addAttribute("content", dir + "user_auth");
				System.out.println("[SUCCESS] : MemberController/user_auth - 비밀번호찾기 이메일확인 화면출력");
			} catch (Exception e) {
				e.printStackTrace();
				System.out.println("[ERROR] : MemberController/user_auth - 없는 아이디");
			}
			return "index";
		}
	}

	// 127.0.0.1/member/send_authemail
	@ResponseBody
	@RequestMapping("/send_authemail")
	public Mail send_authemail(HttpSession session, Model model, Member member, Mail mail) {
		Mail result = null;
		try {
			// 본인인증을 확인하기 위한 이메일 저장
			String name = member.getName();
			String to = member.getEmail();
			System.out.println("name: " + member.getName());
			System.out.println("to: " + member.getEmail());
			//
			if (!"".equals(to) && to != null) {

				// 6자리(111111~999999)의 난수 인증번호 생성
				Random r = new Random();
				String auth = String.valueOf(r.nextInt(888888) + 111111);

				// templete에 넘길 값 저장
				HashMap<String, String> values = new HashMap<String, String>();
				values.put("name", name);
				values.put("auth", auth);

				// 전송할 이메일 데이터 셋팅
				mail.setTo(to);
				mail.setSubject("[SOF8] 비밀번호찾기 인증번호를 안내해드립니다.");
				mail.setTemplate("/mail-templates/auth");
				mail.setValues(values);

				// 이메일 전송
				eservice.sendMail(mail);
				model.addAttribute("name", name);
				model.addAttribute("email", to);
				model.addAttribute("auth", auth);
				result = mail;
				System.out.println("[SUCCESS] : MemberController/send_authemail - 인증번호 메일 전송 성공");
			} else {
				System.out.println("[ERROR] : MemberController/send_authemail - 이메일 받아오기 실패");
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("[ERROR] : MemberController/send_authemail - 인증번호 메일 전송 실패");
		}
		return result;
	}

	// 127.0.0.1/member/user_authok
	@RequestMapping("/user_authok")
	public String user_authok(HttpSession session, Model model) {
		// 세션이 널이 아니라면(로그인 중이라면)
		if (!checkNullSession(session))
			return "redirect:/";
		else {
			model.addAttribute("content", dir + "user_authok");
			System.out.println("[SUCCESS] : MemberController/user_authok - 비밀번호찾기 새로운 비밀번호 화면출력");
			return "index";
		}
	}	
	
	// 127.0.0.1/member/reset_password
	@RequestMapping("/reset_password")
	public String reset_password(HttpSession session, Model model) {
		// 세션이 널이 아니라면(로그인 중이라면)
		if (!checkNullSession(session))
			return "redirect:/";
		else {
			model.addAttribute("content", dir + "reset_password");
			System.out.println("[SUCCESS] : MemberController/reset_password - 비밀번호찾기 새로운 비밀번호 화면출력");
			return "index";
		}
	}

	// 127.0.0.1/member/mypage
	@RequestMapping("/mypage")
	public String mypage(HttpSession session, Model model) {
		// 세션이 널 이라면(로그인을 안했다면)
		if (checkNullSession(session))
			return "redirect:/";
		else {
			// 세션 아이디를 다운캐스팅하여 Member 변수에 초기화
			Member user = (Member) session.getAttribute("login_user");
			try {
				// 회원 정보 로딩
				user = service.get(user.getUser_id());
				System.out.println("user: " + user);
				model.addAttribute("user", user);
				model.addAttribute("content", dir + "mypage");
				System.out.println("[SUCCESS] : MemberController/mypage - 마이페이지 화면출력");

			} catch (Exception e) {
				e.printStackTrace();
			}
			return "index";
		}
	}

	// 로그인 중인지 유무 확인 메소드
	public Boolean checkNullSession(HttpSession session) {
		if (session.getAttribute("member") == null || session.getAttribute("admin") == null) {
			return true;
		}
		return false;
	}
}

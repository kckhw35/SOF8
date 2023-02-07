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
import com.sof8.frame.CryptoUtil;
import com.sof8.frame.MaskingUtil;
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

		// 로그인 중이 아니라면
		if (checkNullSession(session)) {

			// 회원가입폼 출력
			model.addAttribute("content", dir + "account");

			return "index";

		} else {

			return "redirect:/";

		}

	}

	// 127.0.0.1/member/accountok
	@RequestMapping("/accountok")
	public String accountok(Model model, Member member, Mail mail) {

		try {

			// 가입할 비밀번호 암호화
			String encryptPwd = CryptoUtil.sha512(member.getPwd());
			member.setPwd(encryptPwd);
			
			// 회원가입 기능 실행
			service.register(member);

			// templete에 넘길 값 저장
			HashMap<String, String> values = new HashMap<String, String>();
			values.put("name", member.getName());
			values.put("user_id", member.getUser_id());

			// 전송할 이메일 데이터 셋팅
			mail.setTo(member.getEmail());
			mail.setSubject("[SOF8] " + member.getName() + " 님의 회원가입을 축하드립니다.");
			mail.setTemplate("/mail-templates/welcome");
			mail.setValues(values);

			// 이메일 전송
			eservice.sendMail(mail);

			// 회원가입 성공화면 출력
			model.addAttribute("content", dir + "accountok");

			// 성공화면에 출력할 이름
			model.addAttribute("name", member.getName());

		} catch (Exception e) {

			// 회원가입 실패화면 출력
			model.addAttribute("content", dir + "accountfail");

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

		} catch (Exception e) {

			e.printStackTrace();

		}

		return result;

	}

	// 127.0.0.1/member/login
	@RequestMapping("/login")
	public String login(HttpSession session, Model model, Member member) {

		// 로그인 중이 아니라면
		if (checkNullSession(session)) {

			model.addAttribute("content", dir + "login");

			return "index";

		} else {

			return "redirect:/";

		}

	}

	// 127.0.0.1/member/loginok
	@ResponseBody
	@RequestMapping("/loginok")
	public String loginok(HttpSession session, Member member) {

		String result = "1";

		try {

			// 입력한 비밀번호 암호화
			String encryptPwd = CryptoUtil.sha512(member.getPwd());

			// 가입된 아이디 조회
			Member m = service.get(member.getUser_id());

			// 가입된 아이디라면
			if (m != null) {

				// 가입된 아이디가 활동가능하다면
				if (m.getEnable() == true) {

					// 가입된 아이디의 비밀번호가 일치한다면
					if (m.getPwd().equals(encryptPwd)) {

						// 세션에 로그인 유저정보 저장
						session.setAttribute("member", m);

						// 홈 화면으로 이동
						return "redirect:/";

						// 가입된 아이디의 비밀번호가 불일치한다면
					} else {

						result = "0";

					}

					// 가입된 아이디가 활동불가능하다면
				} else {

					result = "-1";

				}

				// 미가입된 아이디라면
			} else {

				result = "0";

			}

		} catch (Exception e) {

			e.printStackTrace();

		}

		return result;
	}

	// 127.0.0.1/member/logout
	@RequestMapping("/logout")
	public String logout(HttpSession session, Model model) {

		// 회원 세션이 존재할 시 세션을 삭제하여 로그아웃
		if (!checkNullSession(session))
			session.removeAttribute("member");

		model.addAttribute("content", null);

		return "redirect:/";

	}

	// 127.0.0.1/member/find_id
	@GetMapping("/find_id")
	public String find_id(HttpSession session, Model model, Member member) {

		// 로그인 중이 아니라면
		if (checkNullSession(session)) {

			model.addAttribute("content", dir + "find_id");

			return "index";

		} else {

			return "redirect:/";

		}
	}

	// 127.0.0.1/find_id
	@PostMapping("/found_id")
	public Object found_id(HttpSession session, Model model, Member member) {

		// 로그인 중이 아니라면
		if (checkNullSession(session)) {

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
						model.addAttribute("masking", MaskingUtil.getMaskedId(m.getUser_id()));
						model.addAttribute("member", m);

						// 찾은 아이디가 없다면
					} else {

						error = "이름 또는 이메일을 잘못 입력하셨습니다.";
					}

				} catch (Exception e) {

					e.printStackTrace();
					error = "이름 또는 이메일을 잘못 입력하셨습니다.";

				}

			}

			model.addAttribute("error", error);

			return "member/find_id :: #form_findid";

		} else {

			return "redirect:/";

		}
	}

	// 127.0.0.1/member/find_password
	@RequestMapping("/find_password")
	public String find_password(HttpSession session, Model model, Member member) {

		// 로그인 중이 아니라면
		if (checkNullSession(session)) {

			model.addAttribute("content", dir + "find_password");

			return "index";

		} else {

			return "redirect:/";
		}

	}

	// 127.0.0.1/member/user_auth
	@RequestMapping("/user_auth")
	public String user_auth(HttpSession session, Model model, Member member) {

		// 로그인 중이 아니라면
		if (checkNullSession(session)) {

			try {

				session.setAttribute("id", member.getUser_id());

				// 아이디와 일치하는 회원정보 저장
				Member m = service.get(member.getUser_id());
				model.addAttribute("name", m.getName());
				model.addAttribute("email", m.getEmail());
				model.addAttribute("masking", MaskingUtil.getMaskedEmail(m.getEmail()));
				model.addAttribute("member", m);
				model.addAttribute("content", dir + "user_auth");

			} catch (Exception e) {

				e.printStackTrace();

			}

			return "index";

		} else {

			return "redirect:/";

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
				session.setAttribute("name", name);
				session.setAttribute("email", to);
				session.setAttribute("auth", auth);

				result = mail;

			}

		} catch (Exception e) {

			e.printStackTrace();

		}

		return result;

	}

	// 127.0.0.1/member/user_authok
	@RequestMapping("/user_authok")
	public String user_authok(HttpSession session, Model model) {

		// 로그인 중이 아니라면
		if (checkNullSession(session)) {

			model.addAttribute("content", dir + "user_authok");

			return "index";

		} else {

			return "redirect:/";

		}

	}

	// 127.0.0.1/member/reset_password
	@RequestMapping("/reset_password")
	public String reset_password(HttpSession session, Model model) {

		// 로그인 중이 아니라면
		if (checkNullSession(session)) {

			model.addAttribute("content", dir + "reset_password");

			return "index";

		} else {

			return "redirect:/";
		}

	}

	// 127.0.0.1/member/reset_passwordok
	@RequestMapping("/reset_passwordok")
	public String reset_passwordok(HttpSession session, Model model, Member member, String pwd) {

		// 로그인 중이 아니라면
		if (checkNullSession(session)) {

			try {

				String user_id = (String) session.getAttribute("id");

				member.setUser_id(user_id);
				member.setPwd(pwd);

				service.modifyPwd(member);

				return "redirect:/";

			} catch (Exception e) {

				e.printStackTrace();

			}

			return "index";
		} else {

			return "redirect:/";
		}

	}

	// 127.0.0.1/member/mypage
	@RequestMapping("/mypage")
	public String mypage(HttpSession session, Model model) {

		// 로그인 중이라면
		if (!checkNullSession(session)) {

			// 세션 아이디를 다운캐스팅하여 Member 변수에 초기화
			Member user = (Member) session.getAttribute("login_user");

			try {

				// 회원 정보 로딩
				user = service.get(user.getUser_id());
				model.addAttribute("user", user);
				model.addAttribute("content", dir + "mypage");

			} catch (Exception e) {

				e.printStackTrace();

			}

			return "index";

		} else {

			return "redirect:/";
		}
	}

	// 로그인 중인지 유무 확인 메소드
	private Boolean checkNullSession(HttpSession session) {

		if (session.getAttribute("member") == null) {

			return true;

		} else {

			return false;

		}

	}

}

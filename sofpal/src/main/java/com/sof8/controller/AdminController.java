package com.sof8.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sof8.dto.Admin;
import com.sof8.dto.Member;
import com.sof8.dto.Paging;
import com.sof8.dto.Product;
import com.sof8.frame.CryptoUtil;
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
	public String login(HttpSession session, Model model, Admin Admin) {

		if (checkNullSession(session)) {

			model.addAttribute("content", dir + "login");

			return "index";

		} else {

			return "redirect:/";

		}

	}

	// 로그인 기능
	// 127.0.0.1/admin/loginok
	@ResponseBody
	@RequestMapping("/loginok")
	public String loginok(HttpSession session, Admin admin) {

		String result = "1";

		try {

			// 가입된 아이디 조회
			Admin a = aservice.get(admin.getAdmin_id());

			// 가입된 아이디라면
			if (a != null) {
				
				// 입력한 비밀번호 암호화
				String encryptPwd = CryptoUtil.sha512(admin.getAdmin_pwd());
				
				// 가입된 아이디의 비밀번호가 일치한다면
				if (a.getAdmin_pwd().equals(encryptPwd)) {

					// 세션에 로그인 유저정보 저장
					session.setAttribute("admin", a);

					// 홈 화면으로 이동
					return "redirect:/";

				} else {

					// error = "아이디 또는 비밀번호가 일치하지 않습니다.";
					result = "0";

				}

			} else {

				// error = "아이디 또는 비밀번호가 일치하지 않습니다.";
				result = "0";

			}

		} catch (Exception e) {

			e.printStackTrace();

		}

		return result;
	}

	// 관리자 로그아웃
	// 127.0.0.1/admin/logout
	@RequestMapping("/logout")
	public String logout(HttpSession session, Model model) {

		// 세션이 존재할 시 세션을 삭제하여 로그아웃
		if (!checkNullSession(session))
			session.removeAttribute("admin");

		return "redirect:/";
	}

	// 관리자 회원가입 화면
	// 127.0.0.1/admin/account
	@RequestMapping("/account")
	public String admin(HttpSession session, Model model, Admin Admin) {

		if (checkNullSession(session)) {

			model.addAttribute("content", dir + "account");

			return "index";

		} else {

			return "redirect:/";
		}

	}

	// 관리자 회원가입 기능
	// 127.0.0.1/admin/accountok
	@RequestMapping("/accountok")
	public String accountok(Model model, Admin admin) {

		try {
			
			// 가입할 비밀전호 암호화
			String encryptPwd = CryptoUtil.sha512(admin.getAdmin_pwd());
			admin.setAdmin_pwd(encryptPwd);
			
			// 회원가입 기능 실행
			aservice.register(admin);

			// 회원가입 성공화면 출력
			model.addAttribute("content", dir + "accountok");

			// 성공화면에 출력할 이름
			model.addAttribute("name", admin.getAdmin_name());

		} catch (Exception e) {

			// 회원가입 실패화면 출력
			model.addAttribute("content", dir + "accountfail");

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

		} catch (Exception e) {

			e.printStackTrace();

		}

		return result;

	}

	// 회원 리스트 화면
	// 127.0.0.1/admin/memberlist
	@RequestMapping("/memberlist")
	public String memberlist(HttpSession session, Model model, @RequestParam(defaultValue = "1") int page,
			@RequestParam(required = false) String keyword,
			@RequestParam(value = "type", defaultValue = "user_id") String type) {

		if (!checkNullSession(session)) {

			try {
				
				// 검색한 데이터의 총 개수
				int totalRow = mservice.getTotal(keyword, type);
				// 페이징을 위한 데이터 입력
				Paging paging = new Paging(6, 5, totalRow, page, keyword, type);
				// 페이징 후 데이터 검색
				List<Member> members = mservice.getList(paging);

				model.addAttribute("members", members);
				model.addAttribute("page", page);
				model.addAttribute("paging", paging);
				model.addAttribute("content", dir + "memberlist");

			} catch (Exception e) {

				e.printStackTrace();

			}

			return "index";

		} else {

			return "redirect:/";

		}

	}

	// 회원 활성화
	// 127.0.0.1/admin/memberenable
	@ResponseBody
	@RequestMapping("/memberenable")
	public Boolean memberenable(HttpSession session, @RequestParam(value = "arrlist[]") List<String> arrlist) {

		Boolean result = false;

		if (!checkNullSession(session)) {

			for (String user_id : arrlist) {

				try {

					mservice.modifyEnable(user_id);
					result = true;

				} catch (Exception e) {

					e.printStackTrace();

				}

			}

		}

		return result;

	}

	// 회원 비활성화
	// 127.0.0.1/admin/memberdisable
	@ResponseBody
	@RequestMapping("/memberdisable")
	public Boolean memberdisable(HttpSession session, @RequestParam(value = "arrlist[]") List<String> arrlist) {

		Boolean result = false;

		if (!checkNullSession(session)) {

			for (String user_id : arrlist) {

				try {

					mservice.modifyDisable(user_id);
					result = true;

				} catch (Exception e) {

					e.printStackTrace();

				}

			}

		}

		return result;
	}

	// 회원 상세
	// 127.0.0.1/admin/memberedit
	@RequestMapping("/memberedit/{user_id}")
	public String memberedit(HttpSession session, Model model, Member member, @PathVariable String user_id) {

		// 세션이 있다면(로그인 중이라면)
		if (!checkNullSession(session)) {

			try {

				// 회원 정보 로딩
				Member m = mservice.get(user_id);

				model.addAttribute("member", m);
				model.addAttribute("content", dir + "memberedit");

				return "index";

			} catch (Exception e) {

				e.printStackTrace();

			}

		}

		return "redirect:/";

	}

	// 회원정보 수정
	// 127.0.0.1/admin/membereditok
	@RequestMapping("/membereditok")
	public String membereditok(HttpSession session, Member member) {

		// 세션이 있다면(로그인 중이라면)
		if (!checkNullSession(session)) {

			try {

				// 회원정보 수정
				mservice.modify(member);

			} catch (Exception e) {

				e.printStackTrace();

			}

			return "redirect:/admin/memberlist";

		} else {

			return "redirect:/";

		}

	}

	// 회원삭제
	// 127.0.0.1/admin/memberdelete
	@ResponseBody
	@RequestMapping("/memberdelete")
	public Boolean memberdelete(HttpSession session, @RequestParam(value = "arrlist[]") List<String> arrlist) {

		Boolean result = false;

		if (!checkNullSession(session)) {

			for (String user_id : arrlist) {

				try {

					mservice.remove(user_id);
					result = true;

				} catch (Exception e) {

					e.printStackTrace();

				}

			}

		}

		return result;

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
	
	@RequestMapping("/console")
	public String console() {

		return "/admin/console";
	}
	
	// 로그인 중인지 유무 확인 메소드
	private Boolean checkNullSession(HttpSession session) {

		if (session.getAttribute("admin") == null)
			return true;
		else
			return false;

	}
	
}

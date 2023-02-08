package com.sof8.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sof8.dto.Mark;
import com.sof8.dto.Member;
import com.sof8.dto.Paging;
import com.sof8.dto.Qna;
import com.sof8.service.MarkService;
import com.sof8.service.MemberService;
import com.sof8.service.OrderService;
import com.sof8.service.QnaService;
import com.sof8.service.ReplyService;

@Controller
@RequestMapping("/mypage")
public class MypageController {

	@Autowired
	MemberService service;

	@Autowired
	MarkService mservice;
	
	@Autowired
	OrderService oservice;
	/* MyPage 1:1문의내역 확인 - Park */
	@Autowired
	QnaService qnaService;
	@Autowired
	ReplyService replyService;

	
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

					service.modifyDisable(m.getUser_id());
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
	
	// 마이페이지 - 주문목록
	// 127.0.0.1/mypage/orderlist
	@RequestMapping("/orderlist")
	public String orderlist(HttpSession session, Model model, @RequestParam(defaultValue = "1") int page) {

		Member member =  (Member) session.getAttribute("member");

		// 세션이 있다면(로그인 중이라면)
		if (session.getAttribute("member") != null) {
			try {
				String keyword = member.getUser_id();
				String type= "user_id";
				
				// 검색한 아이디의 총 찜 수
				int totalRow = oservice.getTotal(keyword);
				Paging paging = null;
				List<Mark> marks= null;
				do {
					// 페이징을 위한 데이터 입력
					paging = new Paging(5,5,totalRow, page, keyword, type);
					// 페이징 후 데이터 검색 
					marks = mservice.getList(paging);
					--page;
				}while (marks.isEmpty());

				model.addAttribute("marks", marks);
				model.addAttribute("page", page);
				model.addAttribute("paging", paging);				
				
				System.out.println("marks: " + marks);
				System.out.println("page: " + page);
				System.out.println("paging: " + paging);
				System.out.println("[SUCCESS] : MypageController/mark - 찜목록 검색 성공");
			} catch (Exception e) {
				e.printStackTrace();
				System.out.println("[ERROR] : MypageController/mark - 찜목록 검색 실패");
			}
			
			// 찜목록 화면이동
			model.addAttribute("content", dir + "orderlist");
			System.out.println("[SUCCESS] : MypageController/orderlist - 주문목록 화면 출력");
		}
		return "index";
	}
	
	// 마이페이지 - 찜 목록
	// 127.0.0.1/mypage/mark
	@RequestMapping("/mark")
	public String mark(HttpSession session, Model model,  
			@RequestParam(defaultValue = "1") int page) {
		
		Member member =  (Member) session.getAttribute("member");
		// 세션이 있다면(로그인 중이라면)
		if (session.getAttribute("member") != null) {
		
			try {
				String keyword = member.getUser_id();
				String type= "user_id";
				
				// 검색한 아이디의 총 찜 수
				int totalRow = mservice.getTotal(keyword);
				Paging paging = null;
				List<Mark> marks= null;
				do {
					// 페이징을 위한 데이터 입력
					paging = new Paging(5,5,totalRow, page, keyword, type);
					// 페이징 후 데이터 검색 
					marks = mservice.getList(paging);
					--page;
				}while (marks.isEmpty());

				model.addAttribute("marks", marks);
				model.addAttribute("page", page);
				model.addAttribute("paging", paging);				
				
				System.out.println("marks: " + marks);
				System.out.println("page: " + page);
				System.out.println("paging: " + paging);
				System.out.println("[SUCCESS] : MypageController/mark - 찜목록 검색 성공");
			} catch (Exception e) {
				e.printStackTrace();
				System.out.println("[ERROR] : MypageController/mark - 찜목록 검색 실패");
			}
			
			// 찜목록 화면이동
			model.addAttribute("content", dir + "mark");
			System.out.println("[SUCCESS] : MypageController/mark - 찜목록 화면 출력");
			return "index";
		} else {
			return "redirect:/";
		}
	}
	
	// 찜 추가
	@ResponseBody
	@PostMapping("/addmark")
	public Map<String,Integer> addmark(HttpSession session, Model model, @RequestParam(value="p_id") int p_id) {
		Mark ma = new Mark();
		Member m = (Member)session.getAttribute("member");
		Map<String, Integer> mark = new HashMap<String, Integer>();
		
		// 로그인 상태 확인
		if (session.getAttribute("member") == null) {
			mark.put("m_id",-1);
			return mark;
		}else {
			try {
				ma.setUser_id(m.getUser_id());
				ma.setP_id(p_id);
				mservice.register(ma);
				mark.put("m_id", ma.getM_id());
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		return mark;
	}
	
	// 찜삭제
	// 127.0.0.1/mypage/markdelete
	@ResponseBody
	@RequestMapping("/markdelete")
	public Boolean markdelete(HttpSession session, @RequestParam(value = "arrlist[]") List<Integer> arrlist) {

		Boolean result = false;

		if (session.getAttribute("member")!= null) {
			for (int m_id : arrlist) {
				try {
					mservice.remove(m_id);
					result = true;
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		return result;
	}	

	
	/* mypage에서 1:1 문의 내역 확인 - Park*/
	// 127.0.0.1/mypage/qna
	@RequestMapping("/qna")
	public String myQna(HttpSession session, Model model,
			@RequestParam(defaultValue = "1") int page) {
		
		Member member = (Member)session.getAttribute("member");
		
		/* 예외처리 로직: 로그인이 안되어있으면, Home으로 이동 */
		if (member == null) {
			return "redirect:/member/login";
		}
		
		String keyword = member.getUser_id();
		String type = "user_id";
		try {
			
			int totalRow = qnaService.getTotal(keyword, type);
			Paging paging = new Paging(10, 5, totalRow, page, keyword, type);
			List<Qna> qnas = qnaService.getList(paging);
			
			for (Qna qna : qnas) {
				qna.setReply(replyService.getReply(qna.getR_id()));
			}
			
			model.addAttribute("qnas", qnas);
			model.addAttribute("page", page);
			model.addAttribute("paging", paging);
			model.addAttribute("content", dir + "qna");
			
			System.out.println("[SUCCESS] : MypageController/qna - qna 화면 출력");
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("[ERROR] : MypageController/qna - qna 화면 출력");
		}
		
		model.addAttribute("content", dir + "qna");
		
		return "index";
	}
	
	/* mypage에서 1:1 문의 내역 확인 - Park*/
	// 127.0.0.1/mypage/qna/content
	@RequestMapping("/qna/content")
	public String myQnaContent(HttpSession session, Model model,
			@RequestParam("id") int r_id,
			@RequestParam(defaultValue = "1") int page) {
		
		Member member = (Member)session.getAttribute("member");
		
		/* 예외처리 로직: 로그인이 안되어있으면, Home으로 이동 */
		if (member == null) {
			return "redirect:/member/login";
		}
		
		String keyword = member.getUser_id();
		String type = "user_id";
		
		Qna qna = null;
		try {
			int totalRow = qnaService.getTotal(keyword, type);
			Paging paging = new Paging(10, 5, totalRow, page, keyword, type);
			model.addAttribute("paging", paging);

			
			qna = qnaService.get(r_id);
			qna.setReply(replyService.getReply(qna.getR_id()));
			System.out.println("/qna/content - succes");
			
		} catch (Exception e) {
			System.out.println("/qna/content - Fail");
			e.printStackTrace();
		}
		
		model.addAttribute("qna", qna);
		model.addAttribute("content", dir + "qna-content");
		
		return "index";
	}
	
	/* mypage에서 1:1 문의 작성 - Park*/
	// 127.0.0.1/mypage/qna/write
	@RequestMapping("/qna/write")
	public String myQnaWrite(HttpSession session, Model model) {
		
		Member member = (Member)session.getAttribute("member");
		
		/* 예외처리 로직: 로그인이 안되어있으면, login page로 이동 */
		if (member == null) {
			return "redirect:/member/login";
		}
		
		model.addAttribute("member", member);
		model.addAttribute("content", dir + "qna-write");
		
		return "index";
	}
	
	/* mypage에서 1:1 문의 작성한것 저장- Park*/
	// 127.0.0.1/mypage/qna/save
	@RequestMapping("/qna/save")
	public String myQnaWrite(HttpSession session, Model model,
			@RequestParam String title, @RequestParam String content) {
		
		Member member = (Member)session.getAttribute("member");
		
		/* 예외처리 로직: 로그인이 안되어있으면, login page로 이동 */
		if (member == null) {
			return "redirect:/member/login";
		}
		
		Qna qna = new Qna();
		qna.setTitle(title);
		qna.setContent(content);
		qna.setUser_id(member.getUser_id());
		
		try {
			qnaService.register(qna);
			System.out.println("[SUCCESS] : qnaService.register(qna)");
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		model.addAttribute("member", member);
		
		return "redirect:/mypage/qna";
	}
}















package com.sof8.controller;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
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

import com.sof8.dto.Coupon;
import com.sof8.dto.Mark;
import com.sof8.dto.Member;
import com.sof8.dto.Order;
import com.sof8.dto.Paging;
import com.sof8.dto.Product;
import com.sof8.dto.Qna;
import com.sof8.frame.CryptoUtil;
import com.sof8.service.CouponService;
import com.sof8.service.MarkService;
import com.sof8.service.MemberService;
import com.sof8.service.OrderService;
import com.sof8.service.QnaService;
import com.sof8.service.ReplyService;
import com.sof8.service.ReservationService;

@Controller
@RequestMapping("/mypage")
public class MypageController {

	@Autowired
	MemberService service;

	@Autowired
	MarkService mservice;
	
	@Autowired
	CouponService cservice;
	
	@Autowired
	OrderService oservice;
	
	@Autowired
	ReservationService rservice;
	
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

	//
	@ResponseBody
	@RequestMapping("/getEncryptPwd")
	public String getEncryptPwd(String now_pwd) {
		String encryptPwd = null;
		// 가입할 비밀번호 암호화
		try {
			encryptPwd = CryptoUtil.sha512(now_pwd);
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return encryptPwd;
	}
	
	// 127.0.0.1/mypage/editok
	@RequestMapping("/editok")
	public String editok(HttpSession session, Member member, Model model) {
		// 세션이 있다면(로그인 중이라면)
		if (session.getAttribute("member") != null) {
			try {
				// 수정할 비밀번호 암호화
				String encryptPwd = CryptoUtil.sha512(member.getPwd());
				member.setPwd(encryptPwd);
				
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
	@RequestMapping("/cancelok")
	public Boolean cancelok(HttpSession session, Model model, Member member) {
		Boolean result = false;
		System.out.println("member: " + member);

		// 세션이 있다면(로그인 중이라면)
		if (session.getAttribute("member") != null) {
			try {
				Member m = (Member) session.getAttribute("member");
				System.out.println("member: " + member);
				System.out.println("m: " + m);
				String encryptPwd = CryptoUtil.sha512(member.getPwd());
				member.setPwd(encryptPwd);
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
	// 127.0.0.1/mypage/order
	@RequestMapping("/order")
	public String order(HttpSession session, Model model, 
			@RequestParam(defaultValue = "1") int page,
			@RequestParam(defaultValue = "user_id") String type,
			@RequestParam(defaultValue = "") String first, 
			@RequestParam(defaultValue = "") String last) {

		Member member =  (Member) session.getAttribute("member");

		// 세션이 있다면(로그인 중이라면)
		if (session.getAttribute("member") != null) {
			try {
				String keyword = member.getUser_id();
				
				// 검색한 아이디의 총 찜 수
				int totalRow = oservice.getTotal(keyword, type, first, last);
				Paging paging = null;
				List<Order> orders= null;
				
				System.out.println("totalRow: " + totalRow);
				System.out.println("keyword: " + keyword);
				System.out.println("type: " + type);
				System.out.println("first: " + first);
				System.out.println("last: " + last);
				
				if(totalRow>0) {
					do {
						// 페이징을 위한 데이터 입력
						paging = new Paging(5,5,totalRow, page, keyword, type);
						paging.setFirst(first);
						paging.setLast(last);	
						
						// 페이징 후 데이터 검색 
						orders = oservice.getList(paging);
						--page;
					}while (orders.isEmpty());	
				} else {
					paging = new Paging(5,5,totalRow, page, keyword, type);
				}
				
				model.addAttribute("orders", orders);
				model.addAttribute("page", page);
				model.addAttribute("paging", paging);				
				
				System.out.println("orders: " + orders);
				System.out.println("page: " + page);
				System.out.println("paging: " + paging);
				System.out.println("[SUCCESS] : MypageController/order - 주문목록 검색 성공");
			} catch (Exception e) {
				e.printStackTrace();
				System.out.println("[ERROR] : MypageController/order - 주문목록 검색 실패");
			}
			
			// 찜목록 화면이동
			model.addAttribute("content", dir + "order");
			System.out.println("[SUCCESS] : MypageController/order - 주문목록 화면 출력");
		}
		return "index";
	}
	
	// 마이페이지 - 주문취소
	// 127.0.0.1/mypage/cancelOrder
	@ResponseBody
	@RequestMapping("/cancelOrder")
	public Boolean cancelOrder(HttpSession session, Model model, 
			@RequestParam(value = "o_id") int o_id) {
		Boolean result = false;
		System.out.println("o_id: " + o_id);
		try {
			// 주문 상태 변경 - 주문 취소
			oservice.modifyStatus(o_id);
			// 예약 취소
			rservice.remove(o_id);
			result = true;
			System.out.println("[SUCCESS] 주문취소 성공");
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("[ERROR] 주문취소 실패");
		}

		return result;
	}
	
	
	// 마이페이지 - 찜 목록
	// 127.0.0.1/mypage/mark
	@RequestMapping("/mark")
	public String mark(HttpSession session, Model model,  
			@RequestParam(defaultValue = "1") int page,
			@RequestParam(defaultValue = "user_id") String type) {
		
		String[] p_img = null;
		String main_img = null;
		Member member =  (Member) session.getAttribute("member");
		// 세션이 있다면(로그인 중이라면)
		if (session.getAttribute("member") != null) {
		
			try {
				String keyword = member.getUser_id();
				
				// 검색한 아이디의 총 찜 수
				int totalRow = mservice.getTotal(keyword);
				Paging paging = null;
				List<Mark> marks= null;
				if(totalRow>0) {
					do {
						// 페이징을 위한 데이터 입력
						paging = new Paging(5,5,totalRow, page, keyword, type);
						// 페이징 후 데이터 검색 
						marks = mservice.getList(paging);
						--page;
						
						// 상품 메인 이미지
						for(Mark m : marks) {
							main_img = m.getP_img();
							p_img = main_img.split(",");
							main_img = p_img[0];
							m.setP_img(main_img);
						}
					}while (marks.isEmpty());
				} else {
					paging = new Paging(5,5,totalRow, page, keyword, type);
				}


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

	@RequestMapping("/coupon")
	public String coupon(HttpSession session, Model model, 
			@RequestParam(defaultValue = "1") int page,
			@RequestParam(defaultValue = "user_id") String type,
			@RequestParam(defaultValue = "") String first, 
			@RequestParam(defaultValue = "") String last) {
	

		// 변수 선언 및 초기화
		String keyword = null;
		Member member =  (Member) session.getAttribute("member");
		Map<String, Object> map = new HashMap<String, Object>();
		int totalRow = 0;
		Paging paging = null;
		List<Coupon> coupons= null;
		
		// 세션이 있다면(로그인 중이라면)
		if (session.getAttribute("member") != null) {
			try {
				
				keyword = member.getUser_id();
				
				map.put("keyword", keyword);
				map.put("type", type);
				map.put("first", first);
				map.put("last", last);
				
				// 검색한 아이디의 총 찜 수
				totalRow = cservice.getTotal(map);

				System.out.println("keyword: " + keyword);
				System.out.println("type: " + type);
				System.out.println("first: " + first);
				System.out.println("last: " + last);
				System.out.println("totalRow: " + totalRow);

				if(totalRow>0) {
					do {
						// 페이징을 위한 데이터 입력
						paging = new Paging(5,5,totalRow, page, keyword, type);
						paging.setFirst(first);
						paging.setLast(last);	
						
						// 페이징 후 데이터 검색 
						coupons = cservice.getList(paging);
						--page;
					}while (coupons.isEmpty());	
				} else {
					paging = new Paging(5,5,totalRow, page, keyword, type);
				}
				
				model.addAttribute("coupons", coupons);
				model.addAttribute("page", page);
				model.addAttribute("paging", paging);				
				
				System.out.println("coupons: " + coupons);
				System.out.println("page: " + page);
				System.out.println("paging: " + paging);
				System.out.println("[SUCCESS] : MypageController/coupon - 쿠폰목록 검색 성공");
			} catch (Exception e) {
				e.printStackTrace();
				System.out.println("[ERROR] : MypageController/coupon  - 쿠폰목록 검색 실패");
			}
			
			// 쿠폰목록 화면이동
			model.addAttribute("content", dir + "coupon");
			System.out.println("[SUCCESS] : MypageController/coupon - 쿠폰목록 화면 출력");
		}
		return "index";
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
	
	/* mypage에서 1:1 문의 삭제- Park*/
	// 127.0.0.1/mypage/qna/delete
	@RequestMapping("/qna/delete")
	public String myQnaDelete(HttpSession session, Model model,
			@RequestParam int r_id) {
		
		Member member = (Member)session.getAttribute("member");
		
		/* 예외처리 로직: 로그인이 안되어있으면, login page로 이동 */
		if (member == null) {
			return "redirect:/member/login";
		}
		
		Qna qna = null;
		
		try {
			qna = qnaService.get(r_id);
			qna.setEnable(false);
			
			qnaService.modify(qna);
			System.out.println("[SUCCESS] : qnaService.modify(qna)");
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		model.addAttribute("member", member);
		
		return "redirect:/mypage/qna";
	}
}

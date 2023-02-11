package com.sof8.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.sof8.dto.Member;
import com.sof8.dto.Order;
import com.sof8.dto.Paging;
import com.sof8.dto.Review;
import com.sof8.service.MemberService;
import com.sof8.service.OrderService;
import com.sof8.service.ReviewService;

@Controller
@RequestMapping("/review")
public class ReviewController {

	@Autowired
	MemberService service;
	@Autowired
	OrderService oservice;
	@Autowired
	ReviewService reviewService;

	String dir = "review/";
	
	// 마이페이지 - 주문목록
	// 127.0.0.1/review
	@RequestMapping("/record")
	public String reviewRecord(HttpSession session, Model model, 
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
						paging = new Paging(totalRow,5,totalRow, page, keyword, type);
						paging.setFirst(first);
						paging.setLast(last);	
						
						// 페이징 후 데이터 검색 
						orders = oservice.getList(paging);
						--page;
					}while (orders.isEmpty());	
				} else {
					paging = new Paging(5,5,totalRow, page, keyword, type);
				}
				
				//해당 주문에 리뷰를 작성했는지 아니면 작성할 수 있는지 확인
				for (Order order : orders) {
					order.setReview(reviewService.getReviewByDetailId(order.getDe_id()));
					System.out.println(order);
				}
				
				
				model.addAttribute("orders", orders);
				model.addAttribute("page", page);
				model.addAttribute("paging", paging);
				model.addAttribute("member", member);
				
				System.out.println("orders: " + orders);
				System.out.println("page: " + page);
				System.out.println("paging: " + paging);
				System.out.println("[SUCCESS] : ReviewController/order - 주문목록 검색 성공");
			} catch (Exception e) {
				e.printStackTrace();
				System.out.println("[ERROR] : ReviewController/order - 주문목록 검색 실패");
			}
			
			model.addAttribute("content", dir + "review-record");
			System.out.println("[SUCCESS] : ReviewController/order - 리뷰작성 가능 목록 화면 출력");
		}
		return "index";
	}
	
	@RequestMapping("/candidate")
	public String reviewCandidate(HttpSession session, Model model, 
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
						paging = new Paging(totalRow,5,totalRow, page, keyword, type);
						paging.setFirst(first);
						paging.setLast(last);	
						
						// 페이징 후 데이터 검색 
						orders = oservice.getList(paging);
						--page;
					}while (orders.isEmpty());	
				} else {
					paging = new Paging(5,5,totalRow, page, keyword, type);
				}
				
				//해당 주문에 리뷰를 작성했는지 아니면 작성할 수 있는지 확인
				for (Order order : orders) {
					order.setReview(reviewService.getReviewByDetailId(order.getDe_id()));
					System.out.println(order);
				}
				
				
				model.addAttribute("orders", orders);
				model.addAttribute("page", page);
				model.addAttribute("paging", paging);
				model.addAttribute("member", member);
				
				System.out.println("orders: " + orders);
				System.out.println("page: " + page);
				System.out.println("paging: " + paging);
				System.out.println("[SUCCESS] : ReviewController/order - 주문목록 검색 성공");
			} catch (Exception e) {
				e.printStackTrace();
				System.out.println("[ERROR] : ReviewController/order - 주문목록 검색 실패");
			}
			
			model.addAttribute("content", dir + "review-candidate");
			System.out.println("[SUCCESS] : ReviewController/order - 리뷰작성 가능 목록 화면 출력");
		}
		return "index";
	}
	
	
	//리뷰 작성
	@RequestMapping("/write")
	public String reviewWrite(HttpSession session, Model model,
			@RequestParam("de_id") int de_id,
			@RequestParam("user_id") String user_id) {
		
		Member member =  (Member) session.getAttribute("member");
		
		//예외처리 로직: 로그인 안되어있으면 로그인 화면으로 이동
		if (member == null) {
			return "redirect:/member/login";
		}
		
		//본인이 아니라면
		if(!user_id.equals(member.getUser_id())) {
			return "redirect:/";
		}
		
		model.addAttribute("de_id", de_id);
		model.addAttribute("content", dir + "review-write");
		
		return "index";
	}
	
	@RequestMapping("/save")
	public String reviewWrite(HttpSession session, Model model,
			@RequestParam("user_id") String user_id,
			@ModelAttribute Review review) {
		
		Member member =  (Member) session.getAttribute("member");
		
		//예외처리 로직: 로그인 안되어있으면 로그인 화면으로 이동
		if (member == null) {
			return "redirect:/member/login";
		}
		
		//본인이 아니라면 홈페이지로 이동
		if(!user_id.equals(member.getUser_id())) {
			return "redirect:/";
		}
		
		return "index";
	}
}

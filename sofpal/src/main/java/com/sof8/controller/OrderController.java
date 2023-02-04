package com.sof8.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.sof8.dto.Cart;
import com.sof8.dto.Member;
import com.sof8.dto.OrderForm;
import com.sof8.dto.Schedule;
import com.sof8.service.CartService;
import com.sof8.service.DeliveryService;
import com.sof8.service.DetailOrderService;
import com.sof8.service.MemberService;
import com.sof8.service.OrderFormService;
import com.sof8.service.OrderService;
import com.sof8.service.ProductService;
import com.sof8.service.ReservationService;
import com.sof8.service.ScheduleService;

@Controller
@RequestMapping("/order")
public class OrderController {

	String dir = "order/";

	@Autowired
	ProductService pservice;
	
	@Autowired
	OrderService oservice;
	
	@Autowired
	DetailOrderService dservice;
	
	@Autowired
	ReservationService rservice;
	
	@Autowired
	MemberService mservice;
	
	@Autowired
	OrderFormService ofservice;
	
	@Autowired
	CartService cservice;
	
	@Autowired
	DeliveryService deservice;
	
	@Autowired
	ScheduleService sservice;
	
	// 장바구니 보기
	@RequestMapping("/cart")
	public String cart(HttpSession session, Model model, Cart c) {
		List<Cart> clist = null;
		Member  m = (Member)session.getAttribute("member");
		
		if (session.getAttribute("member") == null) {
			return "redirect:/member/login";
		}else {
			try {
				clist = cservice.selectusercart(m.getUser_id());
				
				model.addAttribute("m", m);
				model.addAttribute("clist", clist);
				model.addAttribute("content", dir + "cart");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		return "index";
	}
	
	// 장바구니 추가
		@RequestMapping("/addcart")
		public String addcart(HttpSession session, Model model, int p_id, int quantity) {
			Cart c = new Cart();
			Member  m = (Member)session.getAttribute("member");
			
			// 로그인 상태 확인
			if (session.getAttribute("member") == null) {
				return "redirect:/member/login";
			}else {
				try {
					c.setUser_id(m.getUser_id()); 
					c.setP_id(p_id);
					c.setC_cnt(quantity); 
					cservice.register(c);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}

			return "redirect:/product/productdetail?p_id="+p_id;
		}
		
		// 장바구니 삭제
		@RequestMapping("/deletecart")
		public String deletecart(HttpSession session, Model model, HttpServletRequest request) {
			String[] c_id = request.getParameterValues("c_id");
			
			if (session.getAttribute("member") == null) {
				return "redirect:/member/login";
			}else {
				for(String cart : c_id) {
					int cart_id = Integer.parseInt(cart);
					System.out.println("삭제되는 c_id : " + cart_id);
					try {
						cservice.remove(cart_id);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
			return "redirect:/order/cart";
		}
		
		// 장바구니 물건 개수 수정
		@RequestMapping("updatecart")
		public String updatecart(HttpSession session, Model model, @RequestParam(value="c_id") int c_id) {
			
			return "redirect:/order/cart";
		}
		
		// 배달 일정 확인
		@PostMapping("/checkschedule")
		public String checkschedule(Model model, @RequestParam(value="r_date") String r_date) {
			List<Schedule> slist = new ArrayList<>();
			List<Integer> enable_time = new ArrayList<Integer>();
			int dmancnt = 0;
			int schedulecnt = 0;
			
			System.out.println(r_date);
			
			
			// 배달기사 정보
			try {
				dmancnt = deservice.deliverymancnt();	// 총 배달기사 수
				slist = sservice.checktime(r_date);
				System.out.println(slist);
				
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			model.addAttribute("dmancnt", dmancnt);
			model.addAttribute("schedulecnt", schedulecnt);
			
			return "redirect:/order";
		}
		
		// 주문 폼
		@RequestMapping("/order")
		public String order(HttpSession session, Model model, Cart c_ids) {
			Member  m = (Member)session.getAttribute("member");
			List<Cart> clist = new ArrayList<>();
			List<Integer> c_id = c_ids.getC_ids();
			Cart c = null;
			
			int cnt = 0;

			// 로그인 상태 확인
			if (session.getAttribute("member") == null) {
				return "redirect:/member/login";
			}else {
				// 상품 정보
				for(int i : c_id) {
					try {
						c = cservice.selectedproduct(i);
						clist.add(c);
						model.addAttribute("clist", clist);
						cnt++;
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
				
				model.addAttribute("m", m);
				model.addAttribute("totalcnt", cnt);
				model.addAttribute("content", dir+"order");
			}
							
			return "index";
		}
		
		// 주문 완료
		@RequestMapping("ordered")
		public String ordered(HttpSession session, Model model, OrderForm of, Cart o_ids) {
			Member  m = (Member)session.getAttribute("member");
			int before_cnt = 0;
			int now_cnt = 0;
			
			if (session.getAttribute("member") == null) {
				return "redirect:/member/login";
			}else {
				
				of.setUser_id(m.getUser_id());
				
				if(of.getPay_option().equals("무통장입금")) {
					of.setO_status("입금대기");
				}else if(of.getPay_option().equals("신용카드")) {
					of.setO_status("결제완료");
				}
				
				
				try {
					// 주문 및 상세주문 등록
					ofservice.registerorder(of);
					System.out.println(of);
					
					
//					ofservice.registerdetailorder(of);
					ofservice.registerreservation(of);
					
					// 상품 재고 개수 변경
//					for(int c_id : o_ids.getC_ids()) {
//						// 기존 재고 확인
//						before_cnt = pservice.getcnt(oservice.selectpid(c_id));
//						// 재고 개수 변경
//						now_cnt = before_cnt - oservice.selectcnt(c_id);
//						pservice.updatecnt(now_cnt);
//					}
					
					
					model.addAttribute("content", dir+"confirmation" );
				} catch (Exception e) {
					e.printStackTrace();
				}
				
				System.out.println(of);
			}
			return "index";
		}
}


















package com.sof8.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sof8.dto.Cart;
import com.sof8.dto.Coupon;
import com.sof8.dto.DetailOrder;
import com.sof8.dto.Mail;
import com.sof8.dto.Member;
import com.sof8.dto.OrderForm;
import com.sof8.dto.Product;
import com.sof8.dto.Schedule;
import com.sof8.service.CartService;
import com.sof8.service.CouponService;
import com.sof8.service.DeliveryService;
import com.sof8.service.DetailOrderService;
import com.sof8.service.EmailService;
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
	
	@Autowired
	MemberService service;

	@Autowired
	EmailService eservice;
	
	@Autowired
	CouponService coservice;

	// 장바구니 보기
	@RequestMapping("/cart")
	public String cart(HttpSession session, Model model, Cart c) {
		List<Cart> clist = null;
		Member m = (Member) session.getAttribute("member");
		Cart c_ids = new Cart();
		
		if (session.getAttribute("member") == null) {
			return "redirect:/member/login";
		} else {
			try {
				clist = cservice.selectusercart(m.getUser_id());

				//
				int total_price = 0;
				int total_discount = 0;
				int total_delcost = 0;

				for (Cart i : clist) {
					total_price += (i.getC_cnt() * i.getPrice());
					total_discount += i.getDiscount();
					total_delcost += i.getDel_cost();
				}

				c.setTotal_price(total_price);
				c.setTotal_discount(total_discount);
				c.setTotal_delcost(total_delcost);
				c.setTotal(total_price + total_discount + total_delcost);
				
				model.addAttribute("checked", c_ids.getC_ids());
				model.addAttribute("cart", c);
				model.addAttribute("m", m);
				model.addAttribute("clist", clist);
				model.addAttribute("content", dir + "cart");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		return "index";
	}

	// 장바구니 선택 상품 정보
	@ResponseBody
	@PostMapping("/productinfo")
	public Map<String, Integer> productinfo(HttpSession session, Model model, HttpServletRequest request) {
		String[] c_ids = request.getParameterValues("c_ids");
		Map<String, Integer> info = new HashMap<String, Integer>();
		Cart cart = null;
		Product product = null;
		int price = 0;
		int discount = 0;
		int delcost = 0;
		int sumprice = 0;
		int sumdiscount = 0;
		int sumdelcost = 0;
		int c_id = 0;
		int p_id = 0;
		int c_cnt = 0;

		for (String c_info : c_ids) {
			// 장바구니 번호 확인
			c_id = Integer.parseInt(c_info);

			try {
				// 해당 번호의 장바구니 정보 가져오기
				cart = cservice.get(c_id);
				// 상품 번호 및 개수 가져오기
				p_id = cart.getP_id();
				c_cnt = cart.getC_cnt();

				// 상품의 총 가격, 총 할인금액, 총 배송비 구하기
				product = pservice.get(p_id);
				price = product.getPrice() * c_cnt;
				discount = product.getDiscount() * c_cnt;
				delcost = product.getDel_cost() * c_cnt;

			} catch (Exception e) {
				e.printStackTrace();
			}
			sumprice += price;
			sumdiscount += discount;
			sumdelcost += delcost;
		}
		info.put("sumprice", sumprice);
		info.put("sumdiscount", sumdiscount);
		info.put("sumdelcost", sumdelcost);
		info.put("total", (sumprice - sumdiscount + sumdelcost));

		return info;
	}

	// 장바구니 추가
	@ResponseBody
	@PostMapping("/addcart")
	public Map<String, Integer> addcart(HttpSession session, Model model, @RequestParam(value = "p_id") int p_id,
			@RequestParam(value = "quantity") int quantity) {
		Cart c = new Cart();
		Member m = (Member) session.getAttribute("member");
		Map<String, Integer> cart = new HashMap<String, Integer>();

		// 로그인 상태 확인
		if (session.getAttribute("member") == null) {
			cart.put("c_id", -1);
			return cart;
		} else {
			try {
				c.setUser_id(m.getUser_id());
				c.setP_id(p_id);
				c.setC_cnt(quantity);
				cservice.register(c);
				cart.put("c_id", c.getC_id());
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		return cart;
	}

	// 장바구니 삭제
	@RequestMapping("/deletecart")
	public String deletecart(HttpSession session, Model model, Cart c_ids) {
		List<Integer> c_id = c_ids.getC_ids();
		
		if (session.getAttribute("member") == null) {
			return "redirect:/member/login";
		} else {
			for (int cart_id : c_id) {
				System.out.println("삭제번호:" + cart_id);
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
	public String updatecart(HttpSession session, Model model, int c_id, int quantity) {
		Cart c = new Cart();
		List<Cart> clist = null;
		Member m = (Member) session.getAttribute("member");
		
		if (session.getAttribute("member") == null) {
			return "redirect:/member/login";
		} else {
			try {
				// 개수 변경
				c.setC_id(c_id);
				c.setC_cnt(quantity);
				cservice.modify(c);
				
				// 변경 후 장바구니 내용 보여주기
				clist = cservice.selectusercart(m.getUser_id());

				int total_price = 0;
				int total_discount = 0;
				int total_delcost = 0;

				for (Cart i : clist) {
					total_price += (i.getC_cnt() * i.getPrice());
					total_discount += i.getDiscount();
					total_delcost += i.getDel_cost();
				}

				c.setTotal_price(total_price);
				c.setTotal_discount(total_discount);
				c.setTotal_delcost(total_delcost);
				c.setTotal(total_price + total_discount + total_delcost);
				
				model.addAttribute("cart", c);
				model.addAttribute("m", m);
				model.addAttribute("clist", clist);
				model.addAttribute("content", dir + "cart");
				
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return "redirect:/order/cart";
	}

	// 배달 일정 확인
	@ResponseBody
	@PostMapping("/checkschedule")
	public Map<String, List<Integer>> checkschedule(Model model, @RequestParam(value = "r_date") String r_date) {
		List<Schedule> slist = new ArrayList<>();
		List<Integer> disable_time = new ArrayList<Integer>();
		Map<String, List<Integer>> cant = new HashMap<String, List<Integer>>();
		int dmancnt = 0;

		// 해당 날짜에 배달 예약 불가능한 시간 확인
		try {
			dmancnt = deservice.deliverymancnt(); // 총 배달기사 수
			slist = sservice.checktime(r_date);

			for (Schedule s : slist) {
				if (s.getS_cnt() == dmancnt) { // 해당 시간에 모든 기사들이 예약이 있음
					disable_time.add(s.getS_time());
				}
			}
			cant.put("disabletime", disable_time);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return cant;
	}
	
	// 쿠폰 적용
	@ResponseBody
	@PostMapping("/coupon")
	public Map<String, Integer> coupon(Model model, @RequestParam(value = "cd_id") String cd_id) {
		Map<String, Integer> coupon = new HashMap<String, Integer>();
		int coupon_id = Integer.parseInt(cd_id);
		
		try {
			coupon.put("discount", coservice.coupondiscount(coupon_id));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return coupon;
	}

	// 주문 폼
	@RequestMapping("/order")
	public String order(HttpSession session, Model model, Cart c_ids) {
		Member m = (Member) session.getAttribute("member");
		List<Cart> clist = new ArrayList<>();
		List<Integer> c_id = c_ids.getC_ids();
		Cart c = null;
		List<Coupon> co = new ArrayList<Coupon>();
		
		int cnt = 0;

		// 로그인 상태 확인
		if (session.getAttribute("member") == null) {
			return "redirect:/member/login";
		} else {
			// 쿠폰 정보
			try {
				co = coservice.couponlist(m.getUser_id());
			} catch (Exception e1) {
				e1.printStackTrace();
			}
			
			// 상품 정보
			for (int i : c_id) {
				try {
					c = cservice.selectedproduct(i);
					clist.add(c);
					cnt++;
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			model.addAttribute("coupon", co);
			model.addAttribute("clist", clist);
			model.addAttribute("m", m);
			model.addAttribute("totalcnt", cnt);
			model.addAttribute("content", dir + "order");
		}

		return "index";
	}

	// 주문 완료
	@RequestMapping("ordered")
	public String ordered(HttpSession session, Model model, OrderForm of, Coupon c, Cart c_ids) {
		Member m = (Member) session.getAttribute("member");
		Schedule s = new Schedule();
		DetailOrder d = new DetailOrder();
		ArrayList<Integer> p_id = of.getP_id();
		ArrayList<Integer> d_cnt = of.getD_cnt();
		ArrayList<Integer> price = of.getPrice();
		List<Product> orderedproduct = new ArrayList<Product>();
		String bank_info = null;
		int dman_id = 0;
		int before_cnt = 0;
		int now_cnt = 0;
		
		if (session.getAttribute("member") == null) {
			return "redirect:/member/login";
		} else {

			of.setUser_id(m.getUser_id());
			
			// 쿠폰 적용
			if(c.getCd_id() != 0) {
				try {
					coservice.usecoupon(c.getCd_id());
					of.setC_id(coservice.getcoid(c.getCd_id()));
					c.setC_discount(coservice.coupondiscount(c.getCd_id()));
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			
			// 결제 옵션에 따른 상태 변경
			if (of.getPay_option().equals("무통장입금")) {
				of.setO_status("입금대기");
				if(of.getNote().equals("신한")) {
					bank_info = "신한은행 [999-87456-32101 주식회사 SOF8]";
				}else if(of.getNote().equals("하나")) {
					bank_info = "KEB하나은행 [123-456789-01234 주식회사 SOF8]";
				}else if(of.getNote().equals("우리")) {
					bank_info = "우리은행 [135-79548-13542 주식회사 SOF8]";
				}else if(of.getNote().equals("국민")) {
					bank_info = "KB국민은행 [987-32145-1245 주식회사 SOF8]";
				}else if(of.getNote().equals("기업")) {
					bank_info = "IBK기업은행 [258-726415-12397 주식회사 SOF8]";
				}
			} else if (of.getPay_option().equals("신용카드")) {
				of.setO_status("결제완료");
			}
			
			try {
				// 주문 등록
				ofservice.registerorder(of);

				// 상세주문 등록
				for (int i = 0; i < of.getP_id().size(); i++) {
					d.setO_id(of.getO_id());
					d.setP_id(p_id.get(i));
					d.setD_cnt(d_cnt.get(i));
					d.setPrice(price.get(i));

					// 주문한 상품 내역
					ofservice.registerdetailorder(d);
					orderedproduct = oservice.selectorderd(of.getO_id());
				}

				// 배달 기사 설정
				s.setS_date(of.getR_date());
				s.setS_time(of.getR_time());
				dman_id = deservice.selectdeliveryman(s);

				of.setD_id(dman_id);

				// 예약 등록
				ofservice.registerreservation(of);

				// 배송 일정 등록
				ofservice.registerschedule(of);

				// 상품 재고 개수 변경
				for (int c_id : c_ids.getC_ids()) {
					// 기존 재고 확인
					before_cnt = pservice.getcnt(oservice.selectpid(c_id));
					// 재고 개수 변경
					now_cnt = before_cnt - oservice.selectcnt(c_id);
					pservice.updatecnt(now_cnt);

					// 장바구니에서 해당 상품 삭제
					cservice.remove(c_id);
				}
				
					// 메일
					Mail mail = new Mail();
					Date date = new Date();
					SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					List<String> p_name = new ArrayList<String>();
					List<String> p_img = new ArrayList<String>();
					String name = null;
					String img = null;
					
					// template에 넘길 값
					HashMap<String, OrderForm> values = new HashMap<String, OrderForm>();
					for(int id : of.getP_id()) {
						name = ofservice.getpname(id);
						img = ofservice.getpimg(id);
						p_name.add(name);
						p_img.add(img);
					}
					
					of.setP_name(p_name);
					of.setP_img(p_img);
					of.setName(m.getName());
					of.setO_date(formatter.format(date));
					values.put("order",of);
					
					// 전송할 이메일 데이터 셋팅
					mail.setTo(m.getEmail());
					mail.setSubject("[SOF8] " + m.getName() + " 님 주문이 완료되었습니다.");
					mail.setTemplate("/mail-templates/order");
					mail.setValues(values);

					// 이메일 전송
					eservice.sendMail(mail);
					
				model.addAttribute("coupon", c);
				model.addAttribute("bank", bank_info);
				model.addAttribute("orderlist", orderedproduct);
				model.addAttribute("content", dir + "confirmation");
			} catch (Exception e) {
				e.printStackTrace();
			}

		}
		return "index";
	}
}

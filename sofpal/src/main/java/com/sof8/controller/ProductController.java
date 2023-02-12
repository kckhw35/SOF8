package com.sof8.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.sof8.dto.Member;
import com.sof8.dto.Paging;
import com.sof8.dto.Product;
import com.sof8.dto.Review;
import com.sof8.frame.ImgUtil;
import com.sof8.service.CategoryService;
import com.sof8.service.ProductService;
import com.sof8.service.ReviewService;

@Controller
@RequestMapping("/product")
public class ProductController {
	
	String dir = "product/";
	
	@Autowired
	ProductService pservice;
	
	@Autowired
	CategoryService cservice;
	
	@Autowired
	ReviewService reviewService;
	
	@Value("${imgdir}")
	String imgdir;
	
	// 전체 상품보기
	@RequestMapping("/productall")
	public String productall(HttpSession session, Model model, @RequestParam(defaultValue = "1") int page, @RequestParam(required = false) String keyword, @RequestParam(value = "type", defaultValue = "p_name") String type) {
		List<Product> list = null;
		Map<String, Object> map = new HashMap<String, Object>();
		Paging paging = null;
		int totalRow;
		
		// 검색
		map.put("keyword", keyword);
		map.put("type", type);
		
		// 카테고리
		map.put("cat_id", 0);
				
		// 일반 사용자
		if (session.getAttribute("admin") == null) {
			try {
				// 검색한 데이터의 총 개수
				totalRow = pservice.selecttotal(map);

				if(totalRow>0) {
					do {
						// 페이징을 위한 데이터 입력
						paging = new Paging(12, 5, totalRow, page, keyword, type);
						// 페이징 후 데이터 검색
						list = pservice.selectlist(paging);
						--page;
					}while(list.isEmpty());
				} else {
					// 페이징을 위한 데이터 입력
					paging = new Paging(12, 5, totalRow, page, keyword, type);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {	// 관리자
			try {
				// 검색한 데이터의 총 개수
				totalRow = pservice.selecttotaladmin(map);
				
				if(totalRow>0) {
					do {
						// 페이징을 위한 데이터 입력
						paging = new Paging(12, 5, totalRow, page, keyword, type);
						// 페이징 후 데이터 검색
						list = pservice.selectlistadmin(paging);
						--page;
					}while(list.isEmpty());
				} else {
					// 페이징을 위한 데이터 입력
					paging = new Paging(12, 5, totalRow, page, keyword, type);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		model.addAttribute("plist", list);
		model.addAttribute("page", page);
		model.addAttribute("paging", paging);
		model.addAttribute("content", dir+"productall");
		return "index";
	}
	
	// 패브릭소파 상품보기
	@RequestMapping("/fabric")
	public String fabric(HttpSession session, Model model, @RequestParam(defaultValue = "1") int page, @RequestParam(required = false) String keyword, @RequestParam(value = "type", defaultValue = "p_name") String type) {
		List<Product> list = null;
		Map<String, Object> map = new HashMap<String, Object>();
		Paging paging = null;
		int totalRow;
		
		// 검색
		map.put("keyword", keyword);
		map.put("type", type);
		
		// 카테고리
		map.put("cat_id", 1);
		
		// 일반 사용자
		if (session.getAttribute("admin") == null) {
			try {
				// 검색한 데이터의 총 개수
				totalRow = pservice.selecttotal(map);
				
				if(totalRow>0) {
					do {
						// 페이징을 위한 데이터 입력
						paging = new Paging(12, 5, totalRow, page, keyword, type);
						paging.setCat_id(1);
						// 페이징 후 데이터 검색
						list = pservice.selectlist(paging);
						--page;
					}while(list.isEmpty());
				} else {
					// 페이징을 위한 데이터 입력
					paging = new Paging(12, 5, totalRow, page, keyword, type);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {	// 관리자
			try {
				// 검색한 데이터의 총 개수
				totalRow = pservice.selecttotaladmin(map);
				
				if(totalRow>0) {
					do {
						// 페이징을 위한 데이터 입력
						paging = new Paging(12, 5, totalRow, page, keyword, type);
						paging.setCat_id(1);
						// 페이징 후 데이터 검색
						list = pservice.selectlistadmin(paging);
						--page;
					}while(list.isEmpty());
				} else {
					// 페이징을 위한 데이터 입력
					paging = new Paging(12, 5, totalRow, page, keyword, type);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		model.addAttribute("plist", list);
		model.addAttribute("page", page);
		model.addAttribute("paging", paging);
		model.addAttribute("content", dir+"fabric");
		return "index";
	}
	
	// 가죽소파 상품보기
	@RequestMapping("/leather")
	public String leather(HttpSession session, Model model, @RequestParam(defaultValue = "1") int page, @RequestParam(required = false) String keyword, @RequestParam(value = "type", defaultValue = "p_id") String type) {
		List<Product> list = null;
		Map<String, Object> map = new HashMap<String, Object>();
		Paging paging = null;
		int totalRow;
		
		// 검색
		map.put("keyword", keyword);
		map.put("type", type);
		
		// 카테고리
		map.put("cat_id", 2);
		
		// 일반 사용자
		if (session.getAttribute("admin") == null) {
			try {
				// 검색한 데이터의 총 개수
				totalRow = pservice.selecttotal(map);
				
				if(totalRow>0) {
					do {
						// 페이징을 위한 데이터 입력
						paging = new Paging(12, 5, totalRow, page, keyword, type);
						paging.setCat_id(2);
						// 페이징 후 데이터 검색
						list = pservice.selectlist(paging);
						--page;
					}while(list.isEmpty());
				} else {
					// 페이징을 위한 데이터 입력
					paging = new Paging(12, 5, totalRow, page, keyword, type);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {	// 관리자
			try {
				// 검색한 데이터의 총 개수
				totalRow = pservice.selecttotaladmin(map);
				
				if(totalRow>0) {
					do {
						// 페이징을 위한 데이터 입력
						paging = new Paging(12, 5, totalRow, page, keyword, type);
						paging.setCat_id(2);
						// 페이징 후 데이터 검색
						list = pservice.selectlistadmin(paging);
						--page;
					}while(list.isEmpty());
				} else {
					// 페이징을 위한 데이터 입력
					paging = new Paging(12, 5, totalRow, page, keyword, type);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		model.addAttribute("plist", list);
		model.addAttribute("page", page);
		model.addAttribute("paging", paging);
		model.addAttribute("content", dir+"leather");
		return "index";
	}
	
	// 펫소파 상품보기
	@RequestMapping("/pet")
	public String pet(HttpSession session, Model model, @RequestParam(defaultValue = "1") int page, @RequestParam(required = false) String keyword, @RequestParam(value = "type", defaultValue = "p_id") String type) {
		List<Product> list = null;
		Map<String, Object> map = new HashMap<String, Object>();
		Paging paging = null;
		int totalRow;
		
		// 검색
		map.put("keyword", keyword);
		map.put("type", type);
		
		// 카테고리
		map.put("cat_id", 3);
		
		// 일반 사용자
		if (session.getAttribute("admin") == null) {
			try {
				// 검색한 데이터의 총 개수
				totalRow = pservice.selecttotal(map);
				
				if(totalRow>0) {
					do {
						// 페이징을 위한 데이터 입력
						paging = new Paging(12, 5, totalRow, page, keyword, type);
						paging.setCat_id(3);
						// 페이징 후 데이터 검색
						list = pservice.selectlist(paging);
						--page;
					}while(list.isEmpty());
				} else {
					// 페이징을 위한 데이터 입력
					paging = new Paging(12, 5, totalRow, page, keyword, type);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {	// 관리자
			try {
				// 검색한 데이터의 총 개수
				totalRow = pservice.selecttotaladmin(map);
				
				if(totalRow>0) {
					do {
						// 페이징을 위한 데이터 입력
						paging = new Paging(12, 5, totalRow, page, keyword, type);
						paging.setCat_id(3);
						// 페이징 후 데이터 검색
						list = pservice.selectlistadmin(paging);
						--page;
					}while(list.isEmpty());
				} else {
					// 페이징을 위한 데이터 입력
					paging = new Paging(12, 5, totalRow, page, keyword, type);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		model.addAttribute("plist", list);
		model.addAttribute("page", page);
		model.addAttribute("paging", paging);
		model.addAttribute("content", dir+"pet");
		return "index";
	}
	
	// 리빙룸 상품보기
	@RequestMapping("/livingroom")
	public String livingroom(HttpSession session, Model model, @RequestParam(defaultValue = "1") int page, @RequestParam(required = false) String keyword, @RequestParam(value = "type", defaultValue = "p_id") String type) {
		List<Product> list = null;
		Map<String, Object> map = new HashMap<String, Object>();
		Paging paging = null;
		int totalRow;
		
		// 검색
		map.put("keyword", keyword);
		map.put("type", type);
		
		// 카테고리
		map.put("cat_id", 4);
		
		// 일반 사용자
		if (session.getAttribute("admin") == null) {
			try {
				// 검색한 데이터의 총 개수
				totalRow = pservice.selecttotal(map);
				
				if(totalRow>0) {
					do {
						// 페이징을 위한 데이터 입력
						paging = new Paging(12, 5, totalRow, page, keyword, type);
						paging.setCat_id(4);
						// 페이징 후 데이터 검색
						list = pservice.selectlist(paging);
						--page;
					}while(list.isEmpty());
				} else {
					// 페이징을 위한 데이터 입력
					paging = new Paging(12, 5, totalRow, page, keyword, type);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {	// 관리자
			try {
				// 검색한 데이터의 총 개수
				totalRow = pservice.selecttotaladmin(map);
				
				if(totalRow>0) {
					do {
						// 페이징을 위한 데이터 입력
						paging = new Paging(12, 5, totalRow, page, keyword, type);
						paging.setCat_id(4);
						// 페이징 후 데이터 검색
						list = pservice.selectlistadmin(paging);
						--page;
					}while(list.isEmpty());
				} else {
					// 페이징을 위한 데이터 입력
					paging = new Paging(12, 5, totalRow, page, keyword, type);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		model.addAttribute("plist", list);
		model.addAttribute("page", page);
		model.addAttribute("paging", paging);
		model.addAttribute("content", dir+"livingroom");
		return "index";
	}
	
	// 상품 상세보기
	@RequestMapping("/productdetail")
	public String productdetail(Model model, int p_id, @RequestParam(defaultValue = "1") int page, @RequestParam(required = false) String keyword, @RequestParam(required = false) String type, HttpSession httpsession) {
		
		/* 상품 관련 로직 */
		Product p = null;
		try {
			p = pservice.get(p_id);
			model.addAttribute("p", p);
		
		} catch (Exception e) {
			e.printStackTrace();
		}
		model.addAttribute("content", dir+"productdetail");
		
		
		/* 로그인 확인 - 본인 리뷰 수정 삭제 가능구현 위해서 */ 
		if (httpsession.getAttribute("member") != null) {
			Member member = (Member) httpsession.getAttribute("member");
			model.addAttribute("member", member);
		}
		
		/* 리뷰관련 로직 - Park */
		try {
			//페이징 처리
			int totalRow = reviewService.getTotal(keyword, type, p_id);
			Paging paging = new Paging(totalRow, 5, totalRow, page, keyword, type);
			//리뷰 처리
			List<Review> reviews = reviewService.getReviewByProductId(paging, p_id);
			model.addAttribute("page", page);
			model.addAttribute("paging", paging);
			model.addAttribute("reviews", reviews);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return "index";
	}
	
	// 상품 등록하기
	@RequestMapping("/register")
	public String register(Model model, Product product) {
		
		String p_img = product.getImg().getOriginalFilename();		
		product.setP_img(p_img);
		
		try {
			ImgUtil.saveFile(product.getImg(), imgdir);
			// 카테고리 설정
			product.setCat_id(pservice.selectcategory(product));
			pservice.register(product);
			model.addAttribute("obj", product);
			
		} catch (Exception e) {
			model.addAttribute("content", "redirect:/admin/registerproduct");
			e.printStackTrace();
		}
		return "redirect:productall";
	}
	
	// 상품 수정하기
	@RequestMapping("/update")
	public String update(Model model, Product product) {
		
		String p_img = product.getImg().getOriginalFilename();		
		
		if(p_img.equals("") || p_img == null) {
			try {
				// 카테고리 설정
				product.setCat_id(pservice.selectcategory(product));
				pservice.modify(product);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}else {
			p_img = product.getImg().getOriginalFilename();
			product.setP_img(p_img);
			try {
				product.setCat_id(pservice.selectcategory(product));
				pservice.modify(product);
				ImgUtil.saveFile(product.getImg(), imgdir);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		return "redirect:productdetail?p_id="+product.getP_id();
	}
	
	// 상품 삭제하기
	@RequestMapping("/delete")
	public String delete(Model model, int p_id) {
		try {
			pservice.remove(p_id);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return "redirect:productall";
	}
}


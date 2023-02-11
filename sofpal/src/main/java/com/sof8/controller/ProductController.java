package com.sof8.controller;

import java.util.ArrayList;
import java.util.List;

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
import com.sof8.service.ProductService;
import com.sof8.service.ReviewService;

@Controller
@RequestMapping("/product")
public class ProductController {
	
	String dir = "product/";
	
	@Autowired
	ReviewService reviewService;
	
	@Autowired
	ProductService pservice;
	
	@Value("${imgdir}")
	String imgdir;
	
	// 상품보기
	@RequestMapping("/productall")
	public String productall(Model model) {
		List<Product> list = null;
		
		try {
			list = pservice.get();
		} catch (Exception e) {
			e.printStackTrace();
		}
		model.addAttribute("plist", list);
		model.addAttribute("content", dir+"productall");
		return "index";
	}
	
	@RequestMapping("/fabric")
	public String fabric(Model model) {
		
		List<Product> list = null;
		List<Product> plist = new ArrayList<Product>();
		
		try {
			
			list = pservice.get();
			for(Product p:list) {
				pservice.getmaincat(p.getCat_id());
				if( pservice.getmaincat(p.getCat_id())== 1) {
					plist.add(p);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		model.addAttribute("plist", plist);
		model.addAttribute("content", dir+"fabric");
		return "index";
	}
	
	@RequestMapping("/leather")
	public String leather(Model model) {
		
		List<Product> list = null;
		List<Product> plist = new ArrayList<Product>();
		
		try {
			
			list = pservice.get();
			for(Product p:list) {
				pservice.getmaincat(p.getCat_id());
				if( pservice.getmaincat(p.getCat_id())== 2) {
					plist.add(p);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		model.addAttribute("plist", plist);
		model.addAttribute("content", dir+"leather");
		return "index";
	}
	
	@RequestMapping("/pet")
	public String pet(Model model) {

		List<Product> list = null;
		List<Product> plist = new ArrayList<Product>();
		
		try {
			
			list = pservice.get();
			for(Product p:list) {
				pservice.getmaincat(p.getCat_id());
				if( pservice.getmaincat(p.getCat_id())== 3) {
					plist.add(p);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		model.addAttribute("plist", plist);
		model.addAttribute("content", dir+"pet");
		return "index";
	}
	
	@RequestMapping("/livingroom")
	public String livingroom(Model model) {

		List<Product> list = null;
		List<Product> plist = new ArrayList<Product>();
		
		try {
			
			list = pservice.get();
			for(Product p:list) {
				pservice.getmaincat(p.getCat_id());
				if( pservice.getmaincat(p.getCat_id())== 4) {
					plist.add(p);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		model.addAttribute("plist", plist);
		model.addAttribute("content", dir+"livingroom");
		return "index";
	}
	
	// 상품 상세보기
	@RequestMapping("/productdetail")
	public String productdetail(Model model, int p_id,
			@RequestParam(defaultValue = "1") int page,
			@RequestParam(required = false) String keyword,
			@RequestParam(required = false) String type,
			HttpSession httpsession) {
		
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


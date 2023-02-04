package com.sof8.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.sof8.dto.Product;
import com.sof8.frame.ImgUtil;
import com.sof8.service.ProductService;

@Controller
@RequestMapping("/product")
public class ProductController {
	
	String dir = "product/";
	
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
		
		try {
			list = pservice.get();
		} catch (Exception e) {
			e.printStackTrace();
		}
		model.addAttribute("plist", list);
		model.addAttribute("content", dir+"fabric");
		return "index";
	}
	
	@RequestMapping("/leather")
	public String leather(Model model) {
		model.addAttribute("content", dir+"leather");
		return "index";
	}
	
	@RequestMapping("/pet")
	public String pet(Model model) {
		model.addAttribute("content", dir+"pet");
		return "index";
	}
	
	@RequestMapping("/livingroom")
	public String livingroom(Model model) {
		model.addAttribute("content", dir+"livingroom");
		return "index";
	}
	
	// 상품 상세보기
	@RequestMapping("/productdetail")
	public String productdetail(Model model, int p_id) {
		Product p = null;
		try {
			p = pservice.get(p_id);
			model.addAttribute("p", p);
		
		} catch (Exception e) {
			e.printStackTrace();
		}		
		model.addAttribute("content", dir+"productdetail");
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


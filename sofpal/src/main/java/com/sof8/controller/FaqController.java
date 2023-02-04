package com.sof8.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sof8.dto.Faq;
import com.sof8.service.FaqService;

@Controller
@RequestMapping("/faq")
public class FaqController {
	
	@Autowired
	FaqService service;
	
	String dir ="faq/";
	
	@ResponseBody
	@GetMapping("/content")
	public String getFaqContent(@RequestParam int b_id) {
		Faq faq = new Faq();
		try {
			faq = service.get(b_id);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return faq.getContent();
	}
	
	
	@RequestMapping("")
	public String getFaqList(Model model, 
			@RequestParam(required = false, defaultValue="title") String searchOption,
			@RequestParam(required = false) String keyword,
			@RequestParam(required = false, defaultValue="1") Integer page) {
		
		List<Faq> faqs = null;
		
		//검색없이 전체 조회 하는 경우
		if (keyword == null || keyword.equals("null")) {
			try {
				faqs = service.get();
			} catch (Exception e) {
				System.out.println("Fail");
				e.printStackTrace();
			}
		} else {
		//검색과 함께 게시글 조회 
			try {
				faqs = service.getSearchedNotice(searchOption, keyword);
			} catch (Exception e) {
				System.out.println("Fail");
				e.printStackTrace();
			}
		}
		
		model.addAttribute("searchOption", searchOption);
		model.addAttribute("keyword", keyword);
		model.addAttribute("page", page);
		
		model.addAttribute("faqs", faqs);
		model.addAttribute("content", dir+"list");
		return "index";
	}
	
	
	//게시글 클릭
	//@RequestMapping("/{b_id}")
	public String getFaqContent(Model model, 
			@PathVariable("b_id") int b_id,
			@RequestParam(required = false) String searchOption,
			@RequestParam(required = false) String keyword,
			@RequestParam(required = false, defaultValue="1") Integer page) {
		
		Faq faq = null;
		
		try {
			faq = service.get(b_id);
		} catch (Exception e) {
			System.out.println("Fail");
			e.printStackTrace();
		}
		
		model.addAttribute("searchOption", searchOption);
		model.addAttribute("keyword", keyword);
		model.addAttribute("page", page);
		model.addAttribute("faq", faq);
		model.addAttribute("content", dir+"content");
		return "index";
	}
	
	@RequestMapping("/write")
	public String writeFaq(Model model,
			@RequestParam(required=false) Integer b_id) {
		if (b_id !=null) {
			try {
				Faq faq = service.get(b_id);
				model.addAttribute("faq", faq);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		model.addAttribute("content", dir+"write");
		return "index";
	}
	
	@RequestMapping("/save")
	public String writeFaq(Model model, 
			@RequestParam(required=false) String admin_id,
			@RequestParam String title,
			@RequestParam String content) {
		
		Faq faq = new Faq();
		faq.setAdmin_id("jonghoon");
		faq.setTitle(title);
		faq.setContent(content);
		try {
			service.register(faq);
			System.out.println("저장 성공");
		}catch (Exception e) {
			e.printStackTrace();
		}
		return "redirect:/faq";
	}
	
	@RequestMapping("/update")
	public String updateNotice(Model model,
			@RequestParam int b_id,
			@RequestParam String title,
			@RequestParam String content) {
		
		Faq faq = null;
		try {
			faq = service.get(b_id);
			faq.setTitle(title);
			faq.setContent(content);
			
			service.modify(faq);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return "redirect:/faq";
	}
	
	@RequestMapping("/delete")
	public String deleteFaq(Model model,
			@RequestParam int b_id) {
		
		try {
			service.remove(b_id);
		} catch (Exception e) {
			System.out.println("Fail");
			e.printStackTrace();
		} 

		model.addAttribute("content", dir + "list");
		return "redirect:/faq";
	}
	
}

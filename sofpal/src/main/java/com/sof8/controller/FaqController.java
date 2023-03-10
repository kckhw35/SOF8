package com.sof8.controller;

import java.time.LocalDateTime;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.sof8.dto.Admin;
import com.sof8.dto.Faq;
import com.sof8.dto.Paging;
import com.sof8.service.FaqService;

@Controller
@RequestMapping("/faq")
public class FaqController {
	
	@Autowired
	FaqService faqService;
	
	String dir ="faq/";
	
	@RequestMapping("/list")
	public String getFaqList(HttpSession session, Model model, 
			@RequestParam(defaultValue = "1") int page,
			@RequestParam(required = false) String keyword,
			@RequestParam(required = false) String type) {
		
		// 관리자인지 확인
		if(session.getAttribute("admin") != null) {
			Admin admin = (Admin)session.getAttribute("admin");
			model.addAttribute("admin", admin);
		}
		
		try {
			int totalRow = faqService.getTotal(keyword, type);
			Paging paging = new Paging(10, 5, totalRow, page, keyword, type);
			List<Faq> faqs = faqService.getList(paging);
			
			
			model.addAttribute("faqs", faqs);
			model.addAttribute("page", page);
			model.addAttribute("paging", paging);
			model.addAttribute("content", dir + "faq-list");
			
			System.out.println("[SUCCESS] : FAQ리스트 화면출력");
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("[ERROR] : FAQ리스트 로딩 실패");
		}
		
		return "index";
	}
	
	
	@RequestMapping("/content/{id}")
	public String faqContent(HttpSession session, Model model,
			@PathVariable("id") int id,
			@RequestParam(defaultValue = "1") int page,
			@RequestParam(required = false) String keyword,
			@RequestParam(required = false) String type) {
		
		// 관리자인지 확인
		if(session.getAttribute("admin") != null) {
			Admin admin = (Admin)session.getAttribute("admin");
			model.addAttribute("admin", admin);
		}
		
		Faq faq = new Faq();
		
		
		try {
			faq = faqService.get(id);
			
			//공지사항 조회수 증가
			int hit = faq.getHit();
			faq.setHit(hit + 1);
			faqService.modify(faq);
			
			int totalRow = faqService.getTotal(keyword, type);
			Paging paging = new Paging(10, 5, totalRow, page, keyword, type);
			
			model.addAttribute("faq", faq);
			model.addAttribute("page", page);
			model.addAttribute("paging", paging);
			model.addAttribute("content", dir + "faq-content");
			
			System.out.println("[SUCCESS] : ");
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("[ERROR] : ");
		}
		
		return "index";
	}
	

	
	@RequestMapping("/content/write")
	public String writeFaq(HttpSession session, Model model,
			@RequestParam(required=false) Integer b_id) {
		
		// 관리자인지 확인
		if(session.getAttribute("admin") == null) {
			return "redirect:/";
		}
		
		if (b_id !=null) {
			try {
				Faq faq = faqService.get(b_id);
				model.addAttribute("faq", faq);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		model.addAttribute("content", dir+"faq-write");
		return "index";
	}
	
	@RequestMapping("/content/save")
	public String writeNotice(HttpSession session, Model model, 
			@RequestParam String title,
			@RequestParam String content) {
		
		// 관리자인지 확인
		if(session.getAttribute("admin") == null) {
			return "redirect:/";
		}
		
		Faq faq = new Faq();
		faq.setTitle(title);
		faq.setContent(content);
		faq.setRdate(LocalDateTime.now());

		try {
			faqService.register(faq);
			System.out.println("저장 성공");
		}catch (Exception e) {
			e.printStackTrace();
		}
		return "redirect:/faq/list";
	}
	
	
	@RequestMapping("/content/update")
	public String updateNotice(HttpSession session, Model model,
			@RequestParam int b_id,
			@RequestParam String title,
			@RequestParam String content) {
		
		// 관리자인지 확인
		if(session.getAttribute("admin") == null) {
			return "redirect:/";
		}
		
		Faq faq = null;
		try {
			faq = faqService.get(b_id);
			faq.setTitle(title);
			faq.setContent(content);
			faq.setRdate(LocalDateTime.now());

			faqService.modify(faq);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return "redirect:/faq/content/" + b_id;
	}
	
	@RequestMapping("/content/{b_id}/delete")
	public String NoticeDelete(HttpSession session, Model model, 
			@PathVariable("b_id") int b_id) {

		// 관리자인지 확인
		if(session.getAttribute("admin") == null) {
			return "redirect:/";
		}
		
		try {
			faqService.remove(b_id);
		} catch (Exception e) {
			System.out.println("Fail");
			e.printStackTrace();
		} 
		return "redirect:/faq/list";
	}
	
}

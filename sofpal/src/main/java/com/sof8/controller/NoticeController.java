package com.sof8.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.sof8.dto.Notice;
import com.sof8.service.NoticeService;

@Controller
@RequestMapping("/notice")
public class NoticeController {
	
	@Autowired
	NoticeService service;
	
	String dir ="notice/";
	
	//공지사항 검색 없이 렌더링 & 검색 렌더링
	@RequestMapping("")
	public String SearchingNotice(Model model, 
			@RequestParam(required = false, defaultValue="title") String searchOption,
			@RequestParam(required = false) String keyword,
			@RequestParam(required = false, defaultValue="1") Integer page) {
		
		List<Notice> notices = null;
		
		//검색X
		if (keyword == null || keyword.equals("null")) {
			try {
				notices = service.get();
			} catch (Exception e) {
				System.out.println("Fail");
				e.printStackTrace();
			}
		} else {
		//검색 기능 
			try {
				notices = service.getSearchedNotice(searchOption, keyword);
			} catch (Exception e) {
				System.out.println("Fail");
				e.printStackTrace();
			}
		}
		
		model.addAttribute("searchOption", searchOption);
		model.addAttribute("keyword", keyword);
		model.addAttribute("page", page);
		
		model.addAttribute("notices", notices);
		model.addAttribute("content", dir+"list");
		return "index";
	}
	
	//게시글 클릭
	@RequestMapping("/{b_id}")
	public String NoticeContent(Model model, 
			@PathVariable("b_id") int b_id,
			@RequestParam(required = false) String searchOption,
			@RequestParam(required = false) String keyword,
			@RequestParam(required = false, defaultValue="1") Integer page) {
		
		Notice notice = null;
		
		try {
			notice = service.get(b_id);
			//조회수 증가
			int hit = notice.getHit();
			notice.setHit(hit + 1);		
			service.modify(notice);
		} catch (Exception e) {
			System.out.println("Fail");
			e.printStackTrace();
		}
		
		model.addAttribute("searchOption", searchOption);
		model.addAttribute("keyword", keyword);
		model.addAttribute("page", page);
		model.addAttribute("notice", notice);
		model.addAttribute("content", dir+"content");
		return "index";
	}
	
	 
}

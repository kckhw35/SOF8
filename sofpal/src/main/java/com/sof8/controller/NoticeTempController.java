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
import com.sof8.dto.NoticeTemp;
import com.sof8.dto.Paging;
import com.sof8.service.NoticeServiceTemp;

@Controller
@RequestMapping("/notice")
public class NoticeTempController {
	
	@Autowired
	NoticeServiceTemp noticeService;
	
	String dir ="notice/";
	
	//127.0.0.1/notice/list
	@RequestMapping("/list")
	public String noticeList(HttpSession session, Model model,
			@RequestParam(defaultValue = "1") int page,
			@RequestParam(required = false) String keyword,
			@RequestParam(required = false) String type) {
		
		// 관리자인지 확인
		if(session.getAttribute("admin") != null) {
			Admin admin = (Admin)session.getAttribute("admin");
			model.addAttribute("admin", admin);
		}
		
		try {
			int totalRow = noticeService.getTotal(keyword, type);
			Paging paging = new Paging(10, 5, totalRow, page, keyword, type);
			List<NoticeTemp> notices = noticeService.getList(paging);
			
			model.addAttribute("notices", notices);
			model.addAttribute("page", page);
			model.addAttribute("paging", paging);
			model.addAttribute("content", dir + "notice-list");
			
			System.out.println("[SUCCESS] : QnaController/qna - QnA리스트 화면출력");
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("[ERROR] : QnaController/qna - QnA리스트 로딩 실패");
		}
		
		return "index";
	}
	
	//127.0.0.1/notice/content/{id}
	@RequestMapping("/content/{id}")
	public String noticeContent(HttpSession session, Model model,
			@PathVariable("id") int id,
			@RequestParam(defaultValue = "1") int page,
			@RequestParam(required = false) String keyword,
			@RequestParam(required = false) String type) {
		
		// 관리자인지 확인
		if(session.getAttribute("admin") != null) {
			Admin admin = (Admin)session.getAttribute("admin");
			model.addAttribute("admin", admin);
		}
		
		NoticeTemp notice = null;
		
		try {
			//페이지에 보여줄 공지사항
			notice = noticeService.get(id);
			
			//공지사항 조회수 증가
			int hit = notice.getHit();
			notice.setHit(hit + 1);
			noticeService.modify(notice);
			
			int totalRow = noticeService.getTotal(keyword, type);
			Paging paging = new Paging(10, 5, totalRow, page, keyword, type);
			
			model.addAttribute("notice", notice);
			model.addAttribute("page", page);
			model.addAttribute("paging", paging);
			model.addAttribute("content", dir + "notice-content");
			
			System.out.println("[SUCCESS] : QnaController/qna - QnA리스트 화면출력");
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("[ERROR] : QnaController/qna - QnA리스트 로딩 실패");
		}
		
		return "index";
	}
	
	@RequestMapping("/content/write")
	public String writeNotice(HttpSession session, Model model,
			@RequestParam(required=false) Integer b_id) {
		
		// 관리자인지 확인
		if(session.getAttribute("admin") == null) {
			return "redirect:/";
		}
		
		if (b_id !=null) {
			try {
				NoticeTemp notice = noticeService.get(b_id);
				model.addAttribute("notice", notice);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		model.addAttribute("content", dir+"notice-write");
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
		
		NoticeTemp notice = new NoticeTemp();
		notice.setTitle(title);
		notice.setContent(content);
		notice.setRdate(LocalDateTime.now());

		try {
			noticeService.register(notice);
			System.out.println("저장 성공");
		}catch (Exception e) {
			e.printStackTrace();
		}
		return "redirect:/notice/list";
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
		
		NoticeTemp notice = null;
		try {
			notice = noticeService.get(b_id);
			notice.setTitle(title);
			notice.setContent(content);
			notice.setRdate(LocalDateTime.now());

			noticeService.modify(notice);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return "redirect:/notice/content/" + b_id;
	}
	
	// view를 통하지 않고 url 검색으로 게시판을 지워버릴 수 도 있다....
	@RequestMapping("/content/{b_id}/delete")
	public String NoticeDelete(HttpSession session, Model model, 
			@PathVariable("b_id") int b_id) {

		// 관리자인지 확인
		if(session.getAttribute("admin") == null) {
			return "redirect:/";
		}
		
		try {
			noticeService.remove(b_id);
		} catch (Exception e) {
			System.out.println("Fail");
			e.printStackTrace();
		} 
		return "redirect:/notice/list";
	}
}

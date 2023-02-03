package com.sof8.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.sof8.dto.Paging;
import com.sof8.dto.Qna;
import com.sof8.service.QnaService;
import com.sof8.service.ReplyService;

@Controller
@RequestMapping("/qna")
public class QnaController {
	
	@Autowired
	QnaService qnaService;
	
	@Autowired
	ReplyService replyService;
	
	String dir ="qna/";
	
	//127.0.0.1/qna/list
	@RequestMapping("/list")
	public String qnalist(HttpSession session, Model model,
			@RequestParam(defaultValue = "1") int page,
			@RequestParam(required = false) String keyword,
			@RequestParam(required = false) String type) {
		
		if (session.getAttribute("admin") != null) {
			try {
				int totalRow = qnaService.getTotal(keyword, type);
				Paging paging = new Paging(10, 5, totalRow, page, keyword, type);
				List<Qna> qnas = qnaService.getList(paging);
				
				for (Qna qna : qnas) {
					qna.setReply(replyService.getReply(qna.getR_id()));
				}
				
				model.addAttribute("qnas", qnas);
				model.addAttribute("page", page);
				model.addAttribute("paging", paging);
				model.addAttribute("content", dir + "list");
				
				System.out.println("[SUCCESS] : QnaController/qna - QnA리스트 화면출력");
			} catch (Exception e) {
				e.printStackTrace();
				System.out.println("[ERROR] : QnaController/qna - QnA리스트 로딩 실패");
			}
		}else {
			return "redirect:/";
		}
		return "index";
	}
	
	//127.0.0.1/qna/content
	@RequestMapping("/content")
	public String qnaContent(Model model, HttpSession session, @RequestParam("id") int r_id) {
		
		/* 예외 처리: 관리자 로그인 안되어있으면 접속 불가능*/
		if (session.getAttribute("admin") == null) {
			return "redirect:/";
		}
		Qna qna = null;
		try {
			qna = qnaService.get(r_id);
			qna.setReply(replyService.getReply(qna.getR_id()));
			
		} catch (Exception e) {
			System.out.println("/qna/content - Fail");
			e.printStackTrace();
		}
		
		model.addAttribute("qna", qna);
		model.addAttribute("content", dir + "content");
		
		return "index";
	}
}

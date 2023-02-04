package com.sof8.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.sof8.dto.Admin;
import com.sof8.dto.Paging;
import com.sof8.dto.Qna;
import com.sof8.dto.Reply;
import com.sof8.service.QnaService;
import com.sof8.service.ReplyService;

@Controller
@RequestMapping("/admin/qna")
public class AdminQnaController {
	
	@Autowired
	QnaService qnaService;
	
	@Autowired
	ReplyService replyService;
	
	String dir ="qna/";
	
	//127.0.0.1/admin/qna/list
	@RequestMapping("/list")
	public String qnalist(HttpSession session, Model model,
			@RequestParam(defaultValue = "1") int page,
			@RequestParam(required = false) String keyword,
			@RequestParam(required = false) String type) {
		
		/* 예외처리 로직: 관리자가 아닌경우 홈페이지*/
		if (session.getAttribute("admin") == null) {
			return "redirect:/";
		}
		
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
		
		return "index";
	}
	
	//127.0.0.1/admin/qna/content
	@RequestMapping("/content")
	public String qnaContent(Model model, HttpSession session, 
			@RequestParam("id") int r_id,
			@RequestParam(defaultValue = "1") int page,
			@RequestParam(required = false) String keyword,
			@RequestParam(required = false) String type) {
		
		/* 예외 처리: 관리자 로그인 안되어있으면 접속 불가능*/
		if (session.getAttribute("admin") == null) {
			return "redirect:/";
		}
		Qna qna = null;
		try {
			int totalRow = qnaService.getTotal(keyword, type);
			Paging paging = new Paging(10, 5, totalRow, page, keyword, type);
			model.addAttribute("paging", paging);
			
			qna = qnaService.get(r_id);
			qna.setReply(replyService.getReply(qna.getR_id()));
			System.out.println("/qna/content - succes");
			
		} catch (Exception e) {
			System.out.println("/qna/content - Fail");
			e.printStackTrace();
		}
		
		model.addAttribute("admin", session.getAttribute("admin"));
		model.addAttribute("qna", qna);
		model.addAttribute("content", dir + "content");
		
		return "index";
	}
	
	//127.0.0.1/admin/qna/reply
	@RequestMapping("/reply")
	public String writeReply(Model model, HttpSession session, 
			@RequestParam String reply, @RequestParam int id) {
		
		
		/* 예외 처리: 관리자 로그인 안되어있으면 접속 불가능*/
		if (session.getAttribute("admin") == null) {
			return "redirect:/";
		}
		
		Reply replyObject = new Reply();
		Admin admin = (Admin)session.getAttribute("admin");
		try {
			Qna qna = qnaService.get(id);

			replyObject.setAdmin_id(admin.getAdmin_id());
			replyObject.setReply(reply);
			replyObject.setR_id(id);
			
			
			replyService.register(replyObject);
			
			qna.setReply(replyService.getReply(qna.getR_id()));
			qnaService.modify(qna);
			
		} catch (Exception e) {
			System.out.println("/qna/content - Fail");
			e.printStackTrace();
		}
		
		return "redirect:/admin/qna/content?id="+id;
	}
	
	//127.0.0.1/admin/qna/edit
	@RequestMapping("/edit")
	public String editReply(Model model, HttpSession session, 
			@RequestParam String reply, @RequestParam int id) {
		
		
		/* 예외 처리: 관리자 로그인 안되어있으면 접속 불가능*/
		if (session.getAttribute("admin") == null) {
			return "redirect:/";
		}
		
		Reply replyObject = null;
		Admin admin = (Admin)session.getAttribute("admin");
		
		try {
			Qna qna = qnaService.get(id);
			
			replyObject = replyService.getReply(id);
			
			replyObject.setAdmin_id(admin.getAdmin_id());
			replyObject.setReply(reply);
			
			replyService.modify(replyObject);
			
			qna.setReply(replyObject);
			qnaService.modify(qna);
			
		} catch (Exception e) {
			System.out.println("/qna/content - Fail");
			e.printStackTrace();
		}
		
		return "redirect:/admin/qna/content?id="+id;
	}
}

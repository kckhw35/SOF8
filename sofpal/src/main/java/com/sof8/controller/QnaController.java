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

@Controller
@RequestMapping("/qna")
public class QnaController {
	
	@Autowired
	QnaService service;
	
	String dir ="qna/";
	
	//127.0.0.1/qna/qnalist
	@RequestMapping("/qnalist")
	public String memberlist(HttpSession session, Model model,
			@RequestParam(defaultValue = "1") int page,
			@RequestParam(required = false) String keyword,
			@RequestParam(required = false) String type) {
		
		System.out.println(keyword);
		System.out.println(type);
		if (session.getAttribute("admin") != null) {
			try {
				int totalRow = service.getTotal(keyword, type);
				Paging paging = new Paging(10, 5, totalRow, page, keyword, type);
				List<Qna> qnas = service.getList(paging); 

				model.addAttribute("qnas", qnas);
				model.addAttribute("page", page);
				model.addAttribute("paging", paging);
				model.addAttribute("content", dir + "qnalist");
				
				System.out.println("totalRow: " + totalRow);
				System.out.println("paging: " + paging);
				System.out.println("qnas: " + qnas);				
				System.out.println("[SUCCESS] : QnaController/qna - QnA리스트 화면출력");
			} catch (Exception e) {
				e.printStackTrace();
				System.out.println("[ERROR] : QnaController/qna - QnA리스트 로딩 실패");
			}
		}
		return "index";
	}
}

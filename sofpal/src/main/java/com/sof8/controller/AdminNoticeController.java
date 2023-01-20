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
@RequestMapping("/admin/notice")
public class AdminNoticeController {

	@Autowired
	NoticeService service;

	String dir = "notice/";

	// view를 통하지 않고 url 검색으로 게시판을 지워버릴 수 도 있다....
	@RequestMapping("/{b_id}/delete")
	public String NoticeDelete(Model model, @PathVariable("b_id") int b_id) {

		// 관리자로 로그인이 안되어 있으면, redirect:/notice/{b_id} -> 나중에 추가해야한다.
		
		try {
			service.remove(b_id);
		} catch (Exception e) {
			System.out.println("Fail");
			e.printStackTrace();
		} 

		model.addAttribute("content", dir + "list");
		return "redirect:/notice";
	}
}

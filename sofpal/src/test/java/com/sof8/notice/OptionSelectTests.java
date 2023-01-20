package com.sof8.notice;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.sof8.dto.Notice;
import com.sof8.service.NoticeService;

@SpringBootTest
class OptionSelectTests {
	 
	@Autowired
	NoticeService service;

	@Test
	void contextLoads() {
		List<Notice> notices = new ArrayList<Notice>();
		try {
			notices = service.getSearchedNotice("title", "제주");
			for (Notice notice : notices) {
				System.out.println(notice);
			}
			System.out.println("OK");
		} catch (Exception e) {
			System.out.println("Fail");
			e.printStackTrace();
		}
	}
}

package com.sof8.notice;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.sof8.dto.Notice;
import com.sof8.service.NoticeService;

@SpringBootTest
class InsertTests {
	 
	@Autowired
	NoticeService service;

	@Test
	void contextLoads() {
		Notice notice = new Notice("jonghoon", "새로운 제목","새로운 내용");
		
		try {
			service.register(notice);
			System.out.println("OK");
		} catch (Exception e) {
			System.out.println("Fail");
			e.printStackTrace();
		}
	}
}

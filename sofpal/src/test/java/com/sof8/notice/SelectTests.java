package com.sof8.notice;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.sof8.dto.Notice;
import com.sof8.service.NoticeService;

@SpringBootTest
class SelectTests {
	 
	@Autowired
	NoticeService service;

	@Test
	void contextLoads() {
		Notice notice = null;
		
		try {
			notice = service.get(4);
			System.out.println(notice);
			System.out.println("OK");
		} catch (Exception e) {
			System.out.println("Fail");
			e.printStackTrace();
		}
	}
}

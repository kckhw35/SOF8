package com.sof8.notice_temp;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.sof8.dto.NoticeTemp;
import com.sof8.service.NoticeServiceTemp;

@SpringBootTest
class InsertTests {
	 
	@Autowired
	NoticeServiceTemp service;

	@Test
	void contextLoads() {
		LocalDateTime now = LocalDateTime.now();
		
		NoticeTemp notice = new NoticeTemp();
		notice.setRdate(now);
		notice.setTitle("Java에서 Insert한 공지사항");
		notice.setContent("Java에서  Content");
		try {
			service.register(notice);
			System.out.println("OK");
		} catch (Exception e) {
			System.out.println("Fail");
			e.printStackTrace();
		}
	}
}

package com.sof8.notice;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.sof8.dto.Notice;
import com.sof8.service.NoticeService;

@SpringBootTest
class UpdateTests {
	 
	@Autowired
	NoticeService service;

	@Test
	void contextLoads() {
		Notice notice = new Notice("jonghoon", "변경된 제목","변경된 내용");
		notice.setB_id(57);
		try {
			service.modify(notice);
			System.out.println("OK");
		} catch (Exception e) {
			System.out.println("Fail");
			e.printStackTrace();
		}
	}
}

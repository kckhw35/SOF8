package com.sof8.notice_temp;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.sof8.dto.NoticeTemp;
import com.sof8.service.NoticeServiceTemp;

@SpringBootTest
class UpdateTests {
	 
	@Autowired
	NoticeServiceTemp service;

	@Test
	void contextLoads() {
		NoticeTemp notice = new NoticeTemp();
		
		notice.setB_id(4);
		notice.setType("공지사항");
		notice.setTitle("Update through Java");
		notice.setContent("내용 변경");
		notice.setHit(5);
		
		try {
			service.modify(notice);
			System.out.println("OK");
		} catch (Exception e) {
			System.out.println("Fail");
			e.printStackTrace();
		}
	}
}

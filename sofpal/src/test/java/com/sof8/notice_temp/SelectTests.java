package com.sof8.notice_temp;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.sof8.dto.NoticeTemp;
import com.sof8.service.NoticeServiceTemp;

@SpringBootTest
class SelectTests {
	 
	@Autowired
	NoticeServiceTemp service;

	@Test
	void contextLoads() {
		NoticeTemp notice = null;
		
		try {
			notice = service.get(1);
			System.out.println(notice);
			System.out.println("OK");
		} catch (Exception e) {
			System.out.println("Fail");
			e.printStackTrace();
		}
	}
}

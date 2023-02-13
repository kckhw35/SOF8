package com.sof8.notice_temp;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.sof8.dto.NoticeTemp;
import com.sof8.dto.Paging;
import com.sof8.service.NoticeServiceTemp;

@SpringBootTest
class SelectListTest {
	 
	@Autowired
	NoticeServiceTemp service;

	@Test
	void contextLoads() {
		List<NoticeTemp> notices = new ArrayList<NoticeTemp>();
		try {
			int total = service.getTotal(null, null);
			Paging paging = new Paging(10, 5, total, 1, null, null);
			System.out.println(total);
			notices = service.getList(paging);
			for (NoticeTemp notice : notices) {
				System.out.println(notice);
			}
			System.out.println("OK");
		} catch (Exception e) {
			System.out.println("Fail");
			e.printStackTrace();
		}
	}
}

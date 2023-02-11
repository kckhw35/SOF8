package com.sof8.qna;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.sof8.dto.Paging;
import com.sof8.dto.Qna;
import com.sof8.service.QnaService;

@SpringBootTest
class SelectListTest {
	 
	@Autowired
	QnaService service;

	@Test
	void contextLoads() {
		List<Qna> qnas = new ArrayList<Qna>();
		try {
			int totalRow = service.getTotal(null, null);
			Paging paging = new Paging(10, 5, totalRow , 2, null, null);
			qnas = service.getList(paging);
			for (Qna qna : qnas) {
				System.out.println(qna);
			}
			System.out.println("OK");
		} catch (Exception e) {
			System.out.println("Fail");
			e.printStackTrace();
		}
	}
}

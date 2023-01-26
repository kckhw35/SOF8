package com.sof8.faq;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.sof8.dto.Faq;
import com.sof8.service.FaqService;

@SpringBootTest
class SelectAllTests {
	 
	@Autowired
	FaqService service;

	@Test
	void contextLoads() {
		List<Faq> faqs = new ArrayList<Faq>();
		try {
			faqs = service.get();
			for (Faq faq : faqs) {
				System.out.println(faq);
			}
			System.out.println("OK");
		} catch (Exception e) {
			System.out.println("Fail");
			e.printStackTrace();
		}
	}
}

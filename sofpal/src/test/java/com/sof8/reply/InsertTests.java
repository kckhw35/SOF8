package com.sof8.reply;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.sof8.dto.Reply;
import com.sof8.service.ReplyService;

@SpringBootTest
class InsertTests {
	 
	@Autowired
	ReplyService service;

	@Test
	void contextLoads() {
		Reply reply = new Reply();
		reply.setR_id(5);
		reply.setAdmin_id("jonghoon");
		reply.setReply("Java JUnit Test 문의 작성");
		
		try {
			service.register(reply);
			System.out.println("OK");
		} catch (Exception e) {
			System.out.println("Fail");
			e.printStackTrace();
		}
	}
}

package com.sof8.reply;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.sof8.dto.Reply;
import com.sof8.service.ReplyService;

@SpringBootTest
class UpdateTests {
	 
	@Autowired
	ReplyService service;

	@Test
	void contextLoads() {
		Reply reply = new Reply();
		reply.setReply("Edit in Java 문의 사항");
		reply.setRe_id(6);
		try {
			service.modify(reply);
			System.out.println("OK");
		} catch (Exception e) {
			System.out.println("Fail");
			e.printStackTrace();
		}
	}
}

package com.sof8.reply;

import java.time.LocalDateTime;

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
		try {
			Reply reply = service.getReply(1);
			
			reply.setReply("Edit in Java 문의 사항");
			reply.setMdate(LocalDateTime.now());
			
			service.modify(reply);
			System.out.println("OK");
		} catch (Exception e) {
			System.out.println("Fail");
			e.printStackTrace();
		}
	}
}

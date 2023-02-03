package com.sof8.reply;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.sof8.dto.Reply;
import com.sof8.service.ReplyService;

@SpringBootTest
class SelectReplyTests {
	 
	@Autowired
	ReplyService service;

	@Test
	void contextLoads() {
		Reply reply = null;
		
		try {
			reply = service.getReply(5);
			System.out.println(reply);
			System.out.println("OK");
		} catch (Exception e) {
			System.out.println("Fail");
			e.printStackTrace();
		}
	}
}

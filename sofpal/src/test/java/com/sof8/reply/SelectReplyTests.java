package com.sof8.reply;

import java.util.List;

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
		List<Reply> reply = null;
		
		try {
			reply = service.getReply(55);
			for (Reply re : reply) {
				System.out.println(re);
			}
			System.out.println("OK");
		} catch (Exception e) {
			System.out.println("Fail");
			e.printStackTrace();
		}
	}
}

package com.sof8.Qna;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.sof8.dto.Qna;
import com.sof8.service.QnaService;

@SpringBootTest
class UpdateTests {
	 
	@Autowired
	QnaService service;

	@Test
	void contextLoads() {
		Qna qna = new Qna();
		qna.setRe_pwd("password-changed");
		qna.setTitle("문의 제목 변경요~");
		qna.setContent("내용도 변경요~ ㄹ어나밀디ㅏ머딞ㅈㄹㅈㅁ");
		qna.setR_id(57);
		try {
			service.modify(qna);
			System.out.println("OK");
		} catch (Exception e) {
			System.out.println("Fail");
			e.printStackTrace();
		}
	}
}

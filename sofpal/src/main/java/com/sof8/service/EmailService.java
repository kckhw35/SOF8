package com.sof8.service;

import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;

import com.sof8.dto.Mail;
import com.sof8.frame.EmailUtil;

@Service
public class EmailService implements EmailUtil {
	@Autowired
	private JavaMailSender sender;

	@Autowired
	private SpringTemplateEngine template;

	@Override
	public void sendMail(Mail email) throws Exception {
		MimeMessage message = sender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(message, true);

		// 메일 제목 설정
		helper.setSubject(email.getSubject());

		// 수신자 설정
		helper.setTo(email.getTo());

		// 템플릿에 전달할 데이터 설정
		Context context = new Context();
		email.getValues().forEach((key, value) -> {
			context.setVariable(key, value);
		});
		
        // 메일 내용 설정 : 템플릿 프로세스
		String html = template.process(email.getTemplate(), context);
		helper.setText(html, true);
		
		// 메일 보내기
		sender.send(message);
	}
}

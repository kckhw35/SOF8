package com.sof8.dto;

import java.util.HashMap;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class Mail {
	
	private String to;	// 받는사람
	private String template;// 이메일 템플릿
	private String subject;	// 이메일 제목	
	private HashMap<String, String> values;	// 이메일에 들어가는 값
}

package com.sof8.dto;

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
public class Faq {
	private Integer b_id;
	private String admin_id;
	private String type;
	private String title;
	private String content;
	private String b_img;
	private int hit;
	private String rdate;
	
	//Insert 시 사용
	public Faq(String admin_id, String title, String content) {
		this.admin_id = admin_id;
		this.title = title;
		this.content = content;
	}
}
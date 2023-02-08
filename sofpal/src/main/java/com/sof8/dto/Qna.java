package com.sof8.dto;

import java.util.Date;

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
public class Qna {
	private Integer r_id;
	private String user_id;
	private Integer p_id;	//사용X
	private Integer de_id;	//사용X
	private String re_pwd;	
	private String title;
	private String content;
	private String r_img;	//사용X
	private Integer grade;	//사용X
	private Integer up;		//사용X
	private Date rdate;		
	
	/* 1:1 문의에 대한 답변 */
	private Reply reply;
}
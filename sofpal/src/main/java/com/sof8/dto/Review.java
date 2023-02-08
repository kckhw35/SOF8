package com.sof8.dto;

import java.time.LocalDateTime;

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
public class Review {
	private Integer r_id;
	private String user_id;
	private Integer p_id;
	private Integer de_id;
	private String re_pwd;	
	private String title;
	private String content;
	private String r_img;
	private Integer grade;
	private Integer up;
	private LocalDateTime rdate;		
}
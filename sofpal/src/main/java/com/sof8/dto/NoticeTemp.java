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
public class NoticeTemp {
	private Integer b_id;
	private String type;
	private String title;
	private String content;
	private String b_img;
	private int hit;
	private LocalDateTime rdate;
}
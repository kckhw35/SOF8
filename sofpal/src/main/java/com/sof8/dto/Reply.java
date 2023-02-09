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
public class Reply {
	private Integer re_id;
	private Integer r_id;
	private String admin_id;
	private String reply;
	private LocalDateTime rdate;	//등록일
	private LocalDateTime mdate;	//수정일
}
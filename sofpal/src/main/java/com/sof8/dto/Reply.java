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
public class Reply {
	private Integer re_id;
	private Integer r_id;
	private String admin_id;
	private String reply;
	private Date rdate;
}
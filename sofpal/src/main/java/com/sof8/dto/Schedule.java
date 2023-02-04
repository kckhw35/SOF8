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
public class Schedule {
	private int s_id;
	private int d_id;
	private String s_date;
	private int s_time;
	private boolean status;
	
	private int s_cnt;		// 시간별 예약 개수
}

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
public class Reservation {
	private int re_id;
	private int d_id;
	private int o_id;
	private String r_date;
	private int r_time;
	
	// 주문
	private String o_status;	// 주문 상태
	
	// 배송
	private String d_name;	// 배송기사 이름
	private String d_tel;	// 배송기사 연락처
	private String re_req;	// 배송시 요청사항
}

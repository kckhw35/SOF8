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
public class Coupon {
	
	//쿠폰 
	private int co_id;  //쿠폰아이디
	private String c_name; //쿠폰명
	private String c_content; //쿠폰내용
	private int c_discount; //할인금
	
	
	
	// 쿠폰상세 
	private String user_id;   //사용자 아이디 
	private boolean usaged; //사용여부
	private String startdate; //발급일 
	private String enddate; //유효기간 
	
	
	

	
}

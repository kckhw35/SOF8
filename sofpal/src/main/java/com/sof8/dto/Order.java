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
public class Order {
	private String user_id;
	private int c_id;
	private int totalprice;
	private int discount;
	private String pay_option;
	private String c_date;
	private String re_name;
	private String re_tel;
	private String re_zipcode;
	private String re_addr;
	private String re_deaddr;
	private String re_req;
	
	private Date o_date;		// 주문일
	private int o_id;			// 주문번호
	private String p_name;		// 상품명
	private String size;		// 상품크기
	private String color;		// 상품색상
	private int price;			// 상품금액
	private int d_cnt;			// 주문수량
	private String o_status;	// 주문상태
	
	private int p_id;			// 상품아이디
	private String p_img;		// 상품사진
	private int de_id;			// 상세주문 아이디
	
	private Review review;		//review
}

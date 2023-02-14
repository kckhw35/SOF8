package com.sof8.dto;

import java.util.ArrayList;
import java.util.List;

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
public class OrderForm {
	// Order
	private int o_id;
	private String user_id;
	private int totalprice;
	private int totaldiscount;
	private String pay_option;
	private String o_status;
	private String o_date;
	private String c_date;
	private String re_name;
	private String re_tel;
	private String re_zipcode;
	private String re_addr;
	private String re_deaddr;
	private String re_req;
	private String note;
	
	// DetailOrder
	private int de_id;
	private ArrayList<Integer> p_id;
	private ArrayList<Integer> d_cnt;
	private ArrayList<Integer> price;
	
	// Reservation
	private int re_id;
	private int d_id;
	private String r_date;
	private int r_time;
	
	// Coupon
	private int co_id;
	private int cd_id;
	private int c_discount;
	
	private String name;
	private List<String> p_name;
	private List<String> p_img;
	private String b_name;		// 구매 상품 이름
	private String b_cnt;		// 구매 상품 수량
	private String b_price;		// 구매 상품 총 금액
}

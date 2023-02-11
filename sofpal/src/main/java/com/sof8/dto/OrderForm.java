package com.sof8.dto;

import java.util.ArrayList;

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
	private int c_id;
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
	
}

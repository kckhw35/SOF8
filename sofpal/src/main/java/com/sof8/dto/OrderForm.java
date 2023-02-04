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
	
	// DetailOrder
	private int de_id;
	private ArrayList<Integer> p_id;
	private ArrayList<Integer> d_cnt;
	private ArrayList<Integer> price;
	
	// Reservation
	private int re_id;
	private int d_id;
	private String date;
	private String time;
	private String deliver_date;
	
	
	public OrderForm(int o_id, String user_id, int c_id, int totalprice, int totaldiscount, String pay_option,
			String o_status, String o_date, String c_date, String re_name, String re_tel, String re_zipcode,
			String re_addr, String re_deaddr, String re_req) {
		super();
		this.o_id = o_id;
		this.user_id = user_id;
		this.c_id = c_id;
		this.totalprice = totalprice;
		this.totaldiscount = totaldiscount;
		this.pay_option = pay_option;
		this.o_status = o_status;
		this.o_date = o_date;
		this.c_date = c_date;
		this.re_name = re_name;
		this.re_tel = re_tel;
		this.re_zipcode = re_zipcode;
		this.re_addr = re_addr;
		this.re_deaddr = re_deaddr;
		this.re_req = re_req;
	}
	
	public OrderForm(int de_id, ArrayList<Integer> p_id, ArrayList<Integer> d_cnt, ArrayList<Integer> price) {
		super();
		this.de_id = de_id;
		this.p_id = p_id;
		this.d_cnt = d_cnt;
		this.price = price;
	}

	public OrderForm(int re_id, int d_id, String date, String time, String deliver_date) {
		super();
		this.re_id = re_id;
		this.d_id = d_id;
		this.date = date;
		this.time = time;
		this.deliver_date = deliver_date;
	}
	
}

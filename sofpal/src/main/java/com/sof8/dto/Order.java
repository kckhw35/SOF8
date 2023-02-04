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
public class Order {
	private int o_id;
	private String user_id;
	private int c_id;
	private int totalprice;
	private int discount;
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
}

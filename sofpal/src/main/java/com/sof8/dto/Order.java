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
	int o_id;
	String user_id;
	int c_id;
	int totalprice;
	int discount;
	String pay_option;
	String o_status;
	String o_date;
	String c_date;
	String re_name;
	String re_tel;
	String re_zipcode;
	String re_addr;
	String re_deaddr;
	String re_req;
}

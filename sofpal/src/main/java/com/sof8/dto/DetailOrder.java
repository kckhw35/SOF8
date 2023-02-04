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
public class DetailOrder {
	private int de_id;
	private int o_id;
	private int p_id;
	private int d_cnt;
	private int price;
}

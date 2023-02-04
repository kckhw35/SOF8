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
}

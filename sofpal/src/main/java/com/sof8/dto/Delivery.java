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
public class Delivery {
	private int d_id;
	private String d_name;
	private String d_tel;
	private float longitude;
	private float latitude;
}

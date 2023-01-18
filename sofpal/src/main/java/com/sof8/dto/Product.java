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
public class Product {
	int p_id;
	int cat_id;
	String p_name;
	String p_content;
	String p_img;
	int price;
	int del_cost;
	int cnt;
	int discount;
	int size;
	String color;
	boolean visiable;
}

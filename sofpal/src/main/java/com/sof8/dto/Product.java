package com.sof8.dto;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

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
	private int p_id;
	private int cat_id;
	private String p_name;
	private String p_content;
	private String p_img;
	private int price;
	private int del_cost;
	private int p_cnt;
	private int discount;
	private String size;
	private String color;
	private boolean visiable;
	
	private List<MultipartFile> img_product;	// 상품 이미지
	private List<MultipartFile> img_list;		// 상품 설명 이미지
	private List<String> img_namelist;
	private String main_img;
	private String cat_name;
	
}

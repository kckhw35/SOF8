package com.sof8.dto;

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
public class Mark {
	// 찜 테이블
	private int m_id;			// 찜 아이디
	// 사용자 테이블
	private String user_id;		// 사용자 아이디
	// 상품 테이블
	private int p_id;			// 상품 아이디
	private String p_img;		// 상품 사진명
	private String p_name;		// 상품명
	private String p_content;	// 상품 정보
	private String color;		// 색상
	private MultipartFile img;	// 상품 이미지
	private int price;			// 가격
	private int del_cost;		// 배송비
	private int discount;		// 할인
}

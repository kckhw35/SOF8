package com.sof8.dto;

import java.util.Date;

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
public class Member {
	private String user_id;
	private String pwd;
	private String name;
	private String email;
	private String tel;
	private String postcode;
	private String addr;
	private String de_addr;
	private Date joindate;
	private Boolean enable;
	
	private String edit_pwd; 
	private Boolean save_id; 
	private String auth;
	
}

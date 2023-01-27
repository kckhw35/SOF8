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
public class Admin {
	private String admin_id;
	private String admin_pwd;
	private String admin_name;
	
	private String edit_pwd; 
	private Boolean save_id;
}

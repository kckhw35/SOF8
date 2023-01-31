package com.sof8.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import com.sof8.dto.Admin;
import com.sof8.frame.Sof8Mapper;

@Repository
@Mapper
public interface AdminMapper extends Sof8Mapper<String, Admin>{

	public void updateEnable(String k) throws Exception;
	
}

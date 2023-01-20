package com.sof8.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import com.sof8.dto.Member;
import com.sof8.frame.Sof8Mapper;

@Repository
@Mapper
public interface MemberMapper extends Sof8Mapper<String, Member>{

	public void updateEnable(String k) throws Exception;
	
}

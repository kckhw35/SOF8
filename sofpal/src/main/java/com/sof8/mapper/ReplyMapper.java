package com.sof8.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import com.sof8.dto.Reply;
import com.sof8.frame.Sof8Mapper;

@Repository
@Mapper
public interface ReplyMapper extends Sof8Mapper<Integer, Reply>{
	
	public Reply selectReply(Integer k) throws Exception;
}
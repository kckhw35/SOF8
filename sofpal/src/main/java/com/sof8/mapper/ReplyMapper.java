package com.sof8.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import com.sof8.dto.Reply;
import com.sof8.frame.Sof8Mapper;

@Repository
@Mapper
public interface ReplyMapper extends Sof8Mapper<Integer, Reply>{
	
	//reply table 의 PK(re_id)가 아닌 Qna의 PK를 가지고 reply 조회
	public Reply selectReply(Integer k) throws Exception;
}
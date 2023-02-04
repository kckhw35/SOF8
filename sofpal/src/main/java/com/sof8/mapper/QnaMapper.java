package com.sof8.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import com.sof8.dto.Paging;
import com.sof8.dto.Qna;
import com.sof8.frame.Sof8Mapper;

@Repository
@Mapper
public interface QnaMapper extends Sof8Mapper<Integer, Qna>{
	
	//페이징을 위한 메소드
	public List<Qna> selectList(Paging paging) throws Exception;
	
	// 페이징을 위한 전체 회원 수
	public int getTotal(Map<String, Object> map) throws Exception;
}
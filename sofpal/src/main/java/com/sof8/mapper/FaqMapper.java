package com.sof8.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import com.sof8.dto.Faq;
import com.sof8.dto.Paging;
import com.sof8.frame.Sof8Mapper;

@Repository
@Mapper
public interface FaqMapper extends Sof8Mapper<Integer, Faq>{
	
	//페이징을 위한 메소드
	public List<Faq> selectList(Paging paging) throws Exception;
	
	// 페이징을 위한 전체 회원 수
	public int getTotal(Map<String, Object> map) throws Exception;

}
package com.sof8.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import com.sof8.dto.Paging;
import com.sof8.dto.Review;
import com.sof8.frame.Sof8Mapper;

@Repository
@Mapper
public interface ReviewMapper extends Sof8Mapper<Integer, Review>{
	
	//페이징 처리
	public List<Review> selectList(Paging paging) throws Exception;
	
	public int getTotal(Map<String, Object> map) throws Exception;
}
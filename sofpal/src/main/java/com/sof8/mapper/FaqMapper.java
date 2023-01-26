package com.sof8.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import com.sof8.dto.Faq;
import com.sof8.frame.Sof8Mapper;

@Repository
@Mapper
public interface FaqMapper extends Sof8Mapper<Integer, Faq>{
	
	public List<Faq> optionSelect(Map<String, Object> searchInfo) throws Exception;

}
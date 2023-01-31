package com.sof8.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import com.sof8.dto.Notice;
import com.sof8.frame.Sof8Mapper;

@Repository
@Mapper
public interface NoticeMapper extends Sof8Mapper<Integer, Notice>{
	
	public List<Notice> optionSelect(Map<String, Object> searchInfo) throws Exception;

}
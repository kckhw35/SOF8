package com.sof8.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import com.sof8.dto.Mark;
import com.sof8.dto.Paging;
import com.sof8.frame.Sof8Mapper;

@Repository
@Mapper
public interface MarkMapper extends Sof8Mapper<Integer, Mark>{
	
	// 페이징을 위한 메소드
	public List<Mark> selectList(Paging paging) throws Exception;
	
	// 페이징을 위한 전체 찜목록 수
	public int getTotal(String user_id) throws Exception;
	
}

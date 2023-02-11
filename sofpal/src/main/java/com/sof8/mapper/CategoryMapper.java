package com.sof8.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import com.sof8.dto.Category;
import com.sof8.frame.Sof8Mapper;

@Repository
@Mapper
public interface CategoryMapper extends Sof8Mapper<Integer, Category>{
	public String selectcatname(Integer K) throws Exception;
}

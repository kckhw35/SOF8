package com.sof8.mapper;

import java.util.HashMap;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import com.sof8.dto.DetailOrder;
import com.sof8.frame.Sof8Mapper;

@Repository
@Mapper
public interface DetailOrderMapper extends Sof8Mapper<Integer, DetailOrder>{
	public int selectTodayProducts() throws Exception;
	public int selectWeekProducts() throws Exception;
	public int selectMonthProducts() throws Exception;
	public int selectYearProducts() throws Exception;
	public HashMap<String, Object> selectTodayCategoryProducts() throws Exception;
	public HashMap<String, Object> selectWeekCategoryProducts() throws Exception;
	public HashMap<String, Object> selectMonthCategoryProducts() throws Exception;
	public HashMap<String, Object> selectYearCategoryProducts() throws Exception;
}

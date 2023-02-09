package com.sof8.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import com.sof8.dto.DetailOrder;
import com.sof8.frame.Sof8Mapper;

@Repository
@Mapper
public interface DetailOrderMapper extends Sof8Mapper<Integer, DetailOrder>{
}

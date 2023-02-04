package com.sof8.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import com.sof8.dto.Schedule;
import com.sof8.frame.Sof8Mapper;

@Repository
@Mapper
public interface ScheduleMapper extends Sof8Mapper<Integer, Schedule>{
	public Schedule confirmschedule() throws Exception;
	public int schedulecnt() throws Exception;
	public List<Schedule> checktime(String s_date) throws Exception;
}

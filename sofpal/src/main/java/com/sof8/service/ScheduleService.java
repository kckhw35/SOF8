package com.sof8.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sof8.dto.Schedule;
import com.sof8.frame.Sof8Service;
import com.sof8.mapper.ScheduleMapper;

@Service
public class ScheduleService implements Sof8Service<Integer, Schedule>{

	@Autowired
	ScheduleMapper mapper;

	@Override
	public void register(Schedule v) throws Exception {
		mapper.insert(v);
	}

	@Override
	public void remove(Integer k) throws Exception {
		mapper.delete(k);
	}

	@Override
	public void modify(Schedule v) throws Exception {
		mapper.update(v);
	}

	@Override
	public Schedule get(Integer k) throws Exception {
		return mapper.select(k);
	}

	@Override
	public List<Schedule> get() throws Exception {
		return mapper.selectall();
	}
	
	public Schedule confirmschedule() throws Exception{
		return mapper.confirmschedule();
	}
	
	public int schedulecnt() throws Exception{
		return mapper.schedulecnt();
	}
	
	public List<Schedule> checktime(String s_date) throws Exception{
		return mapper.checktime(s_date);
	}
}

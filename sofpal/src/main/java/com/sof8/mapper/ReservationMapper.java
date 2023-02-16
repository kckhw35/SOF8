package com.sof8.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import com.sof8.dto.Reservation;
import com.sof8.frame.Sof8Mapper;

@Repository
@Mapper
public interface ReservationMapper extends Sof8Mapper<Integer, Reservation>{

	public int selectTodayDeliverys() throws Exception;
	public List<Reservation> gettodaydelivery() throws Exception;
	public List<Reservation> getreservelist() throws Exception;
	public List<Reservation> getmonthdelivery() throws Exception;
}

package com.sof8.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import com.sof8.dto.Review;
import com.sof8.frame.Sof8Mapper;

@Repository
@Mapper
public interface ReviewMapper extends Sof8Mapper<Integer, Review>{
	
	//상품정보를 받아서 그에 대한 리뷰 가져오기
	public List<Review> selectReviewByProductId(Map<String, Object> map) throws Exception;
	
	//나중에 상세주문 번호를 통해 사용자가 리뷰 작성했나 확인
	public Review selectReviewByDeId(int de_id) throws Exception;
	
	public int getTotal(Map<String, Object> map) throws Exception;
}
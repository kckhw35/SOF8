package com.sof8.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sof8.dto.Paging;
import com.sof8.dto.Review;
import com.sof8.frame.Sof8Service;
import com.sof8.mapper.ReviewMapper;

@Service
public class ReviewService implements Sof8Service<Integer, Review>{

	@Autowired
	ReviewMapper mapper;

	@Override
	public void register(Review v) throws Exception {
		mapper.insert(v);
	}

	@Override
	public void remove(Integer k) throws Exception {
		mapper.delete(k);
	}


	@Override
	public void modify(Review v) throws Exception {
		mapper.update(v);
	}

	@Override
	public Review get(Integer k) throws Exception {
		return mapper.select(k);
	}

	@Override
	public List<Review> get() throws Exception {
		return mapper.selectall();
	}
	
	public List<Review> getReviewByProductId(Paging paging, int p_id) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("paging", paging);
		map.put("p_id", p_id);
		return mapper.selectReviewByProductId(map);
	}
	
	public Review getReviewByDetailId(int de_id) throws Exception{
		return mapper.selectReviewByDeId(de_id);
	}
	
	public Boolean canWriteReview(int de_id) throws Exception{
		Review review = mapper.selectReviewByDeId(de_id);
		/* 만약 리뷰가 없다면 True: 리뷰 작성 가능 */
		return (review == null);
	}
	
	
	public int getTotal(String keyword, String type, int p_id) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("p_id", p_id);
		map.put("keyword", keyword);
		map.put("type", type);
		return mapper.getTotal(map);
	}
}

package com.sof8.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sof8.dto.Faq;
import com.sof8.dto.Paging;
import com.sof8.frame.Sof8Service;
import com.sof8.mapper.FaqMapper;

@Service
public class FaqService implements Sof8Service<Integer, Faq>{

	@Autowired
	FaqMapper mapper;

	@Override
	public void register(Faq v) throws Exception {
		mapper.insert(v);
	}

	@Override
	public void remove(Integer k) throws Exception {
		mapper.delete(k);
	}

	@Override
	public void modify(Faq v) throws Exception {
		mapper.update(v);
	}

	@Override
	public Faq get(Integer k) throws Exception {
		return mapper.select(k);
	}

	@Override
	public List<Faq> get() throws Exception {
		return mapper.selectall();
	}
	
	public List<Faq> getList(Paging paging) throws Exception {
		return mapper.selectList(paging);
	}
	
	public int getTotal(String keyword, String type) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("keyword", keyword);
		map.put("type", type);
		return mapper.getTotal(map);
	}
	
}

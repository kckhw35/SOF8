package com.sof8.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sof8.dto.Notice;
import com.sof8.frame.Sof8Service;
import com.sof8.mapper.NoticeMapper;

@Service
public class NoticeService implements Sof8Service<Integer, Notice>{

	@Autowired
	NoticeMapper mapper;

	@Override
	public void register(Notice v) throws Exception {
		mapper.insert(v);
	}

	@Override
	public void remove(Integer k) throws Exception {
		mapper.delete(k);
	}

	@Override
	public void modify(Notice v) throws Exception {
		mapper.update(v);
	}

	@Override
	public Notice get(Integer k) throws Exception {
		return mapper.select(k);
	}

	@Override
	public List<Notice> get() throws Exception {
		return mapper.selectall();
	}
	
	public List<Notice> getSearchedNotice(String searchOption, String keyword) throws Exception {
		Map<String, Object> searchInfo = new HashMap<String, Object>();
		searchInfo.put("searchOption", searchOption);
		searchInfo.put("keyword", keyword);
		
		return mapper.optionSelect(searchInfo);
	}
	
}

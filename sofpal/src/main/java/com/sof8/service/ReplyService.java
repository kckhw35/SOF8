package com.sof8.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sof8.dto.Reply;
import com.sof8.frame.Sof8Service;
import com.sof8.mapper.ReplyMapper;

@Service
public class ReplyService implements Sof8Service<Integer, Reply>{

	@Autowired
	ReplyMapper mapper;

	@Override
	public void register(Reply v) throws Exception {
		mapper.insert(v);
	}

	@Override
	public void remove(Integer k) throws Exception {
		mapper.delete(k);
	}

	@Override
	public void modify(Reply v) throws Exception {
		mapper.update(v);
	}

	@Override
	public Reply get(Integer k) throws Exception {
		return mapper.select(k);
	}

	@Override
	public List<Reply> get() throws Exception {
		return mapper.selectall();
	}
	
	public List<Reply> getReply(Integer k) throws Exception {
		return mapper.selectReply(k);
	}
}

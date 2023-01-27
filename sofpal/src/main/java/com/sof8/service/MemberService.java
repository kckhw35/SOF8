package com.sof8.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sof8.dto.Member;
import com.sof8.frame.Sof8Service;
import com.sof8.mapper.MemberMapper;

@Service
public class MemberService implements Sof8Service<String, Member>{

	@Autowired
	MemberMapper mapper;

	@Override
	public void register(Member v) throws Exception {
		mapper.insert(v);
	}

	@Override
	public void remove(String k) throws Exception {
		mapper.delete(k);
	}

	@Override
	public void modify(Member v) throws Exception {
		mapper.update(v);
	}

	@Override
	public Member get(String k) throws Exception {
		return mapper.select(k);
	}

	@Override
	public List<Member> get() throws Exception {
		return mapper.selectall();
	}

	public void modifyEnable(String k) throws Exception {
		mapper.updateEnable(k);
	}

	public Member getId(Member v) throws Exception {
		return mapper.selectMemberId(v);
	}

}

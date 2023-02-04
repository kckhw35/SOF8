package com.sof8.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import com.sof8.dto.Member;
import com.sof8.dto.Paging;
import com.sof8.frame.Sof8Mapper;

@Repository
@Mapper
public interface MemberMapper extends Sof8Mapper<String, Member>{

	// 페이징을 위한 메소드
	public List<Member> selectList(Paging paging) throws Exception;
	
	// 페이징을 위한 전체 회원 수
	public int getTotal(Map<String, Object> map) throws Exception;
	
	public void updatePwd(Member v) throws Exception;
	
	// 회원 상태 비활성화 메소드
	public void updateDisable(String k) throws Exception;

	// 회원 상태 활성화 메소드
	public void updateEnable(String k) throws Exception;
	
	// 이름과 이메일로 회원 검색
	public Member selectMemberId(Member v) throws Exception;	
	
}

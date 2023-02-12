package com.sof8.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@ToString
public class Paging {
	
	private int rowCount; 		// 한 페이지 당 보여줄 게시물 개수
	private int pageCount;		// 한 블럭에 몇 개의 페이지 개수
									// ex) 1 2 3 4 5 일 때 1을 의미.
	private int startPage = 1;		// 한 블럭의 시작 페이지: 기본 값 1
	private int endPage; 			// 한 블럭의 끝 페이지
	
	private int page; 				// 현재 페이지
	
	private int totalRow; 			// 총 게시물 개수
	private int totalPage; 			// 총 페이지 개수

	private boolean prev = false; // 다음 페이지로 이동하는 버튼 유무
	private boolean next = false; // 이전 페이지로 이동하는 버튼 유무

	private int offset; 			// 얼만큼 끊어서 가져올 것인가.
	
	private String keyword;
	private String type;
	private String first;
	private String last;		
	int cat_id = 0;
	
	public Paging(int rowCount, int pageCount, int totalRow, int page, String keyword, String type) {

		// 한 페이지 당 보여줄 게시물 개수
		setRowCount(rowCount);
		
		// 한 블럭에 몇 개의 페이지 개수
		setPageCount(pageCount);
		
		// 총 게시물 개수
		setTotalRow(totalRow);
		
		// 총 페이지 개수 구하기
		setTotalPage(totalRow, this.rowCount);

		// 한 블럭의 첫 페이지 구하기
		setStartPage(this.startPage, page, this.pageCount);

		// 한 블럭의 끝 페이지 구하기
		setEndpage(this.startPage, this.pageCount, this.totalPage);

		// 현재 페이지 구하기
		setPage(page);
		
		// 이전 블록 버튼 유무 판별하기
		isPrev(page, this.pageCount);

		// 다음 블록 버튼 유무 판별하기
		isNext(this.endPage, this.totalPage);

		// offset 구하기
		setOffset(page, this.rowCount);
		
		setKeyword(keyword);
		
		setType(type);
	}
	
	// 총 페이지 개수 구하기
	private void setTotalPage(final int totalCount, final int rowCount) {
		this.totalPage = (int) Math.ceil(totalCount * 1.0 / rowCount);
	}

	// 한 블럭의 첫 페이지 구하기
	private void setStartPage(final int startPage, final int page, final int pageCount) {
		this.startPage = startPage + (((page - startPage) / pageCount) * pageCount);
	}

	// 한 블럭의 끝 페이지 구하기
	private void setEndpage(final int startPage, final int pageCount, final int totalPageCount) {
		this.endPage = ((startPage - 1) + pageCount) < totalPageCount ? (startPage - 1) + pageCount : totalPageCount;
	}
	
	// 현재 페이지 구하기
	public void setPage(final int page) {
		this.page = page;
	}
	
	// 이전 블럭으로 이동할 버튼 생성 유무
	private void isPrev(final int page, final int pageCount) {
		this.prev = 1 < ((page * 1.0) / pageCount);
	}

	// 다음 블럭으로 이동할 버튼 생성 유무
	private void isNext(final int endPage, final int totalPageCount) {
		this.next = endPage < totalPageCount;
	}

	// offset 구하기 // 쿼리 select 시 끊어서 가져오기
	private void setOffset(final int page, final int rowCount) {
		this.offset = (page - 1) * rowCount;
		if(this.offset<0)	this.offset = this.offset * -1;
	}
}

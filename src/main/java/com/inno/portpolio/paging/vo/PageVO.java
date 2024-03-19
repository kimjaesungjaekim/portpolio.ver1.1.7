package com.inno.portpolio.paging.vo;

import lombok.Data;

@Data
public class PageVO {
	
	private int pageNum;
	private int countPerPage;
	private int pageCnt; 
	
	public int getPageStart() {
	        return (pageNum-1)*countPerPage;
	}
	
	private String keyword;
	private String condition;

	
	public PageVO() {
		this.pageNum =1;
		this.countPerPage=10;
	}
}

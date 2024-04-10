package com.inno.portpolio.paging.vo;

import lombok.Data;

@Data
public class PaginationVO {
	
	private int page;
	private int pageSize;
	private int totRecCnt;
	private int totPage;
	private int startIndexNo;
	private int curScrStartNo;
	private int blockSize;
	private int curBlock;
	private int lastBlock;
	
	private String part;
	private String searchStr;
}

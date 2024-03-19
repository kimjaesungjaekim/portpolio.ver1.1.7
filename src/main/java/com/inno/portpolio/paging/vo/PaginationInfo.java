package com.inno.portpolio.paging.vo;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.inno.portpolio.paging.BootstrapPaginationRenderer;
import com.inno.portpolio.paging.PaginationRender;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;


/**
* @author 연구개발 5팀 사원 김재성
* @since 2024. 03. 11.
* @version 1.0
* @see javax.servlet.http.HttpServlet 
* <pre>
* [[개정이력(Modification Information)]]
*    수정일            수정자               수정내용
* ------------     --------    ----------------------
* 2024.03.11.        김재성       최초작성
* Copyright (c) 2024 by INNOVATION-T All right reserved
* </pre>
*/


@Getter
@ToString
@NoArgsConstructor	//기본생성자 생성
@JsonIgnoreProperties("renderer")
public class PaginationInfo<T> implements Serializable {
	
	private int totalRecord;	
	private int currentPage;
	
	private int screenSize = 10;
	private int blockSize = 5;
	
	private int totalPage;
	private int startRow ;
	private int endRow;
	private int startPage;
	private int endPage ;
	
	private List<T> dataList;	// 다른곳에서도 사용하기위해서!
	
	private SearchVO simpleCondition;	// 단순 키워드 검색조건
	private T detailCondition;
	
private PaginationRender renderer = new BootstrapPaginationRenderer();
	
	public int getPageSize() {
	    return this.screenSize;
	}

	public void setDetailCondition(T detailCondition) {
		this.detailCondition = detailCondition;
	}
	
	public void setSimpleCondition(SearchVO simpleCondition) {
		this.simpleCondition = simpleCondition;
	}
	
	public void setRenderer(PaginationRender renderer) {
		this.renderer = renderer;
	}
	
	public void setDataList(List<T> dataList) {
		this.dataList = dataList;
	}
	
	public void setTotalRecord(int totalRecord) {
		this.totalRecord = totalRecord;
		totalPage = (totalRecord +(screenSize - 1)) / screenSize;	
		//totalPage는 setter가 있으면 안됨
	}
	
	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
		endRow = currentPage * screenSize;
		startRow = endRow - (screenSize - 1);
		endPage = blockSize * ((currentPage + (blockSize - 1)) / blockSize);
		startPage = endPage - (blockSize - 1);
		// endRow, startRow, endPage,startPage는 setter가 있으면 안됨
	}
	
	public int getEndPage() {
		//endPage가 totalPage보다 크면 endPage 작으면 totalPage
		return endPage < totalPage ? endPage : totalPage;
	}
	
	public String getPagingHTML(){
		return renderer.renderPagination(this);
	}
	
}

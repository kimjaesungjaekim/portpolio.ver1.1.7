package com.inno.portpolio.paging;

import com.inno.portpolio.paging.vo.PaginationInfo;

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

public class DefaultPaginationRenderer implements PaginationRender {
	private final String PATTERN = "<a href='javascript:;' onclick='fn_paging(%d);'>%s</a>";
	@Override
	public String renderPagination(PaginationInfo<?> paging) {
		int startPage = paging.getStartPage();
		int endPage = paging.getEndPage();
		int totalPage = paging.getTotalPage();
		StringBuffer html = new StringBuffer();
		
		if(startPage > 1) {
			html.append(
				String.format(PATTERN, startPage-1, "이전")
			);
		}
		
		for(int page = startPage; page <= endPage; page++) {
			html.append(
				String.format(PATTERN, page, page)
			);
		}
		
		if(endPage < totalPage) {
			html.append(
				String.format(PATTERN, endPage+1, "다음")
			);
		}
		
		return html.toString();
	}
}

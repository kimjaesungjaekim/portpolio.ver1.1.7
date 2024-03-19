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

public interface PaginationRender {
	
	/**
	 * PaginationInfo의 프로퍼티(startPage~endPage)에 따라 링크를 생성
	 * @param paging
	 * @return
	 */
	public String renderPagination(PaginationInfo<?> paging);
}

package com.inno.portpolio.paging.vo;

import lombok.AllArgsConstructor;
import lombok.Data;


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
@Data
@AllArgsConstructor
public class SearchVO {
	
	private String searchType;
	private String searchWord;
}

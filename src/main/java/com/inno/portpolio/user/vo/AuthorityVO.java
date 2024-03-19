package com.inno.portpolio.user.vo;

import javax.validation.constraints.NotBlank;

import com.inno.portpolio.common.validation.InsertGroup;

import lombok.Data;
import lombok.EqualsAndHashCode;
/**
* @author 연구개발 5팀 사원 김재성
* @since 2024. 03. 07.
* @version 1.0
* @see javax.servlet.http.HttpServlet 
* <pre>
* [[개정이력(Modification Information)]]
*    수정일            수정자               수정내용
* ------------     --------    ----------------------
* 2023.12.02.        김재성       최초작성
* Copyright (c) 2024 by INNOVATION-T All right reserved
* </pre>
*/

@Data
@EqualsAndHashCode(of="userId")
public class AuthorityVO {

	@NotBlank(groups = InsertGroup.class)
	private String userId;
	
	@NotBlank
	private String authNm;
}

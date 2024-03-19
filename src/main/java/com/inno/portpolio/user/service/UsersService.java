package com.inno.portpolio.user.service;

import com.inno.portpolio.common.enumpkg.ServiceResult;
import com.inno.portpolio.user.vo.UserVO;

/**
 *
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

public interface UsersService {
	
	/**
	 * 회원가입
	 * @param user
	 * @return
	 */
	public ServiceResult register(UserVO user);
}

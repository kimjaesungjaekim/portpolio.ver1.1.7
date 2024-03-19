package com.inno.portpolio.user.mapper;

import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.inno.portpolio.user.vo.AuthorityVO;
import com.inno.portpolio.user.vo.UserVO;
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
@Repository
@Mapper
public interface UsersMapper {
	
	/**
	 * 회원가입 
	 * @param user
	 * @return
	 */
	public int register(UserVO user);
	
	/**
	 * 회원가입 시 권한 등록
	 * @param authVO
	 * @return
	 */
	public int authorityRegister(AuthorityVO authVO);
	
	/**
	 * 로그인 시큐리티 인증 인가
	 * @param useId
	 * @return <key , value>
	 */
	public Map<String, Object> loadUserByUsername (@Param("userId") String userId);
}

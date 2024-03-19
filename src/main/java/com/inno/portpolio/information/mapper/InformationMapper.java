package com.inno.portpolio.information.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.inno.portpolio.information.vo.AcademicCareerVO;
import com.inno.portpolio.information.vo.CareerVO;
import com.inno.portpolio.information.vo.CertificateOfQualificationVO;
import com.inno.portpolio.information.vo.SkilVO;
import com.inno.portpolio.user.vo.UserVO;

/**
* @author 연구개발 5팀 사원 김재성
* @since 2024. 03. 08.
* @version 1.0
* @see javax.servlet.http.HttpServlet 
* <pre>
* [[개정이력(Modification Information)]]
*    수정일            수정자               수정내용
* ------------     --------    ----------------------
* 2024.03.08.        김재성       최초작성
* Copyright (c) 2024 by INNOVATION-T All right reserved
* </pre>
*/
@Mapper
public interface InformationMapper {
	
	/**
	 * 인포메이션 메뉴 클릭 후 메인 학력 정보 조회 출력
	 * @return
	 */
	public List<AcademicCareerVO> selectSchoolInformation();
	/**
	 * 인포메이션 메뉴 클릭 후 메인 경력 정보 조회 출력
	 * @return
	 */
	public List<CareerVO> selectCareerInformation();
	/**
	 * 인포메이션 메뉴 클릭 후 메인 자격증 정보 조회 출력
	 * @return
	 */
	public List<CertificateOfQualificationVO> selectCertificateInformation();
	/**
	 * 인포메이션 메뉴 클릭 후 메인 기술 정보 조회 출력
	 * @return
	 */
	public List<SkilVO> selectSkilInformation();
	
	
	/**
	 * 관리자 로그인 후 프로필 정보 수정
	 * @param users
	 * @return
	 */
	public int updateProfileInformation(UserVO users);
	
	/**
	 * 경력 추가
	 * @param career
	 * @return
	 */
	public int insertCareer(CareerVO career);
	
	/**
	 * 경력 삭제
	 * @param career
	 * @return
	 */
	public int deleteCareer(CareerVO career);
	
	/**
	 * 학력 추가
	 * @param career
	 * @return
	 */
	public int insertSchool(AcademicCareerVO school);
	
	/**
	 * 학력 삭제
	 * @param career
	 * @return
	 */
	public int deleteSchool(AcademicCareerVO school);
	
	/**
	 * 자격증 추가
	 * @param certificate
	 * @return
	 */
	public int insertCertificate(CertificateOfQualificationVO certificate);
	
	/**
	 * 자격증 삭제
	 * @param certificate
	 * @return
	 */
	public int deleteCertificate(CertificateOfQualificationVO certificate);
	
	/**
	 * 기술 추가
	 * @param skil
	 * @return
	 */
	public int insertSkil(SkilVO skil);
	
	/**
	 * 기술 삭제
	 * @param skil
	 * @return
	 */
	public int deleteSkil(SkilVO skil);
}

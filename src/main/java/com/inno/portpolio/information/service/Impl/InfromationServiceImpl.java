package com.inno.portpolio.information.service.Impl;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.inno.portpolio.common.enumpkg.ServiceResult;
import com.inno.portpolio.information.mapper.InformationMapper;
import com.inno.portpolio.information.service.InformationService;
import com.inno.portpolio.information.vo.AcademicCareerVO;
import com.inno.portpolio.information.vo.CareerVO;
import com.inno.portpolio.information.vo.CertificateOfQualificationVO;
import com.inno.portpolio.information.vo.SkilVO;
import com.inno.portpolio.user.vo.UserVO;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
* 2024.03.10.        김재성       정보 경력,학력,자격증,기술 로직 구현
* Copyright (c) 2024 by INNOVATION-T All right reserved
* </pre>
*/
@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class InfromationServiceImpl implements InformationService {
	
	private final InformationMapper informationMapper;
	
	@Override
	public List<AcademicCareerVO> retrieveSchoolInformation() {
		
		List<AcademicCareerVO> schoolInfo = informationMapper.selectSchoolInformation();
		
		log.info("serviceImpl 에서 schoolInfo 객체 확인 : {}",schoolInfo);
		
		return schoolInfo;
	}
	
	@Override
	public List<CareerVO> retrieveCareerInformation() {
		
		List<CareerVO> careerInfo = informationMapper.selectCareerInformation();
		
		log.info("serviceImpl 에서 careerInfo 객체 확인 : {}",careerInfo);
		
		return careerInfo;
	}

	@Override
	public List<CertificateOfQualificationVO> retrieveCertificateInformation() {
		
		List<CertificateOfQualificationVO> certificateInfo = informationMapper.selectCertificateInformation();
		
		log.info("serviceImpl 에서 certificateInfo 객체 확인 : {}",certificateInfo);
		
		return certificateInfo;
	}

	@Override
	public List<SkilVO> retrieveSkilInformation() {
		
		List<SkilVO> skilInfo = informationMapper.selectSkilInformation();
		
		log.info("serviceImpl 에서 skilInfo 객체 확인 : {}",skilInfo);
		
		return skilInfo;
	}
	
	@Override
	public ServiceResult modifyProfileInformation(UserVO uesr) {
		int cnt=0;
		ServiceResult res;
		
		cnt = informationMapper.updateProfileInformation(uesr);
		
		if(cnt >0) {
			res = ServiceResult.OK;
		}else {
			res = ServiceResult.FAIL;
		}
		
		return res;
	}
	
	@Override
	public ServiceResult createCareer(CareerVO career) {
		
		int cnt=0;
		ServiceResult res;
		
		career.setInfoNo("CA");
		
		cnt = informationMapper.insertCareer(career);
		
		if(cnt >0) {
			res = ServiceResult.OK;
		}else {
			res = ServiceResult.FAIL;
		}
		
		return res;
	}

	@Override
	public ServiceResult removeCareer(CareerVO career) {
		int cnt=0;
		ServiceResult res;
		
		cnt = informationMapper.deleteCareer(career);
		
		if(cnt >0) {
			res = ServiceResult.OK;
		}else {
			res = ServiceResult.FAIL;
		}
		
		return res;
	}

	@Override
	public ServiceResult createSchool(AcademicCareerVO school) {
		int cnt=0;
		ServiceResult res;
		
		school.setInfoNo("SC");
		
		cnt = informationMapper.insertSchool(school);
		
		if(cnt >0) {
			res = ServiceResult.OK;
		}else {
			res = ServiceResult.FAIL;
		}
		
		return res;
	}
	
	@Override
	public ServiceResult removeSchool(AcademicCareerVO school) {
		int cnt=0;
		ServiceResult res;
		
		cnt = informationMapper.deleteSchool(school);
		
		if(cnt >0) {
			res = ServiceResult.OK;
		}else {
			res = ServiceResult.FAIL;
		}
		
		return res;
	}

	@Override
	public ServiceResult createCertificate(CertificateOfQualificationVO certificate) {
		int cnt=0;
		ServiceResult res;
		
		certificate.setInfoNo("CE");
		
		log.info("자격증 추가 등록 시 들어온 데이터 정보 확인 : {}",certificate);
		
		cnt = informationMapper.insertCertificate(certificate);
		
		if(cnt >0) {
			res = ServiceResult.OK;
		}else {
			res = ServiceResult.FAIL;
		}
		
		return res;
	}

	@Override
	public ServiceResult removeCertificate(CertificateOfQualificationVO certificate) {
		int cnt=0;
		ServiceResult res;
		
		cnt = informationMapper.deleteCertificate(certificate);
		
		if(cnt >0) {
			res = ServiceResult.OK;
		}else {
			res = ServiceResult.FAIL;
		}
		
		return res;
	}

	@Override
	public ServiceResult createSkil(SkilVO skil) {
		int cnt=0;
		ServiceResult res;
		
		skil.setInfoNo("SK");
		
		cnt = informationMapper.insertSkil(skil);
		
		if(cnt >0) {
			res = ServiceResult.OK;
		}else {
			res = ServiceResult.FAIL;
		}
		
		return res;
	}

	@Override
	public ServiceResult removeSkil(SkilVO skil) {
		int cnt=0;
		ServiceResult res;
		
		cnt = informationMapper.deleteSkil(skil);
		
		if(cnt >0) {
			res = ServiceResult.OK;
		}else {
			res = ServiceResult.FAIL;
		}
		
		return res;
	}

}

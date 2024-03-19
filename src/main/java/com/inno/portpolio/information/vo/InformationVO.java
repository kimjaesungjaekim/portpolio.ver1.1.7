package com.inno.portpolio.information.vo;

import java.util.List;

import javax.validation.constraints.NotBlank;

import lombok.Data;
import lombok.EqualsAndHashCode;

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
@Data
@EqualsAndHashCode(of="infoNo")
public class InformationVO {
	
	@NotBlank
	private String infoNo;
	@NotBlank
	private String infoNm;
	@NotBlank
	private String useId;
	
	// has 1:N 관계 
	private List<CareerVO> career;
	
	private List<CertificateOfQualificationVO> certificate;
	
	private List<AcademicCareerVO> acdmcr;
	
	private List<SkilVO> skil;
}

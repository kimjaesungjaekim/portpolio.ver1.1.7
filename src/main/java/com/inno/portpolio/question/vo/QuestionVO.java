package com.inno.portpolio.question.vo;

import java.time.LocalDateTime;
import java.util.List;

import javax.validation.constraints.NotBlank;

import org.springframework.web.multipart.MultipartFile;

import com.inno.portpolio.common.file.vo.AttachmentFileVO;

import lombok.Data;
import lombok.EqualsAndHashCode;
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
* 2024.03.12.        김재성       페이징 ROWNUM 프로터피 추가
* 2024.03.13.        김재성       파일 VO 프로퍼티 추가
* Copyright (c) 2024 by INNOVATION-T All right reserved
* </pre>
*/
@Data
@EqualsAndHashCode(of="qestnNo")
public class QuestionVO {
	
	private Integer rnum;
	
	@NotBlank
	private String qestnNo;
	@NotBlank
	private String userId;
	@NotBlank
	private String qestnContent;
	@NotBlank
	private String qestnTitle;
	@NotBlank
	private String qestnPassword;
	
	private String qestnAnswer;
	
	@NotBlank
	private LocalDateTime  qestnDate;
	
	private LocalDateTime  qestnAnswerDate;
	
	private String atchmnflNo;
	
	private List<MultipartFile> fileList;
	
	// 파일 여러개 받아올때
	private List<AttachmentFileVO> attachmentFile;

}

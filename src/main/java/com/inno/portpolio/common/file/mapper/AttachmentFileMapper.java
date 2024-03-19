package com.inno.portpolio.common.file.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.inno.portpolio.common.file.vo.AttachmentFileVO;
/**
* @author 연구개발 5팀 사원 김재성
* @since 2024. 03. 13.
* @version 1.0
* @see javax.servlet.http.HttpServlet 
* <pre>
* [[개정이력(Modification Information)]]
*    수정일            수정자               수정내용
* ------------     --------    ----------------------
* 2024.03.13.        김재성       최초 작성
* Copyright (c) 2024 by INNOVATION-T All right reserved
* </pre>
*/
@Mapper
public interface AttachmentFileMapper {
	
	/**
	 * 파일등록 리스트 조회 해오기
	 * @param atchmnflNo
	 * @return
	 */
	public List<AttachmentFileVO> selectAttachmentFile(String atchmnflNo);
	
	/**
	 * 개별 파일 다운로드 
	 * @param attachmentFile
	 * @return
	 */
	public AttachmentFileVO selectAttachmentFileOne(AttachmentFileVO attachmentFile);
	
	/**
	 * 첫 파일 등록
	 * @param attachmentFile
	 * @return
	 */
	public int firstInsertAttachmentFile(AttachmentFileVO attachmentFile);
	/**
	 * 이후 파일 등록
	 * @param attachmentFile
	 * @return
	 */
	public int afterInsertAttachmentFile(AttachmentFileVO attachmentFile);
	
	/**
	 * 파일 삭제
	 * @param attachmentFile
	 * @return
	 */
	public int deleteAttachmentFile(AttachmentFileVO attachmentFile);
	
	/**
	 * 파일 수정
	 * @param attachmentFile
	 * @return
	 */
	public int updateAttachmentFile(AttachmentFileVO attachmentFile);
}

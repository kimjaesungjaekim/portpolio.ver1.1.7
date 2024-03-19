package com.inno.portpolio.common.file.service.Impl;

import java.io.File;
import java.util.List;

import org.apache.ibatis.javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.inno.portpolio.common.file.mapper.AttachmentFileMapper;
import com.inno.portpolio.common.file.service.AttachmentFileService;
import com.inno.portpolio.common.file.vo.AttachmentFileVO;

import lombok.extern.slf4j.Slf4j;
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
@Service
@Slf4j
public class AttachmentFileServiceImpl implements AttachmentFileService {
	
	@Autowired
	private AttachmentFileMapper attachmentFileMapper;
	
	@Value("${file.upload.dir}")
	private String saveFolderPath;
	
	private File saveFolder;
	
	@Override
	public void firstCreateAttachmentFile(AttachmentFileVO attachmentFile) throws Exception {
		
		if(attachmentFile !=null && ! attachmentFile.getAtchmnflNm().equals("")) {
			attachmentFileMapper.firstInsertAttachmentFile(attachmentFile);
		}else {
			throw new Exception();
		}
		
	}
	@Override
	public void afterCreateAttachmentFile(AttachmentFileVO attachmentFile) throws Exception {
		
		if(attachmentFile !=null && ! attachmentFile.getAtchmnflNm().equals("")) {
			attachmentFileMapper.afterInsertAttachmentFile(attachmentFile);
		}else {
			throw new Exception();
		}
		
	}

	@Override
	public void deleteAttachmentFile(AttachmentFileVO attachmentFile) {
		
		attachmentFileMapper.deleteAttachmentFile(attachmentFile);
		
		
	}
	
	@Override
	public List<AttachmentFileVO> selectAttachmentFile(String atchmnflNo) {
		
		return attachmentFileMapper.selectAttachmentFile(atchmnflNo);
	}
	
	
	@Override
	public AttachmentFileVO retrieveAttachmentFileOne(AttachmentFileVO attachmentFile) throws NotFoundException {
		
			AttachmentFileVO attachmenfFileVO = attachmentFileMapper.selectAttachmentFileOne(attachmentFile);
			
			if (attachmenfFileVO != null) {
		        try {
		            File fileToSave = new File(saveFolder, attachmenfFileVO.getAtchmnflNm());
		            attachmenfFileVO.saveTo(fileToSave);
		        } catch (Exception e) {
		            throw new RuntimeException(e);
		        }
		    } else {
		        throw new NotFoundException(String.format("%d 해당하는 파일자료가 없음", attachmenfFileVO.getAtchmnflNm()));
		    }
		    
		    return attachmenfFileVO;
	}

}

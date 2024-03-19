package com.inno.portpolio.common.file.controller;

import java.io.File;
import java.net.URLDecoder;
import java.nio.charset.Charset;
import java.util.HashMap;

import org.apache.ibatis.javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.server.ResponseStatusException;

import com.inno.portpolio.common.file.service.AttachmentFileService;
import com.inno.portpolio.common.file.vo.AttachmentFileVO;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Controller
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/attachmentFile")
public class AttachmentFileController {
	
	private final AttachmentFileService attachmentService;
	
	@Value("${file.upload.dir}")
	private String saveFolderPath;
	
	// 파일 다운로드
	@GetMapping("/download/{atchmnflNo}/{atchmnflSn}")
	public ResponseEntity<Resource> attachmentFileDownload(
			 @PathVariable("atchmnflNo") String atchmnflNo
			,@PathVariable("atchmnflSn") Integer atchmnflSn
		) throws NotFoundException {
			
			log.info("String atchmnflNo,atchmnflSn 데이터 확인 : {} {}",atchmnflNo,atchmnflSn);
			AttachmentFileVO attachmentFileVO = new AttachmentFileVO();
			
			attachmentFileVO.setAtchmnflNo(atchmnflNo);
			attachmentFileVO.setAtchmnflSn(atchmnflSn);
			
			AttachmentFileVO fileDownload =  attachmentService.retrieveAttachmentFileOne(attachmentFileVO);
			
			if(fileDownload == null) {
				throw new ResponseStatusException(HttpStatus.NOT_FOUND,"해당파일이 존재하지 않습니다.");
			}
			
			String filePath = saveFolderPath + "/" + fileDownload.getAtchmnflStreNm();
			
			File file = new File(filePath);
			
			if (!file.exists()) {
			       throw new ResponseStatusException(HttpStatus.NOT_FOUND, "해당파일이 존재하지 않습니다.");
			}
			
			Resource fileResource = new FileSystemResource(file);
			
			org.springframework.http.HttpHeaders headers = new org.springframework.http.HttpHeaders();
			
			ContentDisposition disposition = ContentDisposition.attachment()
															   .filename(fileDownload.getAtchmnflNm(),Charset.defaultCharset())
															   .build();
			
			headers.setContentDisposition(disposition);
			headers.setContentLength(attachmentFileVO.getAtchmnflMg());
			headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
			
		return ResponseEntity.ok().headers(headers).body(fileResource);
	}
	
	@GetMapping("/delete/{atchmnflNo}/{atchmnflSn}/{atchmnflStreNm}")
	@Transactional
	public String removeAttachmentFile(
			@PathVariable("atchmnflNo") String atchmnflNo
			,@PathVariable("atchmnflSn") int atchmnflSn
			,@PathVariable("atchmnflStreNm") String atchmnflStreNm
		){
		
//		HashMap<String, Object> map = new HashMap<>();
		try {
			
			AttachmentFileVO attachmentFile = new AttachmentFileVO();
			String deleteFileName = "";
			
			attachmentFile.setAtchmnflNo(atchmnflNo);
			attachmentFile.setAtchmnflSn(atchmnflSn);
			attachmentFile.setAtchmnflStreNm(atchmnflStreNm);
			
			attachmentService.deleteAttachmentFile(attachmentFile);
			
			deleteFileName = URLDecoder.decode(atchmnflStreNm,"UTF-8");
			
			File file = new File(saveFolderPath + File.separator + deleteFileName );
			boolean result = file.delete();
			
			File parentFile = new File (file.getParent(),"s_" + file.getName());
			result = parentFile.delete();
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return "redirect:question/questionModifyForm/" + atchmnflNo ;
	}
	
	
}

package com.inno.portpolio.common.file.controller;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URLDecoder;
import java.nio.charset.Charset;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import org.apache.ibatis.javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
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
	public ResponseEntity<Resource> attachmentFileDownload(@PathVariable("atchmnflNo") String atchmnflNo,
			@PathVariable("atchmnflSn") Integer atchmnflSn) throws NotFoundException {

		log.info("String atchmnflNo,atchmnflSn 데이터 확인 : {} {}", atchmnflNo, atchmnflSn);
		AttachmentFileVO attachmentFileVO = new AttachmentFileVO();

		attachmentFileVO.setAtchmnflNo(atchmnflNo);
		attachmentFileVO.setAtchmnflSn(atchmnflSn);

		AttachmentFileVO fileDownload = attachmentService.retrieveAttachmentFileOne(attachmentFileVO);

		if (fileDownload == null) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "해당파일이 존재하지 않습니다.");
		}

		String filePath = saveFolderPath + "/" + fileDownload.getAtchmnflStreNm();

		File file = new File(filePath);

		if (!file.exists()) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "해당파일이 존재하지 않습니다.");
		}

		Resource fileResource = new FileSystemResource(file);

		org.springframework.http.HttpHeaders headers = new org.springframework.http.HttpHeaders();

		ContentDisposition disposition = ContentDisposition.attachment()
				.filename(fileDownload.getAtchmnflNm(), Charset.defaultCharset()).build();

		headers.setContentDisposition(disposition);
		headers.setContentLength(attachmentFileVO.getAtchmnflMg());
		headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);

		return ResponseEntity.ok().headers(headers).body(fileResource);
	}

	// 파일 zip 파일 다운로드
	@GetMapping("/download/zip/{qestnTitle}/{atchmnflNo}")
	public ResponseEntity<Resource> attachmentFileZipDownload(
			 @PathVariable("qestnTitle") String qestnTitle
			,@PathVariable("atchmnflNo") String atchmnflNo
		)throws IOException{
		
		log.info("ZIP 파일 다운로드 atchmnflNo 데이터 체크 : {} ", atchmnflNo);
		
		List<AttachmentFileVO> fileList = attachmentService.selectAttachmentFile(atchmnflNo);
		
		log.info("ZIP 파일 다운로드 fileList 데이터 체크 : {} ", fileList);
		
		if(fileList.isEmpty() && fileList ==null) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND,"파일이 존재하지 않습니다.");
		}
		
		ByteArrayOutputStream byteArrayOS =new ByteArrayOutputStream();
		try {
			ZipOutputStream zipOS = new ZipOutputStream(byteArrayOS);
			
			for(AttachmentFileVO attach : fileList) {
				
				File zipFile = new File(saveFolderPath + "/"+ attach.getAtchmnflStreNm()) ;
				
				try {
					FileInputStream fileIS = new FileInputStream(zipFile);
					
					ZipEntry zipEntry = new ZipEntry(attach.getAtchmnflNm());
					
					zipOS.putNextEntry(zipEntry);
					
					byte[] bytes = new byte[1024];
					int length;
					while((length = fileIS.read(bytes)) >= 0) {
						zipOS.write(bytes, 0, length);
					}
					zipOS.closeEntry();
					
				} catch (Exception e) {
					e.printStackTrace();
				  }
			}
		} catch (Exception e) {
			e.printStackTrace();
		  }
		
		byte[] zipBytes = byteArrayOS.toByteArray();
		Resource zipResource = new ByteArrayResource(zipBytes);
		
		HttpHeaders headers = new HttpHeaders();
		headers.setContentDisposition(ContentDisposition.attachment().filename(qestnTitle + ".zip").build());
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        headers.setContentLength(zipBytes.length);
			
		return ResponseEntity.ok().headers(headers).body(zipResource);
	}

	// 파일 삭제
	@GetMapping("/delete/{atchmnflNo}/{atchmnflSn}/{atchmnflStreNm}/{qestnNo}")
	@Transactional
	public String removeAttachmentFile(@PathVariable("atchmnflNo") String atchmnflNo,
			@PathVariable("atchmnflSn") int atchmnflSn, @PathVariable("atchmnflStreNm") String atchmnflStreNm,
			@PathVariable("qestnNo") String qestnNo) {

//		HashMap<String, Object> map = new HashMap<>();
		try {

			AttachmentFileVO attachmentFile = new AttachmentFileVO();
			String deleteFileName = "";

			attachmentFile.setAtchmnflNo(atchmnflNo);
			attachmentFile.setAtchmnflSn(atchmnflSn);

			attachmentService.deleteAttachmentFile(attachmentFile);

			deleteFileName = URLDecoder.decode(atchmnflStreNm, "UTF-8");

			File file = new File(saveFolderPath + File.separator + deleteFileName);
			boolean result = file.delete();

			File parentFile = new File(file.getParent(), "s_" + file.getName());
			result = parentFile.delete();

		} catch (Exception e) {
			e.printStackTrace();
		}

		return "redirect:/question/questionModifyForm/" + qestnNo;
	}

}

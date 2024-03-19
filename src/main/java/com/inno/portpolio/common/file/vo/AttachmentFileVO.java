package com.inno.portpolio.common.file.vo;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.nio.file.Files;
import java.util.Base64;
import java.util.UUID;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
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
@Data
@EqualsAndHashCode(of={"atchmnflNo","atchmnflSn"})
@NoArgsConstructor
public class AttachmentFileVO implements Serializable {
	
	private MultipartFile multipartFile;
	
	public AttachmentFileVO(MultipartFile qnaFile){
		super();
		this.multipartFile = qnaFile;
		this.atchmnflNm = qnaFile.getOriginalFilename();
		this.atchmnflMimeType = qnaFile.getContentType();
		this.atchmnflMg = qnaFile.getSize();
		this.atchmnflIndictMg = FileUtils.byteCountToDisplaySize(atchmnflMg);
		this.atchmnflStreNm = UUID.randomUUID().toString();
	}
	
	private String atchmnflNo;
	private Integer atchmnflSn;
	private String atchmnflNm;
	private String atchmnflStreNm;
	private String atchmnflStrePath;
	private long atchmnflMg;
	private String atchmnflIndictMg;
	private String atchmnflMimeType;
	
	public void saveTo(File saveFolder) throws IllegalStateException, IOException {
		if(multipartFile!=null) {
			multipartFile.transferTo(new File(saveFolder, atchmnflStreNm));
		}
	}
	
	private byte[] fileByteData;
	
	public void setFileByteData() throws IOException {
		if(StringUtils.isNotBlank(this.atchmnflStreNm)) {
			File file = new File(this.atchmnflStrePath, this.atchmnflStreNm);
			this.fileByteData = Files.readAllBytes(file.toPath());
		}
	}
	
	public String getBase64() {
        if(this.fileByteData != null) {
        	return Base64.getEncoder().encodeToString(this.fileByteData);
        }else {
        	return null;
        }
	}
}

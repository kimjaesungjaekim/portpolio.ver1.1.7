package com.inno.portpolio.question.service.Impl;

import java.io.File;
import java.io.IOException;
import java.net.URLDecoder;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.inno.portpolio.common.enumpkg.ServiceResult;
import com.inno.portpolio.common.file.service.AttachmentFileService;
import com.inno.portpolio.common.file.vo.AttachmentFileVO;
import com.inno.portpolio.paging.vo.PageVO;
import com.inno.portpolio.paging.vo.PaginationInfo;
import com.inno.portpolio.question.mapper.QuestionMapper;
import com.inno.portpolio.question.service.QuestionService;
import com.inno.portpolio.question.vo.QuestionVO;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
* 2024.03.19.        김재성       게시글 수정 파일 수정
* Copyright (c) 2024 by INNOVATION-T All right reserved
* </pre>
*/
@Service
@RequiredArgsConstructor
@Slf4j
public class QuestionServiceImpl implements QuestionService {
	
	private final QuestionMapper questionMapper;
	
	private final AttachmentFileService attachmentFileService;
	
	private File saveFolder;
	
	@Value("${file.upload.dir}")
	private String saveFolderPath;
	
	@PostConstruct
	public void setFileFolder() {
	    Path folderPath = Paths.get(saveFolderPath);
	    saveFolder = folderPath.toFile();

	    if (!saveFolder.exists()) {
	        try {
	            Files.createDirectories(folderPath);
	            log.info("생성된 폴더: {}", saveFolder.getAbsolutePath());
	        } catch (IOException e) {
	            log.error("폴더를 생성하지 못했습니다.", e);
	        }
	    }
	}
	
	// 질문사항 리스트 페이징 처리
	@Override
	public List<QuestionVO> retrieveQuestionList(PageVO pageVO) {
		
		log.info("질문사항 페이징 데이터 체크 : {}", pageVO);
		
		int totalRecord = questionMapper.selectQuestionTotalRecord(pageVO);
		
		pageVO.setCountPerPage(totalRecord);
		
		List<QuestionVO> dataList = questionMapper.selectQuestionList(pageVO);
		
		log.info("질문사항 페이징 DB에서 온 리스트 체크 : {}", dataList);
		
		return dataList;
		
	}
	
	@Override
	public PaginationInfo<QuestionVO> retrieveDifferentQuestionList(PaginationInfo<QuestionVO> paging) {
		
		log.info("질문사항 페이징 데이터 체크 : {}", paging);
		
		int totalRecord = questionMapper.selectDifferentQuestionTotalRecord(paging);
		
		paging.setTotalRecord(totalRecord);
		
		List<QuestionVO> dataList = questionMapper.selectDifferentQuestionList(paging);
		
		log.info("질문사항 페이징 DB에서 온 리스트 체크 : {}", dataList);
		
		paging.setDataList(dataList);
		
		return paging;
	}

	
	@Override
	public int retrieveQuestionTotalRecord(PageVO pageVO) {
		
		int countPerPage = questionMapper.selectQuestionTotalRecord(pageVO);
		
		return countPerPage;
	}

	@Override
	@Transactional
	public ServiceResult createQuestion(QuestionVO question) throws IOException {
	    ServiceResult res = null;
	    
	    log.info(" 서비스 임플 파일 객체 정보 확인 : {}", question);
	    String commonAtchmnflNo = null; 

	    List<MultipartFile> multipartFile = question.getFileList();
	    
	    // 파일 유무 확인
	    if (!multipartFile.isEmpty() && multipartFile !=null && multipartFile.size() >= 1 ) {
	        try {
	        	
	            for (int i = 0; i < multipartFile.size(); i++) {
	                MultipartFile atch = multipartFile.get(i);
	                
	                log.info("QnA 게시판 등록 시 파일 첨부 개별 정보 : {}", atch);

	                AttachmentFileVO atchmnfl = new AttachmentFileVO(atch); 
	                
	                atchmnfl.setMultipartFile(atch);
	                atchmnfl.setAtchmnflStrePath(saveFolder.getCanonicalPath());
	                atchmnfl.setAtchmnflSn(i+1);
	                
	                log.info("atchmnfl  파일 첨부 개별 정보 : {}", atchmnfl);
	                
	                if (i == 0) {
	                	
	                    attachmentFileService.firstCreateAttachmentFile(atchmnfl);
	                    commonAtchmnflNo = atchmnfl.getAtchmnflNo(); // 첫 번째 파일의 atchmnflNo 저장
	                    
	                    atchmnfl.saveTo(saveFolder);
	                    
	                } else {
	                	
	                    atchmnfl.setAtchmnflNo(commonAtchmnflNo);
	                    attachmentFileService.afterCreateAttachmentFile(atchmnfl);
	                    
	                    atchmnfl.saveTo(saveFolder);
	                }
	            }
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	    }
	    question.setAtchmnflNo(commonAtchmnflNo);
	    
	    int cnt = questionMapper.insertQuestion(question);
	    
		if(cnt > 0) {
			res = ServiceResult.OK;
		}else {
			res = ServiceResult.FAIL;
		}
	    
	    return res;
	}
	
	@Override
	public QuestionVO retrieveQuestionOne(String qestnNo) {
		QuestionVO questionOne = questionMapper.selectQuestionOne(qestnNo);
		return questionOne;
	}
	
	
	@Override
	@Transactional
	public ServiceResult modifyQuestion(QuestionVO question) {
		ServiceResult res = null;
	    
	    log.info(" 서비스 임플 파일 객체 정보 확인 : {}", question);
	    
	    String commonAtchmnflNo = null; 

	    List<MultipartFile> rawMultipartFile = question.getFileList();
	    
	    log.info("QnA 수정에서 rawMultipartFile 유무 체크 : {}", rawMultipartFile.get(0).getOriginalFilename());
	    
	    // 파일 유무 확인
	    if (!rawMultipartFile.isEmpty() && rawMultipartFile !=null && rawMultipartFile.size() >= 1 ) {
	    	
	    	try {
	    		
	    		boolean fileExistsInDB = false;
	    		
	    		
	            for (int i = 0; i < rawMultipartFile.size(); i++) {
	                MultipartFile atch = rawMultipartFile.get(i);
	                
	                List<AttachmentFileVO> DBAttachmentFile = attachmentFileService.selectAttachmentFile(question.getAtchmnflNo());
	                
	                log.info("QnA 게시판 수정 시 파일 첨부 개별 정보 : {}", atch);

	                AttachmentFileVO atchmnfl = new AttachmentFileVO(atch); 
	                
	                fileExistsInDB=true; // 초기화 해서 계속 false 값 유지
	                
	                for(int j =0; j < DBAttachmentFile.size(); j++) {
	                	if(! atch.getOriginalFilename().equals(DBAttachmentFile.get(j).getAtchmnflNm())) {
	                		fileExistsInDB =false;
	                		
	                		break;  // 존재 할때
	                	}
	                }
	                
	                if(!fileExistsInDB) {
	                	atchmnfl.setMultipartFile(atch);
	                	atchmnfl.setAtchmnflStrePath(saveFolder.getCanonicalPath());
	                	
	                	atchmnfl.setAtchmnflSn(question.getFileList().size()+1);
	                	
	                	log.info("atchmnfl  파일 첨부 개별 정보 : {}", atchmnfl);
	                	
	                	if (DBAttachmentFile.size() == 0) {
	                		
	                		attachmentFileService.firstCreateAttachmentFile(atchmnfl);
	                		commonAtchmnflNo = atchmnfl.getAtchmnflNo(); // 첫 번째 파일의 atchmnflNo 저장
	                		
	                		question.setAtchmnflNo(commonAtchmnflNo);;
	                		atchmnfl.saveTo(saveFolder);
	                		
	                	} else {
//	                		int maxSn = DBAttachmentFile.stream().mapToInt(AttachmentFileVO::getAtchmnflSn).max().orElse(0);
	                		commonAtchmnflNo = question.getAtchmnflNo();
	                		atchmnfl.setAtchmnflNo(commonAtchmnflNo);
	                		atchmnfl.setAtchmnflSn(DBAttachmentFile.size() + 1);
	                		attachmentFileService.afterCreateAttachmentFile(atchmnfl);
	                		
	                		atchmnfl.saveTo(saveFolder);
	                	}
	                }
	            }
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	    }
	    
	    int cnt = questionMapper.updateQuestion(question);
	    
		if(cnt > 0) {
			res = ServiceResult.OK;
		}else {
			res = ServiceResult.FAIL;
		}
	    
	    return res;
	}

	@Override
	public ServiceResult modifyQuestionAnswer(QuestionVO question) {
		ServiceResult res;
		
		int cnt = questionMapper.updateQuestionAnswer(question);
		
		if(cnt >0) {
			res = ServiceResult.OK;
		}else {
			res = ServiceResult.FAIL;
		}
		return res;
	}

	@Override
	public ServiceResult removeQuestion(String qestnNo) {
		
		ServiceResult res =null;
		QuestionVO question = questionMapper.selectQuestionOne(qestnNo);
		List<AttachmentFileVO> fileList = attachmentFileService.selectAttachmentFile(question.getAtchmnflNo());
		
		AttachmentFileVO attachmentFile = new AttachmentFileVO();
		
		
		for(int i =0; i < fileList.size() ; i ++) {
			
			String deleteFileName = "";
			
			try {
				attachmentFile.setAtchmnflNo(fileList.get(i).getAtchmnflNo());
				attachmentFile.setAtchmnflSn(fileList.get(i).getAtchmnflSn());
				
				attachmentFileService.deleteAttachmentFile(attachmentFile);
				
				deleteFileName = URLDecoder.decode(fileList.get(i).getAtchmnflStreNm(),"UTF-8");
				
				File file = new File(saveFolderPath + File.separator + deleteFileName );
				
				boolean result = file.delete();
				
				File parentFile = new File (file.getParent(),"s_" + file.getName());
				result = parentFile.delete();
				
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		int cnt = questionMapper.deleteQuestion(qestnNo);	
		if(cnt > 0) {
			res = ServiceResult.OK;
		}else {
			res = ServiceResult.FAIL;
		}
		
		return res ;
		
	}



	
	

}

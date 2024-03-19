package com.inno.portpolio.question.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.apache.ibatis.annotations.Update;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.inno.portpolio.common.enumpkg.ServiceResult;
import com.inno.portpolio.common.file.mapper.AttachmentFileMapper;
import com.inno.portpolio.common.file.service.AttachmentFileService;
import com.inno.portpolio.common.file.vo.AttachmentFileVO;
import com.inno.portpolio.paging.BootstrapPaginationRenderer;
import com.inno.portpolio.paging.vo.PageVO;
import com.inno.portpolio.paging.vo.Pagination;
import com.inno.portpolio.paging.vo.PaginationInfo;
import com.inno.portpolio.paging.vo.SearchVO;
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
* 2024.03.13.        김재성       등록 추가
* Copyright (c) 2024 by INNOVATION-T All right reserved
* </pre>
*/
@Controller
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/question")
public class QuestionController {
	
	private final QuestionService questionService;
	
	private final QuestionMapper questionMapper;
	
	private final AttachmentFileService attachmentService;
	
	@GetMapping("/main")
	public String maingPage(
		final Model model
		,@RequestParam(value = "page",defaultValue = "1") final int page
		,PageVO pageVO
		) {
		Pagination pagination = new Pagination(this.questionMapper.getCount(), page); // 모든 게시글 개수 구하기.
		
	    List<QuestionVO> qnalist = this.questionMapper.getListPage(pagination);

	    model.addAttribute("qnalist", qnalist);
	    model.addAttribute("page", page);
	    model.addAttribute("pagination", pagination);
		
		return "question/questionDiffrerntMain";
	}
	
	@PostMapping("/list")
	@ResponseBody
	public PaginationInfo<QuestionVO> questionList(
			 @ModelAttribute("simpleCondition") SearchVO simpleCondition
			,@RequestParam(value="page", required = false, defaultValue = "1") int currentPage
			,Model model
			
		) {
	
		PaginationInfo<QuestionVO> paging = new PaginationInfo<QuestionVO>();
		
		paging.setSimpleCondition(simpleCondition);
		paging.setCurrentPage(currentPage);
		paging.setRenderer(new BootstrapPaginationRenderer());
		
		PaginationInfo<QuestionVO> pagination = questionService.retrieveDifferentQuestionList(paging);
		
		log.info(" 컨트롤러 단에서 넘겨주는 데이터 pagination : {}" , pagination);

		model.addAttribute("pagination", pagination);
		
		
		return pagination;
	};
	
	@GetMapping("/questionInsertForm")
	public String questionInsertForm() {
		
		return "/question/qnaForm";
	};
	
	@PostMapping("/add")
	public String insertQusetion(
			 @ModelAttribute QuestionVO question
			,Authentication auth
			,HttpServletResponse response
		){
		
		String userId = auth.getName();
		question.setUserId(userId);
		
		log.info("파일 리스트 확인 : {}", question.getFileList());
		
		ServiceResult result;
		String viewName =null;
		String script = "";
		try {
			result = questionService.createQuestion(question);
			
			if(result.equals(ServiceResult.OK)) {
				script = "<script>alert('QnA 등록 성공 리스트로 돌아갑니다.');</script>";
				viewName = "redirect:/question/main";
			}else {
				script = "<script>alert('QnA 등록 실패! 다시 작성해주세요.');</script>";
				viewName = "redirect:/question/questionInsertForm";
			}
			response.getWriter().write(script);
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return viewName;
	}
	
	@GetMapping("/detail/{qestnNo}")
	public String questionDetail(
			@PathVariable("qestnNo") String qestnNo
			,Model model
		) {
		
		log.info("qestnNo 데이터 체크 : {}", qestnNo);
		
			QuestionVO questionOne = questionService.retrieveQuestionOne(qestnNo);
			
			String atchmnflNo = questionOne.getAtchmnflNo();
			
			List<AttachmentFileVO> attachmentFile = attachmentService.selectAttachmentFile(atchmnflNo);
			
			log.info("questionOne 데이터 체크 : {}", questionOne);
			log.info("attachmentFile 데이터 체크 : {}", attachmentFile);
			
			model.addAttribute("questionOne",questionOne);
			model.addAttribute("attachmentFile",attachmentFile);
		
		return "question/questionDetailMain";
	}
	
	@PutMapping("/detail/answer")
	@ResponseBody
	public HashMap<String, Object> questionDetailAnswer(
			@RequestBody QuestionVO question
		){
		
		log.info("답변 글 데이터 체크 : {}", question);
		
		ServiceResult res;
    	HashMap<String, Object> map = new HashMap<String, Object>();
    	try {
    		 res = questionService.modifyQuestionAnswer(question);
    		 
    		 if(res.equals(ServiceResult.OK)) {
    			 map.put("result",res);
    			 map.put("message","답변글 등록 성공 ");
    		 }else {
    			 map.put("result",res);
    			 map.put("message","답변글 등록 실패 "); 
    		 }
    		 
		} catch (Exception e) {
			e.printStackTrace();
		}

    	return map;
	};
	
	@GetMapping("/questionModifyForm/{qestnNo}")
	public String questiondetailModifyForm(
			 @PathVariable("qestnNo") String qestnNo
			 ,Model model
		) {
		
		log.info("qestnNo 데이터 체크 : {}", qestnNo);
		
		if(qestnNo !=null) {
			QuestionVO questionOne = questionService.retrieveQuestionOne(qestnNo);
			String atchmnflNo = questionOne.getAtchmnflNo();
			
			List<AttachmentFileVO> attachmentFile = attachmentService.selectAttachmentFile(atchmnflNo);
			
			log.info("questionOne 데이터 체크 : {}", questionOne);
			log.info("attachmentFile 데이터 체크 : {}", attachmentFile);
			model.addAttribute("questionOne",questionOne);
			model.addAttribute("attachmentFile",attachmentFile);
		}
		
		return "/question/qnaForm";
	}
	
	
}

package com.inno.portpolio.question.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.inno.portpolio.paging.vo.PageVO;
import com.inno.portpolio.paging.vo.Pagination;
import com.inno.portpolio.paging.vo.PaginationInfo;
import com.inno.portpolio.question.vo.QuestionVO;


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
* 2024.03.12.        김재성       질문사항 등록 수정 삭제 메서드 추가
* Copyright (c) 2024 by INNOVATION-T All right reserved
* </pre>
*/

@Mapper
public interface QuestionMapper {
	
	/**
	 * Qna 질문사항 리스트
	 * 페이징 처리
	 * @return
	 */
	public List<QuestionVO> selectQuestionList(PageVO pageVO);
	
	/**
	 * 총 레코드수 연산
	 * @param paging
	 * @return
	 */
	public int selectQuestionTotalRecord(PageVO pageVO);
	
	public int getCount();
	
	public List<QuestionVO> getListPage(Pagination pagination);
	
	/**
	 * 다른 버전 - Qna 질문사항 리스트
	 * 페이징 처리
	 * @return
	 */
	public List<QuestionVO> selectDifferentQuestionList(PaginationInfo<QuestionVO> paging);
	
	/**
	 * 다른 버전 - 총 레코드수 연산
	 * @param paging
	 * @return
	 */
	public int selectDifferentQuestionTotalRecord(PaginationInfo<QuestionVO> paging);
	
	/**
	 * QnA 상세보기 화면 정보
	 * @param qestnNo
	 * @return
	 */
	public QuestionVO selectQuestionOne(String qestnNo);
	
	/**
	 * 질문사항 등록
	 * @param question
	 * @return
	 */
	public int insertQuestion(QuestionVO question);
	
	/**
	 * 질문사항 수정
	 * @param question
	 * @return
	 */
	public int updateQuestion(QuestionVO question);
	
	/**
	 * 질문사항 삭제
	 * @param question
	 * @return
	 */
	public int deleteQuestion(QuestionVO question);

	/**
	 * 등록된 질문사항 글 답변글 Update
	 * @param qestnAnswer
	 * @return
	 */
	public int updateQuestionAnswer(QuestionVO question);
	
}

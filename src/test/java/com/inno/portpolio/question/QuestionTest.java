package com.inno.portpolio.question;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit.jupiter.web.SpringJUnitWebConfig;

import com.inno.portpolio.question.mapper.QuestionMapper;

@SpringJUnitWebConfig
class QuestionTest {
	
	@Autowired
	private QuestionMapper mapper;
	
	@Test
	void test() {
		try {
//			int cnt = mapper.selectQuestionTotalRecord();
			
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

}

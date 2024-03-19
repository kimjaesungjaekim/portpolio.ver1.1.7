/**
 * @author 연구개발 5팀 사원 김재성
 * @since 2024. 03. 18.
 * @version 1.0
 * @see javax.servlet.http.HttpServlet
 * 
 *
 * [[개정이력(Modification Information)]]
 *    수정일            수정자               수정내용
 * ------------     --------    ----------------------
 * 2024.03.18.        김재성       최초 작성
 * Copyright (c) 2024 by INNOVATION-T All right reserved
 *
 */

 $(function(){
	 
	 // 답변 등록 수정 ajax 함수
	 function questionModifyAjax(data){
		
		console.log("data 내용 확인 ",data);
		
		$.ajax({
	        url: '/question/detail/answer',  
	        type: 'PUT',  
	        data: JSON.stringify(data),
	        dataType: "json",
	        contentType: 'application/json',
	        success : function(resp){
				if(resp.result =="OK"){
					alert(resp.message);
					location.reload();
				}else{
					alert(resp.message);
				}
			},
		 	error : function(xhr, status,err){
		 		console.log("상태 : ", status);
		 		console.log("에러 : ", err);
		 		alert("잘못된 요청 발생 !");
		 	}
	 	});
	 }
	 
	 // 답변글 등록
	 $("#qnaDetailAnswerForm").on("submit",function(e){
		e.preventDefault();
		
		data={
			qestnAnswer:$("#answerTextarea").val(),
			qestnNo:$("#questionDiv").data("qestn-no")
		}
		questionModifyAjax(data);
	 });
	 
	 //모달 열기
	 $("#questionModifyModalBtn").on("click",function(){
		alert(" 답변 글 수정 폼");
		$("#answerModifyModal").show();
	 });
	
	// 모달 닫기
	$("#answerModifyModalClose").on("click",function(){
		$("#answerModifyModal").hide();
		$("#careerAddModalForm textarea").val('');
	});
	
	// 답변글 수정
	$("#answerModifyModal").on("submit",function(e){
		e.preventDefault();
		
		data={
			qestnAnswer:$("#qestnAnswerModalTextarea").val(),
			qestnNo:$("#questionDiv").data("qestn-no")
		}
		questionModifyAjax(data);
	 });
	 
	 //게시글 삭제
	 $("#questionDetailRemoveBtn").on("click",function(){
		
		let qestnNo=$("#questionDiv").data("qestn-no");
		
		let confirmed = confirm("정말로 삭제 하시겠습니까? - 등록된 파일도 삭제 됩니다. -");
		
		if(confirmed){
			
			$.ajax({
	        url: `/question/remove/${qestnNo}`,  
	        type: 'GET',  
	        //data: qestnNo,
	        dataType: "json",
	        //contentType: 'application/json',
	        success : function(resp){
				if(resp.result =="OK"){
					alert(resp.message);
					location.href="/question/main";
				}else{
					alert(resp.message);
				}
			},
		 	error : function(xhr, status,err){
		 		console.log("상태 : ", status);
		 		console.log("에러 : ", err);
		 		alert("잘못된 요청 발생 !");
		 	}
	 	});
		}else {
			alert("잘 선택하셨습니다.");
		}
		
		 
	 });
	
	 
 });
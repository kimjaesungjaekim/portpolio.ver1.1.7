/**
* @author 연구개발 5팀 사원 김재성
* @since 2024. 03. 07.
* @version 1.0
* @see javax.servlet.http.HttpServlet 
* <pre>
* [[개정이력(Modification Information)]]
*    수정일            수정자               수정내용
* ------------     --------    ----------------------
* 2023.12.02.        김재성       최초작성
* Copyright (c) 2024 by INNOVATION-T All right reserved
* </pre>
*/

$(function(){
	
$("#signUpFormTag").on("submit",function(e){
		//alert("확인");
		e.preventDefault();
		
		let formData = {
	        useId: $("#useId").val(),
	        usePw: $("#usePw").val(),
	        useNm: $("#useNm").val(),
	        useTel: $("#useTel").val(),
	        useEmail: $("#useEmail").val(),
    	};
		
		console.log("폼 입력값 : ",formData);
		
		$.ajax({
			url : "/login/signup",
			method : "POST",
			data : JSON.stringify(formData) ,
//			data : formData ,
			dataType : 'json',
			contentType: 'application/json',
			success : function(resp){
				if(resp.result =="OK"){
					alert(resp.message);
					location.href="/";
				}else if(resp.result =="DUPLICATED"){
					alert(resp.message);
				}else{
					alert(resp.message);
					location.reload();
				}
			},
			error : function(xhr, status,err){
				console.log("상태 : ", status);
				console.log("에러 : ", err);
				alert("잘못된 요청 발생 !");
			}
		});
	});
}) ;
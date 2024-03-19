/**
 * 
 * 
 * @author 연구개발 5팀 사원 김재성
 * @since 2024. 03. 08.
 * @version 1.0
 * @see javax.servlet.http.HttpServlet
 * 
 *      <pre>
 * [[개정이력(Modification Information)]]
 *    수정일            수정자               수정내용
 * ------------     --------    ----------------------
 * 2024.03.08.        김재성       최초작성
 * 2024.03.11.        김재성       경력 학력 자격증 기술 추가 삭제
 * Copyright (c) 2024 by INNOVATION-T All right reserved
 *      </pre>
 */

 $(function(){
	 
	// ----------------- 기본정보 -----------------------------
	$("#profileInformationBtn").on("click",function(){
		
		//alert("수정하기");
			
		$("#profileModal").show();
	});
	
	$("#profileInformationModalClose").on("click",function(){
		$("#profileModal").hide();
		$("#profileModifyForm input").val('');
		$("#profileModifyForm textarea").val('');
	}); 
	
	$("#profileModifyForm").on("submit",function(event){
		event.preventDefault();
		
		//let data = $(this).serialize();
		let data = {
	        userNm: $("#userNm").val(),
	        userTel: $("#userTel").val(),
	        userEmail: $("#userEmail").val(),
	        userIntroduce: $("#userIntroduce").val(),
    	};
		
		console.log("data", data);
		
		$.ajax({
			url : "/information/modify",
			method : "POST",
			data : JSON.stringify(data) ,
//			data : formData ,
			dataType : 'json',
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
	});
	
	// ----------------- 경력  ------------------------
    $("#careerAddModalBtn").on("click",function(){
			
		$("#careerModal").show();
	});
	
	$("#careerAddModalClose").on("click",function(){
		$("#careerModal").hide();
		$("#careerAddModalForm input").val('');
	});
	
	$("#careerAddModalForm").on("submit",function(event){
		event.preventDefault();
		
		//let data = $(this).serialize();
		let data = {
	        careerNm: $("#careerNm").val(),
	        careerSpot: $("#careerSpot").val(),
	        careerBeginDate: $("#careerBeginDate").val(),
	        careerEndDate: $("#careerEndDate").val(),
    	};
		
		console.log("data", data);
		
		$.ajax({
			url : "/information/career/add",
			method : "POST",
			data : JSON.stringify(data) ,
//			data : formData ,
			dataType : 'json',
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
	});
	
	$("#careerRemoveBtn").on("click",function(){
		
			let careerChekced = $("#careerDiv input[name='selectedCareer']:checked");
			
			let careerVaules = [];
			
			careerChekced.each(function(){
				  careerVaules.push($(this).val());
			});
			
			console.log("선택된 경력 value 값들 확인 : " + careerVaules);
			
			if (confirm("선택하신 경력 정보를 삭제 하시겠습니까?")) {
			    console.log("사용자가 확인을 선택했습니다. 경력 정보를 삭제합니다.");
			    
			    $.ajax({
		//			url : `/information/career/remove?careerVaules=${encodeURIComponent(JSON.stringify(careerVaules))}`,
					url : '/information/career/remove',
					type : "POST",
		//			data: {careerVaules:careerVaules}, // 배열을 전달
					data : JSON.stringify(careerVaules) ,
					dataType : 'json',
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
			    
			}else {
			    $("#careerDiv input[name='selectedCareer']").prop('checked', false);
			}
	});
	
	// ----------------- 학력  ------------------------
    $("#schoolAddModalBtn").on("click",function(){
			
		$("#schoolModal").show();
	});
	
	$("#schoolAddModalClose").on("click",function(){
		$("#schoolModal").hide();
		$("#schoolAddModalForm input").val('');
		$("#schoolAddModalForm select").val('-1');
	});
	
	$("#schoolAddForm").on("submit",function(event){
		event.preventDefault();
		
		let data = {
	        acdmcrNm: $("#acdmcrNm").val(),
	        acdmcrSe: $("#acdmcrSe").val(),
	        acdmcrBeginDate: $("#acdmcrBeginDate").val(),
	        acdmcrEndDate: $("#acdmcrEndDate").val(),
	        acdmcrGraduate: $("#acdmcrGraduate").val(),
	        acdmcrScore: $("#acdmcrScore").val()
    	};
		
		console.log("data", data);
		
		$.ajax({
			url : "/information/school/add",
			method : "POST",
			data : JSON.stringify(data) ,
//			data : formData ,
			dataType : 'json',
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
	});
	
	$("#schoolRemoveBtn").on("click",function(){
		
			let schoolChekced = $("#schoolDiv input[name='selectedSchool']:checked");
			
			let schoolValues = [];
			
			schoolChekced.each(function(){
				  schoolValues.push($(this).val());
			});
			
			console.log("선택된 경력 value 값들 확인 : " + schoolValues);
			
			if (confirm("선택하신 학력 정보를 삭제 하시겠습니까?")) {
			    
			    $.ajax({
					url : '/information/school/remove',
					type : "POST",
					data : JSON.stringify(schoolValues) ,
					dataType : 'json',
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
			    
			}else {
			    $("#schoolDiv input[name='selectedSchool']").prop('checked', false);
			}
	});
	
	// ----------------- 자격증 ------------------------
    $("#certificateAddModalBtn").on("click",function(){
			
		$("#certificateModal").show();
	});
	
	$("#certificateAddModalClose").on("click",function(){
		$("#certificateModal").hide();
		$("#certificateAddModalForm input").val('');
	});
	
	$("#certificateAddModalForm").on("submit",function(event){
		event.preventDefault();
		
		let data = {
	        crqfcNm: $("#crqfcNm").val(),
	        crqfcCode: $("#crqfcCode").val(),
	        crqfcAgency: $("#crqfcAgency").val(),
	        crqfcDate: $("#crqfcDate").val(),
    	};
		
		console.log("data", data);
		
		$.ajax({
			url : "/information/certificate/add",
			method : "POST",
			data : JSON.stringify(data) ,
//			data : formData ,
			dataType : 'json',
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
	});
	
	$("#certificateRemoveBtn").on("click",function(){
		
			let certificateChekced = $("#certificateDiv input[name='selectedCertificate']:checked");
			
			let certificateValues = [];
			
			certificateChekced.each(function(){
				  certificateValues.push($(this).val());
			});
			
			console.log("선택된 경력 value 값들 확인 : " + certificateValues);
			
			if (confirm("선택하신 학력 정보를 삭제 하시겠습니까?")) {
			    
			    $.ajax({
					url : '/information/certificate/remove',
					type : "POST",
					data : JSON.stringify(certificateValues) ,
					dataType : 'json',
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
			    
			}else {
			    $("#certificateDiv input[name='selectedCertificate']").prop('checked', false);
			}
	});
	
	
	// ----------------- 기술 스킬 ------------------------
    $("#skilAddModalBtn").on("click",function(){
			
		$("#skilModal").show();
	});
	
	$("#skilAddModalClose").on("click",function(){
		$("#skilModal").hide();
		$("#skilAddModalForm input").val('');
	});
	
	$("#skilAddModalForm").on("submit",function(event){
		event.preventDefault();
		
		let data = {
	        skilNm: $("#skilNm").val(),
	        skilContent: $("#skilContent").val(),
	        skilNice: $("#skilNice").val(),
    	};
		
		console.log("data", data);
		
		$.ajax({
			url : "/information/skil/add",
			method : "POST",
			data : JSON.stringify(data) ,
//			data : formData ,
			dataType : 'json',
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
	});
	
	$("#skilRemoveBtn").on("click",function(){
		
			let skilChekced = $("#skilDiv input[name='selectedSkil']:checked");
			
			let skilValues = [];
			
			skilChekced.each(function(){
				  skilValues.push($(this).val());
			});
			
			console.log("선택된 기술 value 값들 확인 : " + skilValues);
			
			if (confirm("선택하신 기술 정보를 삭제 하시겠습니까?")) {
			    
			    $.ajax({
					url : '/information/skil/remove',
					type : "POST",
					data : JSON.stringify(skilValues) ,
					dataType : 'json',
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
			    
			}else {
			    $("#skilDiv input[name='selectedSkil']").prop('checked', false);
			}
	});
	
	
 });
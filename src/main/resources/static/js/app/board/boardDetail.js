/**
 * 
 */

$(function(){
  fn_commentList();
});
//▶▷▶▷▶▷▶▷▶▷▶▷▶▷▶▷▶▷▶▷▶▷▶대댓글 달기▶▷▶▷▶▷▶▷▶▷▶▷▶▷▶▷▶▷▶▷▶▷▶▷▶▷▶▷▶
function fn_replyComment(commNo) {
    var replyForm = document.createElement('div');
    replyForm.innerHTML = `
        <textarea id="replyContent${commNo}" rows="3" cols="50"></textarea>
        <button class="btn btn-sm btn-success" onclick="postReply(${commNo})">등록</button>
    `;
    document.getElementById('commentContent' + commNo).after(replyForm);
}


function postReply(commNo) {
    var replyContent = document.getElementById('replyContent' + commNo).value;
    var boNo = $('#currBoNo').val();
    
    $.ajax({
        type: "POST",
        url: "/board/reply",
        contentType: "application/json",
        data: JSON.stringify({
			commUpnum: commNo
			, commContent: replyContent
			, boNo : boNo
			}),
        success: function (data) {
            if (data.success == "Y") {
                alert('답글이 등록되었습니다.');
                // 댓글 목록을 다시 불러옴
                fn_commentList();
            } else {
                alert('답글 등록 중 오류가 발생했습니다.');
            }
        },
        error: function () {
            alert('답글 등록 중 오류가 발생했습니다.');
        }
    });
}






//▶▷▶▷▶▷▶▷▶▷▶▷▶▷▶▷▶▷▶▷▶▷▶댓글수정▶▷▶▷▶▷▶▷▶▷▶▷▶▷▶▷▶▷▶▷▶▷▶▷▶▷▶▷▶

function fn_modifyComm(commNo) {
	//수정하려는 html요소 가져오기
    var commentContent = document.getElementById('commentContent' + commNo);
    
    //사용자가 직접 편집할수있게함
    commentContent.contentEditable = "true";
	
	//완료버튼 생성하기
    var saveButton = document.createElement('button');
    
    //생성한 버튼에 클래스 추가
    saveButton.className = 'btn btn-sm btn-success mt-2';
    saveButton.innerText = '완료';
    
    
    saveButton.onclick = function () {
        $.ajax({
            type: "POST",
            url: "/board/updateComm/" + commNo,
            contentType: "application/json",
            data: JSON.stringify({commContent: commentContent.innerText}),
            success: function (data) {
                if (data.success == "Y") {
                    alert('댓글이 수정되었습니다.');
                    // 댓글 목록을 다시 불러옴
                    fn_commentList();
                } else {
                    alert('댓글 수정 중 오류가 발생했습니다.');
                }
            },
            error: function () {
                alert('댓글 수정 중 오류가 발생했습니다.');
            }
        });
    };
	//댓글 내용 바로 뒤에 완료버튼 추가
    commentContent.after(saveButton);
}


//▶▷▶▷▶▷▶▷▶▷▶▷▶▷▶▷▶▷▶▷▶▷▶댓글삭제▶▷▶▷▶▷▶▷▶▷▶▷▶▷▶▷▶▷▶▷▶▷▶▷▶▷▶▷▶

function fn_removeComm(commNo){
	
	$.ajax({
		type: "POST",
		url: "/board/deleteComm/",
		data:{
			commNo:commNo
		},
		success: function (data) {
			if(data.success == "Y"){
				alert("댓글이 삭제되었습니다.")
				fn_commentList();
			}else{
				alert("처리중 오류가 발생했습니다.")
			}
		},
		error: function () {
			alert("처리중 오류가 발생했습니다.")
		}
	});
	
}

//▶▷▶▷▶▷▶▷▶▷▶▷▶▷▶▷▶▷▶▷▶▷▶댓글등록▶▷▶▷▶▷▶▷▶▷▶▷▶▷▶▷▶▷▶▷▶▷▶▷▶▷▶▷▶

function fn_insertComm(){
	
	var boNo = $('#currBoNo').val();
	
	//비동기
	var insertComm = new FormData($("#commentForm")[0]);
	insertComm.set("boNo", boNo);
	
	$.ajax({
		type: "POST",
		url: "/board/insertComm",
		data: insertComm,
		processData: false,
		contentType: false,
		cache: false,
		success: function (data) {
			if(data.success == "Y"){
				alert("댓글이 등록되었습니다.")
				fn_commentList();
			}else{
				alert("처리중 오류가 발생했습니다.")
			}
		},
		error: function () {
			alert("처리중 오류가 발생했습니다.")
		}
	});
	
}


//▶▷▶▷▶▷▶▷▶▷▶▷▶▷▶▷▶▷▶▷▶▷▶댓글▶▷▶▷▶▷▶▷▶▷▶▷▶▷▶▷▶▷▶▷▶▷▶▷▶▷▶▷▶
//댓글 목록
function fn_commentList(){
	
	var boNo = $('#currBoNo').val();
	let settings = {
		url : '/board/commList/'+boNo,
		method : 'get',
		dataType: 'json'
	};
	
	$.ajax(settings).done(function(resp){
		
		var commList = resp.commList;
		var divTag='';
		
		if(commList.length != 0){
			$.each(commList , function(index, v){
				if(v.commUpnum == null && v.commDel =="N"){  // 기본 댓글
					divTag += generateCommentHtml(v, commList);
				}
			})	
		}else{
			divTag += `
				<div class="card border-left-info shadow h-100 ">
		            <div class="card-body">
		                <div class="row no-gutters align-items-center">
		                    <div class="col mr-2">
		                        <div class="row no-gutters align-items-center">
		                            <div class="col-auto">
		                                <div class="h5 mb-0 mr-3 font-weight-bold text-gray-800">작성된 댓글이 없습니다.</div>
		                            </div>
		                        </div>
		                    </div>
		                </div>
		            </div>
		        </div>
			`;
		}
		$("#commentArea").html(divTag);		
	})
}

function generateCommentHtml(comment, commList) {
	
	/*
		some() : 주어진 조건을 만족하는 요소가 하나라도 있는지 확인
		return true -> 대댓글을 가지고있음
		return false -> 대댓글이 없음
	*/	
	var hasReplies = commList.some(v => v.commUpnum === comment.commNo);
	
	let divTag = `
		<div class="card border-left-info shadow h-100  mb-3">
            <div class="card-body">
                <div class="row no-gutters align-items-center">
                    <div class="col mr-2">
                        <div class="font-weight-bold text-info text-uppercase mb-1">
                        	<div style="display: inline-block; margin-right: 10px;">${comment.commNo}</div>
                        	<div style="display: inline-block; margin-right: 10px;">${comment.commId}</div>
                        	<div style="display: inline-block; margin-right: 10px;">${comment.commDate}</div>
                        </div>
                        <div class="row no-gutters align-items-center">
                            <div class="col-auto">
                                <div class="h5 mb-0 mr-3 font-weight-bold text-gray-800"
                                	contentEditable="false" id="commentContent${comment.commNo}">
                                		${comment.commContent}
                                </div>
                            </div>
                        </div>
                    </div>
			        <div class="col-auto">
			            <button class="btn btn-sm btn-danger" onclick="fn_removeComm(${comment.commNo})" ${hasReplies ? 'disabled' : ''}>삭제</button>
			            <button class="btn btn-sm btn-info" onclick="fn_modifyComm(${comment.commNo})">수정</button>
			            <button class="btn btn-sm btn-primary" onclick="fn_replyComment(${comment.commNo})">답글달기</button>
			        </div>
                </div>
            </div>
        </div>
	`;
	// 대댓글 처리
	$.each(commList, function(index, v){
		if(v.commUpnum == comment.commNo && v.commDel =="N"){
			divTag += `<div class="ml-5">${generateCommentHtml(v, commList)}</div>`;
		}
	});

	return divTag;
}




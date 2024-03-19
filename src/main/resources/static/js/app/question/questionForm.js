/**
 * @author 연구개발 5팀 사원 김재성
 * @since 2024. 03. 13.
 * @version 1.0
 * @see javax.servlet.http.HttpServlet
 * 
 *      <pre>
 * [[개정이력(Modification Information)]]
 *    수정일            수정자               수정내용
 * ------------     --------    ----------------------
 * 2024.03.13.        김재성       최초 작성
 * Copyright (c) 2024 by INNOVATION-T All right reserved
 *      </pre>
 */
$(function(){
	
	$("#passwordMarkBtn").on("click", function(){
		 let btnType = $(this).parent("#passwordDiv").find("input");
		 
	     if (btnType.length > 0) {
	         if (btnType.attr("type") === "password") {
	             btnType.attr("type", "text");
	         } else {
	             btnType.attr("type", "password");
	         }
	     }
    	
    	console.log(" 패스워드 타입 확인 : ", btnType);
	});
	
	$("#modifyQuestionFormBtn").on("click",function(event){
		event.preventDefault();
		
		let formData = new FormData($("#questionModifyForm")[0]);
		
		console.log("formdata 확인 : ", formData);
		
		let fileList = $("#input")[0].files;
		
		formData.append("fileList",fileList);
		
		console.log("파일 체크 :",fileList);
		
		$.ajax({
			url : "/question/modify",
			type: "POST",
			contentType: false,
			processData : false,
			data: formData,
			dataType: "json",
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
});		
	
	////////////파일 드래그엔 드롭 스크립트/////////////////////////
var input = document.getElementById("input");
var initLabel = document.getElementById("label");

input.addEventListener("change", (event) => {
  const files = changeEvent(event);
  handleUpdate(files);
});

initLabel.addEventListener("mouseover", (event) => {
  event.preventDefault();
  const label = document.getElementById("label");
  label?.classList.add("label--hover");
});

initLabel.addEventListener("mouseout", (event) => {
  event.preventDefault();
  const label = document.getElementById("label");
  label?.classList.remove("label--hover");
});

document.addEventListener("dragenter", (event) => {
  event.preventDefault();
  console.log("dragenter");
  if (event.target.className === "inner") {
    event.target.style.background = "#616161";
  }
});

document.addEventListener("dragover", (event) => {
  console.log("dragover");
  event.preventDefault();
});

document.addEventListener("dragleave", (event) => {
  event.preventDefault();
  console.log("dragleave");
  if (event.target.className === "inner") {
    event.target.style.background = "#3a3a3a";
  }
});

document.addEventListener("drop", (event) => {
  event.preventDefault();
  console.log("drop");
  if (event.target.className === "inner") {
    const files = event.dataTransfer?.files;
    event.target.style.background = "#3a3a3a";
    handleUpdate([...files]);
    
    const fileInput = $("#input").val(...files);
    formData.append('fileList', ...files)
  }
});

function changeEvent(event){
  const { target } = event;
  return [...target.files];
};

function handleUpdate(fileList){
  const preview = document.getElementById("preview");
  
  fileList.forEach((file) => {
    const reader = new FileReader();
    reader.addEventListener("load", (event) => {
      const img = el("img", {
        className: "embed-img",
        src: event.target?.result,
      });
      const imgContainer = el("div", { className: "container-img" }, img);
      preview.append(imgContainer);
    });
    reader.readAsDataURL(file);
  });
};

function el(nodeName, attributes, ...children) {
  const node =
    nodeName === "fragment"
      ? document.createDocumentFragment()
      : document.createElement(nodeName);

  Object.entries(attributes).forEach(([key, value]) => {
    if (key === "events") {
      Object.entries(value).forEach(([type, listener]) => {
        node.addEventListener(type, listener);
      });
    } else if (key in node) {
      try {
        node[key] = value;
      } catch (err) {
        node.setAttribute(key, value);
      }
    } else {
      node.setAttribute(key, value);
    }
  });

  children.forEach((childNode) => {
    if (typeof childNode === "string") {
      node.appendChild(document.createTextNode(childNode));
    } else {
      node.appendChild(childNode);
    }
  });

  return node;
}

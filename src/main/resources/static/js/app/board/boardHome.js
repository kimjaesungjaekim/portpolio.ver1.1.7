/**
 * 게시판 관련 JS코드
 */




//▶▷▶▷▶▷▶▷▶▷▶▷▶▷▶▷▶▷▶▷▶▷▶게시판 상세화면▷▶▷▶▷▶▷▶▷▶▷▶▷▶▷▶▷▶▷▶▷▶▷▶▷▶▷▶▷▶

//게시판 상세화면 페이지 이동
function fn_boardDetail(boNo){

	location.href = "/board/detail/"+boNo;
}




//▶▷▶▷▶▷▶▷▶▷▶▷▶▷▶▷▶▷▶▷▶▷▶게시판 페이징▷▶▷▶▷▶▷▶▷▶▷▶▷▶▷▶▷▶▷▶▷▶▷▶▷▶▷▶▷▶


let defaultSort ="";
let defaultSortOption ="";
let defaultNum =10;


//정렬 갯수
function fn_listChange(num){
	fn_boardPaging(0,defaultSort,defaultSortOption,num);
}



//조회순
function fn_SortByRcnt(){
	
	//초기 내림차순
	$('#sortRcnt').toggleClass('sortRcnt'); 
	let sortOption =  $('#sortRcnt').hasClass('sortRcnt'); //true 내림차순
	
	if(sortOption){
		$('#rcntName').html("조회순 ↓");
	}else{
		$('#rcntName').html("조회순 ↑");
	}
	defaultSort="boRcnt";
	defaultSortOption=sortOption ? 'desc' :'asc';
	
	fn_boardPaging(0,defaultSort,defaultSortOption,$('#listSelect').val());
}



//좋아요순

function fn_SortByHit(){
	
	//초기 내림차순
	$('#sortHit').toggleClass('sortHit'); 
	let sortOption =  $('#sortHit').hasClass('sortHit'); //true 내림차순
	
	if(sortOption){
		$('#hitName').html("추천순 ↓");
	}else{
		$('#hitName').html("추천순 ↑");
	}
	defaultSort="boHit";
	defaultSortOption=sortOption ? 'desc' :'asc';
	
	fn_boardPaging(0,defaultSort,defaultSortOption,$('#listSelect').val());
}


//페이징목록

$(function(){
  fn_boardPaging(0);
});

function fn_boardPaging(page,sort,sortOption,num){
	
	$.ajax({
		type: "GET",
		url: "/board/list",
		data:{
			page:page,
			sort:sort,
			sortOption:sortOption,
			num : num
		},
		success: function (data) {
			let paging = data.paging.content;
			
			if(paging[0] != null){
				
				let trTag ="";
				$.each(paging , function(index,v){
					if(v.boNo != null){
						trTag+=`
							<tr onclick="fn_boardDetail(${v.boNo})">
		                        <td>${v.boNo}</td>
		                        <td >${v.boTitle}</td>
		                        <td >${v.memId}</td>
		                        <td >${v.boDate}</td>
		                        <td >${v.boRcnt}</td>
		                        <td >${v.boHit}</td>
	                        </tr>
						`;
					}else{
						trTag +=`
				            <tr>
				              <td colspan='6'>내역 없음</td>
				            </tr>  
				         `;
					}
					$('#boardList').html(trTag);
					
					let endPage = (data.paging.size * ((data.paging.number + data.paging.size - 1) / data.paging.size)) - (data.paging.number % data.paging.size);
					let startPage = endPage - (data.paging.size - 1);
					
					// 페이지 버튼 추가
					let ulTag = `
					    <ul class="pagination justify-content-center">
					        <li class="page-item ${startPage == 0 ? 'disabled' : ''}">
					            <a class="page-link" onclick="fn_boardPaging(${startPage == 0 ? 0 : startPage - 1},'${sort}','${sortOption}',${num})">
					                <span>이전</span>
					            </a>
					        </li>
					`;
					    for (let i = startPage; i <=  endPage; i++) {
							if(i < data.paging.totalPages){
			                    ulTag += `
			                        <li class="page-item ${data.paging.number === i ? 'active' : ''}">
			                            <a class="page-link" onclick="fn_boardPaging(${i},'${sort}','${sortOption}',${num})">${i+1}</a>
			                        </li>
			                    `;
							}
		                }
					ulTag += `
					        <li class="page-item ${endPage >= data.paging.totalPages-1 ? 'disabled' : ''}">
					            <a class="page-link" onclick="fn_boardPaging(${endPage >= data.paging.totalPages-1 ? data.paging.totalPages-1 : endPage+1},'${sort}','${sortOption}',${num})">
					                <span>다음</span>
					            </a>
					        </li>
					    </ul>
					`;
					$('#boardPaging').html(ulTag);
				})	
			}
		},
		error: function () {
		}
	})
};

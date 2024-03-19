/**
 * @author 연구개발 5팀 사원 김재성
 * @since 2024. 03. 11.
 * @version 1.0
 * @see javax.servlet.http.HttpServlet
 * 
 *      <pre>
 * [[개정이력(Modification Information)]]
 *    수정일            수정자               수정내용
 * ------------     --------    ----------------------
 * 2024.03.11.        김재성       최초 작성
 * Copyright (c) 2024 by INNOVATION-T All right reserved
 *      </pre>
 */


/**
* 그리드 설정
* @variable {Dom} el : 그리드 element(DOM)
* @variable {boolean} scrollX : X 스크롤 사용여부
* @variable {boolean} scrollY : Y 스크롤 사용여부
* @variable {boolean} draggable : 드레그 사용 여부
* @variable {Object} header
*      - @variable {Number} height : 헤더 높이
* @variable {Number} bodyHeight : 그리드 바디 높이
* @variable {*} contextMenu : 마우스 우클릭 옵션
* @variable {Array} columns :
*      - @variable {String} header : 컬럼명(헤더)
*      - @variable {String} name : 컬럼 name (input data와 이름이 일치해야함)
*      - @variable {String} align : 정렬
*      - @variable {Number} width : 너비
*      - @variable {String} whiteSpace : 줄바꿈 설정
*      - @variable {Function} formatter : 출력 포멧
* 기타 옵션은 공식 document를 참조하자.
*/

$(function() {
  review.init();
});

let review = {

  seq: null,
  pagination : null,
  cnt : null,
  limit : 10,
  currentPage: 1,

  init: function () {
    let _this = this;

    let Grid = tui.Grid;
    Grid.applyTheme('clean');

    this.grid = new Grid({
      el: document.getElementById('grid'),
      scrollX: false,
      scrollY: false,
      bodyHeight: 450,
      rowHeaders: ['checkbox'],
      columns: [
        {
          header: "작성자",
          name: "useId",
          align: "center",
          sortable: true,                
          resizable: true,
          // [선택] 필터 옵션
          filter: {
	        type: 'text',
	        showApplyBtn: true,
	        showClearBtn: true
	      },
          // [선택] 유효성 검증 옵션
		   /* validation: {
		        unique: true,               // [선택] 유일성 체크 확인 옵션
		        dataType: 'number',         // [선택] 데이터 타입 체크 옵션
		        required: true,             // [선택] 필수 여부 체크 옵션
		        min: 1,                     // [선택] 최소값
		        max: 100,                   // [선택] 최대값
		     }*/
        },
        {
          header: "제목",
          name: "useTitle",
          align: "center",
          sortable: true,                
          resizable: true,
          // [선택] 필터 옵션
          filter: {
	        type: 'text',
	        showApplyBtn: true,
	        showClearBtn: true
	      },
          // [선택] 유효성 검증 옵션
		  /*  validation: {
		        unique: true,               // [선택] 유일성 체크 확인 옵션
		        dataType: 'number',         // [선택] 데이터 타입 체크 옵션
		        required: true,             // [선택] 필수 여부 체크 옵션
		        min: 1,                     // [선택] 최소값
		        max: 100,                   // [선택] 최대값
		     }*/
        },
        {
          header: "내용",
          name: "useContent",
          align: "center",
          sortable: true,                
          resizable: true,
          // [선택] 필터 옵션
          filter: {
	        type: 'text',
	        showApplyBtn: true,
	        showClearBtn: true
	      },
          // [선택] 유효성 검증 옵션
		  /*  validation: {
		        unique: true,               // [선택] 유일성 체크 확인 옵션
		        dataType: 'number',         // [선택] 데이터 타입 체크 옵션
		        required: true,             // [선택] 필수 여부 체크 옵션
		        min: 1,                     // [선택] 최소값
		        max: 100,                   // [선택] 최대값
		     }*/
        },
        {
          header: "작성일시",
          name: "useDate",
          align: "center",
          sortable: true,                
          resizable: true,
          // [선택] 필터 옵션
          filter: {
	        type: 'text',
	        showApplyBtn: true,
	        showClearBtn: true
	      },
          // [선택] 유효성 검증 옵션
		  /*  validation: {
		        unique: true,               // [선택] 유일성 체크 확인 옵션
		        dataType: 'number',         // [선택] 데이터 타입 체크 옵션
		        required: true,             // [선택] 필수 여부 체크 옵션
		        min: 1,                     // [선택] 최소값
		        max: 100,                   // [선택] 최대값
		     }*/
        },
      ],
    });

    /* 데이터 총 개수 세는 함수 */
    this.cnt = this.readCnt();

    /* 초기 데이터 읽어오는 함수 */
    let list = this.read();

    /* 페이지네이션 초기화하는 함수 */
    this.pagination = new tui.Pagination(document.getElementById('pagination'),    {
      visiblePages: 5, // 한 번에 보여줄 1,2,3,4 목차 개수
      totalItems : this.cnt, // 전체 아이템 개수가 몇 개인지
      itemsPerPage: this.limit, // 한 페이지에 몇 개씩 보여줄 것인지
      centerAlign: true // 현재 선택된 페이지 중앙 정렬
    });


    /* 읽어온 데이터 그리드에 그리는 함수 */
    if (list) {
      this.grid.resetData(list);
      this.pagination.setTotalItems(this.cnt);
    }

    /* 페이지 이동 시 실행되는 함수 */
    this.pagination.on('afterMove', function (ev) {
      _this.currentPage = ev.page;
      let list = _this.read();
      if (list) {
        _this.grid.resetData(list);
        _this.pagination.setTotalItems(_this.cnt);
      }
    });

    /* 검색창 관련 함수 */
    const searchEl = $(".search");
    const searchInputEl = searchEl.find("input");

    searchEl.click(function () {
      searchInputEl.focus();
      searchInputEl.val('');
    })

    searchInputEl.on("focus", function() {
      searchEl.addClass("focused");
      searchInputEl.attr("placeholder", "검색할 내용을 입력하세요.");
    });

    searchInputEl.on("blur", function() {
      searchEl.removeClass("focused");
      // searchInputEl.val('');
      searchInputEl.attr("placeholder", "");
    });

    /* 검색창 enterkey 이벤트 */
    $(".search_input").on("keydown", function(e){
      if(e.keyCode === 13) {
        let list = _this.read();
        if (list) {
          _this.grid.resetData(list);
          _this.cnt = _this.readCnt();
          _this.pagination.setTotalItems(_this.cnt);
        }
      }
    });


    /* 게시글 상세보기 */
    this.grid.on('click', (ev) => {
      let _this = this;
      let selectedColumn = ev.columnName;

      /* 내용을 선택하는 경우에만 수행 */
      if (selectedColumn != "content") return;

      /* Column을 클릭했을 때만 수행 */
      let focuesCell = this.grid.getFocusedCell();

      if (focuesCell) {

        let review_id = _this.grid.getRow(ev.rowKey).review_id;
        this.readOne(review_id);
      }
    });
  },

  /* CRUD 함수들 */
  /* CREATE */
  create: function (data) {
    let _this = this;

    data = data;

    $.ajax({
      type: "PUT",
      url: "/review",
      async: false,
      contentType:"application/json; charset=utf-8",
      data: JSON.stringify({
        title: data.title,
        content: data.content
      }),
      success: function(response) {
        $("dialog").hide();
        _this.read();
      },
      error: function () {

      }
    })
  },

  /* READ */
  read: function() {
    let _this = this;
    let data;

    $.ajax({
      type:"POST",
      url:"/review",
      async: false,
      contentType:"application/json; charset=utf-8",
      data: JSON.stringify({
        page: this.currentPage, // 현재 페이지
        limit: this.limit, // 한번에 몇 개의 데이터를 보여줄 것인지
        content: $(".search_input").val(),
      }),
      success: function(response){
        data = response;
        _this.grid.resetData(data);
        // _this.pagination.setTotalItems(response.length);
      },
      error: function(response) {
        console.log(response);
        swal({
          title: response.responseText,
          type: 'warning'
        })
      }
    });
    return data;
  },
  
  /* CRUD 도와주는 함수들 */
  /* 데이터 개수 세는 함수 */
  readCnt: function () {
    let _this = this;
    let cnt;

    $.ajax({
      type: "POST",
      url: "/review/cnt",
      async: false,
      contentType:"application/json; charset=utf-8",
      data: JSON.stringify({
        content: $(".search_input").val(),
      }),
      success: function(response){
        cnt = response;
      },
      error: function() {
      }
    });
    return cnt;
  }
};




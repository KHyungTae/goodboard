<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="ko">
<head>  <!-- include를 하여 어떤 화면을 만들더라도 <body>태그 안쪽의 내용만 바뀌고, 나머지는 항상 똑같이 작성 -->
<%@ include file="/WEB-INF/include/include-header.jspf" %>
</head>
<body>
<div class="container">
<div class="table-responsive">
<h2>게시판 목록</h2>
<table class="table">
	<colgroup>
		<col width="8%"/>
		<col width="10%"/>
		<col width="*"/>
		<col width="10%"/>
		<col width="15%"/>
	</colgroup>
	<thead>
		<tr>
			<th scope="col">글번호</th>
			<th scope="col">작성자</th>
			<th scope="col">제  목</th>
			<th scope="col">조회수</th>
			<th scope="col">작성일</th>
		</tr>
	</thead>
		<tbody>
			
		</tbody>
</table>

<div id="PAGE_NAVI" class="pad_5"></div>  <!-- 앞으로 페이징 태그가 그려질 부분 (밑에서 공통할수를 이용해 페이징 태그가 작성됨.) -->
<input type="hidden" id="PAGE_INDEX" name="PAGE_INDEX"/>  <!-- 현재 페이지 번호가 저장될 부분 -->

<br/>
<a href="#this" class="btn btn-default" id="write">글쓰기</a>
</div>
</div>
<!-- include를 하여 어떤 화면을 만들더라도 <body>태그 안쪽의 내용만 바뀌고, 나머지는 항상 똑같이 작성 -->
<%@ include file="/WEB-INF/include/include-body.jspf" %>
<script type="text/javascript">
	$(document).ready(function() {
		fn_selectBoardList(1);  //최초에 화면이 호출되면 1페이지의 내용을 조회하는 것을 의미.
		
		$("#write").on("click", function(e) { //글쓰기 버튼
			e.preventDefault();
			fn_openBoardWrite();
		});
		
		$("a[name='title']").on("click", function(e) {  //제목
			e.preventDefault();
			fn_openBoardDetail($(this)); //제목클릭하면 fn_openBoardDetail함수가 실행, 인자값으로 $(this)가 넘겨짐.
		});								 //이를 jQuery객체를 뜻한다. (게시글 제목인 <a>태그를 의미)
	});
	
	function fn_openBoardWrite() {
		var comSubmit = new ComSubmit();
		comSubmit.setUrl("<c:url value='/sample/openBoardWrite.do'/>");
		comSubmit.submit();
	}
	
	function fn_openBoardDetail(obj) {
		var comSubmit = new ComSubmit(); //폼에 동적으로 값을 추가하는 기능을 편하게 사용하기 위함인데, (ComSubmit객체 생성)
		comSubmit.setUrl("<c:url value='/sample/openBoardDetail.do'/>");
		/* obj.parent().find("#IDX").val()은 jQuery를 이용하여 <a>태그의 부모노드 내에서 IDX라는 값을 가진 태그를 찾아
		   그 태그의 값을 가져오도록 한 것이다. */
		comSubmit.addParam("IDX", obj.parent().find("#IDX").val()); //이것을 addParam함수가 그 역할을 해주고,
		comSubmit.submit();											//서버로 전송될 키,값을 인자값으로 받는다.
	}
	
	function fn_selectBoardList(pageNo) { //파라미터로 pageNo를 받는다. (pageNo는 호출하고자 하는 페이지 번호를 의미)
		var comAjax = new ComAjax();  //coomon.js파일에 ComAjax를 사용.
		comAjax.setUrl("<c:url value='/sample/selectBoardList.do'/>");
		comAjax.setCallback("fn_selectBoardListCallback"); //ajax요청이 완료된 후 호출될 함수의 이름을 지정
		comAjax.addParam("PAGE_INDEX", pageNo);  //현재 페이지 번호
		comAjax.addParam("PAGE_ROW", 10);  //한페이지에 보여줄 행(데이터)의 수
		comAjax.ajax();
	}
	
	//ajax호출이 되고 난 후 실행되는 콜백함수로 여기서는 화면을 다시 그리는 역할을 수행.
	function fn_selectBoardListCallback(data) {  //(data)는 서버에 전송된 json형식의 결과값이다.
		var total = data.TOTAL;
		var body = $("table>tbody");
		body.empty();
						  //기존 c:otherwise태그 부분
		if(total == 0) {  //만약 조회된 결과가 0일 경우(데이터가 없을경우), 조회된 결과가 없기 때문에
			var str = "<tr>" + "<td colspan='5'>조회된 결과가 없습니다.</td>" + "</tr>"; //이 문구가 표시된다.
			body.append(str);
		}
		else {  //조회된 결과가 0이 아닐경우(데이터가 존재할 경우)
			var params = {  /* common.js파일에서 만든 gfn_renderPaging함수를 수행하기 위해서 파라미터를 만드는 과정이고
						       var변수명={} 선언을 하면 Object가 만들어지고, 거기에 각각 key와 value형식으로 값을 추가한다.*/
					divId : "PAGE_NAVI", //( divId는 key, "PAGE_NAVI"는 value형식이다.")
					pageIndex : "PAGE_INDEX", //( pageIndex는 key, "PAGE_INDEX"는 value형식이다.")
					totalCount : total, //( totalCount는 key, "total"는 value형식이다.")
					eventName : "fn_selectBoardList" //( eventName는 key, "fn_selectBoardList"는 value형식이다.")
			};
			gfn_renderPaging(params); //gfn_rederPageing함수를 호출하면 object의 값을 이용하여 페이징 태그를 만든다.
			
			/* 기존 c:forEach태그 부분 */
			var str = "";
			//data.list가 서버에서 보내준 데이터, 이를 이용해서 jQuery의 .each함수를 사용하여 HTML태그를 만들어준다.
			$.each(data.list, function(key, value) {
				str += "<tr>" + 
						    "<td>" + value.RNUM + "</td>" + 
							"<td>" + value.CREA_ID + "</td>" +
							"<td class='title'>" +
								"<a href='#this' name='title'>" + value.TITLE + "</a>" + 
								"<input type='hidden' name='title' id='IDX' value=" + value.IDX + ">" + "</td>" +
							"<td>" + value.HIT_CNT + "</td>" +
							"<td>" + value.CREA_DTM + "</td>" +
					   "</tr>";
			});
			body.append(str);
			
			//새롭게 추가된 각각의 목록의 제목에 상세보기로 이동할 수 있도록 click이벤트를 바인딩 함.
			$("a[name='title']").on("click", function(e) {  //제목
				e.preventDefault();
				fn_openBoardDetail($(this));
			});
		}
	}
</script>
</body>
</html>
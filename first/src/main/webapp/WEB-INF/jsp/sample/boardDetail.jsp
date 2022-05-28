<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="ko">
<head>
<%@ include file="/WEB-INF/include/include-header.jspf" %>
</head>

<body style="text-align: -webkit-center;">
<div class="container">
<div class="table-responsive">
<table class="table">

	<colgroup>
		<col width="15%"/>
		<col width="35%"/>
		<col width="15%"/>
		<col width="35%"/>
	</colgroup>
	<caption><h2>게시글 상세</h2></caption>
	
	<tbody>
	
		<tr>
			<th scope="row" style="text-align:center;">글번호</th>
			<td>${map.IDX }</td>
			<th scope="row" style="text-align:center;">조회수</th>
			<td>${map.HIT_CNT }</td>
		</tr>
		<tr>
			<th scope="row" style="text-align:center;">작성자</th>
			<td><c:out value="${map.CREA_ID }" escapeXml="true"></c:out></td>
			
			<th scope="row" style="text-align:center;">작성시간</th>
			<td>${map.CREA_DTM }</td>
		</tr>
		<tr>
			<th scope="row" style="text-align:center;">제  목</th>
			<td colspan="3" style="text-align:left;"><c:out value="${map.TITLE }" escapeXml="true"></c:out></td>
		</tr>
		<tr>
			<td colspan="4"><textarea class="content_view" readonly><c:out value="${map.CONTENTS }" escapeXml="true"></c:out></textarea></td>
		</tr>
		
		
		
		<%-- <tr>
				<th scope="row">첨부파일</th>  <!-- 추가: 첨부파일을 보여주는 부분 -->
				<td colspan="3">
						<c:forEach var="row" items="${list }">
								<input type="hidden" id="IDX" value="${row.IDX }">
								<a href="#this" name="file">${row.ORIGINAL_FILE_NAME }</a>
								
								(${row.FILE_SIZE }kb)
						</c:forEach>
				</td>
		</tr> --%>


	</tbody>
</table>

<br/>
<div style="float:right;">
<a href="#this" class="btn btn-default" id="list">목록으로</a>
</div>
<div style="float: left;">
<a href="#this" class="btn btn-default" id="update">수정하기</a>
</div>
</div>
</div>
<%@ include file="/WEB-INF/include/include-body.jspf" %>
<script type="text/javascript">
	$(document).ready(function() {
		$("#list").on("click", function(e) {  //목록으로 버튼
			e.preventDefault();
			fn_openBoardList();
		});

		$("#update").on("click", function(e) {  //수정하기 버튼
			e.preventDefault();
			fn_openBoardUpdate();
		});

		/* $("a[name='file']").on("click", function(e) {  //파일 이름 (첨부파일 다운로드)
			e.preventDefault();
			fn_downloadFile($(this));  //파일 명을 클릭하면 fn_downloadFile함수가 실행
		}) */
	});

	function fn_openBoardList() {
		var comSubmit = new ComSubmit();
		comSubmit.setUrl("<c:url value='/sample/openBoardList.do'/>");
		comSubmit.submit();
	}

	function fn_openBoardUpdate() {
		var idx = "${map.IDX}";
		var comSubmit = new ComSubmit();
		comSubmit.setUrl("<c:url value='/sample/openBoardUpdate.do'/>");
		comSubmit.addParam("IDX", idx);
		comSubmit.submit();
	}
	//첨부파일 다운로드
	/* function fn_downloadFile(obj) {
		var idx = obj.parent().find("#IDX").val(); //fn_downloadFile함수에 해당 파일의 IDX값을 가져와서
		var comSubmit = new ComSubmit();
		comSubmit.setUrl("<c:url value='/common/downloadFile.do'/>");  // /common/downloadFile.do 주소로
		comSubmit.addParam("IDX", idx);
		comSubmit.submit();  //submit한다.
	} */
</script>
</body>
</html>
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
<form id="frm">  <!-- 업로드할때 추가  name="frm" enctype="multipart/form-data" -->
	<table class="table">
		<colgroup>
			<col width="15%"/>
			<col width="35%"/>
			<col width="15%"/>
			<col width="35%"/>
		</colgroup>
		<caption><h2>게시글 수정</h2></caption>
		<tbody>
			<tr>
				<th scope="row" style="text-align:center;">글 번호</th>
				<td>
					${map.IDX }
					<input type="hidden" id="IDX" name="IDX" value="${map.IDX }">
				</td>
				<th scope="row" style="text-align:center;">조회수</th>
				<td>${map.HIT_CNT }</td>
			</tr>
			<tr>
				<th scope="row" style="text-align:center;">작성자</th>
				<td>${map.CREA_ID }</td>
				<th scope="row" style="text-align:center;">작성시간</th>
				<td>${map.CREA_DTM }</td>
			</tr>
			<tr>
				<th scope="row" style="text-align:center;">제목</th>
				<td colspan="3">
					<input type="text" id="TITLE" name="TITLE" class="wdp_90" maxlength="30" value="${map.TITLE }"/>
				</td>
			</tr>
			<tr>
				<td colspan="4" class="view_text">
					<textarea rows="20" cols="100" title="내용" id="CONTENTS" class="content_view" name="CONTENTS" onkeyup="chkMsgLength(500,this,txt_Byte);">${map.CONTENTS }</textarea>
					<p style="float:right"><span id="txt_Byte" style="padding-left:60;">0</span>/500</p>
				</td>
			</tr>
			<%-- <tr>
				<th scope="row">첨부파일</th>
				<td colspan="3">
					<div id="fileDiv">
						<c:forEach var="row" items="${list }" varStatus="var">
							<p> <!-- 기존에 저장된 파일에서는 해당파일번호인 IDX값이 존재 이를이용해 신규파일정보와 아닌것을 구분. -->
								<input type="hidden" id="IDX" name="IDX_${var.index }" value="${row.IDX }">
								<a herf="#this" id="name_${var.index }" name="name_${var.index }">${row.ORIGINAL_FILE_NAME }</a>
								<input type="file" id="file_${var.index }" name="file_${var.index }">
								(${row.FILE_SIZE }kb)
								<a href="#this" class="btn" id="delete_${var.index }" name="delete_${var.index }">삭제</a>
							</p>
						</c:forEach>
					</div>
				</td>
			</tr> --%>
		</tbody>
	</table>
</form>

<!-- <a href="#this" class="btn" id="addFile">파일 추가</a> -->
<div style="float:left;">
<a href="#this" class="btn btn-default" id="update">저장하기</a>
<a href="#this" class="btn btn-default" id="delete">삭제하기</a>
</div>
<div style="float:right;">
<a href="#this" class="btn btn-default" id="list">목록으로</a>
</div>
</div></div>
<%@ include file="/WEB-INF/include/include-body.jspf" %>
<script type="text/javascript">

	var gfv_count = '${fn:length(list)+1}';
	$(document).ready(function() {
		$("#list").on("click", function(e) {  //목록으로 버튼
			e.preventDefault();
			fn_openBoardList();
		});
		
		$("#update").on("click", function(e) {  //저장하기 버튼
			e.preventDefault();
			fn_updateBoard();
		});
		
		$("#delete").on("click", function(e) {  //삭제하기 버튼
			e.preventDefault();
			fn_deleteBoard();
		});
		
		/* $("#addFile").on("click", function(e) {
			e.preventDefault();
			fn_addFile();
		}); */
		
		/* $("a[name='delete']").on("click", function(e) {
			e.preventDefault();
			fn_deleteFile($(this));
		}); */
	});
	
	function fn_openBoardList() {
		var comSubmit = new ComSubmit();
		comSubmit.setUrl("<c:url value='/sample/openBoardList.do'/>");
		comSubmit.submit();
	}
	
	function fn_updateBoard() {
		var comSubmit = new ComSubmit("frm");
		comSubmit.setUrl("<c:url value='/sample/updateBoard.do'/>");
		comSubmit.submit();
	}
	
	function fn_deleteBoard() {
		var comSubmit = new ComSubmit();
		comSubmit.setUrl("<c:url value='/sample/deleteBoard.do'/>");
		comSubmit.addParam("IDX", $("#IDX").val());
		var confirm_val = confirm("정말 삭제하시겠습니까?");
		if(confirm_val == true) {
		} else if(confirm_val == false) {
			return false;
		}
		comSubmit.submit();
	}
	
	/* function fn_addFile() {
		var str="<p>" + "<input type='file' id='file_"+(gfv_count)+"' name='file_"+(gfv_count)+"'>"
		+"<a href='#this' class='btn' id='delete_"+(gfv_count)+"' name='delete_"+(gfv_count)+"'>삭제</a>" + "</p>";
		$("#fileDiv").append(str);
		$("#delete_"+(gfv_count++)).on("click", function(e) {
			e.preventDefault()
			fn_deleteFile($(this));
		});
	} */
	
	/* function fn_deleteFile(obj) {
		obj.parent().remove();
	} */
</script>

<script type="text/javascript">

//메세지 바이트 체크
function chkMsgLength(intMax,objMsg,st) {
	var length = lengthMsg(objMsg.value);
		st.innerHTML = length;//현재 byte수를 넣는다
	if (length > intMax) {
		alert("문자메세지는 500글자 이상이므로 초과된 글자수는 자동으로 삭제됩니다.\n");
		objMsg.value = objMsg.value.replace(/\r\n$/, "");
		objMsg.value = assertMsg(intMax,objMsg.value, st);
	}
}


function TempNull() {
	return false;
}


// 현재 메시지 바이트 수 계산
function lengthMsg(objMsg) {
	var nbytes = 0;
		for (i=0; i<objMsg.length; i++) {
			var ch = objMsg.charAt(i);
		if(escape(ch).length > 50) {
			nbytes += 2;
		}
		else if (ch == '\n') {
		if (objMsg.charAt(i-1) != '\r') {
			nbytes += 1;
			}
		}
		else if (ch == '<' || ch == '>') {
			nbytes += 50;
		}
		else {
			nbytes += 1;
		}
	}
		return nbytes;
}

// 600 바이트 넘는 문자열 자르기

	function assertMsg(intMax, objMsg, st) {
		var inc = 0;
		var nbytes = 0;
		var msg = "";
		var msglen = objMsg.length;

		for (i = 0; i < msglen; i++) {
			var ch = objMsg.charAt(i);

			if (escape(ch).length > 50) {
				inc = 2;
			} else if (ch == '\n') {
				if (objMsg.charAt(i - 1) != '\r') {
					inc = 1;
				}
			} else if (ch == '<' || ch == '>') {
				inc = 50;
			} else {
				inc = 1;
			}

			if ((nbytes + inc) > intMax) {
				break;
			}

			nbytes += inc;
			msg += ch;
		}

		st.innerHTML = nbytes; //현재 byte수를 넣는다

		return msg;
	}
</script>
</body>
</html>
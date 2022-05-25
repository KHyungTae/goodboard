<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="ko">
<head>  <!-- include를 하여 어떤 화면을 만들더라도 <body>태그 안쪽의 내용만 바뀌고, 나머지는 항상 똑같이 작성 -->
<%@ include file="/WEB-INF/include/include-header.jspf" %> 
</head>
<body>
<div class="container" style="text-align: -webkit-center;">
<div class="table-responsive" style="overflow:hidden; width: 1000px">
<form id="frm" style="text-align: -webkit-center;"> <!-- 해당 폼을 Multipart형식임을 알려줌 (사진,동영상,등 글자가아닌 파일은 모두 Multipart형식 데이터이다.) 업로드할떄 name="frm" enctype="multipart/form-data" -->
	<table class="table">
		<colgroup>
			<col width="15%">
			<col width="*"/>
		</colgroup>
		<caption><h2>게시글 작성</h2></caption>
		<tbody>
			<tr>
				<th scope="row" style="text-align:center;">비밀번호</th>  <!-- name="CREA_PW"은 SQL.xml의 변수명과 같다. name을 키로 데이터가 서버에 전송 -->
				<td><input type="text" id="crea_pw" name="CREA_PW" class="wdp_90" maxlength="4"></input></td>
			</tr>
			<tr>
				<th scope="row" style="text-align:center;">제  목</th>  <!-- name="TITLE"은 SQL.xml의 변수명과 같다. name을 키로 데이터가 서버에 전송 -->
				<td><input type="text" id="title" name="TITLE" class="wdp_90" maxlength="30"></input></td>
			</tr>
			
			<tr>
				<th scope="row" style="text-align:center;">작성자</th>  <!-- name="CREA_ID"은 SQL.xml의 변수명과 같다. name을 키로 데이터가 서버에 전송 -->
				<td><input type="text" id="crea_id" name="CREA_ID" class="wdp_90" maxlength="10"></input></td>
			</tr>
			
			<tr>  
				<td colspan="2" class="view_text">
					<textarea class="content_view" rows="20" cols="100" title="내용" id="contents" name="CONTENTS" onkeyup="chkMsgLength(500,this,txt_Byte);" placeholder="500자 이내로 작성하실 수 있습니다."></textarea>
					<p style="float:right;"><span id="txt_Byte" style="padding-left:60;">0</span>/500</p>
				</td>   <!-- name="CONTENTS"은 SQL.xml의 변수명과 같다. name을 키로 데이터가 서버에 전송 -->
				
          
        
			</tr>
		</tbody>
	</table>
	<!-- <div id="fileDiv">
		<p>
			<input type="file" id="file" name="file_0"> 업로드 + 다중업로드
			<a href="#this" class="btn" id="delete" name="delete">삭제</a>
		</p>
	</div>
	
	<br/><br/>
	
	<a href="#this" class="btn" id="addFile">파일 추가</a> -->
</form>
<div style="float: left;">
<a href="#this" class="btn btn-default" id="write">작성하기</a>
</div>
<div style="float: right;">
<a href="#this" class="btn btn-default" id="list">목록으로</a>
</div>
</div>
</div>
<!-- include를 하여 어떤 화면을 만들더라도 <body>태그 안쪽의 내용만 바뀌고, 나머지는 항상 똑같이 작성 -->
<%@ include file="/WEB-INF/include/include-body.jspf" %>
<script type="text/javascript">
	var gfv_count = 1;
	$(document).ready(function() {
		$("#list").on("click", function(e) {  //목록으로 버튼
			e.preventDefault();
			fn_openBoardList();
		});
		
		$("#write").on("click", function(e) {  //작성하기 버튼
			e.preventDefault();
			fn_insertBoard();
		});
		
		/* $("#addFile").on("click", function(e) {  //파일 추가 버튼
			e.preventDefault();
			fn_addFile();
		}); */
		
		/* $("a[name='delete']").on("click", function(e) {  //삭제 버튼
			e.preventDefault();
			fn_deleteFile($(this));
		}); */
	});
	
	function fn_openBoardList() {
		var comSubmit = new ComSubmit();  //자바스크립트 객체를 생성 (common.js에 submit기능을 하는 함수가 ComSubmit객체이다)
		comSubmit.setUrl("<c:url value='/sample/openBoardList.do'/>");  //JSTL태그로 ContextPath를 자동으로 붙임.
		comSubmit.submit();  //ComSubmit객체의 submit함수를 이용하여 전송(submit).
	}
	/* Comsubmit객체는 객체가 생성될 때, form의 아이디(frm)가 인자 값으로 들어오면 그 폼을 전송하고, 
	   파라미터가 없으면 숨겨둔 폼을 이용하여 데이터를 전송하도록 구현함. */ 
	function fn_insertBoard() {  
		var crea_pw = $("#crea_pw").val()
		var title = $("#title").val()
		var crea_id = $("#crea_id").val()
		var contents = $("#contents").val()
		
		if (crea_pw == null || crea_pw == '') {
			alert("비밀번호를 입력하세요.");
			return 0;
		} else if (title == null || title == '') {
			alert("제목을 입력하세요.");
			return 0;
		} else if (crea_id == null || crea_id == '') {
			alert("작성자를 이름을 입력하세요.");
			return 0;
		} else if (contents == null || contents == '') {
			alert("내용을 입력하세요.");
			return 0;
		}
		
		$("#CREA_PW").val(crea_pw)
		$("#TITLE").val(title)
		$("#CREA_ID").val(crea_id)
		$("#contents").val(contents)
		
		var comSubmit = new ComSubmit("frm");  //전송할 데이터가 있는 frm이라는 id를 가진 form을 이용하도록 id를 넘겨줌.
		comSubmit.setUrl("<c:url value='/sample/insertBoard.do'/>");
		alert("게시물이 등록되었습니다.");
		comSubmit.submit();
	}
	
	/* <input type='file'>태그의 name이 동일할 경우, 서버에서는 단 하나의 파일만 전송되는 문제가 발생하기 때문에
	   gfv_count라는 전역변수를 선언하고, 태그가 추가될때마다 그 값을 1씩 증가시켜서 name값이 계속 바뀌도록 함. 
	   
	   삭제 버튼을 누르면 해당 버튼이 위치한 <p>태그 자체를 삭제하도록 구성. */
	
	/* function fn_addFile() { 
		var str = "<p><input type='file' name='file_" + (gfv_count++)
				+ "'><a href='#this' class='btn' name='delete'>삭제</a></p>";
		$("#fileDiv").append(str);
		$("a[name='delete']").on("click", function(e) {
			e.preventDefault();
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

// 80 바이트 넘는 문자열 자르기

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

<script type="text/javascript">



</script>

</body>
</html>
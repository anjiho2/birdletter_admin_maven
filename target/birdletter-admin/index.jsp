<%@page import="com.challabros.birdletter.admin.dto.AdminDto" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<%@include file="/common/jsp/top.jsp" %>

<script type="text/javascript" src="<%=webRoot%>/dwr/interface/loginService.js"></script>
<script type="text/javascript">

function loginCheck() {
	var adminId = $("#adminId").val();
	var adminPass = $("#adminPass").val();
	
	if (adminId == "") {
		alert("아이디를 입력하세요.");
		$("#adminId").focus();
		return;
	}
	if (adminPass == "") {
		alert("비밀번호를 입력하세요.");
		$("#adminPass").focus();
		return;
	}
	
	loginService.loginChk(adminId, adminPass, function(chk) {
		if (chk > 0 ) {
			loginOk(adminId, adminPass);
		}else {
			alert("아이디또는 비밀번호가 잘못됬습니다.");
			return;
		}
	});
}

function loginOk(val1, val2) {
	with(document.frm) {
		param1.value = val1;
		param2.value = val2;
		page_gbn.value = "session";
		action = "<%=webRoot%>/login.do";
		submit();
	}
}
</script>
<% if (session.getAttribute("adminInfo") == null) { %>
<body id="login">
<form name="frm" method="post">
<input type="hidden" name="param1" />
<input type="hidden" name="param2" />
<input type="hidden" name="page_gbn" id="page_gbn">
<header>
	<div>
		<h1 style="margin-left: 80px;">관리자 로그인</h1><br>
	</div>
</header>
<section id="content">
	<form action="submit.jsp" id="loginform">
		<fieldset>
			<section><label for="username">아이디</label>
				<div><input type="text" id="adminId" name="adminId" autofocus class="input width-full"></div>
			</section>
			<section><label for="password">비밀번호</label>
				<div><input type="password" id="adminPass" name="adminPass" class="input width-full" onkeypress="javascript:if(event.keyCode == 13){loginCheck(); return false;}"></div>
				<!-- <div><label for="remember" class="checkbox mid-margin-top"><input type="checkbox" id="remember" name="remember">아이디 저장</label></div> -->
			</section>
			<section>
				<div class="small-margin-top"><a href="javascript:loginCheck();" class="button white-gradient glossy mid-margin-bottom">로그인</a></div>
			</section>
		</fieldset>
	</form>
</section>
</form>
<% } else { %>
<form name="frm" method="post">
<input type="hidden" name="page_gbn" id="page_gbn">
</form>
<jsp:include page="/daul/include/header.jsp" flush="false"/>

<%-- <%@ include file="/common/jsp/top_menu.jsp"%> --%>
<%} %>
</body>
</html>

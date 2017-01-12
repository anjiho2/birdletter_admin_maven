<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String webRoot = request.getContextPath();
	response.setStatus(HttpServletResponse.SC_OK);
%>
<%@ include file="/common/jsp/common.jsp" %>
<!DOCTYPE HTML>
<html xmlns="http://www.w3.org/1999/xhtml" lang="ko" xml:lang="ko">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
	<meta http-equiv="X-UA-Compatible" content="IE=edge" />
	<meta name="viewport"  content="width=device-width, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0, user-scalable=no"/>
	<title></title>
	<script src="<%=webRoot%>/js/page.js"></script>
	<link rel="shortcut icon" href="favicon.ico">
	
	<link rel="stylesheet" type="text/css" href="<%=webRoot%>/daul/css/reset.css">
	<link rel="stylesheet" type="text/css" href="<%=webRoot%>/daul/css/button.css">

</head>
<script type="text/javascript">
function goMain() {
	with(document.frm) {
		action = "<%=webRoot%>/dashboard.do";
		page_gbn.value = "dashboardList";
		submit();
	}
}
</script>
<body>
<form name="frm" method="post">
<input type="hidden" name="page_gbn" id="page_gbn">
	<div class="sub">
		<div class="subbody container"> 
			<div class="wrap">
				<div class="inbody">
					<div class="error_container">
						<h1>500</h1>
						<h2>Server error!</h2>
						<hr>
						<p>대신에 다음과 같은 방법을 시도하십시오.</p>
						<ul>
							<li><a class="button" href="<%=webRoot%>/index.jsp" style="margin-right:30px;">로그인 페이지 이동</a></li>
							<li><a class="button" href="javascript:goMain();" style="margin-right:30px;">메인 페이지 이동</a></li>
							<li><a class="button" href="javascript:history.go(-1);">이전 페이지 이동</a></li>
						</ul>
					</div>

				</div>
			</div>
		</div>
	</div>
</form>
</body>

</html>

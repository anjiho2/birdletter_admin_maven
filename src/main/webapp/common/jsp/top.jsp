<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/common/jsp/common.jsp" %>
<%
	String webRoot = request.getContextPath();
	String serverName = request.getServerName();
%>

<!--  jquery plugin -->
<%-- <script type='text/javascript' src="<%=webRoot%>/common/js/jquery-1.11.3.min.js"></script>

<link rel="stylesheet" href="<%=webRoot%>/Bootstrap/css/bootstrap.min.css">
<link rel="stylesheet" href="<%=webRoot%>/Bootstrap/css/bootstrap-theme.min.css">
<!-- bootstrap 스크립트 -->
<script src="<%=webRoot%>/Bootstrap/js/bootstrap.min.js"></script>
--%>
<link rel="shortcut icon" href="<%=webRoot%>/common/images/icon/favicon.ico" type="image/x-icon">
<link rel="icon" href="<%=webRoot%>/common/images/icon/favicon.ico" type="image/x-icon">

<!-- 공통 유틸 스크립트 -->
<script type='text/javascript' src="<%=webRoot%>/common/js/common.js"></script>
<script type='text/javascript' src="<%=webRoot%>/common/js/comment.js"></script>
<script type='text/javascript' src="<%=webRoot%>/js/blank-check.js"></script>
<script type='text/javascript' src="<%=webRoot%>/js/page.js"></script>
<script type='text/javascript' src="<%=webRoot%>/js/selectbox.js"></script>
<%-- <script type='text/javascript' src="<%=webRoot%>/js/datepicker.js"></script> --%>
<script type='text/javascript' src="<%=webRoot%>/js/few-weeks.js"></script>
<!-- 페이징 관련 필수 스트립트 -->
<script type='text/javascript' src="<%=webRoot%>/common/js/com_page.js"></script>
<!-- dwr 필수 스트립트 -->
<script type='text/javascript' src="<%=webRoot%>/dwr/engine.js"></script>
<script type='text/javascript' src="<%=webRoot%>/dwr/util.js"></script>
 
<script type="text/javascript" src="<%=webRoot%>/daul/js/libs/jquery-1.10.2.js"></script>
<%-- <script type="text/javascript" src="<%=webRoot%>/daul/js/libs/jquery-1.10.2.min.js"></script> --%>
<%-- <script type="text/javascript" src="<%=webRoot%>/daul/js/libs/jquery-migrate-1.2.1.min.js"></script> --%>
<script type="text/javascript" src="<%=webRoot%>/daul/js/libs/jquery-ui-1.9.2.custom.min.js"></script>
<%-- <script type="text/javascript" src="<%=webRoot%>/daul/js/libs/modernizr.custom.js"></script> --%>
<script type="text/javascript" src="<%=webRoot%>/daul/js/libs/jquery.cookie.js"></script>
<%-- <script type="text/javascript" src="<%=webRoot%>/daul/js/libs/html5shiv.js"></script> --%>
<%-- <script type="text/javascript" src="<%=webRoot%>/daul/js/plugins.js"></script> --%>
<%-- <script type="text/javascript" src="<%=webRoot%>/daul/js/script_html.js"></script> --%>

<%-- <script type="text/javascript" charset="utf-8" src="<%=webRoot%>/daul/js/plugins/customdatepicker.js"></script> --%><!--날짜선택-->
<script type="text/javascript" charset="utf-8" src="<%=webRoot%>/daul/js/plugins/jquery.mCustomScrollbar.min.js"></script><!--커스텀 스크롤-->
<%-- <script type="text/javascript" charset="utf-8" src="<%=webRoot%>/daul/js/plugins/jquery.slides.js"></script> --%><!--이미지 슬라이드-->
<%-- <script type="text/javascript" charset="utf-8" src="<%=webRoot%>/daul/js/plugins/masonry.pkgd.js"></script> --%><!--벽돌레이아웃-->
<%-- <script type="text/javascript" charset="utf-8" src="<%=webRoot%>/daul/js/plugins/calendar_mon_ahmax.js"></script> --%><!--월 선택 달력-->
<%-- <script type="text/javascript" charset="utf-8" src="<%=webRoot%>/daul/js/plugins/ibutton.min.js"></script> --%><!--스위치 모양 라디오, 체크박스-->

<%-- <script type="text/javascript" charset="utf-8" src="<%=webRoot%>/daul/js/config.js"></script> --%><!-- (기본)기본 설정 값-->
<!--[if lt IE 7]><script type="text/javascript" charset="utf-8" src="css/font/elegant_font/lte-ie7.js"></script><![endif]-->
	
<script type='text/javascript' src="<%=webRoot%>/common/js/alert.js"></script><!-- jquery alert -->
<script type='text/javascript' src="<%=webRoot%>/common/js/jquery.confirm.js"></script>	<!-- jquery alert -->

<script src='//cdn.rawgit.com/fgelinas/timepicker/master/jquery.ui.timepicker.js'></script>
	
<link rel="stylesheet" type="text/css" href="<%=webRoot%>/daul/css/jquery/jquery-ui.css"><!-- jQuery ui(기본)-->
<link rel="stylesheet" type="text/css" href="<%=webRoot%>/daul/css/reset.css"><!-- (기본)-->
<link rel="stylesheet" type="text/css" href="<%=webRoot%>/daul/css/plugins/jquery.mCustomScrollbar.css"><!--커스텀 스크롤-->
<link rel="stylesheet" type="text/css" href="<%=webRoot%>/daul/css/plugins/ibutton.css"><!--스위치 모양 라디오, 체크박스-->
<link rel="stylesheet" type="text/css" href="<%=webRoot%>/daul/css/plugins/jquery.fancybox.css"><!--갤러리 fancybox -->
<link rel="stylesheet" type="text/css" href="<%=webRoot%>/daul/css/font/elegant/style.css"><!--elegant 폰트아이콘-->
<link rel="stylesheet" type="text/css" href="<%=webRoot%>/daul/css/font/entypo/style.css"><!--entypo 폰트아이콘-->
<link rel="stylesheet" type="text/css" href="<%=webRoot%>/daul/css/icons.css"><!--이미지 아이콘-->
<link rel="stylesheet" type="text/css" href="<%=webRoot%>/daul/css/common.css"><!-- (기본)공통 CSS-->
<link rel="stylesheet" type="text/css" href="<%=webRoot%>/daul/css/colors.css"><!-- (기본)컬러 CSS-->
<link rel="stylesheet" type="text/css" href="<%=webRoot%>/daul/css/button.css"><!-- (기본)버튼 CSS-->
<link rel="stylesheet" type="text/css" href="<%=webRoot%>/daul/css/style.css"><!-- (기본)-->
<link rel="stylesheet" type="text/css" href="<%=webRoot%>/daul/css/theme_white.css"><!-- (기본)-->
<link rel="stylesheet" type="text/css" href="<%=webRoot%>/common/css/jquery.confirm.css"><!-- jquery alert css -->


<!-- 공통으로 쓰는 값 모여있는 스크립트 -->
<script src="<%=webRoot%>/js/value.js"></script>
<!-- 페이지 이동 스크립트 -->
<script src="<%=webRoot%>/js/page.js"></script>

<meta charset="utf-8">
<title>버드레터 관리자</title>
</head>
//쿠키 저장
function setCookie(key, value) {  
	$.cookie(key, value, {expires:10000});
}
//쿠키 불러오기
function getCookie(key) {  
	return $.cookie(key); 
}

//테마 정보
var tmdata = String(getCookie('at_themeDatas'));
//세로 스크롤 정보
var vsdata = String(getCookie('at_vsDatas'));

//테마 정보 처리
if(tmdata == "[object Document]" || tmdata=="" || tmdata=="undefined" || tmdata=="null"){
	document.write("<link rel='stylesheet' type='text/css' href='daul/css/theme_white.css'>")
	$("input[id=theme-white]").attr("checked","checked");
}else{
	alert("black");
	document.write("<link rel='stylesheet' type='text/css' href='daul/css/theme_"+tmdata+".css'>")
	$("input[id=theme-"+tmdata+"]").attr("checked","checked");
}

//세로 스크롤 정보 처리
if(vsdata == "true"){
	$("input[id=vertical_lock]").attr("checked", "checked");
	
	$("html").css("overflow-y", "hidden");
	$("#foot").css({
		"position" : "fixed",
		"z-index" : 101,
		"width" : "100%",
		"left" : 0,
		"bottom" : 0
	});

	$( window ).resize(function() {
		//리사이즈 할 때 마다 스크롤 재 설정
		verticalLock();
	});
	verticalLock();
}

//테마 사용자 선택 처리
$("input[type=radio][name=tm_radio]").change(function(){
	var getdata = $("input[type=radio][name=tm_radio]:checked").val();
	setCookie('at_themeDatas', getdata);
	location.reload();
});
//세로 스크롤 사용자 선택 처리
$("input[type=checkbox][id=vertical_lock]").change(function(){
	setCookie('at_vsDatas', this.checked);
	location.reload();
});

//세로 스크롤 처리
function verticalLock(){
	var v_gab;
	if(window.innerWidth > 900){
		v_gab = 38;
	}else{
		v_gab = 78;
	}
	var contentHeight = window.innerHeight - $("#header").height() - $(".breadcrumb").height() - $("h2.title").height() - $("p.title_info").height() - $("#foot").height()-v_gab;
	var navHeight = window.innerHeight - $("#header").height() - $("#foot").height()-27;

	$(".contentbody").css({
		"height" : contentHeight,
		"overflow-y" : "auto"
	});
	$(".side_nav").css({
		"height" : navHeight
	});
}


jQuery(function($) {
	//초기 실행
	$(document).ready(function() {
		//상위 메뉴 open
		var activeMc = $(".side_nav .active")
		$(activeMc).parent().parent("li").addClass("active");
		$(activeMc).parent().parent("li").parent().parent("li").addClass("active");
		$(".side_nav .sub.active").find("ul:first").show();
		//top 버튼
		$().UItoTop({ easingType: 'easeOutQuart' });
		//커스텀 라디오 체크박스 초기화
		$('input:radio[checked=checked], input:checkbox[checked=checked]').each(function(){
			if ($(this).parent().is('label.button') || $(this).parent().is('label.radio') || $(this).parent().is('label.checkbox')){
				$(this).parent().addClass('active');
			}
		});
		
		// prettify 코드 가공
		$(".prettyprint").each(function(){
			if($(this).is(".noReplace")){
				return;
			}
			var htmlText = $(this).html().replace(/&/g, '&amp;').replace(/</g, '&lt;').replace(/>/g, '&gt;');
			//var htmlText = $(this).html().replace(/&/g, '&amp;').replace(/</g, '&lt;').replace(/>/g, '&gt;').replace(/\t/g, '&#9;').replace(/\n/g, '\r\n');
			$(this).empty();
			$(this).html(htmlText);
		});
		
		var sc_theme = "ligth-2";
		if(tmdata=="white") sc_theme = "dark-2";
		
		//좌측 메뉴 스크롤 활성화
		$(".side_nav").mCustomScrollbar({
			scrollButtons:{enable:false},
			scrollInertia :200,
			theme:sc_theme
		});
		$(".side_nav .mCSB_container").css({"margin-right" :0});
		$(".side_nav .mCSB_scrollTools").css({"margin-right" :-8});
		
	});
	
	//위젯 처리
	$(".portlet").addClass( "ui-widget ui-widget-content ui-helper-clearfix ui-corner-all" )
		.find( ".portlet-header" )
			.prepend( "<em class='ui-icon'></em>")
			.end()
	$(".portlet").find(".portlet-header .ui-icon").unbind();
	$(".portlet").find(".portlet-header .ui-icon").click(function() {
		if($(this).is(".active")){
			$(this).removeClass("active").parent().parent().find(".portlet-content").slideDown("fast",function(){

			});
		}else{

			$(this).addClass("active").parent().parent().find(".portlet-content").slideUp("fast",function(){

			});
		}
	});

	//메뉴 클릭
	$(".side_nav .sub > a").click(function () {
		var parent = $(this).parent("li");
		if ( $(parent).is('.active')) {
			$(parent).removeClass("active").find("ul:first").slideUp("easeOutQuart",function(){
				$(".side_nav").mCustomScrollbar("update");
			});
		}else{
			$(parent).addClass("active").find("ul:first").slideDown("easeOutQuart",function(){
				$(".side_nav").mCustomScrollbar("update");
			});
		}
    });
	
	//메뉴 토글
	$('.nav_btn>a').click(function(){
		$('.side_nav').toggle();
	});
	
	//윈도우창 리사이즈 이벤트
	var checkNum;
	$( window ).resize(function() {
		//반응형 메뉴 숨김
		if(window.innerWidth >900){
			if(checkNum <= 900){
				$('.side_nav').show();
			}
		}else{
			if(checkNum > 900){
				$('.side_nav').hide();
			}
		}
		checkNum=window.innerWidth;
	});
	
	//accordion
	$(".accordion>li>a").click(function(){
		var parent = $(this).parent("li");
		if($(parent).is(".active")){
			$(parent).removeClass("active").find(".content").slideUp("fast");
		}else{
			$(parent).parent().find("li.active").removeClass("active").find(".content").slideUp("fast");
			$(parent).addClass("active").find(".content").slideDown("fast");
		}
	});
	$(".accordion>li.active").find(".content").slideDown();
	

	// Style Radios and checkboxes style changes
	$('input:radio, input:checkbox').change( function(event)
	{
		var element = $(this),
			replacement = element.data('replacement'),
			checked = this.checked;
		// Update visual styles
		if (replacement)
		{
			// Update style
			replacement[checked ? 'addClass' : 'removeClass']('checked');
		}
		// Button labels
		else if (element.parent().is('label.button') || element.parent().is('label.radio') || element.parent().is('label.checkbox'))
		{
			element.parent()[checked ? 'addClass' : 'removeClass']('active');
		}
		
		// If radio, refresh others without triggering 'change'
		if (this.type === 'radio')
		{
			$('input[name="'+this.name+'"]:radio').not(this).each(function(i)
			{
				var input = $(this),
					replacement = input.data('replacement');

				// Switch
				if (replacement)
				{
					replacement[checked ? 'removeClass' : 'addClass']('checked');
				}
				// Button labels
				else if (input.parent().is('label.button') || input.parent().is('label.radio'))
				{
					input.parent()[checked ? 'removeClass' : 'addClass']('active');
				}
			});
		}
	});
	
	
	//Autoexpanding textareas
	$('.auto_textarea').each(function(){
		$(this).wrap('<div class="textareabox" style="position:relative;"/>');
		var textarea = $(this),
			originHeight = textarea.height(),
			originClientHeight = textarea[0].clientHeight,
			clone = textarea.clone(),
			extraHeight = parseInt(textarea.css('fontSize')),
			timer, content, cloneScrollHeight;
		$(textarea).css("overflow", "hidden");
		clone
			.removeAttr('id')
			.removeAttr('name')
			.addClass("clone_textarea")
			.css({
				position: 'absolute',
				top: 0,
				left: -9999,
				overflow: 'hidden',
			}).appendTo($(textarea).parent());
		
		textarea.focus(function() {
			var clone = $(this).parent().find(".clone_textarea")
			timer = setInterval(function() {
				content = textarea.val();
				clone.val(content);
				cloneScrollHeight = clone[0].scrollHeight;
				
				if (originClientHeight < cloneScrollHeight) {
					textarea.css('height', cloneScrollHeight + extraHeight);
				} else if (originClientHeight === cloneScrollHeight) {
					textarea.css('height', originHeight);
				}
			}, 100);
		});
		
		textarea.blur(function() {
			clearInterval(timer);
		});
	});
});
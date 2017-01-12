
// default  max year
var max_year = 2020;

// default  min year
var min_year = 2000;

// default  date format (xxxx-xx-xx)
var date_split_format = "-";

// default scroll Size
var scrollYSize = 32;

// today			
var dayObj	  	  = new Date();
var todayDate_year = dayObj.getFullYear();
var todayDate_mon  = ((dayObj.getMonth()+101)+"").substring(1,3);
var todayDate_date  = ((dayObj.getDate()+100)+"").substring(1,3);
var todayDate_full  = todayDate_year+date_split_format+todayDate_mon+date_split_format+todayDate_date;

//가속
var ingNum = 0;

//타이머 아이디
var timerID;

function customDatepicker(target){
	var id = target + "CustomDate";
	//
	$(function(){
		if( $("#" + target).attr("data-minyear") != undefined && $("#" + target).attr("data-minyear") != "") min_year = $("#" + target).attr("data-minyear");
		if( $("#" + target).attr("data-maxyear") != undefined && $("#" + target).attr("data-maxyear") != "") max_year = $("#" + target).attr("data-maxyear");
		if( $("#" + target).attr("data-startdate") != undefined && $("#" + target).attr("data-startdate") != "") {
			start_date = $("#" + target).attr("data-startdate");
		}else{
			start_date = todayDate_full;
		}

		var tInput  = $("#" + target).offset();
		var tHeight = $("#" + target).outerHeight();
		
		$(document.body).append($("<div style='display:none' style='background:#999999'><div id='"+ id +"'></div></div>"));
		
		$("#" + id).addClass("cd_ui_customDatePicker");
		$("#" + id).addClass("grey-gradient glossy");
		$("#" + id).html(makeCustomDate(id));
		
		$("#" + target).click(function(e){
			$("#" + id).parent().dialog({
				width:314,
				modal:true,
				dialogClass: "transparent_styled",
				open:function(){
					$(window).scroll(function(){
						$("#" + id).parent().dialog({ position: { my: "center", at: "center", of: window} }); 
					});
				},
				close:function(){
					$(window).scroll().unbind();
					$(this).dialog("close");
				}
			});
			
			if( $("#" + target).attr("data-startdate") != undefined && $("#" + target).attr("data-startdate") != "") {
				start_date = $("#" + target).attr("data-startdate");
			}else{
				start_date = todayDate_full;
			}
			
			if($(this).val() != "") start_date = $(this).val();
			setStartDate($("#" + id), start_date);
			setFullDate($("#" + id));
		});
		
		$("#"+id +"_year").bind({'mousewheel': function (event, delta) {
			event.preventDefault();
			targetScroll($(this), delta);
		}});
		
		$("#"+id +"_month").bind({'mousewheel': function (event, delta) {
			event.preventDefault();
			targetScroll($(this), delta);
		}});
		
		$("#"+id +"_date").bind({'mousewheel': function (event, delta) {
			event.preventDefault();
			targetScroll($(this), delta);
		}});
		
		if (navigator.userAgent.match(/Android/i)
            || navigator.userAgent.match(/webOS/i)
            || navigator.userAgent.match(/iPhone/i)
            || navigator.userAgent.match(/iPad/i)
            || navigator.userAgent.match(/iPod/i)
            || navigator.userAgent.match(/BlackBerry/i)
            || navigator.userAgent.match(/Windows Phone/i)
            || navigator.userAgent.match(/Opera Mini/i)
            || navigator.userAgent.match(/IEMobile/i)
            ) {
			//Mobile
			$("#"+id+" .cd_ui_upbtn").click(function(){
				var btnTarget = $(this).parent().find("ul");
				targetScroll($(btnTarget),-1);
			});
			$("#"+id+" .cd_ui_downbtn").click(function(){
				var btnTarget = $(this).parent().find("ul");
				targetScroll($(btnTarget),1);
			});
			
			var touch_scrollTop;
			var touch_start_y;
			var touch_end_y
			$("#"+id +"_year, #"+id +"_month, #"+id +"_date").bind("mousedown touchstart",function(event){
				event.preventDefault(); event.stopImmediatePropagation();
				var touch=event.originalEvent.touches[0] || event.originalEvent.changedTouches[0];
				touch_start_y=touch.pageY;
				touch_scrollTop = $(this).scrollTop();
			}).bind("mousemove touchmove",function(event){
				event.preventDefault(); event.stopImmediatePropagation();
				var touch=event.originalEvent.touches[0] || event.originalEvent.changedTouches[0]
				touch_end_y=touch.pageY;
				$(this).scrollTop(touch_scrollTop+(touch_start_y-touch_end_y)*2);
			}).bind("mouseleave touchend",function(event){
				$(this).stop(true).animate({ scrollTop: Math.round($(this).scrollTop()/scrollYSize)*scrollYSize },{
					duration: 300,
					easing: "easeOutBounce",
					complete: function() {
						ingNum=0;
						$(this).scrollTop(Math.round($(this).scrollTop()/scrollYSize)*scrollYSize);
						var fullDate = setFullDate($(this).parents(".cd_ui_customDatePicker")).split(date_split_format);
						if($(this).is(".cd_ui_year") || $(this).is(".cd_ui_month")){
							checkDateMaxNum($(this).parents(".cd_ui_customDatePicker"), fullDate[0], fullDate[1], fullDate[2]);
						}
					}
				});
			});
		}else{
			//PC
			$("#"+id+" .cd_ui_upbtn").live({
				mousedown : function() {
					var btnTarget = $(this).parent().find("ul");
					targetScroll($(btnTarget),-1);
					timerID=setInterval(function(){
						targetScroll($(btnTarget),-1);
					},240);
				},
				mouseup : function() {
					ingNum=0;
					clearInterval(timerID);
				},
				mouseout : function() {
					ingNum=0;
					clearInterval(timerID);
				}
			});
			$("#"+id+" .cd_ui_downbtn").live({
				mousedown : function() {
					var btnTarget = $(this).parent().find("ul");
					targetScroll($(btnTarget),1);
					timerID=setInterval(function(){
						targetScroll($(btnTarget),1);
					},240);
				},
				mouseup : function() {
					ingNum=0;
					clearInterval(timerID);
				},
				mouseout : function() {
					ingNum=0;
					clearInterval(timerID);
				}
			});
		}

		$("#"+id+" .confirmbtn").click(function(){
			$("#" + target).val(setFullDate($("#" + id)));
			$("#" + id).parent().dialog("close");
		});
		
		$("#"+id+" .cancelbtn").click(function(){
			$("#" + id).parent().dialog("close");
		});
	});
}
//스크롤 함수
function targetScroll(target, delta){
	ingNum ++;
	var sTop = $(target).scrollTop();
	var cap = sTop + ((delta * scrollYSize) * -(ingNum));
	$(target).stop(true).animate({ scrollTop: cap },{
		duration: 300,
		easing: "easeOutBounce",
		complete: function() {
			$(this).scrollTop(Math.round($(this).scrollTop()/scrollYSize)*scrollYSize);
			ingNum=0;
			var fullDate = setFullDate($(target).parents(".cd_ui_customDatePicker")).split(date_split_format);
			if($(target).is(".cd_ui_year") || $(target).is(".cd_ui_month")){
				checkDateMaxNum($(target).parents(".cd_ui_customDatePicker"), fullDate[0], fullDate[1], fullDate[2]);
			}
		}
	});
}

//초기 호출 시 날짜 적용
function setStartDate(pTarget, start_date){
	var fullDate = start_date.split(date_split_format);
	var y_gab = max_year - min_year;
	
	var y_num = fullDate[0]-min_year;
	var m_num = fullDate[1]-1;
	var d_num = fullDate[2]-1;

	$(pTarget).find(".cd_ui_year").scrollTop(y_num*scrollYSize);
	$(pTarget).find(".cd_ui_month").scrollTop(m_num*scrollYSize);
	$(pTarget).find(".cd_ui_date").scrollTop(d_num*scrollYSize);
}

//사용자 선택 날짜 활성화
function setFullDate(pTarget){
	var y_num = Math.round($(pTarget).find(".cd_ui_year").scrollTop()/scrollYSize);
	var m_num = Math.round($(pTarget).find(".cd_ui_month").scrollTop()/scrollYSize);
	var d_num = Math.round($(pTarget).find(".cd_ui_date").scrollTop()/scrollYSize);
	
	$(pTarget).find(".cd_ui_year li.active").removeClass("active");
	$(pTarget).find(".cd_ui_month li.active").removeClass("active");
	$(pTarget).find(".cd_ui_date li.active").removeClass("active");
	
	$(pTarget).find(".cd_ui_year li").eq(y_num+1).addClass("active");
	$(pTarget).find(".cd_ui_month li").eq(m_num+1).addClass("active");
	$(pTarget).find(".cd_ui_date li").eq(d_num+1).addClass("active");

	var year = $(pTarget).find(".cd_ui_year li").eq(y_num+1).text();
	var month = (((m_num+1)+100)+"").substring(1,3);
	var date = (((d_num+1)+100)+"").substring(1,3);
	var fullDate = year+date_split_format+month+date_split_format+date;

	return fullDate;
}

//월별 마지막 일 체크
function checkDateMaxNum(pTarget, year, month, date) {
	var maxDateNum = new Date(new Date(year, month, 1)-86400000).getDate();

	$(pTarget).find(".cd_ui_date li").show();
	if(maxDateNum == 31) return;
	for(var i=maxDateNum+1;i <= 31;i++){
		$(pTarget).find(".cd_ui_date li").eq(i).css("display","none");
		$(pTarget).find(".cd_ui_date li").eq(i).removeClass("active");
	}
	if(date>maxDateNum){
		$(pTarget).find(".cd_ui_date li").eq(maxDateNum).addClass("active");
	}
}

//사용 안함
function setPosition(target, id){
	var tInput  = $("#" + target).offset();
	var tHeight = $("#" + target).outerHeight();							
	var calHeight 	= $("#" + id).outerHeight();				
	if( tInput != null){
		$("#" + id).css({"top":tInput.top+tHeight , "left":tInput.left});
	}
}

//DISPLAY 생성
function makeCustomDate(id){
	var today;
	var cal_html = "";				 
	cal_html += "<div class='cd_ui_header'><div class='midtxt'>년</div><div class='midtxt'>월</div><div class='midtxt'>일</div></div>";
	cal_html += "<div class='cd_ui_body'><div class='cd_ui_area'><a class='button grey-gradient cd_ui_upbtn' >▲</a><ul id='"+ id +"_year' class='cd_ui_year'><li class='blank'>&nbsp;</li>";
	for(var i=min_year;i<=max_year;i++){
		cal_html += "<li class='be'>" + i + "</li>"; 
	}
	cal_html += "<li class='blank'>&nbsp;</li></ul><a class='button grey-gradient cd_ui_downbtn' >▼</a></div>";
	cal_html += "<div class='cd_ui_area'><a class='button grey-gradient cd_ui_upbtn' >▲</a><ul id='"+ id +"_month' class='cd_ui_month'><li class='blank'>&nbsp;</li>";
	for(i=1;i<=12;i++){
		cal_html += "<li class='be'>" + i + "</li>"; 
	}
	cal_html += "<li class='blank'>&nbsp;</li></ul><a class='button grey-gradient cd_ui_downbtn' >▼</a></div>";
	cal_html += "<div class='cd_ui_area'><a class='button grey-gradient cd_ui_upbtn' >▲</a><ul id='"+ id +"_date' class='cd_ui_date'><li class='blank'>&nbsp;</li>";
	for(i=1;i<=31;i++){
		cal_html += "<li class='be'>" + i + "</li>"; 
	}
	cal_html += "<li class='blank'>&nbsp;</li></ul><a class='button grey-gradient cd_ui_downbtn' >▼</a></div></div>";
	cal_html += "<div class='cd_ui_footer'><a  class='button anthracite-gradient glossy confirmbtn'>적용</a>&nbsp;&nbsp;<a  class='button grey-gradient glossy cancelbtn'>닫기</a></div>";
	
	return cal_html;
}








	
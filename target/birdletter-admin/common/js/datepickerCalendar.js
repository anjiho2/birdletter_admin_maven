// Calendar
function getContextPath(){
	var offset=location.href.indexOf(location.host)+location.host.length;
	var ctxPath=location.href.substring(offset,location.href.indexOf('/',offset+1));
	return ctxPath;
}

function fn_calendarFromToJob(sDate,eDate){

	if(sDate != undefined){
		$("#date1Bf").val($.datepicker.formatDate($.datepicker.ATOM, sDate));
	}
	
	if(eDate != undefined){
		$("#date1Af").val($.datepicker.formatDate($.datepicker.ATOM, eDate));
	}

	$(".calendar").datepicker({
		monthNamesShort: ['1월','2월','3월','4월','5월','6월','7월','8월','9월','10월','11월','12월'],
		dayNamesMin: ['일', '월', '화', '수', '목', '금', '토'],
		dateFormat: 'yy-mm-dd',  
		buttonImageOnly: true,
  	    buttonImage: getContextPath()+'/img/common/ico_day.png',
		buttonText: '달 력',
		prevText: '이전달',
		nextText: '다음달',
		showOn: 'both',
		changeMonth: true,
		changeYear: true
	});
	$('img.ui-datepicker-trigger').attr('align', 'absmiddle'); 
	document.getElementById("ui-datepicker-div").style.display = "none";
}

function fn_calendarFromToReq(sDate,eDate){
	
	if(sDate != undefined){
		$("#date2Bf").val($.datepicker.formatDate($.datepicker.ATOM, sDate));
	}
	if(eDate != undefined){
		$("#date2Af").val($.datepicker.formatDate($.datepicker.ATOM, eDate));
	}
	$(".calendar").datepicker({
		monthNamesShort: ['1월','2월','3월','4월','5월','6월','7월','8월','9월','10월','11월','12월'],
		dayNamesMin: ['일', '월', '화', '수', '목', '금', '토'],
		dateFormat: 'yy-mm-dd',  
		buttonImageOnly: true,
		buttonImage: getContextPath()+'/img/common/ico_day.png',
		buttonText: '달 력',
		prevText: '이전달',
		nextText: '다음달',
		showOn: 'both',
		changeMonth: true,
		changeYear: true
	});
	$('img.ui-datepicker-trigger').attr('align', 'absmiddle'); 
	document.getElementById("ui-datepicker-div").style.display = "none";
}

function fn_calendarOne(cDate){
	if(cDate != undefined){
		$("#date1Bf").val($.datepicker.formatDate($.datepicker.ATOM, cDate));
	}
	$("#date1Bf").datepicker({
		monthNamesShort: ['1월','2월','3월','4월','5월','6월','7월','8월','9월','10월','11월','12월'],
		dayNamesMin: ['일', '월', '화', '수', '목', '금', '토'],
		dateFormat: 'yy-mm-dd',  
		buttonImageOnly: true,
  	    buttonImage: getContextPath()+'/img/common//ico_day.png',
		buttonText: '달 력',
		prevText: '이전달',
		nextText: '다음달',
		showOn: 'both',
		changeMonth: true,
		changeYear: true
	});
	$('img.ui-datepicker-trigger').attr('align', 'absmiddle'); 
	document.getElementById("ui-datepicker-div").style.display = "none";
}



function gfn_dateModifyByStringDate(targetObj,stringDate){
	if(targetObj == undefined){
		alert("targetId 미입력");
		return;
	}
	if(stringDate == undefined){
		alert("stringDate 미입력");
		return;
	}
	
	targetObj.val($.datepicker.formatDate($.datepicker.ATOM, gfn_ConvertDateTypeStoD(stringDate)));	
}

function gfn_dateModifyBydate(targetObj,_date){
	if(targetObj == undefined){
		alert("targetObj 미입력");
		return;
	}
	
	if(_date == undefined){
		alert("date 미입력");
		return;
	}
	targetObj.val($.datepicker.formatDate($.datepicker.ATOM, _date));	
}




//--------------------------------------------------
// Convert String date to Date date
//--------------------------------------------------
function gfn_ConvertDateTypeStoD(stringDate){
	if(stringDate.length < 0){
		alert("미입력");
		return;
	}
	stringDate = gfn_replaceStr(stringDate,'-','');
	
	if(stringDate.length > 8){
		alert("변환오류");
		return;
	}
	
	var year = stringDate.substring(0,4);
	var month = stringDate.substring(4,6);
	var day = stringDate.substring(6,8);
	
	return new Date(year,month-1,day);
}

//--------------------------------------------------
// from to date valide
//--------------------------------------------------
function gfn_validFromToDate(sDate,eDate){
	if(sDate.length < 0){
		alert("시작일오류");
		return false;
	}
	if(eDate.length < 0){
		alert("종료일오류");
		return false;
	}
	
	if(sDate > eDate){
		alert("시작일이 종료일보다 클 수 없습니다.");
		return false;
	}
	return true;
}
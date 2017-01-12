// 공통 스크립트 파일
var tot = "0";
var webRoot = "";

//--------------------------------------------------
// 빈 결과값 메인 리스트  처리
//--------------------------------------------------
function gfn_emptyView(gubun,str){
	if (gubun == "H") {
		emptys.innerHTML = "<td colspan='30'></td>";
  		emptys.style.height = "0"; 
  		emptys.style.visibility = "hidden";
		emptys.style.display = "none";
  	 }else{
  		emptys.style.display = "";
  		emptys.style.visibility = "visible";
  		emptys.style.height = "100px"; 
  		emptys.innerHTML = "<b>"+str+"</b>";
  	}
}

// 파일 패스 제거후 파일명 추출
	function fn_clearFilePath(val){
		var tmpStr = val;
		
		var cnt = 0;
		while(true){
			cnt = tmpStr.indexOf("/");
			if(cnt == -1) break;
			tmpStr = tmpStr.substring(cnt+1);
		}
		while(true){
			cnt = tmpStr.indexOf("\\");
			if(cnt == -1) break;
			tmpStr = tmpStr.substring(cnt+1);
		}
		
		return tmpStr;
	}

//--------------------------------------------------
//빈 결과값 메인 리스트  처리
//--------------------------------------------------
function gfn_emptyView2(gubun,str){
	if (gubun == "H") {
		emptys.innerHTML = "<td colspan='30'></td>";
		emptys.style.height = "0"; 
		emptys.style.visibility = "hidden";
		emptys.style.display = "none";
	 }else{
		emptys.style.display = "";
		emptys.style.visibility = "visible";
		emptys.style.height = "125px"; 
		emptys.innerHTML = "<b>"+str+"!</b>";
	}
}

//--------------------------------------------------
// 빈 결과값 메인 리스트  처리 (한페이지에 두개의 리스트가 같이 나올경우 )
//--------------------------------------------------
function gfn_emptyView_new(gubun,str){
	if (gubun == "H") {
		emptys.innerHTML = "<td colspan='30'></td>";
  		emptys.style.height = "0"; 
  		emptys.style.visibility = "hidden";
		emptys.style.display = "none";
  	} else {
  		emptys.style.display = "";
  		emptys.style.visibility = "visible";
  		emptys.style.height = "150px"; 
  		emptys.innerHTML = "<b>"+str+"!</b>";
  	}
}

function gfn_emptyView_alarm(gubun,str){
	if (gubun == "H") {
		emptys_alarm.innerHTML = "<td colspan='30'></td>";
  		emptys_alarm.style.height = "0"; 
  		emptys_alarm.style.visibility = "hidden";
		emptys_alarm.style.display = "none";
  	} else {
  		emptys_alarm.style.display = "";
  		emptys_alarm.style.visibility = "visible";
  		emptys_alarm.style.height = "200px"; 
  		emptys_alarm.innerHTML = "<b>"+str+"!</b>";
  	}
}

function gfn_emptyView_kanje(gubun,str){
	if (gubun == "H") {
		emptys_kanje.innerHTML = "<td colspan='30'></td>";
  		emptys_kanje.style.height = "0"; 
  		emptys_kanje.style.visibility = "hidden";
		emptys_kanje.style.display = "none";
  	} else {
  		emptys_kanje.style.display = "";
  		emptys_kanje.style.visibility = "visible";
  		emptys_kanje.style.height = "150px"; 
  		emptys_kanje.innerHTML = "<b>"+str+"!</b>";
  	}
}

function gfn_emptyView_set(gubun,str){
	if (gubun == "H") {
		emptys_set.innerHTML = "<td colspan='30'></td>";
  		emptys_set.style.height = "0"; 
  		emptys_set.style.visibility = "hidden";
		emptys_set.style.display = "none";
  	} else {
  		emptys_set.style.display = "";
  		emptys_set.style.visibility = "visible";
  		emptys_set.style.height = "120px"; 
  		emptys_set.innerHTML = "<b>"+str+"!</b>";
  	}
}

function gfn_emptyView_name(gubun,str){
	if (gubun == "H") {
		emptys_name.innerHTML = "<td colspan='30'></td>";
  		emptys_name.style.height = "0"; 
  		emptys_name.style.visibility = "hidden";
		emptys_name.style.display = "none";
  	} else {
  		emptys_name.style.display = "";
  		emptys_name.style.visibility = "visible";
  		emptys_name.style.height = "480px"; 
  		emptys_name.innerHTML = "<b>"+str+"!</b>";
  	}
}


function gfn_setTot(str){
	tot = str;
}
//--------------------------------------------------
// 빈 결과값 서브 리스트  처리
//--------------------------------------------------
function gfn_emptySubView(gubun,str){
	if (gubun == "H") {
		emptysSub.innerHTML = "<td colspan='13'></td>";
  		emptysSub.style.visibility = "hidden";
  		emptysSub.style.height = "0"; 
  	} else {
  		emptysSub.style.visibility = "visible";
  		emptysSub.innerHTML = "<b>"+str+"!</b>";
  		emptysSub.style.height = "80"; 
  	}
}

//--------------------------------------------------
//문자열 자르기 - 시작과 끝 지정
//--------------------------------------------------
function gfn_substr(str, start, end) {
	if (str != "") {
		substr = str.substring(start, end);
	}
	return substr;
}
//--------------------------------------------------
// 구분자로 문장 자르기 - 단일문장
//--------------------------------------------------
function gfn_split(str,delim){
	var array = new Array();
	var len = str.lastIndexOf(delim);
	array[0] = str.substring(0,len);
	array[1] = str.substring(len+1,str.length);	
	return array;
}

//--------------------------------------------------
// 구분자로 문장 자르기 - 복수문장
//--------------------------------------------------
function gfn_csplit(str,delim){
	var array = new Array();
	if (str == "") return "";
	
	array = str.split(delim);
	return array;
}

//--------------------------------------------------
// 파라미터 자르기
//--------------------------------------------------
function gfn_strcut(str, len){
	if (str == ""){
		return "";
	} else {
		return str.substring(len);
	}
}

//--------------------------------------------------
//  문자치환
//--------------------------------------------------
function gfn_replaceStr(str,fromStr,toStr ){
    var idx;
    var dst="";
    if (str=="") return str;
    if (!str.substring) return str;
    while((idx = str.indexOf(fromStr))>=0){
        dst += str.substring(0,idx) + toStr;
        str  = str.substring(idx+fromStr.length);
    }
    return dst + str;
}

//--------------------------------------------------
//  문자 존재 여부 체크
//--------------------------------------------------
function gfn_CheckStr(str,fromStr){
    var idx;
    if (str=="" || str == null) return true;
    	idx = str.indexOf(fromStr);
    if (idx == "-1"){
    	return true;
    } else{
    	return false;
    }
}

//--------------------------------------------------
// 로딩 이미지 처리
//--------------------------------------------------
function gfn_Loading(str){
	if (str == "Y"){
		try {
			loading.style.visibility = "visible";
			loading.style.width="230";
			loading.style.height="66";
		} catch (e){
			parent.loading.style.visibility = "visible";
			parent.loading.style.width="230";
			parent.loading.style.height="66";
		}
	} else {
		try {	
			loading.style.visibility = "hidden";
			loading.style.width="0";
			loading.style.height="0";
		} catch (e){
			parent.loading.style.visibility = "hidden";
			parent.loading.style.width="0";
			parent.loading.style.height="0";
		}		
	}
}

//--------------------------------------------------
// 팝업 처리
//--------------------------------------------------
function gfn_winPop(xs,ys,urls,param){ 
 var targeturl = urls + param;
 var winX = 0;
 var winY = 0;
    if (screen.width < 1025){
        winX=0;
        winY=0;
    } else {
        winX=(screen.width)?(screen.width-xs)/2:100;
        winY=(screen.height)?(screen.height-ys)/2:100;
    }
 var features = 'width='+xs+',height='+ys+',left=' + winX + ',top=' + winY + ',location=no,toolbar=no,scrollbars=no,resizable=yes,status=yes';
 var winda = window.open(targeturl,'winc',features);
 winda.focus();
}

function gfn_winPops(xs,ys,urls,param, sid){ 
	var targeturl = urls + param;
	var winX = 0;
	var winY = 0;
    if (screen.width < 1025){
        winX=0;
        winY=0;
    } else {
        winX=(screen.width)?(screen.width-xs)/2:100;
        winY=(screen.height)?(screen.height-ys)/2:100;
    }
	var features = 'width='+xs+',height='+ys+',left=' + winX + ',top=' + winY + ',location=no,toolbar=no,scrollbars=auto,resizable=yes,status=yes';
 	//var winda = window.open(targeturl,sid,features);
 	window.open(targeturl,sid,features);
 	//winda.focus();
}

function gfn_winScrollPops(xs,ys,urls,param, sid){ 
	var targeturl = urls + param;
	var winX = 0;
	var winY = 0;
    if (screen.width < 1025){
        winX=0;
        winY=0;
    } else {
        winX=(screen.width)?(screen.width-xs)/2:100;
        winY=(screen.height)?(screen.height-ys)/2:100;
    }
	var features = 'width='+xs+',height='+ys+',left=' + winX + ',top=' + winY + ',location=no,toolbar=no,scrollbars=yes,resizable=yes,status=yes';
	//var winda = window.open(targeturl,sid,features);
 	window.open(targeturl,sid,features);
 	//winda.focus();
}
 
function gfn_winResizeNoPops(xs,ys,urls,param, sid){ 
	var targeturl = urls + param;
	var winX = 0;
	var winY = 0;
    if (screen.width < 1025){
        winX=0;
        winY=0;
    } else {
        winX=(screen.width)?(screen.width-xs)/2:100;
        winY=(screen.height)?(screen.height-ys)/2:100;
    }
	var features = 'width='+xs+',height='+ys+',left=' + winX + ',top=' + winY + ',location=no,toolbar=no,scrollbars=no,resizable=no,status=yes';
 	var winda = window.open(targeturl,sid,features);
 	winda.focus();
}
 

//--------------------------------------------------
//  문자 존재 여부 체크  - str의 빈값 제외
//--------------------------------------------------
function gfn_CheckNullStr(str,fromStr){
    var idx = "-1";
    if (str != "") {
	    idx = str.indexOf(fromStr);
    }
    if (idx == "-1"){
    	return false;
    } else{
    	return true;
    }
}

//--------------------------------------------------
//  null 을 빈값으로처리
//--------------------------------------------------
function gfn_isnull(str){
	if (str == null){
		return "";
	} else {
		return str;
	}
}

//--------------------------------------------------
//null 을 문자 치환
//--------------------------------------------------
function gfn_isnullvalue(str, fromStr){
	if (str == null || str == ""){
		return fromStr;
	} else {
		return str;
	}
}

//--------------------------------------------------
//  숫자 여부 체크 
//--------------------------------------------------
function gfn_isnum(str){
	if (str == ""){
		return true;
	} else {
		return !isNaN(Number(str));
	}
}

//--------------------------------------------------
//  좌우측여백삭제
//--------------------------------------------------
function gfn_trim(str){ 
    return gfn_ltrim(gfn_rtrim(str)); 
}

//--------------------------------------------------
//  좌측여백삭제
//--------------------------------------------------
function gfn_ltrim(str){ 
    var s = new String(str); 
    if (s.substr(0,1) == " "){ 
        return gfn_ltrim(s.substr(1)); 
    } else { 
        return s; 
    }    
}

//--------------------------------------------------
//  우측여백삭제
//--------------------------------------------------
function gfn_rtrim(str){ 
    var s = new String(str); 
    if(s.substr(s.length-1,1) == " "){ 
        return gfn_rtrim(s.substring(0, s.length-1)); 
    } else { 
       return s; 
    }    
}

//--------------------------------------------------
// 소수점 이하 삭제
//--------------------------------------------------
function roundMarks(pval) {
	var rval = parseInt(pval);
    return rval;
}

//--------------------------------------------------
// 팝업 윈도우 닫기
//--------------------------------------------------
function gfn_close(){
	self.close();
}

//--------------------------------------------------
//날자 포맷 변경처리
//--------------------------------------------------
function gfn_dateFormat(str,len,gu){
	var rdate = ""; 
	if (len == "8"){
		rdate = str.substring(0,4)+"."+str.substring(4,6)+"."+str.substring(6,8);
	} else if (len == "10"){
		if(gu == "C"){
			rdate = str.substring(0, 4) + "년" + str.substring(5, 7) + "월" + str.substring(8, 10) + "일";
		}
		else{
			rdate = str.substring(0,10);
		}
	} else if (len == "14"){
		if (gu == "C"){
			rdate  = str.substring(0,4)+"."+str.substring(4,6)+"."+str.substring(6,8);
		}
		else if (gu == "F"){
			rdate  = str.substring(0,4)+"-"+str.substring(4,6)+"-"+str.substring(6,8);
		}
		else {
			rdate  = str.substring(0,4)+"."+str.substring(4,6)+"."+str.substring(6,8);
			rdate += " "+str.substring(8,10)+":"+str.substring(10,12)+":"+str.substring(12,14);		
		}
	} else if (len == "18"){
		rdate  = str.substring(0,4)+str.substring(5,7)+str.substring(8,10);
		rdate += str.substring(11,13)+str.substring(14,15)+str.substring(17,18);
	} else {
		rdate  = str.substring(0,4)+"-"+str.substring(5,7)+"-"+str.substring(8,10)+" ";
		rdate += str.substring(11,13)+":"+str.substring(14,16)+":"+str.substring(17,19);
	}	
	return rdate;
}


//--------------------------------------------------
// 동일 문자 체크   : 길이만큼의 동일문자 존제시 true 없을시 false
//--------------------------------------------------
function gfn_strCheck(str, len){
	var rdate = false;
	if(str.length < len){
		return str;
	}
	else{
		for(var i = 0; i < str.length; i++){
			var astr = str.substring(i, i + len);
			var bstr = str.substring(i, i+1);
			var cstr = "";
			for(var j = 0; j < len; j++){
				cstr = cstr + bstr;
			}
			
			if(cstr == astr){
				rdate = true;
				return rdate;
				break;
			}
		}
		rdate = false;
		return rdate;
	}
}


//검색 프로그레스 호출 및 숨기기
function loadSearch(topPadding) 
{
  if (document.getElementById) 
  {  
    document.getElementById('progress').style.left = document.body.clientWidth/2 - 150 + "px";
    //document.getElementById('progress').style.top = document.body.scrollTop + (document.body.clientHeight/2) + "px";
    document.getElementById('progress').style.top = document.body.scrollTop + topPadding + "px";  //320
    document.getElementById('progress').style.visibility = 'visible';
  } 
  else 
  {
    if (document.layers) 
    {  
      document.progress.left = document.body.clientWidth/2 - 150;
      document.progress.visibility = 'visible';
    } 
    else 
    {  
      document.all.progress.style.left = document.body.clientWidth/2 - 150;
      document.all.progress.style.top = document.body.scrollTop + (document.body.clientHeight/2);
      document.all.progress.style.visibility = 'visible';
    }
  }
}


//검색 프로그레스 호출 및 숨기기
function loadSearch2() 
{
  if (document.getElementById) 
  {  
    document.getElementById('progress').style.left = document.body.clientWidth/2 - 150 + "px";
    document.getElementById('progress').style.top = document.body.scrollTop + (document.body.clientHeight/2) + "px";
    document.getElementById('progress').style.visibility = 'visible';
  } 
  else 
  {
    if (document.layers) 
    {  
      document.progress.left = document.body.clientWidth/2 - 150;
      document.progress.visibility = 'visible';
    } 
    else 
    {  
      document.all.progress.style.left = document.body.clientWidth/2 - 150;
      document.all.progress.style.top = document.body.scrollTop + (document.body.clientHeight/2);
      document.all.progress.style.visibility = 'visible';
    }
  }
}

function HideSearch() 
{
  if (document.getElementById) 
  {  
    document.getElementById('progress').style.visibility = 'hidden';
  } 
  else 
  {
    if (document.layers) 
    {  
      document.progress.visibility = 'hidden';
    } 
    else 
    {  
      document.all.progress.style.visibility = 'hidden';
    }
  }
}

	// 전화번호 구분별 입력 
   	function fnSetPhoneNo(obj1, obj2, obj3, phoneNo) {
		if(phoneNo == null){
     		return;
    	}
    	if(phoneNo.length == 0){
     		return;
    	}
    
    	var chkHandPhone = phoneNo.substring(0,2);
    
    	var phoneNo1 = "";
    	var phoneNo2 = "";
    	var phoneNo3 = "";
    
    	if(chkHandPhone == "02"){
     		phoneNo1 = chkHandPhone;
     		phoneNo2 = phoneNo.substring(2, phoneNo.length-4);
     		phoneNo3 = phoneNo.substring(phoneNo.length-4);
    	}else{
     		phoneNo1 = phoneNo.substring(0,3);
    	 	phoneNo2 = phoneNo.substring(3, phoneNo.length-4);
     		phoneNo3 = phoneNo.substring(phoneNo.length-4);
    	}
    
    	dwr.util.setValue(obj1, phoneNo1);
    	dwr.util.setValue(obj2, phoneNo2);
    	dwr.util.setValue(obj3, phoneNo3);
   	}
   
   	//사업자 번호 자르기
   	function fn_business_cut(obj1, obj2, obj3, business_no){
   		if(business_no == null){
     		return;
    	}
   		if(business_no.length == 0){
     		return;
    	}
    	
    	var business_no1 = business_no.substring(0,3);
    	var business_no2 = business_no.substring(3,5);
    	var business_no3 = business_no.substring(5,10);
    	
    	dwr.util.setValue(obj1, business_no1);
    	dwr.util.setValue(obj2, business_no2);
    	dwr.util.setValue(obj3, business_no3);
   	}
   	
   	//이메일 자르기
   	function fn_mail_cut(obj1, obj2, email){
   		if(email == null){
     		return;
    	}
    	
   		if(email.length == 0){
     		return;
    	}
    	
    	var mail = email.split("@");
    	
    	dwr.util.setValue(obj1, mail[0]);
    	dwr.util.setValue(obj2, mail[1]);
   	}
   	
   	//전화번호 태그 붙이기
   	function fn_tel_tag(tel_no){
   		if(tel_no == null){
     		return;
    	}
   		if(tel_no.length == 0){
     		return;
    	}

    	var chkHandPhone = tel_no.substring(0,2);
    
    	var phoneNo1 = "";
    	var phoneNo2 = "";
    	var phoneNo3 = "";
    
    	if(chkHandPhone == "02"){
     		phoneNo1 = chkHandPhone;
     		phoneNo2 = tel_no.substring(2, tel_no.length-4);
     		phoneNo3 = tel_no.substring(tel_no.length-4);
    	}else{
     		phoneNo1 = tel_no.substring(0,3);
    	 	phoneNo2 = tel_no.substring(3, tel_no.length-4);
     		phoneNo3 = tel_no.substring(tel_no.length-4);
    	}
    	
    	var rtnTel = phoneNo1 + "-" + phoneNo2 + "-" + phoneNo3;
    	
    	return rtnTel;
   	}
   	
   	//주민번호 태그 붙이기
   	function fn_res_tag(res_no){
   		if(res_no == null){
   			return;
   		}
   		if(res_no.length == 0){
   			return;
   		}
   		var res1 = res_no.substring(0,6);
   		var res2 = "*******";
   		var rtnRes = res1 + " - " + res2;
   		
   		return rtnRes;
   	}
   	
   	//사업자번호 태그 붙이기
   	function fn_business_tag(business_no){
   		if(business_no == null){
     		return;
    	}
   		if(business_no.length == 0){
     		return;
    	}
    	
    	var business_no1 = business_no.substring(0,3);
    	var business_no2 = business_no.substring(3,5);
    	var business_no3 = business_no.substring(5,10);
    	
    	var rtnBusiness = business_no1 + "-" + business_no2 + "-" + business_no3;
    	
    	return rtnBusiness;
    	
   	}
			
	// 파일 확장자 체크
	function fileCheck(file_gubun, file_nm){
		// 허용 가능 확장자 선택
		if(file_gubun == "service" && /.*\.(gif)|(jpeg)|(jpg)|(png)$/.test(file_nm.toLowerCase())){
			return false;
		}
		if(file_gubun == "ctn" && /.*\.(text)|(txt)$/.test(file_nm.toLowerCase()) ){
			return false;
		}		
		if(file_gubun == "bbs" && /.*\.(text)|(txt)$/.test(file_nm.toLowerCase()) ){
			return false;
		}
		if(file_gubun == "frmwr" && /.*\.(text)|(txt)|(exe)|(bin)|(zip)$/.test(file_nm.toLowerCase()) ){
			return false;
		}
		if(file_gubun == "test" && /.*\.(xls)$/.test(file_nm.toLowerCase()) ){
			return false;
		}
		return true;
	}
	
	function checkv4 (obj) {
		var IPvalue = obj.value;
		errorString = "";
		theName = "IPaddress";
		
		var ipPattern = /^(\d{1,3})\.(\d{1,3})\.(\d{1,3})\.(\d{1,3})$/;
		var ipArray = IPvalue.match(ipPattern);
		
		if (IPvalue == "0.0.0.0")
			errorString = errorString + theName + ': '+IPvalue+'는 특수IP로 사용할 수 없습니다.';
		else if (IPvalue == "255.255.255.255")
			errorString = errorString + theName + ': '+IPvalue+'는 특수IP로 사용할 수 없습니다.';
		
		if (ipArray == null)
			errorString = errorString + theName + ': '+IPvalue+'는 올바른 IP가 아닙니다.';
		else {
			for (var i = 0; i < 4; i++) {
				thisSegment = ipArray[i];
				if (thisSegment > 255) {
					errorString = errorString + theName + ': '+IPvalue+'는 올바른 IP가 아닙니다.';
					i = 4;
				}
				if ((i == 0) && (thisSegment > 255)) {
					errorString = errorString + theName + ': '+IPvalue+'는 특수IP로 사용할 수 없습니다.';
					i = 4;
				}
			}
		}
		
		extensionLength = 3;
		if (errorString == ""){
			//alert ("That is a valid IP address.");
			return true;
		}else{
			alert (errorString);
			obj.select();
			return false;
		}
	}
	
	function checkv6 (elem){
		var v6Expression = /^(([A-Fa-f0-9]{1,4}:){7}[A-Fa-f0-9]{1,4})$|^([A-Fa-f0-9]{1,4}::([A-Fa-f0-9]{1,4}:){0,5}[A-Fa-f0-9]{1,4})$|^(([A-Fa-f0-9]{1,4}:){2}:([A-Fa-f0-9]{1,4}:){0,4}[A-Fa-f0-9]{1,4})$|^(([A-Fa-f0-9]{1,4}:){3}:([A-Fa-f0-9]{1,4}:){0,3}[A-Fa-f0-9]{1,4})$|^(([A-Fa-f0-9]{1,4}:){4}:([A-Fa-f0-9]{1,4}:){0,2}[A-Fa-f0-9]{1,4})$|^(([A-Fa-f0-9]{1,4}:){5}:([A-Fa-f0-9]{1,4}:){0,1}[A-Fa-f0-9]{1,4})$|^(([A-Fa-f0-9]{1,4}:){6}:[A-Fa-f0-9]{1,4})$/;
		if(elem.value.match(v6Expression)){
			return true;
		}else{
			alert("올바른 IP가 아닙니다. ");
			return false;
		}
	}

/*
' ------------------------------------------------------------------
' Function    : fc_chk_byte(aro_name)
' Description : 입력한 글자수를 체크
' Argument    : Object Name(글자수를 제한할 컨트롤)
' Return      : 
' ------------------------------------------------------------------
*/
function fc_chk_byte(aro_name,ari_max){
		 
   var ls_str     = aro_name.value; // 이벤트가 일어난 컨트롤의 value 값
   var li_str_len = ls_str.length;  // 전체길이
 
   // 변수초기화
   var li_max      = ari_max; // 제한할 글자수 크기
   var i           = 0;  // for문에 사용
   var li_byte     = 0;  // 한글일경우는 3 그밗에는 1을 더함
   var li_len      = 0;  // substring하기 위해서 사용
   var ls_one_char = ""; // 한글자씩 검사한다
   var ls_str2     = ""; // 글자수를 초과하면 제한할수 글자전까지만 보여준다.
 
   for(i=0; i< li_str_len; i++)
   {
	  // 한글자추출
	  ls_one_char = ls_str.charAt(i);
 
	  // 한글이면 3을 더한다.
	  if (escape(ls_one_char).length > 4)
	  {
		 li_byte = li_byte+3;
	  }
	  // 그외의 경우는 1을 더한다.
	  else
	  {
		 li_byte++;
	  }
 
	  // 전체 크기가 li_max를 넘지않으면
	  if(li_byte <= li_max)
	  {
		 li_len = i + 1;
	  }
   }
   
   // 전체길이를 초과하면
   if(li_byte > li_max){
	  alert( li_max + " 글자를 초과 입력할수 없습니다. \n 초과된 내용은 자동으로 삭제 됩니다. ");
	  ls_str2 = ls_str.substr(0, li_len);
	  aro_name.value = ls_str2;
	  return true;
   }else{
	   return false;
   }
   aro_name.focus();   
}


//문자의 갯수 리턴
function fn_charChk(pw){
	var cnt = 0;
	var pass_regx=/^[a-zA-Z]/;
	
	for (var i = 0; i < pw.length; i++) {
		if(pass_regx.test(pw.charAt(i))) {
			cnt = cnt + 1;
		}
	}
	return cnt;
}

//숫자의 갯수 리턴
function fn_numberChk(pw){
	var cnt = 0;
	var pass_regx=/^[0-9]/;
	
	for (var i = 0; i < pw.length; i++) {
		if(pass_regx.test(pw.charAt(i))) {
			cnt = cnt + 1;
		}
	}
	return cnt;
}
//특수 문자의 갯수리턴
function fn_speChk(pw){
	var cnt = 0;
	var pass_regx= /[^a-zA-Z0-9]/;
	
	for (var i = 0; i < pw.length; i++) {
		if(pass_regx.test(pw.charAt(i))) {
			cnt = cnt + 1;
		}
	}
	return cnt;
}

//비밀번호 체크
function fn_chkUserPw(pw){
	var n_char = fn_charChk(pw);
	var n_num  = fn_numberChk(pw);
	var n_spe  = fn_speChk(pw);
	var n_cnt = 0;
	if(n_char > 0){
		n_cnt = n_cnt+1;
	}
	if(n_num > 0){
		n_cnt = n_cnt+1;
	}
	if(n_spe > 0){
		n_cnt = n_cnt+1;
	}
	
	if(n_cnt < 2){
		alert("문자 종류(영문, 숫자, 특수문자) 중 최소 2종류를 포함하고 있어야 합니다.");
		return false;
	}
	if(pw.length < 10 ){
		alert("자리수가 최소 10자리 이상이어야 합니다.");
		return false;
	}
	return true;
}

//문자열 byte 체크
function gfn_getByteLength(str) {
	var len = 0;
  
	if(str == null){
		return 0;
	}
  
	for(var i = 0; i < str.length; i++){
		var c = escape(str.charAt(i));
		
		if(c.length == 1){
			len++;
		}
		else if(c.indexOf("%u") != -1){
			len += 2;
		}
		else if(c.indexOf("%") != -1){
			len += c.length / 3;
		}
	}
  
	return len;
} 

/**
 * BASE64 암호화
 * @param str
 */
function gfn_base64Encode(str){
	var keyStr = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/=";

	var output = "";
	var chr1, chr2, chr3;
	var enc1, enc2, enc3, enc4;
	var i =0;
 
	do{
		chr1 = str.charCodeAt(i++);
		chr2 = str.charCodeAt(i++);
		chr3 = str.charCodeAt(i++);
 
		enc1 = chr1 >> 2;
		enc2 = ((chr1 & 3) << 4) | (chr2 >> 4 );
		enc3 = ((chr2 & 15) << 2) | (chr3 >> 6 );
		enc4 = chr3 & 63;
 
		if(isNaN(chr2)){
			enc3 = enc4 =64;
		}else if(isNaN(chr3)){
			enc4 = 64;
		}
 
		output = output + keyStr.charAt(enc1) + keyStr.charAt(enc2) + keyStr.charAt(enc3) + keyStr.charAt(enc4);
	}while(i<str.length);
 
	return output;
}

/**
 * 문자를 길이만큼 끝자리를 *로 변환한다.
 * @param str : 변환할 문자열
 * @param len : 변환할 길이
 */
function gfn_stringTrans(str, len){
	if(str.length < 1){
		return str;
	}
	else{
		var strLen = str.length;
		var rtnStr = str.substring(0, (strLen - len));
		
		for(var i = strLen; i > (strLen - len); i--){
			rtnStr += "*";
		}
		
		return rtnStr;
	}
}

/**
 * 자바 스크립트 MAP 생성자
 * @returns {Map}
 */
Map = function(){
	this.map = new Object();
};  

/**
 * 자바 스크립트 MAP 함수
 * 사용법
 * var map = new Map();
 * map.put(key, value);
 * map.get(key);
 */
Map.prototype = {   
	put : function(key, value){   
    this.map[key] = value;
	},   
	get : function(key){   
	    return this.map[key];
	},
	containsKey : function(key){    
		return key in this.map;
	},
	containsValue : function(value){    
		for(var prop in this.map){
			if(this.map[prop] == value) return true;
		}
		return false;
	},
	isEmpty : function(key){    
		return (this.size() == 0);
	},
	clear : function(){   
		for(var prop in this.map){
			delete this.map[prop];
		}
	},
	remove : function(key){    
		delete this.map[key];
	},
	keys : function(){   
	    var keys = new Array();   
	    for(var prop in this.map){   
	        keys.push(prop);
	    }   
	    return keys;
	},
	values : function(){   
		var values = new Array();   
	    for(var prop in this.map){   
	    	values.push(this.map[prop]);
	    }   
	    return values;
	},
	size : function(){
		var count = 0;
		for (var prop in this.map) {
			count++;
		}
		return count;
	}
};

function fn_clear(val1, val2){
	document.getElementById(val1).value = "";
	document.getElementById(val2).value = "";
}

function fn_CheckStrLength(sourceID,cnt,str){ 
   //변수의 초기화
   var obj = document.getElementById(sourceID);
   var now_str = obj.value;                     //이벤트가 발생한 컨트롤의 value값 
   var now_len = obj.value.length;              //현재 value값의 글자 수 
   
   var max_len = cnt;                           //제한할 최대 글자 수 
   var i = 0;                                   //for문에서 사용할 변수 
   var cnt_byte = 0;                            //한글일 경우 2 그외에는 1바이트 수 저장 
   var chk_letter = "";                         //현재 한/영 체크할 letter를 저장 
   
   for (i=0; i<now_len; i++) 
   { 
       //1글자만 추출 
       chk_letter = now_str.charAt(i); 
 
       // 체크문자가 한글일 경우 2byte 그 외의 경우 1byte 증가 
       if (escape(chk_letter).length > 4) 
       { 
           //한글인 경우 2byte (UTF-8인 경우 3byte로...)
           cnt_byte += 2; 
       }else{ 
           //그외의 경우 1byte 증가 
           cnt_byte++; 
       } 
   } 
        
   // 만약 전체 크기가 제한 글자 수를 넘으면     
   if (cnt_byte > max_len) 
   { 
       alert(str + " " + max_len + "자 이내로 입력하세요."); 
       obj.focus();
       return true;
   }   
}

//숫자인지 체크
function fn_numberCheck(ids, str){
	var temp = document.getElementById(ids).value;
	
	if(isNaN(temp) == true){
		alert(str + "는 숫자만 입력하세요.");
		document.getElementById(ids).focus();
		return true;
	}
}

// 이메일 형식체크
function fn_isemail(str){
	var regExp = /[0-9a-zA-Z][_0-9a-zA-Z-]*@[_0-9a-zA-Z-]+(\.[_0-9a-zA-Z-]+){1,2}$/;
	
	if(!str.match(regExp)){
		alert("이메일 형식이 맞지 않습니다. 다시 입력해주세요");
		return true;
	}
}

// 비밀번호 정귝식
function fn_pwdcheck(str){
	var refExp = /([a-zA-Z0-9].*[\{\}\[\]\/?.,;:|\)*~`!^\-_+<>@\#$%&\\\=\(\'\"])|([\{\}\[\]\/?.,;:|\)*~`!^\-_+<>@\#$%&\\\=\(\'\"].*[a-zA-Z0-9])/;
	if(!str.match(refExp)){
		alert("패스워드는 영문자,숫자,특수문자 조합으로 8~12자리로 입력해주세요.");
		return true;
	}
}

/**
 * 특수문자 인코딩
 * @param str
 * @returnsd
 */
function gfn_htmlEntityEnc(str){
	if(str == "" || str == null){
		return str;
	}
	else{
		return str.replace("&", "&amp;").replace("#", "&#35;").replace("<", "&lt;").replace(">", "&gt;").replace(/"/g, "&quot;").replace('\\', "&#39;").replace('%', "&#37;").replace('(', "&#40;").replace(')', "&#41;").replace('+', "&#43;").replace('/', "&#47;").replace('.', "&#46;");
	}
}

/**
 * 특수문자 디코딩
 * @param str
 * @returns
 */
function gfn_htmlEntityDec(str){
	if(str == "" || str == null){
		return str;
	}
	else{
		return str.replace(/&amp;/gi, "&").replace(/&#35;/gi, "#").replace(/&lt;/gi, "<").replace(/&gt;/gi, ">").replace(/&quot;/gi, "'").replace(/&#39;/gi, '\\').replace(/&#37;/gi, '%').replace(/&#40;/gi, '(').replace(/&#41;/gi, ')').replace(/&#43;/gi, '+').replace(/&#47;/gi, '/').replace(/&#46;/gi, '.').replace(/&#59;/g, ";");
	}
}

/**
 * 숫자에 천단위 콤마 찍기
 * @param num
 * @returns
 */
function addThousandSeparatorCommas(num) {
	var str;
	if (num == null) num = "0";
	if ((num != "0")) {
		str = num.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ',');
	} else {
		str = "0";
	}
	return str;
}


function genderTrans(sex) {
	var str = "";
	if (sex != "") {
		if (sex == "MALE") {
			str = "남성";
		} else if (sex == "FEMALE") {
			str = "여성";
		}
	}
	return str;
}

/**
 * 체크박스 전체 체크및 삭제
 * @param tagName
 */
function checkall(checkBoxId) {
	if ($("#"+checkBoxId).prop("checked")) {
		$("input[type=checkbox]").prop("checked", true);
	} else {
		$("input[type=checkbox]").prop("checked", false);
	}
}

function leadingZeros(n, digits) {
	  var zero = '';
	  n = n.toString();

	  if (n.length < digits) {
	    for (i = 0; i < digits - n.length; i++)
	      zero += '0';
	  }
	  return zero + n;
	}

/**
 * 텍스트 수 줄이기
 * @param text
 * @param length
 * @returns
 */
function ellipsis(text, length) {
	
	var ellipsisString = "....";
	var outputString = text;
	
	if (text.length>0 && length>0) {
		if (text.length > length) {
			outputString = text.substring(0, length);
			outputString+= ellipsisString;
		} 
	}
	return outputString;
}

/**
 * 이미지 미리보기
 * @param input
 * @param imageId
 * @param tagId
 */
function preViewImage(input, imageId, tagId) {
	if (tagId != "")
		$("#"+tagId).show();
	
	if (input.files && input.files[0]) {
		var reader = new FileReader();
		reader.onload = function(e) {
			$("#"+imageId).attr("src", e.target.result);
		},
		reader.readAsDataURL(input.files[0]);
	}
}

/**
 * time만큼 시간 추가 
 * @param dateTime
 * @param time
 * @returns {String}
 */
function addTime(dateTime, time) {
	var dt = new Date(dateTime);
	var krTime = new Date(Date.parse(dt) + (time*1000) * 60 * 60);
		
	var year = parseInt(krTime.getFullYear());
	var month = parseInt(krTime.getMonth()+1);
	if (month < 9) {
		month = "0"+month;
	}
	var day = parseInt(krTime.getDate());
	var hour = parseInt(krTime.getHours());
	var minutes = parseInt(krTime.getMinutes());
	var second = parseInt(krTime.getSeconds());
	var makeKorDateTime = year+"-"+month+"-"+day+" "+hour+":"+minutes+":"+second;
	
	return makeKorDateTime;
	
}

function dateSub(day){
	var d = new Date();
	var dt = d - day*24*60*60*1000;
	var nd = new Date(dt);
	var year = nd.getFullYear();
	var month = nd.getMonth()+1;
	var day = nd.getDate();
	if (month <10) {
		month = "0"+month;
	}
	var yyyymmdd = year+month+day;
	return yyyymmdd;	
}

/**
 * @author 안지호
 * @desc 나이계산(birhDay 입력형식 : yyyy-mm-dd hh:mm:ss)
 */
function getAge(birthDay) { 
	var age = "";
	
	if (birthDay != "") {
		var checkNull = birthDay.indexOf("");
		if (checkNull == 0) {
			var preBirth = birthDay.split(" ");
			var birth = preBirth[0].split("-");
			var d1 = new Date(birth[0], birth[1]-1, birth[2]);
			var d2 = new Date();
			var diff = d2.getTime() - d1.getTime();
			var daysPast = Math.floor(diff / (1000 * 60 * 60 * 24));
			var age = Math.floor(daysPast / 365.25)+1;
		}
	}
	return age;
}

/**
 * @author 안지호
 * @param date(yyyy-mm-dd)
 * @desc 요일 계산
 * @returns
 */
function getInputDayLabel(date) {
	var week = new Array('일요일', '월요일', '화요일', '수요일', '목요일', '금요일', '토요일');
	
	var today = new Date(date).getDay();
	var todayLabel = week[today];
	return todayLabel;
}

/**
 * @author 안지호
 * @param val(yyyy-m-d)
 * @desc 날짜형식 재정의
 * @returns {String}
 */
function makeYYYY_MM_DD(val) {
	var yyyy_mm_dd = "";
	if (val != "") {
		var valSplit = val.split("-");
		var year = valSplit[0];
		var month = valSplit[1];
		var day = valSplit[2];
		if (month < 10) {
			month = "0"+month;
		}
		if (day < 10) {
			day = "0"+day;
		}
		yyyy_mm_dd = year+"-"+month+"-"+day;
		return yyyy_mm_dd;
	}
}

function today(){
    var date = new Date();

    var year  = date.getFullYear();
    var month = date.getMonth() + 1; // 0부터 시작하므로 1더함 더함
    var day   = date.getDate();

    if (("" + month).length == 1) { month = "0" + month; }
    if (("" + day).length   == 1) { day   = "0" + day;   }
   
    return year + "-" + month + "-" + day;  
}

function getYear(){
    var date = new Date();
    var year  = date.getFullYear();
    return year  
}

function getMonth() {
    var date = new Date();
    var month = date.getMonth() + 1; // 0부터 시작하므로 1더함 더함
    return month;
}

/**
 * 숫자만 입력받기
 * @param evt
 * @returns {Boolean}
 */
function digit_check(evt) {
	var code = evt.which?evt.which:event.keyCode;
	if (code < 48 || code > 57) {
		alert("숫자만 입력가능합니다.");
		return false;
	}
}

/**
 * date_time값 끝 "." 붙었을 때 처리
 * @param date_time
 * @returns
 */
function getDateTimeSplitComma(date_time) {
	var date = "";
	if (date_time == null) date_time = "";
	if (date_time != "") {
		date = gfn_csplit(date_time, ".");
	}
	return date[0];
}

function innerHTML(tagId, val) {
	if (val != "" && tagId != "") {
		$("#"+tagId).html(val);
	}
}

function innerHTMLAddColor(tagId, color) {
	if ((tagId != "") && (color != "")) {
		$("#"+tagId).css("color", color);
	}
}

function innerValue(tagId, val) {
	if (val != "" && tagId != "") {
		$("#"+tagId).val(val);
	}
}
/**
 * 체크박스 체크상태 변경하기
 * @param checkbox_id
 * @param boolean(true:체크상태로, false:체크안한 상태로)
 */
function isCheckbox(checkbox_id, boolean) {
	if (checkbox_id != "") {
		$("#"+checkbox_id).attr("checked",boolean);
	}
}

/**
 * 페이지 새로고침 하기
 * @param boolean(true:새로고침, false:새로고침 안하기)
 */
function isReloadPage(boolean) {
	window.location.reload(boolean);
}

/**
 * 부모페이지 새로고침
 */
function isReloadParentPage() {
	parent.document.location.reload();	
}

/**
 * 체크박스 체크여부 확인
 * @param checkboxId
 * @returns {Boolean}
 */
function isCheckedCheckbox(checkboxId, type) {
	var boolean = false;
	if (checkboxId != "") {
		if (type == "ID") {
			if ($("#"+checkboxId).is(":checked")) {
				boolean = true;
			} 	
		} else if (type == "NAME") {
			if ($("input[name="+checkboxId+"]").is(":checked")) {
				boolean = true;
			}
		}
	}
	return boolean;
}

function getSelectboxValue(tagId) {
	var selectedvalue = "";
	if (tagId != "") {
		selectedvalue = $("#"+tagId+" option:selected").val();	
	}
	return selectedvalue;
}

function getInputTextValue(tagId) {
	var inputValue = "";
	if (tagId != "") {
		inputValue = $("#"+tagId).val();
	}
	return inputValue;
}

function getInnerHtmlValue(tagId) {
	var innerHtmlValue = "";
	if (tagId != "") {
		innerHtmlValue = $("#"+tagId).html();
	}
	return innerHtmlValue;
}

function focusInputText(tagId) {
	if (tagId != "") {
		$("#"+tagId).focus();
	}
}

/**
 * 테이블 리스트 데이터 표현시 0값을 "0"로 변환
 * @param val
 * @param plusVal
 * @returns {String}
 */
function gfn_zeroToZero(val, plusVal) {
	var str;
	if (val == undefined) val = "0";
	if (plusVal == undefined) plusVal = "";
	
	if (val == "0" && plusVal == "") {
		str = "0";
	} else {
		if (plusVal != "") {
			str = val += plusVal;
		} else {
			str = val;	
		}
	}
	return str;
}

/**
 * 테이블 리스트 데이터 표현시 0값을 "-"로 변환
 * @param val
 * @param plusVal
 * @returns {String}
 */
function gfn_zeroToPipe(val, plusVal) {
	var str;
	if (plusVal == undefined) plusVal = "";
	if (val == "0") {
		str = "-";	
	} else {
		if (plusVal != "") {
			str = val += plusVal;
		} else {
			str = val;	
		}
	}
	return str;
}

function gfn_leftZeroLeave(val) {
	var str = "";
	if (val != "") {
		str = val.replace(/(^0+)/, ""); 
	}
	return str;
}

/**
 * 값 비교해서 원하는 값 리턴
 * @param val(비교할 값)
 * @param if_data (조건 값)
 * @param true_data (참일 떄 값)
 * @param false_data (거짓일 때 값)
 * @returns {String}
 */
function gfn_isData(val, if_data, true_data, false_data) {
	var str = "";
	/*
	if (val == if_data) str = true_data;
	else str = false_date;
	return str;
	*/
	return str = (val == if_data ? true_data : false_data);
	
}

function form_submit(form_id, action_url) {
	$("#"+form_id).attr("action", action_url);
	$("#"+form_id).submit();
}
////////////////////////////////////////////////////////////////////////////////
//HashMap Class Section
////////////////////////////////////////////////////////////////////////////////
/**
* key:value 를 사용하는 HashMap
* @example
* <pre>
* var map = new SOFOHashMap();
* map.put("key","value");       
* map.get("key");               
* map.length;			길이 반환        
* map.keys();			모든 키 객체반환        
* map.values():		    모든 값 객체반환
* map.toQuaryString([option]);   key=value[option] 문자열반환
* map.clear();			초기화               
* map.next();			다음 객체 반환
* map.indexValue(index);위치로 값 찾기	
* map.splice(key);		key 삭제 
* map.point(key);		key 의 위치반환	     	
* </pre>
*/	  
var SOFOHashMap = function()
{
	this.obj = [];
	this.length = 0;		
	
	this.put = function(key, value)
	{ 
		if( this.obj[key] == null )this.length++; 
		this.obj[key] = value; 
	};

	this.get = function(key)
	{
		return this.obj[key];
	};
	
	this.keys = function()
	{
		var keys = [];
		for ( var property in this.obj ) keys.push(property);
		return keys;
	};
	
	this.values = function()
	{
		var values = [];
		for ( var property in this.obj ) values.push(this.obj[property]);
		return values;
	};
	
	this.toQueryString = function(divMark)
	{
		var divMark = (typeof divMark == "undefined") ? "&" : divMark;
		var quaryString = "";
		var key = this.keys();
		var value = this.values();
		if ( this.length < 1 ) return "";
		
		for( var i = 0 ; i < this.length ; i++ )
		{
			if ( quaryString != "" )
				quaryString += divMark;
			quaryString += 	key[i] +"="+ value[i];
		}
		return quaryString;
	};
	
	this.remove = function(index)
	{
		var keys = this.keys();
		keys.splice(index, 1);
		var temp =[];			 	
		for ( var i = 0 ; i < keys.length ; i++ )
		{
			temp[keys[i]] = this.obj[keys[i]];
		}	 
		this.obj = temp;
		this.length = keys.length;
		index--;
	};
	
	this.indexOf = function(key)
	{
		var cnt = 0;
		for ( var i in this.obj )
		{
			if ( key == i ) return cnt;
				cnt++;	
		}
	};
	
	this.splice = function(spliceIndex)
	{
		var keys = this.keys();
		keys.splice(spliceIndex, 1);
		var temp =[];			 	
		for ( var i = 0 ; i < keys.length ; i++ )
		{
			temp[keys[i]]=this.obj[keys[i]];
		}	 
		this.obj = temp;
		this.length = keys.length;
		index--;
	};

	
	this.point = function(key)
	{
		var cnt = 0;
		for ( var i in this.obj )
		{
			if ( key == i ) return cnt;
				cnt++;	
		}
	};

	this.clear = function()
	{
		this.obj = [];
		this.length = 0;
	};
		
	var index = 0;
	this.next = function()
	{
		if ( index == this.length )
		{
			index = 0;
			return -1;
		}
		var values = this.values();
		var currentValue = values[index]; 	
		index++;
		return currentValue;
	};

	this.indexValue = function(Idx)
	{
		var keys = this.keys();
		return this.obj[keys[Idx]];
	};
};

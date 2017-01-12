/*-------------------------------------------------- 
*   파일명    :   com_page.js
*   등록자    :   안지운 |   2014.7.4
*   수정자    :
*   내   용   :   페이지 관련 스크립트 정리
*--------------------------------------------------*/ 

//--------------------------------------------------
//  검색처리
//--------------------------------------------------
// 페이지 이동처리

var webRoot = ".";

function gfn_fgoPage(page) { 
	var sPage = document.getElementById("sPage");
    sPage.value= page; 
    target = "_self";  
    action = self.location;  
    submit(); 
}   

//--------------------------------------------------
//  클릭한 페이지로 이동
//--------------------------------------------------
function gfn_fnList(page) {
 		var sPage = document.getElementById("sPage");
        sPage.value = page;
 	try {
 		fn_search();
 	} catch (e){
 		
 	}
}   

//--------------------------------------------------
//  클릭한 페이지로 이동
//--------------------------------------------------
function gfn_fnList_new(page) { 
 		var sPage = document.getElementById("sPage");
        sPage.value = page;
 	try {
 		fn_detailSearch();
 	} catch (e){

 	}
}

function gfn_fnList2(page) {
	var sPage = document.getElementById("sPage2");
	sPage.value = page;
	try {
		fn_search2();
	} catch (e){
		
	}
}   


//--------------------------------------------------
//  전체건수/페이지를 출력
//--------------------------------------------------
function gfn_printPageNum_new(totalCount,printNum,rowNum,curPage){ 
	
   if (totalCount==0){     //( 총 100건 , 페이지 1/50 )
	   //document.all['total'].innerHTML = " ( 총 0건 , 페이지 0/0 )"; 
   	   var sHtml = gfn_getPageNav_new('1',printNum,curPage); 
   	   document.all['pages'].innerHTML = "";
    } else { 
		var totalPageCnt = Math.ceil(totalCount / rowNum);
		var sHtml = gfn_getPageNav_new(totalPageCnt,printNum,curPage); 
		//document.all['total'].innerHTML = " ( 총 "+totalCount+"건 , 페이지 "+curPage+"/"+totalPageCnt+" )";
		document.all['pages'].innerHTML = sHtml;    
    }
}

function gfn_printPageNum_new2(totalCount,printNum,rowNum,curPage){ 
	
	   if (totalCount==0){     //( 총 100건 , 페이지 1/50 )
		   //document.all['total'].innerHTML = " ( 총 0건 , 페이지 0/0 )"; 
	   	   var sHtml = gfn_getPageNav_new2('1',printNum,curPage); 
	   	   document.all['pages2'].innerHTML = "";
	    } else { 
			var totalPageCnt = Math.ceil(totalCount / rowNum);
			var sHtml = gfn_getPageNav_new2(totalPageCnt,printNum,curPage); 
			//document.all['total'].innerHTML = " ( 총 "+totalCount+"건 , 페이지 "+curPage+"/"+totalPageCnt+" )";
			document.all['pages2'].innerHTML = sHtml;    
	    }
	}

//--------------------------------------------------
//  페이지 내비게이션을 생성
//--------------------------------------------------

function gfn_getPageNav_new(totalPageCnt,printNum,curPage) {  

		var stdPage = parseInt(Math.ceil(parseInt(curPage)/parseInt(printNum)));
		
		var iStart = (stdPage - 1) * printNum + 1 ;
		var iEnd = stdPage * printNum;
		
	    if (iEnd  > totalPageCnt) { 
	        iEnd = totalPageCnt; 
	    } 
		var sHtml = ""; 
		var ibefore = parseInt(curPage) - parseInt(printNum) ;
		if (ibefore < 1) ibefore = 1;
		sHtml += "<a href='javascript:gfn_fnList(1)' ><img src='"+webRoot+"/img/arrow1.png' border='0' align='absmiddle'></a>";
		sHtml += "<a href='javascript:gfn_fnList(" + ibefore + ")' ><img src='"+webRoot+"/img/arrow2.png' border='0' align='absmiddle'></a>";   
		
		for(var i=iStart; i<= iEnd; i++) {  
			if(curPage== i) {
				sHtml += "<strong> <a href='javascript:gfn_fnList(" + i + ")' style='color:red'>" + i + "</a></strong>  ";
	            //sHtml += "<strong title=\"현재페이지\"> " + i + " </strong>  "; 
	        } else { 
	            sHtml += "<a href='javascript:gfn_fnList(" + i + ")'>" + i + "</a>  "; 
	        } 
	        if(i!=iEnd) {// 마지막 라인에는 구분자를 뺌
	          	sHtml += "<a> | </a> ";
	         }
		} 
		var inext = parseInt(curPage) + parseInt(printNum) ;
		if (inext > totalPageCnt) inext = totalPageCnt;
		sHtml += "<a href='javascript:gfn_fnList(" + inext + ")' ><img src='"+webRoot+"/img/arrow3.png' border='0' align='absmiddle'></a>";  
		sHtml += "<a href='javascript:gfn_fnList(" + totalPageCnt + ")'><img src='"+webRoot+"/img/arrow4.png' border='0' align='absmiddle'></a>";  
		
		return sHtml; 
	}

function gfn_getPageNav_new2(totalPageCnt,printNum,curPage) {  

	var stdPage = parseInt(Math.ceil(parseInt(curPage)/parseInt(printNum)));
	
	var iStart = (stdPage - 1) * printNum + 1 ;
	var iEnd = stdPage * printNum;
	
    if (iEnd  > totalPageCnt) { 
        iEnd = totalPageCnt; 
    } 
	var sHtml = ""; 
	var ibefore = parseInt(curPage) - parseInt(printNum) ;
	if (ibefore < 1) ibefore = 1;
	sHtml += "<a href='javascript:gfn_fnList2(1)' ><img src='"+webRoot+"/img/arrow1.png' border='0' align='absmiddle'></a>";
	sHtml += "<a href='javascript:gfn_fnList2(" + ibefore + ")' ><img src='"+webRoot+"/img/arrow2.png' border='0' align='absmiddle'></a>";   
	
	for(var i=iStart; i<= iEnd; i++) {  
		if(curPage== i) {
			sHtml += "<strong> <a href='javascript:gfn_fnList2(" + i + ")' style='color:red'>" + i + "</a></strong>  ";
            //sHtml += "<strong title=\"현재페이지\"> " + i + " </strong>  "; 
        } else { 
            sHtml += "<a href='javascript:gfn_fnList2(" + i + ")'>" + i + "</a>  "; 
        } 
        if(i!=iEnd) {// 마지막 라인에는 구분자를 뺌
          	sHtml += "<a> | </a> ";
         }
	} 
	var inext = parseInt(curPage) + parseInt(printNum) ;
	if (inext > totalPageCnt) inext = totalPageCnt;
	sHtml += "<a href='javascript:gfn_fnList2(" + inext + ")' ><img src='"+webRoot+"/img/arrow3.png' border='0' align='absmiddle'></a>";  
	sHtml += "<a href='javascript:gfn_fnList2(" + totalPageCnt + ")'><img src='"+webRoot+"/img/arrow4.png' border='0' align='absmiddle'></a>";  
	
	return sHtml; 
}
/*-------------------------------------------------- 
* End
*--------------------------------------------------*/ 
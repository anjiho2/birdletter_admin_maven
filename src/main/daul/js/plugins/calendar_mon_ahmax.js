/*
*	2011.03.19 make by ahmax(ahmax95@gmail.com)
*	http://beans9.tistory.com
*/
// max year ( max_year_mon = 0 = now year) 
var max_year_mon = 2023;

// min year
var min_year_mon = 2012;

// date format (xxxx-xx-xx)
var date_split_format_mon = "-";
						
// today			
var dayObj	  	  = new Date();
var todayStr_year = dayObj.getFullYear();
var todayStr_mon  = ((dayObj.getMonth()+101)+"").substring(1,3);
var todayStr_full  = todayStr_year+date_split_format_mon+todayStr_mon;			

function initCalMon(target){
	var id = target + "CalMon";
	$(function(){		
		var tInput  = $("#" + target).offset();
		var tHeight = $("#" + target).outerHeight();
		
		// 입력값이 있을경우 달력초기화시 해당 입력달에 날짜로 셋팅
		var targetVal = $("#" + target).val();							
		if( targetVal == "") targetVal = todayStr_full;
						
		$(document.body).append($("<div id='"+ id +"'>"));
		$(document).click(function(event){
			//alert($(event.target).attr('id'))
			if((event.target.id != id && event.target.id != target && event.target.id != target+"_img") && $("#" + id).css('display') != "none" ){
				$("#" + id).hide();
			};	
		});;
	
    $("#" + id).addClass("divBody");
    $("#" + id).html(makeCalMon(id, targetVal));
    $("#" + id + "_cal div[value]").addClass("dayEvMon");
    
    $("#" + id).click(function(e){
			e.stopPropagation();
		});
		
		//wheel event
		$("#" + id).bind('mousewheel', function(event, delta) {
        if (delta > 0) {moveMon(id, 1);}
				else {	moveMon(id, -1); }
				addHoverMon(id,target);
            return false;
        });

		$("#" + target).attr("readonly", true);
		
		$("#" + target).click(function(e){
			calMonPosition(target,id);									
			if($("#"+target).val() != ""){
				var subYear = $("#"+target).val();														
				$("#"+id+"_calYear").val(subYear.substring(0,4));
				addHoverMon(id,target);
			}
			$("#" + id).show();
		});
		$("#" + target).next("img").attr("id",target+"_img");
		$("#" + target).next("img").click(function(e){
			calMonPosition(target,id);									
			if($("#"+target).val() != ""){
				var subYear = $("#"+target).val();
				
				$("#"+id+"_calYear").val(subYear.substring(0,4));
				addHoverMon(id,target);
			}
			$("#" + id).show();
		});	
		
		//alert('sdf')
		//$("#" + id + "_calYear").attr("value",targetVal.split(date_split_format_mon)[0]);
		
		var index = $("#" + id + "_calYear").find("option[value*="+targetVal.split(date_split_format_mon)[0]+"]").index();
		$("#" + id + "_calYear option:eq("+index+")").attr("selected", "selected");
		
		$("#" + id + "_calYear").change(function(){															
			addHoverMon(id,target);
		}).keyup(function(){
			addHoverMon(id,target);
		}).mousewheel(function(e, d){			
			this.blur();
		});
			
		// top option	
		$("#" + id + "_left").click(function(e){
			moveMon(id,-1);
			addHoverMon(id,target);
			this.blur();
		}).mouseover(function(){
			$(this).addClass("divHeadOnleft");
		}).mouseout(function(){
			$(this).removeClass("divHeadOnleft");
		});
		
		$("#" + id + "_right").click(function(e){
			moveMon(id,1);
			addHoverMon(id,target);
			this.blur();					
		}).mouseover(function(){
			$(this).addClass("divHeadOnRigth");
		}).mouseout(function(){
			$(this).removeClass("divHeadOnRigth");
		});
		
				// date option
		$("#" + id + "_cal div[value]").hover(function(){
			$(this).addClass("onDay");
		},function(){
			$(this).removeClass("onDay");
		}).click(function(e){						
			$("#" + target).attr("value", $("#" + id + "_calYear").val() + date_split_format_mon + $(this).attr("value"));
			$("#" + id).hide();
			$("#" + id + " .selMon").removeClass("selMon");
			$(this).addClass("selMon");
			$("#" + target).change();
		});								
		addHoverMon(id,target);
		
		$(window).resize(function() {
	    	calMonPosition(target,id);
	    });
	});
}

function calMonPosition(target,id){
	var tInput  = $("#" + target).offset();
	var tHeight = $("#" + target).outerHeight();							
	var calHeight 	= $("#" + id).outerHeight();				
	if( tInput != null){
		$("#" + id).css({"top":tInput.top+tHeight , "left":tInput.left});
	}
}

function addHoverMon(id,target){
	$(function(){		
		$("#" + id + " .selMon").removeClass("selMon");
		$("#" + id + " .MonToday").removeClass("MonToday");											
		
		// 선택달 표시
		if($("#"+target).val() != ""){
				var subYear = $("#"+target).val();														
				if($("#"+id+"_calYear").val() == subYear.substring(0,4)){
					$("#" + id + "_cal [value='"+ subYear.substring(5,7) +"']").addClass("selMon");														
		}}
		
		// 현재달 표시
		if($("#"+id+"_calYear").val() == todayStr_year){
			$("#" + id + "_cal [value='"+ todayStr_mon +"']").addClass("MonToday");														
		}
	});
}	

function makeCalMon(id , tValue)
{
	 var today;
	 var cal_html = "";				 
	 var tDate = tValue.split(date_split_format_mon);
	 
	 cal_html += "<table width='100%'><tr><td><div class='divHead'><table class='calCss'><tr><td id='"+ id +"_left' class='divHeadLeft'><span class='ui-icon ui-icon-circle-triangle-w'></span></td><td class='divHeadCenter'><select id='"+id+"_calYear' class='select'>";				 				
	 if( max_year_mon == 0) max_year_mon = tDate[0] ;
	 for(var i=min_year_mon;i<=max_year_mon;i++){	cal_html += "<option value='"+i+"'>" + i + "</option>"; }
	 cal_html += "</select> 년</td><td id='"+ id +"_right' class='divHeadRigth'><span class='ui-icon ui-icon-circle-triangle-e'></span></td></tr></table></div></td></tr><tr><td><div id='" + id + "_cal" + "'><table class='calCss'><tr>";							 

   for (i=1;i<=12;i++){			
		var dValue = i;
		if( i < 10 ) dValue = "0" + dValue;									
		cal_html += "<td><div value='" + dValue +"' class='calDiv'>" + i + "월</div></td>";
		if (i % 4 == "0"){ cal_html +="</tr><tr>"; }
	 }					
	 				 				 
	 cal_html +="</div></td></tr></table></div>";
	 			 
	 return cal_html;	 
}			

function moveMon(id,plus){
	var year  = eval($("#" + id + "_calYear").val());
	year += plus;					
	if( max_year_mon == 0) max_year_mon = todayStr_full.split(date_split_format_mon)[0] ;
	if( year < min_year_mon || year > max_year_mon ) return;	
	var index = $("#" + id + "_calYear").find("option[selected]").removeAttr("selected");
	var index = $("#" + id + "_calYear").find("option[value="+year+"]").index();
	$("#" + id + "_calYear option:eq("+index+")").attr("selected", "selected");
}
			
	
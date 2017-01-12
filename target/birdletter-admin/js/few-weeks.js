var month_days       = new Array( 31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31 );
var month_days_count = new Array();

function is_leap_year( year )
{
    return ( year % 4 == 0 && year % 100 != 0 ) || ( year % 400 == 0 && year % 4000 != 0 );
}

// 년도에 맞게 month_days 와 month_days_count의 값을 설정한다. 
function set_month_days( year )
{
        if( is_leap_year( year ) ) month_days[1] = 29; else month_days[1] = 28;

        month_days_count[0] = 0;

        for( i=0;i<12;i++){
                month_days_count[i+1] = month_days_count[i] + month_days[i];
        }
}

function find_start_week(year, week)
{
	set_month_days( year );

	var weekday = new Date( year,0,1).getDay();
	var total_days = ( (week) * 7 ) - weekday;
	var yy1,yy2,mm1,mm2,dd1,dd2;

	if( total_days < 0 ){
		yy1 = year-1;
		yy2 = year;
		mm1=12;
		mm2=1;
		dd1=32-weekday;
		dd2=7-weekday;
	}else{
		yy1 = yy2 = year;
		
		for( mm1 = 0 ; mm1 < 12 ; mm1++ ){
			if( month_days_count[mm1] > total_days ) break;
		}
		
		dd1 = total_days - month_days_count[mm1-1] + 1;        
		dd2 = dd1 + 6;
		mm2 = mm1;

		if( dd2 > month_days[ mm1-1 ] ){
			mm2 = mm1 % 12 + 1;
			dd2 = dd2 - month_days[ mm1-1];
		}
		
		if( mm1==12 && mm2==1 ) yy2++;  
	}
        
	var ss0 =  year + "년 " + week  + "주" 
	var ss1 =  yy1 + "-" + mm1 + "-" + dd1;
	//var ss2 =  yy2 + "-" + mm2 + "-" + dd2;        
	//alert(ss0 + " : "  + ss1 + "~" + ss2 );
	return ss1;
}

function find_end_week(year, week)
{
	set_month_days( year );

	var weekday = new Date( year,0,1).getDay();
	var total_days = ( (week) * 7 ) - weekday;
        
	var yy1,yy2,mm1,mm2,dd1,dd2;

	if( total_days < 0 ){
		yy1 = year-1;
		yy2 = year;
		mm1=12;
		mm2=1;
		
		dd1=32-weekday;
		dd2=7-weekday;
		
	}else{
		yy1 = yy2 = year;
		
		for( mm1 = 0 ; mm1 < 12 ; mm1++ ){
			if( month_days_count[mm1] > total_days ) break;
		}
		dd1 = total_days - month_days_count[mm1-1] + 1;        
		dd2 = dd1 + 6;
		mm2 = mm1;
		
		if( dd2 > month_days[ mm1-1 ] ){
			mm2 = mm1 % 12 + 1;
			dd2 = dd2 - month_days[ mm1-1];
		}
		
		if( mm1==12 && mm2==1 ) yy2++;  
	}
        
	var ss0 =  year + "년 " + week  + "주" 
	//var ss1 =  yy1 + "-" + mm1 + "-" + dd1;
	var ss2 =  yy2 + "-" + mm2 + "-" + dd2;        
	//alert(ss0 + " : "  + ss1 + "~" + ss2 );
	return ss2;
}
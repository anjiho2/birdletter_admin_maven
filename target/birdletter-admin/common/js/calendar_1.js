var public_description = new init();
var curObj;
var curObj2;
var ctime ="";
var webRoot = "";

//get current date..
var now_date   = new Date();
var s_year     = now_date.getYear();
if( s_year<1900) s_year=s_year+1900;
var s_month    = return0(now_date.getMonth()+1);
var s_day      = return0(now_date.getDate());
var dtype      = "YYYY-MM-DD";
var strLang    = "";
//var month_name=new Array('Jan','Feb','Mar','Apr','May','Jun','Jul','Aug','Sep','Oct','Nov','Dec');
var month_name = new Array('01','02','03','04','05','06','07','08','09','10','11','12');
var day_name   = new Array('S','M','T','W','T','F','S');
var isSelect   = 0;
var calWidth   = 180;
var menuPopup;

function init()
{
   this.put_datetype   = put_datetype;
   this.put_curDate    = put_curDate;
   this.put_month_name = put_month_name;
   this.put_day_name   = put_day_name;
   this.put_select     = put_select;
   this.put_calWidth   = put_calWidth;
}

function hover(on, el)
{
   if (el && el.nodeName == "TD")
   {
      if (el.title == '') return;
      if (on)
      {
         el.style.border = '1px solid #000033';
      }
      else
      {
         el.style.border = '1px solid #ffffff';
      }
   }
}

function choose(y,m, el)
{
   //var el = window.event.srcElement;
   if (el && el.nodeName == "TD")
   {
      if (el.title == '') return;
      return_date(y,m,el.title);
   }
}

function put_datetype(str)
{
   dtype = str;
}

function put_month_name(str)
{
   month_name = str;
}

function put_day_name(str)
{
   day_name = str;
}

function put_select(str)
{
   isSelect = str;
}

function put_calWidth(str)
{
   calWidth = str;
}

function put_curDate(str)
{
	var y=0,m=0,d=0;
	ctime = "";
	if (str.length == 0 )
	{
		y = s_year;
		m = s_month;
		d = s_day;
	}
	else
	{
		y = parseInt(str.substring(0,4),10);
		m = parseInt(str.substring(4,6),10);
		d = parseInt(str.substring(6,8),10);

		if (str.length >= 14) ctime = str.substring(8,14);
	}

	show_current(y,m,d);

	return(false);
}

function setLang( lang )
{

	if( lang != null || lang != "" )
		strLang = lang;
	else
		strLang = "E";
}

function return0(str)
{
	str=""+str;
	if (str.length==1) str="0"+str;
	return str;
}

function dreplace( str , old_char , new_char )
{
	if( str == null || str == "" ) return;
	else
	{
		var fromindex = 0;
		var temp = "";
		for(var i=0 ; i<str.length ; i++)
		{
			fromindex = i;
			pos = str.indexOf(old_char,fromindex);
			if( pos != -1 )
			{
				temp = str.substring(0,pos) + new_char + str.substring(pos+old_char.length);
				str = temp;
				i = pos+new_char.length-1;
			} else break;
		}
		return str;
	}
}

//open calendar
function show_current(y,m,d)
{
	s_year=y;
	s_month=m;
	s_day=d;

	make_calendar(s_year,s_month,s_day)
}

//processing changed date
function return_date(year_item, month_item, day_item)
{
	if( year_item < 1900) year_item = 1900 + year_item;

	month_item=return0(month_item);
	day_item=return0(day_item);
	//make_calendar(year_item,month_item,day_item);
	input_date(year_item,month_item,day_item);
}

//output selected date
function input_date(year_item, month_item, day_item)
{
	var m_name  = new Array("Jan","Feb","Mar","Apr","May","Jun","Jul","Aug","Sep","Oct","Nov","Dec");
	var m_name2 = new Array('JANUARY','FEBRUARY','MARCH','APRIL','MAY','JUNE','JULY','AUGUST','SEPTEMBER','OCTOBER','NOVEMBER','DECEMBER');
	if( year_item < 1900) year_item = 1900 + year_item;
	month_item  = ""+month_item;
	day_item    = ""+day_item;
	month_item2 = return0(month_item);
	day_item2   = return0(day_item);

	realDate = dtype.toUpperCase();
	if      (realDate.indexOf("YYYY") !=-1) realDate=dreplace(realDate, "YYYY", year_item);
	else if (realDate.indexOf("YY")   !=-1) realDate=dreplace(realDate, "YY"  , year_item.toString().substr(2,2));
	if      (realDate.indexOf("DD")   !=-1) realDate=dreplace(realDate, "DD"  , day_item2);
	else if (realDate.indexOf("D")    !=-1) realDate=dreplace(realDate, "D"   , day_item);
	if      (realDate.indexOf("MON")  !=-1) realDate=dreplace(realDate, "MON" , m_name[parseInt(month_item,10)  -1]);
	else if (realDate.indexOf("MMMM") !=-1) realDate=dreplace(realDate, "MMMM", m_name2[parseInt(month_item,10) -1]);
	else if (realDate.indexOf("MMM")  !=-1) realDate=dreplace(realDate, "MMM" , m_name[parseInt(month_item,10)  -1]);
	else if (realDate.indexOf("MM")   !=-1) realDate=dreplace(realDate, "MM"  , month_item2);
	else if (realDate.indexOf("M")    !=-1) realDate=dreplace(realDate, "M"   , month_item);

	//var backupidval = year_item + "-" + month_item2 + "-" + day_item2;
	var backupidval = realDate;
	var backupval   = realDate;

  //window.external.raiseEvent(backupidval,backupval);
  /*
    if(scriptId=="setEndDate")
    {
          setEndDate(backupidval);
    }
    else if(scriptId=="setDate3")
    {
        setDate3(backupidval);
    }
    else
    {
        setStartDate(scriptId, backupidval);
    }
    */

	setCalDate(scriptId, backupidval);
    menuPopup.hide();
}

function setCalDate(targetName, returnStr)
{
	//var f	= document.forms[0];
	//f.elements[targetName].value = returnStr;

    eval(targetName).value = returnStr;
}

// selected date
function get_date()
{
	var y = s_year;
	var m = s_month;
	var d = s_day;

	getDate  = getCalDate(scriptId);
	typeDate = scriptType.toUpperCase();

	if (getDate.length == typeDate.length)
	{
		if      (typeDate.indexOf("YYYY") !=-1) y = getDate.substr(typeDate.indexOf("YYYY"), 4);
		else if (typeDate.indexOf("YY")   !=-1) y = getDate.substr(typeDate.indexOf("YY"),   2);
		if      (typeDate.indexOf("DD")   !=-1) d = getDate.substr(typeDate.indexOf("DD"),   2);
		else if (typeDate.indexOf("D")    !=-1) d = getDate.substr(typeDate.indexOf("D"),    1);
		if      (typeDate.indexOf("MM")   !=-1) m = getDate.substr(typeDate.indexOf("MM"),   2);
		else if (typeDate.indexOf("M")    !=-1) m = getDate.substr(typeDate.indexOf("M"),    1);
	}
	showCalendarPopup(y,m,d);
}

function getCalDate(targetName)
{
	//var f	= document.forms[0];
	//return f.elements[targetName].value;
	return eval(targetName).value;	
}

//draw calendar UI
function make_calendar(y,m,d)
{
    y = parseInt(y, 10);
    m = parseInt(m, 10);
    d = parseInt(d, 10);

	var content="";


    if(window.navigator.appName == "Microsoft Internet Explorer" && window.navigator.appVersion.substring(window.navigator.appVersion.indexOf("MSIE") + 5, window.navigator.appVersion.indexOf("MSIE") + 8) >= 5.5)
    {
        isIe = 1;
    }
    else
    {
        isIe = 0;
    }

    if(isIe)
    {

    	var day_num=new Array(31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31);

        if (m == 0) { y = y - 1; m = 12; }
        else if (m == 13) { y = y + 1; m = 1; }

    	if(((y % 4 == 0) && (y % 100 != 0)) || (y % 400 == 0)) day_num[1]=29;

    	var first   = new Date(y,m-1, 1);
    	firstday    = first.getDay()+1
    	DaysInMonth = day_num[m-1]

    	if ((m-2) == -1) DaysInBefMonth = day_num[11]
    	else DaysInBefMonth = day_num[m-2]


    	if (d > DaysInMonth) day = DaysInMonth
    	else day = d


    	//content=content + "<HTML><BODY style='font-size:0.75em;margin-top: 0; margin-left: 0; left-margin: 0; top-margin: 0; margin-right: 0; right-margin: 0; margin-bottom: 0; bottom-margin: 0; background-color: #ffffff;  font-style: normal; font-weight: normal; font-family: '����', '����', 'arial', 'verdana'>";
    	content=content + "<TABLE width='180px' height='180px' style='border:solid 1;TABLE-layout:fixed '><TR><TD>\n";
    	content=content + "<CENTER>\n";
    	content=content + "<FORM style=font-family:verdana; NAME=calendar>\n";
    	content=content + "<TABLE><TR><TD></TD></TR></TABLE>\n";
    	content=content + "<TABLE style='TABLE-layout:fixed' cellspacing='0' cellpadding='0' width='170' height='160' border='0' bgcolor='#ACAFAF'" +
    	         "onmouseover='parent.hover(true, window.event.srcElement)' onmouseout='parent.hover(false, window.event.srcElement)' onclick='parent.choose(" + y + "," + m + ", window.event.srcElement)'> " +
    	         "<tr><td align=center colspan=7  height='25' style='background-color:#eff7ff'>\n"

      if (isSelect)
      {
    	   content=content + "<select style='font-size:8pt;' name=years  onChange='parent.showCalendarPopup(this.value, calendar.months.value,"+ day+")'>\n"

       	 for( i=1901 ;i <= 2037;i++)
    	   {
    		   content=content + "<option value=" + i
    		   if (i ==y) content=content +  " selected>" + i  + "</option>\n"
    		   else content=content +  ">" + i + "</option>\n"
    	   }

         content=content + "</select>&nbsp;\n"
    	 content=content + "<select style='font-size:8pt;' name=months onChange='parent.showCalendarPopup(calendar.years.value, this.value,"+ day+")'>\n"

    	   for (i=1 ;i<= 12;i++)
    	   {
    		    content=content + "<option value=" + i
    		    if (i ==m) content=content +  " selected>"
    		    else content=content +  ">"

    		    content=content + month_name[i-1]  + "</option>\n"
    	   }

    	   content=content + "</select >\n"
      }
      else
      {
         content=content + "<font style='font-size:12px;font-family:tahoma,verdana; font-weight:bold; vertical-align:middle;'>";
    	 content=content + "<span style='font-size: 8px;font-family:tahoma,verdana; color:blue; text-decoration:none; cursor:hand' font-weight:bold' onClick='parent.showCalendarPopup(" + (y-1) + "," + m + "," + day + ");' ><img src="+webRoot+"/images/icon/arrow2.png width=15px height=15px align=absmiddle></span>&nbsp;" + y;
    	 content=content + "&nbsp;<span style='font-size: 8px;font-family:tahoma,verdana; color:blue; text-decoration:none; cursor:hand' onClick='parent.showCalendarPopup(" + (y+1) + "," + m + "," + day + ");' ><img src="+webRoot+"/images/icon/arrow3.png width=15px height=15px align=absmiddle>&nbsp;</span>";
    	 content=content + "<span style='font-size: 8px;font-family:tahoma,verdana; color:blue; text-decoration:none; cursor:hand' onClick='parent.showCalendarPopup(" + y + "," + (m-1) + "," + day + ");' >&nbsp;<img src="+webRoot+"/images/icon/arrow2.png width=15px height=15px align=absmiddle></span>&nbsp;" + m;
    	 content=content + "&nbsp;<span style='font-size: 8px;font-family:tahoma,verdana; color:blue; text-decoration:none; cursor:hand' onClick='parent.showCalendarPopup(" + y + "," + (m+1) + "," + day + ");' ><img src="+webRoot+"/images/icon/arrow3.png width=15px height=15px align=absmiddle></span>";
         content=content + "</font><span style='font-size:8px'>&nbsp;</span>";
      }

      //content=content + "<font style='font-size:12px; font-family:tahoma,verdana; font-weight:bold; vertical-align:middle;' >" + day +" </font>";
      content=content + "<span style='font-size: 8px;font-family:tahoma,verdana; color:blue; text-decoration:none; cursor:hand' onClick='parent.showCalendarPopup(" + s_year + "," + s_month + "," + s_day + ");' >[Today]</span>";

    	content=content + "</td></tr>\n"

    	//content=content + "<TABLE width=100% border=2 cellspacing=0 cellpadding=2 bordercolorlight=#999999 bordercolordark=#999999 bordercolor=#999999>\n"
    	content=content + "<TR height='20' bgcolor='#EAEAEA' align='center'>\n"
    	content=content + "<Td width='20' style='font-size:12px;color:#C20000;'>" + day_name[0] + "</Td>\n"
    	content=content + "<Td width='20' style='font-size:12px;color:#000000;'>" + day_name[1] + "</Td>\n"
    	content=content + "<Td width='20' style='font-size:12px;color:#000000'>" + day_name[2] + "</Td>\n"
    	content=content + "<Td width='20' style='font-size:12px;color:#000000'>" + day_name[3] + "</Td>\n"
    	content=content + "<Td width='20' style='font-size:12px;color:#000000'>" + day_name[4] + "</Td>\n"
    	content=content + "<Td width='20' style='font-size:12px;color:#000000'>" + day_name[5] + "</Td>\n"
    	content=content + "<Td width='20' style='font-size:12px;color:#000000'>" + day_name[6] + "</Td>\n"
    	content=content + "</TR><TR height='15%' bgcolor=#FFFFFF>\n"

    	var column = 0

    	for (i=1 ; i <= (firstday-1);i++)
    	{
    		//content=content + "<TD align=center style='font-size:12px; font-family:tahoma,verdana; background-color:cccccc; vertical-align:top'>"+ (DaysInBefMonth-(firstday-1)+i) +"</TD>\n"
    		content=content + "<TD align=center style='font-size:12px;bgcolor:#ffffff;'></TD>\n"
    		column =column + 1
    	}

    	for( i=1 ; i<= DaysInMonth;i++)
    	{
    		if(y == s_year && m == s_month && i == s_day) content=content+"<TD align=center style='border:1px; font-size:12px; font-family:tahoma,verdana; font-weight:bold; background-color:#FFDE84; vertical-align:middle; cursor:hand'\n"
    		else if(column ==0) content=content+"<TD align=center style='border:1px; font-size:12px; font-family:tahoma,verdana; background-color:ffffff; vertical-align:middle; color:#ff0000; cursor:hand'\n"
    		else if(column ==6) content=content+"<TD align=center style='border:1px; font-size:12px; font-family:tahoma,verdana; background-color:ffffff; vertical-align:middle; color:#0000ff; cursor:hand'\n"
    		else  content=content+"<TD align=center style='border:1px; font-size:12px; font-family:tahoma,verdana; background-color:ffffff; vertical-align:middle; cursor:hand'\n"
    		content = content + " title=" + i + ">" + i;
    		//content=content + "<span style=cursor:hand; onclick=javascript:parent.return_date(" + y + "," + m+ "," + i + ")>" + i + "</span>"
    		column  = column + 1
    		content = content + "</TD>\n"

    		if(column == 7 && i < DaysInMonth)
    		{
    			content = content + "</TR><TR height='15%' bgcolor=#FFFFFF  >\n"
    			column = 0
    		}
    	}

    	if((column > 0) && (column < 7))
    	{
    		for (i=1 ;i <= (7-column) ; i++)
    			content=content+"<TD align=center style='font-size:12px;bgcolor:#ffffff;'></TD>\n"
    			//content=content+"<TD align=center style='font-size:12px; font-family:tahoma,verdana; background-color:cccccc; vertical-align:top'>"+i+"</TD>\n"

    	}
    		//content=content + "</TR></TABLE>\n"


     // content = content + "<span style='position:absolute;top:0;left:" + (parseInt(calWidth,10) - 12) +
     //           ";background-color:#999999;color:white;font-size:8pt;font-weight:bold;cursor:hand;padding-left:2'" +
     //           " onclick='javascript:parent.closePopup();'>x</span>";
      	content=content +"</FORM></CENTER>\n"
   		content=content + "</TD></TR></TABLE>\n";
      //document.body.innerHTML = content;
      menuPopup = window.createPopup();
      menuPopup.document.body.innerHTML = content;
  }
}

function lostFocus()
{
    menuPopup.document.calendar.years.focus();
        //menuPopup.document.calendar.all['tempFocus'].focus();
        //menuPopup.document.calendar.tempFocus.focus();
    menuPopup.document.calendar.years.blur();
}

function closePopup()
{
    if(menuPopup != null)
    {
        menuPopup.hide();
    }
}
 menuDelay  = 50;  //delay before menu appears
 menuSpeed  = 5;   //speed which menu appears (lower=faster)
 menuOffset = 2;   //offset of menu from mouse pointer

 menuWidth  = 180;
 menuHeight = 180; //menu height

var scriptId;
var scriptType;

var menuXPos = 0;
var menuYPos = 0;

function callCalPop(scriptId,scriptType)
{
    if(window.navigator.appName == "Microsoft Internet Explorer" && window.navigator.appVersion.substring(window.navigator.appVersion.indexOf("MSIE") + 5, window.navigator.appVersion.indexOf("MSIE") + 8) >= 5.5)
    {
	}
	else
	{
    	return alert("Version of Internet Explorer must higher than 5.5");
	}

  this.scriptId   = scriptId;
	this.scriptType = scriptType;
	this.dtype      = scriptType;

	menuXPos = parseInt(window.event.clientX-80);
	menuYPos = parseInt(window.event.clientY);

	get_date();
    //showCalendarPopup(s_year,s_month,s_day);
}

function showCalendarPopup(sYear,sMonth,sDay)
{
    
	make_calendar(sYear,sMonth,sDay);

    menuXIncrement = menuWidth / menuSpeed;
    menuYIncrement = menuHeight / menuSpeed;

    menuTimer = setTimeout("openMenu(0,0)", menuDelay);

    return false;
}


function openMenu(height, width)
{
    iHeight = height;
    iWidth = width;


    if(iHeight < menuHeight)
    {
        menuTimer = setTimeout("openMenu(iHeight + menuYIncrement, iWidth + menuXIncrement)", 1);
    }
    else
    {
        menuPopup.show(menuXPos, menuYPos, iWidth, iHeight, document.body);
        clearTimeout(menuTimer);
    }
}

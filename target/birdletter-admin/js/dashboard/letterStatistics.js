function letterStatistics(val, val2, val3) {
	var searchDate = val;
	var searchType = val2;
	var title = val3;
	
	if (searchDate == "") {
		searchDate = today();
	}
	if (searchType == "daily") {
		statisticsService.letterStatistics(searchDate, "DAY", function(selList) {
			if (selList.length > 0) {
				for (var i = 0; i <selList.length; i++) {
					$(function () {
					    $('#letter_container').highcharts({
					        title: {
					            text: title + ' 편지생성 수',
					            x: -20 //center
					        },
					        subtitle: {
					            text: '검색 기준일 : ' + searchDate,
					            x: -20
					        },
					        xAxis: {
					            categories: [selList[0].date,selList[1].date,selList[2].date,selList[3].date,
					                         selList[4].date,selList[5].date,selList[6].date,selList[7].date,
					                         selList[8].date,selList[9].date,selList[10].date,selList[11].date,
					                         selList[12].date,selList[13].date
					                         ] 
					        },
					        yAxis: {
					            title: {
					                text: '개수'
					            },
					            plotLines: [{
					                value: 0,
					                width: 1,
					                color: '#808080'
					            }]
					        },
					        tooltip: {
					            valueSuffix: ''
					        },
					        legend: {
					            layout: 'vertical',
					            align: 'right',
					            verticalAlign: 'middle',
					            borderWidth: 0
					        },
					        credits: {
					            enabled: false
					        },
					        series: [{
					            name: '개인편지',
					            data: [
										selList[0].privateLetterCnt, selList[1].privateLetterCnt, selList[2].privateLetterCnt, selList[3].privateLetterCnt,
										selList[4].privateLetterCnt, selList[5].privateLetterCnt, selList[6].privateLetterCnt, selList[7].privateLetterCnt,
										selList[8].privateLetterCnt, selList[9].privateLetterCnt, selList[10].privateLetterCnt, selList[11].privateLetterCnt,
										selList[12].privateLetterCnt, selList[13].privateLetterCnt
					                   ]
					        } , {
					            name: '공개편지',
					            data: [selList[0].openLetterCnt, selList[1].openLetterCnt, selList[2].openLetterCnt, selList[3].openLetterCnt,
										selList[4].openLetterCnt, selList[5].openLetterCnt, selList[6].openLetterCnt, selList[7].openLetterCnt,
										selList[8].openLetterCnt, selList[9].openLetterCnt, selList[10].openLetterCnt, selList[11].openLetterCnt,
										selList[12].openLetterCnt, selList[13].openLetterCnt
										]
					        }, {
					            name: '총합',
					            data: [
										selList[0].total, selList[1].total, selList[2].total, selList[3].total,
										selList[4].total, selList[5].total, selList[6].total, selList[7].total,
										selList[8].total, selList[9].total, selList[10].total, selList[11].total,
										selList[12].total, selList[13].total
					                   ]
					        }]
					    });
					});
				}
			}
		});
	} else if (searchType == "weekly") {
		statisticsService.letterStatistics(searchDate, "WEEK", function(selList) {
			var year = getYear();
			if (selList.length > 0) {
				for (var i = 0; i <selList.length; i++) {
					if ((selList[i].length >= 0) &&  (selList[i].length >= 12)) {
						year = year+1;
					}
					$(function () {
					    $('#letter_container').highcharts({
					        title: {
					            text: title + ' 편지생성 수',
					            x: -20 //center
					        },
					        subtitle: {
					            text: '검색 기준일 : ' + searchDate,
					            x: -20
					        },
					        xAxis: {
					            categories: [getStartDayAndEndDay(year, selList[0].date), getStartDayAndEndDay(year, selList[1].date), 
					                         getStartDayAndEndDay(year, selList[2].date), getStartDayAndEndDay(year, selList[3].date),
					                         getStartDayAndEndDay(year, selList[4].date), getStartDayAndEndDay(year, selList[5].date),
					                         getStartDayAndEndDay(year, selList[6].date), getStartDayAndEndDay(year, selList[7].date),
					                         getStartDayAndEndDay(year, selList[8].date), getStartDayAndEndDay(year, selList[9].date),
					                         getStartDayAndEndDay(year, selList[10].date), getStartDayAndEndDay(year, selList[11].date),
					                         getStartDayAndEndDay(year, selList[12].date), getStartDayAndEndDay(year, selList[13].date),
					                         ] 
					        },
					        yAxis: {
					            title: {
					                text: '개수'
					            },
					            plotLines: [{
					                value: 0,
					                width: 1,
					                color: '#808080'
					            }]
					        },
					        tooltip: {
					            valueSuffix: ''
					        },
					        legend: {
					            layout: 'vertical',
					            align: 'right',
					            verticalAlign: 'middle',
					            borderWidth: 0
					        },
					        credits: {
					            enabled: false
					        },
					        series: [{
					            name: '개인편지',
					            data: [
										selList[0].privateLetterCnt, selList[1].privateLetterCnt, selList[2].privateLetterCnt, selList[3].privateLetterCnt,
										selList[4].privateLetterCnt, selList[5].privateLetterCnt, selList[6].privateLetterCnt, selList[7].privateLetterCnt,
										selList[8].privateLetterCnt, selList[9].privateLetterCnt, selList[10].privateLetterCnt, selList[11].privateLetterCnt,
										selList[12].privateLetterCnt, selList[13].privateLetterCnt
					                   ]
					        } , {
					            name: '공개편지',
					            data: [selList[0].openLetterCnt, selList[1].openLetterCnt, selList[2].openLetterCnt, selList[3].openLetterCnt,
										selList[4].openLetterCnt, selList[5].openLetterCnt, selList[6].openLetterCnt, selList[7].openLetterCnt,
										selList[8].openLetterCnt, selList[9].openLetterCnt, selList[10].openLetterCnt, selList[11].openLetterCnt,
										selList[12].openLetterCnt, selList[13].openLetterCnt
										]
					        }, {
					            name: '총합',
					            data: [
										selList[0].total, selList[1].total, selList[2].total, selList[3].total,
										selList[4].total, selList[5].total, selList[6].total, selList[7].total,
										selList[8].total, selList[9].total, selList[10].total, selList[11].total,
										selList[12].total, selList[13].total
					                   ]
					        }]
					    });
					});
				}
			}
		});
	} else if (searchType == "monthly") {
		var year = getSelectboxValue("sel_year");
		var month = getSelectboxValue("sel_month");
		if (year == "") {
			year = getYear();
		}
		if (month == "") {
			month = getMonth();
		}
		statisticsService.monthlyLetterStatistics(year, month, function(selList) {
			if (selList.length > 0) {
				for (var i = 0; i <selList.length; i++) {
					var date = new Array(selList[i].date);
					$(function () {
					    $('#letter_container').highcharts({
					        title: {
					            text: title + ' 편지생성 수',
					            x: -20 //center
					        },
					        subtitle: {
					        	text: '검색 기준일 : ' + year + "년 " + month +"월",
					            x: -20
					        },
					        xAxis: {
					       		 	categories: [selList[0].date,selList[1].date,selList[2].date,selList[3].date,
					                         selList[4].date,selList[5].date,selList[6].date,selList[7].date,
					                         selList[8].date,selList[9].date,selList[10].date,selList[11].date
					                         ]
					        },
					        yAxis: {
					            title: {
					                text: '개수'
					            },
					            plotLines: [{
					                value: 0,
					                width: 1,
					                color: '#808080'
					            }]
					        },
					        tooltip: {
					            valueSuffix: ''
					        },
					        legend: {
					            layout: 'vertical',
					            align: 'right',
					            verticalAlign: 'middle',
					            borderWidth: 0
					        },
					        credits: {
					            enabled: false
					        },
					        series: [{
					            name: '개인편지',
					            data: [
										selList[0].privateLetterCnt, selList[1].privateLetterCnt, selList[2].privateLetterCnt, selList[3].privateLetterCnt,
										selList[4].privateLetterCnt, selList[5].privateLetterCnt, selList[6].privateLetterCnt, selList[7].privateLetterCnt,
										selList[8].privateLetterCnt, selList[9].privateLetterCnt, selList[10].privateLetterCnt, selList[11].privateLetterCnt
					                   ]
					        } , {
					            name: '공개편지',
					            data: [selList[0].openLetterCnt, selList[1].openLetterCnt, selList[2].openLetterCnt, selList[3].openLetterCnt,
										selList[4].openLetterCnt, selList[5].openLetterCnt, selList[6].openLetterCnt, selList[7].openLetterCnt,
										selList[8].openLetterCnt, selList[9].openLetterCnt, selList[10].openLetterCnt, selList[11].openLetterCnt
										]
					        }, {
					            name: '총합',
					            data: [
										selList[0].total, selList[1].total, selList[2].total, selList[3].total,
										selList[4].total, selList[5].total, selList[6].total, selList[7].total,
										selList[8].total, selList[9].total, selList[10].total, selList[11].total
					                   ]
					        }]
					    });
					});
				}
			}
		});
	} else if (searchType == "year") {
		var year = getSelectboxValue("sel_year");
		if (year == "") {
			year = getYear();
		}
		statisticsService.yearLetterStatistics(year, getYear(), function(selList) {
			if (selList.length > 0) {
				var date = new Array();
				for (var i = 0; i <selList.length; i++) {
					date[i] = new Array(selList[i].date); 
					$(function () {
					    $('#letter_container').highcharts({
					        title: {
					            text: title + ' 편지생성 수',
					            x: -20 //center
					        },
					        subtitle: {
					            text: '검색 기준일 : ' + searchDate,
					            x: -20
					        },
					        xAxis: {
					       		 	categories: [selList[0].date, selList[1].date, 
					       		 	             selList[2].date, selList[3].date, selList[4].date
					                         ]
					        },
					        yAxis: {
					            title: {
					                text: '개수'
					            },
					            plotLines: [{
					                value: 0,
					                width: 1,
					                color: '#808080'
					            }]
					        },
					        tooltip: {
					            valueSuffix: ''
					        },
					        legend: {
					            layout: 'vertical',
					            align: 'right',
					            verticalAlign: 'middle',
					            borderWidth: 0
					        },
					        credits: {
					            enabled: false
					        },
					        series: [{
					            name: '개인편지',
					            data: [
										selList[0].privateLetterCnt, selList[1].privateLetterCnt, selList[2].privateLetterCnt, 
										selList[3].privateLetterCnt, selList[4].privateLetterCnt
					                   ]
					        } , {
					            name: '공개편지',
					            data: [selList[0].openLetterCnt, selList[1].openLetterCnt, selList[2].openLetterCnt, 
					                   selList[3].openLetterCnt, selList[4].openLetterCnt
										]
					        }, {
					            name: '총합',
					            data: [
										selList[0].total, selList[1].total, selList[2].total, 
										selList[3].total, selList[4].total
					                   ]
					        }]
					    });
					});
					}
				}
			});
		}
	}
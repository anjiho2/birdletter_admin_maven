function dauStatistics(val, val2, val3) {
	var searchDate = val;
	var searchType = val2;
	var title = val3;
	
	if (searchDate == "") {
		searchDate = today();
	}
	
	if (searchType == "daily") {
		statisticsService.dauStatistics(searchDate, "DAY", function(selList) {
			if (selList.length > 0) {
				for (var i = 0; i <selList.length; i++) {
					$(function () {
					    $('#dau_container').highcharts({
					        title: {
					            text: title + ' DAU',
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
					                text: '접속수'
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
					            name: '남성',
					            data: [
										selList[0].male, selList[1].male, selList[2].male, selList[3].male,
										selList[4].male, selList[5].male, selList[6].male, selList[7].male,
										selList[8].male, selList[9].male, selList[10].male, selList[11].male,
										selList[12].male, selList[13].male
					                   ]
					        } , {
					            name: '여성',
					            data: [selList[0].female, selList[1].female, selList[2].female, selList[3].female,
										selList[4].female, selList[5].female, selList[6].female, selList[7].female,
										selList[8].female, selList[9].female, selList[10].female, selList[11].female,
										selList[12].female, selList[13].female
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
		statisticsService.dauStatistics(searchDate, "WEEK", function(selList) {
			var year = getYear();
			if (selList.length > 0) {
				for (var i = 0; i <selList.length; i++) {
					if ((selList[i].length >= 0) &&  (selList[i].length >= 12)) {
						year = year+1;
					}
					$(function () {
					    $('#dau_container').highcharts({
					        title: {
					            text: title + ' DAU',
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
					                text: '접속수'
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
					            name: '남성',
					            data: [
										selList[0].male, selList[1].male, selList[2].male, selList[3].male,
										selList[4].male, selList[5].male, selList[6].male, selList[7].male,
										selList[8].male, selList[9].male, selList[10].male, selList[11].male,
										selList[12].male, selList[13].male
					                   ]
					        } , {
					            name: '여성',
					            data: [selList[0].female, selList[1].female, selList[2].female, selList[3].female,
										selList[4].female, selList[5].female, selList[6].female, selList[7].female,
										selList[8].female, selList[9].female, selList[10].female, selList[11].female,
										selList[12].female, selList[13].female
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
		statisticsService.monthlyDauStatistics(year, month, function(selList) {
			if (selList.length > 0) {
				for (var i = 0; i <selList.length; i++) {
					var date = new Array(selList[i].date);
					$(function () {
					    $('#dau_container').highcharts({
					        title: {
					            text: title + ' DAU',
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
					                text: '접속수'
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
					            name: '남성',
					            data: [
										selList[0].male, selList[1].male, selList[2].male, selList[3].male,
										selList[4].male, selList[5].male, selList[6].male, selList[7].male,
										selList[8].male, selList[9].male, selList[10].male, selList[11].male
					                   ]
					        } , {
					            name: '여성',
					            data: [selList[0].female, selList[1].female, selList[2].female, selList[3].female,
										selList[4].female, selList[5].female, selList[6].female, selList[7].female,
										selList[8].female, selList[9].female, selList[10].female, selList[11].female
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
		statisticsService.yearDauStatistics(year, getYear(), function(selList) {
			if (selList.length > 0) {
				var date = new Array();
				for (var i = 0; i <selList.length; i++) {
					date[i] = new Array(selList[i].date); 
					$(function () {
					    $('#dau_container').highcharts({
					        title: {
					            text: title + ' DAU',
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
					                text: '접속수'
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
					            name: '남성',
					            data: [
										selList[0].male, selList[1].male, selList[2].male, 
										selList[3].male, selList[4].male
					                   ]
					        } , {
					            name: '여성',
					            data: [selList[0].female, selList[1].female, selList[2].female, 
					                   selList[3].female, selList[4].female
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
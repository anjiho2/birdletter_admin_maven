function memberRegStatistics(val, val2, val3) {
	var searchDate = val;
	var searchType = val2;
	var title = val3;
	
	if (searchDate == "") {
		searchDate = today();
	}
	if (searchType == "daily") {
		statisticsService.memberRegStatistics(searchDate, "DAY", function(selList) {
			if (selList.length > 0) {
				var date = new Array();
				var male = new Array();
				var female = new Array();
				var total = new Array();
				for (var i = 0; i <selList.length; i++) {
					date[i] = selList[i].date;
					male[i] = selList[i].male;
					female[i] = selList[i].female;
					total[i] = selList[i].total;
				}
				$(function () {
				    $('#container2').highcharts({
				        title: {
				            text: title + ' 가입수',
				            x: -20 //center
				        },
				        subtitle: {
				            text: '검색 기준일 : ' + searchDate,
				            x: -20
				        },
				        xAxis: {
				            categories: [
				                         date[0], date[1], date[2], date[3], date[4],
				        	             date[5], date[6], date[7], date[8], date[9],
				        	             date[10], date[11], date[12], date[13]
				                         ] 
				        },
				        yAxis: {
				            title: {
				                text: '가입수'
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
									male[0], male[1], male[2], male[3], male[4],
									male[5], male[6], male[7], male[8], male[9],
									male[10], male[11], male[12], male[13]
				                   ]
				        } , {
				            name: '여성',
				            data: [
				                   female[0], female[1], female[2], female[3], female[4],
				                   female[5], female[6], female[7], female[8], female[9],
				                   female[10], female[11], female[12], female[13]
									]
				        }, {
				            name: '총합',
				            data: [
									total[0], total[1], total[2], total[3], total[4],
									total[5], total[6], total[7], total[8], total[9],
									total[10], total[11], total[12], total[13]
				                   ]
				        }]
				    });
				});
			}
		});
	} else if (searchType == "weekly") {
		statisticsService.memberRegStatistics(searchDate, "WEEK", function(selList) {
			var year = getYear();
			if (selList.length > 0) {
				for (var i = 0; i <selList.length; i++) {
					if ((selList[i].length >= 0) &&  (selList[i].length >= 12)) {
						year = year+1;
					}
					$(function () {
					    $('#container2').highcharts({
					        title: {
					            text: title + ' 가입수',
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
					                text: '가입수'
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
		statisticsService.monthlyMemberRegStatistics(year, month, function(selList) {
			if (selList.length > 0) {
				var date = new Array();
				var male = new Array();
				var female = new Array();
				var total = new Array();
				for (var i = 0; i <selList.length; i++) {
					date[i] = selList[i].date;
					male[i] = selList[i].male;
					female[i] = selList[i].female;
					total[i] = selList[i].total;
				}
				$(function () {
				    $('#container2').highcharts({
				        title: {
				            text: title + ' 가입수',
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
				                text: '가입수'
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
									male[0], male[1], male[2], male[3], male[4],
									male[5], male[6], male[7], male[8], male[9],
									male[10], male[11]
				                   ]
				        } , {
				            name: '여성',
				            data: [
									female[0], female[1], female[2], female[3], female[4],
									female[5], female[6], female[7], female[8], female[9],
									female[10], female[11]
									]
				        }, {
				            name: '총합',
				            data: [
									total[0], total[1], total[2], total[3], total[4],
									total[5], total[6], total[7], total[8], total[9],
									total[10], total[11]
				                   ]
				        }]
				    });
				});
			}
		});
	} else if (searchType == "year") {
		var year = getSelectboxValue("sel_year");
		if (year == "") {
			year = getYear();
		}
		statisticsService.yearMemberRegStatistics(year, getYear(), function(selList) {
			if (selList.length > 0) {
				var date = new Array();
				for (var i = 0; i <selList.length; i++) {
					date[i] = new Array(selList[i].date); 
					$(function () {
					    $('#container2').highcharts({
					        title: {
					            text: title + ' 가입수',
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
					                text: '가입수'
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
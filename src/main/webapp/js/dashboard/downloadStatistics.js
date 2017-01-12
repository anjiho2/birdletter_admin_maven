function downloadStatistics(val, val2, val3) {
	var searchDate = val;
	var searchType = val2;
	var title = val3;
	
	if (searchDate == "") {
		searchDate = today();
	}
	if (searchType == "daily") {
		statisticsService.downloadStatistics(searchDate, "DAY", function(selList) {
			if (selList.length > 0) {
				var date = new Array();
				var ios = new Array();
				var android = new Array();
				var total = new Array();
				for (var i = 0; i <selList.length; i++) {
					date[i] = selList[i].createDate;
					ios[i] = selList[i].ios;
					android[i] = selList[i].android;
					total[i] = selList[i].total;
				}
				$(function () {
				    $('#container').highcharts({
				        title: {
				            text: title + ' 다운로드수',
				            x: -20 //center
				        },
				        subtitle: {
				            text: '검색 기준일 : ' + searchDate,
				            x: -20
				        },
				        xAxis: {
				        	categories: [date[0], date[1], date[2], date[3], date[4],
				        	             date[5], date[6], date[7], date[8], date[9],
				        	             date[10], date[11], date[12], date[13]
				                         ] 
				        },
				        yAxis: {
				            title: {
				                text: '다운수'
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
				        credits: {
				            enabled: false
				        },
				        legend: {
				            layout: 'vertical',
				            align: 'right',
				            verticalAlign: 'middle',
				            borderWidth: 0
				        },
				        series: [{
				            name: '애플',
				            data: [
									ios[0], ios[1], ios[2], ios[3], ios[4],
									ios[5], ios[6], ios[7], ios[8], ios[9],
									ios[10], ios[11], ios[12], ios[13]
				                   ]
				        } , {
				            name: '안드로이드',
				            data: [android[0], android[1], android[2], android[3], android[4],
				                   android[5], android[6], android[7], android[8], android[9],
				                   android[10], android[11], android[12], android[13]
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
		statisticsService.downloadStatistics(searchDate, "WEEK", function(selList) {
			var year = getYear();
			if (selList.length > 0) {
				for (var i = 0; i <selList.length; i++) {
					if ((selList[i].length >= 0) &&  (selList[i].length >= 12)) {
						year = year+1;
					}
					$(function () {
					    $('#container').highcharts({
					        title: {
					            text: title + ' 다운로드수',
					            x: -20 //center
					        },
					        subtitle: {
					            text: '검색 기준일 : ' + searchDate,
					            x: -20
					        },
					        xAxis: {
					            categories: [getStartDayAndEndDay(year, selList[0].createDate), getStartDayAndEndDay(year, selList[1].createDate), 
					                         getStartDayAndEndDay(year, selList[2].createDate), getStartDayAndEndDay(year, selList[3].createDate),
					                         getStartDayAndEndDay(year, selList[4].createDate), getStartDayAndEndDay(year, selList[5].createDate),
					                         getStartDayAndEndDay(year, selList[6].createDate), getStartDayAndEndDay(year, selList[7].createDate),
					                         getStartDayAndEndDay(year, selList[8].createDate), getStartDayAndEndDay(year, selList[9].createDate),
					                         getStartDayAndEndDay(year, selList[10].createDate), getStartDayAndEndDay(year, selList[11].createDate),
					                         getStartDayAndEndDay(year, selList[12].createDate), getStartDayAndEndDay(year, selList[13].createDate),
					                         ] 
					        },
					        yAxis: {
					            title: {
					                text: '다운수'
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
					            name: '애플',
					            data: [
										selList[0].ios, selList[1].ios, selList[2].ios, selList[3].ios,
										selList[4].ios, selList[5].ios, selList[6].ios, selList[7].ios,
										selList[8].ios, selList[9].ios, selList[10].ios, selList[11].ios,
										selList[12].ios, selList[13].ios
					                   ]
					        } , {
					            name: '안드로이드',
					            data: [selList[0].android, selList[1].android, selList[2].android, selList[3].android,
										selList[4].android, selList[5].android, selList[6].android, selList[7].android,
										selList[8].android, selList[9].android, selList[10].android, selList[11].android,
										selList[12].android, selList[13].android
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
		statisticsService.monthlyDownloadStatistics(year, month, function(selList) {
			if (selList.length > 0) {
				var date = new Array();
				var ios = new Array();
				var android = new Array();
				var total = new Array();
				for (var i = 0; i <selList.length; i++) {
					date[i] = selList[i].createDate;
					ios[i] = selList[i].ios;
					android[i] = selList[i].android;
					total[i] = selList[i].total;
				}
				$(function () {
				    $('#container').highcharts({
				        title: {
				            text: title + ' 다운로드수',
				            x: -20 //center
				        },
				        subtitle: {
				        	text: '검색 기준일 : ' + year + "년 " + month +"월",
				            x: -20
				        },
				        xAxis: {
				       		 	categories: 
				       		 				[
				       		 				 date[0], date[1], date[2], date[3], date[4],
					        	             date[5], date[6], date[7], date[8], date[9],
					        	             date[10], date[11]
				       		 				] 
				        },
				        yAxis: {
				            title: {
				                text: '다운수'
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
				            name: '애플',
				            data: [
									ios[0], ios[1], ios[2], ios[3], 
									ios[4], ios[5], ios[6], ios[7], 
									ios[8], ios[9], ios[10], ios[11]
				                   ]
				        } , {
				            name: '안드로이드',
				            data: [
				                   android[0], android[1], android[2], android[3], 
				                   android[4], android[5], android[6], android[7], 
				                   android[8], android[9], android[10], android[11]
									]
				        }, {
				            name: '총합',
				            data: [
									total[0], total[1], total[2], total[3], 
									total[4], total[5], total[6], total[7], 
									total[8], total[9], total[10], total[11]
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
		statisticsService.yearDownloadStatistics(year, getYear(), function(selList) {
			if (selList.length > 0) {
				var date = new Array();
				var ios = new Array();
				var android = new Array();
				var total = new Array();
				for (var i = 0; i <selList.length; i++) {
					date[i] = selList[i].createDate;
					ios[i] = selList[i].ios;
					android[i] = selList[i].android;
					total[i] = selList[i].total;
				}
				$(function () {
				    $('#container').highcharts({
				        title: {
				            text: title + ' 다운로드수',
				            x: -20 //center
				        },
				        subtitle: {
				            text: '검색 기준일 : ' + searchDate,
				            x: -20
				        },
				        xAxis: {
				       		 	categories: [
				       		 	             date[0], date[1], date[2], date[3], date[4] 
				       		 	             ]
				        },
				        yAxis: {
				            title: {
				                text: '다운수'
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
				            name: '애플',
				            data: [ ios[0], ios[1], ios[2], ios[3], ios[4] ]
				        } , {
				            name: '안드로이드',
				            data: [ android[0], android[1], android[2], android[3],android[4] ]
				        }, {
				            name: '총합',
				            data: [	total[0], total[1], total[2], total[3], total[4] ]
				        }]
				    });
				});
			}
		});
	}
}
function arpuStatistics(val, val2, val3) {
	var searchDate = val;
	var searchType = val2;
	var title = val3;
	
	if (searchType == "daily") {
		var year = getSelectboxValue("sel_year");
		var month = getSelectboxValue("sel_month");
		if (searchDate != "") {
			var splitYear = searchDate.split("-");
			year = splitYear[0];
			var splitMonth = searchDate.split("-");
			month = gfn_leftZeroLeave(splitYear[1]);
		} else if (searchDate == "") {
			if (year == "" || year == undefined) {
				year = getYear();
			} 
			if (month == "" || month == undefined) {
				month = getMonth();
			}
			searchDate = year + "년 " + month + "월";
		}
		statisticsService.arpuStatistics(year, month, "", function(selList) {
			if (selList.length > 0) {
				for (var i = 0; i <selList.length; i++) {
					$(function () {
					    $('#arpu_container').highcharts({
					    	chart: {
					            type: 'line'
					        },
					        title: {
					            text: 'ARPU',
					        },
					        subtitle: {
					            text: '검색 기준일 : ' + searchDate,
					        },
					        xAxis: {
					            categories: [selList[0].date,selList[1].date,selList[2].date,selList[3].date,
					                         selList[4].date,selList[5].date,selList[6].date,selList[7].date,
					                         selList[8].date,selList[9].date,selList[10].date,selList[11].date
					                         ] 
					        },
					        yAxis: {
					            title: {
					                text: '금액(원)'
					            }
					        },
					        plotOptions: {
					            line: {
					                dataLabels: {
					                    enabled: true
					                },
					                enableMouseTracking: false
					            }
					        },
					        credits: {
					            enabled: false
					        },
					        series: [{
					            name: 'ARPU',
					            data: [
										Math.round(selList[0].cnt), Math.round(selList[1].cnt), Math.round(selList[2].cnt), Math.round(selList[3].cnt),
										Math.round(selList[4].cnt), Math.round(selList[5].cnt), Math.round(selList[6].cnt), Math.round(selList[7].cnt),
										Math.round(selList[8].cnt), Math.round(selList[9].cnt), Math.round(selList[10].cnt), Math.round(selList[11].cnt)
					                   ]
					        }]
					    });
					});
				}
			}
		});
	} else if (searchType == "weekly") {
		var year = getSelectboxValue("sel_year");
		var month = getSelectboxValue("sel_month");
		if (searchDate != "") {
			var splitYear = searchDate.split("-");
			year = splitYear[0];
			var splitMonth = searchDate.split("-");
			month = gfn_leftZeroLeave(splitYear[1]);
		} else if (searchDate == "") {
			if (year == "" || year == undefined) {
				year = getYear();
			} 
			if (month == "" || month == undefined) {
				month = getMonth();
			}
			searchDate = year + "년 " + month + "월";
		}
		statisticsService.arpuStatistics(year, month, "", function(selList) {
			if (selList.length > 0) {
				for (var i = 0; i <selList.length; i++) {
					$(function () {
					    $('#arpu_container').highcharts({
					    	chart: {
					            type: 'line'
					        },
					        title: {
					            text: 'ARPU',
					        },
					        subtitle: {
					            text: '검색 기준일 : ' + searchDate,
					        },
					        xAxis: {
					            categories: [selList[0].date,selList[1].date,selList[2].date,selList[3].date,
					                         selList[4].date,selList[5].date,selList[6].date,selList[7].date,
					                         selList[8].date,selList[9].date,selList[10].date,selList[11].date
					                         ] 
					        },
					        yAxis: {
					            title: {
					                text: '금액(원)'
					            }
					        },
					        plotOptions: {
					            line: {
					                dataLabels: {
					                    enabled: true
					                },
					                enableMouseTracking: false
					            }
					        },
					        credits: {
					            enabled: false
					        },
					        series: [{
					            name: 'ARPU',
					            data: [
										Math.round(selList[0].cnt), Math.round(selList[1].cnt), Math.round(selList[2].cnt), Math.round(selList[3].cnt),
										Math.round(selList[4].cnt), Math.round(selList[5].cnt), Math.round(selList[6].cnt), Math.round(selList[7].cnt),
										Math.round(selList[8].cnt), Math.round(selList[9].cnt), Math.round(selList[10].cnt), Math.round(selList[11].cnt)
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
		statisticsService.arpuStatistics(year, month, "", function(selList) {
			if (selList.length > 0) {
				for (var i = 0; i <selList.length; i++) {
					$(function () {
					    $('#arpu_container').highcharts({
					    	chart: {
					            type: 'line'
					        },
					        title: {
					            text: 'ARPU',
					        },
					        subtitle: {
					        	text: '검색 기준일 : ' + year + "년 " + month +"월",
					        },
					        xAxis: {
					            categories: [selList[0].date,selList[1].date,selList[2].date,selList[3].date,
					                         selList[4].date,selList[5].date,selList[6].date,selList[7].date,
					                         selList[8].date,selList[9].date,selList[10].date,selList[11].date
					                         ] 
					        },
					        yAxis: {
					            title: {
					                text: '금액(원)'
					            }
					        },
					        plotOptions: {
					            line: {
					                dataLabels: {
					                    enabled: true
					                },
					                enableMouseTracking: false
					            }
					        },
					        credits: {
					            enabled: false
					        },
					        series: [{
					            name: 'ARPU',
					            data: [
										Math.round(selList[0].cnt), Math.round(selList[1].cnt), Math.round(selList[2].cnt), Math.round(selList[3].cnt),
										Math.round(selList[4].cnt), Math.round(selList[5].cnt), Math.round(selList[6].cnt), Math.round(selList[7].cnt),
										Math.round(selList[8].cnt), Math.round(selList[9].cnt), Math.round(selList[10].cnt), Math.round(selList[11].cnt)
					                   ]
					        }]
					    });
					});
				}
			}
		});
	} else if (searchType == "year") {
		var year = getSelectboxValue("sel_year");
		var month = getSelectboxValue("sel_month");
		if (year == "") {
			year = getYear();
		}
		if (month == "") {
			month = getMonth();
		}
		statisticsService.arpuStatistics(year, month, "YEAR", function(selList) {
			if (selList.length > 0) {
				for (var i = 0; i <selList.length; i++) {
					$(function () {
					    $('#arpu_container').highcharts({
					    	chart: {
					            type: 'line'
					        },
					        title: {
					            text: 'ARPU',
					        },
					        subtitle: {
					        	text: '검색 기준일 : ' + year+"년" ,
					        },
					        xAxis: {
					            categories: [selList[0].date,selList[1].date,selList[2].date,selList[3].date,
					                         selList[4].date,selList[5].date,selList[6].date,selList[7].date,
					                         selList[8].date,selList[9].date,selList[10].date,selList[11].date
					                         ] 
					        },
					        yAxis: {
					            title: {
					                text: '금액(원)'
					            }
					        },
					        plotOptions: {
					            line: {
					                dataLabels: {
					                    enabled: true
					                },
					                enableMouseTracking: false
					            }
					        },
					        credits: {
					            enabled: false
					        },
					        series: [{
					            name: 'ARPU',
					            data: [
										Math.round(selList[0].cnt), Math.round(selList[1].cnt), Math.round(selList[2].cnt), Math.round(selList[3].cnt),
										Math.round(selList[4].cnt), Math.round(selList[5].cnt), Math.round(selList[6].cnt), Math.round(selList[7].cnt),
										Math.round(selList[8].cnt), Math.round(selList[9].cnt), Math.round(selList[10].cnt), Math.round(selList[11].cnt)
					                   ]
					        }]
					    });
					});
				}
			}
		});
		}
	}
function mauStatistics(val, val2, val3) {
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
		statisticsService.mauStatistics(year, month, "", function(selList) {
			if (selList.length > 0) {
				for (var i = 0; i <selList.length; i++) {
					$(function () {
					    $('#mau_container').highcharts({
					        title: {
					        	 text: 'MAU',
						            x: -20 //center
					        },
					        subtitle: {
					            text: '검색 기준일 : ' + searchDate,
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
					                text: '접속횟수 '
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
		statisticsService.mauStatistics(year, month, "", function(selList) {
			if (selList.length > 0) {
				for (var i = 0; i <selList.length; i++) {
					$(function () {
					    $('#mau_container').highcharts({
					        title: {
					        	 text: 'MAU',
						            x: -20 //center
					        },
					        subtitle: {
					            text: '검색 기준일 : ' + searchDate,
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
					                text: '접속횟수'
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
	} else if (searchType == "monthly") {
		var year = getSelectboxValue("sel_year");
		var month = getSelectboxValue("sel_month");
		if (year == "") {
			year = getYear();
		}
		if (month == "") {
			month = getMonth();
		}
		statisticsService.mauStatistics(year, month, "", function(selList) {
			if (selList.length > 0) {
				for (var i = 0; i <selList.length; i++) {
					$(function () {
					    $('#mau_container').highcharts({
					        title: {
					        	 text: 'MAU',
						            x: -20 //center
					        },
					        subtitle: {
					            text: '검색 기준일 : ' + year+"년"+month+"월",
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
					                text: '접속횟수'
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
		var month = getSelectboxValue("sel_month");
		if (year == "") {
			year = getYear();
		}
		if (month == "") {
			month = getMonth();
		}
		statisticsService.mauStatistics(year, month, "YEAR", function(selList) {
			if (selList.length > 0) {
				for (var i = 0; i <selList.length; i++) {
					$(function () {
					    $('#mau_container').highcharts({
					        title: {
					        	 text: 'MAU',
						            x: -20 //center
					        },
					        subtitle: {
					            text: '검색 기준일 : ' + year+"년",
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
					                text: '접속횟수'
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
		}
	}
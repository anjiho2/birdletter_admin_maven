function authStatisticsByAge(val, val2, val3) {
	var searchDate = val;
	var searchType = val2;
	var title = val3;
	
	if (searchDate == "") {
		searchDate = today();
	}
	
	if (searchType == "daily") {
		statisticsService.authStatisticsByAge(searchDate, searchType, function(selList) {
			if (selList.length > 0) {
				var cnt = new Array();
				for (var i = 0; i <selList.length; i++) {
					cnt[i] = selList[i].cnt;
				}
				$(function () {
				    $('#age_container').highcharts({
				        chart: {
				            type: 'pie',
				            options3d: {
				                enabled: true,
				                alpha: 45
				            }
				        },
				        title: {
				            text: '일별 연령별 사용자 백분율'
				        },
				        subtitle: {
				            text: '검색 기준일 : ' + searchDate
				        },
				        plotOptions: {
				            pie: {
				                innerSize: 100,
				                depth: 45
				            }
				        },
				        credits: {
				            enabled: false
				        },
				        series: [{
				            name: '사용자수',
				            data: [
				                ['10대이하', cnt[0]],
				                ['10대', cnt[1]],
				                ['20대', cnt[2]],
				                ['30대', cnt[3]],
				                ['40대', cnt[4]],
				                ['50대', cnt[5]],
				                ['60대이상', cnt[6]]
				            ]
				        }]
				    });
				    $("#loader").hide();
				});
			}
		});
	} else if (searchType == "weekly") {
		statisticsService.authStatisticsByAge(searchDate, searchType, function(selList) {
			var year = getYear();
			if (selList.length > 0) {
				for (var i = 0; i <selList.length; i++) {
					if ((selList[i].length >= 0) &&  (selList[i].length >= 12)) {
						year = year+1;
					}
					$(function () {
					    $('#age_container').highcharts({
					        chart: {
					            type: 'pie',
					            options3d: {
					                enabled: true,
					                alpha: 45
					            }
					        },
					        title: {
					            text: '주별 연령별 사용자 백분율'
					        },
					        subtitle: {
					            text: '검색 기준일 : ' + searchDate
					        },
					        plotOptions: {
					            pie: {
					                innerSize: 100,
					                depth: 45
					            }
					        },
					        credits: {
					            enabled: false
					        },
					        series: [{
					        	name: '사용자수',
					            data: [
					                ['10대이하', selList[0].cnt],
					                ['10대', selList[1].cnt],
					                ['20대', selList[2].cnt],
					                ['30대', selList[3].cnt],
					                ['40대', selList[4].cnt],
					                ['50대', selList[5].cnt],
					                ['60대이상', selList[6].cnt],
					            ]
					        }]
					    });
					    $("#loader").hide();
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
		
		if (month <= 9) {
			month = "0"+month;
		}
		var searchDate = year + "-" + month;
		statisticsService.authStatisticsByAge(searchDate, searchType, function(selList) {
			if (selList.length > 0) {
				var cnt = new Array();
				for (var i = 0; i <selList.length; i++) {
					cnt[i] = selList[i].cnt;
				}
				$(function () {
				    $('#age_container').highcharts({
				        chart: {
				            type: 'pie',
				            options3d: {
				                enabled: true,
				                alpha: 45
				            }
				        },
				        title: {
				            text: '월별 연령별 사용자 백분율'
				        },
				        subtitle: {
				            text: '검색 기준일 : ' + searchDate
				        },
				        plotOptions: {
				            pie: {
				                innerSize: 100,
				                depth: 45
				            }
				        },
				        credits: {
				            enabled: false
				        },
				        series: [{
				        	name: '사용자수',
				            data: [
									['10대이하', selList[0].cnt],
									['10대', selList[1].cnt],
									['20대', selList[2].cnt],
									['30대', selList[3].cnt],
									['40대', selList[4].cnt],
									['50대', selList[5].cnt],
									['60대이상', selList[6].cnt]
				            ]
				        }]
				    });
				    $("#loader").hide();
				});
			}
		});
	} else if (searchType == "year") {
		var year = getSelectboxValue("sel_year");
		if (year == "") {
			year = getYear();
		}
		statisticsService.authStatisticsByAge(year, searchType, function(selList) {
			if (selList.length > 0) {
				var cnt = new Array();
				for (var i = 0; i <selList.length; i++) {
					cnt[i] = selList[i].cnt;
				}
				$(function () {
				    $('#age_container').highcharts({
				        chart: {
				            type: 'pie',
				            options3d: {
				                enabled: true,
				                alpha: 45
				            }
				        },
				        title: {
				            text: '연별 연령별 사용자 백분율'
				        },
				        subtitle: {
				            text: '검색 기준일 : ' + year +"년"
				        },
				        plotOptions: {
				            pie: {
				                innerSize: 100,
				                depth: 45
				            }
				        },
				        credits: {
				            enabled: false
				        },
				        series: [{
				        	name: '사용자수',
				            data: [
				                   ['10대이하', selList[0].cnt],
				                   ['10대', selList[1].cnt],
				                   ['20대', selList[2].cnt],
				                   ['30대', selList[3].cnt],
				                   ['40대', selList[4].cnt],
				                   ['50대', selList[5].cnt],
				                   ['60대이상', selList[6].cnt]
				            ]
				        }]
				    });
				    $("#loader").hide();
				});
			}
		});
	}
}
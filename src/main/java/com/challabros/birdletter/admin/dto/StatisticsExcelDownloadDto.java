package com.challabros.birdletter.admin.dto;

import java.util.HashMap;
import java.util.List;

import lombok.Data;

@Data
public class StatisticsExcelDownloadDto {
	private List<DownloadCountInfoDto>downloadList;
	
	private List<MemberStatisticsDto>memberRegStatistics;
	
	private List<MemberStatisticsDto>dauStatistics;
	
	private List<MemberStatisticsDto>mauStatistics;
	
	private List<DateCountDto>arpuStatistics;
	
	private List<DateCountDto>arppuStatistics;
	
	private List<AuthStatisticsDto>ageStatistics;
	
	private List<LetterStatisticsDto>letterStatistics;
	
	public StatisticsExcelDownloadDto() {}
	
	public StatisticsExcelDownloadDto(List<DownloadCountInfoDto>downloadList,
									List<MemberStatisticsDto>memberRegStatistics,
									List<MemberStatisticsDto>dauStatistics,
									List<MemberStatisticsDto>mauStatistics,
									List<DateCountDto>arpuStatistics,
									List<DateCountDto>arppuStatistics,
									List<AuthStatisticsDto> ageStatistics,
									List<LetterStatisticsDto>letterStatistics
									
			) {
				this.downloadList = downloadList;
				this.memberRegStatistics = memberRegStatistics;
				this.dauStatistics = dauStatistics;
				this.mauStatistics = mauStatistics;
				this.arpuStatistics = arpuStatistics;
				this.arppuStatistics = arppuStatistics;
				this.ageStatistics = ageStatistics;
				this.letterStatistics = letterStatistics;
	}
}
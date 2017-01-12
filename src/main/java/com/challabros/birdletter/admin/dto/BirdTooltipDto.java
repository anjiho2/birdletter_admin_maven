package com.challabros.birdletter.admin.dto;

import lombok.Data;

@Data
public class BirdTooltipDto {
	private int rowNum;
	private int idx;
	private String firstTooltip;
	private String secondTooltip;
	private int regularYn;
	private String startDate;
	private String endDate;
	private int orderYn;
	private int orderPriority;
	private int viewYn;
	private String createDate;
}

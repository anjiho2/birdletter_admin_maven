package com.challabros.birdletter.admin.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.web.servlet.view.document.AbstractExcelView;

import com.challabros.birdletter.admin.define.datasource.CornEnum;
import com.challabros.birdletter.admin.dto.AuthStatisticsDto;
import com.challabros.birdletter.admin.dto.CoinBuyLogDto;
import com.challabros.birdletter.admin.dto.CornSaleListDto;
import com.challabros.birdletter.admin.dto.DateCountDto;
import com.challabros.birdletter.admin.dto.DormantUserDto;
import com.challabros.birdletter.admin.dto.DownloadCountInfoDto;
import com.challabros.birdletter.admin.dto.GatchaUseLogDto;
import com.challabros.birdletter.admin.dto.GiftBoxListDomainDto;
import com.challabros.birdletter.admin.dto.ItemBuyRankDto;
import com.challabros.birdletter.admin.dto.LetterStatisticsDto;
import com.challabros.birdletter.admin.dto.MemberStatisticsDto;
import com.challabros.birdletter.admin.dto.NestItemBuyLogDto;
import com.challabros.birdletter.admin.dto.ProductListDto;
import com.challabros.birdletter.admin.dto.StatisticsExcelDownloadDto;
import com.challabros.birdletter.admin.dto.UserProductBuyLogDto;
import com.challabros.birdletter.admin.dto.UserProfileDto;
import com.challabros.birdletter.admin.util.BirdAdminUtil;
import com.challabros.birdletter.admin.util.DateUtils;
import com.challabros.birdletter.admin.util.StringUtil;
import com.challabros.birdletter.admin.util.Value;

public class ExcelService extends AbstractExcelView {
	
	private final String DATA_LIST = "dataList";
	
	private final int columnWith = 256 * 30;
	
	private final String WEEKLY = "weekly";

	@SuppressWarnings({ "unchecked" })
	@Override
	protected void buildExcelDocument(Map<String, Object> model,
			HSSFWorkbook workbook, HttpServletRequest req, HttpServletResponse res)
			throws Exception {
		// TODO Auto-generated method stub
		String fileName = (String)model.get("fileName");
		fileName = new String(fileName.getBytes("UTF-8"), "ISO-8859-1");
		res.setHeader("Content-Disposition", "attachment; filename=\"" + fileName + "\";");
		res.setHeader("Content-Transfer-Encoding", "binary");
		
		HSSFSheet sheet = null;
		if (!"dashboardStatistics".equals((String)model.get("excelContent"))) {
			sheet = createFirstSheet(workbook, model);
			String[] topMenus = (String[])model.get("topMenus");
			createColumnLabel(sheet, topMenus);
		}
		String excelContent = (String)model.get("excelContent");
		List<String[]> topMenuArr = (List<String[]>)model.get("topMenusArr");
		List<String> sheetNameArr = (List<String>)model.get("sheetNameArr");
		
		switch (excelContent) {
		case "userList" :
			userListExcelDownload((List<UserProfileDto>)model.get(DATA_LIST), sheet);
			break;
		case "itemSaleList" :
			itemSaleListExcelDownload((List<UserProductBuyLogDto>)model.get(DATA_LIST), sheet);
			break;
		case "itemSaleRankList" : 
			itemSaleRankListExcelDownload((List<ItemBuyRankDto>)model.get(DATA_LIST), sheet);
			break;
		case "userPopcornBuyList" :
			userPopcornBuyListExcelDownload((List<CoinBuyLogDto>)model.get(DATA_LIST), sheet);
			break;
		case "userItemBuyList" : 
			userItemBuyListExcelDownload((List<UserProductBuyLogDto>)model.get(DATA_LIST), sheet);
			break;
		case "storeList" :
			storeListExcelDownload((List<ProductListDto>)model.get(DATA_LIST), sheet);
			break;
		case "cornSaleList" :
			cornSaleListExcelDownload((List<CornSaleListDto>)model.get(DATA_LIST), sheet);
			break;
		case "dashboardStatistics":
			dashboardStatisticsExcelDownload((StatisticsExcelDownloadDto)model.get(DATA_LIST), sheetNameArr, topMenuArr, workbook, model);
			break;
		case "giftBoxSendList" :
			giftBoxSendListExcelDownload((List<GiftBoxListDomainDto>)model.get(DATA_LIST), sheet);
			break;
		case "dormantUserList" :
			dormantUserListExcelDownload((List<DormantUserDto>)model.get(DATA_LIST), sheet);
			break;
		case "gatchaUseList" :
			gatchaUseListExcelDownload((List<GatchaUseLogDto>)model.get(DATA_LIST), sheet);
			break;
		case "nestItemUseList" :
			nestItemBuyListExcelDownload((List<NestItemBuyLogDto>)model.get(DATA_LIST), sheet);
			break;
		case "retension" :
			retension((List<List<HashMap<String, Object>>>)model.get(DATA_LIST), sheet);
			break;	
		default:
			break;
		}
	}
	
	private void userListExcelDownload(List<UserProfileDto> dataList, HSSFSheet sheet) {
		if (dataList.size() > 0) {
			int i=0;
			for (UserProfileDto userProfileDto : dataList) {
				HSSFRow row = sheet.createRow(i+1);
				row.createCell(0).setCellValue(userProfileDto.getUserName());
				row.createCell(1).setCellValue(userProfileDto.getPhoneNumber());
				row.createCell(2).setCellValue(userProfileDto.getAge());
				row.createCell(3).setCellValue(userProfileDto.getGender());
				i++;
			}
		}
	}
	
	private void itemSaleListExcelDownload(List<UserProductBuyLogDto>dataList, HSSFSheet sheet) {
		if (dataList.size() > 0) {
			int i=0;
			for (UserProductBuyLogDto dto : dataList) {
				HSSFRow row = sheet.createRow(i+1);
				row.createCell(0).setCellValue(dto.getPhoneNumber());
				row.createCell(1).setCellValue(dto.getProductCode());
				row.createCell(2).setCellValue(dto.getProductName());
				row.createCell(3).setCellValue(dto.getCornPrice());
				row.createCell(4).setCellValue(dto.getPopcornPrice());
				row.createCell(5).setCellValue(dto.getCreateDate2());
				i++;
			}
		}
	}
	
	private void itemSaleRankListExcelDownload(List<ItemBuyRankDto>dataList, HSSFSheet sheet) {
		if (dataList.size() > 0) {
			int i=0;
			for (ItemBuyRankDto dto : dataList) {
				HSSFRow row = sheet.createRow(i+1);
				row.createCell(0).setCellValue(dto.getRowNum());
				row.createCell(1).setCellValue(dto.getProductName());
				row.createCell(2).setCellValue(dto.getCnt() + "건");
				i++;
			}
		}
	}
	
	private void userPopcornBuyListExcelDownload(List<CoinBuyLogDto>dataList, HSSFSheet sheet) {
		if (dataList.size() > 0) {
			int i=0;
			for (CoinBuyLogDto dto : dataList) {
				HSSFRow row = sheet.createRow(i + 1);
				row.createCell(0).setCellValue(dto.getPopcornName());
				row.createCell(1).setCellValue(dto.getPopcornPoint());
				row.createCell(2).setCellValue(dto.getCreateDate());
				i++;
			}
		}
	}
	
	private void userItemBuyListExcelDownload(List<UserProductBuyLogDto>dataList, HSSFSheet sheet) {
		if (dataList.size() > 0) {
			int i=0;
			for (UserProductBuyLogDto dto : dataList) {
				HSSFRow row = sheet.createRow(i + 1);
				row.createCell(0).setCellValue(dto.getProductCode());
				row.createCell(1).setCellValue(dto.getProductName());
				row.createCell(2).setCellValue(dto.getCornPrice());
				row.createCell(3).setCellValue(dto.getPopcornPrice());
				row.createCell(4).setCellValue(dto.getCreateDate());
				i++;
			}
		}
	}
	
	public void storeListExcelDownload(List<ProductListDto>dataList, HSSFSheet sheet) {
		if (dataList.size() > 0) {
			int i=0;
			for (ProductListDto dto : dataList) {
				HSSFRow row = sheet.createRow(i + 1);
				row.createCell(0).setCellValue(dto.getProductCode());
				row.createCell(1).setCellValue(dto.getProductName());
				row.createCell(2).setCellValue(dto.getItemCode());
				row.createCell(3).setCellValue(dto.getCornPrice());
				row.createCell(4).setCellValue(dto.getPopcornPrice());
				i++;
			}
		}
	}
	
	private void cornSaleListExcelDownload(List<CornSaleListDto>dataList, HSSFSheet sheet) {
		if (dataList.size() > 0) {
			int i=0;
			for (CornSaleListDto dto : dataList) {
				HSSFRow row = sheet.createRow(i + 1);
				row.createCell(0).setCellValue(dto.getCreateDate());
				row.createCell(1).setCellValue(dto.getUserName());
				row.createCell(2).setCellValue(dto.getPhoneNumber());
				row.createCell(3).setCellValue(CornEnum.getCornPrice(dto.getCornPoint()));
				i++;
			}
		}
	}
	
	private void dashboardStatisticsExcelDownload(StatisticsExcelDownloadDto dto, List<String>sheetNameArr,
			List<String[]>topMenuArr, HSSFWorkbook workbook, Map<String, Object>model) {
		if (dto != null) {
			if (dto.getDownloadList() != null) {	//다운로드 
				HSSFSheet sheet = workbook.createSheet(sheetNameArr.get(0));
				workbook.setSheetName(0, sheetNameArr.get(0));
				sheet.setColumnWidth(1, columnWith);
				String[] menu = topMenuArr.get(0);
				createColumnLabel(sheet, menu);
				
				int i=0;
				for (DownloadCountInfoDto downloadCountInfoDto : dto.getDownloadList()) {
					HSSFRow row = sheet.createRow(i+1);
					if ("weekly".equals((String)model.get("type"))) {
						String startDate = DateUtils.getDateByStartWeek(Integer.parseInt(downloadCountInfoDto.getCreateDate().toString()));
						String endDate = DateUtils.getDateByEndWeek(Integer.parseInt(downloadCountInfoDto.getCreateDate().toString()));
						String date = startDate + "~" + endDate;
						row.createCell(0).setCellValue(date);
					} else {
						row.createCell(0).setCellValue(downloadCountInfoDto.getCreateDate().toString());
					}
					row.createCell(1).setCellValue(downloadCountInfoDto.getIos());
					row.createCell(2).setCellValue(downloadCountInfoDto.getAndroid());
					row.createCell(3).setCellValue(downloadCountInfoDto.getTotal());
					i++;
				}
			}
			if (dto.getMemberRegStatistics() != null) {	// 가입
				HSSFSheet sheet = workbook.createSheet(sheetNameArr.get(1));
				workbook.setSheetName(1, sheetNameArr.get(1));;
				sheet.setColumnWidth(1, columnWith);
				String[] menu = topMenuArr.get(1);
				createColumnLabel(sheet, menu);
				
				int i=0;
				for (MemberStatisticsDto memberStatisticsDto : dto.getMemberRegStatistics()) {
					HSSFRow row = sheet.createRow(i+1);
					if (WEEKLY.equals((String)model.get("type"))) {
						String startDate = DateUtils.getDateByStartWeek(Integer.parseInt(memberStatisticsDto.getDate().toString()));
						String endDate = DateUtils.getDateByEndWeek(Integer.parseInt(memberStatisticsDto.getDate().toString()));
						String date = startDate + "~" + endDate;
						row.createCell(0).setCellValue(date);
					} else {
						row.createCell(0).setCellValue(memberStatisticsDto.getDate());
					}
					row.createCell(1).setCellValue(memberStatisticsDto.getMale());
					row.createCell(2).setCellValue(memberStatisticsDto.getFemale());
					row.createCell(3).setCellValue(memberStatisticsDto.getTotal());
					i++;
				}
			}
			if (dto.getDauStatistics() != null) {	//DAU
				HSSFSheet sheet = workbook.createSheet(sheetNameArr.get(2));
				workbook.setSheetName(2, sheetNameArr.get(2));;
				sheet.setColumnWidth(1, columnWith);
				String[] menu = topMenuArr.get(2);
				createColumnLabel(sheet, menu);
				
				int i=0;
				for (MemberStatisticsDto memberStatisticsDto : dto.getDauStatistics()) {
					HSSFRow row = sheet.createRow(i+1);
					if (WEEKLY.equals((String)model.get("type"))) {
						String startDate = DateUtils.getDateByStartWeek(Integer.parseInt(memberStatisticsDto.getDate().toString()));
						String endDate = DateUtils.getDateByEndWeek(Integer.parseInt(memberStatisticsDto.getDate().toString()));
						String date = startDate + "~" + endDate;
						row.createCell(0).setCellValue(date);
					} else {
						row.createCell(0).setCellValue(memberStatisticsDto.getDate());
					}
					row.createCell(1).setCellValue(memberStatisticsDto.getMale());
					row.createCell(2).setCellValue(memberStatisticsDto.getFemale());
					row.createCell(3).setCellValue(memberStatisticsDto.getTotal());
					i++;
				}
			}
			if (dto.getMauStatistics() != null) {	//MAU
				HSSFSheet sheet = workbook.createSheet(sheetNameArr.get(3));
				workbook.setSheetName(3, sheetNameArr.get(3));;
				sheet.setColumnWidth(1, columnWith);
				String[] menu = topMenuArr.get(2);
				createColumnLabel(sheet, menu);
				
				int i=0;
				for (MemberStatisticsDto memberStatisticsDto : dto.getMauStatistics()) {
					HSSFRow row = sheet.createRow(i+1);
					row.createCell(0).setCellValue(memberStatisticsDto.getDate());
					row.createCell(1).setCellValue(memberStatisticsDto.getMale());
					row.createCell(2).setCellValue(memberStatisticsDto.getFemale());
					row.createCell(3).setCellValue(memberStatisticsDto.getTotal());
					i++;
				}
			}
			if (dto.getArpuStatistics() != null) {	//ARPU
				HSSFSheet sheet = workbook.createSheet(sheetNameArr.get(4));
				workbook.setSheetName(4, sheetNameArr.get(4));;
				sheet.setColumnWidth(1, columnWith);
				String[] menu = topMenuArr.get(3);
				createColumnLabel(sheet, menu);
				
				int i=0;
				for (DateCountDto dateCountDto : dto.getArpuStatistics()) {
					HSSFRow row = sheet.createRow(i+1);
					row.createCell(0).setCellValue(dateCountDto.getDate());
					row.createCell(1).setCellValue(dateCountDto.getCnt());
					i++;
				}
			}
			if (dto.getArppuStatistics() != null) {	//ARPPU
				HSSFSheet sheet = workbook.createSheet(sheetNameArr.get(5));
				workbook.setSheetName(5, sheetNameArr.get(5));;
				sheet.setColumnWidth(1, columnWith);
				String[] menu = topMenuArr.get(4);
				createColumnLabel(sheet, menu);
				
				int i=0;
				for (DateCountDto dateCountDto : dto.getArppuStatistics()) {
					HSSFRow row = sheet.createRow(i+1);
					row.createCell(0).setCellValue(dateCountDto.getDate());
					row.createCell(1).setCellValue(dateCountDto.getCnt());
					i++;
				}
			}
			if (dto.getAgeStatistics() != null) {	//연령별 사용자 백분율
				HSSFSheet sheet = workbook.createSheet(sheetNameArr.get(6));
				workbook.setSheetName(6, sheetNameArr.get(6));
				sheet.setColumnWidth(1, columnWith);
				String[] menu = topMenuArr.get(5);
				createColumnLabel(sheet, menu);
				
				int i=0;
				for (AuthStatisticsDto authStatisticsDto : dto.getAgeStatistics()) {
					HSSFRow row = sheet.createRow(i+1);
					String ages = "";
					if (authStatisticsDto.getAge() == 0) ages = "10대이하";
					else if (authStatisticsDto.getAge() == 1) ages = "10대";
					else if (authStatisticsDto.getAge() == 2) ages = "20대";
					else if (authStatisticsDto.getAge() == 3) ages = "30대";
					else if (authStatisticsDto.getAge() == 4) ages = "40대";
					else if (authStatisticsDto.getAge() == 5) ages = "50대";
					else if (authStatisticsDto.getAge() == 6) ages = "60대";
					
					row.createCell(0).setCellValue(ages);
					row.createCell(1).setCellValue(authStatisticsDto.getCnt());
					i++;
				}
			}
			if (dto.getLetterStatistics() != null) {	//편지 생성수
				HSSFSheet sheet = workbook.createSheet(sheetNameArr.get(7));
				workbook.setSheetName(7, sheetNameArr.get(7));
				sheet.setColumnWidth(1, columnWith);
				String[] menu = topMenuArr.get(6);
				createColumnLabel(sheet, menu);
				
				int i=0;
				for (LetterStatisticsDto letterStatisticsDto : dto.getLetterStatistics()) {
					HSSFRow row = sheet.createRow(i+1);
					if (WEEKLY.equals((String)model.get("type"))) {
						String startDate = DateUtils.getDateByStartWeek(Integer.parseInt(letterStatisticsDto.getDate().toString()));
						String endDate = DateUtils.getDateByEndWeek(Integer.parseInt(letterStatisticsDto.getDate().toString()));
						String date = startDate + "~" + endDate;
						row.createCell(0).setCellValue(date);
					} else {
						row.createCell(0).setCellValue(letterStatisticsDto.getDate());	
					}
					row.createCell(1).setCellValue(letterStatisticsDto.getPrivateLetterCnt());
					row.createCell(2).setCellValue(letterStatisticsDto.getOpenLetterCnt());
					row.createCell(3).setCellValue(letterStatisticsDto.getTotal());
					i++;
				}
			}
		}
	}
	
	private void giftBoxSendListExcelDownload(List<GiftBoxListDomainDto>dataList, HSSFSheet sheet) {
		if (dataList.size() > 0) {
			int i=0;
			for (GiftBoxListDomainDto dto : dataList) {
				HSSFRow row = sheet.createRow(i + 1);
				String createDate[] = StringUtil.splitComma(dto.getCreateDate());
				String expireDate[] = StringUtil.splitComma(dto.getExpireDate());
				
				row.createCell(0).setCellValue(createDate[0]);
				row.createCell(1).setCellValue(dto.getGiftTitle());
				row.createCell(2).setCellValue(BirdAdminUtil.getGiftItemName(dto.getGiftType(), dto.getProductName(), dto.getCoinCount()));
				row.createCell(3).setCellValue(dto.getPhoneNumber());
				row.createCell(4).setCellValue(dto.getUserName());
				row.createCell(5).setCellValue(DateUtils.getAge(dto.getBirthDay(), "KOR"));
				row.createCell(6).setCellValue(dto.getGender().equals(Value.MALE) ? "남성" : "여성");
				row.createCell(7).setCellValue(BirdAdminUtil.getGiftState(dto.getGiftState(), dto.getExpireDate()));
				row.createCell(8).setCellValue(dto.getReceiveDate() == null ? "수령전" : expireDate[0]);
				i++;
			}
		}
	}
	
	private void dormantUserListExcelDownload(List<DormantUserDto>dataList, HSSFSheet sheet) {
		if (dataList.size() > 0) {
			int i=0;
			for (DormantUserDto domain : dataList) {
				HSSFRow row = sheet.createRow(i + 1);
				row.createCell(0).setCellValue(domain.getUserId());
				row.createCell(1).setCellValue(domain.getUserName());
				row.createCell(2).setCellValue(domain.getGender());
				row.createCell(3).setCellValue(domain.getBirthDay());
				row.createCell(4).setCellValue(domain.getCreateDate2());
				i++;
			}
		}
	}
	
	private void gatchaUseListExcelDownload(List<GatchaUseLogDto> dataList, HSSFSheet sheet) {
		if (dataList.size() > 0) {
			int i=0;
			for (GatchaUseLogDto dto : dataList) {
				HSSFRow row = sheet.createRow(i + 1);
				row.createCell(0).setCellValue(dto.getPhoneNumber());
				row.createCell(1).setCellValue(dto.getUserName());
				row.createCell(2).setCellValue(dto.getItemName());
				row.createCell(3).setCellValue(dto.getItemCode());
				row.createCell(4).setCellValue(dto.getCornPrice());
				row.createCell(5).setCellValue(dto.getPopcornPrice());
				row.createCell(6).setCellValue(dto.getRewardPoint());
				row.createCell(7).setCellValue(dto.getCreateDate());
				i++;
			}
		}
	}
	
	private void nestItemBuyListExcelDownload(List<NestItemBuyLogDto>dataList, HSSFSheet sheet) {
		if (dataList.size() > 0) {
			int i=0;
			for (NestItemBuyLogDto dto : dataList) {
				HSSFRow row = sheet.createRow(i + 1);
				row.createCell(0).setCellValue(dto.getPhoneNumber());
				row.createCell(1).setCellValue(dto.getUserName());
				row.createCell(2).setCellValue(dto.getItemName());
				row.createCell(3).setCellValue(dto.getProductCode());
				row.createCell(4).setCellValue(dto.getCreateDate());
				i++;
			}
		}
	}
	
	private void retension(List<List<HashMap<String, Object>>>dataList, HSSFSheet sheet) {
		if (dataList.size() > 0) {
			int i=0;
			for (List<HashMap<String, Object>> maps : dataList) {
				for (HashMap<String, Object> list2 : maps) {
					HSSFRow row = sheet.createRow(i + 1);
					row.createCell(0).setCellValue(String.valueOf(list2.get("date")));
					row.createCell(1).setCellValue(Long.valueOf((Long)list2.get("cnt")));
					i++;
				}
			}
		}
	}
	
	private HSSFSheet createFirstSheet(HSSFWorkbook workbook, Map<String, Object>model) {
		HSSFSheet sheet = workbook.createSheet();
		workbook.setSheetName(0, (String)model.get("sheetName"));
		sheet.setColumnWidth(1, 256*30);
		return sheet;
	}
	
	private void createColumnLabel(HSSFSheet sheet, String[] topMenus) {
		int i = 0;
		HSSFRow firstRow = sheet.createRow(0);
		for (String menus : topMenus) {
			HSSFCell cell = firstRow.createCell(i);
			cell.setCellValue(menus);
			i++;
		}
	}

}

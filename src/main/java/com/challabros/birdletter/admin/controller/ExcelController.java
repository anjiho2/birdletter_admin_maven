package com.challabros.birdletter.admin.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.challabros.birdletter.admin.define.datasource.CollectionItem;
import com.challabros.birdletter.admin.define.datasource.ExcelMenu;
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
import com.challabros.birdletter.admin.error.BirdAdminErrorCode;
import com.challabros.birdletter.admin.error.BirdAdminException;
import com.challabros.birdletter.admin.service.GiftService;
import com.challabros.birdletter.admin.service.ItemService;
import com.challabros.birdletter.admin.service.SaleService;
import com.challabros.birdletter.admin.service.StatisticsService;
import com.challabros.birdletter.admin.service.UserService;
import com.challabros.birdletter.admin.util.DateUtils;
import com.challabros.birdletter.admin.util.StringUtil;
import com.challabros.birdletter.admin.util.Util;
import com.challabros.birdletter.admin.util.Value;

@Controller
@RequestMapping("/excel")
public class ExcelController {
	
	final static Logger logger = LoggerFactory.getLogger(ExcelController.class);
	
	private final String excelService = "excelService";
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private ItemService itemService;
	
	@Autowired
	private SaleService saleService;
	
	@Autowired
	private GiftService giftService;
	
	@Autowired
	private StatisticsService statisticsService;
	
	public String[] topMenus = null;
	
	public String fileName = "";
	
	public String excelContent = "";
	
	public String sheetName = "";
	
	/**
	 * <pre>
	 * 1. Comment : 사용자리스트 엑셀 다운로드
	 * 2. 작성자 : 안지호
	 * 3. 작성일 : 2016. 03. 11
	 * </pre>  
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/userList.do")
	private String userList(Model model) {
		//사용자 리스트 목록 가져오기
		List<UserProfileDto>userList = userService.userListByExcel();
		//엑셀 필요 값 정의
		excelContent = "userList";
		topMenus = StringUtil.getStringArray(ExcelMenu.USER_NAME.getMenuName(), ExcelMenu.PHONE_NUMBER.getMenuName(), 
							ExcelMenu.AGE.getMenuName(), ExcelMenu.GENDER.getMenuName());
		fileName = "사용자리스트" + "_" + Util.returnNowDateByYyyymmddhhmmss();
		sheetName = "사용자 정보";
		//엑셀 모델링 셋팅
		excelModelSetting(model, excelContent, topMenus, fileName, sheetName, userList);
		return excelService;
	}
	
	/**
	 * <pre>
	 * 1. Comment : 아이템 판매 내역 엑셀 다운로드
	 * 2. 작성자 : 안지호
	 * 3. 작성일 : 2016. 03 .11
	 * </pre> 
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/itemSaleList.do")
	private String itemSaleList(Model model) {
		List<UserProductBuyLogDto>dataList = itemService.findUserItemBuyListAll();
		excelContent = "itemSaleList";
		topMenus = StringUtil.getStringArray(ExcelMenu.PHONE_NUMBER.getMenuName(), ExcelMenu.PRODUCT_CODE.getMenuName(), 
					ExcelMenu.PRODUCT_NAME.getMenuName(), ExcelMenu.CORN_PRICE.getMenuName(), 
					ExcelMenu.POPCORN_PRICE.getMenuName(), ExcelMenu.BUY_DATE.getMenuName());
		fileName = "아이템 판매 내역" + "_" + Util.returnNowDateByYyyymmddhhmmss();
		sheetName = "판매리스트";

		excelModelSetting(model, excelContent, topMenus, fileName, sheetName, dataList);
		return excelService;
	}
	
	/**
	 * <pre>
	 * 1. Comment : 아이템 판매 순위 엑셀 다운로드
	 * 2. 작성자 : 안지호
	 * 3. 작성일 : 2016. 03 .14
	 * </pre> 
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/itemSaleRankList.do")
	private String itemSaleRankList(Model model) {
		List<ItemBuyRankDto>dataList = saleService.itemBuyRank(0, 0, "");
		excelContent = "itemSaleRankList";
		topMenus = StringUtil.getStringArray(ExcelMenu.RANKING.getMenuName(), ExcelMenu.ITEM_NAME.getMenuName(), ExcelMenu.NUMBER.getMenuName());
		fileName = "아이템 판매 순위" + "_" + Util.returnNowDateByYyyymmddhhmmss();
		sheetName = "아이템 판매 순위 목록";
		
		excelModelSetting(model, excelContent, topMenus, fileName, sheetName, dataList);
		return excelService;
	}
	
	/**
	 * <pre>
	 * 1. Comment : 유저 팝콘 구매 리스트 엑셀 다운로드
	 * 2. 작성자 : 안지호
	 * 3. 작성일 : 2016. 03 .15
	 * </pre> 
	 * @param model
	 * @param userId
	 * @param phoneNumber
	 * @return
	 */
	@RequestMapping(value="/userPopcornBuyList", method = RequestMethod.POST)
	private String userPopcornBuyList(Model model, 
			@RequestParam(value="user_id", required=true) Long userId, 
			@RequestParam(value="l_phoneNumber", required=true) String phoneNumber) {
		List<CoinBuyLogDto>dataList = userService.popcornBuyLogListAll(userId);
		excelContent = "userPopcornBuyList";
		topMenus = StringUtil.getStringArray(ExcelMenu.POPCORN_NAME.getMenuName(), 
				ExcelMenu.POPCORN_PRICE.getMenuName(), ExcelMenu.BUY_DATE.getMenuName());
		fileName = phoneNumber + "_" + "팝콘구매 내역" + "_" + Util.returnNowDateByYyyymmddhhmmss();
		sheetName = "팝콘구매 내역";
		
		excelModelSetting(model, excelContent, topMenus, fileName, sheetName, dataList);
		return excelService;
	}
	
	/**
	 * <pre>
	 * 1. Comment : 유저 아이템 구매 리스트 엑셀 다운로드
	 * 2. 작성자 : 안지호
	 * 3. 작성일 : 2016. 03 .15
	 * </pre> 
	 * @param model
	 * @param userId
	 * @param phoneNumber
	 * @return
	 */
	@RequestMapping(value="/userItemBuyList", method = RequestMethod.POST)
	private String userItemBuyList(Model model, 
			@RequestParam(value="user_id", required=true) Long userId,
			@RequestParam(value="l_phoneNumber", required=true) String phoneNumber) {
		List<UserProductBuyLogDto>dataList = userService.userItemBuyListAll(userId);
		excelContent = "userItemBuyList";
		topMenus = StringUtil.getStringArray(ExcelMenu.PRODUCT_CODE.getMenuName(), ExcelMenu.POPCORN_NAME.getMenuName(),
					ExcelMenu.CORN_PRICE.getMenuName(), ExcelMenu.POPCORN_PRICE.getMenuName(), ExcelMenu.BUY_DATE.getMenuName());
		fileName = phoneNumber + "_" + "아이템 구매 내역" + "_" + Util.returnNowDateByYyyymmddhhmmss();
		sheetName = "아이템 구매 내역";
		
		excelModelSetting(model, excelContent, topMenus, fileName, sheetName, dataList);
		return excelService;
	}
	
	/**
	 * <pre>
	 * 1. Comment : 스토어 목록 리스트 엑셀 다운로드
	 * 2. 작성자 : 안지호
	 * 3. 작성일 : 2016. 03 .28
	 * </pre> 
	 * @param model
	 * @param storeType
	 * @return
	 */
	@RequestMapping(value="/storeList", method=RequestMethod.POST)
	private String storeList(Model model, 
			@RequestParam(value="store_type", required=false) String storeType) {
		List<ProductListDto>dataList = itemService.productList(0, 0, "", "", "", Util.isNullValue(storeType, ""));
		excelContent = "storeList";
		topMenus = StringUtil.getStringArray(ExcelMenu.PRODUCT_CODE.getMenuName(), ExcelMenu.PRODUCT_NAME.getMenuName(), 
					ExcelMenu.ITEM_CODE.getMenuName(), ExcelMenu.CORN_PRICE.getMenuName(), ExcelMenu.POPCORN_PRICE.getMenuName());
		String storeName = CollectionItem.getStoreName(storeType);
		fileName = storeName + "_스토어목록_" + Util.returnNowDateByYyyymmddhhmmss();
		sheetName = "스토어 목록";
		
		excelModelSetting(model, excelContent, topMenus, fileName, sheetName, dataList);
		return excelService;
	}
	
	/**
	 * <pre>
	 * 1. Comment : 콘 판매 내역 엑셀 다운로드
	 * 2. 작성자 : 안지호
	 * 3. 작성일 : 2016. 07 .01
	 * </pre> 
	 * @param model
	 * @param startDate
	 * @param endDate
	 * @param itemType
	 * @return
	 */
	@RequestMapping(value="/cornSaleList", method=RequestMethod.POST)
	private String cornSaleList(Model model, 
			@RequestParam(value="start_date")String startDate, 
			@RequestParam(value="end_date")String endDate, 
			@RequestParam(value="itemType")String itemType) {
		List<CornSaleListDto>dataList = saleService.totalCornPopcornSaleList(0, startDate, endDate, itemType);
		excelContent = "cornSaleList";
		topMenus = StringUtil.getStringArray(ExcelMenu.BUY_DATE.getMenuName(), ExcelMenu.USER_NAME.getMenuName(), 
					ExcelMenu.PHONE_NUMBER.getMenuName(), ExcelMenu.SALE_PRICE.getMenuName());
		fileName = startDate + "_" + endDate + "_콘 판매 리스트";
		sheetName = "콘 판매 목록";
		
		excelModelSetting(model, excelContent, topMenus, fileName, sheetName, dataList);
		return excelService;
	}
	
	/**
	 * <pre>
	 * 1. Comment : 대시보드 엑셀 다운로드
	 * 2. 작성자 : 안지호
	 * 3. 작성일 : 2016. 08 .25
	 * </pre> 
	 * @param model
	 * @param searchDate
	 * @param type
	 * @param sel_year
	 * @param sel_month
	 * @return
	 */
	@RequestMapping(value="/dashboardStatistics", method=RequestMethod.POST)
	public String dashboardStatistics(Model model, @RequestParam(value="search_date")String searchDate,
			@RequestParam(value="type") String type, @RequestParam(value="selYear") String sel_year, 
			@RequestParam(value="selMonth") String sel_month) {
		try {
			String year = "";
			String month = "";
			if (!"".equals(searchDate)) {
				String date[] = searchDate.split("-");
				year = date[0];
				month = date[1];
			} else {
				year = sel_year;
				month = sel_month;
			}
			String thisYear = DateUtils.getNowYear();
			
			//엑셀 시트 이름 선언
			String downloadSheetName = "다운로드 수";
			String memberRegSheetName = "가입 수";
			String dauSheetName = "DAU";
			String mauSheetName = "MAU";
			String arpuSheetName = "ARPU";
			String arppuSheetName = "ARPPU";
			String ageSheetName = "연령별 사용자 백분율";
			String letterSheetName = "편지생성 수";
			
			String day = "";
			
			//데이터 선언
			List<DownloadCountInfoDto>dailyDownloadStatistics = null;	//다운로드수
			List<MemberStatisticsDto>dailyMemberRegStatistics = null;	//가입수
			List<MemberStatisticsDto>dailyDauStatistics = null;	//DAU
			List<MemberStatisticsDto>dailyMauStatistics = null;	//MAU
			List<DateCountDto>arpuStatistics = null;	//ARPU
			List<DateCountDto>arppuStatistics = null;	//ARPPU
			List<AuthStatisticsDto>ageStatistics = null;	//연령별 사용자 백분율
			List<LetterStatisticsDto>letterStatistics = null;	//편지생성 수
			
			if ("daily".equals(type)) {
				day = "일별 ";
				dailyDownloadStatistics = statisticsService.downloadStatistics(searchDate, Value.DAY);
				dailyMemberRegStatistics = statisticsService.memberRegStatistics(searchDate, Value.DAY);
				dailyDauStatistics = statisticsService.dauStatistics(searchDate, Value.DAY);
				dailyMauStatistics = statisticsService.mauStatistics(Integer.valueOf(year), StringUtil.leaveLeftZero(month), type);
				arpuStatistics = statisticsService.arpuStatistics(Integer.valueOf(year), StringUtil.leaveLeftZero(month), type);
				arppuStatistics = statisticsService.arppuStatistics(Integer.valueOf(year), StringUtil.leaveLeftZero(month), type);
				ageStatistics = statisticsService.authStatisticsByAge(searchDate, type);
				letterStatistics = statisticsService.letterStatistics(searchDate, Value.DAY);
			} else if ("weekly".equals(type)) {
				day = "주별 ";
				dailyDownloadStatistics = statisticsService.downloadStatistics(searchDate, Value.WEEK);
				dailyMemberRegStatistics = statisticsService.memberRegStatistics(searchDate, Value.WEEK);
				dailyDauStatistics = statisticsService.dauStatistics(searchDate, Value.WEEK);
				dailyMauStatistics = statisticsService.mauStatistics(Integer.valueOf(year), StringUtil.leaveLeftZero(month), type);
				arpuStatistics = statisticsService.arpuStatistics(Integer.valueOf(year), StringUtil.leaveLeftZero(month), type);
				arppuStatistics = statisticsService.arppuStatistics(Integer.valueOf(year), StringUtil.leaveLeftZero(month), type);
				ageStatistics = statisticsService.authStatisticsByAge(searchDate, type);
				letterStatistics = statisticsService.letterStatistics(searchDate, Value.WEEK);
			} else if ("monthly".equals(type)) {
				day = "월별 ";
				List<DownloadCountInfoDto>Arr = statisticsService.monthlyDownloadStatistics(Integer.valueOf(year), StringUtil.leaveLeftZero(month));
				List<DownloadCountInfoDto>mapArr = new ArrayList<>();
				for (DownloadCountInfoDto vo : Arr) {
					DownloadCountInfoDto map = new DownloadCountInfoDto();
					map.setCreateDate(vo.getCreateDate());
					map.setIos(vo.getIos());
					map.setAndroid(vo.getAndroid());
					map.setTotal(vo.getTotal());
					mapArr.add(map);
				}
				dailyDownloadStatistics = mapArr;
				dailyMemberRegStatistics = statisticsService.monthlyMemberRegStatistics(Integer.valueOf(year), StringUtil.leaveLeftZero(month));
				dailyDauStatistics = statisticsService.monthlyDauStatistics(Integer.valueOf(year), StringUtil.leaveLeftZero(month));
				dailyMauStatistics = statisticsService.mauStatistics(Integer.valueOf(year), StringUtil.leaveLeftZero(month), "");
				arpuStatistics = statisticsService.arpuStatistics(Integer.valueOf(year), StringUtil.leaveLeftZero(month), "");
				arppuStatistics = statisticsService.arppuStatistics(Integer.valueOf(year), StringUtil.leaveLeftZero(month), "");
				ageStatistics = statisticsService.authStatisticsByAge(searchDate, type);
				letterStatistics = statisticsService.monthlyLetterStatistics(Integer.valueOf(year), StringUtil.leaveLeftZero(month));
			} else if ("year".equals(type)) {
				day = "연별 ";
				List<DownloadCountInfoDto>Arr = statisticsService.yearDownloadStatistics(year, thisYear);
				List<DownloadCountInfoDto>mapArr = new ArrayList<>();
				for (DownloadCountInfoDto vo : Arr) {
					DownloadCountInfoDto map = new DownloadCountInfoDto();
					map.setCreateDate(vo.getCreateDate());
					map.setIos(vo.getIos());
					map.setAndroid(vo.getAndroid());
					map.setTotal(vo.getTotal());
					mapArr.add(map);
				}
				dailyDownloadStatistics = mapArr;
				dailyMemberRegStatistics = statisticsService.yearMemberRegStatistics(year, thisYear);
				dailyDauStatistics = statisticsService.yearDauStatistics(year, thisYear);
				dailyMauStatistics = statisticsService.mauStatistics(Integer.valueOf(year), StringUtil.leaveLeftZero(month), Value.YEAR);
				arpuStatistics = statisticsService.arpuStatistics(Integer.valueOf(year), StringUtil.leaveLeftZero(month), Value.YEAR);
				arppuStatistics = statisticsService.arppuStatistics(Integer.valueOf(year), StringUtil.leaveLeftZero(month), Value.YEAR);
				ageStatistics = statisticsService.authStatisticsByAge(searchDate, type);
				letterStatistics = statisticsService.yearLetterStatistics(year, thisYear);
			}
			downloadSheetName = day + downloadSheetName;
			memberRegSheetName = day + memberRegSheetName;
			dauSheetName = day + dauSheetName;
			mauSheetName = day + mauSheetName;
			arpuSheetName = day + arpuSheetName;
			arppuSheetName = day + arppuSheetName;
			ageSheetName = day + ageSheetName;
			letterSheetName = day + letterSheetName;
			
			//시트별 메뉴값 설정
			String[] downloadMenu = StringUtil.getStringArray(ExcelMenu.DATE.getMenuName(), ExcelMenu.APPLE.getMenuName(), 
														ExcelMenu.ANDROID.getMenuName(), ExcelMenu.TOTAL_SUM.getMenuName());
			String[] memberRegMenu = StringUtil.getStringArray(ExcelMenu.DATE.getMenuName(), ExcelMenu.MALE.getMenuName(), 
														ExcelMenu.FEMALE.getMenuName(), ExcelMenu.TOTAL_SUM.getMenuName());
			String[] dauMenu = StringUtil.getStringArray(ExcelMenu.DATE.getMenuName(), ExcelMenu.MALE.getMenuName(), 
														ExcelMenu.FEMALE.getMenuName(), ExcelMenu.TOTAL_SUM.getMenuName());
			String[] arpuMenu = StringUtil.getStringArray(ExcelMenu.DATE.getMenuName(), ExcelMenu.ARPU.getMenuName());
			String[] arppuMenu = StringUtil.getStringArray(ExcelMenu.DATE.getMenuName(), ExcelMenu.ARPPU.getMenuName());
			String[] ageMenu = StringUtil.getStringArray(ExcelMenu.AGE.getMenuName(), ExcelMenu.CONNECTION_COUNT.getMenuName());
			String[] letterMenu = StringUtil.getStringArray(ExcelMenu.DATE.getMenuName(), ExcelMenu.PRIVATE_LETTER.getMenuName(), 
														ExcelMenu.OPEN_LETTER.getMenuName(), ExcelMenu.TOTAL_SUM.getMenuName());
			
			StatisticsExcelDownloadDto dataList = new StatisticsExcelDownloadDto(dailyDownloadStatistics, dailyMemberRegStatistics,
								dailyDauStatistics, dailyMauStatistics, arpuStatistics, arppuStatistics, ageStatistics, letterStatistics);
			
			//메뉴 값 담기
			List<String[]>topMenusArr = new ArrayList<>();
			topMenusArr.add(downloadMenu);
			topMenusArr.add(memberRegMenu);
			topMenusArr.add(dauMenu);
			topMenusArr.add(arpuMenu);
			topMenusArr.add(arppuMenu);
			topMenusArr.add(ageMenu);
			topMenusArr.add(letterMenu);
			
			//시트명 담기
			List<String>sheestNameArr = new ArrayList<>();
			sheestNameArr.add(downloadSheetName);
			sheestNameArr.add(memberRegSheetName);
			sheestNameArr.add(dauSheetName);
			sheestNameArr.add(mauSheetName);
			sheestNameArr.add(arpuSheetName);
			sheestNameArr.add(arppuSheetName);
			sheestNameArr.add(ageSheetName);
			sheestNameArr.add(letterSheetName);
			
			//파일명 선언
			String fileName = Util.returnNowDateByYyyymmddhhmmss() + "_" + day + "통계모음";
			
			excelModelSetting(model, "dashboardStatistics", topMenusArr, fileName, sheestNameArr, dataList, type);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return excelService;
	}
	
	/**
	 * <pre>
	 * 1. Comment : 선물함 지급 정보
	 * 2. 작성자 : 안지호
	 * 3. 작성일 : 2016. 08 .25
	 * </pre> 
	 * @param model
	 * @param startDate
	 * @param endDate
	 * @param searchType
	 * @param searchValue
	 * @return
	 */
	@RequestMapping(value="/giftBoxSendList", method=RequestMethod.POST)
	private String giftBoxSendList(Model model, 
			@RequestParam(value="start_date")String startDate,
			@RequestParam(value="end_date")String endDate, 
			@RequestParam(value="search_type")String searchType, 
			@RequestParam(value="search_value2")String searchValue) {
		List<GiftBoxListDomainDto>dataList = giftService.presentListAtExcel(startDate, endDate, searchType, searchValue);
		excelContent = "giftBoxSendList";
		topMenus = StringUtil.getStringArray(ExcelMenu.PAY_DATE.getMenuName() , ExcelMenu.GIFT_NAME.getMenuName(), 
					ExcelMenu.GIFT_ITEM.getMenuName(), ExcelMenu.PHONE_NUMBER.getMenuName(), ExcelMenu.USER_NAME.getMenuName(), 
					ExcelMenu.AGE.getMenuName(), ExcelMenu.GENDER.getMenuName(), ExcelMenu.GIFT_STATE.getMenuName(), ExcelMenu.RECEIVE_DATE.getMenuName());
		fileName = Util.returnNowDateByYyyymmddhhmmss() + "_" + "선물지급 목록";
		sheetName = "선물지급 목록";
		
		excelModelSetting(model, excelContent, topMenus, fileName, sheetName, dataList);
		return excelService;
	}
	
	/**
	 * <pre>
	 * 1. Comment : 휴먼사용자 엑셀 다운로드
	 * 2. 작성자 : 안지호
	 * 3. 작성일 : 2016. 08 .25
	 * </pre> 
	 * @param model
	 * @param searchDate
	 * @param dayCount
	 * @return
	 */
	@RequestMapping(value="/dormantUserList", method=RequestMethod.POST)
	private String dormantUserList(Model model, 
			@RequestParam(value="search_date") String searchDate,
			@RequestParam(value="day_count") int dayCount) {
		List<DormantUserDto>dataList = statisticsService.dormantUserStatistics(0, searchDate, dayCount);
		excelContent = "dormantUserList";
		topMenus = StringUtil.getStringArray(ExcelMenu.ID.getMenuName(), ExcelMenu.USER_NAME.getMenuName(), 
					ExcelMenu.GENDER.getMenuName(), ExcelMenu.AGE.getMenuName(), ExcelMenu.REG_DATE.getMenuName());
		fileName = searchDate + "_" + dayCount + "일 후 휴먼사용자 지표";
		sheetName = "휴먼사용자 지표";
		
		excelModelSetting(model, excelContent, topMenus, fileName, sheetName, dataList);
		return excelService;
	}
	
	/**
	 * <pre>
	 * 1. Comment : 가차사용 기록 엑셀 다운로드 
	 * 2. 작성자 : 안지호
	 * 3. 작성일 : 2016. 12 .23
	 * </pre> 
	 * @param model
	 * @param startDate
	 * @param endDate
	 * @param userName
	 * @param phoneNumber
	 * @return
	 */
	@RequestMapping(value="/gatchaUseList", method=RequestMethod.POST)
	private String gatchaUseList(Model model,
			@RequestParam(value = "start_date", defaultValue = "") String startDate,
			@RequestParam(value = "end_date", defaultValue = "") String endDate,
			@RequestParam(value = "userName", defaultValue = "") String userName,
			@RequestParam(value = "phoneNumber", defaultValue = "") String phoneNumber) {
		List<GatchaUseLogDto> dataList = itemService.gatchaUseLogList(0, 0, userName, phoneNumber, startDate, endDate);
		excelContent = "gatchaUseList";
		topMenus = StringUtil.getStringArray(ExcelMenu.PHONE_NUMBER.getMenuName(), ExcelMenu.USER_NAME.getMenuName(), 
					ExcelMenu.ITEM_NAME.getMenuName(), ExcelMenu.ITEM_CODE.getMenuName(), ExcelMenu.CORN_PRICE.getMenuName(), 
					ExcelMenu.POPCORN_PRICE.getMenuName(), ExcelMenu.REWARD_POPCORN.getMenuName(), ExcelMenu.USE_DATE.getMenuName());
		fileName = Util.returnNowDateByYyyymmddhhmmss() + "_가차사용기록";
		sheetName = "가차사용기록";
		
		excelModelSetting(model, excelContent, topMenus, fileName, sheetName, dataList);
		return excelService;
	}
	
	/**
	 * <pre>
	 * 1. Comment : 가차 아이템 구매 내역 엑셀 다운로드 
	 * 2. 작성자 : 안지호
	 * 3. 작성일 : 2016. 12 .26
	 * </pre> 
	 * @param model
	 * @param startDate
	 * @param endDate
	 * @param userName
	 * @param phoneNumber
	 * @return
	 */
	@RequestMapping(value="/nestItemUseList", method=RequestMethod.POST)
	private String nestItemUseList(Model model,
			@RequestParam(value = "start_date", defaultValue = "")String startDate,
			@RequestParam(value = "end_date", defaultValue = "")String endDate,
			@RequestParam(value = "userName", defaultValue = "")String userName,
			@RequestParam(value = "phoneNumber", defaultValue = "")String phoneNumber) {
		List<NestItemBuyLogDto> dataList = itemService.nestItemBuyLogList(0, 0, userName, phoneNumber, startDate, endDate);
		excelContent = "nestItemUseList";
		topMenus = StringUtil.getStringArray(ExcelMenu.PHONE_NUMBER.getMenuName(), 
				ExcelMenu.USER_NAME.getMenuName(), ExcelMenu.ITEM_NAME.getMenuName(), 
				ExcelMenu.ITEM_CODE.getMenuName(), ExcelMenu.BUY_DATE.getMenuName());
		fileName = Util.returnNowDateByYyyymmddhhmmss() + "_둥지 아이템 구매 내역";
		sheetName = "둥지 아이템 구매 내역";
		excelModelSetting(model, excelContent, topMenus, fileName, sheetName, dataList);
		
		return excelService;
	}
	
	/**
	 * <pre>
	 * 1. Comment : 누적, 신규 리텐션 엑셀 다운로드 
	 * 2. 작성자 : 안지호
	 * 3. 작성일 : 2017. 01 .05
	 * </pre> 
	 * @param model
	 * @param retenstionType
	 * @param searchDate
	 * @param dayCount
	 * @return
	 */
	@RequestMapping(value="/retension", method=RequestMethod.POST)
	private String dauRetension(Model model,
			@RequestParam(value="retension_type") String retenstionType,
			@RequestParam(value="search_date") String searchDate,
			@RequestParam(value="day_count") int dayCount) {
		String subFileName = "";
		if ("dau".equals(retenstionType)) subFileName = "누적";
		else if ("reg".equals(retenstionType)) subFileName = "신규";
		
		List<List<HashMap<String, Object>>> dataList = statisticsService.retensionExcelDownload(retenstionType, dayCount, searchDate);
		excelContent = "retension";
		topMenus = StringUtil.getStringArray(ExcelMenu.DATE.getMenuName(), ExcelMenu.USER_COUNT.getMenuName());
		fileName = searchDate + "_" + dayCount + "일 리텐션" + "_" + subFileName;
		sheetName = searchDate + "일 리텐션" + "(" + subFileName + ")";
		excelModelSetting(model, excelContent, topMenus, fileName, sheetName, dataList);
		
		return excelService;
		
	}
	
	public Model excelModelSetting(Model model, String excelContent, String[] topMenus,
			String fileName, String sheetName, List dataList) {
		model.addAttribute("excelContent", Util.isNullValue(excelContent, ""));
		model.addAttribute("topMenus", topMenus);
		model.addAttribute("fileName", Util.isNullValue(fileName+".xls", ""));
		model.addAttribute("sheetName", Util.isNullValue(sheetName, ""));
		model.addAttribute("dataList", dataList);
		return model;
	}
	
	public Model excelModelSetting(Model model, String excelContent, List<String[]>topMenus, 
			String fileName, List<String>sheetName, StatisticsExcelDownloadDto dto, String type) {
		model.addAttribute("excelContent", Util.isNullValue(excelContent, ""));
		model.addAttribute("topMenusArr", topMenus);
		model.addAttribute("fileName", Util.isNullValue(fileName+".xls", ""));
		model.addAttribute("sheetNameArr", sheetName);
		model.addAttribute("type", type);
		model.addAttribute("dataList", dto);
		return model;
	}

}

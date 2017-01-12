package com.challabros.birdletter.admin.manager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;

import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SchedulerFactory;
import org.quartz.impl.StdSchedulerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.challabros.birdletter.admin.util.Util;
import com.challabros.birdletter.admin.define.datasource.AnniversaryEnum;
import com.challabros.birdletter.admin.define.datasource.DataSource;
import com.challabros.birdletter.admin.define.datasource.DataSourceType;
import com.challabros.birdletter.admin.dto.EmailSendDto;
import com.challabros.birdletter.admin.dto.PushReserveDto;
import com.challabros.birdletter.admin.dto.PushSendListDto;
import com.challabros.birdletter.admin.dto.UserProfileDto;
import com.challabros.birdletter.admin.service.EmailService;
import com.challabros.birdletter.admin.service.PushService;
import com.challabros.birdletter.admin.service.UserService;
import com.challabros.birdletter.admin.util.ChinaDateUtil;
import com.challabros.birdletter.admin.util.DateUtils;
import com.challabros.birdletter.admin.util.Value;

@Component
public class CronManager {

	final static Logger logger = LoggerFactory.getLogger(CronManager.class);
	
	private static String[] EMAIL_USERS = {"pyeonzi20c@challabros.com", "anjo070@challabros.com", "crybaby@challabros.com"};
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private PushService pushService;
	
	@Autowired
	private EmailService emailService;
	
	/**
	 * <PRE>
     * 1. Comment : 생일 알림 푸시 세팅
     * 2. 작성자 : 안지호
     * 3. 작성일 : 2016. 09. 20
     * </PRE> 
	 * @param mmdd
	 */
	@Transactional(propagation = Propagation.REQUIRED)
	public void birthdaySchedule(String mmdd) {
		String subject = mmdd + " 생일 알림 스케쥴링 내용";
		String content = "생일 알림 스케쥴링 시작 시간 : " + Util.returnNow();
		
		if (!"".equals(mmdd)) {
			String name = "생일 알림";
			String[] splitMMdd = mmdd.split("-");
			String korMMdd = splitMMdd[0]+ "월 " + splitMMdd[1] + "일";
			
			content += "\n" + korMMdd + name + "\n";
			
			List<HashMap<String, Object>>userMap = new ArrayList<HashMap<String,Object>>();
			userMap = userService.findUserByBirthDay(mmdd);
			//중복제거
			List<HashMap<String, Object>> duplicateUserMap = new ArrayList<HashMap<String, Object>>(new LinkedHashSet<>(userMap));
			if (duplicateUserMap.size() > 0) {
				for (Map<String, Object>user : duplicateUserMap) {
					long userId = (Long)user.get("user_id");
					String label = (String)user.get("user_name") + "님\n" + korMMdd + " 생일이에요";
					String subTitle = AnniversaryEnum.FRIEND_BIRTHDAY.getSubContent();
					
					content +=  (String)user.get("user_name") + ", "; 
					
					List<Long>friendUserIds = userService.findUserFriendShip(userId);
					if (friendUserIds.size() > 0) {
						int pushInfoIdx = pushService.insertPushInfoByBirthDay(name, label, subTitle, DateUtils.plusDay(Util.returnToDate("yyyy-MM-dd"), "YYYYMMDD", 1));
						if (pushInfoIdx > 0) {
							pushService.insertPushSendListAtBirthDay(pushInfoIdx, friendUserIds, userId, label, subTitle, DateUtils.plusDay(Util.returnToDate("yyyy-MM-dd"), "YYYYMMDD", 0));
						}
					}
				}
			} else {
				logger.info("friend is null...");
				content += "생일자가 없습니다.";
			}
		} else {
			logger.info("mmdd is null...");
		}
		content += "\n생일 알림 스케쥴 종료 시간 : " + Util.returnNow();
		try {
			for (String receiver : EMAIL_USERS) {
				EmailSendDto dto = new EmailSendDto(subject, content, receiver);
				emailService.SendEMail(dto);
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		
	}
	
	@Transactional(propagation = Propagation.REQUIRED)
	public void anniversarySchedule(String yyyymmdd, String mmdd) throws Exception {
		if (!"".equals(yyyymmdd) && !"".equals(mmdd)) {
			String content = "";
			String subTitle = "";
			boolean bl = false;
			int pushIdx = 0;
			int sendType = 0;

			logger.info("================ 음력 설/추석인 체크 시작 ================");
			//음력 공휴일 체크
			boolean isLunar = ChinaDateUtil.isLunar(yyyymmdd);
			if (isLunar == true) {
				logger.info("================ 음력 기념일 시작 ================");
				String yyyy_NEW_YEAR = DateUtils.plusDay(Util.returnToDate("yyyy-MM-dd"), "YYYY", 1) + "-" + AnniversaryEnum.NEW_YAER.getDate();	//해당년도와 설날일을 합친 년월일
				String yyyy_THANKS_GIVING_DAY = DateUtils.plusDay(Util.returnToDate("yyyy-MM-dd"), "YYYY", 1) + "-" + AnniversaryEnum.THANKS_GIVING_DAY.getDate();	//해당년도와 추석일을 합친 년월일
				String chinaDate = ChinaDateUtil.ChinaDate(yyyymmdd);	//음력 월/일 가져오기
				if (yyyy_NEW_YEAR.equals(chinaDate)) {
					logger.info("================ 음력 새해 값 가져오기 ================");
					content = AnniversaryEnum.CHINA_NEW_YEAR.getLabel();
					subTitle = AnniversaryEnum.CHINA_NEW_YEAR.getSubContent();
					sendType = AnniversaryEnum.CHINA_NEW_YEAR.getCode();
				} else if (yyyy_THANKS_GIVING_DAY.equals(chinaDate)) {
					logger.info("================ 음력 추석 값 가져오기 ================");
					content = AnniversaryEnum.THANKS_GIVING_DAY.getLabel();
					subTitle = AnniversaryEnum.THANKS_GIVING_DAY.getSubContent();
					sendType = AnniversaryEnum.THANKS_GIVING_DAY.getCode();
				}
				if (!"".equals(content)) {
					logger.info("================ 음력 설/추석 pushInfo 입력 ================");
					bl = pushService.pushInfoInsert(content, subTitle, sendType, DateUtils.plusDay(Util.returnToDate("yyyy-MM-dd"), "YYYYMMDD", 0) + " 15:00:00");
					if (bl == true) {
						pushIdx = pushService.findPushInfoIdx(DateUtils.plusDay(Util.returnToDate("yyyy-MM-dd"), "YYYYMMDD", 0) + " 15:00:00");
						if (pushIdx > 0) {
							if (yyyy_NEW_YEAR.equals(chinaDate)) {
								logger.info("================ 음력 새해 시작 ================");
								List<UserProfileDto> userProfile = userService.userProfileListAll();
								pushService.insertPushSendListByNewYear(userProfile, pushIdx, content, subTitle, DateUtils.plusDay(Util.returnToDate("yyyy-MM-dd"), "YYYYMMDD", 0) + " 15:00:00");
								logger.info("================ 음력 새해 끝 ================");
							} else {
								logger.info("================ 음력 추석 시작 ================");
								pushService.insertPushSendList(pushIdx, 0, "", sendType);
								logger.info("================ 음력 추석 끝 ================");
							}
						}
					} else {
						logger.info("================ 음력 설/추석 pushInfo 입력 오류 발생 ================");
					}
				}
			} else {
				logger.info("================ 양력 기념일 시작 ================");
				String COMING_DAY = DateUtils.getDate(Integer.parseInt(Util.returnToDate("yyyy")), 5, 3, 1, "INTEGER");//성년의날
				String sendDateTime = DateUtils.getPushTime(DateUtils.plusDay(Util.returnToDate("yyyy-MM-dd"), "YYYYMMDD", 0));
				int age = 0;
				String gender = "";
				/**================ 일반 기념일 시작 ================**/
				if (AnniversaryEnum.VALENTINE_DAY.getDate().equals(mmdd)) {
					/**================ 발렌타인 데이 ================**/
					content = AnniversaryEnum.VALENTINE_DAY.getLabel();
					subTitle = AnniversaryEnum.VALENTINE_DAY.getSubContent();
					age = 14;
					gender = Value.FEMALE;
					sendType = AnniversaryEnum.VALENTINE_DAY.getCode();
					
				} else if (AnniversaryEnum.PORK_BELLY_DAY.getDate().equals(mmdd)) {
					/**================ 삼겹살 기념일 시작 ================**/
					content = AnniversaryEnum.PORK_BELLY_DAY.getLabel();
					subTitle = AnniversaryEnum.PORK_BELLY_DAY.getSubContent();
					age = 18;
					sendType = AnniversaryEnum.PORK_BELLY_DAY.getCode();
				} else if (AnniversaryEnum.WHITE_DAY.getDate().equals(mmdd)) {
					/**================ 화이트 데이 시작 ================**/
					content = AnniversaryEnum.WHITE_DAY.getLabel();
					subTitle = AnniversaryEnum.WHITE_DAY.getSubContent();
					age = 14;
					gender = Value.MALE;
					sendType = AnniversaryEnum.WHITE_DAY.getCode();
				} else if (AnniversaryEnum.MOTHERS_DAY.equals(mmdd)) {
					/**================ 어버이날 시작 ================**/
					content = AnniversaryEnum.MOTHERS_DAY.getLabel();
					subTitle = AnniversaryEnum.MOTHERS_DAY.getSubContent();
					sendType = AnniversaryEnum.MOTHERS_DAY.getCode();
				} else if (AnniversaryEnum.TEACHER_DAY.getDate().equals(mmdd)) {
					/**================ 스승의날 시작 ================**/
					content = AnniversaryEnum.TEACHER_DAY.getLabel();
					subTitle = AnniversaryEnum.TEACHER_DAY.getSubContent();
					sendType = AnniversaryEnum.TEACHER_DAY.getCode();
				} else if (COMING_DAY.equals(mmdd)) {
					/**================ 성년의날 시작 ================**/
					content = DateUtils.getDate(Integer.parseInt(Util.returnToDate("yyyy")), 5, 3, 2, "KOREAN") + "\n성년의 날";
					subTitle = AnniversaryEnum.COMING_DAY.getSubContent();
					age = 18;
					sendType = AnniversaryEnum.COMING_DAY.getCode();
				} else if (AnniversaryEnum.PAEPAERO_DAY.getDate().equals(mmdd)) {
					/**================ 빼뺴로 데이 시작 ================**/
					content = AnniversaryEnum.PAEPAERO_DAY.getLabel();
					subTitle = AnniversaryEnum.PAEPAERO_DAY.getSubContent();
					sendType = AnniversaryEnum.PAEPAERO_DAY.getCode();
				} else if (AnniversaryEnum.KOREAN_DAY.getDate().equals(mmdd)) {
					/**================ 한글날 시작 ================**/
					content = AnniversaryEnum.KOREAN_DAY.getLabel();
					subTitle = AnniversaryEnum.KOREAN_DAY.getSubContent();
					sendType = AnniversaryEnum.KOREAN_DAY.getCode(); 
				} else if (AnniversaryEnum.CHRISTMAS.getDate().equals(mmdd)) {
					/**================ 크리스마스 시작 ================**/
					content = AnniversaryEnum.CHRISTMAS.getLabel();
					subTitle = AnniversaryEnum.CHRISTMAS.getSubContent();
					sendType = AnniversaryEnum.CHRISTMAS.getCode();
				} else if (AnniversaryEnum.NEW_YAER.getDate().equals(mmdd)) {
					/**================ 1월1일 ================**/
					content = "새해 첫 날\n"+DateUtils.plusDay(Util.returnToDate("yyyy-MM-dd"), "YYYY", 1)+"년 1월 1일";
					subTitle = AnniversaryEnum.NEW_YAER.getSubContent();
					sendType = AnniversaryEnum.NEW_YAER.getCode();
				}
				if (!"".equals(content) && !"".equals(subTitle)) {
					bl = pushService.pushInfoInsert(content, subTitle, sendType, DateUtils.getPushTime(DateUtils.plusDay(Util.returnToDate("yyyy-MM-dd"), "YYYYMMDD", 0)));
					if (bl == true) {
						pushIdx = pushService.findPushInfoIdx(sendDateTime);
						if (pushIdx > 0) {
							pushService.insertPushSendList(pushIdx, age, gender, sendType);
						}
					}
				}
			}
		}
	}
	
	@Transactional(propagation = Propagation.REQUIRED)
	public void pushSendAllUsers(String content, String subTitle, String sendDate, String time) throws Exception {
		//String sendDateTime = DateUtils.getPushTime(sendDate);
		String sendDateTime = DateUtils.localtimeToUTC(DateUtils.makeDateTimeSecond(sendDate, time));
		
		int pushIdx = pushService.insertPushInfo("단체 알림", content, subTitle, sendDateTime, Value.ADMIN);
		if (pushIdx > 0) {
			pushService.insertPushSendList(pushIdx, 0, "", 0);
		}
	}
	
	public void shutdownQuartz() throws SchedulerException {
		SchedulerFactory factory = new StdSchedulerFactory();
		Scheduler scheduler = factory.getScheduler();
		boolean bl = true;
		scheduler.shutdown(bl);
		logger.info("Scheduler shutdown? " + scheduler.isShutdown());
	}
	
	/**
	 * <PRE>
     * 1. Comment : 예약발송 목록을 발송 목록에 담기
     * 2. 작성자 : 안지호
     * 3. 작성일 : 2017. 01. 12
     * </PRE> 
	 * @param mmdd
	 * @param date
	 */
	@Transactional(propagation = Propagation.REQUIRED)
	public void pushSendByReserve(String date) {
		List<PushReserveDto> reserveList = pushService.selectPushReserve(date);
		for (PushReserveDto reserveDto : reserveList) {
			List<Integer> userIds = new ArrayList<>();
			
			String[] searchDate = Util.split(reserveDto.getSendDateTime(), " ");
			userIds = userService.selectUserIdByAuthLog(DateUtils.getOclock(searchDate[0]), reserveDto.getRetension());
			
			if (userIds.size() > 0) {
				List<PushSendListDto>paramDto = new ArrayList<>();
				int pushIdx = pushService.insertPushInfo(reserveDto.getName(), reserveDto.getContent(), 
						"", reserveDto.getSendDateTime(), reserveDto.getSendType());
				for (Integer userId : userIds) {
					if (pushIdx > 0) {
						PushSendListDto sendListDto = PushSendListDto.consume(
								userId, pushIdx, reserveDto.getContent(), "", reserveDto.getSendDateTime(), String.valueOf(301)
						);
					paramDto.add(sendListDto);
					}
				}
				pushService.insertPushSendListByReserve(paramDto);
			}
		}
	}
}

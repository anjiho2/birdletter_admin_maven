package com.challabros.birdletter.admin.service;

import java.util.HashMap;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.challabros.birdletter.admin.dto.BirdTooltipDto;
import com.challabros.birdletter.admin.dto.NoticeDto;
import com.challabros.birdletter.admin.dto.PagingDto;
import com.challabros.birdletter.admin.dto.PopupNoticeDto;
import com.challabros.birdletter.admin.error.BirdAdminErrorCode;
import com.challabros.birdletter.admin.error.BirdAdminException;
import com.challabros.birdletter.admin.mapper.NoticeMapper;
import com.challabros.birdletter.admin.util.DateUtils;
import com.challabros.birdletter.admin.util.Util;
import com.challabros.birdletter.admin.util.Value;

@Service
public class NoticeService {
	
	final static Logger logger = LoggerFactory.getLogger(NoticeService.class);
	
	@Autowired
	private NoticeMapper noticeMapper;
	
	/**
	 * <pre>
	 * 1. Comment : 공지사항 등록
	 * 2. 작성자 : 안지호
	 * 3. 작성일 : 2016. 01. 04
	 * </pre> 
	 * @param title
	 * @param content
	 * @param fileName
	 * @param imgType
	 * @param viewYn
	 * @return
	 */
	@Transactional(propagation = Propagation.REQUIRED)
	public boolean notiInsert(String title, String content, String fileName, int imgType, int viewYn) {
		int imgWidth = 0;
		int imgHeight = 0;
		
		if (imgType >= 0) {
			if (imgType == 0) {
				imgWidth = 0;
				imgHeight = 0;
			} else if (imgType == 1) {
				imgWidth = 640;
				imgHeight = 200;
			} else if (imgType == 2) {
				imgWidth = 640;
				imgHeight = 300;
			} else if (imgType == 3) {
				imgWidth = 640;
				imgHeight = 400;
			}
		}
		HashMap<String, Object>paramMap = new HashMap<>();
		paramMap.put("title", title);
		paramMap.put("content", content);
		paramMap.put("fileName", fileName);
		paramMap.put("imgWidth", imgWidth);
		paramMap.put("imgHeight", imgHeight);
		paramMap.put("viewYn", String.valueOf(viewYn));
		int result = noticeMapper.notiInsertByFile(paramMap);
		if (result < 1) throw new BirdAdminException(BirdAdminErrorCode.INTERNAL_ERROR);
		return true;
	}
	
	/**
	 * <pre>
	 * 1. Comment : 공지사항 리스트
	 * 2. 작성자 : 안지호
	 * 3. 작성일 : 2016. 01. 04
	 * </pre> 
	 * @param sPage
	 * @param title
	 * @return
	 */
	@Transactional(propagation = Propagation.REQUIRED)
	public List<NoticeDto> noticeList(int sPage, String title) {
		PagingDto pagingDto = Util.getPaging(sPage, Value.pageIntLis15);
		List<NoticeDto>Arr = noticeMapper.noticeList(pagingDto.getStart(), pagingDto.getEnd(), title);
		return Arr;
	}
	
	/**
	 * <pre>
	 * 1. Comment : 공지사항 리스트 개수.
	 * 2. 작성자 : 안지호
	 * 3. 작성일 : 2016. 01. 04
	 * </pre>
	 * @param title
	 * @return
	 */
	@Transactional(propagation = Propagation.REQUIRED)
	public int noticeListCnt(String title) {
		return noticeMapper.noticeListCnt(title);
	}
	
	/**
	 * <pre>
	 * 1. Comment : 공지사항 상세
	 * 2. 작성자 : 안지호
	 * 3. 작성일 : 2016. 01. 04
	 * </pre>   
	 * @param noticeIdx
	 * @return
	 */
	@Transactional(propagation = Propagation.REQUIRED)
	public NoticeDto noticeDetail(int noticeIdx) {
		if (noticeIdx < 1) throw new BirdAdminException(BirdAdminErrorCode.BAD_REQUEST);
		NoticeDto noticeDto = noticeMapper.noticeDetail(noticeIdx);
		return noticeDto;
	}
	
	/**
	 * <pre>
	 * 1. Comment : 공지사항 삭제.
	 * 2. 작성자 : 안지호
	 * 3. 작성일 : 2016. 01. 04
	 * </pre> 
	 * @param noticeIdx
	 * @return
	 */
	@Transactional(propagation = Propagation.REQUIRED)
	public boolean noticeDelete(int noticeIdx) {
		if (noticeIdx < 1) throw new BirdAdminException(BirdAdminErrorCode.BAD_REQUEST);
		int result = noticeMapper.deleteNotice(noticeIdx);
		if (result < 1) throw new BirdAdminException(BirdAdminErrorCode.INTERNAL_ERROR);
		return true;
	}

	/**
	 * <pre>
	 * 1. Comment : 공지사항 수정
	 * 2. 작성자 : 안지호
	 * 3. 작성일 : 2016. 01. 04
	 * </pre>  
	 * @param noticeDto
	 * @return
	 */
	@Transactional(propagation = Propagation.REQUIRED)
	public boolean noticeModify(NoticeDto noticeDto) {
		if (noticeDto == null) throw new BirdAdminException(BirdAdminErrorCode.BAD_REQUEST);
		int result = noticeMapper.modifyNotice(noticeDto);
		if (result < 1) throw new BirdAdminException(BirdAdminErrorCode.INTERNAL_ERROR);
		return true;
	}
	
	/**
	 * <pre>
	 * 1. Comment : 공지사항 등록된 이미지 삭제
	 * 2. 작성자 : 안지호
	 * 3. 작성일 : 2016. 01. 04
	 * </pre>   
	 * @param noticeId
	 * @return
	 */
	@Transactional(propagation = Propagation.REQUIRED)
	public boolean noticeImageRemove(int noticeId) {
		if (noticeId < 1) throw new BirdAdminException(BirdAdminErrorCode.BAD_REQUEST);
		int result = noticeMapper.removeNoticeImage(noticeId);
		if (result < 1) throw new BirdAdminException(BirdAdminErrorCode.INTERNAL_ERROR);
		return true;
	}
	
	/**
	 * <pre>
	 * 1. Comment : 팝업공지 리스트
	 * 2. 작성자 : 안지호
	 * 3. 작성일 : 2016. 04. 05
	 * </pre>  
	 * @param sPage
	 * @param title
	 * @return
	 */
	@Transactional(propagation = Propagation.REQUIRED)
	public List<PopupNoticeDto> popupNoticeList(int sPage, String title) {
		PagingDto pagingDto = Util.getPaging(sPage, Value.pageInList10);
		List<PopupNoticeDto>Arr = noticeMapper.popupNoticeList(pagingDto.getStart(), pagingDto.getEnd(), title);
		return Arr;
	}
	
	/**
	 * <pre>
	 * 1. Comment : 팝업공지 리스트 개수
	 * 2. 작성자 : 안지호
	 * 3. 작성일 : 2016. 04. 05
	 * </pre> 
	 * @param title
	 * @return
	 */
	@Transactional(propagation = Propagation.REQUIRED)
	public int popupNoticeListCnt(String title) {
		return noticeMapper.popupNoticeListCnt(title);
	}
	
	/**
	 * <pre>
	 * 1. Comment : 팝업공지 등록
	 * 2. 작성자 : 안지호
	 * 3. 작성일 : 2016. 04. 06
	 * </pre>  
	 * @param title
	 * @param content
	 * @param imgFileName
	 * @param useYn
	 * @param startDate
	 * @param endDate
	 * @param targerUrl
	 * @return
	 */
	@Transactional(propagation = Propagation.REQUIRED)
	public int insertPopupNotice(String title, String content, String imgFileName, 
			int useYn, String startDate, String endDate, String targerUrl) {
		PopupNoticeDto popupNoticeDto = new PopupNoticeDto();
		popupNoticeDto.setTitle(Util.isNullValue(title, ""));
		popupNoticeDto.setContent(Util.isNullValue(content, ""));
		popupNoticeDto.setImgFileName(Util.isNullValue(imgFileName, ""));
		popupNoticeDto.setUseYn(useYn);
		popupNoticeDto.setStartDate(Util.isNullValue(startDate, ""));
		popupNoticeDto.setEndDate(Util.isNullValue(endDate, ""));
		popupNoticeDto.setTargetUrl(Util.isNullValue(targerUrl, ""));
		return noticeMapper.insertPopupNotice(popupNoticeDto);
	}
	
	/**
	 * <pre>
	 * 1. Comment : 팝업공지 수정
	 * 2. 작성자 : 안지호
	 * 3. 작성일 : 2016. 04. 06
	 * </pre>  
	 * @param idx
	 * @param title
	 * @param content
	 * @param imgFileName
	 * @param useYn
	 * @param startDate
	 * @param endDate
	 * @param targetUrl
	 * @return
	 */
	@Transactional(propagation = Propagation.REQUIRED)
	public int modifyPopupNotice(int idx, String title, String content, String imgFileName, 
			int useYn, String startDate, String endDate, String targetUrl) {
		PopupNoticeDto popupNoticeDto = new PopupNoticeDto();
		popupNoticeDto.setIdx(idx);
		popupNoticeDto.setTitle(Util.isNullValue(title, ""));
		popupNoticeDto.setContent(Util.isNullValue(content, ""));
		popupNoticeDto.setImgFileName(Util.isNullValue(imgFileName, ""));
		popupNoticeDto.setUseYn(useYn);
		popupNoticeDto.setStartDate(Util.isNullValue(startDate, ""));
		popupNoticeDto.setEndDate(Util.isNullValue(endDate, ""));
		popupNoticeDto.setTargetUrl(Util.isNullValue(targetUrl, ""));
		return noticeMapper.modifyPopupNotice(popupNoticeDto);
	}
	
	/**
	 * <pre>
	 * 1. Comment : 팝업공지 상세
	 * 2. 작성자 : 안지호
	 * 3. 작성일 : 2016. 04. 06
	 * </pre>  
	 * @param idx
	 * @return
	 */
	@Transactional(propagation = Propagation.REQUIRED)
	public PopupNoticeDto detailPopupNotice(int idx) {
		if (idx < 1) throw new BirdAdminException(BirdAdminErrorCode.BAD_REQUEST);
		return noticeMapper.detailPopupNotice(idx);
	}
	
	/**
	 * <pre>
	 * 1. Comment : 팝업공지 삭제
	 * 2. 작성자 : 안지호
	 * 3. 작성일 : 2016. 04. 06
	 * </pre> 
	 * @param idx
	 * @return
	 */
	@Transactional(propagation = Propagation.REQUIRED)
	public int deletePopNotice(int idx) {
		if (idx < 1) throw new BirdAdminException(BirdAdminErrorCode.BAD_REQUEST);
		return noticeMapper.deletePopNotice(idx);
	}
	
	/**
	 * <pre>
	 * 1. Comment : 버드 메시지팁 리스트
	 * 2. 작성자 : 안지호
	 * 3. 작성일 : 2016. 11. 21
	 * </pre> 
	 * @param sPage
	 * @param sortType
	 * @return
	 */
	@Transactional(propagation = Propagation.REQUIRED)
	public List<BirdTooltipDto> birdTooltipList(int sPage, String sortType) {
		PagingDto pagingDto = Util.getPaging(sPage, Value.pageIntLis15);
		List<BirdTooltipDto> Arr = noticeMapper.birdTooltipList(pagingDto.getStart(), pagingDto.getEnd(), sortType);
		return Arr;
	}
	
	/**
	 * <pre>
	 * 1. Comment : 버드 메시지팁 개수
	 * 2. 작성자 : 안지호
	 * 3. 작성일 : 2016. 11. 21
	 * </pre> 
	 * @param sortType
	 * @return
	 */
	@Transactional(propagation = Propagation.REQUIRED)
	public int birdTooltipListCnt(String sortType) {
		return noticeMapper.birdTooltipListCnt(Util.isNullValue(sortType, ""));
	}
	
	@Transactional(propagation = Propagation.REQUIRED)
	public boolean isOrderPriority(int orderNum) {
		boolean bl = false;
		int cnt = noticeMapper.isOrderPriority(orderNum);
		if (cnt == 0) bl = true;
		return bl;
	}
	
	@Transactional(propagation = Propagation.REQUIRED)
	public boolean insertBirdTip(String firstTooltip, String secondTooltip, int regularYn,
			String startDate, String endDate, int orderYn, int orderPriority, int viewYn) {
		BirdTooltipDto birdTooltipDto = new BirdTooltipDto();
		birdTooltipDto.setFirstTooltip(Util.isNullValue(firstTooltip, ""));
		birdTooltipDto.setSecondTooltip(Util.isNullValue(secondTooltip, ""));
		birdTooltipDto.setRegularYn(regularYn);
		birdTooltipDto.setStartDate(Util.isNullValue(startDate, null));
		birdTooltipDto.setEndDate(Util.isNullValue(DateUtils.getLastDayOclock(endDate), null));
		birdTooltipDto.setOrderYn(orderYn);
		birdTooltipDto.setOrderPriority(orderPriority);
		birdTooltipDto.setViewYn(viewYn);
		
		int result = noticeMapper.insertBirdTooltip(birdTooltipDto);
		if (result < 1) throw new BirdAdminException(BirdAdminErrorCode.INTERNAL_ERROR);
		return true;
	}
	
	@Transactional(propagation = Propagation.REQUIRED)
	public boolean deleteBirdTip(int idx) {
		if (idx < 1) throw new BirdAdminException(BirdAdminErrorCode.BAD_REQUEST);
		int result = noticeMapper.deleteBirdTip(idx);
		if (result < 1)  throw new BirdAdminException(BirdAdminErrorCode.INTERNAL_ERROR);
		return true;
	}
	
	@Transactional(propagation = Propagation.REQUIRED)
	public BirdTooltipDto detailBirdToolTip(int idx) {
		if (idx < 1) throw new BirdAdminException(BirdAdminErrorCode.BAD_REQUEST);
		BirdTooltipDto birdTooltipDto = noticeMapper.detailBirdToolTip(idx);
		return birdTooltipDto;
	}
	
	@Transactional(propagation = Propagation.REQUIRED)
	public boolean updateBirdToolTip(int idx, String firstTooltip, String secondTooltip, int regularYn, 
			String startDate, String endDate, int orderYn, int orderPriority, int viewYn) {
		BirdTooltipDto birdTooltipDto = new BirdTooltipDto();
		birdTooltipDto.setIdx(idx);
		birdTooltipDto.setFirstTooltip(Util.isNullValue(firstTooltip, ""));
		birdTooltipDto.setSecondTooltip(Util.isNullValue(secondTooltip, ""));
		birdTooltipDto.setRegularYn(regularYn);
		birdTooltipDto.setStartDate(Util.isNullValue(startDate, null));
		birdTooltipDto.setEndDate(Util.isNullValue(DateUtils.getLastDayOclock(endDate), null));
		birdTooltipDto.setOrderYn(orderYn);
		birdTooltipDto.setOrderPriority(orderPriority);
		birdTooltipDto.setViewYn(viewYn);
		
		int result = noticeMapper.updateBirdToolTip(birdTooltipDto);
		if (result < 1) throw new BirdAdminException(BirdAdminErrorCode.INTERNAL_ERROR);
		return true;
		
	}
	
}

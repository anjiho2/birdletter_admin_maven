package com.challabros.birdletter.admin.mapper;

import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.challabros.birdletter.admin.dto.BirdTooltipDto;
import com.challabros.birdletter.admin.dto.NoticeDto;
import com.challabros.birdletter.admin.dto.PopupNoticeDto;

public interface NoticeMapper {
	
	/** SELECT **/
	List<NoticeDto>noticeList(@Param("start")String start, @Param("end")String end, @Param("title")String title);
	
	int noticeListCnt(@Param("title")String title);
	
	NoticeDto noticeDetail(@Param("idx")int idx);
	
	List<PopupNoticeDto>popupNoticeList(@Param("start")String start, @Param("end")String end, @Param("title")String title); 
	
	int popupNoticeListCnt(@Param("title")String title);
	
	PopupNoticeDto detailPopupNotice(@Param("idx")int idx);
	
	List<BirdTooltipDto> birdTooltipList(@Param("start")String start, @Param("end")String end, @Param("sortType")String sortType);
	
	int birdTooltipListCnt(@Param("sortType")String sortType);
	
	int isOrderPriority(@Param("orderNum")int orderNum);
	
	BirdTooltipDto detailBirdToolTip(@Param("idx")int idx);
	
	/** INSERT **/
	int notiInsertByFile(HashMap<String, Object>paramMap);
	
	int insertPopupNotice(PopupNoticeDto popupNoticeDto);
	
	int insertBirdTooltip(BirdTooltipDto birdTooltipDto);
	
	/** DELETE **/
	int deleteNotice(@Param("idx")int idx);
	
	int deletePopNotice(@Param("idx")int idx);
	
	int deleteBirdTip(@Param("idx")int idx);
	
	/** UPDATE **/
	int modifyNotice(NoticeDto noticeDto);
	
	int removeNoticeImage(@Param("idx")int idx);
	
	int modifyPopupNotice(PopupNoticeDto popupNoticeDto);
	
	int updateBirdToolTip(BirdTooltipDto birdTooltipDto);
	
}

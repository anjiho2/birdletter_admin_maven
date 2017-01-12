			<!--  S : nav_area-->
			<div class="nav_btn"><a href="#none">메뉴</a></div>
			<div class="side_nav">
				<ul class="nav nav-list">
					<li id="menu_01">
						<a href="javascript:goPage('dashboard', 'dashboardList');"><span class="icon_desktop"></span>대시보드</a>
					</li>
					<li class="sub">
						<a href="#"><span class="icon-users"></span>사용자</a>
						<ul>
							<li id="menu_02_01"><a href="javascript:goUser('list','');"><span class="arrow_triangle-right"></span>사용자 리스트</a></li>
						</ul>
					</li>
					<li class="sub">
						<a href="#none"><span class="icon-cart"></span>아이템</a>
						<ul>
							<!-- <li id="menu_03_01"><a href="javascript:goItem('buyList');"><span class="arrow_triangle-right"></span>아이템 판매 총내역</a></li> -->
							<li id="menu_03_02"><a href="javascript:goItem('productList');"><span class="arrow_triangle-right"></span>스토어 목록</a></li>
							<!-- <li id="menu_03_03"><a href="javascript:goItem('productInsert');"><span class="arrow_triangle-right"></span>스토어 목록 추가</a></li> -->
							<li id="menu_03_05"><a href="javascript:goItem('cornInfoList');"><span class="arrow_triangle-right"></span>콘 판매 정보</a></li>
							<li id="menu_03_06"><a href="javascript:goItem('popcornInfoList');"><span class="arrow_triangle-right"></span>팝콘 판매 정보</a></li>
							<!-- <li id="menu_03_07"><a href="javascript:goItem('defaultItemList');"><span class="arrow_triangle-right"></span>기본아이템 관리</a></li> -->
							<li id="menu_03_08"><a href="javascript:goItem('itemList');"><span class="arrow_triangle-right"></span>아이템 관리</a></li>
							<li id="menu_03_04"><a href="javascript:goItem('collectionList');"><span class="arrow_triangle-right"></span>콜렉션 관리</a></li>
						</ul>
					</li>
					<li class="sub">
						<a href="#none"><span class="icon_bag "></span>판매</a>
						<ul>
							<li id="menu_04_01"><a href="javascript:goSale('cornSaleInfo');"><span class="arrow_triangle-right"></span>콘 판매 내역</a></li>
							<!-- <li id="menu_04_02"><a href="javascript:goSale('popcornSaleInfo');"><span class="arrow_triangle-right"></span>팝콘 교환 내역</a></li> -->
							<li id="menu_04_03"><a href="javascript:goSale('itemSaleRank')"><span class="arrow_triangle-right"></span>아이템 판매 순위</a></li>
							<li id="menu_04_04"><a href="javascript:goItem('buyList');"><span class="arrow_triangle-right"></span>아이템 판매 총내역</a></li>
						</ul>
					</li>
					<li class="sub">
						<a href="#none"><span class="icon-bar-graph"></span>통계</a>
						<ul>
							<!-- <li id="menu_05_01"><a href="javascript:goStatistics('cornPopcornMonthStatistics');"><span class="arrow_triangle-right"></span>콘&amp;팝콘판매 월간통계</a></li> -->
							<!-- <li id="menu_05_02"><a href="javascript:goStatistics('cornPopcornYearStatistics');"><span class="arrow_triangle-right"></span>콘&amp;팝콘판매 년간통계</a></li> -->
							<!-- <li id="menu_05_03"><a href="javascript:goStatistics('userProductBuyStatistics');"><span class="arrow_triangle-right"></span>아이템 판매 사용자별 통계</a></li> -->
							<li id="menu_05_04"><a href="javascript:goStatistics('userStatistics');"><span class="arrow_triangle-right"></span>사용자 통계</a></li>
							<li id="menu_05_05"><a href="javascript:goStatistics('userMessageSendStatistics');"><span class="arrow_triangle-right"></span>메세지 발송 통계</a></li>
						</ul>
					</li>
					<li class="sub">
						<a href="#none"><span class="icon_clipboard"></span>공지사항</a>
						<ul>
							<li id="menu_06_01"><a href="javascript:goNotice('noticeList')"><span class="arrow_triangle-right"></span>공지사항 목록</a></li>
							<li id="menu_06_02"><a href="javascript:goNotice('popupNoticeList')"><span class="arrow_triangle-right"></span>팝업 공지사항 목록</a></li>
							<li id="menu_06_03"><a href="javascript:goNotice('birdTipList')"><span class="arrow_triangle-right"></span>버드 메시지 Tip 목록</a></li>
						</ul>
					</li>
					<li class="sub">
						<a href="#none"><span class="icon-bell"></span>알림</a>
						<ul>
							<li id="menu_07_01"><a href="javascript:goPush('pushList')"><span class="arrow_triangle-right"></span>기념일 알림 목록</a></li>
							<li id="menu_07_02"><a href="javascript:goPush('birthDayPushList')"><span class="arrow_triangle-right"></span>생일 알림 목록</a></li>
						</ul>
					</li>
					<!-- 
					<li class="sub">
						<a href="#none"><span class="icon-pictures"></span>BSM</a>
						<ul>
							<li id="menu_08_01"><a href="javascript:goBsm('bsmInsert')">BSM 등록</a></li>
							<li id="menu_08_02"><a href="javascript:goBsm('bsmList')"><span class="arrow_triangle-right"></span>BSM 리스트</a></li>
							<li id="menu_08_03"><a href="javascript:goBsm('resTypeList')"><span class="arrow_triangle-right"></span>BSM 이미지타입 관리</a></li>
						</ul>
					</li>
					 -->
					<li class="sub">
						<a href="#none"><span class="social_share_square"></span>광장</a>
						<ul>
							<li id="menu_13_01"><a href="javascript:goPage('square', 'squareList')">편지 리스트</a></li>
						</ul>
					</li>
					<li class="sub">
						<a href="#none"><span class="icon_gift"></span>선물</a>
						<ul>
							<li id="menu_14_01"><a href="javascript:goPage('gift', 'giftPresent')">선물함 선물지급</a></li>
							<li id="menu_14_02"><a href="javascript:goPage('gift', 'presentList')">선물함 지급관리</a></li>
							<li id="menu_14_03"><a href="javascript:goPage('gift', 'userPresentList')">선물하기 내역조회</a></li>
						</ul>
					</li>
					<!-- 
					<li class="sub">
						<a href="#none"><span class="icon_gift"></span>이벤트</a>
						<ul>
							<li id="menu_11_01"><a href="javascript:goPage('event', 'basicEventList')"><span class="arrow_triangle-right"></span>이벤트 리스트</a></li>
							<li id="menu_11_02"><a href="javascript:goPage('event', 'eventCategoryList')"><span class="arrow_triangle-right"></span>이벤트 카테고리</a></li>
						</ul>
					</li>
					 -->
					<li class="sub">
						<a href="#none"><span class="icon_heart"></span>하트</a>
						<ul>
							<li id="menu_12_01"><a href="javascript:goPage('heart', 'todayPresent')"><span class="arrow_triangle-right"></span>오늘의 선물</a></li>
						</ul>
					</li>
					<li class="sub">
						<a href="#none"><span class="icon-gear"></span>버드레터 설정</a>
						<ul>
							<li id="menu_09_01"><a href="javascript:goVersion('versionInfo')"><span class="arrow_triangle-right"></span>버전관리 및 서비스상태</a></li>
						</ul>
					</li>
				</ul>
				<br/>
				<!--
				<div class="blockbox grey-line">
					<div class="f_L"><span class="bold">Theme :</span></div>
					<div style="margin-left:49px;">
						<label for="theme-white" class="radio"><input type="radio" name="tm_radio" id="theme-white" value="white">White</label>
						<label for="theme-black" class="radio"><input type="radio" name="tm_radio" id="theme-black" value="black">Black</label>
					</div>
				</div>
				-->
				<!-- <div class="blockbox grey-line">
					<div class="f_L"><span class="bold">좌측 메뉴 고정 :</span></div>
					<div style="margin-left:86px;">
						<label for="vertical_lock" class="checkbox"><input type="checkbox" name="vertical_lock" id="vertical_lock" value="white">잠금</label>
					</div>
				</div>  -->
			</div>
			<!--  E : nav_area-->
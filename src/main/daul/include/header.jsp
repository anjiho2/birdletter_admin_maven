	<!--  S : header-->
	<header id="header" class="group">
		<div class="topbar group">
			<div class="brand">
				<a href="#">버드레터 운영툴</a>
			</div>
		</div>
		<div class="topright group">
			<div class="userinfo">
			<%-- <% if ("playajin".equals(adminId)) { %> --%>
				<p>최광진님 환영합니다.
					<a href="#" class="button tiny grey-gradient" onclick="javascript:goLogout();"><span class="icon-logout"> </span>Logout</a>
				</p>
			<%-- <% } else { %> --%>
				<p>관리자님 환영합니다.
					<a href="#" class="button tiny grey-gradient" onclick="javascript:goLogout();"><span class="icon-logout"> </span>Logout</a>
				</p>
			<%-- <% } %> --%>
			</div>
				<p></p>
			<ul class="subnav">
				<li>
					<a href="#" class="button compact" onclick="javascript:goPage('dashboard', 'dashboardList');"><span class="icon_house"></span></a>
				</li>
				<li class="dropdown">
				</li>
			</ul>
		</div>
	</header>
	<!--  E : header--> 
	
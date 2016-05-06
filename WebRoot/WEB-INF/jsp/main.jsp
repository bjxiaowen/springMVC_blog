<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html lang="zh-CN">
<head>
<meta http-equiv="Content-Type" content="text/html;charset=UTF-8" />
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<base href="<%=basePath%>">

<title>主页</title>
<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
<meta http-equiv="description" content="This is my page">
<link href="resources/css/style.css" rel="stylesheet">
</head>
<!--ExtJs框架开始-->
<link rel="stylesheet" type="text/css"
	href="resources/Ext/resources/css/ext-all.css" />
<script type="text/javascript"
	src="resources/Ext/adapter/ext/ext-base.js"></script>
<link id="theme" rel="stylesheet" type="text/css"
	href="resources/Ext/resources/css/xtheme-blue.css" />
<script type="text/javascript" src="resources/Ext/ext-all.js"></script>
<script src="resources/Ext/src/locale/ext-lang-zh_CN.js"
	type="text/javascript"></script>
<!--ExtJs框架结束-->
<script src="resources/js/pub_article.js" type="text/javascript"></script>
<script src="resources/js/login.js" type="text/javascript"></script>
<script type="text/javascript" src="resources/js/main.js"></script>
<script type="text/javascript" src="resources/js/jquery-1.7.1.js"></script>
<script type="text/javascript" src="resources/js/list.js"></script>
<script type="text/javascript" src="resources/js/move.js"></script>
<link rel="stylesheet" type="text/css" href="resources/css/normal.css" />
<!-- SmartMenus jQuery plugin -->
<script type="text/javascript" src="resources/js/jquery.smartmenus.js"></script>
<!-- SmartMenus core CSS (required) -->
<link href="resources/css/sm-core-css.css" rel="stylesheet"
	type="text/css" />
<!-- "sm-blue" menu theme (optional, you can use your own CSS, too) -->
<link href="resources/css/sm-blue.css" rel="stylesheet" type="text/css" />
<link href="resources/css/buttons.css" rel="stylesheet" type="text/css" />
<script type="text/javascript">
$(function() {
		//初始化标签中的Ext:Qtip属性。
		Ext.QuickTips.init();
		Ext.form.Field.prototype.msgTarget = 'under';
	//新消息提示
	onPageLoad();
	hovers();
		$('#scroller1').click(function(){$('html,body').animate({scrollTop: '0px'}, 500);});
		$('#scroller2').click(function(){$('html,body').animate({scrollTop: $('.centerbox').offset().top}, 500);});
		$('#scroller3').click(function(){$('html,body').animate({scrollTop:$('.Copyright').offset().top}, 500);});
		scroller();
		window.onscroll = window.onresize =  scroller; 
		$('#main-menu').smartmenus({
			subMenusSubOffsetX: 1,
			subMenusSubOffsetY: -8
		});
		showArticle();
        $(window).scroll(scrollToBottom);
  		//showPanel();
  		$(".toggle").toggle(makeBig,makeSmall);
  		var liLeft = $(".liLeft");
  		var liright = $('.liRight');
  		for(var i = 0;i < liLeft.size();i++){
  			$(liLeft[i]).css("background","url(resources/images/li/"+i+".png) no-repeat");
  		}
  		for(var i = 0;i < liLeft.size();i++){
  			$(liright[i]).css("background","url(resources/images/li/"+(i + 12)+".png) no-repeat");
  		}
});
</script>

<style>
</style>

<body>
	<div id="scroller1" class="hand" onmouseover="movein1(this)"
		onmouseout="moveout1(this)">
		<span id="sc1">回到顶部</span>
	</div>
	<div id="scroller2" class="hand" onmouseover="movein2(this)"
		onmouseout="moveout2(this)">
		<span id="sc2">查看微博</span>
	</div>
	<div id="scroller3" class="hand" onmouseover="movein3(this)"
		onmouseout="moveout3(this)">
		<span id="sc3">前往底部</span>
	</div>
	<div class="scrollerTop"></div>
	<div class="scrollerBottom"></div>
	<div class="showinfo">
		<div class="north">
			<br>姓名：<span class="perName">张三</span><br>个性签名：测试测试测试测试
		</div>
		<div class="south">
			关注：345 <span class="line">|</span>粉丝：46<span class="line">|</span>微博：379<br>
			<br> 所在地：广东 <span class="line">|</span><span
				class="perPhoneNumber">电话：12334235</span><br> <input
				type="hidden" value="perId" class="perId"> <br> <input
				type="button" value="关注" class="button orange"
				onclick="attend($('.perId').val())"> <span class="line">|</span><input
				type="button" value="私信" class="button blue">
		</div>
	</div>
	<div>
		<nav role="navigation"> <!-- Sample menu definition -->
		<ul id="main-menu" class="sm sm-blue">
			<li><a id="authority">您好，<span id="ChatUserName"><security:authentication
							property="name" /> </span> </a>
			</li>
			<li><a href="#">首页</a>
			</li>
			<li><a class="hand">操作列表</a>
				<ul>
					<c:url value="/logout" var="logoutUrl" />
					<li><security:authorize access="anonymous">
							<a onclick="sign(0)" class="hand">用户登陆</a>
						</security:authorize>
					</li>
					<security:authorize access="hasAnyRole('ROLE_ADMIN','ROLE_USER')">
						<li><a href="${logoutUrl}" onclick="logout()">退出登录</a>
						</li>
					</security:authorize>
					<li><a onclick="sign(1)" class="hand">用户注册</a>
					</li>
					<li><a onclick="pub_article()" class="hand">发表微博</a>
					</li>
					<li><a onclick="toshow()" class="hand">列表查看</a>
					</li>
				</ul></li>
			<li><a class="hand ">用户权限</a> <security:authentication
					property="authorities" var="authorities" />
				<ul>
					<c:forEach items="${authorities}" var="authority">
						<li><center>${authority.authority}</center>
						</li>
					</c:forEach>
				</ul>
			</li>
			<li><a class="hand blogclass">博客分类</a>
				<ul>
					<li><a href="/">慢生活(3)</a>
					</li>
					<li><a href="/">程序人生(25)</a>
					</li>
					<li><a href="/">经典美文(39)</a>
					</li>
				</ul></li>
			<li><a class="hand" onclick="talkTime()">聊天室</a>
			</li>
			<li><a href="#">下拉菜单测试</a>
				<ul>
					<li><a href="#">Dummy item</a>
					</li>
					<li><a href="#">Dummy item</a>
					</li>
					<li><a href="#" class="disabled">Disabled menu item</a>
					</li>
					<li><a href="#">Dummy item</a>
					</li>
					<li><a href="#">more...</a>
						<ul>
							<li><a href="#">A pretty long text to test the default
									subMenusMaxWidth:20em setting for the sub menus</a>
							</li>
							<li><a href="#">Dummy item</a>
							</li>
							<li><a href="#">more...</a>
								<ul>
									<li><a href="#">Dummy item</a>
									</li>
									<li><a href="#" class="current">A 'current' class item</a>
									</li>
									<li><a href="#">Dummy item</a>
									</li>
									<li><a href="#">more...</a>
										<ul>
											<li><a href="#">subMenusMinWidth</a>
											</li>
											<li><a href="#">10em</a>
											</li>
											<li><a href="#">forced.</a>
											</li>
										</ul></li>
									<li><a href="#">Dummy item</a>
									</li>
									<li><a href="#">Dummy item</a>
									</li>
								</ul></li>
							<li><a href="#">Dummy item</a>
							</li>
							<li><a href="#">Dummy item</a>
							</li>
							<li><a href="#">Dummy item</a>
							</li>
						</ul></li>
				</ul></li>
			<li><a href="#">个人资料</a>
				<ul class="mega-menu">
					<li>
						<!-- The mega drop down contents -->
						<div style="width:200px;max-width:100%;color:brown">
							<div style="padding:15px 15px;">
								<p class="InfoNname">*姓名：${user.name}</p>
								<p class="nickname">*生日：${user.birthday}</p>
								<p>*性别：${user.gender}</p>
								<p class="url">*地区：${user.province}-${user.city}</p>
								<p class="address">*邮箱：${user.email }</p>
								<p class="role">*电话：${user.phoneNumber}</p>
							</div>
						</div></li>
				</ul></li>
			<li><a><input type="text" style="height:2.3em"> <input
					type="button" value="搜索" class="button pink"> </a></li>
		</ul>
		</nav>
	</div>
	<article> <header> <object id="swftitlebar"
		data="resources/images/79514.swf" width="100%" height="70%"
		type="application/x-shockwave-flash">
		<!-- 使用 allowscriptaccess 使 Flash 应用程序可与承载它的 HTML 页通信。
		此参数是必需的，因为 fscommand() 和 getURL() 操作可能导致 javascript 使用 HTML 页的权限，
		而该权限可能与 Flash 应用程序的权限不同。这与跨域安全性有着重要关系。always 允许随时执行脚本操作。 -->
		<param name="allowScriptAccess" value="always">
		<!--“all”:  SWF 文件中允许使用所有网络 API -->
		<param name="allownetworking" value="all">
		<!-- 启用全屏模式设置为”true”，否则设置为”false”（默认值） -->
		<param name="allowFullScreen" value="true">
		<!-- 使您可以使用 Internet Explorer 4.0 中的透明 Flash 内容、绝对定位和分层显示的功能。
		此标记/属性仅在带有 Flash Player ActiveX 控件的 Windows 中有效。 -->
		<param name="wmode" value="transparent">
		<!-- 指定当观众在浏览器中右击 (Windows) 或按住 Command 键单击 (Macintosh) 应用程序区域时将显示的菜单类型
		"true" 显示完整的菜单，让用户使用各种选项增强或控制回放。
		"false" 显示的是一个只包含"关于 Macromedia Flash Player 6"选项和"设置"选项的菜单。
		 -->
		<param name="menu" value="false">
		<!-- 当 width 和 height 值是百分比时，定义应用程序如何放置在浏览器窗口中,
		noScale 对 Flash 内容进行缩放以填充指定区域，不会发生扭曲，
		它会使应用程序保持原始高宽比，但有可能会进行一些裁剪-->
		<param name="scale" value="noScale">
		<!-- 指定缩放的 Flash SWF 文件在由 width 和 height 设置定义的区域内的位置。
		有关这些条件的详细信息，请参阅scale 属性/参数。 -->
		<param name="salign" value="1">
	</object> </header>
	<div class="leftbox" id="menu">
		<a class="a1">热门微博</a><a>微话题</a> <a>找人</a><a>电影</a> <a>听歌</a><a>股票</a>
		<a>播客</a><a>视频</a> <a>旅游</a><a>购物</a> <a>直播</a><a>新闻</a>
	</div>
	<div class="centerbox box">
		<%--<security:authorize access="hasAnyRole('ROLE_ADMIN','ROLE_USER')">
	<h2 id="styleChange"></h2>
	<div id="pub_article"></div>
	</security:authorize>
		--%>
		<c:if test="${!empty errorMsg}">
			<h2>
				<font size="2" color="red">${errorMsg}</font>
			</h2>
		</c:if>
		<c:forEach var="article" varStatus="status" items="${article}">
			<h2>
				<span class="per_detail"><a class="hand"
					onclick="attend(${article.belongUserid})"
					onmouseover="putInfo('${article.belongUserid}')"> <c:if
							test="${!empty article.headImgName }">
							<img src="showImgs?imgKind=headImg" class="headimg">
						</c:if> <c:if test="${empty article.headImgName }">
							<img src="resources/images/head.png" class="headimg">
						</c:if> From ${article.belongUserName }(点击关注) </a> </span>—— ${article.releaseDate}
			</h2>
			<h3 class="title">
				<c:out value="${article.title}" />
			</h3>
			<div class="text">
				<c:if test="${!empty article.imgs}">
					<p class="img">
						<img src="showImgs?articleid=<c:out value="${article.id}"/>"
							alt="">
					</p>
				</c:if>
				<c:if test="${!empty article.musicName}">
					<p class="audio">
						<audio controls="controls"> <source
							src="showMusic?articleid=<c:out value="${article.id}"/>"
							type="audio/mpeg"></audio>
					</p>
				</c:if>
				<c:if test="${!empty article.videoName}">
					<div class="video">
						<button onclick="makeBig()" class="button blue toggle">屏幕大小切换</button>
						<button onclick="playPause()" class="button purple">播放/暂停</button>
						<br>
						<video class="myVideo" width="240" height="160"
							controls="controls"> <source
							src="showVideo?articleid=<c:out value="${article.id}"/>"
							type="video/mp4" /> </video>
					</div>
				</c:if>
				<p>${article.content}</p>
			</div>
			<div class="textfoot">
				<a class="hand" onclick="praise(${article.id})">赞(&nbsp;<span
					id="praise<c:out value="${article.id}" />"><c:out
							value="${article.praiseNum}" /> </span>)</a> <a class="hand"
					onclick="collect(${article.id})">&nbsp;&nbsp;收藏(&nbsp;<span
					id="collect<c:out value="${article.id}" />"><c:out
							value="${article.collectNum}" /> </span>)</a><a class="hand"
					onclick="remark(${article.id},1)">&nbsp;&nbsp;评论(&nbsp;<span
					id="remark<c:out value="${article.id}" />"><c:out
							value="${article.remarkNum}" /> </span>)</a><a class="hand"
					onclick="reproduce(${article.id})">&nbsp;&nbsp;转载(&nbsp;<span
					id="produce<c:out value="${article.id}" />"><c:out
							value="${article.reproduceNum}" /> </span>)</a>
			</div>
			<div id="showremark${article.id}" style="display:none">
				<div class="text" style="text-align:left">
					<!-- css样式在normel.css中 -->
					<input type="text" name="content"
						class="mytxt RemContent${article.id}" placeholder="评论内容">
					<input type="button" value="评论" class="Rembtn"
						onclick="addRemark(${article.id})">
				</div>
				<div class="remdetail${article.id}"></div>
				<div class="textfoot"></div>
			</div>
		</c:forEach>
		<!-- <input type="hidden" class="sign" value=""> -->
		<input type="hidden" class="pageNow" value="1"> <input
			type="hidden" class="pbl" value="">
		<div id="device"></div>
		<c:if test="${!empty user.id}">
			<input type="hidden" id="loginUserid"
				value="<c:out value="${user.id}"/>">
		</c:if>
		<c:if test="${empty user.id }">
			<input type="hidden" id="loginUserid" value="0">
		</c:if>
		<div id="refresh"><%--刷新获取更多的内容--%>
		</div>
	</div>
	<div class="rightbox box">
		<h2>热门微博分类</h2>
		<div class="text">
			<ul>
				<li class="liLeft"><a href="#">综艺</a></li><li class="liRight"><a href="#">社会</a></li>
				<li class="liLeft"><a href="#">明星</a></li><li class="liRight"><a href="#">笑话</a></li>
				<li class="liLeft"><a href="#">情感</a></li><li class="liRight"><a href="#">时尚</a></li>
				<li class="liLeft"><a href="#">星座</a></li><li class="liRight"><a href="#">电视剧</a></li>
				<li class="liLeft"><a href="#">电影</a></li><li class="liRight"><a href="#">体育</a></li>
				<li class="liLeft"><a href="#">读书</a></li><li class="liRight"><a href="#">动漫</a></li>
				<li class="liLeft"><a href="#">理财</a></li><li class="liRight"><a href="#">健身</a></li>
				<li class="liLeft"><a href="#">美食</a></li><li class="liRight"><a href="#">数码</a></li>
				<li class="liLeft"><a href="#">游戏</a></li><li class="liRight"><a href="#">汽车</a></li>
				<li class="liLeft"><a href="#">摄影</a></li><li class="liRight"><a href="#">科普</a></li>
				<li class="liLeft"><a href="#">健康</a></li><li class="liRight"><a href="#">设计</a></li>
				<li class="liLeft"><a href="#">视频</a></li><li class="liRight"><a href="#">艺术</a></li>
			</ul>
		</div>
	</div>
	<div class="blank"></div>
	<div class="Copyright">
		<a href="/">帮助中心</a> <a href="/">空间客服</a> <a href="/">投诉中心</a> <a
			href="/">空间协议</a>
	</div>
	</article>
</body>
<script type="text/javascript"></script>
</html>

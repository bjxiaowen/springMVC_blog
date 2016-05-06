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
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<base href="<%=basePath%>">

<title>主页</title>

<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
<meta http-equiv="description" content="This is my page">
<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
<link href="resources/css/styles.css" rel="stylesheet">

</head>

<!--ExtJs框架开始-->
<script type="text/javascript"
	src="resources/Ext/adapter/ext/ext-base.js"></script>
<script type="text/javascript" src="resources/Ext/ext-all.js"></script>
<script src="resources/Ext/src/locale/ext-lang-zh_CN.js"
	type="text/javascript"></script>
<script src="resources/js/pub_article.js" type="text/javascript"></script>
<script src="resources/js/login.js" type="text/javascript"></script>
<link rel="stylesheet" type="text/css"
	href="resources/Ext/resources/css/ext-all.css" />
<!--ExtJs框架结束-->
<script type="text/javascript" src="resources/js/main.js"></script>
<script type="text/javascript" src="resources/js/jquery-1.7.1.js"></script>
<script type="text/javascript"
	src="resources/js/jquery.grid-a-licious.min.js"></script>
<link rel="stylesheet" type="text/css" href="resources/css/normal.css" />
<style type="text/css">
</style>
<script type="text/javascript">
$(document).ready(function () {
                $(window).scroll(function () {
                    if(($(window).scrollTop() + $(window).height()) == $(document).height())
                    {
                 	   showArticle();
                 	   if($(".pbl").val() == "none"){
                 	   	alert("已加载完毕！");
                 	   	return;
                 	   }
                        $("#device").gridalicious('append', makeboxes());
                        $(".pageNow").val(parseInt($(".pageNow").val()) + 1);
                    }
                });
                 $("#device").gridalicious({
                gutter: 10,
                width: 700,
                animate: true,
                animationOptions: {
                        speed: 150,
                        duration: 400,
                        complete: onComplete
                },
            });
            
	makeboxes = function() {
     var boxes = new Array;   
			div = $('<div></div>').addClass('item');
			p =$(".pbl").val();
			alert(p);
   	         div.append(p);
                boxes.push(div);
                return boxes;
            };
            //未使用
            function onComplete(data) { 
            } 
            //ajax展示文章(5个）
function showArticle(){
	pageNow = $(".pageNow").val();
	$.ajax({
		type : "get",
		url : "ajaxShowArticle",
		data:{
			pageNow : pageNow
		},
		dataType : " json",
		success : function(data) {
		art = data;
		if(art == "none"){
			$(".pbl").val("none");
			return;
		}
			$(".pbl").val("	<h2>From "+art.belongUserName+"—— "+art.releaseDate+"</h2><h3 class=\"title\">"+art.title+"</h3>"+
		"	<div class=\"text\"><p>"+art.content+"</p></div><div class=\"textfoot\">"+
		"		<a class=\"hand\" onclick=\"praise("+art.id+"})\">赞(<span id=\"praise"+art.id+"\>"+art.praiseNum+"</span>)</a> <a class=\"hand\""+
				"	onclick=\"attend("+art.id+")\">关注</a><a class=\"hand\" onclick=\"remark("+art.id+",1)\">评论</a><a class=\"hand\""+
				"	onclick=\"reproduce("+art.id+")\">转载</a></div><div id=\"remark"+art.id+" style=\"display:none\"><div class=\"text\">"+
			"		<input type=\"text\" name=\"content\"	class=\"mytxt RemContent"+art.id+" placeholder=\"评论内容\">"+
			"	<input type=\"button\" value=\"评论\" class=\"Rembtn\" onclick=\"addRemark("+art.id+")\"></div>"+
			"	<div class=\"data"+art.id+"\"></div><div class=\"textfoot\"></div>	</div>" );
		//<p class=\"img\"><img src=\"showImgs?articleid="+art.id+"\"></p>
		}
	});
}
            
});
</script>
<body>

	<article> <header>
	<div class="toptitle">
		<font size=3 color="#D2691E"><br> <br>您好，<security:authentication
				property="name" />&nbsp;</font>
		<h1>拾忆点滴，谦卑远航</h1>
	</div>

	<object id="swftitlebar" data="resources/images/79514.swf" width="100%"
		height="220" type="application/x-shockwave-flash">

		<param name="allowScriptAccess" value="always">
		<param name="allownetworking" value="all">
		<param name="allowFullScreen" value="true">
		<param name="wmode" value="transparent">
		<param name="menu" value="false">
		<param name="scale" value="noScale">
		<param name="salign" value="1">
	</object> </header>
	<div class="leftbox">
		<div class="vcard box">
			<h2 style="posstion:absolute;">
				<div>
					<h2>
						<ul>
							<font>用户权限： </font>
							<br>
							<security:authentication property="authorities" var="authorities" />
							<ul>
								<c:forEach items="${authorities}" var="authority">
									<li>${authority.authority}</li>
								</c:forEach>
							</ul>
						</ul>
					</h2>
				</div>
				<center>
					<div>
						<c:url value="/logout" var="logoutUrl" />
						<br> <a onclick="login()" class="hand">登陆</a> <br> <a
							href="${logoutUrl}" onclick="logout()">退出登录</a> <br> <a
							onclick="pub_article()" class="hand">发表微博</a> <br> <a
							href="attendList">关注列表</a> <br> <a href="blogLIst">个人博录</a>
					</div>
					<security:authorize
						access="hasAnyRole('ROLE_ADMIN','ROLE_USER','ROLE_BOSS')">
						<form name="form1" action="user/list.html" method='GET'>
							<input type="hidden" name="pageNow" value="1" /> <input
								type="hidden" name="orderType" value="id" /> <input
								type="hidden" name="sortInverse" value="1" /> <a
								href="javascript:document.form1.submit();">用户管理界面</a>
						</form>
					</security:authorize>
			</h2>
			</center>
			<h2>个人资料</h2>
			<img src="resources/images/180.jpg" class="photo">
			<p class="fn">姓名：曾豪</p>
			<p class="nickname">兴趣：</p>
			<p class="url">职业：学生</p>
			<p class="address">专业：电子信息工程</p>
			<p class="role">现居：武汉市华科大</p>
		</div>
		<div class="blogclass box">
			<h2>博客分类</h2>
			<ul>
				<li><a href="/">慢生活(3)</a></li>
				<li><a href="/">程序人生(25)</a></li>
				<li><a href="/">经典美文(39)</a></li>
			</ul>
		</div>
	</div>
	<div class="rightbox box">
		<c:if test="${!empty errorMsg}">
			<h2>
				<font size="2" color="red">${errorMsg}</font>
			</h2>
		</c:if>
		<c:forEach var="article" varStatus="status" items="${article}">
			<h2>From ${article.belongUserName } —— ${article.releaseDate}</h2>
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
				<p>${article.content}</p>
			</div>
			<div class="textfoot">
				<a class="hand" onclick="praise(${article.id})">赞(<span
					id="praise<c:out value="${article.id}" />"><c:out
							value="${article.praiseNum}" /> </span>)</a> <a class="hand"
					onclick="attend(${article.id})">关注</a><a class="hand"
					onclick="remark(${article.id},1)">评论</a><a class="hand"
					onclick="reproduce(${article.id})">转载</a>
			</div>
			<div id="remark${article.id}" style="display:none">
				<div class="text">
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
		<input type="hidden" class="pageNow" value="0"> <input
			type="hidden" class="showNum" value="0">
			<input type="hidden" class="pbl" value="">
		<div id="device" class="gridalicious"></div>
	</div>
	<div class="blank"></div>
	<div class="Copyright">
		<a href="/">帮助中心</a> <a href="/">空间客服</a> <a href="/">投诉中心</a> <a
			href="/">空间协议</a>
		<p>Design by DanceSmile</p>
	</div>
	</article>
</body>
</html>

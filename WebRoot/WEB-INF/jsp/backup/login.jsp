<%@ page language="java"
	import="java.util.*,com.zeng.entity.*,com.utils.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<base href="<%=basePath%>">

<title>登陆主页</title>
<link rel="stylesheet" href="resources/css/model.css" />
<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
<meta http-equiv="description" content="This is my page">
<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->

</head>

<body background="resources/images/background.jpg" style="margin:0;">
	<div>
		<h1>欢迎使用Twitter。</h1>
	</div>
	<div class="top1">
		<div class="tp">
			<a href="#"><img
				src="http://zixun.tuan800.com/uploads/allimg/120607/18_120607093403_1.jpg"
				alt=" " style="height:24px;width:24px;" /> </a>
		</div>
		<div class="language-choice">
			<form name="language" action="save.php" method="post">
				<div>
					<span style="position:relative;font-size:8px">语言：</span> <span
						style="position:relative;top:4px;height:7px;"><select
						name="select">
							<option value="简体中文" selected="selected">中文</option>
							<option value="日文">日本語</option>
							<option value="印度尼西亚文">Bahasa Indonesia</option>
							<option value="马来文">Bahasa Melayu</option>
							<option value="匈牙利文">Magyar</option>
							<option value="丹麦文">Dansk</option>
							<option value="德文">Deutsch</option>
							<option value="英文">English</option>
							<option value="英式英文">English UK</option>
							<option value="意大利文">Italiano</option>
							<option value="菲律宾文">Filipino</option>
					</select> </span>
				</div>
			</form>
		</div>
	</div>
	<div class="h3">
		<h3>联系你的好友和更多精彩。获取你感兴趣的实时更新。并通过每个视角观看事件实时呈现。</h3>
	</div>

	<div class="user-out" style="margin-left:810px; margin-top:85px;">
		<div class="user-in">
			<form name="user" action=" j_spring_security_check" method="post">
				 <input type="text" name="j_username" placeholder="手机、邮件地址或用户名"
					style="height:25px;width:238px;"
					value="${sessionScope['SPRING_SECURITY_LAST_USERNAME']}"/><br>
				<br /> <input type="password" name=j_password placeholder="密码"
					style="height:25px;width:170px" /> <input type="submit" value="登陆"
					class="sb1" /> <br />
				<div style="position:relative;">
					<input type="checkbox"id="_spring_security_remember_me" name="_spring_security_remember_me" /><label for="123">记住我</label>
					&nbsp;&nbsp;&nbsp;<a href="user/forgetPassword.html"
						style="color:#0066CC">忘记密码？</a> <a href="user/main.html"><font color="brown" >游客访问</font></a>
				</div>
				<c:if test="${!empty errorMsg}">
					<font size="2" color="red">${errorMsg}</font>
				</c:if>
				<c:if test="${not empty param.error}">
					<font color="red">Login error.<br /> </font>
						Reason:${sessionScope["SPRING_SECURITY_LAST_EXCEPTION"].message}
				</c:if>
			</form>
		</div>
	</div>
	<div class="new-user-out" style="margin-left:810px; margin-top:35px;">
		<form name="new user" action="user/toregister.html" method="get">
			<span
				style="font-family:Geneva, Arial, Helvetica, sans-serif;font-size:17px;position:relative;top:8px;left:6px;">新来twitter？注册</span><br />
			<hr
				/  style="height:1px;border:none;border-top:1px dashed;color:#CCFFCC">
			<div class="new-user-in">
				<input type="newuesr" name="userName" placeholder="全名"
					style="height:25px;width:241px;margin:3px auto" /> <br /> <input
					type="mail" name="email" placeholder="邮件地址"
					style="height:25px;width:241px;margin:3px auto" /> <br /> <input
					type="password" name="newpassword" placeholder="密码"
					style="height:25px;width:243px;margin:3px auto" /> <br /> <input
					type="submit" value="注册twitter" class="sb2"
					style="position:relative;left:147px;font-size:14px" />
			</div>
		</form>
	</div>
	<div class="sta" style="top:330px;position:absolute;margin-left:250px">
		<a href="#" style="font-weight:bold;font-size:16px">Our gorgeous
			Earth at night under a #fullmoon</a><br />
		<p class="username">
			<a href="#" style="font-size:13px">由 @<span>redbull</span> 拍摄及发布</a>
		</p>
		<a href="#" style="font-size:16px;">10:01 AM - 10 Jul 2014</a>
	</div>
	<div class="list"
		style=" position:absolute;margin-left:350px;margin-top:105px; ">
		<a href="#">关于</a> <a href="#">帮助</a> <a href="#">博客</a> <a href="#">状态</a>
		<a href="#">工作机会</a> <a href="#">条款</a> <a href="#">隐私</a> <a href="#">Cookies</a>
		<a href="#">广告信息</a> <a href="#">商标</a> <a href="#">广告</a> <a href="#">企业</a>
		<a href="#">媒体</a> <a href="#">开发者</a> <a href="#">目录</a> <a>&copy;
			2015 Twitter</a>
	</div>
</body>
</html>

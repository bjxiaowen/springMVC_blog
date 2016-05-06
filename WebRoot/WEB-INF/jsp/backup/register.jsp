<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<base href="<%=basePath%>">

<title>注册页面</title>

<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
<meta http-equiv="description" content="This is my page">
<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
<!--ExtJs框架开始-->
<script type="text/javascript"
	src="resources/Ext/adapter/ext/ext-base.js"></script>
<script type="text/javascript" src="resources/Ext/ext-all.js"></script>
<script src="resources/Ext/src/locale/ext-lang-zh_CN.js"
	type="text/javascript"></script>
<link rel="stylesheet" type="text/css"
	href="resources/Ext/resources/css/ext-all.css" />
<!--ExtJs框架结束-->
<script type="text/javascript" src="resources/js/jquery-1.7.1.js"></script>
<!--[if lt IE 9]>
<script src="js/modernizr.js"></script>
<![endif]-->
</head>
<script language="javascript">
	var flag = false;
	function checkUserid() {
		$.ajax({
			dataType : "html",
			url : 'checkUserName',// 跳转
			data : {
				userName : $("#userName").val()
			},
			type : 'post',
			success : function(data) {
				if (data == "true") { // 用户id已经存在了
					document.getElementById("msg").innerHTML = "该用户已被注册";
					flag = false;
				} else {
					document.getElementById("msg").innerHTML = "该用户可以注册！";
					flag = true;
				}
			}
		});
	}
	function checksame() {
		if (document.getElementById("password1").value != document
				.getElementById("password").value) {
			document.getElementById("msg").innerHTML = "两次输入密码不一致！";
			flag = false;
		} else {
			document.getElementById("msg").innerHTML = "";
			falg = true;
		}
	}
	function checkForm() {
		return flag;
	}
	Ext.onReady(function() {
		// 使用表单提示  
		Ext.QuickTips.init();
		Ext.form.Field.prototype.msgTarget = 'side';// 提示显示方式
		var _kssj = new Ext.form.DateField({
			applyTo : 'birthday',//节点的id  
			style : 'position:relatice;',
			editable : true,
			format : 'Y-m-d',
			emptyText : ''
		});
	});
</script>
<style type="text/css">
.center{text-align:center}
</style>
<body>
	<h1 class="center">新博客注册</h1>
	<form:form modelAttribute="user" method="post" action="register"
		onsubmit="return checkForm()">
		<span id="msg" style="color:red;font-size:20px"></span>
		<div style="text-align:center"><label >账号：</label>
		<input type="text" name="userName"  id="userName"
			onblur="checkUserid()">
		<form:errors path="userName" cssStyle="color:red" ></form:errors>
		<br><br>
		<label >密码：</label>
		<input type="password" name="password" id="password" >
		<br>
		<form:errors path="password" cssStyle="color:red" ></form:errors>
		<br>
		<label >确认密码：</label>
		<input type="password" name="password1" id="password1" 
			onblur="checksame()">
		<br>
		<form:errors path="password" cssStyle="color:red" ></form:errors>
		<br>
		<label >邮箱：</label>
		<input type="text" name="email" >
		<br>
		<form:errors path="email" cssStyle="color:red" ></form:errors>
		<br>
		<label >昵称：</label>
		<input type="text" name="name" >
		<br>
		<form:errors path="name" cssStyle="color:red" ></form:errors>
		<br>
		<input id="birthday" name="birthday" type="text" placeholder="出生日期" />
		<!-- 	<label >出生日期：</label> -->
		</div>
		<br>
		<div class="center"><input type="submit" value="注册" />
		<input type="reset" value="重置" /></div>
		<br>
	</form:form>
</body>
</html>

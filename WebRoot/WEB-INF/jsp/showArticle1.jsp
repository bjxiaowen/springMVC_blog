<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>文章首页</title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->

<script type="text/javascript">
</script>
  </head>
  <body>
	<h2>
				<a class="hand"
					onclick="attend(${article.belongUserid})">From ${article.belongUserName }(点击关注) </a>——
				${article.releaseDate}
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
				<div class="text">
					<input type="text" name="content"
						class="mytxt RemContent${article.id}" placeholder="评论内容">
					<input type="button" value="评论" class="Rembtn"
						onclick="addRemark(${article.id})">
				</div>
				<div class="remdetail${article.id}"></div>
				<div class="textfoot"></div>
			</div>
  </body>
</html>

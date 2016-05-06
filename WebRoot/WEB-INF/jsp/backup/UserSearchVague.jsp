<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
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

<title>My JSP 'userlist.jsp' starting page</title>

<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
<meta http-equiv="description" content="This is my page">

    <link rel="stylesheet" type="text/css" href="resources/css/common.css"/>
    <link rel="stylesheet" type="text/css" href="resources/css/main.css"/>
    <script type="text/javascript" src="resources/js/libs/modernizr.min.js"></script>
    <script type="text/javascript" src="resources/js/jquery-1.6.2.min.js"></script> 
</head>
<script type="text/javascript">
	function submitForm() {
		var frm = document.myForm;
		frm.submit();
	} 
	$(document).ready(function(){
				$(".list_tb tr").mouseover(function(){
					$(this).addClass("highlight");
				}).mouseout(function(){
					$(this).removeClass("highlight");
				})
			});
</script>
<body>

<div class="topbar-wrap white">
    <div class="topbar-inner clearfix">
        <div class="topbar-logo-wrap clearfix">
            <h1 class="topbar-logo none"><a href="index.html" class="navbar-brand">后台管理</a></h1>
            <ul class="navbar-list clearfix">
                <li><a class="on" href="manager.jsp">首页</a></li>
                <li><a href="main.jsp" target="_blank">网站首页</a></li>
            </ul>
        </div>
        <div class="top-info-wrap">
                <ul class="top-info-list clearfix">
                <li>  		 
                	<form name="form1" action="user/list.html" method='GET'>  
					<input type="hidden" name="pageNow" value="1"/>  
					<a href="javascript:document.form1.submit();">用户管理界面</a>  
			 	 	</form> 
 	 			</li>
                <li><a href="user/success.html">博客主页</a></li>
                <li><a href="user/loginOut.html">退出</a></li>
            </ul>
        </div>
    </div>
</div>
<div class="container clearfix">
    <div class="sidebar-wrap">
        <div class="sidebar-title">
            <h1>菜单</h1>
        </div>
        <div class="sidebar-content">
            <ul class="sidebar-list">
                <li>
                    <i class="icon-font">&#xe003;</i>常用操作
                    <ul class="sub-menu">
                        <li><a href="design.html"><i class="icon-font">&#xe005;</i>博文管理</a></li>
                        <li><a href="design.html"><i class="icon-font">&#xe006;</i>分类管理</a></li>
                        <li><a href="design.html"><i class="icon-font">&#xe004;</i>留言管理</a></li>
                        <li><a href="design.html"><i class="icon-font">&#xe012;</i>评论管理</a></li>
                        <li><a href="user_manager.jsp"><i class="icon-font">&#xe052;</i>用户管理</a></li>
                        <li><a href="design.html"><i class="icon-font">&#xe033;</i>友情链接</a></li>
                    </ul>
                </li>
                <li>
                    <i class="icon-font">&#xe018;</i>系统管理
                    <ul class="sub-menu">
                        <li><a href="system.html"><i class="icon-font">&#xe017;</i>系统设置</a></li>
                        <li><a href="system.html"><i class="icon-font">&#xe037;</i>清理缓存</a></li>
                        <li><a href="system.html"><i class="icon-font">&#xe046;</i>数据备份</a></li>
                        <li><a href="system.html"><i class="icon-font">&#xe045;</i>数据还原</a></li>
                    </ul>
                </li>
            </ul>
        </div>
    </div>
    <!--/sidebar-->
    <div class="main-wrap">

        <div class="crumb-wrap">
            <div class="crumb-list"><i class="icon-font"></i><a href="index.html">首页</a><span class="crumb-step">
            &gt;</span><span class="crumb-name"><a href="user/list.html?pageNow=1">用户管理</a></span>
            <span class="crumb-step">
            &gt;</span><span class="crumb-name">查询结果</span>
         </div>
        </div>
        <div class="result-wrap">
                <div class="result-title">
                    <div class="result-list">
                        <a href="user_manager_newuser.jsp"><i class="icon-font"></i>新增用户</a>
                        <a id="batchDel" href="javascript:void(0)"><i class="icon-font"></i>批量删除</a>
                    </div>
                </div>
                <div class="result-content">
                    <table class="result-tab" width="100%">
                        <tr>
                            <th class="tc" width="5%"><input class="allChoose" name="" type="checkbox"></th>
                            <th><center>序号</center></th>
                            <th><center>名称</center></th>
                            <th><center>账号</center></th>
                            <th><center>密码</center></th>
                            <th><center>性别</center></th>
                            <th><center>邮箱</center></th>
                            <th><center>密码验证问题</center></th>
                            <th><center>密码验证答案</center></th>
                            <th><center>修改信息</center></th>
                            <th><center>删除用户</center></th>
                          </tr>
                        <c:if test="${!empty errorMsg}">
				    		<tr><center><font size="5" color="red">${errorMsg}</font></center></tr>
					    </c:if>
						<c:set var="page" value="${page}" />
						<c:forEach var="user" varStatus="status" items="${userVague}">
						<c:if test="${status.count%2==1}">
						<tr style="background-color:#F7FFFF">
						</c:if>
						<c:if  test="${status.count%2==0}">
						<tr style="background-color:white">
						</c:if>
							<td class="tc"><input name="id[]" value="58" type="checkbox" width="2%"></td>
							<td width="4%"><center><input class="common-input sort-input" name="ord[]" value="${user.id}" type="text"></center></td>
							<td width="10%"><c:out value="${user.name}"/></td>
							<td width="10%"><c:out value="${user.userName}"/></td>
							<td width="10%"><c:out value="${user.password}"/></td>
							<td width="5%"><c:out value="${user.gender}"/></td>
							<td width="10%"><c:out value="${user.email}"/></td>
							<td width="15%"><c:out value="${user.question}"/></td>
							<td width="15%"><c:out value="${user.answer}"/></td>
							<td width="7%"><a href="user/update.html?id=<c:out value="${user.id}"/>">修改</a></td>
							<td width="7%"><a href="user/delete.html?id=<c:out value="${user.id}"/>"onClick="if(!confirm('确定删除该信息吗？'))return false;else return true;">删除</a></td>
						</tr>
						</c:forEach>
					</table>
			
					<center>
					<font size="2">共</font>
					<c:out value="${page.totalPage}"/>
					<font size="2">页   &nbsp;&nbsp;当前第</font>
					<c:out value="${page.pageNow}"/>
					<font size="2">页&nbsp;&nbsp; 共</font>
					<c:out value="${page.totalSize}"/>
					<font size="2">条记录&nbsp;&nbsp;
					<!-- 首页始终显示第一页 -->
					<a href="user/search.html?pageNow=1&keywords=${keywords}&SearchType=${SearchType}&informationType=${informationType}">首页</a>
					<!-- 如果有前一页就提交前一页的pageNow值 -->
					<c:choose>
						<c:when test="${page.hasPre}">
							<a href="user/search.html?pageNow=${page.pageNow-1}&keywords=${keywords}&SearchType=${SearchType}&informationType=${informationType}">上一页</a>
						</c:when>
						<c:otherwise>
							<a href="user/search.html?pageNow=1&keywords=${keywords}&SearchType=${SearchType}&informationType=${informationType}">上一页</a>
						</c:otherwise>
					</c:choose>
					<c:choose>
						<c:when test="${page.hasNext}">
							<a href="user/search.html?pageNow=${page.pageNow+1}&keywords=${keywords}&SearchType=${SearchType}&informationType=${informationType}">下一页</a>
						</c:when>
						<c:otherwise>
							<a href="user/search.html?pageNow=${page.totalPage}&keywords=${keywords}&SearchType=${SearchType}&informationType=${informationType}">下一页</a>
						</c:otherwise>
					</c:choose>
					<!-- 尾页始终提交最后一页的pageNow值 -->
					<a href="user/search.html?pageNow=${page.totalPage}&keywords=${keywords}&SearchType=${SearchType}&informationType=${informationType}">尾页</a>
					&nbsp;跳转到:
					</font>
					<form name="myForm" action="user/search.html" method="get" style="display:inline;">
						<input type="hidden" name="keywords" value="${keywords}"/>
						<input type="hidden" name="SearchType" value="${SearchType}"/>
						<select size="1" name="pageNow" style=" height: 18" onchange="submitForm()"> 
						<c:forEach begin="1" end="${page.totalPage}" varStatus="status">
						<option value="${status.index}"${status.index == page.pageNow ? 'selected' : ''}>
							${status.index}
						</option>
						</c:forEach>
						</select>
					</form>	
					</center>
					</div>
				</div>
			</div>	
	</div>
</body>
</html>

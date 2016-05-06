<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>My JSP 'manager.jsp' starting page</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
    <meta charset="utf-8">
    <title>后台管理</title>
    <link rel="stylesheet" type="text/css" href="resources/css/common.css"/>
    <link rel="stylesheet" type="text/css" href="resources/css/main.css"/>
    <script type="text/javascript" src="resources/js/libs/modernizr.min.js"></script>
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
            <form name="myform" id="myform" method="post">
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
                            <th>序号</th>
                            <th>名称</th>
                            <th>账号</th>
                            <th>密码</th>
                            <th>性别</th>
                            <th>邮箱</th>
                            <th>密码验证问题</th>
                            <th>密码验证答案</th>
                            <th>修改信息</th>
                            <th>删除用户</th>
                        </tr>
<%--用户信息查询结果 --%>

					    <c:if test="${!empty usearch}">
						 <tr>
                            <td class="tc"><input name="id[]" value="58" type="checkbox"></td>
                            <td>
                                <input name="ids[]" value="58" type="hidden">
                                <input class="common-input sort-input" name="ord[]" value="${user.id}" type="text">
                            </td>
                            <td>${usearch.name}</td>
                            <td>${usearch.userName}</td>
                            <td>${usearch.password}</td>
                            <td>${usearch.gender}</td>
                            <td>${usearch.email}</td>
                            <td>${usearch.question}</td>
                            <td>${usearch.answer}</td>                             
                            <td width="10%"><a href="user/update.html?id=<c:out value="${user.id}"/>">修改</a></td>
							<td width="10%"><a href="user/delete.html?id=<c:out value="${user.id}"/>"onClick="if(!confirm('确定删除该信息吗？'))return false;else return true;">删除</a></td>
                        </tr> 
                        </c:if>
                       	<c:if test="${!empty errorMsg}">
				    		<tr><center><font size="5" color="red">${errorMsg}</font></center></tr>
					    </c:if>
					</div>	
                    </table>
                </div>
            </form>
        </div>
    <!--/main-->
</div>
</body>

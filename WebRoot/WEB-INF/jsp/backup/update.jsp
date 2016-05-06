<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>My JSP 'update.jsp' starting page</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	
	<link rel="stylesheet" type="text/css" href="resources/css/list.css">
	 <link rel="stylesheet" type="text/css" href="resources/css/common.css"/>
    <link rel="stylesheet" type="text/css" href="resources/css/main.css"/>
    <script type="text/javascript" src="js/libs/modernizr.min.js"></script>

  </head>
  
  <body>
  <div class="topbar-wrap white">
    <div class="topbar-inner clearfix">
        <div class="topbar-logo-wrap clearfix">
            <h1 class="topbar-logo none"><a href="index.html" class="navbar-brand">后台管理</a></h1>
            <ul class="navbar-list clearfix">
                <li><a class="on" href="index.html">首页</a></li>
                <li><a href="#" target="_blank">网站首页</a></li>
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
                    <a href="#"><i class="icon-font">&#xe003;</i>常用操作</a>
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
                    <a href="#"><i class="icon-font">&#xe018;</i>系统管理</a>
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
            <div class="crumb-list"><i class="icon-font"></i><a href="manager.jsp">首页</a>
            <span class="crumb-step">&gt;</span><span class="crumb-name"><a href="user/list.html?pageNow=1">用户管理</a></span>
            <span class="crumb-step">&gt;</span><span>修改用户</span></div>
        </div>
				<div class="result-content">
				<c:set var="user" value="${user}" />
    				
				<form action="user/update" method="post">
                    <table class="result-tab" width="100%" >           
                        <tr>    
                        	<th>编号</th>
                            <th>名称</th>
                            <th>账号</th>
                            <th>密码</th>
                            <th>性别</th>
                            <th>邮箱</th>
                            <th>密码验证问题</th>
                            <th>密码验证答案</th>
                        </tr>
						<input type="hidden" name="id" value="<c:out value="${user.id }"/>" readonly/>
						<tr >	
							<td >${user.id }</td>
							<td ><input name="name" type="text" value="${user.name }"style="height:30px;width:150px"/></td>
							<td ><input name="userName" type="text" value="<c:out value="${user.userName}"/>" style="height:30px;width:150px"></td>
                            <td ><input name="password" type="password" value="<c:out value="${user.password }"/>" style="height:30px;width:150px"></td>
                            <td ><input type="radio" name="gender" value="男" checked/>男<input type="radio" name="gender" value="女"/>女</td>
                            <td ><input name="email" type="text" value="${user.email}"/></td>
                            <td>
                            	<select name="question" style="position:relative; top:16px;height:25px;width:150px">
								    <option value="你的父亲的名字是？">你的父亲的名字是？
									<option value="你的母亲的名字是？">你的母亲的名字是？
								    <option value="你的生日是多少？">你的生日是多少？
								    <option value="你最喜欢的颜色是？">你最喜欢的颜色是？
								    <option value="你的工作是什么？">你的工作是什么？			   		    				    				    
								 </select><br/><br/>
							</td>
                            <td><input name="answer" type="text" value="${user.answer}" style="height:30px;width:150px"/></td>
                        </tr>
  						</table>
  						<center>
  						<div>&nbsp</div>
  						<div>
							<input type="submit" class="button" value="修改" 
			                style=" height: 45px;width: 90px;background-color:#0099FF;	border:hidden;#0033CC;border-radius:5px;"/>
			                &nbsp&nbsp&nbsp<input type="reset" value="重置"
			                style=" height: 45px;width: 90px;background-color:green;	border:hidden;#0033CC;border-radius:5px;"/>
  						  </div>
  						</center>				
  					</form>
  </div>
 </div>
</div>
  </body>
</html>

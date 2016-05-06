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
<!-- 页面布局css -->
<link href="resources/css/list.css" rel="stylesheet">
<link rel="stylesheet" href="resources/css/liststyle.css">
</head>
<!--ExtJs框架开始-->
<link rel="stylesheet" type="text/css"
	href="resources/Ext/resources/css/ext-all.css" />
<link id="theme" rel="stylesheet" type="text/css"
	href="resources/Ext/resources/css/ext-all-xtheme-gray.css" />
<script type="text/javascript"
	src="resources/Ext/adapter/ext/ext-base.js"></script>
<script type="text/javascript" src="resources/Ext/ext-all.js"></script>
<script src="resources/Ext/src/locale/ext-lang-zh_CN.js"
	type="text/javascript"></script>
<!--ExtJs框架结束-->
<script src="resources/js/pub_article.js" type="text/javascript"></script>
<script src="resources/js/login.js" type="text/javascript"></script>

<script type="text/javascript" src="resources/js/main.js"></script>
<script type="text/javascript" src="resources/js/jquery-1.7.1.js"></script>
<script type="text/javascript"
	src="resources/js/jquery.grid-a-licious.min.js"></script>
<script type="text/javascript" src="resources/js/list.js"></script>
<link rel="stylesheet" type="text/css" href="resources/css/normal.css" />
<!-- SmartMenus jQuery plugin -->
<script type="text/javascript" src="resources/js/jquery.smartmenus.js"></script>
<!-- SmartMenus core CSS (required) -->
<link href="resources/css/sm-core-css.css" rel="stylesheet"
	type="text/css" />
<!-- "sm-blue" menu theme (optional, you can use your own CSS, too) -->
<link href="resources/css/sm-blue.css" rel="stylesheet" type="text/css" />
<script type="text/javascript">
	// SmartMenus jQuery init -
	$(function() {
		$('#main-menu').smartmenus({
			subMenusSubOffsetX : 1,
			subMenusSubOffsetY : -8
		});
	});
	Ext
			.onReady(function() {
				//初始化标签中的Ext:Qtip属性。
				Ext.QuickTips.init();
				Ext.form.Field.prototype.msgTarget = 'side';
				//Ext.util.CSS.swapStyleSheet("theme","resources/Ext/resources/css/ext-all-xtheme-black.css");

				var vroot1 = new Ext.tree.AsyncTreeNode({
					text : '文章管理',
					leaf : false
				});
				var vloader1 = new Ext.tree.TreeLoader({
					url : "articleManage",
					baseParams : {}
				});
				vloader1.on("beforeload", function(loader, node) {
					//		alert(loader.url);
					loader.baseParams.parent = node.text;
				});
				var tr_panel1 = new Ext.tree.TreePanel({
					loader : vloader1,
					autoScroll : true,
					useArrows : true,//小箭头
					root : vroot1
				});
				//绑定点击事件，点击文章标题展开文章内容
				tr_panel1.on("click", function(node) {
					id = node.id;
					var patrn = /^[0-9]{1,20}$/;
					if (patrn.exec(id) != null) {
						//查询某一篇文章
						centerPanel.load({
							url : "showArticle/"+id,
						/*	params : {
								articleid : id
							},*/
							scope : this,
							discardUrl : false,
							nocache : false,
							text : "正在加载，请稍候...",
							timeout : 30,
							scripts : true
						});
					}
				});
				function showarticle1(data) {
				}
				var vroot2 = new Ext.tree.AsyncTreeNode({
					text : '我的关注',
					leaf : false
				});
				var vloader2 = new Ext.tree.TreeLoader({
					url : "friendManage"
				});
				var tr_panel2 = new Ext.tree.TreePanel({
					loader : vloader2,
					useArrows : true,//小箭头
					root : vroot2,
				});
				tr_panel2.on("click", function(node) {
					id = node.id;
					var patrn = /^[0-9]{1,20}$/;
					if (patrn.exec(id) != null) {
						alert("查看好友" + node.text + "的个人信息");
					}
				});

				var vroot3 = {
					text : '用户中心',
					expanded : true,
					leaf : false,
					children : [ {
						id : "userInfo",
						text : '个人信息',
						leaf : true
					}, {
						id : "changePass",
						text : '修改密码',
						leaf : true
					}, {
						id : "findPass",
						text : '找回密码',
						leaf : true
					}, {
						id : "InfoSet",
						text : '消息设置',
						leaf : true
					}, {
						id : "helpCenter",
						text : '帮助中心',
						leaf : true
					} ]
				};

				var tr_panel3 = new Ext.tree.TreePanel({
					root : vroot3,
					useArrows : true,//小箭头
				});
				tr_panel3.on("click", function(node) {
					id = node.id;
					if (id == "userInfo") {
						alert("个人信息");
					} else if (id == "changePass") {
						alert("修改密码");
					} else if (id == "InfoSet") {
						alert("消息设置");
					} else if (id == "helpCenter") {
						alert("帮助中心");
					} else {
						alert("找回密码");
					}
				});

				var vroot4 = ({
					text : '系统管理',
					leaf : false,
					children : [ {
						text : '全部用户',
						leaf : true
					}, {
						text : '全部文章',
						leaf : true
					} ]
				});
				var tr_panel4 = new Ext.tree.TreePanel({
					useArrows : true,//小箭头
					root : vroot4,
					rootVisible : true,
				});
				//绑定点击事件，点击标题展开内容
				tr_panel4
						.on(
								"click",
								function(node) {
									centerPanel.body.update("");
									var items = centerPanel.items;
									for ( var i = 0; i < items.length; i++) {
										centerPanel.remove(items[i]);
									}

									//系统管理列表展示
									var page_size = 10;
									if (node.text == "全部用户") {
										var MyRecord = Ext.data.Record.create([
												{
													name : 'id'
												}, {
													name : 'name'
												}, {
													name : 'userName'
												}, {
													name : 'password'
												}, {
													name : 'birthday'
												}, {
													name : 'email'
												}, {
													name : 'gender'
												}, {
													name : 'QQ'
												}, {
													name : 'phoneNumber'
												}, {
													name : 'location'
												} ]);
										//数据源
										store = new Ext.data.Store(
												{
													pruneModifiedRecords : true,
													proxy : new Ext.data.HttpProxy(
															{
																url : 'showUserByPage'
															}),
													reader : new Ext.data.JsonReader(
															{
																totalProperty : 'totalProperty',
																root : 'root'
															}, MyRecord)
												});
										store.load({
											params : {
												start : 0,
												limit : page_size
											}
										});
										//添加按钮
										var tbtn1 = new Ext.Toolbar.Button(
												{
													text : '查看选中项',
													listeners : {
														'click' : function() {
															var row = grid1
																	.getSelectionModel()
																	.getSelections();
															if (row.length == 0) {
																Ext.Msg
																		.alert(
																				"提示",
																				"请先选中你要删除的内容！");
																return;
															} else {
																for ( var i = 0; i < row.length; i++) {
																	alert(row[i]
																			.get('id'));
																}
															}
														}
													}
												});
										//添加按钮
										var tbTop1 = new Ext.Toolbar.Button({
											text : '回到顶部',
											listeners : {
												'click' : function() {
													grid1.getView()
															.scrollToTop();
												}
											}
										});
										var tbAdd1 = new Ext.Toolbar.Button(
												{
													text : '添加信息',
													handler : function() {
														if (store.getAt(0).get(
																'userName') != undefined) {
															var p = new MyRecord(
																	{
																		id : "",
																		name : "",
																		userName : "",
																		password : "",
																		birthday : "",
																		email : "",
																		gender : "",
																		QQ : "",
																		phoneNumber : "",
																		location : "",
																	});
															grid1.stopEditing();
															store.insert(0, p);
															grid1.startEditing(
																	0, 0);
														} else {
															Ext.Msg.alert('提示',
																	'请把信息补充完整');
														}
													}
												});
										var tbDelete1 = new Ext.Toolbar.Button(
												{
													text : '删除信息',
													handler : function() {
														var row = grid1
																.getSelectionModel()
																.getSelections();
														if (row.length == 0) {
															Ext.Msg
																	.alert(
																			"提示",
																			"请先选中你要删除的内容！");
															return;
														} else {
															Ext.Msg
																	.confirm(
																			'提示',
																			'确定要删除吗?',
																			function(
																					btn) {
																				if (btn == "yes") {
																					var ids = "{";
																					for ( var i = 0; i < row.length; i++) {
																						store
																								.remove(row[i]);
																						if (i == 0) {
																							ids += "\"id"
																									+ i
																									+ "\":"
																									+ row[i].id
																									+ "";
																						} else {
																							ids += ",\"id"
																									+ i
																									+ "\":"
																									+ row[i].id
																									+ "";
																						}
																					}
																					ids += "}";
																					$
																							.ajax({
																								type : 'post',
																								url : "deleteUser",
																								data : {
																									ids : ids
																								},
																								dataType : 'html',
																								success : function(
																										data) {
																									Ext.Msg
																											.alert(
																													"提示",
																													data);
																									store
																											.reload();
																								},
																								error : function() {
																									Ext.Msg
																											.alert(
																													"错误",
																													"与后台数据交互出错!");
																								}
																							});
																				}
																			});
														}
													}
												});
										var btnSave1 = new Ext.Toolbar.Button(
												{
													text : '保存修改',
													handler : function() {
														var m = store.modified
																.slice(0);
														var jsonArray = [];
														Ext
																.each(
																		m,
																		function(
																				item) {
																			jsonArray
																					.push(item.data);
																		});
														Ext.lib.Ajax
																.request(
																		'POST',
																		'modifyUser',
																		{
																			success : function(
																					response) {
																				Ext.Msg
																						.alert(
																								"信息",
																								response.responseText,
																								function() {
																									store
																											.reload();
																								});
																			},
																			failure : function() {
																				Ext.Msg
																						.alert(
																								"错误",
																								"与后台交互出现问题");
																			}
																		},
																		'data='
																				+ encodeURIComponent(Ext
																						.encode(jsonArray)));
													}
												});

										//分页控件
										var pager1 = new Ext.PagingToolbar(
												{
													pageSize : 10,
													store : store,
													displayInfo : true,
													emptyMsg : '没有任何记录',
													displayMsg : '<div style="color:blue"> 显示第 {0} 条到第 {1} 条记录，一共有 {2} 条</div>',
												/* 					listeners : {
												 "beforechange" : function(bbar, params) {
												 var grid = bbar.ownerCt;
												 var store = grid.getStore();
												 var start = params.start;
												 var limit = params.limit;
												 alert(store.getCount());
												 return false;
												 }
												 } */
												});

										//复选框列
										var sm = new Ext.grid.CheckboxSelectionModel();
										//定义列
										var columns = [
												sm,
												{
													header : '编号',
													dataIndex : 'id',
													sortable : true,
													width : 40,
													editor : new Ext.grid.GridEditor(
															new Ext.form.TextField(
																	{
																		allwoblank : false
																	}))
												},
												{
													header : '姓名',
													dataIndex : 'name',
													sortable : true,
													width : 65,
													editor : new Ext.grid.GridEditor(
															new Ext.form.TextField(
																	{
																		allwoblank : false
																	}))
												},
												{
													header : '账号',
													dataIndex : 'userName',
													sortable : true,
													width : 70,
													editor : new Ext.grid.GridEditor(
															new Ext.form.TextField(
																	{
																		allwoblank : false
																	}))
												},//renderer: sexrender,
												{
													header : '密码',
													dataIndex : 'password',
													sortable : true,
													width : 70,
													editor : new Ext.grid.GridEditor(
															new Ext.form.TextField(
																	{
																		allwoblank : false
																	}))
												},//renderer: Ext.util.Format.dateRenderer('Y-m-d') 
												{
													header : '出生日期',
													dataIndex : 'birthday',
													sortable : true,
													width : 100,
													editor : new Ext.grid.GridEditor(
															new Ext.form.TextField(
																	{
																		allwoblank : false
																	}))
												},
												{
													header : '邮箱',
													dataIndex : 'email',
													sortable : true,
													width : 90,
													editor : new Ext.grid.GridEditor(
															new Ext.form.TextField(
																	{
																		allwoblank : false
																	}))
												},
												{
													header : '性别',
													dataIndex : 'gender',
													sortable : true,
													width : 33,
													editor : new Ext.grid.GridEditor(
															new Ext.form.TextField(
																	{
																		allwoblank : false
																	}))
												},
												{
													header : 'QQ',
													dataIndex : 'QQ',
													sortable : true,
													width : 90,
													editor : new Ext.grid.GridEditor(
															new Ext.form.TextField(
																	{
																		allwoblank : false
																	}))
												},
												{
													header : '手机号',
													dataIndex : 'phoneNumber',
													sortable : true,
													width : 90,
													editor : new Ext.grid.GridEditor(
															new Ext.form.TextField(
																	{
																		allwoblank : false
																	}))
												},
												{
													header : '所在地',
													dataIndex : 'location',
													sortable : true,
													width : 70,
													editor : new Ext.grid.GridEditor(
															new Ext.form.TextField(
																	{
																		allwoblank : false
																	}))
												} ];
										var viewConfig1 = {
											forceFit : true,
											enableRowBody : true,
											getRowClass : function(record,
													rowIndex, p, ds) {
												var cls = 'white-row';
												switch (rowIndex % 3) {
												case 0:
													cls = 'yellow-row';
													break;
												case 1:
													cls = 'green-row';
													break;
												}
												return cls;
											}
										};
										//列表
										var grid1 = new Ext.grid.EditorGridPanel(
												{
													sm : sm,
													//     el : 'grid',
													height : 300,
													columns : columns,
													title : '用户列表',
													store : store,
													autoScroll : true,
													columnLines : true,
													bbar : new Ext.Toolbar([
															tbtn1, tbTop1,
															tbAdd1, tbDelete1,
															btnSave1 ]),
													tbar : pager1,
													viewConfig : viewConfig1
												});

										centerPanel.removeAll();
										centerPanel.add(grid1);
										centerPanel.doLayout();
									} else if (node.text == "全部文章") {
										var MyRecord = Ext.data.Record.create([
												{
													name : 'id'
												}, {
													name : 'title'
												}, {
													name : 'belongUserName'
												}, {
													name : 'content'
												}, {
													name : 'releaseDate'
												}, {
													name : 'emisReproduceail'
												}, {
													name : 'praiseNum'
												}, {
													name : 'collectNum'
												}, {
													name : 'remarkNum'
												}, {
													name : 'reproduceNum'
												} ]);
										//数据源
										var store1 = new Ext.data.Store(
												{
													proxy : new Ext.data.HttpProxy(
															{
																url : 'showArticleByPage'
															}),
													reader : new Ext.data.JsonReader(
															{
																totalProperty : 'totalProperty',
																root : 'root'
															}, MyRecord)
												});
										store1.load({
											params : {
												start : 0,
												limit : page_size
											}
										});
										//添加按钮
										var tbtn2 = new Ext.Toolbar.Button(
												{
													text : '查看选中项',
													listeners : {
														'click' : function() {
															var row = grid2
																	.getSelectionModel()
																	.getSelections();
															for ( var i = 0; i < row.length; i++) {
																alert(row[i]
																		.get('id'));
															}
														}
													}
												});
										//添加按钮
										var tbTop2 = new Ext.Toolbar.Button({
											text : '回到顶部',
											listeners : {
												'click' : function() {
													grid2.getView()
															.scrollToTop();
												}
											}
										});

										//分页控件
										var pager2 = new Ext.PagingToolbar(
												{
													pageSize : 10,
													store : store1,
													displayInfo : true,
													emptyMsg : '没有任何记录',
													displayMsg : '<div style="color:blue"> 显示第 {0} 条到第 {1} 条记录，一共有 {2} 条</div>',
												/* 					listeners : {
												 "beforechange" : function(bbar, params) {
												 var grid = bbar.ownerCt;
												 alert(grid.getStore());
												 var start = params.start;
												 var limit = params.limit;
												 alert(store.getCount());
												 return false;
												 } 
												 }*/
												});

										//复选框列
										var sm = new Ext.grid.CheckboxSelectionModel();
										//定义列
										var columns = [ sm, {
											header : '编号',
											dataIndex : 'id',
											sortable : true,
											width : 40
										}, {
											header : '标题',
											dataIndex : 'title',
											sortable : true,
											width : 80
										}, {
											header : '作者',
											dataIndex : 'belongUserName',
											sortable : true,
											width : 70
										},//renderer: sexrender,
										{
											header : '内容',
											dataIndex : 'content',
											sortable : true,
											width : 140
										},//renderer: Ext.util.Format.dateRenderer('Y-m-d') 
										{
											header : '发布日期',
											dataIndex : 'releaseDate',
											sortable : true,
											width : 100
										}, {
											header : '是否转载',
											dataIndex : 'isReproduce',
											sortable : true,
											width : 50
										}, {
											header : '点赞量',
											dataIndex : 'praiseNum',
											sortable : true,
											width : 60
										}, {
											header : '收藏量',
											dataIndex : 'collectNum',
											sortable : true,
											width : 60
										}, {
											header : '评论量',
											dataIndex : 'remarkNum',
											sortable : true,
											width : 60
										}, {
											header : '转载量',
											dataIndex : 'reproduceNum',
											sortable : true,
											width : 60
										} ];
										//列表
										var grid2 = new Ext.grid.GridPanel({
											sm : sm,
											height : 300,
											columns : columns,
											title : '文章列表',
											autoScroll : true,
											store : store1,
											columnLines : true,
											bbar : [ tbtn2, tbTop2 ],
											tbar : pager2,
										});
										centerPanel.add(grid2);
										centerPanel.doLayout();
									}
								});
				var AccordionLayout = new Ext.Panel({
					layout : 'accordion',
					layoutConfig : {
						animate : true
					},
					//          border:false,
					items : [ new Ext.Panel({
						title : '文章管理',
						border : false,
						layout : 'fit',
						items : tr_panel1
					}), new Ext.Panel({
						title : '关注列表',
						border : false,
						layout : 'fit',
						items : tr_panel2
					}), new Ext.Panel({
						title : '个人中心',
						border : false,
						layout : 'fit',
						items : tr_panel3
					}), new Ext.Panel({
						title : '系统管理',
						border : false,
						layout : 'fit',
						items : tr_panel4
					}) ]
				});

				var centerPanel = new Ext.Panel({
					id : 'mainContent',
					region : 'center',
					autoScroll : true,
					layout : 'fit',
					bodyStyle : 'overflow-x:hidden; overflow-y:scroll',
				/*	autoLoad : {
						url : "toindexPanel"
					}*/
				});
				//总面板
				var panel1 = new Ext.Panel({
					renderTo : "viewport",
					title : '面板',
					width : '100%',
					height : 400,
					collapsible : true,
					titleCollapse : true,
					hideCollapseTool : true,
					layout : 'border',
					//     border:false ,	 
					items : [ {
						title : '操作列表',
						region : 'west',
						layout : 'fit',
						split : true,
						collapsible : true,
						width : '20%',
						items : AccordionLayout
					}, centerPanel ]
				});
				//主题切换下拉框
				//创建数据源[数组数据源]
			    var combostore = new Ext.data.ArrayStore({
			        fields: ['id', 'name'],
			        data: [["black", '黑色'], ['blue', '蓝色'], ['brown', '棕色'],['gray', '灰色'], ['green', '绿色'],
			        ["pink", '粉红色'], ['purple', '紫色'], ['red', '红色']]
			    });
			    //创建Combobox
			    var combobox = new Ext.form.ComboBox({
			        hideLabel:true,
			        store: combostore,
			        displayField: 'name',
			        valueField: 'id',
			        triggerAction: 'all',
			        emptyText: '主题切换',
			        editable:false,
			       renderTo:'styleChange',
			        mode: 'local'
			    });
			    //Combobox获取值
			    combobox.on('select', function () {
			    	v = combobox.getValue();
			        if(v == 'black'){
			            Ext.util.CSS.swapStyleSheet("theme","resources/css/ext/ext-all-xtheme-black.css");
			        }else if(v == 'blue'){
			            Ext.util.CSS.swapStyleSheet("theme","resources/css/ext/ext-all-xtheme-blue03.css");
			        }else if(v == 'brown'){
			            Ext.util.CSS.swapStyleSheet("theme","resources/css/ext/ext-all-xtheme-brown.css");
			        }else if(v == 'gray'){
			            Ext.util.CSS.swapStyleSheet("theme","resources/css/ext/ext-all-xtheme-gray.css");
			        }else if(v == 'green'){
			            Ext.util.CSS.swapStyleSheet("theme","resources/css/ext/ext-all-xtheme-green.css");
			        }else if(v == 'pink'){
			            Ext.util.CSS.swapStyleSheet("theme","resources/css/ext/ext-all-xtheme-pink.css");
			        }else if(v == 'purple'){
			            Ext.util.CSS.swapStyleSheet("theme","resources/css/ext/ext-all-xtheme-purple.css");
			        }else if(v == 'red'){
			            Ext.util.CSS.swapStyleSheet("theme","resources/css/ext/ext-all-xtheme-red03.css");
			        }
			    });
				
			});
</script>
<style type="text/css">
/* .yellow-row{ background-color : yellow !important};

.green-row{ background-color : green !important};

.white-row{	background-color: blue !important};
 */
.x-tree-node-collapsed .x-tree-node-icon {
	background-image: url(resources/images/p1.ico);
}

.x-tree-node-expanded .x-tree-node-icon {
	background-image: url(resources/images/p2.ico);
}

.x-tree-node-leaf .x-tree-node-icon {
	background-image: url(resources/images/p3.ico);
}
</style>
<body>
	<div>
		<nav role="navigation"> <!-- Sample menu definition -->
		<ul id="main-menu" class="sm sm-blue">
			<li><a>您好，<security:authentication property="name" /> </a>
			</li>
			<li><a href="main">首页</a>
			</li>
			<li><a class="hand"><span onclick="showList()">操作列表</span> </a>
				<ul>
					<c:url value="/logout" var="logoutUrl" />
					<li><security:authorize access="anonymous">
							<a onclick="login()" class="hand">用户登陆</a>
						</security:authorize>
					</li>
					<security:authorize access="hasAnyRole('ROLE_ADMIN','ROLE_USER')">
						<li><a href="${logoutUrl}" onclick="logout()">退出登录</a>
						</li>
					</security:authorize>
					<li><a href="toregister">用户注册</a>
					</li>
					<li><a onclick="pub_article()" class="hand">发表微博</a>
					</li>
					<li><a href="toshow" class="hand">列表查看</a>
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
				</ul></li>
			<li><a href="#">个人资料</a>
				<ul class="mega-menu">
					<li>
						<!-- The mega drop down contents -->
						<div style="width:400px;max-width:100%;">
							<div style="padding:15px 15px;">
								<!--   <img src="resources/images/180.jpg" class="photo"> -->
								<p class="fn">*姓名：${user.name}</p>
								<p class="nickname">*生日：${user.birthday}</p>
								<p>*性别：${user.gender}</p>
								<p class="url">*地区：${user.province}省${user.city}</p>
								<p class="address">*邮箱：${user.email }</p>
								<p class="role">*电话：${user.phoneNumber}</p>
							</div>
						</div></li>
				</ul></li>
			<li><a><div id="styleChange"></div></a>
			</li>
		</ul>
		</nav>
	</div>
	<article> <header> <object id="swftitlebar"
		data="resources/images/124.swf" width="100%" height="70%"
		type="application/x-shockwave-flash">
		<param name="allowScriptAccess" value="always">
		<param name="allownetworking" value="all">
		<param name="allowFullScreen" value="true">
		<param name="wmode" value="transparent">
		<param name="menu" value="false">
		<param name="scale" value="noScale">
		<param name="salign" value="1">
	</object> </header>
	<div class="rightbox box">
		<div id="viewport"></div>
	</div>
	<div class="blank"></div>
	<div class="Copyright">
		<a href="/">帮助中心</a> <a href="/">空间客服</a> <a href="/">投诉中心</a> <a
			href="/">空间协议</a>
	</div>
	</article>
</body>
</html>

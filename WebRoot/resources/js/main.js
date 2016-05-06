//视频处理
	function playPause()
	{ 
		myVideo = $('.myVideo')[0];
		if (myVideo.paused) 
		  myVideo.play(); 
		else 
		  myVideo.pause(); 
	} ;
	function makeBig()
	{ 
		myVideo = $('.myVideo')[0];
		myVideo.width= myVideo.height =  420; 
	} ;
	function makeSmall()
	{ 
		myVideo = $('.myVideo')[0];
		myVideo.width = 240;
		myVideo.height = 160; 
	};

//滚动导航条
			var timer1;
			var timer2;
			var timer3;
		//div滑出
		function movein1(s1){
		clearTimeout(timer1);
		$(s1).css("background",'#AA0000') ;
			startMove(s1, {width: 100},function(){timer1 = $("#sc1").show();});
		};
		function moveout1(s1){
			timer1 = setTimeout(function(){
			startMove(s1, {width: 10},function(){
			$(s1).css("background",'none');
			});},200);
			if($("#sc1").css("display") == "none"){
				setTimeout('$("#sc1").hide()',150);
			}else{
				$("#sc1").hide();
			}
		}
		function movein2(s2){
		clearTimeout(timer2);
		$(s2).css("background",'#0066FF') ;
			startMove(s2, {width: 100},function(){$("#sc2").show();});
		};
		function moveout2(s2){
			timer2 = setTimeout(function(){
			startMove(s2, {width: 10},function(){
				$(s2).css("background",'none') ;
			});},200);
			if($("#sc2").css("display") == "none"){
				setTimeout('$("#sc2").hide()',150);
			}else{
				$("#sc2").hide();
			}
		}
		function movein3(s3){
			clearTimeout(timer3);
			$(s3).css("background",'#444444') ;
			startMove(s3, {width: 100},function(){$("#sc3").show();});
		};
		function moveout3(s3){
			timer3 = setTimeout(function(){
			startMove(s3, {width: 10},function(){
				$(s3).css("background",'none');
			});},200);
			if($("#sc3").css("display") == "none"){
				setTimeout('$("#sc3").hide()',150);
			}else{
				$("#sc3").hide();
			}
		}
//瀑布流展示文章
function showArticle(){
	pageNow = $(".pageNow").val();
	$(".pageNow").val(parseInt($(".pageNow").val()) + 1);
	$.ajax({
		type : "get",
			url : "ajaxShowArticle",
		data:{
			pageNow : pageNow
		},
		dataType : " json",
		success : function(art) {
			if(art.id == 1){
				$(".pbl").val("1");
			}else{
				if(art.imgsName == undefined){
					$(".pbl").val("<h2>	<span class=\"per_detail\" ><a class=\"hand\" onclick=\"attend("+art.belongUserId+")\" >"+
					"<img  src=\"resources/images/head.png\"  class=\"headimg\">From "+art.belongUserName+" (点击关注)</a></span>—— "+art.releaseDate+"</h2><h3 class=\"title\">"+art.title+"</h3>"+
				"	<div class=\"text\"><p>"+art.content+"</p></div><div class=\"textfoot\">"+
				"		<a class=\"hand\" onclick=\"praise("+art.id+")\">赞(&nbsp;<span id=\"praise"+art.id+"\"\>"+art.praiseNum+"</span> ) </a>"+
				"<a class=\"hand\"onclick=\"collect("+art.id+")\">&nbsp;收藏(&nbsp;<span id=\"collect"+art.id+"\"\>"+art.collectNum+"</span> )</a>"+
				"<a class=\"hand\" onclick=\"remark("+art.id+",1)\">&nbsp;&nbsp;评论(&nbsp;<span id=\"remark"+art.id+"\"\>"+art.remarkNum+"</span> )</a><a class=\"hand\""+
						"	onclick=\"reproduce("+art.id+")\">&nbsp;&nbsp;转载(&nbsp;<span id=\"reproduce"+art.id+"\"\>"+art.reproduceNum+"</span> )</a></div><div id=\"showremark"+art.id+"\" style=\"display:none\"><div class=\"text\">"+
					"		<input type=\"text\" name=\"content\"	class=\"mytxt RemContent"+art.id+" placeholder=\"评论内容\">"+
					"	<input type=\"button\" value=\"评论\" class=\"Rembtn\" onclick=\"addRemark("+art.id+")\"></div>"+
					"	<div class=\"data"+art.id+"\"></div><div class=\"textfoot\"></div>	</div>" );
				hovers();
					}else{
						$(".pbl").val("	<h2><span class=\"per_detail\" ><a class=\"hand\" onclick=\"attend("+art.belongUserid+")\">"+
						"<img  src=\"resources/images/head.png\"  class=\"headimg\">From "+art.belongUserName+" (点击关注)</a></span>—— "+art.releaseDate+"</h2><h3 class=\"title\">"+art.title+"</h3>"+
				"	<p class=\"img\"><img src=\"showImgs?articleid="+art.id+"\"></p><div class=\"text\">"+
				"<p>"+art.content+"</p></div><div class=\"textfoot\">"+
				"		<a class=\"hand\" onclick=\"praise("+art.id+")\">赞(<span id=\"praise"+art.id+"\"\>"+art.praiseNum+"</span> ) </a>"+
				"<a class=\"hand\"onclick=\"collect("+art.id+")\">收藏(<span id=\"collect"+art.id+"\"\>"+art.collectNum+"</span> )</a>"+
				"<a class=\"hand\" onclick=\"remark("+art.id+",1)\">评论(<span id=\"remark"+art.id+"\"\>"+art.remarkNum+"</span> )</a><a class=\"hand\""+
						"	onclick=\"reproduce("+art.id+")\">转载(<span id=\"reproduce"+art.id+"\"\>"+art.reproduceNum+"</span> )</a></div><div id=\"showremark"+art.id+"\" style=\"display:none\"><div class=\"text\">"+
					"		<input type=\"text\" name=\"content\"	class=\"mytxt RemContent"+art.id+" placeholder=\"评论内容\">"+
					"	<input type=\"button\" value=\"评论\" class=\"Rembtn\" onclick=\"addRemark("+art.id+")\"></div>"+
					"	<div class=\"data"+art.id+"\"></div><div class=\"textfoot\"></div>	</div>" );
					hovers();
					}
			}
		}
	});
}
//滑到底监控
function scrollToBottom() {
	if (($(window).scrollTop() + $(window).height()) == $(document).height()) {
		if ($(".pbl").val() == "0")
			return;
		if ($(".pbl").val() == "1") {
			alert("已加载完毕！");
			$(".pbl").val("0");
			return;
		}
		div = $('<div></div>').addClass('item');
		p = $(".pbl").val();
		div.append(p);
		$("#device").append(div);
		showArticle();
	}
};

// 保持滚动条
function scroller() {
	var scrollheight = $(window).scrollTop();
	browserheight = $(window).height();
	ttop = $(".scrollerTop").height();
	tops = scrollheight + (browserheight - ttop) / 2;
	$(".scrollerTop").css("top", tops);
	$("#scroller1").css("top", tops);
	$("#scroller2").css("top", tops + 40);
	$("#scroller3").css("top", tops + 80);
}
// 移到头像展示个人信息
var MouseEvent = function(e) {
	this.x = e.pageX;
	this.y = e.pageY;
};
var Mouse = function(e) {
	var kdheight = $(document).scrollTop();
	mouse = new MouseEvent(e);
	leftpos = mouse.x +20;
	toppos =  mouse.y - kdheight -120;
};
function putInfo(userid){
	if(userid == $(".perId").val())return;
	$.ajax({
	dataType : "json",
	url : 'getPerInfo',// 跳转
	data : {
		userid : userid,
	},
	type : 'get',
	success:function(data){
		$(".perName").html(data.name);
		$(".perPhoneNumber").html(data.phoneNumber);
		$(".perId").val(data.id);
	},error:function(){
		Ext.Msg.alert("异常","与后台数据交互存在异常！");
	}
});
}

var timer1;
function hovers() {
	$(".per_detail").hover(function(e) {
		if (document.body.clientWidth > 500) {
			Mouse(e);
			$(".showinfo").css({
				top : toppos,
				left : leftpos,
			}).fadeIn(100);
		}
		// alert($(".showinfo").css("z-index"));
	}, function() {
		timer1 = setTimeout("jQuery('.showinfo').hide()", 500);
	});
	$(".showinfo").hover(function(e) {
		clearTimeout(timer1);
	}, function() {
		$('.showinfo').hide();
	});
};
// 页面初始化，处理聊天窗口
function onPageLoad() {
	var talkerId = $("#loginUserid").val();
	if (talkerId == 0)
		return;
	$.ajax({ //这是固定的标记头
		type : "get",//这是提交方式，一般有post和get
		dataType : "json",//这是从后台返回数据类型，一般是html，json更轻量级
		url : "checkisNewMessage",//这是要提交的url
		data : {
			toUserid : talkerId//这是要传输的数据名,格式“参数名”：内容
		},
		success : function(data) {//当异步提交成功时的回调函数，data是从后台返回的数据
			if (data == "")
				return;
			showRecord();//这是我自己写的函数
			initMsg(data);//自定义函数
		},
		error : function() {//当与后台交互失败时的回调函数，比如url地址错误，网络状态异常等
			Ext.Msg.alert("错误", "与服务器数据交互出错！");
		}
	});
	
}
function showRecord(data) {
	var recordPanel = new Ext.Panel(
			{
				html : "<center><font color=\"red\ size=\"20px\">你有新信息！</font></center><div id=\"recordMsg\"><div id=\"lastRecDom\" style=\"height: 1px;\"></div></div>",
				region : 'center',
				frame : true,
			});
	// 聊天记录窗口
	var recordWin = new Ext.Window({
		title : '你有新信息！',
		draggable : true,
		plain : true,
		// border:false,
		width : document.body.clientWidth >= 300 ? 300
				: document.body.clientWidth,
		height : 250,
		layout : 'fit',
		resizable : false,
		closable : true,
		shadow : false,
		modal : true,
		items : [ recordPanel ],
	// buttons:btnhide
	});
	recordWin.show();
}
function initMsg(list) {
	$("#recordMsg [id!='lastRecDom']").remove();
	var lng = new Date().getTime();
	// 表现为颜色的不一致
	for ( var i = 0; i < list.length; i++) {
		// 如果是别人发过来的
		// alert("1");
		createRecTalkEl(list[i].fromUserName + ":" + list[i].msg, "msg_left",
				"_" + (lng++));
	}
};
function createRecTalkEl(content, cls, id) {
	var dom = $("<textarea>" + content + "</textarea>").addClass(cls).attr({
		id : id,
		rows : 1
	});
	$("#lastRecDom").before(dom);
	return dom;
}
// 注册表单
function register() {
	// 提交按钮
	var btnreset = new Ext.Button({
		text : '重 置',
		handler : function() {
			form.getForm().reset();
		}
	});
	// 提交按钮
	var btnsubmit = new Ext.Button({
		text : '提 交',
		handler : function() {
			alert("表单提交");
		}
	});
	var registerPanel = new Ext.Panel({
		border : false,
	});
	// 窗口
	var win = new Ext.Window({
		title : '用户注册 ',
		plain : true,
		width : document.body.clientWidth >= 570 ? 570
				: document.body.clientWidth,
		draggable : false,
		height : 350,
		width : "20%",
		layout : 'fit',
		resizable : true,
		shadow : false,
		modal : true,
		closable : true,
		animCollapse : true,
		buttonAlign : 'center',
		items : registerPanel,
		buttons : [ btnsubmit, btnreset ]
	});
	win.show();
}

// 添加分组
function addAttenderGroup(id) {
	if ($.trim($("#ChatUserName").text()) == "游客") {
		Ext.MessageBox.confirm('权限不足', "尚未登录?请先登录", function(btn) {
			if (btn == "yes")
				sign(0);
		});
		return;
	}
	var btnsubmit = new Ext.Button({
		text : '提交',
		handler : function() {
			$.ajax({
				type : 'post',
				dataType : 'html',
				url : 'addAttendwithGroup',
				data : {
					nickName : $("#nickName").val(),
					friendid : id,
					groupId : groupbox.getValue()
				},
				success : function(data) {
					Ext.Msg.alert('提示', data);
					groupWin.destroy();
					groupform.form.reset();
				},
				error : function() {
					alert("与后台数据交互出错！");
				}
			});
		}
	});
	var btncancel = new Ext.Button({
		text : '取消',
		handler : function() {
			groupWin.destroy();
			groupform.form.reset();
		}
	});
	var btncreate = new Ext.Button({
		text : '创建新分组',
		handler : function() {
			newGroup.show();
			createsubmit.show();
			createcancel.show();
			btncreate.hide();
		}
	});
	var createsubmit = new Ext.Button({
		text : '创建',
		hidden : true,
		handler : function() {
			if ($("#newGroup").val() == "输入新分组的名字") {
				Ext.Msg.alert("提示", "分组名字不能为空！");
				return;
			}
			$.ajax({
				type : 'post',
				dataType : 'html',
				url : 'addGroups',
				data : {
					groupName : $('#newGroup').val()
				},
				success : function(data) {
					Ext.Msg.alert("提示", data);
					groupStore.load();
					newGroup.hide();
					createsubmit.hide();
					createcancel.hide();
					btncreate.show();
					newGroup.reset();
				},
				error : function() {
					Ext.Msg.alert('提示', "与后台数据交互出错！");
				}
			});
		}
	});
	var createcancel = new Ext.Button({
		text : '取消',
		hidden : true,
		handler : function() {
			newGroup.hide();
			createsubmit.hide();
			createcancel.hide();
			btncreate.show();
		}
	});
	var nickName = new Ext.form.TextField({
		width : '100%',
		id : 'nickName',
		name : 'nickName',
		hideLabel : true
	});
	var newGroup = new Ext.form.TextField({
		width : '70%',
		id : 'newGroup',
		name : 'newGroup',
		allowBlank : false,
		blankText : '请输入新分组的名字',
		emptyText : '输入新分组的名字',
		hideLabel : true,
		hidden : true,
	});
	// 创建数据源[数组数据源]
	var groupStore = new Ext.data.Store({
		proxy : new Ext.data.HttpProxy({
			url : 'getGroups'
		}),
		reader : new Ext.data.JsonReader({
			totalProperty : 'totalProperty',
			root : 'data'
		}, [ {
			name : 'id'
		}, {
			name : 'name'
		} ])
	});

	// 创建Combobox
	var groupbox = new Ext.form.ComboBox({
		store : groupStore,
		displayField : 'name',
		hideLabel : true,
		width : '100%',
		valueField : 'id',
		triggerAction : 'all',
		emptyText : '请选择...',
		allowBlank : false,
		pageSize : 5,
		minListWidth : 230,
		blankText : '请选择分组',
		editable : false,
		mode : 'local', // 该属性和以下方法为了兼容ie8
		listeners : {
			'render' : function() {
				groupStore.load();
			}
		}
	});
	var groupform = new Ext.form.FormPanel({
		frame : true,
		hideLabel : true,
		width : '100%',
		layout : 'form',
		buttonAlign : 'center',
		items : [ {
			xtype : 'fieldset',
			title : '设置备注名称',
			items : [ nickName ]
		}, {
			xtype : 'fieldset',
			title : '为关注选择分组',
			items : [ groupbox, btncreate, {
				layout : 'column',
				items : [ newGroup, createsubmit, createcancel ]
			} ]
		} ]
	});
	var groupWin = new Ext.Window({
		title : '添加分组 ',
		plain : true,
		width : 250,
		draggable : false,
		height : 270,
		resizable : true,
		layout : 'fit',
		shadow : false,
		modal : true,
		closable : true,
		animCollapse : true,
		buttonAlign : 'center',
		items : [ groupform ],
		buttons : [ btnsubmit, btncancel ]
	});
	groupWin.show();
}
// 关注
function attend(id) {
	if ($.trim($("#ChatUserName").text()) == "游客") {
		Ext.MessageBox.confirm('权限不足', "尚未登录?请先登录", function(btn) {
			if (btn == "yes")
				sign(0);
		});
		return;
	}
	$.ajax({
		dataType : "html",
		url : 'checkAttend',// 跳转
		data : {
			friendid : id,
		},
		type : 'get',
		success : function(data) {
			if (data == "success") {
				addAttenderGroup(id);
				$('#attend' + id).html(parseInt($('#attend' + id).html()) + 1);
			} else {
				Ext.MessageBox.confirm("取消关注", "已关注，想取消关注吗？", function(choice) {
					if (choice == "yes") {
						$
								.ajax({
									dataType : "html",
									url : 'cancel_attend',// 跳转
									data : {
										friendid : id,
									},
									type : 'get',
									success : function() {
										Ext.MessageBox.alert("取消关注", "取消成功！");
										$('#attend' + id).html(
												parseInt($('#attend' + id)
														.html()) - 1);
									},
									error : function() {
										alert("异常！");
									}
								});
					}
				});
			}
		},
		error : function() {
			alert("异常！");
		}
	});
}
// 收藏
function collect(id) {
	if ($.trim($("#ChatUserName").text()) == "游客") {
		Ext.MessageBox.confirm('权限不足', "尚未登录?请先登录", function(btn) {
			if (btn == "yes")
				sign(0);
		});
		return;
	}
	$.ajax({
		dataType : "html",
		url : 'collection',// 跳转
		data : {
			articleid : id,
		},
		type : 'get',
		success : function(data) {
			if (data == "success") {
				// addTag(id);
				alert("成功收藏！");
				$('#collect' + id).html(
						parseInt($('#collect' + id).html()) + 1 + "&nbsp");
			} else {
				Ext.MessageBox.confirm("取消收藏", "已收藏，想取消收藏吗？", function(choice) {
					if (choice == "yes") {
						$.ajax({
							dataType : "html",
							url : 'cancel_collect',// 跳转
							data : {
								articleid : id,
							},
							type : 'get',
							success : function() {
								Ext.MessageBox.alert("取消收藏", "取消成功！");
								$('#collect' + id)
										.html(
												parseInt($('#collect' + id)
														.html()) - 1);
							},
							error : function() {
								alert("异常！");
							}
						});
					}
				});
			}
		},
		error : function() {
			alert("异常！");
		}
	});
}
// 转载
function reproduce(articleid) {
	if ($.trim($("#ChatUserName").text()) == "游客") {
		Ext.MessageBox.confirm('权限不足', "尚未登录?请先登录", function(btn) {
			if (btn == "yes")
				sign(0);
		});
		return;
	}
	var tabs = new Ext.TabPanel({
		activeTab : 0,// 初始显示第几个Tab页
		deferredRender : false,// 是否在显示每个标签的时候再渲染标签中的内容.默认true
		tabPosition : 'top',// 表示TabPanel头显示的位置,只有两个值top和bottom.默认是top.
		enableTabScroll : true,// 当Tab标签过多时,出现滚动条
	});
	// 选项卡
	tabs.add({
		id : 'tab1',
		title : '我的微博',
		layout : 'fit',
	});
	tabs.add({
		id : 'tab2',
		title : '好友圈',
		layout : 'fit',
	});
	tabs.add({
		id : 'tab3',
		title : '私信',
		layout : 'fit',
	});
	// 表单项1内容
	var textarea1 = new Ext.form.TextArea({
		name : 'remark',
		id : 'myblob',
		// fieldLabel : '附加评论',
		style : "align:'center'",
		emptyText : '附加你的点评',
		width : 390,
		height : 70,
	});
	var tip1 = new Ext.form.TextField({
		id : 'tip1',
		anchor : '96%',
		style : 'text-align:center',
		emptyText : '将微博定向转发到你的微博，任何游览你微博的人都能看到。',
		disabled : true
	});
	var form1 = new Ext.form.FormPanel({
		labelAlign : 'left',
		labelWidth : 50,
		layout : 'form',
		hideLabels : true,
		frame : true,
		bodyStyle : 'padding:6px 0px 0px 15px;',
		items : [ tip1, {
			layout : 'column',
			xtype : 'fieldset',
			width : 415,
			title : '附加评论',
			items : textarea1
		} ],
		buttons : [ {
			text : '转 发',
			handler : function() {
				Ext.Ajax.request({
					url : 'reproduce',
					params : {
						articleid : articleid,
						content : $("#myblob").val(),
						reproduceArea : "personalBlog"
					},
					success : function(response) {
						Ext.Msg.alert('恭喜', '恭喜您成功转载！', function() {
							parent.window.location.reload();
						});
					},
					error : function() {
						Ext.Msg.alert("异常", "异常");
					}
				});
			}
		}, {
			text : '重置',
			handler : function() {
				form1.getForm().reset();
			}
		} ]
	});
	// 表单项2内容
	var textarea2 = new Ext.form.TextArea({
		name : 'remark',
		id : 'friendCircle',
		// fieldLabel : '附加评论',
		style : "align:'center'",
		emptyText : '附加你的点评',
		width : 390,
		height : 70,
	});
	var tip2 = new Ext.form.TextField({
		id : 'tip2',
		anchor : '96%',
		style : 'text-align:center',
		emptyText : '将微博定向转发到好友圈，只有你的好友可见这条微博。',
		disabled : true
	});
	var form2 = new Ext.form.FormPanel({
		labelAlign : 'left',
		labelWidth : 50,
		layout : 'form',
		hideLabels : true,
		frame : true,
		bodyStyle : 'padding:6px 0px 0px 15px;',
		items : [ tip2, {
			layout : 'column',
			xtype : 'fieldset',
			width : 415,
			title : '附加评论',
			items : textarea2
		} ],
		buttons : [ {
			text : '转 发',
			handler : function() {
				Ext.Ajax.request({
					url : 'reproduce',
					params : {
						articleid : articleid,
						content : $("#friendCircle").val(),
						reproduceArea : "friendCircle"
					},
					success : function(response) {
						Ext.Msg.alert('恭喜', '恭喜您成功转载！', function() {
							parent.window.location.reload();
						});
					},
					error : function() {
						Ext.Msg.alert("异常", "异常");
					}
				});
			}
		}, {
			text : '重置',
			handler : function() {
				form2.getForm().reset();
			}
		} ]
	});
	var tip3 = new Ext.form.TextField({
		id : 'tip3',
		anchor : '96%',
		style : 'text-align:center',
		emptyText : '将微博定向转发给你的好友',
		disabled : true
	});
	var recei = new Ext.form.TextField({
		allowBlank : false,
		id : 'friendUserName',
		blankText : '请填收信人',
		msgTarget : "side",
		fieldLabel : '收信人',
		emptyText : '请填入收信人的用户名',
		anchor : '93%',
	});
	var textarea3 = new Ext.form.TextArea({
		name : 'remark',
		id : 'personalMsg',
		fieldLabel : '内容',
		style : "align:'center'",
		height : 70,
		emptyText : '想对你的好友说的话',
		anchor : '93%',
	});
	var form3 = new Ext.form.FormPanel({
		labelAlign : 'left',
		labelWidth : 50,
		layout : 'form',
		hideLabels : true,
		frame : true,
		bodyStyle : 'padding:6px 0px 0px 15px;',
		items : [ tip3, {
			xtype : 'fieldset',
			width : 415,
			items : [ recei, textarea3 ]
		} ],
		buttons : [ {
			text : '转 发',
			handler : function() {
				Ext.Ajax.request({
					url : 'reproduce',
					params : {
						articleid : articleid,
						personalMsg : $("#personalMsg").val(),
						reproduceArea : "personalFriend",
						frienduserName : $("#receiver").val()
					},
					success : function(data) {
						Ext.Msg.alert('恭喜', '恭喜您成功转载！', function() {
							parent.window.location.reload();
						});
					},
					error : function() {
						Ext.Msg.alert("异常", "异常");
					}
				});
			}
		}, {
			text : '重置',
			handler : function() {
				form3.getForm().reset();
			}
		} ]
	});
	// 窗体
	var tabwin = new Ext.Window({
		title : '转发到',
		layout : 'fit',
		width : 476,
		height : 270,
		resizable : false,
		modal : true,
		closable : true,
		maximizable : false,
		minimizable : false,
		items : tabs,
	});
	tabs.getItem('tab1').add(form1);
	tabs.getItem('tab2').add(form2);
	tabs.getItem('tab3').add(form3);
	tabs.doLayout();
	tabwin.show();
}
// 查看评论
function remark(id, pageNow) {
	var wrapper1 = document.getElementById("showremark" + id);
	// alert(document.getElementById("showremark"+id));
	if (wrapper1.style.display == "none") {
		ajaxshow(id, pageNow);
		wrapper1.style.display = "block";
	} else {
		wrapper1.style.display = "none";
	}
}

// 显示评论
function ajaxshow(id, pageNow) {
	$
			.ajax({
				type : "get",
				url : "showRemark",
				data : {
					articleid : id,
					pageNow : pageNow
				},
				dataType : " json",
				success : function(data) {
					$(".remdetail" + id).empty();
					if (data != "none") {
						var page = data.page;
						var remark = data.remark;
						for ( var i = 0; i < remark.length; i++) {
							$(".remdetail" + id)
									.append(
											"<h2 size=\"2px\">评论: "
													+ remark[i].releaseDate
													+ " | XXX</h2 ><br><font size=\"3px\">"
													+ remark[i].content
													+ "</font>"
													+ " <br /><br />");
						}
						var pageNext = page.pageNow < page.totalPage ? page.pageNow + 1
								: page.totalPage;
						var pageLast = page.pageNow > 1 ? page.pageNow - 1 : 1;
						$(".remdetail" + id)
								.append(
										"<center>共 "
												+ page.totalPage
												+ " 页 &nbsp;&nbsp;当前第 "
												+ +page.pageNow
												+ " 页&nbsp;&nbsp; 共 "
												+ page.totalSize
												+ " 条记录&nbsp;&nbsp;<a onclick=\"ajaxshow("
												+ id
												+ ","
												+ 1
												+ ")\"\" class=\"hand\">首页</a> "
												+ "<a onclick=\"ajaxshow("
												+ id
												+ ","
												+ pageLast
												+ ")\"\" class=\"hand\">上一页</a>"
												+ "<a onclick=\"ajaxshow("
												+ id
												+ ","
												+ pageNext
												+ ")\"\" class=\"hand\">&nbsp;&nbsp;下一页</a>"
												+ "<a onclick=\"ajaxshow("
												+ id
												+ ","
												+ page.totalPage
												+ ")\" class=\"hand\">&nbsp;&nbsp;尾页</a></center>");
					}
				}
			});
}
// 提交评论
function addRemark(articleid) {
	if ($.trim($("#ChatUserName").text()) == "游客") {
		Ext.MessageBox.confirm('权限不足', "尚未登录?请先登录", function(btn) {
			if (btn == "yes")
				sign(0);
		});
		return;
	}
	var content = $(".RemContent" + articleid).val();
	if (content == "") {
		alert("评论内容不能为空！");
		return;
	}
	$.ajax({
		dataType : "html",
		url : 'addRemark',// 跳转
		data : {
			articleid : articleid,
			content : content
		},
		type : 'post',
		success : function(data) {
			if (data == "success") {
				alert("评论成功！");
				$(".RemContent" + articleid).val("");
				$('#remark' + articleid).html(
						parseInt($('#remark' + articleid).html()) + 1
								+ "&nbsp;");
				// alert($('#remark' + id).html());
				ajaxshow(articleid, 1);
			}
		},
		error : function() {
			alert("异常！");
		}
	});
}
// 注销
function logout() {
	alert("退出成功！");
}
// 点赞
function praise(id) {
	if ($.trim($("#ChatUserName").text()) == "游客") {
		Ext.MessageBox.confirm('权限不足', "尚未登录?请先登录", function(btn) {
			if (btn == "yes")
				sign(0);
		});
		return;
	}
	// ajax提交
	$.ajax({
		dataType : "html",
		url : 'addPraise',// 跳转
		data : {
			articleid : id
		},
		type : 'post',
		success : function(data) {
			if (data == "success") {
				alert("点赞成功！");
				$('#praise' + id).html(parseInt($('#praise' + id).html()) + 1);
			} else {
				alert("已经点赞过了！");
			}
		},
		error : function() {
			alert("异常！");
		}
	});
}

// 前往列表展示界面
function toshow() {
	if ($.trim($("#ChatUserName").text()) == "游客") {
		Ext.MessageBox.confirm('权限不足', "尚未登录?请先登录", function(btn) {
			if (btn == "yes")
				sign(0);
		});
		return;
	}
	location = "toshow";
}
// 前往聊天界面
function talkTime() {
	if ($.trim($("#ChatUserName").text()) == "游客") {
		Ext.MessageBox.confirm('权限不足', "尚未登录?请先登录", function(btn) {
			if (btn == "yes")
				sign(0);
		});
		return;
	}
	location = "tochat?userName=" + $("#ChatUserName").html();
}

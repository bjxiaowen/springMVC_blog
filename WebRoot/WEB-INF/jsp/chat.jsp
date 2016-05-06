<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<base href="<%=basePath%>">

<title>聊天界面</title>
<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
<meta http-equiv="description" content="This is my page">
		<script type="text/javascript" src="<%=path%>/dwr/engine.js"></script>
		<script type="text/javascript" src="<%=path%>/dwr/util.js"></script>
		<script type="text/javascript" src="<%=path%>/dwr/interface/dwrController.js"></script>
			<style type="text/css">
.msg_left {
	-webkit-border-radius: 5;
	-moz-border-radius: 5;
	width: 100%;
	background-color: #eeeeee;
	border-width: 1px;
	border-color: #cccccc;
	border-style: solid;
	padding: 0 0px;
	margin: 0 0px;
	resize: vertical;
	overflow: hidden;
}

.msg_right {
	-webkit-border-radius: 5;
	-moz-border-radius: 5;
	width: 100%;
	background-color: #cccccc;
	border-width: 1px;
	border-color: #eeeeee;
	border-style: solid;
	padding: 0 0px;
	margin: 0 0px;
	resize: vertical;
	overflow: hidden;
}
</style>
		<script type="text/javascript" src="resources/js/jquery-1.7.1.js"></script>
<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
<!--ExtJs框架开始-->
<script type="text/javascript" src="resources/Ext/adapter/ext/ext-base.js"></script>
<script type="text/javascript" src="resources/Ext/ext-all.js"></script>
<link rel="stylesheet" type="text/css"
	href="resources/Ext/resources/css/ext-all.css" />
<!--ExtJs框架结束-->

<style type="text/css">
.loginicon {
	background-image: url(image/login.gif) !important;
}
</style>
<script type="text/javascript">
	Ext.onReady(function() {
		//初始化标签中的Ext:Qtip属性。
		Ext.QuickTips.init();
		Ext.form.Field.prototype.msgTarget = 'side';
		
		var uroot  = new Ext.tree.AsyncTreeNode({text:'我的好友',leaf:false});
	var uloader = new Ext.tree.TreeLoader({url:"talk_getAllUsers?userid="+'${user.id}',baseParams:{}});
	uloader.on("beforeload",function(loader,node){
		loader.baseParams.parent = node.text;
	});
		var tr_panel = new Ext.tree.TreePanel({
		loader: uloader,
		useArrows:true,//小箭头
		root:uroot
			});
	//绑定点击事件，点击文章标题展开文章内容
	tr_panel.on("click", function (node){
		id = node.id;
		var patrn=/^[0-9]{1,20}$/;
		if(patrn.exec(id) != null){
		$("#chatObject").html("你的聊天对象是："+node.text);
		$("#who").val(id);
	}});
		//treePanel
		westPanel = new Ext.Panel({
			title:'聊天对象',
			width:140,
			id:'westPanel',
			region : 'west',
			collapsible: true,  
            constrain :true,
			autoScroll:true,
			frame:true,
			border:false,
			split :true,
			layout:'fit',
			html:'<input type="hidden"id="who" value="0">',
			items:tr_panel
		});
		//文本编辑框
		var editor = new Ext.form.HtmlEditor({
//		fieldLabel : '正文',
		id : 'content',
		name : 'content',
		width :  document.body.clientWidth >= 570? 420:document.body.clientWidth - 150,
	});
	
		var btnSubmit  =new Ext.Button({
			text : '提交',
			id:'talk',
			handler:function(){
	//		alert("xinxi");
				sendMsg();
			}
		});
		var btnReset = new Ext.Button({
			text : '重置',
			handler: function(){
			alert();
			}
		});
			var btnChat_info = new Ext.Button({
			text:"聊天记录",
			handler:function(){
			if($('#who').val() == "0"){
				Ext.Msg.alert("提示","请先选择一个聊天对象!");
				return;
			}
				create();
				recordWin.show();
			}
		});
		var btnLeave = new Ext.Button({
			text:'退出聊天',
			handler:function(){
				location  = "main";
			}
		});
			var centerPanel = new Ext.Panel({
			title:'<span id="chatObject">聊天内容</div>',
			height:300,
			id:'centerPanel',
			layout:'accordion',  
			region : 'center',
			autoScroll:true,
			layout:'fit',
		//	frame:true,
			html:"<div id=\"msg\"><div id=\"lastDom\" style=\"height: 1px;\"></div></div>"
		});
			var southPanel = new Ext.Panel({
			title:'您好，${user.name}',
			frame:true,
			id:'southPanel',
			border:false,
			region : 'south',
			height:200,
			autoScoll:true,
			layout:'fit',
			items:editor,
			buttons:[btnSubmit,btnReset,btnChat_info,btnLeave],
		});
			eastPanel = new Ext.Panel({
			id:'eastPanel',
			region : 'center',
			autoScoll:true,
			border:false,
			layout:'border',
			items :[centerPanel,southPanel]
		});

		var chatPanel = new Ext.Panel({
                  broder:false,
                  layout: 'border',
                  items: [westPanel,eastPanel],
              });
              var btnhide = new Ext.Button({
          	text:'关闭窗口',
          	handler : function(){
         		recordWin.hide();
          	}
          });
          var recordPanel = new Ext.Panel({
          	html:"<div id=\"recordMsg\"><div id=\"lastRecDom\" style=\"height: 1px;\"></div></div>",
          	region:'center',
          	frame:true,
          });
         //聊天记录窗口
		var recordWin = new Ext.Window({
			title:'聊天记录',
			draggable : true,
			plain : true,
	//		border:false,
			width : document.body.clientWidth >= 300? 300:document.body.clientWidth,
			height : 250,
			layout:'fit',
			resizable : false,
			closable:true,
			shadow : false,
			modal : true,
			items:[recordPanel],
			//buttons:btnhide
		});
              
		var charWin = new Ext.Window({
		title : '聊天窗口 ',
		plain : true,
		border:false,
		width : document.body.clientWidth >= 570? 570:document.body.clientWidth,
		draggable : true,
		height : 430,
		layout:'fit',
		closable:false,
		resizable : false,
		shadow : false,
		modal : true,
		animCollapse : true,
		items:chatPanel,
		});
		charWin.show();
	});
</script>
<script type="text/javascript">
function create () {
		//alert("聊天对象的id为："+ $('#who').val());
		$.ajax( {
			url : "talk_getAllTalks",
			dataType : "json",
			type : "POST",
			data : {
				"fromUserId" : "${user.id}",
				"toUserId" : $('#who').val()
			},
			success : function(data) {
	//		alert("成功函数的调用");
				initMsg(data.talks);
			}
			});
			}
//touchmove: 手指在屏幕上滑动的时候连续地触发
//在这个事件发生期间，调用preventDefault()事件可以阻止滚动。
document.addEventListener('touchmove', function(e) {
	e.preventDefault();
}, false);

function onPageLoad() {
	var talkerId = '${user.id}';
	dwrController.onPageLoad(talkerId);
}
function showMessage(msg,createDate) {
	var id = "_" + (new Date().getTime());
	alert("收到新信息");
	createTalkEl(msg, "msg_left", id);
 	$.ajax({
		type:"post",
		dataType:"json",
		url:'updateStatus',
		data:{
			createDate:createDate,
			toUserId:"${user.id}"
		}
	}) ;
}
function sendMsg() {
	var id = "_" + (new Date().getTime());
	var msg = Ext.get('content').dom.value;
	createTalkEl('${user.name}:' + msg, "msg_right", id);
	var toUserId = document.getElementById('who').value;
	if(toUserId == ""){
		alert("请先选择你要说话的人！");
		return;
	}
	dwrController.sendMessage(toUserId,'${user.id}','${user.name}', msg);
}

/**创建msg框体*/
function createTalkEl(content, cls, id) {
	var dom = $("<textarea>" + content + "</textarea>").addClass(cls).attr( {
		id : id,
		rows : 1
	});
	$("#lastDom").before(dom);
	return dom;
}
function createRecTalkEl(content, cls, id) {
	var dom = $("<textarea>" + content + "</textarea>").addClass(cls).attr( {
		id : id,
		rows : 1
	});
	$("#lastRecDom").before(dom);
	return dom;
}
function initMsg(list) {
	//重刷新msg时 清空记录
//alert($("#msg [id!='lastDom']").html());
	//id不是lastDom的子div都要去掉
	$("#recordMsg [id!='lastRecDom']").remove();
	var lng = new Date().getTime();
	//表现为颜色的不一致
	for ( var i = 0; i < list.length; i++) {
		//如果是别人发过来的
		if (list[i].toUser.id == "${user.id}") {
	//	alert("1");
			createRecTalkEl(list[i].toUser.name + ":" + list[i].Msg, "msg_left",
					"_" + (lng++));
		} else {//如果是自己发过去的
	//	alert("2");
			createRecTalkEl(list[i].toUser.name + ":" + list[i].Msg, "msg_right",
					"_" + (lng++));
		}
	}
}

function change(id){
	$("#who").val(id);
	create();
}
</script>
<style type="text/css">
.x-tree-node-collapsed .x-tree-node-icon {
	background-image: url(resources/images/p3.ico);
}

.x-tree-node-expanded .x-tree-node-icon {
	background-image: url(resources/images/p2.ico);
}

.x-tree-node-leaf .x-tree-node-icon {
	background-image: url(resources/images/user.gif);
}
</style>
</head>
	<body
		onload="dwr.engine.setActiveReverseAjax(true);
    		dwr.engine.setNotifyServerOnPageUnload(true);
    		onPageLoad();">
</body>
</html>

/*function showPanel(){
    var editor = new Ext.form.HtmlEditor({
		fieldLabel : '正文',
		id : 'content',
		name : 'content',
		enableAlignments : true,
		enableColors : true,
		enableFont : false,
		enableFontSize : true,
		enableFormat : true,
		enableLinks : true,
		enableLists : true,
		enableSourceEdit : true,
		width :  document.body.clientWidth >= 570? 420:document.body.clientWidth - 150,
		height : 120
	});
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
			// Ext.getCmp('content').setValue("测试看能不能修改~~");
			// alert(Ext.getCmp('content').value);
			if (Ext.get('title').dom.value == "") {
				alert("标题不能为空！");
			} else if (Ext.get('content').dom.value == "") {
				alert("正文内容不能为空！");
			} else {
				var param = "";
				// 获取文件内容，如果不为空，进行相应处理
				var file = Ext.get('file').dom.value;
				if (file != "") {
					// 文件上传格式判断：
					// 获取单选框选中的值
					dom_filetype = document.getElementsByName("fileType");
					for ( var i = 0; i < dom_filetype.length; i++) {
						if (dom_filetype[i].checked == true) {
							var fileType = dom_filetype[i].value;
						}
					}

					// 判断特定内容后缀名
					var strTemp = file.split(".");
					var strExtension = strTemp[strTemp.length - 1]
							.toLowerCase();
					// 1、图片
					if (fileType == "imgs") {
						if (strExtension != 'jpg' && strExtension != 'gif'
								&& strExtension != 'png'
								&& strExtension != 'bmp') {
							alert("请选择合适的图片文件!(jpg/gif/png/bmp)");
							return;
						}
					}
					// 2、音乐WAV\MP3\WMA\OGG
					if (fileType == "music") {
						if (strExtension != 'wav' && strExtension != 'mp3'
								&& strExtension != 'wma'
								&& strExtension != 'ogg') {
							alert("请选择合适的图片文件!(WAV\MP3\WMA\OGG)");
							return;
						}
					}
					// 3、视频 rmvb\wmv\avi\mp4\3gp\mkv
					if (fileType == "video") {
						if (strExtension != 'rmvb' && strExtension != 'wmv'
								&& strExtension != '3gp'
								&& strExtension != 'mkv'
								&& strExtension != 'avi'
								&& strExtension != 'mp4') {
							alert("请选择合适的视频文件(rmvb\wmv\avi\mp4\3gp\mkv)");
							return;
						}
					}
					// 获取文件名，并判断添加后缀
					var fileName = Ext.get('fileName').dom.value;
					var filenameTemp = fileName.split(".");
					var finalname = filenameTemp[0].toLowerCase() + "."
							+ strExtension;
		// param = "fileType:" + fileType + ",fileName:" + finalname;
					// 填写到表单item中
					Ext.getCmp('fileName').setValue(finalname);
				}
				// 表单提交
				form.getForm().submit({
					url : 'addArticle',// 后台处理的页面
		// waitMsg : 'Uploading your photo...'
				});
				Ext.Msg.alert('恭喜','发表成功！' , function(succ){
					parent.window.location.reload(); 
				}); 
			}
		}
	});
	// 标题
	var title = new Ext.form.TextField({
		width : 100,
		id : 'title',
		name : 'title',
		fieldLabel : '标题',
	});
	// 文件名称
	var filename = new Ext.form.TextField({
		width : 'auto',
		id : 'fileName',
		name:'fileName',
		fieldLabel : '名称'
	});
	// 文件
	var file = new Ext.form.TextField({
		width : 'auto',
		id : 'file',
		name:'file',
		inputType : 'file',
		fieldLabel : '文件'
	});
	// 文件类型单选
	var rdofile = new Ext.form.RadioGroup({
		fieldLabel : '类型',
		width : 'auto',
		id : 'fileType',
		style : 'padding-top:3px;height:17px;',
		items : [ {
			name : 'fileType',
			inputValue : 'imgs',
			boxLabel : '图片',
			checked : true
		}, {
			name : 'fileType',
			inputValue : 'video',
			boxLabel : '视频',
		}, {
			name : 'fileType',
			inputValue : 'music',
			boxLabel : '音乐'
		} ]
	});

	// 表单
	var form = new Ext.form.FormPanel({
		frame : true,
		labelAlign : 'left',
		buttonAlign : 'center',
		labelWidth : 30,
		width : '100%',
		layout : 'form',
		fileUpload : true,
		bodyStyle : 'padding:6px 0px 0px 15px',
		items : [ {
			layout : 'form',
			xtype : 'fieldset',
			width: '93%',
			title : '正文',
			items : [ title, editor ]
		}, {
			xtype : 'fieldset',
			title : '上传文件',
			width: '93%',
			layout : 'form',
			items : [ {
				layout : 'column',
				items : [ {
					layout : 'form',
					items : filename
				}, {
					labelAlign : 'right',
					layout : 'form',
					items : rdofile
				} ]
			}, file ]
		} ],
		buttons:[ btnsubmit, btnreset ]
	});
	// 窗口
	var win = new Ext.Panel({
		title : '<span style="color:orange"><center>有什么新鲜事想告诉大家?</center> </span>',
		plain : true,
		width:document.body.clientWidth >= 440? 440:document.body.clientWidth,
		height : 400,
		collapsible : true,
		shadow : false,
		animCollapse : true,
		autoScroll:true,
		buttonAlign : 'center',
		renderTo : 'pub_article',
		items : form,
	//	buttons : []
	});
}*/

function pub_article() {
	if ($.trim($("#ChatUserName").text()) == "游客") {
		Ext.MessageBox.confirm('权限不足', "尚未登录?请先登录", function(btn) {
			if (btn == "yes")
				sign(0);
		});
		return;
	}
	// 正文内容编辑器
	var editor = new Ext.form.HtmlEditor({
		fieldLabel : '正文',
		id : 'content',
		name : 'content',
		enableAlignments : true,
		enableColors : true,
		enableFont : false,
		enableFontSize : true,
		enableFormat : true,
		enableLinks : true,
		enableLists : true,
		enableSourceEdit : true,
		width :  document.body.clientWidth >= 570? 420:document.body.clientWidth - 150,
		height : 120
	});
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
			// Ext.getCmp('content').setValue("测试看能不能修改~~");
			// alert(Ext.getCmp('content').value);
			if (Ext.get('title').dom.value == "") {
				alert("标题不能为空！");
			} else if (Ext.get('content').dom.value == "") {
				alert("正文内容不能为空！");
			} else {
				var param = "";
				// 获取文件内容，如果不为空，进行相应处理
				var file = Ext.get('file').dom.value;
				if (file != "") {
					// 文件上传格式判断：
					// 获取单选框选中的值
					dom_filetype = document.getElementsByName("fileType");
					for ( var i = 0; i < dom_filetype.length; i++) {
						if (dom_filetype[i].checked == true) {
							var fileType = dom_filetype[i].value;
						}
					}

					// 判断特定内容后缀名
					var strTemp = file.split(".");
					var strExtension = strTemp[strTemp.length - 1]
							.toLowerCase();
					// 1、图片
					if (fileType == "imgs") {
						if (strExtension != 'jpg' && strExtension != 'gif'
								&& strExtension != 'png'
								&& strExtension != 'bmp') {
							alert("请选择合适的图片文件!(jpg/gif/png/bmp)");
							return;
						}
					}
					// 2、音乐WAV\MP3\WMA\OGG
					if (fileType == "music") {
						if (strExtension != 'wav' && strExtension != 'mp3'
								&& strExtension != 'wma'
								&& strExtension != 'ogg') {
							alert("请选择合适的图片文件!(WAV/MP3/WMA/OGG)");
							return;
						}
					}
					// 3、视频 rmvb\wmv\avi\mp4\3gp\mkv
					if (fileType == "video") {
						if (strExtension != 'rmvb' && strExtension != 'wmv'
								&& strExtension != '3gp'
								&& strExtension != 'mkv'
								&& strExtension != 'avi'
								&& strExtension != 'mp4') {
							alert("请选择合适的视频文件(rmvb/wmv/avi/mp4/3gp/mkv)");
							return;
						}
					}
					// 获取文件名，并判断添加后缀
					var fileName = Ext.get('fileName').dom.value;
					var filenameTemp = fileName.split(".");
					var finalname = filenameTemp[0].toLowerCase() + "."
							+ strExtension;
		// param = "fileType:" + fileType + ",fileName:" + finalname;
					// 填写到表单item中
					Ext.getCmp('fileName').setValue(finalname);
				}
				// 表单提交
				form.getForm().submit({
					url : 'addArticle',// 后台处理的页面
		// waitMsg : 'Uploading your photo...'
				});
				Ext.Msg.alert('恭喜','发表成功！' , function(succ){
					parent.window.location.reload(); 
				}); 
			}
		}
	});
	// 标题
	var title = new Ext.form.TextField({
		width : 100,
		id : 'title',
		name : 'title',
		fieldLabel : '标题',
	});
	// 文件名称
	var filename = new Ext.form.TextField({
		width : 'auto',
		id : 'fileName',
		name:'fileName',
		fieldLabel : '名称'
	});
	// 文件
	var file = new Ext.form.TextField({
		width : 'auto',
		id : 'file',
		name:'file',
		inputType : 'file',
		fieldLabel : '文件'
	});
	// 文件类型单选
	var rdofile = new Ext.form.RadioGroup({
		fieldLabel : '类型',
		width : 'auto',
		id : 'fileType',
		style : 'padding-top:3px;height:17px;',
		items : [ {
			name : 'fileType',
			inputValue : 'imgs',
			boxLabel : '图片',
			checked : true
		}, {
			name : 'fileType',
			inputValue : 'video',
			boxLabel : '视频',
		}, {
			name : 'fileType',
			inputValue : 'music',
			boxLabel : '音乐'
		} ]
	});

	// 表单
	var form = new Ext.form.FormPanel({
		frame : true,
		labelAlign : 'left',
		labelWidth : 30,
		width : '100%',
		layout : 'form',
		fileUpload : true,
		bodyStyle : 'padding:6px 0px 0px 15px',
		items : [ {
			layout : 'form',
			xtype : 'fieldset',
			width: '93%',
			title : '正文',
			items : [ title, editor ]
		}, {
			xtype : 'fieldset',
			title : '上传文件',
			width: '93%',
			layout : 'form',
			items : [ {
				layout : 'column',
				items : [ {
					layout : 'form',
					items : filename
				}, {
					labelAlign : 'right',
					layout : 'form',
					items : rdofile
				} ]
			}, file ]
		} ]
	});
	// 窗口
	var win = new Ext.Window({
		title : '发表微文 ',
		plain : true,
		width : document.body.clientWidth >= 570? 570:document.body.clientWidth,
		draggable : false,
		height : 430,
		resizable : true,
		shadow : false,
		modal : true,
		closable : true,
		animCollapse : true,
		buttonAlign : 'center',
		//renderTo : 'pub_article',
		items : form,
		buttons : [ btnsubmit, btnreset ]
	});
	win.show();
}
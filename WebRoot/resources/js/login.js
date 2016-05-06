function sign(kind) {
	// 用户名input
	var txtusername = new Ext.form.TextField({
		width : 160,
		allowBlank : false,
		maxLength : 20,
		id : 'j_username',
		name : 'j_username',
		fieldLabel : '用户名',
		blankText : '请输入用户名',
		maxLengthText : '用户名不能超过20个字符',
		regex : /^.{4,}$/,
		regexText : '长度不能少于4位',
	});
	// 密码input
	var txtpassword = new Ext.form.TextField({
		width : 160,
		allowBlank : false,
		maxLength : 20,
		inputType : 'password',
		id : 'j_password',
		name : 'j_password',
		fieldLabel : '密码',
		blankText : '请输入密码',
		regex : /^.{4,}$/,
		regexText : '长度不能少于4位'
	});
	// 用户名input
	var Regusername = new Ext.form.TextField({
		allowBlank : false,
		maxLength : 20,
		fieldLabel : '用户名',
		id : 'ReguserName',
		blankText : '请输入用户名',
		emptyText : "请输入用户名",
		msgTarget : "side",
		anchor : '90%',
		maxLengthText : '用户名不能超过20个字符',
		regex : /^.{4,}$/,
		regexText : '长度不能少于4位',
	});
	// 密码input
	var Regpassword = new Ext.form.TextField({
		allowBlank : false,
		maxLength : 20,
		inputType : 'password',
		id : 'Regpassword',
		msgTarget : "side",
		anchor : '90%',
		fieldLabel : '密码',
		blankText : '请输入密码',
		regex : /^.{4,}$/,
		regexText : '长度不能少于4位'
	});
	var RegRepassword = new Ext.form.TextField({
		allowBlank : false,
		maxLength : 20,
		id : 'RegRepassword',
		inputType : 'password',
		fieldLabel : '确认密码',
		blankText : '请确认密码',
		msgTarget : "side",
		anchor : '90%',
		regex : /^.{4,}$/,
		regexText : '长度不能少于4位'
	});
	var Regname = new Ext.form.TextField({
		allowBlank : false,
		maxLength : 10,
		id : 'Regname',
		fieldLabel : '昵称',
		emptyText : '请输入昵称',
		msgTarget : "side",
		maxLengthText : '昵称不能超过10个字符',
		anchor : '90%',
	});
	var Regmail = new Ext.form.TextField({
		maxLength : 10,
		id : 'Regemail',
		fieldLabel : '邮箱',
		anchor : '90%',
	});
	var Regphone = new Ext.form.TextField({
		maxLength : 10,
		id : 'RegphoneNum',
		fieldLabel : '手机号',
		anchor : '90%',
	});
	var RegDate = new Ext.form.DateField({
		fieldLabel : '生日',
		msgTarget : "side",
		id : 'birthday',
		anchor : '90%',
		format : 'Y-m-d',
		disabledDays : [ 0, 7 ]
	});
	// 重置按钮1
	var btnreset1 = new Ext.Button({
		text : '重 置',
		handler : function() {
			LoginForm.getForm().reset();
		}
	});
	// 重置按钮2
	var btnreset2 = new Ext.Button({
		text : '重 置',
		handler : function() {
			RegForm.getForm().reset();
		}
	});
	// 注册按钮
	var btnregiter = new Ext.Button({
		text : '注 册',
		handler : function() {
			if (RegForm.getForm().isValid()) {
				if ($('#RegRepassword').val() == $("#Regpassword").val()) {
					Ext.Ajax.request({
						url : 'register',
						params : {
							userName : $("#ReguserName").val(),
							password : $("#Regpassword").val(),
							name : $("#Regname").val(),
							email : $("#Regemail").val(),
							birthday : $("#birthday").val(),
							phoneNumber : $("#RegphoneNum").val()
						},
						success : function(response) {
							if (response.responseText == "success") {
								Ext.Msg.alert('成功', '注册成功！请登录。',function(){
									RegForm.getForm().reset();
									tabs.setActiveTab("LoginTab");
								});
							} else {
								Ext.Msg.alert("失败", "用户名已被注册，请修改！");
							}
						}
					});
				} else {
					Ext.Msg.alert('密码不一致', "两个密码输入不一致！");
				}
			}
		}
	});
	// 提交按钮
	var btnsubmit = new Ext.Button({
		text : '提 交',
		handler : function() {
			if (LoginForm.getForm().isValid()) {
				Ext.Ajax.request({
					url : 'j_spring_security_check',
					params : {
						j_username : Ext.get('j_username').dom.value,
						j_password : Ext.get('j_password').dom.value
					},
					success : function(response, options) {
						if (response.responseText == "fail") {
							Ext.Msg.alert('失败', '用户名或密码有误');
						} else {
							Ext.Msg.alert('恭喜', '您已成功登陆！', function(succ) {
								win.hide();
								parent.window.location.reload();
							});
						}
					}
				});
			}
		}
	});
	// 表单
	var LoginForm = new Ext.form.FormPanel({
		labelAlign : 'right',
		labelWidth : 45,
		frame : true,
		cls : 'logform',
		buttonAlign : 'center',
		bodyStyle : 'padding:16px 0px 0px 15px',
		items : [ txtusername, txtpassword ],
		buttons : [ btnsubmit, btnreset1 ]
	});

	// 表单
	var RegForm = new Ext.form.FormPanel({
		labelAlign : 'left',
		labelWidth : 56,
		frame : true,
		cls : 'Regform',
		buttonAlign : 'center',
		bodyStyle : 'padding:0px 0px 0px 0px',
		items : [ {
			xtype : 'fieldset',
			title : '必填项',
			items : [ Regusername, Regpassword, RegRepassword, Regname ]
		}, {
			xtype : 'fieldset',
			title : '非必填项',
			items : [ RegDate, Regmail, Regphone ]
		} ],
		buttons : [ btnregiter, btnreset2 ]
	});
	if (kind == "0") {
		var tabs = new Ext.TabPanel({
			animate : true,
			activeTab : 0,// 初始显示第几个Tab页
			deferredRender : true,// 是否在显示每个标签的时候再渲染标签中的内容.默认true
			tabPosition : 'top',// 表示TabPanel头显示的位置,只有两个值top和bottom.默认是top.
		});
	} else if (kind == "1") {
		var tabs = new Ext.TabPanel({
			animate : true,
			activeTab : 1,// 初始显示第几个Tab页
			deferredRender : true,// 是否在显示每个标签的时候再渲染标签中的内容.默认true
			tabPosition : 'top',// 表示TabPanel头显示的位置,只有两个值top和bottom.默认是top.
		});
	}
	tabs.add({
		id : 'LoginTab',
		title : '用户登录',
		layout : 'fit',
		listeners : {
			'beforeshow' : function() {
				win.setHeight(230);
				tabs.getItem('LoginTab').add(LoginForm);
			}
		}
	});
	tabs.add({
		id : 'RegTab',
		title : '用户注册',
		layout : 'fit',
		listeners : {
			'beforeshow' : function() {
				win.setHeight(400);
				tabs.getItem("RegTab").add(RegForm);
			}
		}
	});
	tabs.on("click", function() {
		alert();
	});
	// 窗体
	var win = new Ext.Window({
		title : '用户登陆',
		iconCls : 'logicon',
		plain : true,
		layout : 'fit',
		width : 276,
		height : 200,
		resizable : false,
		shadow : true,
		modal : true,
		closable : true,
		animCollapse : true,
		items : tabs
	});
	tabs.doLayout();
	win.show();
}
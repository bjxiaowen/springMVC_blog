package com.utils;

import javax.servlet.http.Cookie;

import com.zeng.entity.*;

public class Remcookie {

	public User_info getUserByCookie(Cookie[] cks) {
		User_info user = new User_info();// 用户对象
		// 获取客户端Cookie中所有的cookies
		Cookie[] cookies = cks;
		Cookie cookie = null;// 创建Cookie对象
		String name = "";// 当前cookie的名字
		String value = "";// 当前cookie的值
		// 判断cookies是否为空
		if (cookies != null) {
			// 循环遍历cookies，获取需要的用户名和密码
			for (int i = 0; i < cookies.length; i++) {
				cookie = cookies[i];// 获取第i个cookie
				name = cookie.getName();// 为name赋值
				value = cookie.getValue();// 为value赋值
				// 判断是否有用户名和密码的cookie，如有则读取
				if ("username".equals(name)) {
					user.setUserName(value);
				} else if ("password".equals(name)) {
					user.setPassword(value);
				}
			}
		}
		return user;
	}

}

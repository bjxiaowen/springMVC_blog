package com.utils;

import javax.servlet.http.Cookie;

import com.zeng.entity.*;

public class Remcookie {

	public User_info getUserByCookie(Cookie[] cks) {
		User_info user = new User_info();// �û�����
		// ��ȡ�ͻ���Cookie�����е�cookies
		Cookie[] cookies = cks;
		Cookie cookie = null;// ����Cookie����
		String name = "";// ��ǰcookie������
		String value = "";// ��ǰcookie��ֵ
		// �ж�cookies�Ƿ�Ϊ��
		if (cookies != null) {
			// ѭ������cookies����ȡ��Ҫ���û���������
			for (int i = 0; i < cookies.length; i++) {
				cookie = cookies[i];// ��ȡ��i��cookie
				name = cookie.getName();// Ϊname��ֵ
				value = cookie.getValue();// Ϊvalue��ֵ
				// �ж��Ƿ����û����������cookie���������ȡ
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

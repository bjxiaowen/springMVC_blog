package com.utils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpSession;

import org.directwebremoting.Container;
import org.directwebremoting.ServerContextFactory;
import org.directwebremoting.WebContextFactory;
import org.directwebremoting.event.ScriptSessionEvent;
import org.directwebremoting.event.ScriptSessionListener;
import org.directwebremoting.extend.ScriptSessionManager;
import org.directwebremoting.servlet.DwrServlet;

import com.zeng.entity.Talker;
import com.zeng.entity.User_info;

@SuppressWarnings("serial")
public class DwrScriptSessionManagerUtil extends DwrServlet {

	public void init() throws ServletException {
		//.get()��ȡ��ǰ��ServerContextʵ��
		Container container = ServerContextFactory.get().getContainer();
		ScriptSessionManager ssm = container
				.getBean(ScriptSessionManager.class);
		//��ʼ��һ������������ڼ����һ��webӦ���л�Ծsession�ı仯�����ڱ仯ʱΪ��session���id
		ScriptSessionListener ssl = new ScriptSessionListener() {
			//ScriptSessionEvent ����session�仯���¼�����getSession��ȡ��ǰ�¼��仯��session
			//session�������֪ͨ
			public void sessionCreated(ScriptSessionEvent e) {
				HttpSession session = WebContextFactory.get().getSession();
				User_info user = (User_info) session.getAttribute("user");
				e.getSession().setAttribute("userId", user.getId());
				 System.out.println("a ScriptSession is created!");
			}

			public void sessionDestroyed(ScriptSessionEvent e) {
				System.out.println("a ScriptSession is distroyed");
			}
		};
		ssm.addScriptSessionListener(ssl);
	}
}

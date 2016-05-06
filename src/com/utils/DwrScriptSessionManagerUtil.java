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
		//.get()获取当前的ServerContext实例
		Container container = ServerContextFactory.get().getContainer();
		ScriptSessionManager ssm = container
				.getBean(ScriptSessionManager.class);
		//初始化一个监控器，用于监控在一个web应用中活跃session的变化，并在变化时为该session添加id
		ScriptSessionListener ssl = new ScriptSessionListener() {
			//ScriptSessionEvent 代表session变化的事件，用getSession获取当前事件变化的session
			//session被创造的通知
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

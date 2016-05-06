package com.zeng.chat;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.ServletException;

import org.directwebremoting.Browser;
import org.directwebremoting.ScriptBuffer;
import org.directwebremoting.ScriptSession;
import org.directwebremoting.ScriptSessionFilter;
import org.directwebremoting.WebContextFactory;
import org.directwebremoting.annotations.RemoteMethod;
import org.directwebremoting.annotations.RemoteProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.zeng.entity.Talker;
import com.zeng.entity.User_info;
import com.zeng.manager.TalkManager;
import com.zeng.manager.UserManager;
import com.utils.DwrScriptSessionManagerUtil;

public class DirectController {
	@Autowired
	private TalkManager talkManager;

	public void onPageLoad(final String userid) {
		ScriptSession ss = WebContextFactory.get().getScriptSession();
		ss.setAttribute("userId", userid);
		// 注册一个监听器
		DwrScriptSessionManagerUtil dwrScriptSessionManagerUtil = new DwrScriptSessionManagerUtil();
		try {
			dwrScriptSessionManagerUtil.init();
		} catch (ServletException e) {
			e.printStackTrace();
		}

	}

	public void sendMessage(final int toUserId, final int fromUserId,
			final String fromUserName, final String msg) {
		final Talker talker = new Talker();
		SimpleDateFormat df = new SimpleDateFormat("HH:mm:ss ");
		Calendar ca = Calendar.getInstance();
		String createDay = df.format(ca.getTime());
		SimpleDateFormat df1 = new SimpleDateFormat("yyyy-MM-dd E HH:mm:ss ");
		Calendar ca1 = Calendar.getInstance();
		String createDate = df1.format(ca1.getTime());
		talker.setCreateDay(createDay);
		talker.setCreateDate(createDate);
		talker.setFromUserId(fromUserId);
		talker.setToUserId(toUserId);
		talker.setFromUserName(fromUserName);
		talker.setMsg(msg);
		talker.setIsRead(0);
		talkManager.save(talker);
		// final User_info user = talkManager.getTalkerById(fromUserId);
		Browser.withAllSessionsFiltered(new ScriptSessionFilter() {
			public boolean match(ScriptSession ss) {
				// 遍历scriptsession查找满足条件的session，满足则返回true，则将此session添加到信息推送行列，返回false则过滤掉
				// true if the session matches, false otherwise
				System.out.println("before\n**********\n" + ss + "\n********");
				if (ss.getAttribute("userId") == null) {
					System.out.println("不存在userId");
					return false;
				} else {
					// 对全体说话
					if (toUserId == 0) {
						// 把自己排除在外
						if (ss.getAttribute("userId").equals(
								String.valueOf(fromUserId))) {
							return false;
						} else {
							return true;
						}
					} else {
						return ss.getAttribute("userId").equals(
								String.valueOf(toUserId));
					}
				}
			}
		}, new Runnable() {// 对过滤后满足的scriptsession子集进行任务操作
					private ScriptBuffer sb = new ScriptBuffer();

					public void run() {
						sb.appendCall("showMessage", fromUserName + ":" + msg,talker.getCreateDate());
						// 获取刚刚满足的sessions
						Collection<ScriptSession> session = Browser
								.getTargetSessions();
						System.out.print("\n选择满足条件的用户：" + session.size());
						for (ScriptSession scriptSession : session) {
							// 添加了之后呢
							scriptSession.addScript(sb);
							System.out.println("after\n**********\n"
									+ scriptSession + "\n********");
						}
					}
				});
	}
	
	
	
	/*	final List<Talker> talkers = talkManager.getTalkerByToUserId(Integer
			.parseInt(userid));
	System.out.println("\n"+"\n看看talkers的个数："+talkers.size()+"\n");
	boolean flag = false;
	for(Talker talker : talkers){
		if(talker.getIsRead() == 0 ){
			flag = true;
			break;
		}
	}
	System.out.println("看看flag的真假"+flag);
	if (flag) {
		Browser.withAllSessionsFiltered(new ScriptSessionFilter() {
			public boolean match(ScriptSession ss) {
				System.out.println("before\n**********\n" + ss + "\n********"+ss.getAttribute("userId").equals(userid));
				System.out.println(ss.getAttribute("userId").equals(userid));
				return ss.getAttribute("userId").equals(userid);
			}
		}, new Runnable() {// 对过滤后满足的scriptsession子集进行任务操作
					private ScriptBuffer sb = new ScriptBuffer();
					public void run() {
						StringBuffer Str = new StringBuffer("你有新信息，来自于");
						for (Talker talker : talkers) {
							if(talker.getIsRead() == 0){
								Str.append("," + talker.getFromUserName() + ":"
										+ talker.getMsg() + "        ");
							}
						}
						System.out.println(Str);
						sb.appendCall("newMessage", Str);
						// 获取刚刚满足的sessions
						Collection<ScriptSession> session = Browser
								.getTargetSessions();
						System.out.print("\n选择满足条件的用户：" + session.size());
						for (ScriptSession scriptSession : session) {
							scriptSession.addScript(sb);
							System.out.println("after\n**********\n"
									+ scriptSession + "\n********");
						}
					}
				});
	}*/
}

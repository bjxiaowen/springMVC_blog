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
		// ע��һ��������
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
				// ����scriptsession��������������session�������򷵻�true���򽫴�session��ӵ���Ϣ�������У�����false����˵�
				// true if the session matches, false otherwise
				System.out.println("before\n**********\n" + ss + "\n********");
				if (ss.getAttribute("userId") == null) {
					System.out.println("������userId");
					return false;
				} else {
					// ��ȫ��˵��
					if (toUserId == 0) {
						// ���Լ��ų�����
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
		}, new Runnable() {// �Թ��˺������scriptsession�Ӽ������������
					private ScriptBuffer sb = new ScriptBuffer();

					public void run() {
						sb.appendCall("showMessage", fromUserName + ":" + msg,talker.getCreateDate());
						// ��ȡ�ո������sessions
						Collection<ScriptSession> session = Browser
								.getTargetSessions();
						System.out.print("\nѡ�������������û���" + session.size());
						for (ScriptSession scriptSession : session) {
							// �����֮����
							scriptSession.addScript(sb);
							System.out.println("after\n**********\n"
									+ scriptSession + "\n********");
						}
					}
				});
	}
	
	
	
	/*	final List<Talker> talkers = talkManager.getTalkerByToUserId(Integer
			.parseInt(userid));
	System.out.println("\n"+"\n����talkers�ĸ�����"+talkers.size()+"\n");
	boolean flag = false;
	for(Talker talker : talkers){
		if(talker.getIsRead() == 0 ){
			flag = true;
			break;
		}
	}
	System.out.println("����flag�����"+flag);
	if (flag) {
		Browser.withAllSessionsFiltered(new ScriptSessionFilter() {
			public boolean match(ScriptSession ss) {
				System.out.println("before\n**********\n" + ss + "\n********"+ss.getAttribute("userId").equals(userid));
				System.out.println(ss.getAttribute("userId").equals(userid));
				return ss.getAttribute("userId").equals(userid);
			}
		}, new Runnable() {// �Թ��˺������scriptsession�Ӽ������������
					private ScriptBuffer sb = new ScriptBuffer();
					public void run() {
						StringBuffer Str = new StringBuffer("��������Ϣ��������");
						for (Talker talker : talkers) {
							if(talker.getIsRead() == 0){
								Str.append("," + talker.getFromUserName() + ":"
										+ talker.getMsg() + "        ");
							}
						}
						System.out.println(Str);
						sb.appendCall("newMessage", Str);
						// ��ȡ�ո������sessions
						Collection<ScriptSession> session = Browser
								.getTargetSessions();
						System.out.print("\nѡ�������������û���" + session.size());
						for (ScriptSession scriptSession : session) {
							scriptSession.addScript(sb);
							System.out.println("after\n**********\n"
									+ scriptSession + "\n********");
						}
					}
				});
	}*/
}

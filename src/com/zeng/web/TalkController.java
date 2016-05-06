package com.zeng.web;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.zeng.entity.Talker;
import com.zeng.entity.User_info;
import com.zeng.manager.TalkManager;
@Controller
public class TalkController {
	@Autowired
	TalkManager talkManager;
	
/*	@RequestMapping(value="talk_addTalk")
	public void addTalk(HttpServletRequest request) {
		User_info user = (User_info) request.getSession().getAttribute("user");
		talker.setCreateDate(new Date());
		talkManager.save(talker);
	}*/
/*	@RequestMapping(value="talk_login")
	public String login() {
		System.out.println("***********\nµÇÂ½²âÊÔ\n***********");
		user = talkManager.getTalkerByNamePwd(user.getName(), user.getPwd());
		System.out.println("***********\nµÇÂ½²âÊÔ\n***********");
		request.getSession().setAttribute("user", user);
	//	return SUCCESS;
}*/	
	@RequestMapping(value="updateStatus")
	public void updateStatus(String createDate,int toUserId,HttpServletResponse response) throws IOException{
		System.out.print("\n"+createDate+"\n"+toUserId);
		Talker talker = new Talker();
		talker.setCreateDate(createDate);
		talker.setToUserId(toUserId);
		talkManager.updateStatusByInfo(talker);
		response.getWriter().write("success");
	}
	@RequestMapping(value="checkisNewMessage")
	public void checkisNewMessage(@RequestParam(value="toUserid") int toUserid,HttpServletResponse response) throws IOException{
		List<Talker> list = talkManager.getTalkerByToUserId(toUserid);
		if(list != null){
			talkManager.updateReadStatus(list);
			JSONArray jsonArray = JSONArray.fromObject(list);
			System.out.print(jsonArray);
			response.getWriter().println(jsonArray);
		}else{
			response.getWriter().print("Îª¿Õ");
		}
	}
	@RequestMapping(value="talk_getAllTalks")
	public void getAllTalks(@RequestParam(value="fromUserId")int fromUserId,@RequestParam(value="toUserId")int toUserId,
			HttpServletResponse response) throws IOException {
		List<Talker> talks = talkManager.getTalks(fromUserId,toUserId);
		JSONArray jsonArray = new JSONArray();
		JSONObject jsonObject = new JSONObject();
		JSONObject jsonUser = new JSONObject();
		for(Talker talker : talks){
			jsonObject.put("id",talker.getToUserId());
			jsonObject.put("name",talker.getFromUserName());
			jsonUser.put("toUser",jsonObject);
			jsonUser.put("Msg",talker.getMsg());
			jsonUser.put("createDate",talker.getCreateDay());
			jsonArray.add(jsonUser);
		}
		JSONObject Str = new JSONObject();
		Str.put("talks",jsonArray);
		response.getWriter().print(Str);
		System.out.print("\n\n"+Str+"\n\n");
	//	response.getWriter().print("²âÊÔÒ»´Î");
		//Êä³öjson×Ö¶Î
	}
	@RequestMapping(value="talk_getAllUsers")
	public void getAllUsers(HttpServletResponse response,@RequestParam(value="userid") int userid) throws IOException{
		List<User_info> users = talkManager.getUsers();
		JSONArray jsonArray = new JSONArray();
		JSONObject jsonObject = new JSONObject();
		for(User_info user : users){
			if(user.getId() != userid){
				jsonObject.put("leaf", "true");
				jsonObject.put("id",user.getId());
				jsonObject.put("text",user.getName());
				jsonArray.add(jsonObject);
			}
		}
		response.getWriter().print(jsonArray);
		System.out.print("\n\n"+jsonArray+"\n\n");
	}
}

package com.zeng.dao;

import java.util.List;

import com.zeng.entity.Talker;
import com.zeng.entity.User_info;

public interface TalkDao {

	User_info getTalkerById(int id);

	List<Talker> getTalks(int fromUser, int toUser);

	List<User_info> getUsers();

	void save(Talker talker);

	List<Talker> getTalkersByToUserId(int userid);

	void updateReadStatus(List<Talker> list);

	void updateStatusByInfo(Talker talker);
	
}

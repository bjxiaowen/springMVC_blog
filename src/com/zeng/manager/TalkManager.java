package com.zeng.manager;

import java.util.List;

import com.zeng.entity.Talker;
import com.zeng.entity.User_info;

public interface TalkManager {

	User_info getTalkerById(int toUser);

	List<Talker> getTalks(int fromUserId, int toUserId);

	List<User_info> getUsers();

	void save(Talker talker);

	List<Talker> getTalkerByToUserId(int userid);

	void updateReadStatus(List<Talker> list);

	void updateStatusByInfo(Talker talker);

}

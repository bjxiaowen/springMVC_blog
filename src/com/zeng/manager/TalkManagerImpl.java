package com.zeng.manager;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import com.zeng.dao.TalkDao;
import com.zeng.entity.Talker;
import com.zeng.entity.User_info;
@Service
public class TalkManagerImpl implements TalkManager{

	@Autowired
	private TalkDao talkDao;

	@Override
	public User_info getTalkerById(int id) {
		return talkDao.getTalkerById(id);
	}
	@Override
	public List<Talker> getTalks(int fromUser, int toUser) {
		return talkDao.getTalks(fromUser, toUser);
	}
	@Override
	public List<User_info> getUsers() {
		return talkDao.getUsers();
	}
	@Override
	public void save(Talker talker) {
		talkDao.save(talker);
		
	}
	@Override
	public List<Talker> getTalkerByToUserId(int userid) {
		return talkDao.getTalkersByToUserId(userid);
		
	}
	@Override
	public void updateReadStatus(List<Talker> list) {
		talkDao.updateReadStatus(list);
		
	}
	@Override
	public void updateStatusByInfo(Talker talker) {
		talkDao.updateStatusByInfo(talker);
		
	}

/*
	public String save(Object obj) {
		return talkDao.save(obj);
	}

	public User getTalkerByNamePwd(String name, String pwd) {
		return talkDao.getTalkerByNamePwd(name, pwd);
	}



	public List<TalkerDTO> getTalks(String fromUser, String toUser) {
		return talkDao.getTalks(fromUser, toUser);
	}

	public List<User> getUsers() {
		return talkDao.getUsers();
	}*/
}

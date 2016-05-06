package com.zeng.dao;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.stereotype.Service;

import com.zeng.entity.Talker;
import com.zeng.entity.User_info;

@SuppressWarnings("unchecked")
@Service
public class TalkDaoImpl implements TalkDao{

	@Autowired
	SessionFactory sessionfactory;
	@Override
	public User_info getTalkerById(int id) {
		return (User_info) sessionfactory.getCurrentSession().get(User_info.class, id);
	}
	
	@Override
	public List<Talker> getTalkersByToUserId(int userid){
		return sessionfactory.getCurrentSession().createQuery("from Talker where toUserId = ? and isRead = ?")
				.setParameter(1,0).setParameter(0, userid).list();
	}
	@Override
	public void save(Talker talker) {
		System.out.print(talker);
		sessionfactory.getCurrentSession().saveOrUpdate(talker);
	}
	//这里实现从数据库中提取数据，即聊天记录
	public List<Talker> getTalks(int fromUserid, int toUserid) {
		String hql = "FROM Talker where (fromUserId = ? and toUserId = ?) or (toUserId = ? and fromUserId = ?)";
		List<Talker> list = sessionfactory.getCurrentSession().createQuery(hql).
				setParameter(0,fromUserid).setParameter(1,toUserid).setParameter(2, fromUserid).setParameter(3, toUserid).list();
		System.out.println(list.size());
		for(Talker talker:list){
			System.out.println("*****\n"+talker.getMsg()+"'n*****");
		}
		 return list;
	}

	public List<User_info> getUsers() {
		return sessionfactory.getCurrentSession().createQuery("from User_info").list();
	}
	/*public String save(Object obj) {
		return (String) getHibernateTemplate().save(obj);
	}

	public User getTalkerByNamePwd(String name, String pwd) {
		return (User) getHibernateTemplate().find(
				"FROM User U WHERE U.name = ? and U.pwd = ? ",
				new Object[] { name, pwd }).get(0);
	}


*/

	@Override
	public void updateReadStatus(List<Talker> list) {
		for(Talker talker :list){
			sessionfactory.getCurrentSession().createQuery("update Talker set isRead = 1 where id = ?")
			.setParameter(0, talker.getId()).executeUpdate();
		}
		
	}

	@Override
	public void updateStatusByInfo(Talker talker) {
		sessionfactory.getCurrentSession().createSQLQuery("update Talker set isRead = 1 where createDate = ? and toUserId = ?")
		.setParameter(0, talker.getCreateDate()).setParameter(1, talker.getToUserId()).executeUpdate();
		System.out.println(talker.getToUserId());
		
	}
}

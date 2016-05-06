package com.zeng.dao;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.zeng.entity.User_info;
import com.zeng.entity.User_role;

@Repository
public class User_infoDaoImpl implements User_infoDao {
	@Autowired
	private SessionFactory sessionFactory;

	// 通过用户名查找用户
	@SuppressWarnings("unchecked")
	@Override
	public User_info getUserByUserName(String userName) {
		String hql = "from User_info u where u.userName=?";
		Query query = sessionFactory.getCurrentSession().createQuery(hql);
		query.setString(0, userName);
		List<User_info> u = query.list();
		if (u.size() > 0) {
			return u.get(0);
		} else
			return null;
	}

	// 获得所有用户数量
	@Override
	public int getAllcount() {
		String hql = "select count(*) from User_info";
		Long count = (Long) sessionFactory.getCurrentSession().createQuery(hql)
				.uniqueResult();
		if( count > 0){
			return count.intValue();
		}else{
			return 0;
		}
	}

	// 分页加载用户列表
	@Override
	public List<User_info> listByPage(int pageNow, int pageSize, String orderType,
			int sortInverse) {
		//这种占位符方式不知道为什么不行
		// String hql = "from User_info order by ? ASC ";
		String hql = "";
		if (orderType.equals("id")) {
			if (sortInverse % 2 == 1) {
				hql = "from User_info order by id ASC ";
			} else {
				hql = "from User_info order by id DESC ";
			}
		} else if (orderType.equals("name")) {
			if (sortInverse % 2 == 1) {
				hql = "from User_info order by name ASC ";
			} else {
				hql = "from User_info order by name DESC ";
			}
		} else if (orderType.equals("userName")) {
			if (sortInverse % 2 == 1) {
				hql = "from User_info order by userName ASC ";
			} else {
				hql = "from User_info order by userName DESC ";
			}
		} else if (orderType.equals("password")) {
			if (sortInverse % 2 == 1) {
				hql = "from User_info u order by password ASC ";
			} else {
				hql = "from User_info order by password DESC ";
			}
		} else if (orderType.equals("gender")) {
			if (sortInverse % 2 == 1) {
				hql = "from User_info order by gender ASC ";
			} else {
				hql = "from User_info order by gender DESC ";
			}
		} else if (orderType.equals("email")) {
			if (sortInverse % 2 == 1) {
				hql = "from User_info order by email ASC ";
			} else {
				hql = "from User_info order by email DESC ";
			}
		} else if (orderType.equals("question")) {
			if (sortInverse % 2 == 1) {
				hql = "from User_info order by question ASC ";
			} else {
				hql = "from User_info order by question DESC ";
			}
		} else if (orderType.equals("answer")) {
			if (sortInverse % 2 == 1) {
				hql = "from User_info order by answer ASC ";
			} else {
				hql = "from User_info order by answer DESC ";
			}
		}
		Query query = sessionFactory.getCurrentSession().createQuery(hql);
		// query.setString(0, orderType);
		query.setFirstResult((pageNow - 1) * pageSize);
		query.setMaxResults(pageSize);
		return query.list();
	}
	//通过id查找用户
	@Override
	public User_info getUser_info(String id) {

		String hql = "from User_info u where u.id=?";
		Query query = sessionFactory.getCurrentSession().createQuery(hql);
		query.setString(0, id);

		return (User_info) query.uniqueResult();
	}

	//添加用户个人信息
	@Override
	public void addUser_info(User_info user_info) {
		sessionFactory.getCurrentSession().save(user_info);
	}
	
	//删除用户
	@Override
	public void delUser_info(Integer id) {
		String hql = "delete User_info u where u.id = ?";
		Query query = sessionFactory.getCurrentSession().createQuery(hql);
		query.setParameter(0, id);
		query.executeUpdate();
	}
	//	修改用户
	@Override
	public boolean updateUser_info(User_info user) {

		String hql = "update User_info u set u.userName = ? , u.password = ?, u.name = ?, u.email = ?, u.gender = ?, u.question = ?, u.answer = ? where u.id = ?";
		Query query = sessionFactory.getCurrentSession().createQuery(hql);
		query.setString(0, user.getUserName());
		query.setString(1, user.getPassword());
		query.setString(2, user.getName());
		query.setString(3, user.getEmail());
		query.setString(4, user.getGender());
		query.setString(5, user.getBirthday());
		query.setInteger(7, user.getId());
		return (query.executeUpdate() > 0);
	}
	@Override
	//高级检索
		public List<User_info> advancedSearch(int pageNow, int pageSize, User_info user) {
			String hql = " from User_info where name like ? AND userName like ? AND gender like ? AND email like ? ";
			Query query = sessionFactory.getCurrentSession().createQuery(hql);
			query.setString(0, "%"+user.getName()+"%");
			query.setString(1, "%"+user.getUserName()+"%");
			query.setString(2, "%"+user.getGender()+"%");
			query.setString(3, "%"+user.getEmail()+"%");
			query.setFirstResult((pageNow - 1) * pageSize);
			query.setMaxResults(pageSize);
			return query.list();
		
		}
		
		//按姓名模糊查找
		@Override
		public List<User_info> findUserByVagueInformation(int pageNow, int pageSize, Object value, String informationType) {
			String hql= "";
			if(informationType.equals("name")) {
				hql = " from User_info where name like ? ";
			}
			else if(informationType.equals("userName")) {
				hql = "from User_info where userName like ? ";
			}
			Query query = sessionFactory.getCurrentSession().createQuery(hql);
			query.setString(0, "%"+value+"%");
			query.setFirstResult((pageNow - 1) * pageSize);
			query.setMaxResults(pageSize);
			return query.list();
		}
		
		//按姓名精确查找
		@SuppressWarnings("unchecked")
		@Override
		public User_info findUserByAccurateInformation(String value, String informationType) {
			String hql = "";
			if (informationType.equals("name")){
				hql = " from User_info where name = ?";
			}
			else if (informationType.equals("userName")){
				hql = " from User_info where userName = ?";
			}
			else if (informationType.equals("gender")){
				 hql = " from User_info where gender = ? ";
			}
			else if (informationType.equals("id")){
				hql = " from User_info where id = ?";
			}
			Query query = sessionFactory.getCurrentSession().createQuery(hql);
			query.setString(0, value);
			List<User_info> u = query.list();
			if (u.size() > 0) {
				return u.get(0);
			} else
				return null;
		}


		@SuppressWarnings("unchecked")
		@Override
		public List<User_info> getUserByPage(int pageNow,int pageSize) {
			String hql = "from User_info ";
			List<User_info> list =  sessionFactory.getCurrentSession().createQuery(hql)
					.setFirstResult(pageNow).setMaxResults(pageSize).list();
			return list.size() > 0? list:null;
		}

		@Override
		public void modifyUser(List<User_info> users) {
			Session session = sessionFactory.openSession();
			Transaction tx = session.beginTransaction();
			for(User_info user :users){
				if(user.getId() == 0){
					user.setId(null);
					session.save(user);
				}else{
				session.update(user);
				}
			}
			tx.commit();
		}

		@Override
		public void delUser_role(Integer id) {
			sessionFactory.getCurrentSession().createQuery("delete from User_role where userid = ?").setParameter(0, id).executeUpdate();
		}

		@Override
		public User_info getUserByUserId(int userid) {
			return (User_info) sessionFactory.getCurrentSession().get(User_info.class,userid);
		}
		
}

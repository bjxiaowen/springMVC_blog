package com.zeng.manager;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.utils.MD5;
import com.zeng.dao.ArticleDao;
import com.zeng.dao.User_infoDao;
import com.zeng.entity.Groups;
import com.zeng.entity.User_info;
import com.zeng.entity.User_role;
@Repository
public class UserManagerImpl implements UserManager {
	@Autowired
	private User_infoDao userDao;

	@Autowired
	private ArticleDao articleDao;
	// 验证登陆
	@Override
	public User_info loginCheck(User_info user) {
		User_info u = userDao.getUserByUserName(user.getUserName());
		if (u != null && u.getPassword().equals(user.getPassword())) {
			return u;
		} else {
			return null;
		}
	}

	// 用户分页显示
	@Override
	public List<User_info> listByPage(int pageNow, int pageSize, String orderType,
			int sortInverse) {
		List<User_info> list = userDao.listByPage(pageNow, pageSize, orderType,
				sortInverse);
		if (list.size() > 0) {
			return list;
		} else {
			return null;
		}
	}

	// 获得所有用户数量
	@Override
	public int getAllcount() {
		return userDao.getAllcount();
	}
	//通过用户名查找用户
	@Override
	public User_info getUserByUserName(String userName) {
		return userDao.getUserByUserName(userName);
	}
	//增加用户
	@Override
	public void addUser(User_info user) {
//		MD5 md5 = new MD5();
//		user.setPassword(md5.getMD5ofStr(user.getPassword()));
		User_role user_role = new User_role();
		user_role.setRole("ROLE_USER");
		user_role.setUser_info(user);
		Set<User_role> roleSet = new HashSet<User_role>();
		user.setUser_role(roleSet);
		user.getUser_role().add(user_role);
		userDao.addUser_info(user);
		//添加默认分组
		Groups group = new Groups();
		group.setUser_info(user);
		group.setGroupName("特别关注");
		articleDao.addGroup(group);
	}
	//删除用户
	@Override
	public void delUser(Integer id) {
		userDao.delUser_role(id);
		userDao.delUser_info(id);
	}
	//修改用户
	@Override
	public boolean updateUser(User_info user) {
		return userDao.updateUser_info(user);
	}
	//模糊查找
	@Override
	public List<User_info> findUserByVagueInformation(int pageNow, int pageSize, Object value, String informationType) {
		List<User_info> list = userDao.findUserByVagueInformation(pageNow, pageSize, value, informationType);
		if (list.size() > 0) {
			return list;
		} else {
			return null;
		}
	}
	//精确查找
	@Override
	public User_info findUserByAccurateInformation(String value, String informationType) {
		return userDao.findUserByAccurateInformation(value, informationType);
	}
	//高级查找
	@Override
	public List<User_info> advancedSearch(int pageNow, int pageSize , User_info user) {
		List<User_info> list = userDao.advancedSearch(pageNow, pageSize, user);
		if (list.size() > 0) {
			return list;
		} else {
			return null;
		}
	}

	@Override
	public List<User_info> getUserByPage(int pageNow,int pageSize) {
		return  userDao.getUserByPage(pageNow,pageSize);
	}

	@Override
	public void modifyUser(List<User_info> users) {
		userDao.modifyUser(users);
	}

	@Override
	public User_info getUserByUserId(int userid) {
		return userDao.getUserByUserId(userid);
	}

}

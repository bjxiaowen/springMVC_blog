package com.zeng.dao;

import java.util.List;

import com.zeng.entity.User_info;
import com.zeng.entity.User_role;

public interface User_infoDao {

	public User_info getUser_info(String id);

	public void addUser_info(User_info user);
	
	public void delUser_info(Integer id);
	
	public boolean updateUser_info(User_info user);
	//通过用户名查找用户
	public User_info getUserByUserName(String userName);
	//获得所有用户量
	public int getAllcount();
	//分页显示
	public List<User_info> listByPage(int pageNow, int pageSize, String orderType,
			int sortInverse);

	User_info findUserByAccurateInformation(String value, String informationType);

	List<User_info> findUserByVagueInformation(int pageNow, int pageSize,
			Object value, String informationType);

	public List<User_info> advancedSearch(int pageNow, int pageSize, User_info user);

	public List<User_info> getUserByPage(int pageNow, int pageSize);

	public void modifyUser(List<User_info> users);

	public void delUser_role(Integer id);

	public User_info getUserByUserId(int userid);

}

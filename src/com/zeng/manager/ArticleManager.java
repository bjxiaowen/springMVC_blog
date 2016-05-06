package com.zeng.manager;

import java.util.List;

import com.zeng.entity.Article;
import com.zeng.entity.Attend;
import com.zeng.entity.Collection;
import com.zeng.entity.Groups;
import com.zeng.entity.Remark;
import com.zeng.entity.Reproduce;
import com.zeng.entity.User_info;

public interface ArticleManager {
	//读取博文
	public List<Article> getArticle(int pageNow,int pageSize);
	
	//添加用户
	public void addArticle(Article article, String  belongUserName);
	
	//添加点赞
	public boolean addPraise(String userName, Integer articleid);
	
	//测试上传
	public void uploadfile(byte[] bytes,String imgsName);
	
	//根据id获取文章
	public Article getArticlebyId(Integer articleid );
	
	//添加评论
	public void addRemark(Integer articleid, String userName, Remark remark);
	
	//分页展示评论
	public List<Remark> listRemarkbyPage(Integer pageNow,Integer pageSize,Integer articleid);
	
	//获取评论数量
	public Integer getRemarkCount(Integer articleid);

	public boolean addCollection(String userName, Integer articleid);

	public void cancel_attend(String userName, Integer friendid);

	public void cancel_Collection(String userName, Integer articleid);

	public List<Collection> getAllcollection(String userName);

	public List<Article> getAllarticle(String userName);

	public List<Attend> getAllattend(String userName);

	public List<Article> getAllarticleByPage(int pageNow, int pageSize);

	public void addReproduce(String userName, Reproduce reproduce);

	public int getArticleCount();

	public void addGroup(String userName, String groupName);

	public boolean checkAttend(String userName, Integer friendid);

	public void addAttenderGroup(Attend attend);

	public List<Groups> getGroupsByPage(String userName, Integer start, Integer limit);

	public int getGroupsCount(String userName);

	
}

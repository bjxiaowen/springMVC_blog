package com.zeng.dao;

import java.util.List;

import com.zeng.entity.Article;
import com.zeng.entity.Attend;
import com.zeng.entity.Collection;
import com.zeng.entity.Groups;
import com.zeng.entity.Remark;
import com.zeng.entity.Reproduce;
import com.zeng.entity.User_info;

public interface ArticleDao {
	public List<Article> getArticle(int pageNow,int pageSize) ;
	
	public void addArticle(Article article);
	
	public Article getArticlebyuserName(String userName);
	
	public boolean addPraise(Integer userid, Integer articleid);
	
	public Article getArticlebyId(Integer articleid );
	
	public void uploadfile(byte[] bytes,String fileName);
	
	public void addRemark(Remark remark);

	public Integer getRemarkCount(Integer articleid);

	public List<Remark> listRemarkByPage(Integer pageNow, Integer pageSize,
			Integer articleid);

	public void cancel_Collection(int id, Integer articleid);

	public void cancel_attend(int id, Integer friendid);

	public boolean checkIscAttend(int id, Integer friendid);

	public boolean checkIscCollect(int id, Integer articleid);

	public void addCollection(Collection collection);

	public List<Article> getAllArticleByUserId(int id);

	public List<Collection> getAllCollectionByCollecorId(int id);

	public List<Attend> getAllAttend(int id);
	
	public List<Article>getArticleByPage(int pageNow, int pageSize);

	public void addReproduce(Reproduce reproduce);

	public void addReproduceNum(Integer articleid);

	public int getArticleCount();

	public void addGroup(Groups group);

	public Groups getGroupById(Integer groupId);

	public void addAttend(Attend attend);

	public List<Groups> getGroupsByPage(int id, Integer start, Integer limit);

	public int getGroupsCount(int  userid);
}

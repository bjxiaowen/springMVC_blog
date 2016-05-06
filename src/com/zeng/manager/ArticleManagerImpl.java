package com.zeng.manager;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.zeng.dao.ArticleDao;
import com.zeng.dao.User_infoDao;
import com.zeng.entity.Article;
import com.zeng.entity.Attend;
import com.zeng.entity.Collection;
import com.zeng.entity.Groups;
import com.zeng.entity.Remark;
import com.zeng.entity.Reproduce;
import com.zeng.entity.User_info;

@Repository
public class ArticleManagerImpl implements ArticleManager{

	@Autowired
	private ArticleDao articleDao;
	
	@Autowired
	private User_infoDao user_infoDao;
	
	@Override
	public List<Article> getArticle(int pageNow,int pageSize) {
		return articleDao.getArticle(pageNow, pageSize);
	}

	@Override
	public void addArticle(Article article, String  belongUserName) {
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd E HH:mm:ss ");
		Calendar ca = Calendar.getInstance();
		String releaseDate = df.format(ca.getTime());
		article.setReleaseDate(releaseDate);
		article.setPraiseNum(0);
		article.setCollectNum(0);
		article.setRemarkNum(0);
		article.setIsReproduce(0);
		article.setReproduceNum(0);
		User_info user_info = user_infoDao.getUserByUserName(belongUserName);
		article.setBelongUserName(user_info.getUserName());
		article.setUser_info(user_info);
		articleDao.addArticle(article);
	}
	//添加点赞
	@Override
	public boolean addPraise(String userName, Integer articleid) {
		User_info user_info = user_infoDao.getUserByUserName(userName);
		return articleDao.addPraise( user_info.getId(), articleid);
	}
	@Override
	public void uploadfile(byte[] bytes,String imgsName){
		articleDao.uploadfile(bytes, imgsName);
	}
	@Override
	public Article getArticlebyId(Integer articleid ){
		return articleDao.getArticlebyId(articleid);
	}

	@Override
	public void addRemark(Integer articleid, String userName, Remark remark) {
		remark.setArticle(articleDao.getArticlebyId(articleid));
		User_info user = user_infoDao.getUserByUserName(userName);
		remark.setUser_info(user);
		remark.setRemarker(user.getName());
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd E HH:mm:ss ");
		Calendar ca = Calendar.getInstance();
		String releaseDate = df.format(ca.getTime());
		remark.setReleaseDate(releaseDate);
		articleDao.addRemark(remark);
	}

	@Override
	public List<Remark> listRemarkbyPage(Integer pageNow, Integer pageSize,
			Integer articleid) {
		List<Remark> list = articleDao.listRemarkByPage(pageNow, pageSize,articleid);
		if (list.size() > 0) {
			return list;
		} else {
			return null;
		}
	}

	@Override
	public Integer getRemarkCount(Integer articleid) {
		return articleDao.getRemarkCount(articleid);
	}

	@Override
	public boolean addCollection(String userName, Integer articleid) {
		User_info user = user_infoDao.getUserByUserName(userName);
		//true表示已存在，false表示不存在,则添加
		if(articleDao.checkIscCollect(user.getId(),articleid)){
			return false;
		}else{
			//注意添加文章的收藏量
			User_info user_info = user_infoDao.getUserByUserName(userName);
			Article article = articleDao.getArticlebyId(articleid);
			Collection collection = new Collection();
			collection.setArticle(article);
			collection.setUser_info(user_info);
			collection.setArticleName(article.getTitle());
			articleDao.addCollection(collection);
			return true;
		}
	}

	@Override
	public void cancel_attend(String userName, Integer friendid) {
		User_info user = user_infoDao.getUserByUserName(userName);
		articleDao.cancel_attend(user.getId(),friendid);
	}

	@Override
	public void cancel_Collection(String userName, Integer articleid) {
		User_info user = user_infoDao.getUserByUserName(userName);
		articleDao.cancel_Collection(user.getId(),articleid);
		
	}

	@Override
	public List<Collection> getAllcollection(String userName) {
		User_info user = user_infoDao.getUserByUserName(userName);
		return articleDao.getAllCollectionByCollecorId(user.getId());
	}

	@Override
	public List<Article> getAllarticle(String userName) {
		User_info user = user_infoDao.getUserByUserName(userName);
		return articleDao.getAllArticleByUserId(user.getId());
	}

	@Override
	public List<Attend> getAllattend(String userName) {
		User_info user = user_infoDao.getUserByUserName(userName);
		return articleDao.getAllAttend(user.getId());
	}

	@Override
	public List<Article> getAllarticleByPage(int pageNow,int pageSize) {
		return articleDao.getArticleByPage(pageNow,pageSize);
	}

	@Override
	public void addReproduce(String userName, Reproduce reproduce) {
		User_info user = user_infoDao.getUserByUserName(userName);
		reproduce.setReproducerid(user.getId());
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd E HH:mm:ss ");
		Calendar ca = Calendar.getInstance();
		String reproduceDate = df.format(ca.getTime());
		reproduce.setReproduceDate(reproduceDate);
		articleDao.addReproduce(reproduce);
		//还有给用户加文章转载量
		articleDao.addReproduceNum(reproduce.getArticleid());
	}

	@Override
	public int getArticleCount() {
		return articleDao.getArticleCount();
	}

	@Override
	public List<Groups> getGroupsByPage(String userName,Integer start,Integer limit ) {
		User_info user = user_infoDao.getUserByUserName(userName);
		return articleDao.getGroupsByPage(user.getId(),start,limit);
	}

	@Override
	public void addGroup(String userName, String groupName) {
		User_info user = user_infoDao.getUserByUserName(userName);
		Groups group = new Groups();
		group.setUser_info(user);
		group.setGroupName(groupName);
		articleDao.addGroup(group);
	}

	@Override
	public void addAttenderGroup(Attend attend) {
		String friendName = user_infoDao.getUserByUserId(attend.getFriendid()).getName();
		attend.setFriendName(friendName);
		Groups group = articleDao.getGroupById(attend.getGroupId());
		attend.setGroup(group);
		articleDao.addAttend(attend);
	}
	//true表示大于0个用户，即已存在
	@Override
	public boolean checkAttend(String userName, Integer friendid) {
		int userid = user_infoDao.getUserByUserName(userName).getId();
		return articleDao.checkIscAttend(userid,friendid);
	}

	@Override
	public int getGroupsCount(String userName) {
		int userid = user_infoDao.getUserByUserName(userName).getId();
		return articleDao.getGroupsCount(userid);
	}

}

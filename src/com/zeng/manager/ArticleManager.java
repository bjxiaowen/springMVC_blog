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
	//��ȡ����
	public List<Article> getArticle(int pageNow,int pageSize);
	
	//����û�
	public void addArticle(Article article, String  belongUserName);
	
	//��ӵ���
	public boolean addPraise(String userName, Integer articleid);
	
	//�����ϴ�
	public void uploadfile(byte[] bytes,String imgsName);
	
	//����id��ȡ����
	public Article getArticlebyId(Integer articleid );
	
	//�������
	public void addRemark(Integer articleid, String userName, Remark remark);
	
	//��ҳչʾ����
	public List<Remark> listRemarkbyPage(Integer pageNow,Integer pageSize,Integer articleid);
	
	//��ȡ��������
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

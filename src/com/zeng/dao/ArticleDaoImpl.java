package com.zeng.dao;

import java.security.acl.Group;
import java.util.LinkedList;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.zeng.entity.Article;
import com.zeng.entity.Attend;
import com.zeng.entity.Collection;
import com.zeng.entity.Groups;
import com.zeng.entity.PraiseNum;
import com.zeng.entity.Remark;
import com.zeng.entity.Reproduce;
import com.zeng.entity.User_info;

@Repository
public class ArticleDaoImpl implements ArticleDao {

	@Autowired
	private SessionFactory sessionFactory;

	@SuppressWarnings("unchecked")
	@Override
	public List<Article> getArticle(int pageNow, int pageSize) {
		String hql = "from Article order by releaseDate DESC";
		Query query = sessionFactory.getCurrentSession().createQuery(hql);
		query.setFirstResult((pageNow - 1) * pageSize).setMaxResults(pageSize);
		List<Article> list = query.list();
		return (list.size() > 0 ? list : null);
	}

	@Override
	public void addArticle(Article article) {
		sessionFactory.getCurrentSession().save(article);
	}

	@Override
	public Article getArticlebyuserName(String userName) {
		String hql = "from Article where userName = ?";
		Query query = sessionFactory.getCurrentSession().createQuery(hql)
				.setParameter(0, userName);
		return (Article) query.uniqueResult();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.zeng.dao.ArticleDao#addPraise(java.lang.Integer,
	 * java.lang.Integer) true:表示成功点赞;false：表示已点赞过
	 */
	@SuppressWarnings("unchecked")
	@Override
	public boolean addPraise(Integer userid, Integer articleid) {
		boolean flag = true;
		Session session = sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		List<PraiseNum> list = session
				.createQuery("from PraiseNum where userid = ?")
				.setParameter(0, userid).list();
		if (list != null) {
			for (PraiseNum pr : list) {
				if (pr.getArticleid() == articleid) {
					flag = false;
				}
			}
		}
		// 未点赞
		// 保留点赞记录
		if (flag == true) {
			PraiseNum praiseNum = new PraiseNum();
			praiseNum.setArticle((Article) session
					.get(Article.class, articleid));
			praiseNum.setUser_info((User_info) session.get(User_info.class,
					userid));
			session.save(praiseNum);
			// 点赞数+1
			String sql = "update article set praiseNum = praiseNum + 1 where id = ?";
			session.createSQLQuery(sql).setParameter(0, articleid)
					.executeUpdate();
		}
		tx.commit();
		return flag;
	}

	@Override
	public Article getArticlebyId(Integer articleid) {
		String hql = "from Article where id = ?";
		Query query = sessionFactory.getCurrentSession().createQuery(hql)
				.setParameter(0, articleid);
		return (Article) query.uniqueResult();
	}

	@Override
	public void uploadfile(byte[] bytes, String fileName) {
		String sql = "update article set imgs = ? ,imgsName = ? where id = ";
		sessionFactory.getCurrentSession().createSQLQuery(sql)
				.setParameter(0, bytes).setParameter(1, fileName)
				.executeUpdate();
	}

	@Override
	public void addRemark(Remark remark) {
		sessionFactory.getCurrentSession().save(remark);
		String sql = "update article set  remarkNum = remarkNum + 1 where id = ?";
		sessionFactory.getCurrentSession().createSQLQuery(sql).setParameter(0, remark.getArticle().getId()).executeUpdate();
	}

	@Override
	public Integer getRemarkCount(Integer articleid) {
		String hql = "select count(*) from Remark where remarkArticleid = ?";
		Long count = (Long) sessionFactory.getCurrentSession().createQuery(hql)
				.setParameter(0, articleid).uniqueResult();
		if (count > 0) {
			return count.intValue();
		} else {
			return 0;
		}
	}
	@Override
	public int getGroupsCount(int  userid) {
		String hql = "select count(*) from Groups where belongUserid= ?";
		Long count = (Long) sessionFactory.getCurrentSession().createQuery(hql)
				.setParameter(0, userid).uniqueResult();
		if (count > 0) {
			return count.intValue();
		} else {
			return 0;
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Remark> listRemarkByPage(Integer pageNow, Integer pageSize,
			Integer articleid) {
		String hql = "from Remark where remarkArticleid = ? ";
		return sessionFactory.getCurrentSession().createQuery(hql)
				.setParameter(0, articleid)
				.setFirstResult((pageNow - 1) * pageSize)
				.setMaxResults(pageSize).list();
	}

	@Override
	public void cancel_Collection(int id, Integer articleid) {
		String sql = "delete from collection where Articleid = ? and collectorid = ?";
		sessionFactory.getCurrentSession().createSQLQuery(sql).setParameter(0, articleid).setParameter(1, id).executeUpdate();
		String sql1 = "update article set  collectNum = collectNum - 1 where id = ?";
		sessionFactory.getCurrentSession().createSQLQuery(sql1).setParameter(0, articleid).executeUpdate();
	}

	@Override
	public void cancel_attend(int id, Integer friendid) {
		String sql = "delete from attend where userid = ? and friendid = ?";
		sessionFactory.getCurrentSession().createSQLQuery(sql).setParameter(0, id).setParameter(1, friendid).executeUpdate();
	}

	@Override
	public void addCollection(Collection collection) {
		sessionFactory.getCurrentSession().save(collection);
		String sql = "update article set collectNum = collectNum + 1 where id = ?";
		sessionFactory.getCurrentSession().createSQLQuery(sql).setParameter(0, collection.getArticle().getId()).executeUpdate();
	}

	@Override
	public void addAttend(Attend attend) {
		sessionFactory.getCurrentSession().save(attend);
	}

	@Override
	public boolean checkIscAttend(int userid, Integer friendid) {
		String hql = "from Attend where userid = ? and friendid = ?";
		Query query = sessionFactory.getCurrentSession().createQuery(hql).setParameter(0, userid).setParameter(1,friendid);
		return (query.list().size() > 0  ?  true:false);
	}

	@Override
	public boolean checkIscCollect(int id, Integer articleid) {
		String hql = " from Collection where Articleid = ? and collectorid = ?";
		Query query = sessionFactory.getCurrentSession().createQuery(hql).setParameter(0, articleid).setParameter(1,id);
		return (query.list().size() > 0 ? true : false);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Article> getAllArticleByUserId(int id) {
		String hql = "from Article where belongUserid = ?";
		return sessionFactory.getCurrentSession().createQuery(hql).setParameter(0, id).list();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Collection> getAllCollectionByCollecorId(int id) {
		String hql = "from Collection where collectorid = ?";
		return  sessionFactory.getCurrentSession().createQuery(hql).setParameter(0, id).list();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Attend> getAllAttend(int id) {
		String hql = "from Attend where userid = ?";
		return sessionFactory.getCurrentSession().createQuery(hql).setParameter(0, id).list();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Article> getArticleByPage(int pageNow,int pageSize) {
		List<Article> list =  sessionFactory.getCurrentSession().createQuery("from Article")
				.setFirstResult(pageNow).setMaxResults(pageSize).list();
		return list.size() > 0? list:null;
	}

	@Override
	public void addReproduce(Reproduce reproduce) {
		sessionFactory.getCurrentSession().save(reproduce);
	}

	@Override
	public void addReproduceNum(Integer articleid) {
		String sql = "update article set reproduceNum = reproduceNum + 1 where id = ?";
		sessionFactory.getCurrentSession().createSQLQuery(sql).setParameter(0, articleid).executeUpdate();
	}

	@Override
	public int getArticleCount() {
		String hql = "select count(*) from Article";
		Long count =   (Long) sessionFactory.getCurrentSession().createQuery(hql).uniqueResult();
		if( count > 0){
			return count.intValue();
		}else{
			return 0;
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Groups> getGroupsByPage(int id,Integer start,Integer limit) {
		return sessionFactory.getCurrentSession().createQuery("from Groups where belongUserid = ?")
		.setFirstResult(start).setMaxResults(limit).setParameter(0, id).list();
	}

	@Override
	public void addGroup(Groups group) {
		sessionFactory.getCurrentSession().save(group);
	}

	@Override
	public Groups getGroupById(Integer groupId) {
		return (Groups) sessionFactory.getCurrentSession().get(Groups.class, groupId);
	}


}

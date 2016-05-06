package com.test;


import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

import com.zeng.entity.Article;
import com.zeng.entity.User_info;
import com.zeng.entity.User_role;


public class SSHtest {
	
	@Test
	public void testssh(){
		ApplicationContext ac = new FileSystemXmlApplicationContext("/src/config/spring/spring-common.xml");//ClassPathXmlApplicationContext("spring-common.xml");
		SessionFactory sessionFactory = (SessionFactory) ac.getBean("sessionFactory");////
		Session session = sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		String hql = "select u from User_info u left join u.user_role r where u.id = r.userid";
		List lists = session.createQuery(hql).list();
		for(Iterator pairs = lists.iterator();pairs.hasNext();){
		//	Object[] pair = (Object[]) pairs.next();
		//	User_info user = (User_info) pair[0];
		//	User_role role = (User_role) pair[1];
			System.out.println(pairs.next());
//			System.out.println(role);
		}
		tx.commit();
	}
	//@Test
	public void testjson(){
/*		String str = "{\"id\":1,\"name\":\"admin\",\"userName\":\"admin\",\"password\":\"admin\",\"birthday\":\"123\","+
"\"email\":\"789\",\"gender\":\"213\",\"location\":\"123省市\"}";
		JSONObject json = JSONObject.fromObject(str);
		System.out.println(json);
		Iterator<String> keys=json.keys();
		String key;  
        while(keys.hasNext()){  
            key=keys.next(); 
           System.out.println(json.get(key));
           // json.get(key) == null;
            System.out.println(key);*/
		
//        }
//		User_info user = new User_info(\"1\",\"test\",\"3\");
//		JSONObject jsonObject = JSONObject.fromObject(user); 
//		System.out.println(jsonObject.toString());//输出
	//	List<User_info> stuList = new ArrayList<User_info>(); 
//		for (int i = 0; i < stuList.length; i++) { 
//			stus = new User_info(); 
//		stus[i].setAge(i*10+8); 
//		stus[i].setName(\"zhang\"+i); 
//		stus[i].setSex(\"male\"); 
//		//添加到list,一会儿用 
//		stuList.add(stus[i]); 
		SimpleDateFormat df = new SimpleDateFormat("HH:mm:ss ");
		Calendar ca = Calendar.getInstance();
		String releaseDate = df.format(ca.getTime());
		SimpleDateFormat df1= new SimpleDateFormat("yyyy-MM-dd E");
		Calendar ca1 = Calendar.getInstance();
		String releaseDate1 = df1.format(ca1.getTime());
		System.out.println(releaseDate);
		System.out.println(releaseDate1);
		} 
//	@Test
	public void testjson2(){
	/*	ApplicationContext ac = new FileSystemXmlApplicationContext(\"/src/config/spring/spring-common.xml\");//ClassPathXmlApplicationContext(\"spring-common.xml\");
		SessionFactory sessionFactory = (SessionFactory) ac.getBean(\"sessionFactory\");////
		Session session = sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		String hql = \"from Article order by releaseDate DESC\";
		Query query = session.createQuery(hql);
		query.setFirstResult(0).setMaxResults(5);
		List<Article> list = query.list();
		JSONArray jsonArray = new JSONArray(); 
		for(Article article:list){
			JSONObject jsonObject = new JSONObject();
			jsonObject.put(\"id\",article.getId());
			jsonObject.put(\"releaseDate\",article.getReleaseDate());
			jsonObject.put(\"belongUserName\",article.getBelongUserName());
			jsonObject.put(\"title\",article.getTitle());
			jsonObject.put(\"content\",article.getContent());
			jsonObject.put(\"PraiseNum\",article.getPraiseNum());
			jsonObject.put(\"imgs\",article.getImgsName());
			jsonArray.add(jsonObject);
		}
	System.out.println(jsonArray+\"\n\n\n\");
//		JSONArray jsonArray = new JSONArray(); 
//		jsonArray.addAll(list);
//		System.out.println(jsonArray);
		tx.commit();*/
	}
}

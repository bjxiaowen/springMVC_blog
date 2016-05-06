package com.zeng.aop;

import org.junit.Test;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;


public class TestAop {
	@Test
	public void test() { 
		ApplicationContext ctx = new FileSystemXmlApplicationContext("src/config/spring/spring-beans.xml");     
        BuyBook b = (BuyBook)ctx.getBean("newBuyBook");  
		
        b.buyBook("С��", "�������㡷");
        b.buyBook("С��", "��������2��");
	}

}

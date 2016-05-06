package com.zeng.aop;

public class BuyBookImpl implements BuyBook {

	@Override
	public void buyBook(String customer, String book) {
		System.out.println(customer+"你好！你成功购买了一本"+book+"!欢迎再次光临！");
	}

}

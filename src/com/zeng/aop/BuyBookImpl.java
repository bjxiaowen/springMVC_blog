package com.zeng.aop;

public class BuyBookImpl implements BuyBook {

	@Override
	public void buyBook(String customer, String book) {
		System.out.println(customer+"��ã���ɹ�������һ��"+book+"!��ӭ�ٴι��٣�");
	}

}

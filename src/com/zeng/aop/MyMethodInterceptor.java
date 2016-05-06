package com.zeng.aop;

import java.util.HashSet;
import java.util.Set;

import org.aopalliance.intercept.Invocation;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

public class MyMethodInterceptor implements MethodInterceptor{
	private Set customers =  new HashSet();
	@Override
	public Object invoke(MethodInvocation arg0) throws Throwable {
		String customer =  (String) arg0.getArguments()[0];
		Object result = null;
		if(customers.contains(customer)){  
            System.out.println("注意，一名顾客只能买一本打折书！");  
       } else{  
            result = arg0.proceed();  
       }  
       customers.add(customer);  
       return result;  
	}
	
}

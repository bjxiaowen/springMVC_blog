package com.zeng.aop;

import java.lang.reflect.Method;

import org.springframework.aop.MethodBeforeAdvice;

public class MyBeforeAdvice implements MethodBeforeAdvice {

	@Override
	public void before(Method arg0, Object[] arg1, Object arg2)
			throws Throwable {
		String customer = (String)arg1[0];        
        System.out.println("ª∂”≠π‚¡Ÿ!"+customer+"!");     
    }

}

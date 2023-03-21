package com.ssm.vue.jd.util;

import org.springframework.context.support.ClassPathXmlApplicationContext;

public class SpringUtil {
	
	private static ClassPathXmlApplicationContext app = new ClassPathXmlApplicationContext("conf/spring.xml");
	
	//获取bean对象
	public static <T> T getBean(Class<T> c) {
		T t;
		t = app.getBean(c);
		return t;
	}
	
	public static Object getBean(String name) {
		
		Object bean = app.getBean(name);
		return bean;
	}
	
	
	
}

package com.ssm.vue.jd.dao;

import com.ssm.vue.jd.pojo.User;

public interface LoginDao {
	
	//用户登录的方法
	User login(User user);
	
	//写入用户最后登录时间的方法
	int lastLoginTime(int user_id);
	
	

}

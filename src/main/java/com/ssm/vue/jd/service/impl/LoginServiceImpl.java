package com.ssm.vue.jd.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ssm.vue.jd.dao.LoginDao;
import com.ssm.vue.jd.pojo.User;
import com.ssm.vue.jd.service.LoginService;

@Service
public class LoginServiceImpl implements LoginService {
	
	@Autowired
	LoginDao loginDao;

	@Override
	@Transactional(rollbackFor = Exception.class)
	public User login(User user) {
		User login_user = loginDao.login(user);
		if(user == null) {
			return null;
		}
		//插入用户最后登录时间
		loginDao.lastLoginTime(login_user.getUser_id());
		return login_user;
	}
	//用户登录的方法
	
}

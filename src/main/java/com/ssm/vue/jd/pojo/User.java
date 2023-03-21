package com.ssm.vue.jd.pojo;

import java.util.Date;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("prototype")
public class User {
	
	private int user_id;//用户编号
	private String user_name;//用户名
	private String user_pass;//密码
	private Date last_login_date;//最后登录时间 
	
	public User() {
		
	}
	
	public User(int user_id, String user_name, String user_pass, Date last_login_date) {
		super();
		this.user_id = user_id;
		this.user_name = user_name;
		this.user_pass = user_pass;
		this.last_login_date = last_login_date;
	}
	
	public int getUser_id() {
		return user_id;
	}
	public void setUser_id(int user_id) {
		this.user_id = user_id;
	}
	public String getUser_name() {
		return user_name;
	}
	public void setUser_name(String user_name) {
		this.user_name = user_name;
	}
	public String getUser_pass() {
		return user_pass;
	}
	public void setUser_pass(String user_pass) {
		this.user_pass = user_pass;
	}
	public Date getLast_login_date() {
		return last_login_date;
	}
	public void setLast_login_date(Date last_login_date) {
		this.last_login_date = last_login_date;
	}
	
	
}

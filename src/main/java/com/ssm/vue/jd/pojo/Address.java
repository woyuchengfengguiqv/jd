package com.ssm.vue.jd.pojo;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("prototype")
public class Address {
	private int address_id;//地址表主键
	private int user_id;//用户编号
	private String name;//收货人姓名
	private String address;//收件人地址
	private String phone;//收件人电话
	
	public Address() {
		
	}
	
	public Address(int address_id, int user_id, String name, String address, String phone) {
		super();
		this.address_id = address_id;
		this.user_id = user_id;
		this.name = name;
		this.address = address;
		this.phone = phone;
	}

	public int getAddress_id() {
		return address_id;
	}

	public void setAddress_id(int address_id) {
		this.address_id = address_id;
	}

	public int getUser_id() {
		return user_id;
	}

	public void setUser_id(int user_id) {
		this.user_id = user_id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}
	
	
	
}

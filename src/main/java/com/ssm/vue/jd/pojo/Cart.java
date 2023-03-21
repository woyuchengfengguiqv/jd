package com.ssm.vue.jd.pojo;

import java.util.Date;
import java.util.List;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("prototype")
public class Cart {
	private int cart_id;//购物车主键
	private int user_id;//用户编号
	private int commodity_id;//商品编号
	private int count;//商品数量
	private Date timestamp;//加购时间
	private int selected;//选购状态(0未购买，1已购买)
	private Commodity commodity;//商品信息
	
	public Cart() {
		
	}
	
	public Cart(int cart_id, int user_id, int commodity_id, int count, Date timestamp, int selected,
			Commodity commodity) {
		super();
		this.cart_id = cart_id;
		this.user_id = user_id;
		this.commodity_id = commodity_id;
		this.count = count;
		this.timestamp = timestamp;
		this.selected = selected;
		this.commodity = commodity;
	}

	public int getCart_id() {
		return cart_id;
	}

	public void setCart_id(int cart_id) {
		this.cart_id = cart_id;
	}

	public int getUser_id() {
		return user_id;
	}

	public void setUser_id(int user_id) {
		this.user_id = user_id;
	}

	public int getCommodity_id() {
		return commodity_id;
	}

	public void setCommodity_id(int commodity_id) {
		this.commodity_id = commodity_id;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public Date getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
	}

	public int getSelected() {
		return selected;
	}

	public void setSelected(int selected) {
		this.selected = selected;
	}

	public Commodity getCommodity() {
		return commodity;
	}

	public void setCommodity(Commodity commodity) {
		this.commodity = commodity;
	}
	
	
	
	
	
	
}

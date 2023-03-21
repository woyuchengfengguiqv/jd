package com.ssm.vue.jd.dao;

import java.util.List;

import com.ssm.vue.jd.pojo.Cart;
import com.ssm.vue.jd.pojo.User;

//购物车数据层
public interface CartDao {
	
	
	//添加购物车的方法
	int addProductToCart(Cart cart);
	
	//判断该商品是否已经被该用户加入购物车(查询count字段)
	int existsCommodity(Cart cart);
	
	//修改购物车某用户添加商品的数量
	int updateCart(Cart cart);
	
	
	
	//查询当前用户购物车商品信息
	List<Cart> queryCartByUser(User user);
}

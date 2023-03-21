package com.ssm.vue.jd.service;

import java.util.List;

import com.ssm.vue.jd.pojo.Cart;
import com.ssm.vue.jd.pojo.Commodity;
import com.ssm.vue.jd.pojo.User;

public interface CartService {
	
	//向购物车添加商品的方法
	int addProductToCart(Cart cart);
	
	//查询当前用户的购物车商品信息
	List<Cart> queryCaryByUser(User user);

	List<Commodity> redisQuertShoppingCart(int user_id);

}

package com.ssm.vue.jd.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssm.vue.jd.dao.CartDao;
import com.ssm.vue.jd.dao.CommodityDao;
import com.ssm.vue.jd.pojo.Cart;
import com.ssm.vue.jd.pojo.Commodity;
import com.ssm.vue.jd.pojo.User;
import com.ssm.vue.jd.service.CartService;
import com.ssm.vue.jd.util.JedisUtil;

//向购物车添加商品
@Service
public class CartServiceImpl implements CartService {
	
	
	@Autowired
	CartDao cartDao;
	
	@Autowired
	CommodityDao commodityDao;

	//向购物车添加商品
	@Override
	public int addProductToCart(Cart cart) {
		//向购物车添加商品
		//判断该商品是已经被该用户加入购物车
		int count = cartDao.existsCommodity(cart);
		if(count != 0) {
			count += cart.getCount();
			cart.setCount(count);
			//修改购物车商品数
			int updateCart = cartDao.updateCart(cart);
			return updateCart;
		}
		int addProductToCart = cartDao.addProductToCart(cart);
		return addProductToCart;
	}

	

	//查询当前用户的购物车商品信息
	@Override
	public List<Cart> queryCaryByUser(User user) {
		List<Cart> queryCartByUser = cartDao.queryCartByUser(user);
		return queryCartByUser;
	}
	
	//从查询当前用户的购物车商品信息（redis实现）
	@Override
	public List<Commodity> redisQuertShoppingCart(int user_id) {
		//根据商品编号查询
		//获取hash表的所有键和值
		Map<String, String> commodityMap = JedisUtil.hgetAll(user_id+"");
		Set<String> keys = commodityMap.keySet();
		List<Commodity> list = new ArrayList<Commodity>();
		for(String key:keys) {
			//根据商品编号查询商品信息
			String value = commodityMap.get(key);
			int commodity_id = Integer.parseInt(key);
			Commodity commodity = commodityDao.getOnlyCommodityById(commodity_id);
			commodity.setCartShoppingNum(Integer.parseInt(value));//购物车商品数量
			list.add(commodity);
		}
		return list;
	}
	
}

package com.ssm.vue.jd.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.ssm.vue.jd.pojo.Address;
import com.ssm.vue.jd.pojo.Order;
import com.ssm.vue.jd.pojo.User;

public interface OrderService {
	
	
	//获取地址信息的方法
	List<Address>  getAddress(Map<String,Object> map);

	//生成订单的方法
	long createOrder(Map<String, Object> map) throws RuntimeException;
	
	//查询订单信息
	Order getOrderById(Order order);
	
	//完成订单的方法
	int completeOrder(Order order);
	
	//生成购物车订单(返回订单编号)
	long createShoppingCartOrder(Order order);
	
	//获取所有订单信息
	List<Order> getAllOrder(int user_id);

}

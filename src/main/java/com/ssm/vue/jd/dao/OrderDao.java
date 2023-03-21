package com.ssm.vue.jd.dao;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.ssm.vue.jd.pojo.Address;
import com.ssm.vue.jd.pojo.Order;
import com.ssm.vue.jd.pojo.OrderGoods;
import com.ssm.vue.jd.pojo.User;

public interface OrderDao {
	//生成订单的方法(未支付的订单)
	int createOrder(Map<String,Object> map);
	//生成订单的方法(购物车未支付订单)
	int createOrder1(Order order);
	
	
	//向订单商品详情表插入信息
	int insertOrderGoodsByOrder(Map<String,Object> map);
	
	//向商品订单详情表插入信息
	int insertOrderGoodsByOrder1(OrderGoods orderGoods);
	
	
	
	//获取收货信息的方法
	List<Address> getAddress(Map<String,Object> map);
	
	//查询订单信息的方法
	Order getOrderById(Order order);
	
	//查询订单商品列表
	List<OrderGoods> getOrderGoodsByOrderId(Order order);
	
	//完成订单的方法
	int completeOrder(Order order);
	
	//根据订单编号查询订单信息
	Order getOrderByOrder_id(long order_id);
	
	//获取当前用户的订单表的所有订单编号
	List<Long> getAllOrder_id(int user_id);
	
	//查询当前用户所有的订单信息
	List<Order> getAllOrder(int user_id);
}

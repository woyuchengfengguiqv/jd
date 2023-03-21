package com.ssm.vue.jd.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.gson.Gson;
import com.ssm.vue.jd.dao.CommodityDao;
import com.ssm.vue.jd.dao.OrderDao;
import com.ssm.vue.jd.pojo.Address;
import com.ssm.vue.jd.pojo.Commodity;
import com.ssm.vue.jd.pojo.Order;
import com.ssm.vue.jd.pojo.OrderGoods;
import com.ssm.vue.jd.pojo.User;
import com.ssm.vue.jd.service.OrderService;
import com.ssm.vue.jd.util.CreateOrderId;
import com.ssm.vue.jd.util.HttpClientUtils;

@Service
public class OrderServiceImpl implements OrderService {
	
	@Autowired
	OrderDao orderDao;
	@Autowired
	CommodityDao commodityDao;

	//生成订单的方法("未完成订单")
	//生成订单成功返回订单编号
	@Override
	@Transactional(rollbackFor = Exception.class)
	public long createOrder(Map<String,Object> map) throws RuntimeException {
		//生成订单编号
		long order_id = CreateOrderId.createOrderId();
		map.put("order_id", order_id);
		//设置订单状态为0(未完成)
		map.put("order_state", 0);
		//将订单商品列表取出
		LinkedHashMap<String, Object> orderGoods = (LinkedHashMap<String, Object>)map.get("commodityList");
		//将商品取出
		//OrderGoods orderGoods = commodityList.get(0);//订单商品详情类
		//查询商品库存
		int stock = commodityDao.getStock(orderGoods);
		if((Integer)orderGoods.get("order_amount") > stock) {
			//库存不足，抛出异常
			throw new RuntimeException("库存不足，提交订单失败");
		}
		//库存充足，调用减库存的方法
		Map<String,Object> substock = new HashMap<String,Object>();
		//商品编号
		substock.put("commodity_id", orderGoods.get("commodity_id"));
		//相减后的库存
		substock.put("stock", stock - (Integer)orderGoods.get("order_amount"));
		int subtractStock = commodityDao.subtractStock(substock);
		if(subtractStock == 0) {
			//减库存失败
			throw new RuntimeException("提交订单失败");
		}
		//将订单编号写入订单商品详情表
		orderGoods.put("order_id", order_id);
		//将订单写入数据库
		int createOrder = orderDao.createOrder(map);
		//将订单商品写入订单商品详情表
		int insertOrderGoodsByOrder = orderDao.insertOrderGoodsByOrder(orderGoods);
		if(createOrder ==0 || insertOrderGoodsByOrder==0) {
			//库存不足，抛出异常
			throw new RuntimeException("提交订单失败");
		}
		return order_id;
	}

	//查询收件人信息
	@Override
	public List<Address> getAddress(Map<String,Object> map) {
		// TODO Auto-generated method stub
		//获取当前用户收货信息
		List<Address> address = orderDao.getAddress(map);
		return address;
	}

	//查询订单信息的方法
	@Override
	public Order getOrderById(Order order) {
		Order orderById = orderDao.getOrderById(order);
		List<OrderGoods> orderGoodsByOrderId = orderDao.getOrderGoodsByOrderId(order);
		orderById.setCommodityList(orderGoodsByOrderId);
		return orderById;
	}

	@Override
	public int completeOrder(Order order) {
		// TODO Auto-generated method stub
		
		
		
		
		Date date = new Date(System.currentTimeMillis() + (1000*60*60*24*2));
		order.setReceiving_time(date);
		int completeOrder = orderDao.completeOrder(order);
		return completeOrder;
	}

	
	//购物车生成订单
	@Override
	@Transactional(rollbackFor = Exception.class)
	public long createShoppingCartOrder(Order order) throws RuntimeException{
		//生成订单编号
		long order_id = CreateOrderId.createOrderId();
		order.setOrder_id(order_id);
		//将订单商品表取出(里面也存储着用户购买的商品数量)
		List<OrderGoods> goodsList = order.getCommodityList();
		//根据商品编号查询商品信息
		List<Commodity> commodityById = commodityDao.getCommodityById(goodsList);
		//遍历商品信息
		//记录订单总价格
		BigDecimal order_price = new BigDecimal(0);
		int total = 0;//记录订单 商品数量
		for(Commodity commodity:commodityById) {//外层，数据库查询出的商品信息
			//比较商品库存是否大于用户购买数量
			for(OrderGoods orderGoods:goodsList) {//里层，前端传入的商品编号，和购买商品数量
				if(commodity.getCommodity_id() == orderGoods.getCommodity_id()) {
					//是同一件商品比较库存数量是否大于用户购买的数量
					if(commodity.getStock() >= orderGoods.getOrder_amount()) {
						//库存充足，调用减库存的方法
						int stock = commodity.getStock() - orderGoods.getOrder_amount();
						commodityDao.subtractStock1(stock);
						//将商品表的价格等信息写入订单商品表
						orderGoods.setImg_url(commodity.getImg_url());//图片地址
						orderGoods.setOrder_commodity_name(commodity.getCommodity_name());//商品名称
						orderGoods.setOrder_commodity_price(commodity.getCommodity_price());//商品价格
						orderGoods.setOrder_id(order_id);//订单编号
						//商品总价格
						BigDecimal  total_price = orderGoods.getOrder_commodity_price()
								.multiply(new BigDecimal(orderGoods.getOrder_amount()));
						orderGoods.setTotal_price(total_price);//商品总价
						order_price = order_price.add(total_price);
						//记录订单商品总数
						total += orderGoods.getOrder_amount();
						break;//跳出循环
					}else {
						//库存不足，提交订单失败
						throw new RuntimeException("库存不足，生成订单失败");
					}
				}
			}
			
		}
		//将订单总价写入订单表
		order.setOrder_price(order_price);
		//将订单商品总数写入订单表
		order.setTotal(total);
		
		//循环接收没有抛出异常说明库存充足，将订单信息写入数据库
		int createOrder = orderDao.createOrder1(order);
		if(createOrder == 0) {
			//写入订单失败，抛出异常
			throw new RuntimeException("提交订单失败");
		}
		//遍历订单商品表，将订单商品详情写入数据库
		for(OrderGoods orderGoods:goodsList) {
			int insertOrderGoodsByOrder = orderDao.insertOrderGoodsByOrder1(orderGoods);
			if(insertOrderGoodsByOrder == 0) {
				throw new RuntimeException("提交订单失败");
			}
		}
		//提交订单成功返回订单编号
		return order_id;
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public List<Order> getAllOrder(int user_id) {
		// TODO Auto-generated method stub
		// 获取当前用户所有订单编号
		/*List<Long> allOrder_id = orderDao.getAllOrder_id(user_id);
		List<Order> orders = new ArrayList<Order>();
		for(long order_id:allOrder_id) {
			Order order = orderDao.getOrderByOrder_id(order_id);
			orders.add(order);
		}*/
		List<Order> orders = orderDao.getAllOrder(user_id);
		return orders;
	}
	
	
	
	

}

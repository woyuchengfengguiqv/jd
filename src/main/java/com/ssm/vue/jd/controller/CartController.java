package com.ssm.vue.jd.controller;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ssm.vue.jd.pojo.Cart;
import com.ssm.vue.jd.pojo.Commodity;
import com.ssm.vue.jd.pojo.Msg;
import com.ssm.vue.jd.pojo.Order;
import com.ssm.vue.jd.pojo.OrderGoods;
import com.ssm.vue.jd.pojo.User;
import com.ssm.vue.jd.service.CartService;
import com.ssm.vue.jd.service.CommodityService;
import com.ssm.vue.jd.service.OrderService;
import com.ssm.vue.jd.util.JedisUtil;
import com.ssm.vue.jd.util.SpringUtil;

import redis.clients.jedis.Jedis;

@Controller
public class CartController {

	@Autowired
	CartService cartService;
	
	@Autowired
	OrderService orderService;
	
	@Autowired
	CommodityService commodityService; 

	// 向购物车添加商品的方法
	@RequestMapping("addProductToCart")
	@ResponseBody
	public Msg addProductToCart(@RequestBody Cart cart, HttpServletRequest req, HttpServletResponse resp) {
		// 获取用户编号
		HttpSession session = req.getSession();
		LinkedHashMap<String, Object> user = (LinkedHashMap<String, Object>) session.getAttribute("user");
		int user_id = (int) user.get("user_id");
		// 将用户编号写入购物车类
		cart.setUser_id(user_id);
		// 向购物成添加商品
		int addProductToCart = cartService.addProductToCart(cart);
		Msg msg = SpringUtil.getBean(Msg.class);
		if (addProductToCart != 0) {
			// 添加商品成功
			msg.setCode(1);
			msg.setMsg("向购物车添加商品成功");
		} else {
			// 添加商品成功
			msg.setCode(0);
			msg.setMsg("向购物车添加商品失败");
		}
		return msg;
	}

	// 查询当前用户的购物车商品信息
	@RequestMapping("queryCaryByUser")
	@ResponseBody
	public Msg queryCaryByUser(HttpServletRequest req, HttpServletResponse resp) {
		// 获取用户编号
		HttpSession session = req.getSession();
		LinkedHashMap<String, Object> map = (LinkedHashMap<String, Object>) session.getAttribute("user");
		int user_id = (int) map.get("user_id");
		User user = SpringUtil.getBean(User.class);
		user.setUser_id(user_id);
		//查询此用户在购物车的所有商品信息
		List<Cart> queryCaryByUser = cartService.queryCaryByUser(user);
		Msg msg = SpringUtil.getBean(Msg.class);
		if(queryCaryByUser != null) {
			//查询成功
			msg.setCode(1);
			msg.setData(queryCaryByUser);
			msg.setMsg("查询购物车成功");
		}else {
			//查询失败
			msg.setCode(0);
			msg.setData(queryCaryByUser);
			msg.setMsg("查询购物车失败");
		}
		return msg;
		
	}
	
	//生成购物车商品订单
	@RequestMapping("shoppingCartCreateOrder")
	@ResponseBody
	public Msg shoppingCartCreateOrder(@RequestBody Order order,HttpServletRequest req, HttpServletResponse resp) {
		
		//此前报错会在service层中抛出异常
		//获取当前用户的用户编号
		HttpSession session = req.getSession();
		Object login_user = session.getAttribute("user");
		LinkedHashMap<String, Object> user = (LinkedHashMap<String, Object>)login_user;
		int user_id = (int)user.get("user_id");
		order.setUser_id(user_id);
		//生成未完成订单
		long order_id = orderService.createShoppingCartOrder(order);
		Msg msg = SpringUtil.getBean(Msg.class);
		
		msg.setCode(1);
		msg.setData(order_id+"");
		msg.setMsg("查询购物车成功");
		
		return msg;
	}
	
	//Redis实现数据库
	//向购物车添加商品
	@RequestMapping("redisShoppingCartAdd")
	@ResponseBody
	public Msg redisShoppingCartAdd(@RequestBody Cart cart,HttpServletRequest req, HttpServletResponse resp) {
		//类型hash
		//key,用户编号，field,商品编号   value:商品数量
		//获取当前登录用户编号
		HttpSession session = req.getSession();
		Object attribute = session.getAttribute("user");
		LinkedHashMap<String, Object> user = (LinkedHashMap<String, Object>) attribute;
		int user_id = (Integer)user.get("user_id");
		Map<String,String> product = new HashMap<String,String>();
		product.put(cart.getCommodity_id()+"", cart.getCount()+"");

		Msg msg = SpringUtil.getBean(Msg.class);
		
		//代码冗余，稍后修改
		if(!JedisUtil.exists(user_id+"")) {
			//不存在该用户的购物车创建
			//成功创建返回1，失败返回0
			Long hsetnx = JedisUtil.hsetnx(user_id+"", cart.getCommodity_id()+"", cart.getCount()+"");
			if(hsetnx == 1) {
				//创建购物车添加商品成功
				msg.setCode(1);
				msg.setMsg("加入购物车成功");
			}else {
				//创建购物车添加商品失败
				msg.setCode(0);
				msg.setMsg("加入购物车事变");
			}
			return msg;
		}
		//购物车已存在，判断该商品是否存在
		if(!JedisUtil.hexists(user_id+"", cart.getCommodity_id()+"")) {
			//购物车不存在该商品，添加
			Long hsetnx = JedisUtil.hsetnx(user_id+"", cart.getCommodity_id()+"", cart.getCount()+"");
			if(hsetnx == 1) {
				//创建购物车添加商品成功
				msg.setCode(1);
				msg.setMsg("加入购物车成功");
			}else {
				//创建购物车添加商品失败
				msg.setCode(0);
				msg.setMsg("加入购物车事变");
			}
			return msg;
		}
		//该商品已存在，直接增加数量(返回value的值（商品数量）)
		long hincrBy = JedisUtil.hincrBy(user_id+"", cart.getCommodity_id()+"", cart.getCount());
		//创建购物车添加商品成功
		msg.setCode(1);
		msg.setMsg("加入购物车成功");
		return msg;
	}
	
	
	//redis购物车，查询所有购物车的商品信息
	@RequestMapping("redisQuertShoppingCart")
	@ResponseBody
	public Msg redisQuertShoppingCart(HttpServletRequest req, HttpServletResponse resp) {
		//这是一行没什么卵用的代码
		//获取当前登录用户信息
		HttpSession session = req.getSession();
		Object attribute = session.getAttribute("user");
		LinkedHashMap<String, Object> user = (LinkedHashMap<String, Object>) attribute;
		int user_id = (Integer)user.get("user_id");
		//调用查询当前用户购物车商品信息的方法
		List<Commodity> productMsg = cartService.redisQuertShoppingCart(user_id);
		Msg msg = SpringUtil.getBean(Msg.class);
		msg.setCode(1);
		msg.setData(productMsg);
		msg.setMsg("查询商品成功");
		
		//向前段返回信息
		return msg;
	}

}

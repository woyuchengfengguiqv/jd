package com.ssm.vue.jd.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;
import com.ssm.vue.jd.pojo.Address;
import com.ssm.vue.jd.pojo.Msg;
import com.ssm.vue.jd.pojo.Order;
import com.ssm.vue.jd.pojo.User;
import com.ssm.vue.jd.service.OrderService;
import com.ssm.vue.jd.util.HttpClientUtils;
import com.ssm.vue.jd.util.SpringUtil;

@Controller
public class OrderController {

	@Autowired
	OrderService orderService;

	// 获取地址信息
	@RequestMapping("getAddress")
	@ResponseBody
	public Msg getAddress(HttpServletRequest req, HttpServletResponse resp) {
		// 获取user对象
		HttpSession session = req.getSession();
		Object obj = session.getAttribute("user");
		LinkedHashMap<String, Object> map = (LinkedHashMap<String, Object>) obj;
		List<Address> address = orderService.getAddress(map);
		Msg msg = SpringUtil.getBean(Msg.class);
		if (address != null && address.size() != 0) {
			// 获取地址成功
			msg.setCode(1);
			msg.setData(address.get(0));
			msg.setMsg("获取地址信息成功");
		} else {
			// 获取地址成功
			msg.setCode(0);
			msg.setData(address);
			msg.setMsg("获取地址信息失败");
		}
		return msg;
	}

	// 生成订单(未完成状态的订单)
	@RequestMapping("createOrder")
	@ResponseBody
	public Msg createOrder(@RequestBody Object object, HttpServletRequest req, HttpServletResponse resp) {
		LinkedHashMap<String, Object> map = (LinkedHashMap<String, Object>) object;
		// 获取当前登录的用户编号
		HttpSession session = req.getSession();
		Object login_user = session.getAttribute("user");
		LinkedHashMap<String, Object> user = (LinkedHashMap<String, Object>) login_user;
		map.put("user_id", user.get("user_id"));
		long order_id = orderService.createOrder(map);
		Msg msg = SpringUtil.getBean(Msg.class);
		if (order_id != 0) {
			// 生成订单成功，将订单编号返回给浏览器
			msg.setCode(1);

			msg.setData(order_id + "");
			msg.setMsg("成功生成订单");
		} else {
			msg.setCode(0);
			msg.setData(order_id);
			msg.setMsg("库存不足，提交订单失败");
		}
		return msg;
	}

	// 获取订单信息
	@RequestMapping("getOrderById")
	@ResponseBody
	public Msg getOrderById(@RequestBody Order order, HttpServletRequest req, HttpServletResponse resp) {
		Order orderById = orderService.getOrderById(order);
		Msg msg = SpringUtil.getBean(Msg.class);
		if (orderById != null) {
			// 查询订单信息成功
			msg.setCode(1);
			msg.setData(orderById);
			msg.setMsg("查询订单信息成功");
		} else {
			// 查询订单信息成功
			msg.setCode(0);
			msg.setData(orderById);
			msg.setMsg("查询订单信息失败");
		}
		return msg;
	}

	// 完成订单的方法
	@RequestMapping("completeOrder")
	@ResponseBody
	public Msg completeOrder(String order_id, HttpServletRequest req, HttpServletResponse resp) {
		Order order = new Order();
		order.setOrder_id(Long.parseLong(order_id));
		Gson gson = new Gson();
		//完成订单
		int result = orderService.completeOrder(order);
		
		// String post = HttpClientUtils.post("http://47.94.220.67:10001/aliPcPay",
		// map);
		// int completeOrder = orderService.completeOrder(order);
		 Msg msg = SpringUtil.getBean(Msg.class);
		if(result != 0) {
			 //成功完成订单
			msg.setCode(1);
			msg.setMsg("成功完成订单");
		 }else {
			 //完成订单失败
			 msg.setCode(0);
			 msg.setMsg("出错了，完成订单失败");
		}
		//
		System.out.println("成功完成订单");
		 return msg;
	}
	
	//获取所有订单信息
	@RequestMapping("getAllOrder")
	@ResponseBody
	public Msg getAllOrder(HttpServletRequest req, HttpServletResponse resp) {
		//获取用户编号
		HttpSession session = req.getSession();
		Object login_user = session.getAttribute("user");
		LinkedHashMap<String, Object> user = (LinkedHashMap<String, Object>) login_user;
		int user_id = (Integer)user.get("user_id");
		
		List<Order> allOrder = orderService.getAllOrder(user_id);
		 Msg msg = SpringUtil.getBean(Msg.class);
		if(allOrder != null) {
			 //查询所有订单成功
			msg.setCode(1);
			msg.setData(allOrder);
			msg.setMsg("查询所有订单成功");
		 }else {
			 //完成订单失败
			msg.setCode(0);
			msg.setData(allOrder);
			msg.setMsg("查询所有订单失败");
		}
		//
		 return msg;
	}
	
}
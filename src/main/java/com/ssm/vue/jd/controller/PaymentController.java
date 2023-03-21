package com.ssm.vue.jd.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;
import com.ssm.vue.jd.pojo.Order;
import com.ssm.vue.jd.service.OrderService;
import com.ssm.vue.jd.util.HttpClientUtils;

@Controller
public class PaymentController {
	@Autowired
	OrderService orderService;
	@Autowired
	Order orderDao;

	@RequestMapping("alipayPayment")
	@ResponseBody 
	public String alipayPayment(@RequestBody Order order) {
		Gson gson = new Gson();
		Map<String, Object> params = new HashMap<String, Object>();
		// 查询订单详情
		Order order_msg = orderService.getOrderById(order);
		params.put("out_trade_no", order.getOrder_id());
		params.put("total_amount", order_msg.getOrder_price());
		params.put("subject", "order payment");
		//前端
		params.put("return_url", "http://18363268605.gnway.cc:80/index");
		params.put("notify_url", "http://nbkelasi.e5.luyouxia.net:45918/jd/completeOrder"+"?"+"order_id="+order.getOrder_id());
		String params_json = gson.toJson(params);
		String result = HttpClientUtils.ajaxPostJson("http://47.94.220.67:10001/aliPcPay", params_json);
		return result;
	}

}

 package com.ssm.vue.jd.pojo;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

//订单类
@Component
@Scope("prototype")
public class Order {
	private long order_id;//订单编号
	private int user_id;//用户编号
	private Date build_date;//生成订单时间
	private BigDecimal order_price;//订单价格
	private int order_state;//订单状态，0未完成，1已支付
	private int total;//商品总数
	private Date payment_time;//付款时间
	private Date receiving_time;//到货时间
	private String recipient_name;//收件人姓名
	private String recipient_address;//收件人地址
	private String recipient_phone;//收件人电话
	private List<OrderGoods> commodityList;//订单商品详情表
	
	public Order() {
		
	}
	
	public Order(long order_id, int user_id, Date build_date, BigDecimal order_price, int order_state, int total,
			Date payment_time, Date receiving_time, String recipient_name, String recipient_address,
			String recipient_phone, List<OrderGoods> commodityList) {
		super();
		this.order_id = order_id;
		this.user_id = user_id;
		this.build_date = build_date;
		this.order_price = order_price;
		this.order_state = order_state;
		this.total = total;
		this.payment_time = payment_time;
		this.receiving_time = receiving_time;
		this.recipient_name = recipient_name;
		this.recipient_address = recipient_address;
		this.recipient_phone = recipient_phone;
		this.commodityList = commodityList;
	}

	public long getOrder_id() {
		return order_id;
	}

	public void setOrder_id(long order_id) {
		this.order_id = order_id;
	}

	public int getUser_id() {
		return user_id;
	}

	public void setUser_id(int user_id) {
		this.user_id = user_id;
	}

	public Date getBuild_date() {
		return build_date;
	}

	public void setBuild_date(Date build_date) {
		this.build_date = build_date;
	}

	public BigDecimal getOrder_price() {
		return order_price;
	}

	public void setOrder_price(BigDecimal order_price) {
		this.order_price = order_price;
	}

	public int getOrder_state() {
		return order_state;
	}

	public void setOrder_state(int order_state) {
		this.order_state = order_state;
	}

	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}

	public Date getPayment_time() {
		return payment_time;
	}

	public void setPayment_time(Date payment_time) {
		this.payment_time = payment_time;
	}

	public Date getReceiving_time() {
		return receiving_time;
	}

	public void setReceiving_time(Date receiving_time) {
		this.receiving_time = receiving_time;
	}

	public String getRecipient_name() {
		return recipient_name;
	}

	public void setRecipient_name(String recipient_name) {
		this.recipient_name = recipient_name;
	}

	public String getRecipient_address() {
		return recipient_address;
	}

	public void setRecipient_address(String recipient_address) {
		this.recipient_address = recipient_address;
	}

	public String getRecipient_phone() {
		return recipient_phone;
	}

	public void setRecipient_phone(String recipient_phone) {
		this.recipient_phone = recipient_phone;
	}

	public List<OrderGoods> getCommodityList() {
		return commodityList;
	}

	public void setCommodityList(List<OrderGoods> commodityList) {
		this.commodityList = commodityList;
	}
	
	
	
	
	
	
	
	
	
	
	
	
}

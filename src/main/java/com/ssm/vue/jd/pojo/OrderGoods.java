package com.ssm.vue.jd.pojo;

import java.math.BigDecimal;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

//订单商品表
@Component
@Scope("prototype")
public class OrderGoods {
	private int commodity_id;//商品编号
	private int order_goods_id;//订单商品表主键
	private long order_id;//订单编号
	private String order_commodity_name;//订单商品名称
	private int order_amount;//订单商品数量
	private BigDecimal order_commodity_price;//商品单价
	private BigDecimal total_price;//商品总价
	private String img_url;//商品图片地址
	
	public OrderGoods() {
		
	}
	
	public OrderGoods(int commodity_id, int order_goods_id, long order_id, String order_commodity_name,
			int order_amount, BigDecimal order_commodity_price, BigDecimal total_price, String img_url) {
		super();
		this.commodity_id = commodity_id;
		this.order_goods_id = order_goods_id;
		this.order_id = order_id;
		this.order_commodity_name = order_commodity_name;
		this.order_amount = order_amount;
		this.order_commodity_price = order_commodity_price;
		this.total_price = total_price;
		this.img_url = img_url;
	}

	public int getCommodity_id() {
		return commodity_id;
	}

	public void setCommodity_id(int commodity_id) {
		this.commodity_id = commodity_id;
	}

	public int getOrder_goods_id() {
		return order_goods_id;
	}

	public void setOrder_goods_id(int order_goods_id) {
		this.order_goods_id = order_goods_id;
	}

	public long getOrder_id() {
		return order_id;
	}

	public void setOrder_id(long order_id) {
		this.order_id = order_id;
	}

	public String getOrder_commodity_name() {
		return order_commodity_name;
	}

	public void setOrder_commodity_name(String order_commodity_name) {
		this.order_commodity_name = order_commodity_name;
	}

	public int getOrder_amount() {
		return order_amount;
	}

	public void setOrder_amount(int order_amount) {
		this.order_amount = order_amount;
	}

	public BigDecimal getOrder_commodity_price() {
		return order_commodity_price;
	}

	public void setOrder_commodity_price(BigDecimal order_commodity_price) {
		this.order_commodity_price = order_commodity_price;
	}

	public BigDecimal getTotal_price() {
		return total_price;
	}

	public void setTotal_price(BigDecimal total_price) {
		this.total_price = total_price;
	}

	public String getImg_url() {
		return img_url;
	}

	public void setImg_url(String img_url) {
		this.img_url = img_url;
	}
	
	
	
	
	
	
	
	

}

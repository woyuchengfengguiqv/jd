package com.ssm.vue.jd.pojo;

import java.math.BigDecimal;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

//商品表
@Component
@Scope("prototype")
public class Commodity {
	private int commodity_id;//商品编号
	private String commodity_name;//商品名称
	private String commodity_info;//商品简介
	private BigDecimal commodity_price;//商品单价
	private String brand;//品牌
	private String place;//产地
	private int stock;//库存
	private String img_url;//图片链接
	//
	private int cartShoppingNum;//该商品在购物车的数量
	
	public Commodity() {
		
	}
	
	public Commodity(int commodity_id, String commodity_name, String commodity_info, BigDecimal commodity_price,
			String brand, String place, int stock, String img_url, int cartShoppingNum) {
		super();
		this.commodity_id = commodity_id;
		this.commodity_name = commodity_name;
		this.commodity_info = commodity_info;
		this.commodity_price = commodity_price;
		this.brand = brand;
		this.place = place;
		this.stock = stock;
		this.img_url = img_url;
		this.cartShoppingNum = cartShoppingNum;
	}

	public int getCommodity_id() {
		return commodity_id;
	}

	public void setCommodity_id(int commodity_id) {
		this.commodity_id = commodity_id;
	}

	public String getCommodity_name() {
		return commodity_name;
	}

	public void setCommodity_name(String commodity_name) {
		this.commodity_name = commodity_name;
	}

	public String getCommodity_info() {
		return commodity_info;
	}

	public void setCommodity_info(String commodity_info) {
		this.commodity_info = commodity_info;
	}

	public BigDecimal getCommodity_price() {
		return commodity_price;
	}

	public void setCommodity_price(BigDecimal commodity_price) {
		this.commodity_price = commodity_price;
	}

	public String getBrand() {
		return brand;
	}

	public void setBrand(String brand) {
		this.brand = brand;
	}

	public String getPlace() {
		return place;
	}

	public void setPlace(String place) {
		this.place = place;
	}

	public int getStock() {
		return stock;
	}

	public void setStock(int stock) {
		this.stock = stock;
	}

	public String getImg_url() {
		return img_url;
	}

	public void setImg_url(String img_url) {
		this.img_url = img_url;
	}

	public int getCartShoppingNum() {
		return cartShoppingNum;
	}

	public void setCartShoppingNum(int cartShoppingNum) {
		this.cartShoppingNum = cartShoppingNum;
	}
	
	
	
	
	
}

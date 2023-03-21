package com.ssm.vue.jd.service;

import java.util.List;
import java.util.Map;

import com.ssm.vue.jd.pojo.Commodity;
import com.ssm.vue.jd.pojo.Order;

public interface CommodityService {
	
	//查询所有商品的方法
	List<Commodity> queryAllCommodity(Map<String,Object> map);
	
	//查询商品表数据总数
	int getCount();
	
	//根据商品编号查询商品
	Commodity queryCommodityById(Commodity commodity_id);
	
	
}

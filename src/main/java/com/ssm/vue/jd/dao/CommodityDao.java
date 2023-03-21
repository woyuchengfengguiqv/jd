package com.ssm.vue.jd.dao;

import java.util.List;
import java.util.Map;

import com.ssm.vue.jd.pojo.Commodity;
import com.ssm.vue.jd.pojo.Order;

public interface CommodityDao {
	
	//查询所有商品的方法
	List<Commodity> queryAllCommodity(Map<String,Object> map);
	
	//查询商品表数据总数
	int getCount();
	
	//根据商品id查询商品
	Commodity queryCommodityById(Commodity commodity_id);
	
	//查询商品库存的方法
	int getStock(Map<String,Object> map);
	
	//减库存的的方法
	int subtractStock(Map<String,Object> map);
	//减库存的方法
	int subtractStock1(int stock);
	
	
	//根据商品编号查询商品信息
	List<Commodity> getCommodityById(List list);
	
	//根据商品编号查询商品信息
	Commodity getOnlyCommodityById(int commodity_id);
}

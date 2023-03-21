package com.ssm.vue.jd.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssm.vue.jd.dao.CommodityDao;
import com.ssm.vue.jd.dao.LoginDao;
import com.ssm.vue.jd.pojo.Commodity;
import com.ssm.vue.jd.service.CommodityService;

@Service
public  class CommodityServiceImpl implements CommodityService {
	
	
	@Autowired
	CommodityDao commodityDao;
	
	//查询商品列表
	@Override
	public List<Commodity> queryAllCommodity(Map<String, Object> map) {
		
		return commodityDao.queryAllCommodity(map);
	}

	//查询商品数量
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return commodityDao.getCount();
	}

	//根据商品id查询商品
	@Override
	public Commodity queryCommodityById(Commodity commodity_id) {
		// TODO Auto-generated method stub
		return commodityDao.queryCommodityById(commodity_id);
	}

}

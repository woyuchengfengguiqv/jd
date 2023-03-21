package com.ssm.vue.jd.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ssm.vue.jd.pojo.Commodity;
import com.ssm.vue.jd.pojo.Msg;
import com.ssm.vue.jd.pojo.PageBean;
import com.ssm.vue.jd.service.CommodityService;
import com.ssm.vue.jd.util.SpringUtil;


@Controller
public class CommodityController {
	
	@Autowired
	CommodityService commodityService;
	
	//查询全部商品(无需设置拦截器，任何人都可以查看)
	@RequestMapping("queryCommodityController")
	@ResponseBody
	public Msg QueryCommodityController(@RequestBody PageBean page, HttpServletRequest req,HttpServletResponse resp) {
		String requestURI = req.getRequestURI();
		System.out.println(""+requestURI);
		if(page == null) {
			page = SpringUtil.getBean(PageBean.class);
			page.setCurrentPage(1);
			page.setPageSize(1);
		}
		if(page.getCurrentPage() <= 0) {
			page.setCurrentPage(1);
		}
		if(page.getPageSize() <= 0) {
			page.setPageSize(5);
		}
		//数据总数
		int count = commodityService.getCount();
		page.setCount(count);
		//要显示的数据
		
		int firstIndex = (page.getCurrentPage() - 1) * page.getPageSize();
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("firstIndex", firstIndex);
		map.put("pageSize", page.getPageSize());
		List<Commodity> commodityList = commodityService.queryAllCommodity(map);
		page.setData(commodityList);
		//总页数
		int totalPage = (page.getCount() + page.getPageSize() - 1) / page.getPageSize();
		page.setTotalPage(totalPage);
		
		int currentPage = page.getCurrentPage() > page.getTotalPage()  ? page.getTotalPage():page.getCurrentPage();
		
		//上一页
		int prePage = page.getCurrentPage() <= 1 ? 1:page.getCurrentPage()-1;
		page.setPrePage(prePage);
		//下一页
		int nextPage = page.getCurrentPage() >= page.getTotalPage() ? page.getTotalPage() : page.getCurrentPage()+1;
		page.setNextPage(nextPage);
		
		Msg msg = SpringUtil.getBean(Msg.class);
		if(commodityList != null & commodityList.size() != 0) {
			//查询成功
			msg.setCode(1);
			msg.setMsg("employee query success");
			msg.setData(page);
		}else {
			//查询失败
			msg.setCode(0);
			msg.setMsg("employee query failure");
		}
		
		return msg;
	}
	
	//根据商品编号查询商品
	@RequestMapping("queryCommodityById")
	@ResponseBody
	public Msg queryCommodityById(@RequestBody Commodity commodity, HttpServletRequest req,HttpServletResponse resp) {
		Commodity queryCommodityById = commodityService.queryCommodityById(commodity);
		Msg msg = SpringUtil.getBean(Msg.class);
		if(queryCommodityById != null) {
			//查询成功
			msg.setCode(1);
			msg.setMsg("product query succeeded");
			msg.setData(queryCommodityById);
		}else {
			//查询商品失败
			msg.setCode(0);
			msg.setMsg("failed to query goods");
			msg.setData(queryCommodityById);
		}
		return msg;
	}

}

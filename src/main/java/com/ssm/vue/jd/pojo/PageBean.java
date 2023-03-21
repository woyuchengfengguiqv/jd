package com.ssm.vue.jd.pojo;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("prototype")
public class PageBean {
	//数据库查询
	private int count;//数据总数
	private Object data;//要显示的数据
	//前端传入
	private int currentPage;//当前页
	private int pageSize;//每页显示条数
	private int prePage;//上一页
	private int nextPage;//下一页
	//计算得出
	private int totalPage;//总页数
	
	
	
	
	public PageBean(int count, Object data, int currentPage, int prePage, int nextPage, int totalPage, int pageSize) {
		super();
		this.count = count;
		this.data = data;
		this.currentPage = currentPage;
		this.prePage = prePage;
		this.nextPage = nextPage;
		this.totalPage = totalPage;
		this.pageSize = pageSize;
	}
	
	public PageBean() {
		
	}
	
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
	public Object getData() {
		return data;
	}
	public void setData(Object data) {
		this.data = data;
	}
	public int getCurrentPage() {
		return currentPage;
	}
	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}
	public int getPrePage() {
		return prePage;
	}
	public void setPrePage(int prePage) {
		this.prePage = prePage;
	}
	public int getNextPage() {
		return nextPage;
	}
	public void setNextPage(int nextPage) {
		this.nextPage = nextPage;
	}
	public int getTotalPage() {
		return totalPage;
	}
	public void setTotalPage(int totalPage) {
		this.totalPage = totalPage;
	}
	public int getPageSize() {
		return pageSize;
	}
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
	
	
	
}

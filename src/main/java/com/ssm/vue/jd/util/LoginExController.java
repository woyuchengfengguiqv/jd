package com.ssm.vue.jd.util;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.ssm.vue.jd.pojo.Msg;

import io.jsonwebtoken.ExpiredJwtException;

//统一异常处理
@ControllerAdvice
public class LoginExController {
	
	@ExceptionHandler(ExpiredJwtException.class)
	public Msg loginExpiration(HttpServletRequest req,HttpServletResponse resp) {
		Msg msg = SpringUtil.getBean(Msg.class);
		msg.setCode(10);//登录过期
		msg.setMsg("您的登录已过期，请重新登录");
		return msg;
	}
}

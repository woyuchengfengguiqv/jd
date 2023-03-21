package com.ssm.vue.jd.util;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.ssm.vue.jd.pojo.User;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;

//登录拦截器
public class LoginInterceptor implements HandlerInterceptor {
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		String signature = "nbvvDFSEadf123..";
		//获取token
		String token = request.getHeader("Authorization");
		String url = request.getRequestURI();
		System.out.println(url);
		System.out.println("interception----:" + token);
		if(token == null) {
			return false;
		}
		//使用签名验证token
		//从前端返回的token是可能带有""的
		String real_token = token.substring(1, token.length()-1);
		System.out.println("real_token----:"+real_token);
		JwtParser jwtParse = Jwts.parser();
		//解析错误直接抛出异常
		Jws<Claims> parseClaimsJws = jwtParse.setSigningKey(signature).parseClaimsJws(real_token);
		//取出token中的数据
		Claims claims = parseClaimsJws.getBody();
		Object user = claims.get("login_user");
		HttpSession session = request.getSession();
		session.setAttribute("user", user);

		return true;
		
		
		
		
		
		
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		// TODO Auto-generated method stub
		HandlerInterceptor.super.postHandle(request, response, handler, modelAndView);
	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		// TODO Auto-generated method stub
		HandlerInterceptor.super.afterCompletion(request, response, handler, ex);
	}


}

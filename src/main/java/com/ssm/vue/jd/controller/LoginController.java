package com.ssm.vue.jd.controller;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ssm.vue.jd.pojo.Msg;
import com.ssm.vue.jd.pojo.User;
import com.ssm.vue.jd.service.LoginService;
import com.ssm.vue.jd.util.SpringUtil;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Controller
public class LoginController {

	@Autowired
	LoginService loginService;
	
	@RequestMapping("login")
	@ResponseBody
	public Msg login(@RequestBody User user, HttpServletRequest req,HttpServletResponse resp) {
		String signature = "nbvvDFSEadf123..";
		User login_user = loginService.login(user);
		Msg msg = SpringUtil.getBean(Msg.class);
		if(login_user != null) {
			//用户登录成功
			//生成token
			String token = Jwts.builder()
				.setHeaderParam("typ","JWT")
				.setHeaderParam("alg", "HS256")
				.claim("login_user", login_user)
				//.setId(UUID.randomUUID() +"")
				.setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 24))
				.signWith(SignatureAlgorithm.HS256, signature)
				.compact();
			
			msg.setCode(1);
			msg.setMsg("login successfully");
			msg.setData(token);
			System.out.println("login----:" + token);
		}else {
			//用户登录失败
			msg.setCode(0);
			msg.setMsg("Login failed because the user name or password is incorrect");
			
		}
		
		return msg;
	}
	
	
}

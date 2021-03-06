package com.atguigu.shiro.handlers;

import javax.servlet.http.HttpSession;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.atguigu.shiro.service.ShiroService;

@Controller
@RequestMapping("/shiro")
public class ShiroHandler {
	
	@Autowired
	private ShiroService shiroService;
	
	@RequestMapping("/testAnnotation")
	public String testShiroAnnotation(HttpSession session) {
		session.setAttribute("Tom", "value1234");
		shiroService.getTime1();
		return "redirect:/list.jsp";
	}

	@RequestMapping("/login")
	public String login(@RequestParam("username") String username,
			@RequestParam("password") String password) {
		
		Subject currentUser = SecurityUtils.getSubject();
		 if (!currentUser.isAuthenticated()) {
	        	// 把用户名和密码封装为 UsernamePasswordToken 对象
	            UsernamePasswordToken token = new UsernamePasswordToken(username, password);
	            // rememberme
	            token.setRememberMe(true);
	            try {
	            	System.out.println("1."+token.hashCode());
	            	// 执行登录. 
	                currentUser.login(token);
	            }
	            // ... catch more exceptions here (maybe custom ones specific to your application?
	            // 所有认证时异常的父类. 
	            catch (AuthenticationException ae) {
	                System.out.println("登录失败："+ae.getMessage());
	            }
	        }

		return "redirect:/list.jsp ";
	}
}

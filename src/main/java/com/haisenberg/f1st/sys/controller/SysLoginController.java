package com.haisenberg.f1st.sys.controller;

import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.ExcessiveAttemptsException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import io.swagger.annotations.Api;

/**
 * @ClassName: LoginController.java
 * @Package: com.haisenberg.f1st.sys.controller
 * @Description: 
 * @author 张翔
 * @date 2018年5月9日 下午4:16:48
 * @Version: 
 */
@RestController
@Api(value="系统登录相关api",tags={"系统登录操作接口"})
public class SysLoginController {
	
	@RequestMapping("/toLogin")
	public ModelAndView login(HttpServletRequest request,Model model,String userName, String password, boolean rememberMe) throws Exception{
		UsernamePasswordToken token = new UsernamePasswordToken(userName, password.toCharArray());
		token.setRememberMe(rememberMe);
		Subject currentUser = SecurityUtils.getSubject();
		String error = "";
		try {
			currentUser.login(token);
			return new ModelAndView("menu");
		} catch (UnknownAccountException ex) {// 用户名没有找到
			error = "您输入的用户名不存在！";
		} catch (IncorrectCredentialsException ex) {// 用户名密码不匹配
			error = "用户名密码不匹配 ！";
		} catch (ExcessiveAttemptsException e) {
			error = "密码错误次数已超五次，账号锁定1小时！";
		} catch (AuthenticationException ex) {// 其他的登录错误
			ex.printStackTrace();
			error = "其他的登录错误  ！";
		}
		model.addAttribute("message", error);
	    // 此方法不处理登录成功,由shiro进行处理
		return new ModelAndView("login");
	}

	@RequestMapping("/logout")
	public String logout(HttpServletRequest request, Model model) {
		Subject currentUser = SecurityUtils.getSubject();
		currentUser.logout();
		return "login";
	}
}

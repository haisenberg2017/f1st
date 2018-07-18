package com.haisenberg.f1st.sys.controller;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.ExcessiveAttemptsException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.google.code.kaptcha.impl.DefaultKaptcha;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * @ClassName: LoginController.java
 * @Package: com.haisenberg.f1st.sys.controller
 * @Description:
 * @author 张翔
 * @date 2018年5月9日 下午4:16:48
 * @Version:
 */
@RestController
@Api(value = "系统登录相关api", tags = { "系统登录操作接口" })
public class SysLoginController {
	@Autowired
	private DefaultKaptcha captchaProducer;
	
	/**
	 * 登录校验
	 * @param request
	 * @param userName
	 * @param password
	 * @param rememberMe
	 * @param vrifyCode
	 * @return
	 * @throws Exception
	 */
	@ApiOperation(value = "登录校验")
	@RequestMapping(value="/toLogin",method = RequestMethod.POST)
	public ModelAndView login(HttpServletRequest request, String userName, String password,
			boolean rememberMe, String vrifyCode) throws Exception {
		ModelAndView view = new ModelAndView();
		// 验证验证码
		String captchaId = (String) request.getSession().getAttribute("vrifyCode");
		if (captchaId==null||!captchaId.equals(vrifyCode)) {
			view.addObject("message", "错误的验证码");
			view.setViewName("login");
			return view;
		}
		UsernamePasswordToken token = new UsernamePasswordToken(userName, password.toCharArray());
		token.setRememberMe(rememberMe);
		Subject currentUser = SecurityUtils.getSubject();
		String error = "";
		try {
			currentUser.login(token);
			view.setViewName("index");
			return view;
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
		// 此方法不处理登录成功,由shiro进行处理
		view.addObject("message", error);
		view.setViewName("login");
		return view;
	
	}

	/**
	 * 验证码生成器
	 * 
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@ApiOperation(value = "验证码生成器")
	@RequestMapping(value = "/defaultKaptcha")
	public void getKaptchaImage(HttpServletRequest request, HttpServletResponse response) throws Exception {
		byte[] captchaChallengeAsJpeg = null;
		ByteArrayOutputStream jpegOutputStream = new ByteArrayOutputStream();
		try {
			// 生产验证码字符串并保存到session中
			String createText = captchaProducer.createText();
			request.getSession().setAttribute("vrifyCode", createText);
			// 使用生产的验证码字符串返回一个BufferedImage对象并转为byte写入到byte数组中
			BufferedImage challenge = captchaProducer.createImage(createText);
			ImageIO.write(challenge, "jpg", jpegOutputStream);
		} catch (IllegalArgumentException e) {
			response.sendError(HttpServletResponse.SC_NOT_FOUND);
			return;
		}
		// 定义response输出类型为image/jpeg类型，使用response输出流输出图片的byte数组
		captchaChallengeAsJpeg = jpegOutputStream.toByteArray();
		response.setHeader("Cache-Control", "no-store");
		response.setHeader("Pragma", "no-cache");
		response.setDateHeader("Expires", 0);
		response.setContentType("image/jpeg");
		ServletOutputStream responseOutputStream = response.getOutputStream();
		responseOutputStream.write(captchaChallengeAsJpeg);
		responseOutputStream.flush();
		responseOutputStream.close();
	}

	/**
	 * 登出
	 * @param request
	 * @param model
	 * @return
	 */
	@ApiOperation(value = "登出")
	@RequestMapping("/logout")
	public String logout(HttpServletRequest request, Model model) {
		Subject currentUser = SecurityUtils.getSubject();
		currentUser.logout();
		return "login";
	}
	
}

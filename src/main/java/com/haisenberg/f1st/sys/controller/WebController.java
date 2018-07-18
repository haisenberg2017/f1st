package com.haisenberg.f1st.sys.controller;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.baidu.ueditor.ActionEnter;

@RestController
@RequestMapping("/")
public class WebController {
	private final Logger logger = LoggerFactory.getLogger(WebController.class);

	@RequestMapping("/{page}")
	public ModelAndView toPage(@PathVariable(value = "page") String page) {
		ModelAndView model = new ModelAndView(page);
		logger.info("----------------toPage[{}]-------------", page);
		return model;
	}
	
	/**
	 * ueditor上传文件接口
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "/ueditor/config")
	public void config(HttpServletRequest request, HttpServletResponse response) {
		response.setContentType("application/json");
		String rootPath = request.getSession().getServletContext().getRealPath("/");
		try {
			String exec = new ActionEnter(request, rootPath).exec();
			PrintWriter writer = response.getWriter();
			writer.write(exec);
			writer.flush();
			writer.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
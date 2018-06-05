package com.haisenberg.f1st.sys.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

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

}
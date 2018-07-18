package com.haisenberg.f1st.business.blog.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.haisenberg.f1st.business.blog.pojo.BlogArticle;
import com.haisenberg.f1st.business.blog.service.BlogService;
import com.haisenberg.f1st.sys.pojo.SysUser;
import com.haisenberg.f1st.utils.CommonUtils;
import com.haisenberg.f1st.utils.Constants;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping(value = "/blog")
@Api(value="博客相关api",tags={"博客操作接口"})
public class BlogController {
	private final Logger logger = LoggerFactory.getLogger(BlogController.class);
	@Autowired
	private BlogService blogService;
	/**
	 * 博客权限列表
	 * @param webData
	 * @return
	 * @throws Exception
	 */
	@ApiOperation(value = "获取博客列表")
	@RequestMapping(value = "/list", method = RequestMethod.POST)
	public Map<String, Object> findAll(@RequestBody Map<String, Object> webData) throws Exception {
		long sTime = System.currentTimeMillis();
		logger.info("开启查询博客列表的请求，请求参数[{}]", webData.toString());
		Map<String, Object> resultMap = new HashMap<>();
		resultMap.put("flag", Constants.ERROR_RESPONSE);
		resultMap.put("msg", "参数不全");
		//获取登录用户信息
		SysUser loginUser = CommonUtils.getLoginUser();
		Page<BlogArticle> pageList = blogService.findAllByPage(webData,loginUser);
		List<BlogArticle> list = pageList.getContent();
		if (list == null || list.size() < 1) {
			resultMap.put("msg", "查询数据为空");
		}
		resultMap.put("flag", Constants.SUCCESS_RESPONSE);
		resultMap.put("data", list);
		resultMap.put("totalPages", pageList.getTotalPages());
		resultMap.put("totalSize", pageList.getTotalElements());
		long eTime = System.currentTimeMillis();
		logger.info("查询全部用户的请求结束，消耗时间time={}", eTime - sTime);
		return resultMap;
	}
}

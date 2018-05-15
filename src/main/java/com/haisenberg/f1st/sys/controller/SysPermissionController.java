package com.haisenberg.f1st.sys.controller;

import java.util.Date;
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

import com.haisenberg.f1st.sys.pojo.SysPermission;
import com.haisenberg.f1st.sys.service.SysPermissionService;
import com.haisenberg.f1st.utils.Constants;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping(value = "/permission")
@Api(value = "系统权限相关api", tags = { "系统权限操作接口" })
public class SysPermissionController {
	private final Logger logger = LoggerFactory.getLogger(SysPermissionController.class);
	@Autowired
	private SysPermissionService sysPermissionService;

	@ApiOperation(value = "获取权限列表")
	@RequestMapping(value = "/list", method = RequestMethod.POST)
	public Map<String, Object> findAll(@RequestBody Map<String, Object> webData) throws Exception {
		long sTime = System.currentTimeMillis();
		logger.info("开启查询全部用户的请求，请求参数[{}]", webData.toString());
		Map<String, Object> resultMap = new HashMap<>();
		resultMap.put("flag", Constants.ERROR_RESPONSE);
		resultMap.put("msg", "参数不全");
		Page<SysPermission> pageList = sysPermissionService.findAllByPage(webData);
		List<SysPermission> list = pageList.getContent();
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
	
	@ApiOperation(value = "查询权限详情")
	@RequestMapping(value = "/detail", method = RequestMethod.POST)
	public Map<String, Object> detail(@RequestBody Map<String, Object> webData) throws Exception {
		long sTime = System.currentTimeMillis();
		logger.info("开启查询权限详情的请求，请求参数[{}]", webData.toString());
		Map<String, Object> resultMap = new HashMap<>();
		resultMap.put("flag", Constants.ERROR_RESPONSE);
		resultMap.put("msg", "参数不全");
		if(webData.get("permissionId")==null||webData.get("permissionId").toString().length()<1){
			resultMap.put("msg", "permissionId参数为空");
		}
		SysPermission sysPermission = sysPermissionService.findByPermissionId(Long.valueOf(webData.get("permissionId").toString()));
		if (sysPermission == null) {
			resultMap.put("msg", "查询数据为空");
		}
		resultMap.put("flag", Constants.SUCCESS_RESPONSE);
		resultMap.put("data", sysPermission);
		long eTime = System.currentTimeMillis();
		logger.info("查询权限详情的请求结束，消耗时间time={}", eTime - sTime);
		return resultMap;
	}

	@ApiOperation(value = "保存权限信息")
	@RequestMapping(value = "/saveOrUpdate", method = RequestMethod.POST)
	public Map<String, Object> saveOrUpdate(@RequestBody Map<String, Object> webData) throws Exception {
		long sTime = System.currentTimeMillis();
		logger.info("开启用户保存的请求，请求参数[{}]", webData.toString());
		Map<String, Object> resultMap = new HashMap<>();
		resultMap.put("flag", Constants.ERROR_RESPONSE);
		resultMap.put("msg", "参数不全");
		if (webData.get("permissionName") == null || "".equals(webData.get("permissionName").toString())) {
			resultMap.put("msg", "permissionName参数为空");
			return resultMap;
		}
		if (webData.get("permissionType") == null || "".equals(webData.get("permissionType").toString())) {
			resultMap.put("msg", "permissionType参数为空");
			return resultMap;
		}
		if (webData.get("url") == null || "".equals(webData.get("url").toString())) {
			resultMap.put("msg", "url参数为空");
			return resultMap;
		}
		if (webData.get("permission") == null || "".equals(webData.get("permission").toString())) {
			resultMap.put("msg", "permission参数为空");
			return resultMap;
		}
		if (webData.get("parentId") == null || "".equals(webData.get("parentId").toString())) {
			resultMap.put("msg", "parentId参数为空");
			return resultMap;
		}
		if (webData.get("url") == null || "".equals(webData.get("url").toString())) {
			resultMap.put("msg", "url参数为空");
			return resultMap;
		}
		if (webData.get("seq") == null || "".equals(webData.get("seq").toString())) {
			resultMap.put("msg", "seq参数为空");
			return resultMap;
		}
		SysPermission sysPermission = new SysPermission();
		if (webData.get("permissionId") != null || !"".equals(String.format("%s", webData.get("permissionId")))) {
			Long permissionId = Long.valueOf(String.format("%s", webData.get("permissionId")));
			sysPermission = sysPermissionService.findByPermissionId(permissionId);
		} else {
			sysPermission.setCreateTime(new Date());
		}

		sysPermission.setPermissionName(webData.get("permissionName").toString());
		sysPermission.setModifyTime(new Date());
		sysPermission.setPermissionType(webData.get("permissionType").toString());
		sysPermission.setPermission(webData.get("permission").toString());
		sysPermission.setUrl(webData.get("permission").toString());
		sysPermission.setSeq((Long) webData.get("seq"));
		sysPermissionService.save(sysPermission);

		resultMap.put("flag", Constants.SUCCESS_RESPONSE);
		resultMap.put("msg", "用户保存成功！");
		long eTime = System.currentTimeMillis();
		logger.info("保存用户的请求结束，消耗时间time={}", eTime - sTime);
		return resultMap;
	}
}
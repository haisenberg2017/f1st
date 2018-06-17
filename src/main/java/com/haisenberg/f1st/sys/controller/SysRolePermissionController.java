package com.haisenberg.f1st.sys.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONArray;
import com.haisenberg.f1st.sys.pojo.SysRole;
import com.haisenberg.f1st.sys.service.SysPermissionService;
import com.haisenberg.f1st.sys.service.SysRoleService;
import com.haisenberg.f1st.utils.Constants;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping(value = "/role_permission")
@Api(value = "角色权限相关api", tags = { "角色权限操作接口" })
public class SysRolePermissionController {
	private final Logger logger = LoggerFactory.getLogger(SysRolePermissionController.class);
	@Autowired
	private SysPermissionService sysPermissionService;
	@Autowired
	private SysRoleService sysRoleService;

	@ApiOperation(value = "获取角色权限列表")
	@RequestMapping(value = "/list", method = RequestMethod.POST)
	public Map<String, Object> list() throws Exception {
		long sTime = System.currentTimeMillis();
		logger.info("开启查询角色权限的请求" );
		Map<String, Object> resultMap = new HashMap<>();
		resultMap.put("flag", Constants.ERROR_RESPONSE);
		resultMap.put("msg", "参数不全");
		//ztree
		JSONArray jsonArray = sysPermissionService.getPermissionForZtree();
		//角色下拉
		List<SysRole> selectlist=sysRoleService.findAll();
		resultMap.put("data", jsonArray);
		resultMap.put("select", selectlist);
		long eTime = System.currentTimeMillis();
		logger.info("查询角色权限的请求结束，消耗时间time={}", eTime - sTime);
		return resultMap;
	}
	@ApiOperation(value = "获取对应角色权限列表")
	@RequestMapping(value = "/checkPermission", method = RequestMethod.POST)
	public Map<String, Object> checkPermission(@RequestBody Map<String, Object> webData) throws Exception {
		long sTime = System.currentTimeMillis();
		logger.info("开启查询对应角色权限的请求，请求参数[{}]", webData.toString());
		Map<String, Object> resultMap = new HashMap<>();
		resultMap.put("flag", Constants.ERROR_RESPONSE);
		resultMap.put("msg", "参数不全");
		if(webData.get("roleId")==null||webData.get("roleId").toString().length()<1){
			return resultMap;
		}
		List<Long> list=sysPermissionService.checkPermission(webData);
		resultMap.put("data", list);
		long eTime = System.currentTimeMillis();
		logger.info("查询对应角色权限的请求结束，消耗时间time={}", eTime - sTime);
		return resultMap;
	}

	@ApiOperation(value = "保存角色权限信息")
	@RequestMapping(value = "/saveOrUpdate", method = RequestMethod.POST)
	public Map<String, Object> saveOrUpdate(@RequestBody Map<String, Object> webData) throws Exception {
		long sTime = System.currentTimeMillis();
		logger.info("开启用户保存的请求，请求参数[{}]", webData.toString());
		Map<String, Object> resultMap = new HashMap<>();
		resultMap.put("flag", Constants.ERROR_RESPONSE);
		resultMap.put("msg", "参数不全");
		if(webData.get("roleId")==null||webData.get("roleId").toString().length()<1){
			return resultMap;
		}
		List<Long> idList=new ArrayList<>();
		if(webData.get("nodeIds")==null||webData.get("nodeIds").toString().length()<1){
			return resultMap;
		}else{
			try {
				String nodeIds = webData.get("nodeIds").toString();
				String[] ids = nodeIds.split(",");
				for (int i = 0; i < ids.length; i++) {
					String idStr=ids[i].trim();
					idList.add(Long.valueOf(idStr));
				}			
			} catch (Exception e) {
				resultMap.put("msg", "参数格式异常");
				return resultMap;
			}	
		}
		Boolean flag = sysPermissionService.rolePermissionSave(Long.valueOf(webData.get("roleId").toString()),idList);
		if(flag){
			resultMap.put("flag", Constants.SUCCESS_RESPONSE);
			resultMap.put("msg", "角色权限保存成功！");
		}else{
			resultMap.put("msg", "角色权限保存失败！");
		}
		long eTime = System.currentTimeMillis();
		logger.info("保存用户的请求结束，消耗时间time={}", eTime - sTime);
		return resultMap;
	}
}

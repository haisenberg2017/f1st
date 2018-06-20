package com.haisenberg.f1st.sys.controller;

import java.util.ArrayList;
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

import com.haisenberg.f1st.sys.pojo.SysRole;
import com.haisenberg.f1st.sys.pojo.SysUser;
import com.haisenberg.f1st.sys.service.SysRoleService;
import com.haisenberg.f1st.sys.service.SysUserService;
import com.haisenberg.f1st.utils.Constants;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping(value = "/user")
@Api(value="系统用户相关api",tags={"系统用户操作接口"})
public class SysUserController {
	private final Logger logger = LoggerFactory.getLogger(SysUserController.class);
	@Autowired
	private SysUserService sysUserService;
	@Autowired
	private SysRoleService sysRoleService;
	@ApiOperation(value="获取用户列表")
	@RequestMapping(value="/list",method=RequestMethod.POST)
	public Map<String, Object> findAll(@RequestBody Map<String, Object> webData)
			throws Exception {
		long sTime = System.currentTimeMillis();
		logger.info("开启查询全部用户的请求，请求参数[{}]", webData.toString());
		Map<String, Object> resultMap = new HashMap<>();
		resultMap.put("flag", Constants.ERROR_RESPONSE);
		resultMap.put("msg", "参数不全");
		Page<SysUser> pageList = sysUserService.findAllByPage(webData);
		List<SysUser> list = pageList.getContent();
		if (list == null || list.size() < 1) {
			resultMap.put("msg", "查询数据为空");
			return resultMap;
		}
		resultMap.put("flag", Constants.SUCCESS_RESPONSE);
		resultMap.put("data", list);
		resultMap.put("msg", "请求成功	");
		resultMap.put("total", pageList.getTotalElements());
		long eTime = System.currentTimeMillis();
		logger.info("查询全部用户的请求结束，消耗时间time={}", eTime - sTime);
		return resultMap;
	}
	
	
	@ApiOperation(value = "查询用户详情")
	@RequestMapping(value = "/detail", method = RequestMethod.POST)
	public Map<String, Object> detail(@RequestBody Map<String, Object> webData) throws Exception {
		long sTime = System.currentTimeMillis();
		logger.info("开启查询角色详情的请求，请求参数[{}]", webData.toString());
		Map<String, Object> resultMap = new HashMap<>();
		resultMap.put("flag", Constants.ERROR_RESPONSE);
		resultMap.put("msg", "参数不全");
		if(webData.get("userId")==null||webData.get("userId").toString().length()<1){
			resultMap.put("msg", "userId参数为空");
		}
		SysUser sysUser = sysUserService.findByUserId(Long.valueOf(webData.get("userId").toString()));
		List<Long> roleIds=sysRoleService.getRoleIdByUserId(Long.valueOf(webData.get("userId").toString()));
		if (sysUser == null) {
			resultMap.put("msg", "查询数据为空");
		}	
		resultMap.put("flag", Constants.SUCCESS_RESPONSE);
		resultMap.put("data", sysUser);
		resultMap.put("select", roleIds);
		long eTime = System.currentTimeMillis();
		logger.info("查询角色详情的请求结束，消耗时间time={}", eTime - sTime);
		return resultMap;
	}
	
	@ApiOperation(value="保存用户信息")
	@RequestMapping(value="/saveOrUpdate",method=RequestMethod.POST)
	public Map<String, Object> saveOrUpdate(@RequestBody Map<String, Object> webData)
			throws Exception {
		long sTime = System.currentTimeMillis();
		logger.info("开启用户保存的请求，请求参数[{}]", webData.toString());
		Map<String, Object> resultMap = new HashMap<>();
		resultMap.put("flag", Constants.ERROR_RESPONSE);
		resultMap.put("msg", "参数不全");
		if(webData.get("username")==null||"".equals(webData.get("username").toString())){
			resultMap.put("msg", "username参数为空");
			return resultMap;
		}
		if(webData.get("password")==null||"".equals(webData.get("password").toString())){
			resultMap.put("msg", "password参数为空");
			return resultMap;
		}
		if(webData.get("realName")==null||"".equals(webData.get("realName").toString())){
			resultMap.put("msg", "username参数为空");
			return resultMap;
		}
		if(webData.get("state")==null||"".equals(webData.get("state").toString())){
			resultMap.put("msg", "state参数为空");
			return resultMap;
		}
		SysUser sysUser =new SysUser();
		if(webData.get("userId")!=null&&!"".equals(String.format("%s", webData.get("userId")))){
			Long userId = Long.valueOf(String.format("%s", webData.get("userId")));
			sysUser = sysUserService.findByUserId(userId);
		}else{
			sysUser.setUsername(webData.get("username").toString());
			sysUser.setCreateTime(new Date());
		}
		sysUser.setRealName(webData.get("realName").toString());
		sysUser.setModifyTime(new Date());
		sysUser.setPassword(webData.get("password").toString());
		sysUser.setState(Integer.valueOf(webData.get("state").toString()));
		resultMap = sysUserService.save(sysUser);
		if(resultMap.get("flag").equals(Constants.SUCCESS_RESPONSE)&&webData.get("roleIds")!=null&&webData.get("roleIds").toString().length()>0){
			sysUserService.userRoleSave(Long.valueOf(resultMap.get("userId").toString()),webData.get("roleIds").toString());
		}
		long eTime = System.currentTimeMillis();
		logger.info("保存用户的请求结束，消耗时间time={}", eTime - sTime);
		return resultMap;
	}
	@ApiOperation(value="修改用户状态")
	@RequestMapping(value="/changeState",method=RequestMethod.POST)
	public Map<String, Object> changeState(@RequestBody Map<String, Object> webData)
			throws Exception {
		long sTime = System.currentTimeMillis();
		logger.info("修改用户状态，请求参数[{}]", webData.toString());
		Map<String, Object> resultMap = new HashMap<>();
		resultMap.put("flag", Constants.ERROR_RESPONSE);
		resultMap.put("msg", "参数不全");
		if(webData.get("userId")==null||"".equals(webData.get("userId").toString())){
			resultMap.put("msg", "userId参数为空");
			return resultMap;
		}
		if(webData.get("state")==null||"".equals(webData.get("state").toString())){
			resultMap.put("msg", "state参数为空");
			return resultMap;
		}
		Long userId = Long.valueOf(String.format("%s", webData.get("userId")));
		SysUser	sysUser = sysUserService.findByUserId(userId);
		if(sysUser==null){
			resultMap.put("msg", "服务器异常，查询不到对应用户！！！");
			return resultMap;
		}else{
			Integer state = Integer.valueOf(webData.get("state").toString());
			sysUser.setState(state);
			sysUser.setModifyTime(new Date());
			resultMap=sysUserService.update(sysUser);
			if(resultMap!=null&&Constants.SUCCESS_RESPONSE==Integer.valueOf(resultMap.get("flag").toString())){
				String msg="";
				if(state==0){
					msg="启用["+sysUser.getUsername()+"]用户成功！";
				}else{
					msg="禁用["+sysUser.getUsername()+"]用户成功！";
				}
				resultMap.put("msg", msg);
			}	
		}
		
		long eTime = System.currentTimeMillis();
		logger.info("修改用户状态的请求结束，消耗时间time={}", eTime - sTime);
		return resultMap;
	}
	@ApiOperation(value="删除用户信息")
	@RequestMapping(value="/del",method=RequestMethod.POST)
	public Map<String, Object> del(@RequestBody Map<String, Object> webData)
			throws Exception {
		long sTime = System.currentTimeMillis();
		logger.info("开启用户删除的请求，请求参数[{}]", webData.toString());
		Map<String, Object> resultMap = new HashMap<>();
		resultMap.put("flag", Constants.ERROR_RESPONSE);
		resultMap.put("msg", "参数不全");
		if(webData.get("userId")==null||"".equals(webData.get("userId").toString())){
			resultMap.put("msg", "userId参数为空");
			return resultMap;
		}
		String userId = (String)webData.get("userId");
		if(userId.contains(",")){//批量删除
			String[] ids = userId.split(",");
			List<Long> list = new ArrayList<>();
			for (String id : ids) {
				list.add(Long.valueOf(id.trim()));
			}
			int batchDelete = sysUserService.batchDelete(list);
			if(batchDelete>0){
				resultMap.put("msg", "用户批量删除成功！");
				resultMap.put("flag", Constants.SUCCESS_RESPONSE);
			}else{
				resultMap.put("msg", "用户批量删除失败！");	
			}
		}else{	
			sysUserService.delete(Long.valueOf(userId));
			resultMap.put("msg", "用户删除成功！");
			resultMap.put("flag", Constants.SUCCESS_RESPONSE);
		}
		long eTime = System.currentTimeMillis();
		logger.info("删除用户的请求结束，消耗时间time={}", eTime - sTime);
		return resultMap;
	}
	
	
	@ApiOperation(value="获取角色列表")
	@RequestMapping(value="/roleList",method=RequestMethod.POST)
	public Map<String, Object> roleList()
			throws Exception {
		long sTime = System.currentTimeMillis();
		logger.info("开启获取角色列表的请求");
		Map<String, Object> resultMap = new HashMap<>();
		resultMap.put("flag", Constants.SUCCESS_RESPONSE);
		resultMap.put("msg", "获取成功");
		List<SysRole> list = sysRoleService.findAll();
		resultMap.put("data", list);
		long eTime = System.currentTimeMillis();
		logger.info("获取角色列表的请求结束，消耗时间time={}", eTime - sTime);
		return resultMap;
	}
}

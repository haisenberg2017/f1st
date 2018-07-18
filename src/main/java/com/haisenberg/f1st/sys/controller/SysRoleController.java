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
import com.haisenberg.f1st.sys.service.SysRoleService;
import com.haisenberg.f1st.utils.Constants;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping(value = "/role")
@Api(value="系统角色相关api",tags={"系统角色操作接口"})
public class SysRoleController {
	private final Logger logger = LoggerFactory.getLogger(SysRoleController.class);
	@Autowired
	private SysRoleService sysRoleService;
	/**
	 * 获取角色列表
	 * @param webData
	 * @return
	 * @throws Exception
	 */
	@ApiOperation(value="获取角色列表")
	@RequestMapping(value="/list",method=RequestMethod.POST)
	public Map<String, Object> findAll(@RequestBody Map<String, Object> webData)
			throws Exception {
		long sTime = System.currentTimeMillis();
		logger.info("开启查询全部角色的请求，请求参数[{}]", webData.toString());
		Map<String, Object> resultMap = new HashMap<>();
		resultMap.put("flag", Constants.ERROR_RESPONSE);
		resultMap.put("msg", "参数不全");
		Page<SysRole> pageList = sysRoleService.findAllByPage(webData);
		List<SysRole> list = pageList.getContent();
		if (list == null || list.size() < 1) {
			resultMap.put("msg", "查询数据为空");
		}
		resultMap.put("flag", Constants.SUCCESS_RESPONSE);
		resultMap.put("data", list);
		resultMap.put("msg", "请求成功	");
		resultMap.put("total", pageList.getTotalElements());
		long eTime = System.currentTimeMillis();
		logger.info("查询全部角色的请求结束，消耗时间time={}", eTime - sTime);
		return resultMap;
	}
	
	
	/**
	 * 查询角色详情
	 * @param webData
	 * @return
	 * @throws Exception
	 */
	@ApiOperation(value = "查询角色详情")
	@RequestMapping(value = "/detail", method = RequestMethod.POST)
	public Map<String, Object> detail(@RequestBody Map<String, Object> webData) throws Exception {
		long sTime = System.currentTimeMillis();
		logger.info("开启查询角色详情的请求，请求参数[{}]", webData.toString());
		Map<String, Object> resultMap = new HashMap<>();
		resultMap.put("flag", Constants.ERROR_RESPONSE);
		resultMap.put("msg", "参数不全");
		if(webData.get("roleId")==null||webData.get("roleId").toString().length()<1){
			resultMap.put("msg", "roleId参数为空");
		}
		SysRole sysRole = sysRoleService.findByRoleId(Long.valueOf(webData.get("roleId").toString()));
		if (sysRole == null) {
			resultMap.put("msg", "查询数据为空");
		}
		resultMap.put("flag", Constants.SUCCESS_RESPONSE);
		resultMap.put("data", sysRole);
		long eTime = System.currentTimeMillis();
		logger.info("查询角色详情的请求结束，消耗时间time={}", eTime - sTime);
		return resultMap;
	}
	
	/**
	 * 保存角色信息
	 * @param webData
	 * @return
	 * @throws Exception
	 */
	@ApiOperation(value="保存角色信息")
	@RequestMapping(value="/saveOrUpdate",method=RequestMethod.POST)
	public Map<String, Object> saveOrUpdate(@RequestBody Map<String, Object> webData)
			throws Exception {
		long sTime = System.currentTimeMillis();
		logger.info("开启角色保存的请求，请求参数[{}]", webData.toString());
		Map<String, Object> resultMap = new HashMap<>();
		resultMap.put("flag", Constants.ERROR_RESPONSE);
		resultMap.put("msg", "参数不全");
		if(webData.get("roleName")==null||"".equals(webData.get("roleName").toString())){
			resultMap.put("msg", "roleName参数为空");
			return resultMap;
		}
		if(webData.get("state")==null||"".equals(webData.get("state").toString())){
			resultMap.put("msg", "state参数为空");
			return resultMap;
		}
		SysRole sysRole =new SysRole();
		if(webData.get("roleId")!=null&&!"".equals(String.format("%s", webData.get("roleId")))){
			Long roleId = Long.valueOf(String.format("%s", webData.get("roleId")));
			sysRole = sysRoleService.findByRoleId(roleId);
		}else{
			sysRole.setCreateTime(new Date());
		}
		sysRole.setDescription(webData.get("description")==null?"":webData.get("description").toString());
		sysRole.setRoleName(webData.get("roleName").toString());
		sysRole.setModifyTime(new Date());
		sysRole.setState(Integer.valueOf(webData.get("state").toString()));
		sysRoleService.save(sysRole);

		resultMap.put("flag", Constants.SUCCESS_RESPONSE);
		resultMap.put("msg", "角色保存成功！");
		long eTime = System.currentTimeMillis();
		logger.info("保存角色的请求结束，消耗时间time={}", eTime - sTime);
		return resultMap;
	}
	
	/**
	 * 修改角色状态
	 * @param webData
	 * @return
	 * @throws Exception
	 */
	@ApiOperation(value="修改角色状态")
	@RequestMapping(value="/changeState",method=RequestMethod.POST)
	public Map<String, Object> changeState(@RequestBody Map<String, Object> webData)
			throws Exception {
		long sTime = System.currentTimeMillis();
		logger.info("修改角色状态，请求参数[{}]", webData.toString());
		Map<String, Object> resultMap = new HashMap<>();
		resultMap.put("flag", Constants.ERROR_RESPONSE);
		resultMap.put("msg", "参数不全");
		if(webData.get("roleId")==null||"".equals(webData.get("roleId").toString())){
			resultMap.put("msg", "userId参数为空");
			return resultMap;
		}
		if(webData.get("state")==null||"".equals(webData.get("state").toString())){
			resultMap.put("msg", "state参数为空");
			return resultMap;
		}
		Long roleId = Long.valueOf(String.format("%s", webData.get("roleId")));
		SysRole	sysRole = sysRoleService.findByRoleId(roleId);
		if(sysRole==null){
			resultMap.put("msg", "服务器异常，查询不到对应角色！！！");
			return resultMap;
		}else{
			Integer state = Integer.valueOf(webData.get("state").toString());
			sysRole.setState(state);
			sysRole.setModifyTime(new Date());
			sysRoleService.save(sysRole);
			resultMap.put("flag", Constants.SUCCESS_RESPONSE);
			String msg="";
			if(state==0){
				msg="启用["+sysRole.getRoleName()+"]角色成功！";
			}else{
				msg="禁用["+sysRole.getRoleName()+"]角色成功！";
			}
			resultMap.put("msg", msg);
		}
		
		long eTime = System.currentTimeMillis();
		logger.info("修改角色状态的请求结束，消耗时间time={}", eTime - sTime);
		return resultMap;
	}
	/**
	 * 删除角色信息
	 * @param webData
	 * @return
	 * @throws Exception
	 */
	@ApiOperation(value="删除角色信息")
	@RequestMapping(value="/del",method=RequestMethod.POST)
	public Map<String, Object> del(@RequestBody Map<String, Object> webData)
			throws Exception {
		long sTime = System.currentTimeMillis();
		logger.info("开启用户删除的请求，请求参数[{}]", webData.toString());
		Map<String, Object> resultMap = new HashMap<>();
		resultMap.put("flag", Constants.ERROR_RESPONSE);
		resultMap.put("msg", "参数不全");
		if(webData.get("roleId")==null||"".equals(webData.get("roleId").toString())){
			resultMap.put("msg", "roleId参数为空");
			return resultMap;
		}
		String roleId = (String)webData.get("roleId");
		if(roleId.contains(",")){//批量删除
			String[] ids = roleId.split(",");
			List<Long> list = new ArrayList<>();
			for (String id : ids) {
				list.add(Long.valueOf(id.trim()));
			}
			int batchDelete = sysRoleService.batchDelete(list);
			if(batchDelete>0){
				resultMap.put("msg", "角色批量删除成功！");
				resultMap.put("flag", Constants.SUCCESS_RESPONSE);
			}else{
				resultMap.put("msg", "角色批量删除失败！");	
			}
		}else{	
			sysRoleService.delete(Long.valueOf(roleId));
			resultMap.put("msg", "角色删除成功！");
			resultMap.put("flag", Constants.SUCCESS_RESPONSE);
		}
		long eTime = System.currentTimeMillis();
		logger.info("删除角色的请求结束，消耗时间time={}", eTime - sTime);
		return resultMap;
	}
}

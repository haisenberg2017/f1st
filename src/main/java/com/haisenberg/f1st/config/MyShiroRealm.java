package com.haisenberg.f1st.config;

import org.apache.log4j.Logger;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;

import com.haisenberg.f1st.sys.pojo.SysPermission;
import com.haisenberg.f1st.sys.pojo.SysRole;
import com.haisenberg.f1st.sys.pojo.SysUser;
import com.haisenberg.f1st.sys.service.SysUserService;
import com.haisenberg.f1st.utils.Constants;

/**
 * @ClassName: MyShiroRealm.java
 * @Package: com.haisenberg.f1st.config
 * @Description:
 * @author 张翔
 * @date 2018年5月9日 下午3:14:51
 * @Version:
 */
public class MyShiroRealm extends AuthorizingRealm {
	private static Logger logger = Logger.getLogger(MyShiroRealm.class);
	@Autowired
	private SysUserService sysUserService;

	/*
	 * 认证（登陆）
	 * 
	 */
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken)
			throws AuthenticationException {
		// 获取基于用户名和密码的令牌
		// 实际上这个authcToken是从LoginController里面currentUser.login(token)传过来的
		UsernamePasswordToken token = (UsernamePasswordToken) authenticationToken;
		// 获取用户的输入的账号.
		String username = (String) token.getPrincipal();
		logger.info("[用户:" + username + "|系统权限认证]");
		// 通过username从数据库中查找 User对象，如果找到，没找到.
		// 实际项目中，这里可以根据实际情况做缓存，如果不做，Shiro自己也是有时间间隔机制，2分钟内不会重复执行该方法
		SysUser sysUser = sysUserService.findByUsernameAndState(username, Constants.ABLE);
		System.out.println("----->>sysUser=" + sysUser);
		if (sysUser == null) {
			return null;
		}
		// 交给AuthenticatingRealm使用CredentialsMatcher进行密码匹配，如果觉得人家的不好可以自定义实现
		SimpleAuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo(sysUser, // 用户名
				sysUser.getPassword(), // 密码
				ByteSource.Util.bytes(sysUser.getCredentialsSalt()), // salt=username+salt
				getName() // realm name
		);
		logger.info("[用户:" + username + "|系统权限认证完成]");
		return authenticationInfo;
	}
	/*
	 * 授权
	 *
	 */
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		System.out.println("权限配置-->MyShiroRealm.doGetAuthorizationInfo()");
		SysUser sysUser = (SysUser) principals.getPrimaryPrincipal();
		logger.info("[用户:" + sysUser.getUsername() + "|权限授权]");
		SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
		for (SysRole role : sysUser.getRoleList()) {
			authorizationInfo.addRole(role.getRoleName());
			for (SysPermission p : role.getPermissions()) {
				authorizationInfo.addStringPermission(p.getPermission());
			}
		}
		logger.info("[用户:" + sysUser.getUsername() + "|权限授权完成]");
		return authorizationInfo;
	}

}

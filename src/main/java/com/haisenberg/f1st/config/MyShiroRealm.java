package com.haisenberg.f1st.config;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
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

/**
 * @ClassName: MyShiroRealm.java
 * @Package: com.haisenberg.f1st.config
 * @Description:
 * @author 张翔
 * @date 2018年5月9日 下午3:14:51
 * @Version:
 */
public class MyShiroRealm extends AuthorizingRealm {
@Autowired
private SysUserService sysUserService;
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		System.out.println("权限配置-->MyShiroRealm.doGetAuthorizationInfo()");
		SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
		SysUser sysUser = (SysUser) principals.getPrimaryPrincipal();
		for (SysRole role : sysUser.getRoleList()) {
			authorizationInfo.addRole(role.getRoleName());
			for (SysPermission p : role.getPermissions()) {
				authorizationInfo.addStringPermission(p.getPermission());
			}
		}
		return authorizationInfo;
	}

	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
		 System.out.println("MyShiroRealm.doGetAuthenticationInfo()");
		    //获取用户的输入的账号.
		    String username = (String)token.getPrincipal();
		    System.out.println(token.getCredentials());
		    //通过username从数据库中查找 User对象，如果找到，没找到.
		    //实际项目中，这里可以根据实际情况做缓存，如果不做，Shiro自己也是有时间间隔机制，2分钟内不会重复执行该方法
		    SysUser sysUser = sysUserService.findByUsername(username);
		    System.out.println("----->>sysUser="+sysUser);
		    if(sysUser == null){
		        return null;
		    }
		    SimpleAuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo(
		    		sysUser, //用户名
		    		sysUser.getPassword(), //密码
		            ByteSource.Util.bytes(sysUser.getCredentialsSalt()),//salt=username+salt
		            getName()  //realm name
		    );
		    return authenticationInfo;
	}

}

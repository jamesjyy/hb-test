package cn.best.approveservice.common.relam;

import cn.best.approveservice.common.utils.CommonUtil;
import cn.best.approveservice.entity.Permission;
import cn.best.approveservice.entity.Role;
import cn.best.approveservice.entity.User;
import cn.best.approveservice.service.UserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.Resource;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class MyShiroRealm extends AuthorizingRealm {

    final Logger logger = LoggerFactory.getLogger(MyShiroRealm.class);

    @Resource
    private UserService userService;

    /*@Autowired
    private TbSysAuthService authService;*/

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {

        //授权逻辑
        //获取登录的用户名
        User user = (User)SecurityUtils.getSubject().getPrincipal();
        //到数据库里查询要授权的内容
       user.getName();
        //记录用户的所有角色和权限
        SimpleAuthorizationInfo simpleAuthorizationInfo=new SimpleAuthorizationInfo();//权限信息
      /*  for(Role r:user.getRoles()){
            //将所有的角色信息添加进来。
            simpleAuthorizationInfo.addRole(r.getRoleName());
            for(Permission p:r.getPermissions()){
                //将此次遍历到的角色的所有权限拿到，添加·进来
                simpleAuthorizationInfo.addStringPermission(p.getName());
            }
        }*/
        return simpleAuthorizationInfo;
    }

    /*主要是用来进行身份认证的，也就是说验证用户输入的账号和密码是否正确。*/
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token)
            throws AuthenticationException {
        UsernamePasswordToken authToken= (UsernamePasswordToken) token;
        //获取用户的输入的账号.
        String username = (String) authToken.getUsername();
        String password=String.valueOf(authToken.getPassword());
        //通过username从数据库中查找 User对象，如果找到，没找到.
        //实际项目中，这里可以根据实际情况做缓存，如果不做，Shiro自己也是有时间间隔机制，2分钟内不会重复执行该方法
        User userInfo = userService.findByUsername(username);
        if(CommonUtil.isNull(userInfo)){
            throw new UnknownAccountException("账号不存在");
        }

        SimpleAuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo(
                userInfo, //用户名
                userInfo.getPassword(),
                getName()  //realm name
        );
        return authenticationInfo;
    }
}

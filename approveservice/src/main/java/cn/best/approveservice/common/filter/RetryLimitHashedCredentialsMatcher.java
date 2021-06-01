package cn.best.approveservice.common.filter;

import cn.best.approveservice.common.utils.CommonUtil;
import org.apache.log4j.Logger;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.credential.SimpleCredentialsMatcher;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheManager;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author zhuyl
 * 自定义密码比较器，用户登陆失败次数超过5次，锁定该账号两小时
 * @date 2020-6-3 16:56
 */
public class RetryLimitHashedCredentialsMatcher extends SimpleCredentialsMatcher {
    private static final Logger log = Logger.getLogger(RetryLimitHashedCredentialsMatcher.class);
    /*@Autowired
    private UserMapper userMapper;*/
    private Cache<String, AtomicInteger> passwordRetryCache;
    private Cache<String, Integer> userStatus;

    public RetryLimitHashedCredentialsMatcher(CacheManager cacheManager) {
        passwordRetryCache = cacheManager.getCache("passwordRetryCache");
        userStatus = cacheManager.getCache("userStatus");
    }

    @Override
    public boolean doCredentialsMatch(AuthenticationToken token, AuthenticationInfo info) {

        //获取用户名
        String username = (String)token.getPrincipal();
        Integer status=userStatus.get(username);
        if(CommonUtil.isNull(status)||status==0){
            //获取用户登录次数
            AtomicInteger retryCount = passwordRetryCache.get(username);
            if (retryCount == null) {
                //如果用户没有登陆过,登陆次数加1 并放入缓存
                retryCount = new AtomicInteger(0);
                passwordRetryCache.put(username, retryCount);
            }
            if (retryCount.incrementAndGet() > 5) {
                //如果用户登陆失败次数大于5次 抛出锁定用户异常  并修改数据库字段
                userStatus.put(username,1);
                log.error("锁定用户" + username);
                //抛出用户锁定异常
                throw new LockedAccountException("用户锁定");
            }
            //判断用户账号和密码是否正确
            boolean matches = super.doCredentialsMatch(token, info);
            if (matches) {
                //如果正确,从缓存中将用户登录计数 清除
                passwordRetryCache.remove(username);
            }
            return matches;
        }else {
            throw new LockedAccountException("用户锁定");
        }
    }

    /**
     * 根据用户名 解锁用户
     * @param username
     * @return
     */
    /*public void unlockAccount(String username){
        User user = userMapper.findUserByUname(username);
        if (user != null){
            //修改数据库的状态字段为锁定
            userStatus.put(username,0);
        }
    }*/



}

package cn.best.approveservice.common.filter;

import cn.best.approveservice.response.Response;
import com.alibaba.fastjson.JSON;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheManager;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.SessionException;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authc.LogoutFilter;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import java.io.PrintWriter;
import java.io.Serializable;
import java.util.Deque;

/**
 * 退出过滤
 * @author zhuyl
 * @version 1.0
 * @date 2020-6-4 17:49
 */
public class CustomLogOutFilter extends LogoutFilter {
    private static String checkOutFlag = "checkOut";
    private Cache<String, Deque<Serializable>> cache;

    public void setCacheManager(CacheManager cacheManager) {
        this.cache = cacheManager.getCache("shiro-activeSessionCache");
    }

    @Override
    protected boolean preHandle(ServletRequest request, ServletResponse response) throws Exception {
        // 清空相关缓存
        Subject subject = getSubject(request, response);
        if(subject != null && subject.getPrincipal() != null) {
            Session session = subject.getSession();
            String username = subject.getPrincipal() + "";
            Serializable sessionId = session.getId();
            Deque<Serializable> deque = cache.get(username);
            if(deque != null && deque.contains(sessionId)) {
                //清除为了校验单一登录留下的缓存
                deque.remove(sessionId);
                if(deque.size() == 0) {
                    cache.remove(username);
                } else {
                    cache.put(username, deque);
                }
                session.setAttribute(checkOutFlag, null);
            }
        }
        try {
            subject.logout();
        } catch (SessionException ise) {
            ise.printStackTrace();
        }
        response.setCharacterEncoding("utf-8");
        PrintWriter writer = response.getWriter();
        Response result=new Response();
        result.success();
        writer.write(JSON.toJSONString(result));
        return false;
    }

}

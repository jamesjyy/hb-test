package cn.best.approveservice.common.filter;

import cn.best.approveservice.common.config.SessionDAO;
import cn.best.approveservice.common.constant.CommonConstants;
import cn.best.approveservice.common.handler.WebSocketHandler;
import org.apache.log4j.Logger;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheManager;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.mgt.DefaultSessionKey;
import org.apache.shiro.session.mgt.SessionManager;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.AccessControlFilter;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import java.io.Serializable;
import java.util.ArrayDeque;
import java.util.Deque;

/**
 * @author zhuyl
 * @version 1.0
 * @date 2020-6-2 16:47
 */
/*判断用户是否重复登录，并踢出前者*/
public class KickoutSessionControlFilter extends AccessControlFilter {
    private static final Logger log = Logger.getLogger(KickoutSessionControlFilter.class);
    private static SessionDAO sessionDAO;

    private boolean kickoutAfter = false; // 踢出之前登录的/之后登录的用户 默认false踢出之前登录的用户
    private int maxSession = 1; // 同一个帐号最大会话数 默认1
    private SessionManager sessionManager;
    private Cache<String, Deque<Serializable>> cache;

    public void setKickoutAfter(boolean kickoutAfter) {
        this.kickoutAfter = kickoutAfter;
    }

    public void setMaxSession(int maxSession) {
        this.maxSession = maxSession;
    }

    public void setSessionManager(SessionManager sessionManager) {
        this.sessionManager = sessionManager;
    }

    // 设置Cache的key的前缀
    public void setCacheManager(CacheManager cacheManager) {
        //必须和ehcache缓存配置中的缓存name一致
        this.cache = cacheManager.getCache("shiro-activeSessionCache");
    }

    @Override
    protected boolean isAccessAllowed(ServletRequest request,
                                      ServletResponse response, Object mappedValue) throws Exception {
        return false;
    }

    @Override
    protected boolean onAccessDenied(ServletRequest request,
                                     ServletResponse response) throws Exception {
        Subject subject = getSubject(request, response);
        // 没有登录授权 且没有记住我
        if (!subject.isAuthenticated() && !subject.isRemembered()) {
            // 如果没有登录，直接进行之后的流程
            return true;
        }
        Session session = subject.getSession();
        log.debug("==session时间设置：" + String.valueOf(session.getTimeout())
                + "===========");
        try {
            // 当前用户
            String username = subject.getPrincipal() + "";
            log.debug("===当前用户username：==" + username);
            Serializable sessionId = session.getId();
            log.debug("===当前用户sessionId：==" + sessionId);
            // 读取缓存用户 没有就存入
            Deque<Serializable> deque = cache.get(username);
            log.debug("===当前deque：==" + deque);
            if (deque == null) {
                // 初始化队列
                deque = new ArrayDeque<Serializable>();
            }
            // 如果队列里没有此sessionId，且用户没有被踢出；放入队列
            if (!deque.contains(sessionId)
                    && session.getAttribute("kickout") == null) {
                // 将sessionId存入队列
                deque.push(sessionId);
                // 将用户的sessionId队列缓存
                cache.put(username, deque);
            }
            // 如果队列里的sessionId数超出最大会话数，开始踢人
            while (deque.size() > maxSession) {
                log.debug("===deque队列长度：==" + deque.size());
                // 是否踢出后来登录的，默认是false；即后者登录的用户踢出前者登录的用户；
                Serializable kickoutSessionId = null;
                if (kickoutAfter) { // 如果踢出后者
                    kickoutSessionId = deque.removeFirst();
                } else { // 否则踢出前者
                    kickoutSessionId = deque.removeLast();
                }
                // 踢出后再更新下缓存队列
                cache.put(username, deque);
                try {
                    // 获取被踢出的sessionId的session对象
                    Session kickoutSession = sessionManager
                            .getSession(new DefaultSessionKey(kickoutSessionId));
                    if (kickoutSession != null) {
                        // 设置会话的kickout属性表示踢出了
                        kickoutSession.setAttribute("kickout", true);
                        WebSocketHandler.sendInfo(CommonConstants.SOCKET_MSG, kickoutSessionId.toString());
                        Subject requestSubject = new Subject.Builder().sessionId(kickoutSessionId).buildSubject();
                        requestSubject.logout();
                    }
                } catch (Exception e) {// ignore exception
                    e.printStackTrace();
                }
            }

            // 如果被踢出了，(前者或后者)直接退出，重定向到踢出后的地址
            if ((Boolean) session.getAttribute("kickout") != null && (Boolean) session.getAttribute("kickout") == true) {
                // 会话被踢出了
                try {
                    // 退出登录
                    subject.logout();
                } catch (Exception e) { // ignore
                }
                saveRequest(request);
                log.debug("==踢出后使用websocket向前端传消息" );
                // 重定向
                WebSocketHandler.sendInfo("退出登录", sessionId.toString());
                return false;
            }
            return true;
        } catch (Exception e) { // ignore
            //重定向到登录界面
            //WebUtils.issueRedirect(request, response, "/auth/getLoginPage");
            return false;
        }
    }

}

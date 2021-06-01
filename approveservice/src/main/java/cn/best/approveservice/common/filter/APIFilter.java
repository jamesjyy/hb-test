package cn.best.approveservice.common.filter;

import cn.best.approveservice.common.constant.CommonConstants;
import cn.best.approveservice.response.Response;
import net.sf.ehcache.CacheException;
import net.sf.ehcache.CacheManager;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.ehcache.EhCacheManager;
import org.apache.shiro.io.ResourceUtils;
import org.apache.shiro.web.util.WebUtils;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author zhuyl
 * 限制单个用户对同一个接口在一分钟内的最大请求次数为10次
 * @date 2020-6-4 11:00
 */
@Component
@Order(2)
public class APIFilter implements Filter {

    private Cache<String, AtomicInteger> cache;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void destroy() {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        EhCacheManager ehcache = new EhCacheManager();
        net.sf.ehcache.CacheManager cacheManager = net.sf.ehcache.CacheManager.getCacheManager("shiro");
        if(cacheManager == null){
            try {
                cacheManager = CacheManager.create(ResourceUtils.getInputStreamForPath("classpath:ehcache.xml"));
            } catch (CacheException | IOException e) {
                e.printStackTrace();
            }
        }
        ehcache.setCacheManager(cacheManager);
        cache=ehcache.getCache("APICount");
        String id = WebUtils.toHttp(servletRequest).getHeader(CommonConstants.URL_TOKEN);
        String uri=WebUtils.toHttp(servletRequest).getRequestURI();
        String method=WebUtils.toHttp(servletRequest).getMethod();
        if("OPTIONS".equals(method)){
            filterChain.doFilter(servletRequest,servletResponse);
        }
        String key=id+uri;
        AtomicInteger count=cache.get(key);
        if (count == null) {
            //如果用第一次对接口发送请求，请求次数加一 并放入缓存
            count = new AtomicInteger(0);
            cache.put(key, count);
        }
        if (count.incrementAndGet() > 60) {
            Response response = new Response();
            servletResponse.setCharacterEncoding("UTF-8");
            PrintWriter out = servletResponse.getWriter();
            ObjectMapper mapper = new ObjectMapper();
            out.println(mapper.writeValueAsString(response.failure("刷新频率过快，请等1分钟后重试！")));
            return;
        }else {
            filterChain.doFilter(servletRequest,servletResponse);
        }

    }
}

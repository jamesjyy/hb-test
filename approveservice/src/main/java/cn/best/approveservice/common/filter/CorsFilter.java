package cn.best.approveservice.common.filter;

import cn.best.approveservice.common.utils.CollectionUtil;
import cn.best.approveservice.common.utils.CommonUtil;
import cn.best.approveservice.common.utils.StringUtil;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;


/**
 * Title: 跨域访问处理(跨域资源共享)    
 * Description: 解决前后端分离架构中的跨域问题
 * @author hai
 * @created 2017年7月4日 下午5:00:09    
 */

@Component
@Order(1)
public class CorsFilter implements Filter {

	/** Log4j日志处理(@author: rico) */
	private static final Logger log = Logger.getLogger(CorsFilter.class);
	@Value("${CorsFilter.allowOrigin}")
	private String allowOrigin;
	private String allowMethods="GET,POST";
	private String allowCredentials="true";
	private String allowHeaders="accept,accept-encoding,content-type,origin,user-agent,x-requested-with,x-token";
	private String exposeHeaders;

	/*@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		allowOrigin = filterConfig.getInitParameter("allowOrigin");
		allowMethods = filterConfig.getInitParameter("allowMethods");
		allowCredentials = filterConfig.getInitParameter("allowCredentials");
		allowHeaders = filterConfig.getInitParameter("allowHeaders");
		exposeHeaders = filterConfig.getInitParameter("exposeHeaders");
	}*/


	@Override
	public void init(FilterConfig filterConfig) throws ServletException {

	}

	/**
	 * @description 通过CORS技术实现AJAX跨域访问,只要将CORS响应头写入response对象中即可
	 * @author hai
	 * @created 2017年7月4日 下午5:02:38      
	 * @param req
	 * @param res
	 * @param chain
	 * @throws IOException
	 * @throws ServletException
	 * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
	 */  
	@Override
	public void doFilter(ServletRequest req, ServletResponse res,
                         FilterChain chain) throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) req;
		HttpServletResponse response = (HttpServletResponse) res;
		String currentOrigin = request.getHeader("Origin");
		final String method = ((HttpServletRequest) req).getMethod();
		log.debug("currentOrigin : " + currentOrigin);
		if (StringUtil.isNotEmpty(allowOrigin)&& StringUtil.isNotEmpty(currentOrigin)) {
			accessSet(response, currentOrigin,"Access-Control-Allow-Origin");
		}
		if (StringUtil.isNotEmpty(allowMethods)) {
			response.setHeader("Access-Control-Allow-Methods", allowMethods);
		}
		if (StringUtil.isNotEmpty(allowCredentials)) {
			response.setHeader("Access-Control-Allow-Credentials",
					allowCredentials);
		}
		if (StringUtil.isNotEmpty(allowHeaders)) {
//			accessSet(response, allowHeaders,"Access-Control-Allow-Headers");
			response.setHeader("Access-Control-Allow-Headers", allowHeaders);
//			"Content-Type,x-token,x-requested-with"
//			response.setHeader("Access-Control-Allow-Headers", "x-requested-with,content-type,x-token");
		}
		if (StringUtil.isNotEmpty(exposeHeaders)) {
			response.setHeader("Access-Control-Expose-Headers", exposeHeaders);
		}
		//解决Ajax跨域预检请求拦截302问题
		//拒绝不合法地址请求
		if("OPTIONS".equals(method)){
			boolean visit = checkUrl(currentOrigin);
			int status = visit? HttpServletResponse.SC_OK: HttpServletResponse.SC_FORBIDDEN;
			response.setStatus(status);
			return;
		}
		/*response.setHeader("Access-Control-Max-Age", "3600");
		response.setHeader("Access-Control-Allow-Origin",request.getHeader("Origin"));
		response.setHeader("Access-Control-Allow-Credentials", "true");
		response.setHeader("Access-Control-Allow-Methods", "POST, OPTION, GET, PUT, DELETE");
		response.setHeader("Access-Control-Max-Age", "3600");
		response.setHeader("Access-Control-Allow-Headers", "accept,accept-encoding,authorization,content-type,dnt,origin,user-agent,x-csrftoken,x-requested-with,x-token");
		response.addHeader("Access-Control-Expose-Headers", "x-token");
		response.setStatus(HttpServletResponse.SC_OK);*/
		chain.doFilter(req, res);
	}

	private void accessSet(HttpServletResponse response, String currentOrigin, String access) {
		List<String> allowOriginList = Arrays.asList(currentOrigin.split(","));
		log.debug("allowOriginList : " + currentOrigin);
		if (CollectionUtil.isNotEmpty(allowOriginList)) {
			if (allowOriginList.contains(currentOrigin)) {
				//"Access-Control-Allow-Origin"
				response.setHeader(access, currentOrigin);
			}
		}
	}

	//判断当前访问请求地址是否是合法地址
	private boolean checkUrl(String currentOrigin){
		if(CommonUtil.isNull(this.allowOrigin) || CommonUtil.isNull(currentOrigin)){
			return false;
		}
		return Arrays.stream(this.allowOrigin.split(",")).anyMatch(url->url.equals(currentOrigin));
	}

	@Override
	public void destroy() {
	}
}
package cn.best.approveservice.common.filter;

import cn.best.approveservice.common.constant.CommonConstants;
import cn.best.approveservice.response.Response;
import com.alibaba.fastjson.JSON;
import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;

//用户是否登录验证
public class ShiroCrosFilter extends FormAuthenticationFilter {

    public ShiroCrosFilter() {
        super();
    }

    @Override
    public boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) {
        //Always return true if the request's method is OPTIONS
        if (request instanceof HttpServletRequest) {
            if (((HttpServletRequest) request).getMethod().toUpperCase().equals("OPTIONS")) {
                return true;
            }
        }
            return super.isAccessAllowed(request, response, mappedValue);
    }

    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
        HttpServletResponse res = (HttpServletResponse)response;
//        res.setHeader("Access-Control-Allow-Origin", "*");
        res.setStatus(HttpServletResponse.SC_OK);
        res.setCharacterEncoding("utf-8");
//         PrintWriter out = new PrintWriter(new OutputStreamWriter(new FileOutputStream("PrintWriterTest.txt"), "utf-8"));
        PrintWriter writer = res.getWriter();
        Response result=new Response();
        result.failure(CommonConstants.FAIL_MSG);
        writer.write(JSON.toJSONString(result));
        writer.close();
        return false;
    }
}

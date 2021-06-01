package cn.best.approveservice.common.filter;

import cn.best.approveservice.response.Response;
import org.apache.shiro.web.filter.authz.PermissionsAuthorizationFilter;
import org.codehaus.jackson.map.ObjectMapper;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import java.io.PrintWriter;
//权限判断
public class MyPermissionFilter extends PermissionsAuthorizationFilter {
    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response, Object mappedValue) throws Exception {
        //权限验证
        response.setCharacterEncoding("UTF-8");
        Response result = new Response();
        PrintWriter out = response.getWriter();
        ObjectMapper mapper = new ObjectMapper();
        out.println(mapper.writeValueAsString(result.failure("Unauthorized")));
        return false;
    }

}

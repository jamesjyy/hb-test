package cn.best.approveservice.controller;


import cn.best.approveservice.common.utils.RequestContextHolderUtil;
import cn.best.approveservice.entity.User;
import cn.best.approveservice.entity.Userinfo;
import org.apache.catalina.Manager;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;
//import java.util.HashMap;
//import java.util.Map;
//import java.util.Set;

public abstract class BaseController {
    final static String USER_CONTEXT = "user";
    /**
     * 注销用户
     * @param
     */
    @SuppressWarnings("unchecked")
    protected void logOut() {
        HttpSession session = RequestContextHolderUtil.getSession();
        session.removeAttribute(USER_CONTEXT);
    }

    /**
     * 获取保存在Session中的对象
     * @return
     */
    @SuppressWarnings("unchecked")
    protected Object getSessionObj() {
        HttpSession session = RequestContextHolderUtil.getSession();
        return session.getAttribute(USER_CONTEXT);
    }

    /**
     * 获取保存在Session中的登录人员信息
     * @return
     */
    protected User getSessionUser() {
        return (User) this.getSessionObj();
    }

    /**
     * 保存对象到Session中
     * @param user
     */
//    @SuppressWarnings("unchecked")
//    protected String setSessionObj(String key, Object obj) {
//        HttpSession session = RequestContextHolderUtil.getSession();
//        session.removeAttribute(SessionKeyConstant.SESSION_CONTEXT);
//        Map<String, Map<String, Object>> map = new HashMap<String, Map<String, Object>>();
//        String sid = UUIDGenerator.getUUID();
//        Map<String, Object> m = new HashMap<String, Object>();
//        m.put(sid, obj);
//        map.put(SessionKeyConstant.USER_CONTEXT, m);
//        session.setAttribute(SessionKeyConstant.SESSION_CONTEXT, map);
//        return sid;
//    }


    /**
     * 获取SID，如果为NULL 返回空字符
     * @return
     */
//    protected String getSid() {
//        HttpServletRequest request = RequestContextHolderUtil.getRequest();
//        return StringUtil.delNull(request.getParameter("sid"));
//    }

    /**
     * 获取访问者的IP地址
     * @param request
     * @return
     */
    public String getIpAddr() {
        HttpServletRequest request = RequestContextHolderUtil.getRequest();
        String ip = request.getHeader("x-forwarded-for");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        return ip;
    }

    /**
     * 判断验证码是否输入
     * @param randCode
     * @return
     */
//    public boolean validateImgCode(String yzm){
//        boolean flag = true;
//        // 从session中取出验证码
//        String randCode = (String)RequestContextHolderUtil.getSession().getAttribute("imgCode");
//
//        // 输入的验证码与session中的验证码比对（忽略大小写）
//        if (CommonUtil.isNull(yzm) || CommonUtil.isNull(randCode)) {
//            flag = false;
//        } else {
//            if (StringUtil.equals(yzm.toLowerCase(), randCode.toLowerCase())) {
//                flag = true;
//            } else {
//                flag = false;
//            }
//        }
//        return flag;
//    }

    /**
     * 通过sid获取userid
     * @param sid
     * @return
     */
//    protected Integer getUserid(String sid) {
//        Set<Integer> kset =SessionContext.sidMap.keySet();
//        for(Integer ks:kset){
//            if(sid.equals(SessionContext.sidMap.get(ks))){
//                return ks;
//            }
//        }
//        return null;
//    }
}

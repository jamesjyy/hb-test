package cn.best.approveservice.controller;

import cn.best.approveservice.entity.User;
import cn.best.approveservice.response.R;
import cn.best.approveservice.service.IndexService;
import com.alibaba.fastjson.JSONObject;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/admin/acl/index")
//@CrossOrigin
public class IndexController extends BaseController{

    @Autowired
    private IndexService indexService;

    /**
     * 根据token获取用户信息
     */
    @GetMapping("info")
    public R info(){
        //获取当前登录用户用户名
        User user = (User) SecurityUtils.getSubject().getPrincipal();
        //获取登录的用户名
        String username = user.getName();
        Map<String, Object> userInfo = indexService.getUserInfo(username);
        return R.ok().data(userInfo);
    }

    @RequestMapping("getlogin")
    public R getlogin(){
        User sessionUser = this.getSessionUser();
        if("".equals(sessionUser)||null==sessionUser){
            boolean flag;
            flag=false;
            return R.ok().data("flag",flag);
        }
        return R.ok();
    }

    /**
     * 获取菜单
     * @return
     */
    @RequestMapping("menu")
    public Map<String,Object> getMenu(){
        Map<String,Object>map=new HashMap<>();
        Map<String,Object>homeInfo=new HashMap<>();
        Map<String,Object>logoInfo=new HashMap<>();
        homeInfo.put("title","首页");
        homeInfo.put("href","welcome.html");
        logoInfo.put("title","宏博数据管理平台");
        logoInfo.put("image","../static/content/plugin/layuimini/images/favicon.ico");
        logoInfo.put("href","");
        map.put("homeInfo",homeInfo);
        map.put("logoInfo",logoInfo);

        //获取当前登录用户用户名
       // User user = (User) SecurityUtils.getSubject().getPrincipal();
        boolean flag=false;
        if (null==this.getSessionUser()||"".equals(this.getSessionUser())){
            flag = true;
            Map<String, Object> data = new HashMap<>();
            data.put("flagss", flag);
            return data;
        }
        User sessionUser = this.getSessionUser();
        //获取登录的用户名

        String username = sessionUser.getName();
        //如果
        String usernames="test666";
        List<JSONObject> permissionList = indexService.getMenu(username);
        map.put("menuInfo",permissionList);
        return map;


    }

    @PostMapping("logout")
    public R logout(){
        return R.ok();
    }

}

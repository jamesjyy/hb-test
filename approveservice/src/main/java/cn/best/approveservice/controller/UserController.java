package cn.best.approveservice.controller;

import cn.best.approveservice.common.utils.CommonUtil;
import cn.best.approveservice.entity.*;
import cn.best.approveservice.response.R;
import cn.best.approveservice.response.Response;
import cn.best.approveservice.service.*;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.Page;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.text.BreakIterator;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * (User)表控制层
 *
 * @author makejava
 * @since 2021-04-08 10:17:59
 */
@RestController
@RequestMapping("user")
public class UserController extends BaseController {
    @Autowired
    private UserinfoService userinfoService;
    @Autowired
    Response response;
    @Autowired
    private RoleService roleService;
    @Autowired
    private UserService userService;
    @Autowired
    private ProinfoService proinfoService;
    @Autowired
    private UserRoleService userRoleService;

    /**
     * 服务对象
     */


    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping("selectOne")
    public User selectOne(Integer id) {
        return this.userService.queryById(id);
    }

    @ApiOperation(value = "用户登录", notes = "用户登录", httpMethod = "POST")
    @RequestMapping("/doLogin")
    @ResponseBody
    public Response doLogin(String username, String password, HttpSession session) {
        Subject subject = SecurityUtils.getSubject();
        String Md5Pass = new Md5Hash(password).toHex();
        UsernamePasswordToken token = new UsernamePasswordToken(username, Md5Pass, false);
        try {
            subject.login(token);
            String sessionId = (String) subject.getSession().getId();
            Map<String, Object> map = new HashMap<>();

            session.setAttribute("user", subject.getPrincipal());
            User user = (User) subject.getPrincipal();
            Map<String, Object> roleMap = roleService.findRoleByUserId(user.getId());
            List<Role> listrole = (List<Role>) roleMap.get("assignRoles");
            List rolelist = new ArrayList();
            for (int i = 0; i < listrole.size(); i++) {
                rolelist.add(listrole.get(i).getRole());
            }
            session.setAttribute("username", username);
            map.put("sessionId", sessionId);
            map.put("rolelist", rolelist);
            map.put("user", JSON.toJSONString(subject.getPrincipal()));
            return response.success(map);
        } catch (UnknownAccountException e) {
            return response.failure("账号不存在");
        } catch (IncorrectCredentialsException e) {
            return response.failure("密码错误");
        } catch (LockedAccountException e) {
            return response.failure("用户已被锁定");
        }
    }

    /**
     * 新增数据
     *
     * @param
     * @return 修改结果
     */
    @ApiOperation("新增用户信息")
    @RequestMapping(value = "/insertUserinfo", produces = "application/json")
    public Response insertUserinfo(@RequestParam("username") String username,
                                   @RequestParam("password") String password,
                                   @RequestParam("companyname") String companyname,
                                   @RequestParam("phone") String phone,
                                   @RequestParam("address") String address, @RequestParam("domains") String[]domains) {
        //如果没有密码默认123456
        if ("".equals(password)||null==password){
            password="123456";
        }

        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Response response = new Response();
        User user = new User();
        boolean flag = false;
        //用户名不能重复
        User byUsername = userService.findByUsername(username);
        //公司名称不能重复
        List<User> findbycompany = userService.findbycompany(companyname);
        if (byUsername != null && !byUsername.equals("")) {
            flag = true;
            Map<String, Object> data = new HashMap<>();
            data.put("flags", flag);
            return response.success(data);
        }

        if (findbycompany.size()!=0) {
            flag = true;
            Map<String, Object> data = new HashMap<>();
            data.put("flag", flag);
            return response.success(data);
        }
        user.setName(username);
        String Md5Pass = new Md5Hash(password).toHex();
        user.setPassword(Md5Pass);
        user.setCompanyName(companyname);
        user.setPhone(phone);
        user.setAddress(address);
        StringBuffer sb = new StringBuffer();
        for(int i = 0; i < domains.length; i++) {
            sb.append(domains[i]+",");
        }
        String substring = sb.substring(0, sb.length() - 1);
        user.setAppid(substring);
        if (null==this.getSessionUser()||"".equals(this.getSessionUser())){
            flag = true;
            Map<String, Object> data = new HashMap<>();
            data.put("flagss", flag);
            return response.success(data);
        }


            //插入用户表数据
        User insert = userService.insert(user);
        //如果是超管默认添加的用户是普通管理员
        if ("admin".equals(this.getSessionUser().getName())){
            List<UserRole> userRoleList = new ArrayList<>();
           UserRole userRole=new UserRole();
           userRole.setGmtCreate(new Date());
           userRole.setRoleId("2");
           userRole.setUserId(insert.getId().toString());
           userRoleList.add(userRole);
            userRoleService.saveBatch(userRoleList);

        }else {
            List<UserRole> userRoleList = new ArrayList<>();
            UserRole userRole=new UserRole();
            userRole.setGmtCreate(new Date());
            userRole.setRoleId("3");
            userRole.setUserId(insert.getId().toString());
            userRoleList.add(userRole);
            userRoleService.saveBatch(userRoleList);
        }
        //如果是普通用户默认添加的是子用户
        //向设备表插入数据
        Proinfo proinfo=new Proinfo();
        for (String appid : domains) {
            proinfo.setAppid(appid);
            proinfo.setUname(companyname);
            proinfo.setUserid(insert.getId());
            proinfo.setEquipment(appid);
            proinfo.setCreatetime(sdf.format(new Date()));
            proinfoService.insert(proinfo);
        }

        return CommonUtil.isNull(insert) ? response.failure("新增数据出现问题！") : response.success(user);

    }

    @ApiOperation(value = "修改zi用户")
    @RequestMapping("updatechild")
    public R updatechild(@RequestParam("username") String username,
                    @RequestParam("password") String password,
                    @RequestParam("companyname") String companyname,
                    @RequestParam("phone") String phone,
                    @RequestParam("id") Integer id,
                    @RequestParam("address") String address,@RequestParam("appid") String appid) {
        //如果没有密码默认123456
        if ("".equals(password)||null==password){
            password="123456";
        }
        User user = new User();
        user.setName(username);
        user.setId(id);
        String Md5Pass = new Md5Hash(password).toHex();
        user.setPassword(Md5Pass);
        user.setCompanyName(companyname);
        user.setPhone(phone);
        user.setAddress(address);
        //修改用户
        userService.update(user);
        return R.ok();

    }

    @ApiOperation(value = "修改用户")
    @RequestMapping("update")
    public R updaye(@RequestParam("username") String username,
                    @RequestParam("password") String password,
                    @RequestParam("companyname") String companyname,
                    @RequestParam("phone") String phone,
                    @RequestParam("id") Integer id,
                    @RequestParam("address") String address, @RequestParam("domains") String[]domains) {
        //如果没有密码默认123456
        if ("".equals(password)||null==password){
            password="123456";
        }
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Response response = new Response();
        User user = new User();
        user.setName(username);
        user.setId(id);
        String Md5Pass = new Md5Hash(password).toHex();
        user.setPassword(Md5Pass);
        user.setCompanyName(companyname);
        user.setPhone(phone);
        user.setAddress(address);
        StringBuffer sb = new StringBuffer();
        for(int i = 0; i < domains.length; i++) {
            sb.append(domains[i]+",");
        }
        String substring = sb.substring(0, sb.length() - 1);
        user.setAppid(substring);
        //修改用户
        userService.update(user);
        //修改设备
        Proinfo proinfo=new Proinfo();
        proinfo.setUserid(id);
        //根据用户id查询对应的设备
        List<Proinfo> selebyuserid = proinfoService.selebyuserid(proinfo);
        for (Proinfo proinfobyuserid: selebyuserid) {
            proinfoService.deleteById(proinfobyuserid.getId());
        }
        for (String appid : domains) {
            proinfo.setAppid(appid);
            proinfo.setUname(companyname);
            proinfo.setEquipment(appid);
            proinfo.setUserid(id);
            proinfo.setUpdatetime(sdf.format(new Date()));

           //删除再新增
            proinfoService.insert(proinfo);
        }
        return R.ok();
    }

    @ApiOperation(value = "根据用户名查询用户是否存在")
    @RequestMapping("selectbyname")
    public R selectbyname(@RequestParam("username") String username) {
        //查询用户名是否存在
        boolean flag = false;
        User byUsername = userService.findByUsername(username);
        if (byUsername != null && !byUsername.equals("")) {

            flag = true;
        }
        return R.ok().data("flag", flag);
    }

    @ApiOperation(value = "根据用户名查询用户是否存在")
    @RequestMapping("selectbycompany")
    public R selectbycompany(@RequestParam("companyname") String companyname) {
        //查询用户名是否存在
        boolean flag = false;
        List<User> bycompany = userService.findbycompany(companyname);
        if (bycompany.size()!=0) {

            flag = true;
        }
        return R.ok().data("flag", flag);
    }

    @ApiOperation(value = "删除用户")
    @RequestMapping("remove")
    public R remove(@RequestParam("id") Integer id) {
        userinfoService.deleteById(id);
        //删除角色用户表  根据用户id查询出对应的角色用户关系表
        QueryWrapper<UserRole> wrapper = new QueryWrapper<>();
        wrapper.eq("user_id",id);
        List<UserRole> userRoles = userRoleService.list(wrapper);
        //调用删除方法
        for (UserRole userRole : userRoles) {
            userRoleService.removeById(userRole.getId());
        }
        //删除设备表
        Proinfo proinfo=new Proinfo();
        proinfo.setUserid(id);
        List<Proinfo> selebyuserid = proinfoService.selebyuserid(proinfo);
        for (Proinfo proinfo1 : selebyuserid) {
            proinfoService.deleteById(proinfo1.getId());
        }
        return R.ok();
    }

    @ApiOperation(value = "分页查询", notes = "用户管理分页查询", httpMethod = "GET")
    @RequestMapping(value = "/getUserListByPage")
    public Response getUserListByPage(User user, @RequestParam(defaultValue = "1") Integer pageNum
            , @RequestParam(defaultValue = "15") Integer pageSize) {
        Response response = new Response();
        //根据登陆用户名查询appid是电压还是局方
        User userlogin = this.getSessionUser();
        String name = userlogin.getName();
        //查询数据库，如果appid是dy就显示电压用户dz显示电阻用户反之显示局方admin显示全部数据
        User byUsername = userService.findByUsername(name);

            List<User> users = userService.queryAllByLimit(user, pageNum, pageSize);
            Map<String, Object> data = new HashMap<>();
            data.put("list", users);
            System.out.println("users" + users);
            data.put("total", users.size());
            return response.success(data);


    }

    @ApiOperation("新增子用户信息")
    @RequestMapping(value = "/insertUser", produces = "application/json")
    public Response insertUser(@RequestParam("username") String username,
                                   @RequestParam("password") String password,
                                   @RequestParam("companyname") String companyname,
                                   @RequestParam("phone") String phone,
                                   @RequestParam("address") String address, @RequestParam("appid") String appid) {
        //判断子用户是否与设备绑定
        if ("".equals(password)||null==password){
            password="123456";
        }
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Proinfo p=new Proinfo();
        p.setUname(companyname);
        p.setEquipment(appid);
        List<Proinfo> proinfoList = proinfoService.queryByu(p);
        boolean flag=false;
        if (proinfoList.size()!=0){
            flag = true;
            Map<String, Object> data = new HashMap<>();
            data.put("flag", flag);
            return response.success(data);
        }
        Response response = new Response();
       // Proinfo proinfo = proinfoService.queryById(Integer.parseInt(appid));
        User user = new User();
        user.setName(username);
        String Md5Pass = new Md5Hash(password).toHex();
        user.setPassword(Md5Pass);
        user.setCompanyName(companyname);
        user.setPhone(phone);
        user.setAddress(address);
        user.setAppid(appid);
        User insert = userService.insert(user);

        List<UserRole> userRoleList = new ArrayList<>();
        UserRole userRole=new UserRole();
        userRole.setGmtCreate(new Date());
        userRole.setRoleId("3");
        userRole.setUserId(insert.getId().toString());
        userRoleList.add(userRole);
        userRoleService.saveBatch(userRoleList);


        //向设备表插入数据
        Proinfo pro=new Proinfo();

        pro.setAppid(appid);
        pro.setUname(companyname);
        pro.setUserid(insert.getId());
        pro.setEquipment(appid);
        pro.setCreatetime(sdf.format(new Date()));
            proinfoService.insert(pro);
        return CommonUtil.isNull(insert) ? response.failure("新增数据出现问题！") : response.success(user);


    }


    //根据用户查询子用户信息
    @ApiOperation(value = "分页查询", notes = "用户管理分页查询", httpMethod = "GET")
    @RequestMapping(value = "/getUserListBychild")
    public Response getUserListBychild(User user, @RequestParam(defaultValue = "1") Integer pageNum
            , @RequestParam(defaultValue = "15") Integer pageSize) {
        //先判断是否包含条件查询字段
       // if(!"".equals(user.getName())||!"".equals(user.getAddress())){}
        boolean flag=false;
        if (null==this.getSessionUser()||"".equals(this.getSessionUser())){
            flag = true;
            Map<String, Object> data = new HashMap<>();
            data.put("flag", flag);
            return response.success(data);
        }
        User sessionUser = this.getSessionUser();
        if(!sessionUser.getName().equals("admin")){
            User userbyid = userService.queryById(sessionUser.getId());
            user.setCompanyName(userbyid.getCompanyName());
        }
        User userid = userService.queryById(user.getId());
        user.setCompanyName(userid.getCompanyName());

        List<User> users = userService.querycompanychild(user,pageNum, pageSize);
        for (User userby : users) {
            if (userby.getAppid().contains("dy")){
                userby.setAppid("电压系统");
            }else if(userby.getAppid().contains("dz")){
                userby.setAppid("电阻系统");
            }else {
                userby.setAppid("局防系统");
            }
        }
        // List<User> users = userService.queryAllByLimit(user, pageNum, pageSize);
        Map<String, Object> data = new HashMap<>();
        data.put("list", users);
        System.out.println("users" + users);
        data.put("total", users.size());
        return response.success(data);


    }




    @ApiOperation(value = "根据用户获取角色数据")
    @GetMapping("/toAssign/{userId}")
    public R toAssign(@PathVariable Integer userId) {
        Map<String, Object> roleMap = roleService.findRoleByUserId(userId);
        return R.ok().data(roleMap);
    }

    @ApiOperation(value = "根据用户分配角色")
    @RequestMapping("/doAssign")
    public R doAssign(@RequestParam String userId, @RequestParam String[] roleId) {
        roleService.saveUserRoleRealtionShip(userId, roleId);
        return R.ok();
    }

    //根据用户名或者公司名称模糊查询
    @ApiOperation(value = "根据用户名或者公司名称模糊查询")
    @RequestMapping("/dolike")
    public R dolike(@RequestParam("username") String username, @RequestParam("companyname") String companyname) {
        userService.findBylike(username, companyname);
        return R.ok();
    }

    //查询所有用户
    @ApiOperation(value = "分页查询", notes = "用户管理查询")
    @RequestMapping(value = "/querycompany")
    public Response querycompany(User user,@RequestParam(defaultValue = "1") Integer pageNum
            , @RequestParam(defaultValue = "10") Integer pageSize) {
        List<User> list = userService.querycompany(user,pageNum, pageSize);
        Map<String, Object> data = new HashMap<>();
        data.put("list", list);
        data.put("total", list.size());

        Response response = new Response();
        return response.success(data);
    }


    //查询用户对应设备数量
    @ApiOperation(value = "查询用户对应设备数量")
    @RequestMapping("/findbycompany")
    public Response findbycompany(@RequestParam("companyname") String companyname) {
        //查詢用户对应的设备数量
        Proinfo proinfo=new Proinfo();
        proinfo.setUname(companyname);
        List<Proinfo> proinfoList = proinfoService.queryByappid(proinfo);
        for (Proinfo proinfos : proinfoList) {
            //获取proinfos中的appid值替换为对应设备数量
            if (proinfos.getAppid().equals("dy")){
                proinfos.setAppid("电压设备数量:"+proinfos.getCount());
            }else if (proinfos.getAppid().equals("dz")){
                proinfos.setAppid("电阻设备数量:"+proinfos.getCount());
            }else {
                proinfos.setAppid("局防设备数量:"+proinfos.getCount());
            }
        }
        Map<String, Object> data = new HashMap<>();
        data.put("proinfo",proinfoList);
        return response.success(data);
    }


    //查询用户对应设备数量
    @ApiOperation(value = "查询用户对应设备数量")
    @RequestMapping("/findbyappid")
    public Response findbyappid(@RequestParam("companyname") String companyname) {
        //查詢用户对应的设备数量
        Proinfo proinfo=new Proinfo();
        proinfo.setUname(companyname);
        List<Proinfo> proinfoList = proinfoService.findbyappid(proinfo);
        Map<String, Object> data = new HashMap<>();
        data.put("proinfo",proinfoList);
        return response.success(data);
    }


    //查询用户chaxun对应设备
    @ApiOperation(value = "查询用户对应设备数量")
    @RequestMapping("/selebyuserid")
    public Response selebyuserid(@RequestParam("id") String id) {
        //查詢用户对应的设备数量
        Proinfo proinfo=new Proinfo();
        proinfo.setUserid(Integer.parseInt(id));
        List<Proinfo> proinfoList = proinfoService.selebyuserid(proinfo);
        Map<String, Object> data = new HashMap<>();
        data.put("proinfo",proinfoList);
        return response.success(data);
    }


}

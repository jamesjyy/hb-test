package cn.best.approveservice.service.impl;

import cn.best.approveservice.common.utils.CommonUtil;
import cn.best.approveservice.dao.TbSysRoleMapper;
import cn.best.approveservice.dao.UserinfoDao;
import cn.best.approveservice.entity.RoleUser;
import cn.best.approveservice.entity.TbSysRole;
import cn.best.approveservice.entity.Userinfo;
import cn.best.approveservice.service.RoleUserService;
import cn.best.approveservice.service.UserinfoService;
import com.github.pagehelper.PageHelper;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import cn.best.approveservice.common.utils.PinyinToolkit;
import java.util.ArrayList;
import java.util.List;

/**
 * 用户表(Userinfo)表服务实现类
 *
 * @author makejava
 * @since 2020-07-15 18:28:46
 */
@Service
public class UserinfoServiceImpl implements UserinfoService {
    @Autowired
    private UserinfoDao userinfoDao;
    @Autowired
    private RoleUserService roleUserService;
    @Autowired
    private TbSysRoleMapper tbSysRoleMapper;
    final Logger logger = LoggerFactory.getLogger(UserinfoServiceImpl.class);
    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @Override
    public Userinfo queryById(Integer id) {
        return this.userinfoDao.queryById(id);
    }

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit  查询条数
     * @return 对象列表
     */
//    @Override
//    public List<Userinfo> queryAllByLimit(int offset, int limit) {
//        return this.userinfoDao.queryAllByLimit(offset, limit);
//    }

    /**
     * 通过条件查询多条数据
     *
     * @param userinfo  实体对象
     * @return 对象列表
     */
    @Override
    public List<Userinfo> queryAll(Userinfo userinfo) {
        return this.userinfoDao.queryAll(userinfo);
    }

    /**
     *@Description 通过用户名查找用户
     *@param username 用户名
     *@return Userinfo对象
     */
    @Override
    public Userinfo findByUsername(String username) {
        return this.userinfoDao.findByUsername(username);
    }

    @Override
    public List<Userinfo> getUserinfoListByPage(Userinfo userinfo, Integer pageNum, Integer pageSize) {
        if(pageNum < 1 || pageSize < 1){
            logger.error("获取用户列表页码出现问题！pageNum={}，pageSize={}",pageNum,pageSize);
            return new ArrayList<>();
        }
        PageHelper.startPage(pageNum,pageSize);
        List<Userinfo> userinfoList=this.userinfoDao.queryUserinfoList(userinfo);
        //添加角色信息
        userinfoList.stream().forEach(e->{
            List<TbSysRole> tbSysRoleList=tbSysRoleMapper.getRoleByUserId(e.getId());
            e.setTbSysRoleList(tbSysRoleList);
        });
        return userinfoList;
    }

    /**
     * 新增数据
     *
     * @param userinfo 实例对象
     * @return 实例对象
     */
    @Override
    public Userinfo insert(Userinfo userinfo) {
        processingUserinfo(userinfo);
        this.userinfoDao.insert(userinfo);
        //添加角色用户中间表
        if(!CommonUtil.isNull(userinfo.getTbSysRoleList())){
            userinfo.getTbSysRoleList().stream().forEach(e->{
                RoleUser roleUser=new RoleUser();
                roleUser.setUserId(userinfo.getId());
                roleUser.setRoleId(e.getRoleId());
                roleUserService.insertRoleUser(roleUser);
            });
        }
        return userinfo;
    }

    /**
     * 处理用户userinfo数据
     * @param userinfo
     * @return
     */
    private void processingUserinfo(Userinfo userinfo){
        userinfo.setAllspelling(PinyinToolkit.cn2Spell(userinfo.getUsername()));//全拼
        userinfo.setFirstspelling(PinyinToolkit.cn2FirstSpell(userinfo.getUsername()));//拼音 首字母
        //密码加密
        String Md5Pass=new Md5Hash(userinfo.getPassword()).toHex();
        userinfo.setPassword(Md5Pass);
        if(CommonUtil.isNull(userinfo.getEnabled())){
            userinfo.setEnabled("1");//启用
        }
    }
    /**
     * 修改数据
     *
     * @param userinfo 实例对象
     * @return 实例对象
     */
    @Override
    public Userinfo update(Userinfo userinfo) {
        //1.删除该用户拥有的角色
        RoleUser roleUser=new RoleUser();
        roleUser.setUserId(userinfo.getId());
        this.roleUserService.deleteRoleUser(roleUser);
        //2.循环添加用户新的角色
        String[] roleids=userinfo.getRoleIds().split(",");
        for (int i = 0; i <roleids.length ; i++) {
            RoleUser roleUser1=new RoleUser();
            roleUser1.setUserId(userinfo.getId());
            roleUser1.setRoleId(Integer.parseInt(roleids[i]));
            this.roleUserService.insertRoleUser(roleUser1);
        }
        processingUserinfo(userinfo);//处理数据
        //3.修改用户信息
        this.userinfoDao.update(userinfo);
        return this.queryById(userinfo.getId());
    }

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(Integer id) {
        RoleUser roleUser=new RoleUser();
        roleUser.setUserId(id);

        return this.userinfoDao.deleteById(id) > 0;
    }
}
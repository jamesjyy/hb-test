package cn.best.approveservice.service.impl;

import cn.best.approveservice.entity.TbSysRole;
import cn.best.approveservice.entity.User;
import cn.best.approveservice.dao.UserDao;
import cn.best.approveservice.entity.Userinfo;
import cn.best.approveservice.service.UserService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * (User)表服务实现类
 *
 * @author makejava
 * @since 2021-04-08 10:17:59
 */
@Service
public class UserServiceImpl implements UserService {
    @Resource
    private UserDao userDao;
    final Logger logger = LoggerFactory.getLogger(UserinfoServiceImpl.class);


    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @Override
    public User queryById(Integer id) {
        return this.userDao.queryById(id);
    }

    @Override
    public User findByUsername(String username) {
        return userDao.findByUsername(username);
    }

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit  查询条数
     * @return 对象列表
     */
    @Override
    public List<User> queryAllByLimit(User user,int offset, int limit) {
        PageHelper.startPage(offset, limit);
        return this.userDao.queryAllByLimit(user);
    }

    @Override
    public List<User> getUserListByPage(User userinfo, Integer pageNum, Integer pageSize) {
        if(pageNum < 1 || pageSize < 1){
            logger.error("获取用户列表页码出现问题！pageNum={}，pageSize={}",pageNum,pageSize);
            return new ArrayList<>();
        }
        PageHelper.startPage(pageNum,pageSize);
        List<User> userinfoList=this.userDao.queryUserinfoList(userinfo);

        return userinfoList;
    }

    @Override
    public List<User> querycompany(User user,int offset, int limit) {
        PageHelper.startPage(offset, limit);
        return userDao.querycompany(user);
    }

    @Override
    public List<User> querycompanychild(User user,int offset, int limit) {
        PageHelper.startPage(offset, limit);
        return userDao.querycompanychild(user);
    }

    /**
     * 新增数据
     *
     * @param user 实例对象
     * @return 实例对象
     */
    @Override
    public User insert(User user) {

        this.userDao.insert(user);

        return user;
    }

    /**
     * 修改数据
     *
     * @param user 实例对象
     * @return 实例对象
     */
    @Override
    public User update(User user) {
        this.userDao.update(user);
        return user;
    }

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(Integer id) {
        return this.userDao.deleteById(id) > 0;
    }

    @Override
    public void findBylike(String username, String companyname) {
        List<User> users = userDao.selectList(new QueryWrapper<User>().like("username", username).or().like("companyname", companyname));


    }

    @Override
    public List<User> queryAllBydy(User user, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        return this.userDao.queryAllBydy(user);
    }

    @Override
    public List<User> queryAllBydz(User user, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        return this.userDao.queryAllBydz(user);
    }

    @Override
    public List<User> queryAllByjf(User user, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        return this.userDao.queryAllByjf(user);
    }

    @Override
    public List<User> findbycompany(String companyname) {
        return userDao.findbycompany(companyname);
    }
}

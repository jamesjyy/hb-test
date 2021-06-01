package cn.best.approveservice.service.impl;

import cn.best.approveservice.entity.UserProinfo;
import cn.best.approveservice.dao.UserProinfoDao;
import cn.best.approveservice.service.UserProinfoService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * (UserProinfo)表服务实现类
 *
 * @author makejava
 * @since 2021-04-08 10:18:00
 */
@Service("userProinfoService")
public class UserProinfoServiceImpl implements UserProinfoService {
    @Resource
    private UserProinfoDao userProinfoDao;

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @Override
    public UserProinfo queryById(Integer id) {
        return this.userProinfoDao.queryById(id);
    }

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit  查询条数
     * @return 对象列表
     */
    @Override
    public List<UserProinfo> queryAllByLimit(int offset, int limit) {
        return this.userProinfoDao.queryAllByLimit(offset, limit);
    }

    /**
     * 新增数据
     *
     * @param userProinfo 实例对象
     * @return 实例对象
     */
    @Override
    public UserProinfo insert(UserProinfo userProinfo) {
        this.userProinfoDao.insert(userProinfo);
        return userProinfo;
    }

    /**
     * 修改数据
     *
     * @param userProinfo 实例对象
     * @return 实例对象
     */
    @Override
    public UserProinfo update(UserProinfo userProinfo) {
        this.userProinfoDao.update(userProinfo);
        return this.queryById(userProinfo.getId());
    }

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(Integer id) {
        return this.userProinfoDao.deleteById(id) > 0;
    }
}

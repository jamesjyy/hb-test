package cn.best.approveservice.service.impl;

import cn.best.approveservice.entity.Authority;
import cn.best.approveservice.dao.AuthorityDao;
import cn.best.approveservice.service.AuthorityService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * (Authority)表服务实现类
 *
 * @author makejava
 * @since 2021-04-08 10:17:55
 */
@Service("authorityService")
public class AuthorityServiceImpl implements AuthorityService {
    @Resource
    private AuthorityDao authorityDao;

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @Override
    public Authority queryById(Integer id) {
        return this.authorityDao.queryById(id);
    }

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit  查询条数
     * @return 对象列表
     */
    @Override
    public List<Authority> queryAllByLimit(int offset, int limit) {
        return this.authorityDao.queryAllByLimit(offset, limit);
    }

    /**
     * 新增数据
     *
     * @param authority 实例对象
     * @return 实例对象
     */
    @Override
    public Authority insert(Authority authority) {
        this.authorityDao.insert(authority);
        return authority;
    }

    /**
     * 修改数据
     *
     * @param authority 实例对象
     * @return 实例对象
     */
    @Override
    public Authority update(Authority authority) {
        this.authorityDao.update(authority);
        return this.queryById(authority.getId());
    }

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(Integer id) {
        return this.authorityDao.deleteById(id) > 0;
    }
}

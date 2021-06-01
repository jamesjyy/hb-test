package cn.best.approveservice.service.impl;

import cn.best.approveservice.entity.RoleAuthority;
import cn.best.approveservice.dao.RoleAuthorityDao;
import cn.best.approveservice.service.RoleAuthorityService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * (RoleAuthority)表服务实现类
 *
 * @author makejava
 * @since 2021-04-08 10:17:59
 */
@Service("roleAuthorityService")
public class RoleAuthorityServiceImpl implements RoleAuthorityService {
    @Resource
    private RoleAuthorityDao roleAuthorityDao;

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @Override
    public RoleAuthority queryById(Integer id) {
        return this.roleAuthorityDao.queryById(id);
    }

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit  查询条数
     * @return 对象列表
     */
    @Override
    public List<RoleAuthority> queryAllByLimit(int offset, int limit) {
        return this.roleAuthorityDao.queryAllByLimit(offset, limit);
    }

    /**
     * 新增数据
     *
     * @param roleAuthority 实例对象
     * @return 实例对象
     */
    @Override
    public RoleAuthority insert(RoleAuthority roleAuthority) {
        this.roleAuthorityDao.insert(roleAuthority);
        return roleAuthority;
    }

    /**
     * 修改数据
     *
     * @param roleAuthority 实例对象
     * @return 实例对象
     */
    @Override
    public RoleAuthority update(RoleAuthority roleAuthority) {
        this.roleAuthorityDao.update(roleAuthority);
        return this.queryById(roleAuthority.getId());
    }

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(Integer id) {
        return this.roleAuthorityDao.deleteById(id) > 0;
    }
}

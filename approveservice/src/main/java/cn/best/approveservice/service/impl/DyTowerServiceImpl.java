package cn.best.approveservice.service.impl;

import cn.best.approveservice.entity.DyTower;
import cn.best.approveservice.dao.DyTowerDao;
import cn.best.approveservice.service.DyTowerService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * (DyTower)表服务实现类
 *
 * @author makejava
 * @since 2021-04-08 10:17:57
 */
@Service("dyTowerService")
public class DyTowerServiceImpl implements DyTowerService {
    @Resource
    private DyTowerDao dyTowerDao;

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @Override
    public DyTower queryById(Integer id) {
        return this.dyTowerDao.queryById(id);
    }

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit  查询条数
     * @return 对象列表
     */
    @Override
    public List<DyTower> queryAllByLimit(int offset, int limit) {
        return this.dyTowerDao.queryAllByLimit(offset, limit);
    }

    /**
     * 新增数据
     *
     * @param dyTower 实例对象
     * @return 实例对象
     */
    @Override
    public DyTower insert(DyTower dyTower) {
        this.dyTowerDao.insert(dyTower);
        return dyTower;
    }

    /**
     * 修改数据
     *
     * @param dyTower 实例对象
     * @return 实例对象
     */
    @Override
    public DyTower update(DyTower dyTower) {
        this.dyTowerDao.update(dyTower);
        return this.queryById(dyTower.getId());
    }

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(Integer id) {
        return this.dyTowerDao.deleteById(id) > 0;
    }
}

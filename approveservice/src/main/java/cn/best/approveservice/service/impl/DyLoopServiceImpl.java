package cn.best.approveservice.service.impl;

import cn.best.approveservice.entity.DyLoop;
import cn.best.approveservice.dao.DyLoopDao;
import cn.best.approveservice.service.DyLoopService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * (DyLoop)表服务实现类
 *
 * @author makejava
 * @since 2021-04-08 10:17:54
 */
@Service("dyLoopService")
public class DyLoopServiceImpl implements DyLoopService {
    @Resource
    private DyLoopDao dyLoopDao;

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @Override
    public DyLoop queryById(Integer id) {
        return this.dyLoopDao.queryById(id);
    }

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit  查询条数
     * @return 对象列表
     */
    @Override
    public List<DyLoop> queryAllByLimit(int offset, int limit) {
        return this.dyLoopDao.queryAllByLimit(offset, limit);
    }

    /**
     * 新增数据
     *
     * @param dyLoop 实例对象
     * @return 实例对象
     */
    @Override
    public DyLoop insert(DyLoop dyLoop) {
        this.dyLoopDao.insert(dyLoop);
        return dyLoop;
    }

    /**
     * 修改数据
     *
     * @param dyLoop 实例对象
     * @return 实例对象
     */
    @Override
    public DyLoop update(DyLoop dyLoop) {
        this.dyLoopDao.update(dyLoop);
        return this.queryById(dyLoop.getId());
    }

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(Integer id) {
        return this.dyLoopDao.deleteById(id) > 0;
    }
}

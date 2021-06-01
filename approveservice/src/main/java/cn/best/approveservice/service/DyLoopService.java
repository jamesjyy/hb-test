package cn.best.approveservice.service;

import cn.best.approveservice.entity.DyLoop;

import java.util.List;

/**
 * (DyLoop)表服务接口
 *
 * @author makejava
 * @since 2021-04-08 10:17:54
 */
public interface DyLoopService {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    DyLoop queryById(Integer id);

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit  查询条数
     * @return 对象列表
     */
    List<DyLoop> queryAllByLimit(int offset, int limit);

    /**
     * 新增数据
     *
     * @param dyLoop 实例对象
     * @return 实例对象
     */
    DyLoop insert(DyLoop dyLoop);

    /**
     * 修改数据
     *
     * @param dyLoop 实例对象
     * @return 实例对象
     */
    DyLoop update(DyLoop dyLoop);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    boolean deleteById(Integer id);

}

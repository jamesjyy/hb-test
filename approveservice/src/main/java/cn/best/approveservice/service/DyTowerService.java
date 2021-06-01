package cn.best.approveservice.service;

import cn.best.approveservice.entity.DyTower;

import java.util.List;

/**
 * (DyTower)表服务接口
 *
 * @author makejava
 * @since 2021-04-08 10:17:56
 */
public interface DyTowerService {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    DyTower queryById(Integer id);

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit  查询条数
     * @return 对象列表
     */
    List<DyTower> queryAllByLimit(int offset, int limit);

    /**
     * 新增数据
     *
     * @param dyTower 实例对象
     * @return 实例对象
     */
    DyTower insert(DyTower dyTower);

    /**
     * 修改数据
     *
     * @param dyTower 实例对象
     * @return 实例对象
     */
    DyTower update(DyTower dyTower);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    boolean deleteById(Integer id);

}

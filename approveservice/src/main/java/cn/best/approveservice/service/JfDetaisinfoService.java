package cn.best.approveservice.service;

import cn.best.approveservice.entity.JfDetaisinfo;

import java.util.List;

/**
 * (JfDetaisinfo)表服务接口
 *
 * @author makejava
 * @since 2021-04-08 10:17:53
 */
public interface JfDetaisinfoService {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    JfDetaisinfo queryById(Integer id);

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit  查询条数
     * @return 对象列表
     */
    List<JfDetaisinfo> queryAllByLimit(int offset, int limit);

    /**
     * 新增数据
     *
     * @param jfDetaisinfo 实例对象
     * @return 实例对象
     */
    JfDetaisinfo insert(JfDetaisinfo jfDetaisinfo);

    /**
     * 修改数据
     *
     * @param jfDetaisinfo 实例对象
     * @return 实例对象
     */
    JfDetaisinfo update(JfDetaisinfo jfDetaisinfo);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    boolean deleteById(Integer id);

}

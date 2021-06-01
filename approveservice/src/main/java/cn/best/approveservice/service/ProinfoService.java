package cn.best.approveservice.service;

import cn.best.approveservice.entity.Proinfo;

import java.util.List;

/**
 * (Proinfo)表服务接口
 *
 * @author makejava
 * @since 2021-04-08 10:17:57
 */
public interface ProinfoService {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    Proinfo queryById(Integer id);

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit  查询条数
     * @return 对象列表
     */
    List<Proinfo> queryAllByLimit(int offset, int limit);


    List<Proinfo> queryByappid(Proinfo proinfo);

    /**
     * 新增数据
     *
     * @param proinfo 实例对象
     * @return 实例对象
     */
    Proinfo insert(Proinfo proinfo);

    /**
     * 修改数据
     *
     * @param proinfo 实例对象
     * @return 实例对象
     */
    Proinfo update(Proinfo proinfo);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    boolean deleteById(Integer id);

    List<Proinfo> findbyappid(Proinfo proinfo);

    List<Proinfo> queryByu(Proinfo p);

    List<Proinfo> selebyuserid(Proinfo proinfo);
}

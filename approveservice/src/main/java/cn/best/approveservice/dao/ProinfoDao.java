package cn.best.approveservice.dao;

import cn.best.approveservice.entity.Proinfo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * (Proinfo)表数据库访问层
 *
 * @author makejava
 * @since 2021-04-08 10:17:57
 */
public interface ProinfoDao {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    Proinfo queryById(Integer id);

    /**
     * 查询指定行数据
     *
     * @param offset 查询起始位置
     * @param limit  查询条数
     * @return 对象列表
     */
    List<Proinfo> queryAllByLimit(@Param("offset") int offset, @Param("limit") int limit);


    /**
     * 通过实体作为筛选条件查询
     *
     * @param proinfo 实例对象
     * @return 对象列表
     */
    List<Proinfo> queryAll(Proinfo proinfo);

//查询用户下面对应设备数量
    List<Proinfo> queryByappid(Proinfo proinfo);

    //查询子用户是否绑定设备
    List<Proinfo> queryByu(Proinfo proinfo);

    /**
     * 新增数据
     *
     * @param proinfo 实例对象
     * @return 影响行数
     */
    int insert(Proinfo proinfo);

    /**
     * 批量新增数据（MyBatis原生foreach方法）
     *
     * @param entities List<Proinfo> 实例对象列表
     * @return 影响行数
     */
    int insertBatch(@Param("entities") List<Proinfo> entities);

    /**
     * 批量新增或按主键更新数据（MyBatis原生foreach方法）
     *
     * @param entities List<Proinfo> 实例对象列表
     * @return 影响行数
     */
    int insertOrUpdateBatch(@Param("entities") List<Proinfo> entities);

    /**
     * 修改数据
     *
     * @param proinfo 实例对象
     * @return 影响行数
     */
    int update(Proinfo proinfo);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 影响行数
     */
    int deleteById(Integer id);

    List<Proinfo> findbyappid(Proinfo proinfo);

    List<Proinfo> selebyuserid(Proinfo proinfo);
}


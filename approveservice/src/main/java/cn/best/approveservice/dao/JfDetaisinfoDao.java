package cn.best.approveservice.dao;

import cn.best.approveservice.entity.JfDetaisinfo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * (JfDetaisinfo)表数据库访问层
 *
 * @author makejava
 * @since 2021-04-08 10:17:53
 */
public interface JfDetaisinfoDao {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    JfDetaisinfo queryById(Integer id);

    /**
     * 查询指定行数据
     *
     * @param offset 查询起始位置
     * @param limit  查询条数
     * @return 对象列表
     */
    List<JfDetaisinfo> queryAllByLimit(@Param("offset") int offset, @Param("limit") int limit);


    /**
     * 通过实体作为筛选条件查询
     *
     * @param jfDetaisinfo 实例对象
     * @return 对象列表
     */
    List<JfDetaisinfo> queryAll(JfDetaisinfo jfDetaisinfo);

    /**
     * 新增数据
     *
     * @param jfDetaisinfo 实例对象
     * @return 影响行数
     */
    int insert(JfDetaisinfo jfDetaisinfo);

    /**
     * 批量新增数据（MyBatis原生foreach方法）
     *
     * @param entities List<JfDetaisinfo> 实例对象列表
     * @return 影响行数
     */
    int insertBatch(@Param("entities") List<JfDetaisinfo> entities);

    /**
     * 批量新增或按主键更新数据（MyBatis原生foreach方法）
     *
     * @param entities List<JfDetaisinfo> 实例对象列表
     * @return 影响行数
     */
    int insertOrUpdateBatch(@Param("entities") List<JfDetaisinfo> entities);

    /**
     * 修改数据
     *
     * @param jfDetaisinfo 实例对象
     * @return 影响行数
     */
    int update(JfDetaisinfo jfDetaisinfo);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 影响行数
     */
    int deleteById(Integer id);

}


package cn.best.approveservice.dao;

import cn.best.approveservice.entity.JfProtype;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * (JfProtype)表数据库访问层
 *
 * @author makejava
 * @since 2021-04-08 10:17:57
 */
public interface JfProtypeDao {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    JfProtype queryById(Integer id);

    /**
     * 查询指定行数据
     *
     * @param offset 查询起始位置
     * @param limit  查询条数
     * @return 对象列表
     */
    List<JfProtype> queryAllByLimit(@Param("offset") int offset, @Param("limit") int limit);


    /**
     * 通过实体作为筛选条件查询
     *
     * @param jfProtype 实例对象
     * @return 对象列表
     */
    List<JfProtype> queryAll(JfProtype jfProtype);

    /**
     * 新增数据
     *
     * @param jfProtype 实例对象
     * @return 影响行数
     */
    int insert(JfProtype jfProtype);

    /**
     * 批量新增数据（MyBatis原生foreach方法）
     *
     * @param entities List<JfProtype> 实例对象列表
     * @return 影响行数
     */
    int insertBatch(@Param("entities") List<JfProtype> entities);

    /**
     * 批量新增或按主键更新数据（MyBatis原生foreach方法）
     *
     * @param entities List<JfProtype> 实例对象列表
     * @return 影响行数
     */
    int insertOrUpdateBatch(@Param("entities") List<JfProtype> entities);

    /**
     * 修改数据
     *
     * @param jfProtype 实例对象
     * @return 影响行数
     */
    int update(JfProtype jfProtype);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 影响行数
     */
    int deleteById(Integer id);

}


package cn.best.approveservice.dao;

import cn.best.approveservice.entity.JfTevdatainfo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * (JfTevdatainfo)表数据库访问层
 *
 * @author makejava
 * @since 2021-04-23 10:36:41
 */
public interface JfTevdatainfoDao {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    JfTevdatainfo queryById(Integer id);

    /**
     * 查询指定行数据
     *
     * @return 对象列表
     */
    List<JfTevdatainfo> queryAllByLimit(Integer userid);
//    List<JfTevdatainfo> queryAllByLimit();

    /**
     * 通过实体作为筛选条件查询
     *
     * @param jfTevdatainfo 实例对象
     * @return 对象列表
     */
    List<JfTevdatainfo> queryAll(JfTevdatainfo jfTevdatainfo);

    /**
     * 新增数据
     *
     * @param jfTevdatainfo 实例对象
     * @return 影响行数
     */
    int insert(JfTevdatainfo jfTevdatainfo);

    /**
     * 批量新增数据（MyBatis原生foreach方法）
     *
     * @param entities List<JfTevdatainfo> 实例对象列表
     * @return 影响行数
     */
    int insertBatch(@Param("entities") List<JfTevdatainfo> entities);

    /**
     * 批量新增或按主键更新数据（MyBatis原生foreach方法）
     *
     * @param entities List<JfTevdatainfo> 实例对象列表
     * @return 影响行数
     */
    int insertOrUpdateBatch(@Param("entities") List<JfTevdatainfo> entities);

    /**
     * 修改数据
     *
     * @param jfTevdatainfo 实例对象
     * @return 影响行数
     */
    int update(JfTevdatainfo jfTevdatainfo);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 影响行数
     */
    int deleteById(Integer id);

    /**
     * 根据UserId进行查询
     * @return
     */
    List<JfTevdatainfo> queryByUserId(@Param("userid") Integer userid);
}


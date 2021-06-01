package cn.best.approveservice.dao;

import cn.best.approveservice.entity.DyDatainfo;
import cn.best.approveservice.entity.JfDatainfo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * (JfDatainfo)表数据库访问层
 *
 * @author makejava
 * @since 2021-04-20 14:02:07
 */
@Repository
public interface JfDatainfoDao {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    JfDatainfo queryById(Integer id);

    /**
     * 查询指定行数据
     *
     * @return 对象列表
     */
    public List<JfDatainfo> queryAllByLimit(JfDatainfo jfDatainfo);


    /**
     * 通过实体作为筛选条件查询
     *
     * @param jfDatainfo 实例对象
     * @return 对象列表
     */
    List<JfDatainfo> queryAll(JfDatainfo jfDatainfo);

    /**
     * 新增数据
     *
     * @param jfDatainfo 实例对象
     * @return 影响行数
     */
    int insert(JfDatainfo jfDatainfo);

    /**
     * 批量新增数据（MyBatis原生foreach方法）
     *
     * @param entities List<JfDatainfo> 实例对象列表
     * @return 影响行数
     */
    int insertBatch(@Param("entities") List<JfDatainfo> entities);

    /**
     * 批量新增或按主键更新数据（MyBatis原生foreach方法）
     *
     * @param entities List<JfDatainfo> 实例对象列表
     * @return 影响行数
     */
    int insertOrUpdateBatch(@Param("entities") List<JfDatainfo> entities);

    /**
     * 修改数据
     *
     * @param jfDatainfo 实例对象
     * @return 影响行数
     */
    int update(JfDatainfo jfDatainfo);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 影响行数
     */
    int deleteById(Integer id);

    /**
     * 故障列表
     *
     * @param
     * @return
     */
    List<JfDatainfo> searchBreakDownList(Map<String,String> params);

    /**
     * 查询故障列表一级列表
     * @param companyName
     * @return
     */
    List<Map> selectCompanyName(@Param("companyName")String companyName);

    /**
     * 查询故障列表二级列表
     * @param companyName
     * @return
     */
    List<Map> selectAreaName(@Param("companyName")String companyName, @Param("areaName")String areaName);

    //新增文件
    public String fileinsert(List<JfDatainfo> jfDatainfo);

}


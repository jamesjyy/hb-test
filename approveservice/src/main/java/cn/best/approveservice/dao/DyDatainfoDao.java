package cn.best.approveservice.dao;

import cn.best.approveservice.entity.*;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * (DyDatainfo)表数据库访问层
 *
 * @author makejava
 * @since 2021-04-08 10:17:54
 */
@Repository
public interface DyDatainfoDao {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    DyDatainfo queryById(Integer id);

    /**
     * 查询指定行数据
     *
     * @param offset 查询起始位置
     * @param limit  查询条数
     * @return 对象列表
     */
    public List<DyDatainfo> queryAllByLimit(DyDatainfo dyDatainfo);

    int querycountloopname(Integer id);

    int querycounttower(String loopname);

    public List<User> AdminqueryAllByLimit(User user);

    public List<DyDatainfo> queryloopAllByLimit(String companyName,String loopname);

    /**
     * 通过实体作为筛选条件查询
     *
     * @param dyDatainfo 实例对象
     * @return 对象列表
     */
    List<DyDatainfo> queryAll(DyDatainfo dyDatainfo);

    /**
     * 新增数据
     *
     * @param dyDatainfo 实例对象
     * @return 影响行数
     */
    int insert(DyDatainfo dyDatainfo);

    /**
     * 批量新增数据（MyBatis原生foreach方法）
     *
     * @param entities List<DyDatainfo> 实例对象列表
     * @return 影响行数
     */
    int insertBatch(@Param("entities") List<DyDatainfo> entities);

    /**
     * 批量新增或按主键更新数据（MyBatis原生foreach方法）
     *
     * @param entities List<DyDatainfo> 实例对象列表
     * @return 影响行数
     */
    int insertOrUpdateBatch(@Param("entities") List<DyDatainfo> entities);

    /**
     * 修改数据
     *
     * @param dyDatainfo 实例对象
     * @return 影响行数
     */
    int update(DyDatainfo dyDatainfo);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 影响行数
     */
    int deleteById(Integer id);

    /**
     * 通过userId来进行删除
     *
     * @param id
     * @return
     */
    int deleteByUserId(Integer id);


    /**
     * 故障列表查询
     *
     * @return
     */
    List<DyDatainfo> queryAnalyseAll(@Param("userid") Integer userid, @Param("loopname") String loopname);

    /**
     * 添加数据
     *
     * @param dyDatainfo 实例对象
     * @return 影响行数
     */
    int insertData(DyDatainfo dyDatainfo);

    /**
     * 根据
     *
     * @param userid
     * @return
     */
    List<DyDatainfo> queryInfo(@Param("userid") Integer userid);


    /**
     * 查询线路下的详细数据
     *
     * @param userid
     * @param loopidtime
     * @return
     */
    List<DataInfo> querytowerData(@Param("userid") Integer userid, @Param("loopname") String loopidtime);

    /**
     * 查询所有的杆塔数据详情
     *
     * @param userid
     * @param loopname
     * @param towernum
     * @return
     */
    List<DyDatainfo> queryDyChildAll(@Param("userid") String userid, @Param("loopname") String loopname, @Param("towernum") String towernum);

    /**
     * 查询下拉框数据
     *
     * @param userid
     * @param loopidtime
     * @param towernum
     * @return
     */
    List<DyDatainfo> queryChildPhase(@Param("userid") Integer userid, @Param("loopname") String loopidtime, @Param("towernum") Integer towernum);

    /**
     * 查询所有数据
     *
     * @param id
     * @param loopname
     * @return
     */
    List<DyDatainfo> queryInfoAll(@Param("userid") Integer userid);

    List<DyDatainfo> queryInfoLT(@Param("loopname") String loopname, @Param("towernum") String towernum);

    /**
     * 按照公司名称和userId来进行删除
     *
     * @param userId
     * @param loopidtime
     */
    void deleteByLoopId(@Param("userid") Integer userid, @Param("loopname") String loopname);

    /**
     * 按照线路名称和userId,杆塔名称来进行删除
     *
     * @param userId
     * @param loopname
     * @param towernum
     */
    void deleteOne(@Param("userid") Integer userId, @Param("loopname") String loopname, @Param("towernum") String towernum);

    /**
     * 查询数据
     *
     * @param userid
     * @param loopname
     * @param towernum
     * @param phase
     * @param strand
     * @return
     */
    List<DyDatainfo> queryTowerData(@Param("userid") Integer userid, @Param("loopname") String loopname, @Param("towernum") String towernum, @Param("phase") String phase, @Param("strand") String strand);

    /**
     * 查询串号
     *
     * @param toString
     * @param loopidtime
     * @param towernum
     * @param strand
     * @return
     */
    List<DyDatainfo> queryDyBunch(@Param("userid") String userid, @Param("loopname") String loopname, @Param("towernum") String towernum, @Param("phase") String phase);


    void deleteByTowernumId(@Param("userid")Integer userId, @Param("loopname")String loopname, @Param("towernum")String towernum);
}

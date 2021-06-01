package cn.best.approveservice.dao;

import cn.best.approveservice.entity.DzData;
import cn.best.approveservice.entity.DzDataMag;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * (DzData)表数据库访问层
 *
 * @author makejava
 * @since 2021-04-08 10:17:55
 */
@Repository
public interface DzDataDao {

    /**
     * 通过ID查询单条数据
     *
     * @return 实例对象
     */
    List<DzData> queryById(@Param("userid") Integer userid);

    /**
     * 查询指定行数据
     *
     * @return 对象列表
     */
    List<DzData> queryAllByLimit(Integer userid);


    /**
     * 通过实体作为筛选条件查询
     *
     * @param dzData 实例对象
     * @return 对象列表
     */
    List<DzData> queryAll(DzData dzData);

    /**
     * 新增数据
     *
     * @param dzData 实例对象
     * @return 影响行数
     */
    int insert(DzData dzData);

    /**
     * 批量新增数据（MyBatis原生foreach方法）
     *
     * @param entities List<DzData> 实例对象列表
     * @return 影响行数
     */
    int insertBatch(@Param("entities") List<DzData> entities);

    /**
     * 批量新增或按主键更新数据（MyBatis原生foreach方法）
     *
     * @param entities List<DzData> 实例对象列表
     * @return 影响行数
     */
    int insertOrUpdateBatch(@Param("entities") List<DzData> entities);

    /**
     * 修改数据
     *
     * @param dzData 实例对象
     * @return 影响行数
     */
    int update(DzData dzData);

    /**
     * 通过主键删除数据
     *
     * @param userid 主键
     * @return 影响行数
     */
    int deleteById(@Param("userid") Integer userid);


    /**
     * 查询所有userId
     * @return
     */
    List<Integer> queryAllUserId(@Param("companyName") String companyName);

    /**
     * 查询所有线路名称
     * @param userids
     * @return
     */
    List<String> queryLoop(Integer userids);

    /**
     * 根据userId查询对应的线路名称
     * @param userid
     * @return
     */
    List<DzData> queryLoopIdNumber(@Param("userid") Integer userid, @Param("loopidtime") String loopidtime);

    /**
     * 根据id和线路名称查询杆塔数量
     * @param userid
     * @param loopidtime
     * @return
     */
    Integer querytowerNumber(@Param("userid") Integer userid, @Param("loopidtime") String loopidtime);

    /**
     * 根据userId进行删除
     * @param userId
     */
    void deleteByUserId(@Param("userid") Integer userId, @Param("loopidtime") String loopidtime);

    /**
     * 根据userId,线路名称,杆塔名称来进行删除
     * @param userId
     * @param loopidtime
     * @param towernum
     */
    void deleteOne(@Param("userid") Integer userId, @Param("loopidtime") String loopidtime, @Param("towernum") String towernum);

}


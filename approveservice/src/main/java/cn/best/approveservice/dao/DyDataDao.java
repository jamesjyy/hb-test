package cn.best.approveservice.dao;

import cn.best.approveservice.entity.DyData;
import cn.best.approveservice.entity.DyDataMag;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * (DyData)表数据库访问层
 *
 * @author makejava
 * @since 2021-04-08 10:17:55
 */
@Repository
public interface DyDataDao {

    /**
     * 通过ID查询单条数据
     *
     * @return 实例对象
     */
    List<DyData> queryById(@Param("userid") Integer userid);

    /**
     * 查询指定行数据
     *
     * @return 对象列表
     */
    List<DyData> queryAllByLimit(Integer userid);


    /**
     * 通过实体作为筛选条件查询
     *
     * @param dyData 实例对象
     * @return 对象列表
     */
    List<DyData> queryAll(DyData dyData);

    /**
     * 新增数据
     *
     * @param dyData 实例对象
     * @return 影响行数
     */
    int insert(DyData dyData);

    /**
     * 批量新增数据（MyBatis原生foreach方法）
     *
     * @param entities List<DyData> 实例对象列表
     * @return 影响行数
     */
    int insertBatch(@Param("entities") List<DyData> entities);

    /**
     * 批量新增或按主键更新数据（MyBatis原生foreach方法）
     *
     * @param entities List<DyData> 实例对象列表
     * @return 影响行数
     */
    int insertOrUpdateBatch(@Param("entities") List<DyData> entities);

    /**
     * 修改数据
     *
     * @param dyData 实例对象
     * @return 影响行数
     */
    int update(DyData dyData);

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
    List<DyData> queryLoopIdNumber(@Param("userid") Integer userid, @Param("loopidtime") String loopidtime);

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


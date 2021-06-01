package cn.best.approveservice.dao;

import cn.best.approveservice.entity.DyDatainfo;
import cn.best.approveservice.entity.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import io.swagger.models.auth.In;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * (User)表数据库访问层
 *
 * @author makejava
 * @since 2021-04-08 10:17:59
 */
@Repository
public interface UserDao extends BaseMapper<User> {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    User queryById(Integer id);

    List<User> findbycompany(String companyname);

    User findByUsername(String username);


    List<User> querycompany(User user);

    List<User> querycompanychild(User user);

    List<User> queryUserinfoList(User user);


    /**
     * 查询指定行数据
     *
     * @return 对象列表
     */
    List<User> queryAllByLimit(User user);


    /**
     * 通过实体作为筛选条件查询
     *
     * @param user 实例对象
     * @return 对象列表
     */
    List<User> queryAll(User user);

    //电压
    List<User> AdminqueryAllByLimit(User user);

    //电阻
    List<User> DzAdminqueryAllByLimit(User user);

    //电压
    int querycountloopname(Integer id);

    //电阻
    int Dzquerycountloopname(Integer id);

    //电压
    List<User> PtuserqueryAllByLimit(User user);

    //电阻
    List<User> DzPtuserqueryAllByLimit(User user);

    /**
     * 新增数据
     *
     * @param user 实例对象
     * @return 影响行数
     */
    int insert(User user);

    /**
     * 批量新增数据（MyBatis原生foreach方法）
     *
     * @param entities List<User> 实例对象列表
     * @return 影响行数
     */
    int insertBatch(@Param("entities") List<User> entities);

    /**
     * 批量新增或按主键更新数据（MyBatis原生foreach方法）
     *
     * @param entities List<User> 实例对象列表
     * @return 影响行数
     */
    int insertOrUpdateBatch(@Param("entities") List<User> entities);

    /**
     * 修改数据
     *
     * @param user 实例对象
     * @return 影响行数
     */
    int update(User user);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 影响行数
     */
    int deleteById(Integer id);

    List<User> queryAllBydy(User user);

    List<User> queryAllBydz(User user);

    List<User> queryAllByjf(User user);

    /**
     * 查询所有的公司名称
     *
     * @param userid
     * @return
     */
    User queryCompanyAll(@Param("userid") Integer userid);


    List<Integer> queryCompanyNameAll(@Param("companyName") String companyName);

    Integer queryRoleId(@Param("userid") Integer userid);

    List<Integer> findUserId(@Param("companyName") String companyName);

    List<Integer> qureyUserId(@Param("userid") Integer userid);


    List<Integer> qureyChildUserId(@Param("userid") String userid, @Param("companyname") String companyname, @Param("loopname") String loopname);

    List<Integer> queryChildPhase(@Param("companyname") String companyname);

    User queryDzCompanyAll(@Param("userid") Integer userid);

    List<Integer> qureyDzChildUserId(@Param("userid") String userid, @Param("companyname") String companyname, @Param("loopname") String loopname);

    List<Integer> queryDzChildPhase(@Param("userid") Integer userid, @Param("loopname") String loopname, @Param("towernum") String towernum);

    /**
     * 查询拥有局放设备对应的公司名称
     *
     * @return
     */
    List<User> qureyCompanyAll(@Param("companyName") String companyName);

    /**
     * 根据设备来查询用户
     *
     * @return
     */
    List<User> queryDyUserAll(@Param("companyName")String companyname);

    /**
     * 根据设备来进行查询
     *
     * @return
     */
    List<User> queryDzUserAll(@Param("companyName") String companyname);
}


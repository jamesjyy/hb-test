package cn.best.approveservice.dao;

import cn.best.approveservice.entity.Userinfo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 用户表(Userinfo)表数据库访问层
 *
 * @author makejava
 * @since 2020-07-15 18:28:45
 */
@Repository
public interface UserinfoDao {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    Userinfo queryById(Integer id);

    /**
     * 查询指定行数据
     *
     * @param offset 查询起始位置
     * @param limit  查询条数
     * @return 对象列表
     */
    List<Userinfo> queryAllByLimit(@Param("offset") int offset, @Param("limit") int limit);

    /**
     * 多条件查询
     * @param userinfo
     * @return
     */
    List<Userinfo> queryUserinfoList(Userinfo userinfo);
    /**
     * 通过实体作为筛选条件查询
     *
     * @param userinfo 实例对象
     * @return 对象列表
     */
    List<Userinfo> queryAll(Userinfo userinfo);

    /**
     *@Description 通过用户名查找用户
     *@param username 用户名
     *@return Userinfo对象
     */
    Userinfo findByUsername(String username);

    /**
     * 新增数据
     *
     * @param userinfo 实例对象
     * @return 影响行数
     */
    int insert(Userinfo userinfo);

    /**
     * 修改数据
     *
     * @param userinfo 实例对象
     * @return 影响行数
     */
    int update(Userinfo userinfo);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 影响行数
     */
    int deleteById(Integer id);

}
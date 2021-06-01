package cn.best.approveservice.service;

import cn.best.approveservice.entity.Userinfo;

import java.util.List;

/**
 * 用户表(Userinfo)表服务接口
 *
 * @author makejava
 * @since 2020-07-15 18:28:46
 */
public interface UserinfoService {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    Userinfo queryById(Integer id);

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit  查询条数
     * @return 对象列表
     */
//    List<Userinfo> queryAllByLimit(int offset, int limit);

    /**
     * 通过条件查询多条数据
     *
     * @param userinfo  实体对象
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
     * 分页查询用户
     * @param userinfo
     * @return
     */
    List<Userinfo> getUserinfoListByPage(Userinfo userinfo,Integer pageNum,Integer pageSize);
    /**
     * 新增数据
     *
     * @param userinfo 实例对象
     * @return 实例对象
     */
    Userinfo insert(Userinfo userinfo);

    /**
     * 修改数据
     *
     * @param userinfo 实例对象
     * @return 实例对象
     */
    Userinfo update(Userinfo userinfo);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    boolean deleteById(Integer id);

}
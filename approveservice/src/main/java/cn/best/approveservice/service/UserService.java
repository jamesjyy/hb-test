package cn.best.approveservice.service;

import cn.best.approveservice.entity.User;
import cn.best.approveservice.entity.Userinfo;

import java.util.List;

/**
 * (User)表服务接口
 *
 * @author makejava
 * @since 2021-04-08 10:17:59
 */
public interface UserService {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    User queryById(Integer id);

    User findByUsername(String username);


    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit  查询条数
     * @return 对象列表
     */
    List<User> queryAllByLimit(User user,int offset, int limit);


    List<User> getUserListByPage(User userinfo, Integer pageNum, Integer pageSize);

    List<User> querycompany(User user,int offset, int limit);

    List<User> querycompanychild(User user,int offset, int limit);


    /**
     * 新增数据
     *
     * @param user 实例对象
     * @return 实例对象
     */
    User insert(User user);

    /**
     * 修改数据
     *
     * @param user 实例对象
     * @return 实例对象
     */
    User update(User user);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    boolean deleteById(Integer id);

    void findBylike(String username, String companyname);

    List<User>queryAllBydy(User user, Integer pageNum, Integer pageSize);
    List<User>queryAllBydz(User user, Integer pageNum, Integer pageSize);
    List<User>queryAllByjf(User user, Integer pageNum, Integer pageSize);

    List<User> findbycompany(String companyname);
}

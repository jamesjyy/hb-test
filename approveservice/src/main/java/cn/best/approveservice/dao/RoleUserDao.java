package cn.best.approveservice.dao;

import cn.best.approveservice.entity.RoleUser;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleUserDao {
    /**
     * 跟据条件删除角色用户中间表
     * @param roleUser
     * @return
     */
    Integer deleteRoleUser(RoleUser roleUser);
    /**
     * 给用户赋予角色
     * @param roleUser
     * @return
     */
    Integer insertRoleUser(RoleUser roleUser);
}

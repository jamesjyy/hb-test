package cn.best.approveservice.service;

import cn.best.approveservice.entity.RoleUser;

public interface RoleUserService {
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

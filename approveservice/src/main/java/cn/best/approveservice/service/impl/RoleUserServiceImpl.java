package cn.best.approveservice.service.impl;

import cn.best.approveservice.dao.RoleUserDao;
import cn.best.approveservice.entity.RoleUser;
import cn.best.approveservice.service.RoleUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoleUserServiceImpl implements RoleUserService {
    @Autowired
    RoleUserDao roleUserDao;
    @Override
    public Integer deleteRoleUser(RoleUser roleUser) {
        return roleUserDao.deleteRoleUser(roleUser);
    }

    @Override
    public Integer insertRoleUser(RoleUser roleUser) {
        return roleUserDao.insertRoleUser(roleUser);
    }
}

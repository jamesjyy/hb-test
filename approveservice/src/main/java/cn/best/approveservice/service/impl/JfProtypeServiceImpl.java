package cn.best.approveservice.service.impl;

import cn.best.approveservice.entity.JfProtype;
import cn.best.approveservice.dao.JfProtypeDao;
import cn.best.approveservice.service.JfProtypeService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * (JfProtype)表服务实现类
 *
 * @author makejava
 * @since 2021-04-08 10:17:58
 */
@Service("jfProtypeService")
public class JfProtypeServiceImpl implements JfProtypeService {
    @Resource
    private JfProtypeDao jfProtypeDao;

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @Override
    public JfProtype queryById(Integer id) {
        return this.jfProtypeDao.queryById(id);
    }

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit  查询条数
     * @return 对象列表
     */
    @Override
    public List<JfProtype> queryAllByLimit(int offset, int limit) {
        return this.jfProtypeDao.queryAllByLimit(offset, limit);
    }

    /**
     * 新增数据
     *
     * @param jfProtype 实例对象
     * @return 实例对象
     */
    @Override
    public JfProtype insert(JfProtype jfProtype) {
        this.jfProtypeDao.insert(jfProtype);
        return jfProtype;
    }

    /**
     * 修改数据
     *
     * @param jfProtype 实例对象
     * @return 实例对象
     */
    @Override
    public JfProtype update(JfProtype jfProtype) {
        this.jfProtypeDao.update(jfProtype);
        return this.queryById(jfProtype.getId());
    }

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(Integer id) {
        return this.jfProtypeDao.deleteById(id) > 0;
    }
}

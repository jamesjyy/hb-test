package cn.best.approveservice.service.impl;

import cn.best.approveservice.entity.JfDetaisinfo;
import cn.best.approveservice.dao.JfDetaisinfoDao;
import cn.best.approveservice.service.JfDetaisinfoService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * (JfDetaisinfo)表服务实现类
 *
 * @author makejava
 * @since 2021-04-08 10:17:53
 */
@Service("jfDetaisinfoService")
public class JfDetaisinfoServiceImpl implements JfDetaisinfoService {
    @Resource
    private JfDetaisinfoDao jfDetaisinfoDao;

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @Override
    public JfDetaisinfo queryById(Integer id) {
        return this.jfDetaisinfoDao.queryById(id);
    }

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit  查询条数
     * @return 对象列表
     */
    @Override
    public List<JfDetaisinfo> queryAllByLimit(int offset, int limit) {
        return this.jfDetaisinfoDao.queryAllByLimit(offset, limit);
    }

    /**
     * 新增数据
     *
     * @param jfDetaisinfo 实例对象
     * @return 实例对象
     */
    @Override
    public JfDetaisinfo insert(JfDetaisinfo jfDetaisinfo) {
        this.jfDetaisinfoDao.insert(jfDetaisinfo);
        return jfDetaisinfo;
    }

    /**
     * 修改数据
     *
     * @param jfDetaisinfo 实例对象
     * @return 实例对象
     */
    @Override
    public JfDetaisinfo update(JfDetaisinfo jfDetaisinfo) {
        this.jfDetaisinfoDao.update(jfDetaisinfo);
        return this.queryById(jfDetaisinfo.getId());
    }

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(Integer id) {
        return this.jfDetaisinfoDao.deleteById(id) > 0;
    }
}

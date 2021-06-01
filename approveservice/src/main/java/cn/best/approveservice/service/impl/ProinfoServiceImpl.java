package cn.best.approveservice.service.impl;

import cn.best.approveservice.entity.Proinfo;
import cn.best.approveservice.dao.ProinfoDao;
import cn.best.approveservice.service.ProinfoService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * (Proinfo)表服务实现类
 *
 * @author makejava
 * @since 2021-04-08 10:17:57
 */
@Service("proinfoService")
public class ProinfoServiceImpl implements ProinfoService {
    @Resource
    private ProinfoDao proinfoDao;

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @Override
    public Proinfo queryById(Integer id) {
        return this.proinfoDao.queryById(id);
    }

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit  查询条数
     * @return 对象列表
     */
    @Override
    public List<Proinfo> queryAllByLimit(int offset, int limit) {
        return this.proinfoDao.queryAllByLimit(offset, limit);
    }

    @Override
    public List<Proinfo> queryByappid(Proinfo proinfo) {
        return proinfoDao.queryByappid(proinfo);
    }


    @Override
    public List<Proinfo> findbyappid(Proinfo proinfo) {
        return proinfoDao.findbyappid(proinfo);
    }

    @Override
    public  List<Proinfo> queryByu(Proinfo proinfo) {
        return proinfoDao.queryByu(proinfo);
    }

    @Override
    public List<Proinfo> selebyuserid(Proinfo proinfo) {
        return proinfoDao.selebyuserid(proinfo);
    }

    /**
     * 新增数据
     *
     * @param proinfo 实例对象
     * @return 实例对象
     */
    @Override
    public Proinfo insert(Proinfo proinfo) {
        this.proinfoDao.insert(proinfo);
        return proinfo;
    }

    /**
     * 修改数据
     *
     * @param proinfo 实例对象
     * @return 实例对象
     */
    @Override
    public Proinfo update(Proinfo proinfo) {
        this.proinfoDao.update(proinfo);
        return this.queryById(proinfo.getId());
    }

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(Integer id) {
        return this.proinfoDao.deleteById(id) > 0;
    }
}

package cn.best.approveservice.service;

import cn.best.approveservice.entity.JfDatainfo;
import cn.best.approveservice.entity.JfTEVSupData;
import cn.best.approveservice.entity.JfTevdatainfo;

import java.util.List;
import java.util.Map;

/**
 * (JfTevdatainfo)表服务接口
 *
 * @author makejava
 * @since 2021-04-23 10:36:42
 */
public interface JfTevdatainfoService {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    JfTevdatainfo queryById(Integer id);

    /**
     * 查询多条数据
     *
     * @return 对象列表
     */
    List<JfTevdatainfo> queryAllByLimit(Integer userid,String companyname,String rolelist, Integer pageNum, Integer pageSize);

    /**
     * 新增数据
     *
     * @param jfTevdatainfo 实例对象
     * @return 实例对象
     */
    JfTevdatainfo insert(JfTevdatainfo jfTevdatainfo);

    /**
     * 修改数据
     *
     * @param jfTevdatainfo 实例对象
     * @return 实例对象
     */
    JfTevdatainfo update(JfTevdatainfo jfTevdatainfo);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    boolean deleteById(Integer id);

    /**
     * 查询报告页面的数据
     * @param id
     * @return
     */
    JfTevdatainfo queryReportData(Integer id);


    /**
     * 根据Id查询分析页面的数据
     * @param id
     * @return
     */
    Map<String, List> queryAnalyseData(Integer id);

    /**
     * 查询编辑页面的数据
     * @param id
     * @return
     */
    JfTevdatainfo queryRedactData(Integer id);

    /**
     * 查询公司名称和站点数量
     * @param userid
     * @param pageNum
     * @param pageSize
     * @return
     */
    List<JfTEVSupData> querySupData(Integer userid, String companyname , Integer pageNum, Integer pageSize);

    /**
     * 根据公司名称删除所有数据
     * @param id
     * @param companyname
     * @return
     */
    JfTevdatainfo deleteSupOne(String id, String companyname);
}

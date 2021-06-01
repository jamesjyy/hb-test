package cn.best.approveservice.service;

import cn.best.approveservice.entity.*;

import java.util.List;
import java.util.Map;

/**
 * (DzData)表服务接口
 *
 * @author makejava
 * @since 2021-04-08 10:17:55
 */
public interface DzDataService {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    List<DzData> queryById(Integer id);

    /**
     * 查询超管第一层级数据
     *
     * @return 对象列表
     */
    List<DzDataSup> queryAllByLimit(String companyname, Integer pageNum, Integer pageSize);

    /**
     * 新增数据
     *
     * @param dzData 实例对象
     * @return 实例对象
     */
    DzData insert(DzData dzData);

    /**
     * 修改数据
     *
     * @param dzData 实例对象
     * @return 实例对象
     */
    List<DzData> update(DzData dzData);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    boolean deleteById(Integer id, String companyname);

    /**
     * 相限列表查询
     *
     * @param id
     * @return
     */
    List<DzDatainfo> queryResult(Integer id);


    /**
     * 图表数据查询
     *
     * @param id
     * @return
     */
    DzChartData queryChart(String id,String companyname,String loopname,String towernum);

    /**
     * 杆塔图数据查询
     *
     * @param id
     * @return
     */
    List<String> queryTower(String id,String companyname, String role , String loopidname, String towernum, String phaseRqs, String bunchRqs);

    /**
     * 二级页面查询
     *
     * @param userid
     * @param loopidtime
     * @param pageNum
     * @param pageSize
     * @return
     */
    List<DzDataMag> queryDzAnalyseAll(Integer userid, String loopidtime, String companyname, Integer pageNum, Integer pageSize);

    /**
     * 根据userId进行删除数据
     *
     * @param valueOf
     */
    boolean deleteManById(Integer valueOf, String companyname, String loopidtime);

    /**
     * 查询杆塔的详细信息
     *
     * @param userid
     * @param loopidtime
     * @param towernum
     * @param pageNum
     * @param pageSize
     * @return
     */
    Map<String, Object> queryDzChildAll(String userid,String companyname , String loopidtime,String role, String towernum, Integer pageNum, Integer pageSize);

    /**
     * 查询下拉框数据
     *
     * @param userid
     * @param loopidtime
     * @param towernum
     * @return
     */
    List<DzDataTowerAll> queryChildPhase(String userid,String companyname, String loopidtime, String towernum,String role);

    /**
     * 故障列表查询
     *
     * @param valueOf
     * @return
     */
    List<DzDataAnalyse> queryDzAnalyseAlls(Integer valueOf);

    /**
     * 根据userId杆塔号,线路名称来进行删除
     * @param loopidtime
     */
    void deleteOne(String userid, String companyname, String loopidtime,String towernum);

    /**
     * 查询串号
     * @param companyname
     * @param loopidtime
     * @param towernum
     * @param bunch
     * @param role
     * @return
     */
    List<DzDataTowerAll> queryChildBunch(String userid ,String companyname, String loopidtime, String towernum, String bunch, String role);
}

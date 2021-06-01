package cn.best.approveservice.service;

import cn.best.approveservice.entity.DyDatainfo;
import cn.best.approveservice.entity.JfDatainfo;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.nio.DoubleBuffer;
import java.util.List;
import java.util.Map;

/**
 * (JfDatainfo)表服务接口
 *
 * @author makejava
 * @since 2021-04-20 14:02:07
 */
public interface JfDatainfoService {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    JfDatainfo queryById(Integer id);

    /**
     * 查询多条数据
     *
     * @return 对象列表
     */
    List<JfDatainfo> queryAllByLimit(JfDatainfo jfDatainfo, Integer pageNum, Integer pageSize);


    /**
     * 新增数据
     *
     * @param jfDatainfo 实例对象
     * @return 实例对象
     */
    JfDatainfo insert(JfDatainfo jfDatainfo);

    /**
     * 修改数据
     *
     * @param jfDatainfo 实例对象
     * @return 实例对象
     */
    int update(JfDatainfo jfDatainfo);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    boolean deleteById(Integer id);

    /**
     * 故障列表
     *
     * @param
     * @return
     */
    List<JfDatainfo> searchBreakDownList(Map<String, String> params);

    /**
     * 故障列表一级列表
     * @param companyName
     * @return
     */
    List<Map> selectCompanyName(String companyName, Integer currentPage, Integer pageSize);

    /**
     * 故障列表二级列表
     * @param companyName
     * @return
     */
    List<Map> selectAreaName(String companyName, String areaName, Integer currentPage, Integer pageSize);

    /**
     * 故障列表编辑页面上传
     * @param
     * @return
     */
    String breakDownUpload(MultipartFile file, HttpServletRequest request, String id);

    /**
     * 故障列表编辑页面上传
     * @param
     * @return
     */
    String fileDelete(String[] reEntiretyImges, String[] reNoImges, String[] reBadnessImges);

    //新增文件
    public String fileinsert(List<JfDatainfo> jfDatainfo);

    //新增文件
    public String mapBase64(String src);

}

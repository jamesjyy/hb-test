package cn.best.approveservice.service;

import cn.best.approveservice.entity.DyDatainfo;
import cn.best.approveservice.entity.User;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * (DyDatainfo)表服务接口
 *
 * @author makejava
 * @since 2021-04-08 10:17:54
 */
public interface DyDatainfoService {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    DyDatainfo queryById(Integer id);

    /**
     * 查询多条数据
     * <p>
     * //     * @param offset 查询起始位置
     *
     * @param limit 查询条数
     * @return 对象列表
     */
    List<DyDatainfo> queryAllByLimit(DyDatainfo dyDatainfo, Integer pageNum, Integer pageSize);

    List<User> AdminqueryAllByLimit(User User, Integer pageNum, Integer pageSize);

    List<DyDatainfo> queryloopAllByLimit(String companyName,String loopname, Integer pageNum, Integer pageSize);

    List<DyDatainfo> querytowernum(String loopname);

    int querycountloopname(Integer id);

    int querycounttower(String loopname);

    List<User> PtuserqueryAllByLimit(User User, Integer pageNum, Integer pageSize);


    /**
     * 新增数据
     *
     * @param dyDatainfo 实例对象
     * @return 实例对象
     */
    DyDatainfo insert(DyDatainfo dyDatainfo);

    /**
     * 修改数据
     *
     * @param dyDatainfo 实例对象
     * @return 实例对象
     */
    DyDatainfo update(DyDatainfo dyDatainfo);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    boolean deleteById(Integer id);

    void saveUpload(Map param, List<Map> fileList);

    Map uploadFile(MultipartFile file, HttpServletRequest request) throws IOException;

}

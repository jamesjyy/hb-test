package cn.best.approveservice.service.impl;

import cn.best.approveservice.common.utils.Base64Util;
import cn.best.approveservice.entity.JfDatainfo;
import cn.best.approveservice.dao.JfDatainfoDao;
import cn.best.approveservice.service.JfDatainfoService;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;

/**
 * (JfDatainfo)表服务实现类
 *
 * @author makejava
 * @since 2021-04-20 14:02:07
 */
@Service()
public class JfDatainfoServiceImpl implements JfDatainfoService {
    @Autowired
    private JfDatainfoDao jfDatainfoDao;

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @Override
    public JfDatainfo queryById(Integer id) {
        return this.jfDatainfoDao.queryById(id);
    }

    /**
     * 查询多条数据
     *
     * @return 对象列表
     */
    @Override
    public List<JfDatainfo> queryAllByLimit(JfDatainfo jfDatainfo , Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<JfDatainfo> list = jfDatainfoDao.queryAllByLimit(jfDatainfo);
        return list;
    }

    /**
     * 新增数据
     *
     * @param jfDatainfo 实例对象
     * @return 实例对象
     */
    @Override
    public JfDatainfo insert(JfDatainfo jfDatainfo) {
        this.jfDatainfoDao.insert(jfDatainfo);
        return jfDatainfo;
    }

    /**
     * 修改数据
     *
     * @param jfDatainfo 实例对象
     * @return 实例对象
     */
    @Override
    public int update(JfDatainfo jfDatainfo ) {
        return this.jfDatainfoDao.update(jfDatainfo);
    }

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(Integer id) {
        return this.jfDatainfoDao.deleteById(id) > 0;
    }

    @Override
    public List<JfDatainfo> searchBreakDownList(Map<String, String> params) {
        PageHelper.startPage(Integer.valueOf(params.get("currentPage")), Integer.valueOf(params.get("pageSize")));
        List<JfDatainfo> data = jfDatainfoDao.searchBreakDownList(params);

        return data;
    }

    @Override
    public List<Map> selectCompanyName(String companyName, Integer currentPage, Integer pageSize) {
        PageHelper.startPage(currentPage, pageSize);
        List<Map> data = jfDatainfoDao.selectCompanyName(companyName);
        return data;
    }

    @Override
    public List<Map> selectAreaName(String companyName, String areaName, Integer currentPage, Integer pageSize) {
        PageHelper.startPage(currentPage, pageSize);
        List<Map> data = jfDatainfoDao.selectAreaName(companyName, areaName);
        return data;
    }

    @Override
    public String breakDownUpload(MultipartFile file, HttpServletRequest request, String id) {
//        Map<String, String> data = new HashMap<>();
//        Date date = new Date();
//        SimpleDateFormat ft = new SimpleDateFormat ("yyyy-MM-dd hh:mm:ss");

        // 指定保存目录
        String filePath = null;
        try {
//            WebApplicationContext webApplicationContext

            filePath = ResourceUtils.getURL("classpath:").getPath() + "static/imges/breakDown/" + id;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        File fileUpload = new File(filePath);
        // 如果目录不存在就创建目录
        if (!fileUpload.exists()) {
            fileUpload.mkdirs();
        }

        String uuid = UUID.randomUUID().toString().replaceAll("-", "");
        String fileName = uuid + file.getOriginalFilename().substring(file.getOriginalFilename().indexOf("."));
        // 获取所保存文件的文件名
        fileUpload = new File(filePath, fileName);

        // 保存文件
        try {
            file.transferTo(fileUpload);
        } catch (IOException e) {
            e.printStackTrace();
        }
        String url = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + "/imges/breakDown/" + id + "/" + fileName;


        return url;
    }

    @Override
    public String fileDelete(String[] reEntiretyImges, String[] reNoImges, String[] reBadnessImges) {
        String path = "";
        File file;
        if (reEntiretyImges.length > 0) {
            for (String item: reEntiretyImges) {
                try {
                    path = ResourceUtils.getURL("classpath:").getPath() + "static" + item.substring(item.indexOf("/imges/"));
                    file = new File(path);
                    // 路径为文件且不为空则进行删除
                    if (file.isFile() && file.exists()) {
                        file.delete();
                    }
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }
        }

        if (reNoImges.length > 0) {
            for (String item: reNoImges) {
                try {
                    path = ResourceUtils.getURL("classpath:").getPath() + "static" + item.substring(item.indexOf("/imges/"));
                    file = new File(path);
                    // 路径为文件且不为空则进行删除
                    if (file.isFile() && file.exists()) {
                        file.delete();
                    }
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }
        }

        if (reBadnessImges.length > 0) {
            for (String item: reBadnessImges) {
                try {
                    path = ResourceUtils.getURL("classpath:").getPath() + "static" + item.substring(item.indexOf("/imges/"));
                    file = new File(path);
                    // 路径为文件且不为空则进行删除
                    if (file.isFile() && file.exists()) {
                        file.delete();
                    }
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }


    @Override
    public String fileinsert(List<JfDatainfo> jfDatainfo) {
        return jfDatainfoDao.fileinsert(jfDatainfo);
    }

    @Override
    public String mapBase64(String src) {
        return Base64Util.GetImageBase64ByUrl(src);
    }
}

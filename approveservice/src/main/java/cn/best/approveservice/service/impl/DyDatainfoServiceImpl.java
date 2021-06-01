package cn.best.approveservice.service.impl;

import cn.best.approveservice.dao.DyDataDao;
import cn.best.approveservice.dao.UserDao;
import cn.best.approveservice.entity.DyData;
import cn.best.approveservice.entity.DyDatainfo;
import cn.best.approveservice.dao.DyDatainfoDao;
import cn.best.approveservice.entity.User;
import cn.best.approveservice.service.DyDatainfoService;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * (DyDatainfo)表服务实现类
 *
 * @author makejava
 * @since 2021-04-08 10:17:54
 */
@Service
public class DyDatainfoServiceImpl implements DyDatainfoService {
    @Resource
    private DyDatainfoDao dyDatainfoDao;

    @Resource
    private DyDataDao dyDataDao;

    @Autowired
    private UserDao userDao;

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @Override
    public DyDatainfo queryById(Integer id) {
        return this.dyDatainfoDao.queryById(id);
    }

    /**
     * 查询多条数据
     * <p>
     * //     * @param offset 查询起始位置
     *
     * @param limit 查询条数
     * @return 对象列表
     */
    @Override
    public List<DyDatainfo> queryAllByLimit(DyDatainfo dyDatainfo, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<DyDatainfo> list = dyDatainfoDao.queryAllByLimit(dyDatainfo);
        return list;
    }

    @Override
    public List<User> AdminqueryAllByLimit(User user, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<User> list = userDao.AdminqueryAllByLimit(user);
        for (int i = 0; i < list.size(); i++) {
            list.get(i).setCountnumber(userDao.querycountloopname(list.get(i).getId()));
        }
        return list;
    }

    @Override
    public List<DyDatainfo> queryloopAllByLimit(String companyName,String loopname, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<DyDatainfo> list = dyDatainfoDao.queryloopAllByLimit(companyName,loopname);
        for (int i = 0; i < list.size(); i++) {
            list.get(i).setTowernumbercount(dyDatainfoDao.querycounttower(list.get(i).getLoopname()));
            list.get(i).setTowerstatus(0);
        }
        return list;
    }


    @Override
    public List<DyDatainfo> querytowernum(String loopname) {
        return null;
    }

    @Override
    public int querycountloopname(Integer id) {
        int sum = userDao.querycountloopname(id);
        return sum;
    }

    @Override
    public int querycounttower(String loopname) {
        int count = dyDatainfoDao.querycounttower(loopname);
        return count;
    }

    @Override
    public List<User> PtuserqueryAllByLimit(User user, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);

        List<User> list = userDao.PtuserqueryAllByLimit(user);
        list.get(0).setCountnumber(userDao.querycountloopname(list.get(0).getId()));
        return list;
    }

    /**
     * 新增数据
     *
     * @param dyDatainfo 实例对象
     * @return 实例对象
     */
    @Override
    public DyDatainfo insert(DyDatainfo dyDatainfo) {
        this.dyDatainfoDao.insert(dyDatainfo);
        return dyDatainfo;
    }

    /**
     * 修改数据
     *
     * @param dyDatainfo 实例对象
     * @return 实例对象
     */
    @Override
    public DyDatainfo update(DyDatainfo dyDatainfo) {
        this.dyDatainfoDao.update(dyDatainfo);
        return this.queryById(dyDatainfo.getId());
    }

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(Integer id) {
        return this.dyDatainfoDao.deleteById(id) > 0;
    }

    @Override
    public void saveUpload(Map param, List<Map> fileList) {
        DyDatainfo dyDataInfo;
        DyData dyData;
        List<DyData> dataList = new ArrayList<>();

        if (fileList.size() > 0) {
            for (int i = 0; i < fileList.size(); i++) {
                dyDataInfo = new DyDatainfo();
                dyData = new DyData();
                dyDataInfo.setLoopname(null != param.get("line") ? param.get("line").toString() : null);
                dyDataInfo.setTime(null != param.get("detectionDate") ? param.get("detectionDate").toString() : null);
                dyDataInfo.setTowernum(null != fileList.get(i).get("tower") ? fileList.get(i).get("tower").toString() : null);
                dyDataInfo.setPhase(null != fileList.get(i).get("phase") ? fileList.get(i).get("phase").toString() : null);
                dyDataInfo.setStrand(null != fileList.get(i).get("strand") ? Integer.valueOf(fileList.get(i).get("strand").toString()) : null);
                dyDataInfo.setUserid(null != param.get("userId") ? param.get("userId").toString() : null);
                dyDataInfo.setResult(null != fileList.get(i).get("dataText") ? fileList.get(i).get("dataText").toString() : null);
                dyDatainfoDao.insertData(dyDataInfo);

                dyData.setLoopidtime(null != param.get("line") ? param.get("line").toString() : null);
                dyData.setTime(null != param.get("detectionDate") ? param.get("detectionDate").toString() : null);
                dyData.setTowernum(null != fileList.get(i).get("tower") ? fileList.get(i).get("tower").toString() : null);
                dyData.setPhase(null != fileList.get(i).get("phase") ? fileList.get(i).get("phase").toString() : null);
                dyData.setUserid(null != param.get("userId") ? param.get("userId").toString() : null);
                dataList.add(dyData);
            }

            // 创建一个根据四个条件去重的Set集合
            Set<DyData> dataSet = new TreeSet<>(Comparator.comparing(o -> (o.getTime() + "" + o.getPhase() + "" + o.getLoopidtime() + "" + o.getTowernum())));

            // 将List全部添加到Set
            if (dataList.size() > 0) {
                dataSet.addAll(dataList);
            }

            if (dataSet.size() > 0) {
                for (DyData data : dataSet) {
                    dyData = new DyData();
                    dyData = data;
                    dyDataDao.insert(dyData);
                }
            }
        }
    }

    @Override
    public Map uploadFile(MultipartFile file, HttpServletRequest request) throws IOException {
        Map<String, Object> result = new HashMap<>();
        Map<String, String> data = new HashMap<>();
        String msg = "success";

        if (file == null || file.isEmpty()) {
            msg = "所上传文件不能为空";

        } else {
            // 指定保存目录
            String filePath = ResourceUtils.getURL("classpath:").getPath() + "static/files/dy";
            File fileUpload = new File(filePath);
            // 如果目录不存在就创建目录
            if (!fileUpload.exists()) {
                fileUpload.mkdirs();
            }
            // 获取所保存文件的文件名
            fileUpload = new File(filePath, file.getOriginalFilename());

            if (fileUpload.exists()) {
                msg = "文件已存在";
            } else {
                // 保存文件
                file.transferTo(fileUpload);
                String url = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + "/target/classes/static/files/dy/" + file.getOriginalFilename();
                String savePath = fileUpload.toString();

                data.put("name", file.getOriginalFilename());
                data.put("url", url);
                data.put("savePath", savePath);
                data.put("phase", file.getOriginalFilename().substring(5, 6));
                data.put("strand", file.getOriginalFilename().substring(6, 7));

                // 读取文件
                FileReader reader = new FileReader(fileUpload.toString());
                BufferedReader br = new BufferedReader(reader);
                String line;
                String dataText = "";
                String regEx = "[' ']+";

                while ((line = br.readLine()) != null) {
                    // 一次读入一行数据
                    if (dataText.equals("")) {
                        dataText += line.trim();
                    } else {
                        dataText += "," + line.trim();
                    }

                    // 处理数据，空格替换成逗号
                    Pattern p = Pattern.compile(regEx);
                    Matcher m = p.matcher(dataText);
                    dataText = m.replaceAll(",");
                }
                br.close();
                data.put("dataText", dataText);
            }
        }
        result.put("msg", msg);
        result.put("data", data);

        return result;
    }
}

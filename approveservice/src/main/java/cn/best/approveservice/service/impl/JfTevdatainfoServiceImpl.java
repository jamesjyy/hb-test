package cn.best.approveservice.service.impl;

import cn.best.approveservice.dao.UserDao;
import cn.best.approveservice.entity.*;
import cn.best.approveservice.dao.JfTevdatainfoDao;
import cn.best.approveservice.service.JfTevdatainfoService;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * (JfTevdatainfo)表服务实现类
 *
 * @author makejava
 * @since 2021-04-23 10:36:42
 */
@Service("jfTevdatainfoService")
public class JfTevdatainfoServiceImpl implements JfTevdatainfoService {
    @Resource
    private JfTevdatainfoDao jfTevdatainfoDao;


    @Autowired
    private UserDao userDao;

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @Override
    public JfTevdatainfo queryById(Integer id) {
        return this.jfTevdatainfoDao.queryById(id);
    }


    /**
     * 查询多条数据
     *
     * @return 对象列表
     */
    @Override
    public List<JfTevdatainfo> queryAllByLimit(Integer userid,String companyname,String rolelist, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
//        List<JfTevdatainfo> list = jfTevdatainfoDao.queryAllByLimit();
        Integer role = Integer.valueOf(rolelist);
        List<JfTevdatainfo> list = new ArrayList<>();

        //判断权限
        if (role == 1) {
            //超管
            List<User> userList = this.userDao.qureyCompanyAll(companyname);
            for (User user : userList) {
                Integer id = user.getId();
                List<JfTevdatainfo> jfTevdatainfos = jfTevdatainfoDao.queryAllByLimit(id);
                for (JfTevdatainfo jfTevdatainfo : jfTevdatainfos) {
                    list.add(jfTevdatainfo);
                }
            }
        } else if (role == 3) {
            //子用户,根据ID进行查询
            list = jfTevdatainfoDao.queryAllByLimit(userid);
        } else {
            //用户,根据公司名称进行查询
            List<User> userList = this.userDao.qureyCompanyAll(companyname);
            for (User user : userList) {
                List<JfTevdatainfo> jfTevdatainfos = jfTevdatainfoDao.queryAllByLimit(user.getId());
                for (JfTevdatainfo jfTevdatainfo : jfTevdatainfos) {
                    list.add(jfTevdatainfo);
                }
            }

        }

        for (int i = 0; i < list.size(); i++) {
            JfTevdatainfo jfTevdatainfo1 = list.get(i);
            String[] split = jfTevdatainfo1.getPeakvalue().split(",");

            Integer peakvalueMax = 0;

            for (int i1 = 0; i1 < split.length; i1++) {
                Integer integer = Integer.valueOf(split[i1]);
                if (peakvalueMax < integer) {
                    peakvalueMax = Integer.valueOf(split[i1]);
                }
            }
            String imgPathFront = "http://localhost:8082/";
            String imgPath = "";
            String img = jfTevdatainfo1.getImg();
            String[] imgSplit = img.split(",");
            for (int i1 = 0; i1 < imgSplit.length; i1++) {
                imgPath = imgPathFront + imgSplit[0];
                break;
            }

            jfTevdatainfo1.setPeakvalue(peakvalueMax.toString());
            jfTevdatainfo1.setImg(imgPath);
        }

        return list;
    }

    /**
     * 新增数据
     *
     * @param jfTevdatainfo 实例对象
     * @return 实例对象
     */
    @Override
    public JfTevdatainfo insert(JfTevdatainfo jfTevdatainfo) {
        this.jfTevdatainfoDao.insert(jfTevdatainfo);
        return jfTevdatainfo;
    }

    /**
     * 修改数据
     *
     * @param jfTevdatainfo 实例对象
     * @return 实例对象
     */
    @Override
    public JfTevdatainfo update(JfTevdatainfo jfTevdatainfo) {

        this.jfTevdatainfoDao.update(jfTevdatainfo);
        return this.queryById(jfTevdatainfo.getId());
    }

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(Integer id) {
        return this.jfTevdatainfoDao.deleteById(id) > 0;
    }

    /**
     * 根据Id来进行后台的查询
     *
     * @param id
     * @return
     */
    @Override
    public JfTevdatainfo queryReportData(Integer id) {
        JfTevdatainfo jfTevdatainfo = this.jfTevdatainfoDao.queryById(id);
        String[] split = jfTevdatainfo.getPeakvalue().split(",");
        Integer peakvalueMax = 0;

        for (int i1 = 0; i1 < split.length; i1++) {
            Integer integer = Integer.valueOf(split[i1]);
            if (peakvalueMax < integer) {
                peakvalueMax = Integer.valueOf(split[i1]);
            }
        }

        jfTevdatainfo.setPeakvalue(peakvalueMax.toString());
        return jfTevdatainfo;
    }

    @Override
    public Map<String, List> queryAnalyseData(Integer id) {

        Map<String, List> map = new HashMap<>();
        List<String> peakvalueList = new ArrayList<>();
        List<String> temperatureList = new ArrayList<>();
        JfTevdatainfo jfTevdatainfo = this.jfTevdatainfoDao.queryById(id);
        String peakvalue = jfTevdatainfo.getPeakvalue();
        String temperature = jfTevdatainfo.getBackupthree();
        String[] peakvalueSplit = peakvalue.split(",");
        for (String s : peakvalueSplit) {
            peakvalueList.add(s);
        }

        String[] temperatureSplit = temperature.split(",");
        for (String s : temperatureSplit) {
            temperatureList.add(s);
        }

        map.put("peakvalueDate", peakvalueList);
        map.put("temperatureDate", temperatureList);

        return map;
    }

    @Override
    public JfTevdatainfo queryRedactData(Integer id) {
        JfTevdatainfo jfTevdatainfo = this.jfTevdatainfoDao.queryById(id);
        return jfTevdatainfo;
    }

    /**
     * 查询公司名称和站点数量
     *
     * @param userid
     * @param pageNum
     * @param pageSize
     * @return
     */
    @Override
    public List<JfTEVSupData> querySupData(Integer userid, String companyname, Integer pageNum, Integer pageSize) {

        //查询拥有局放设备的公司名称和userId
        List<User> userList = this.userDao.qureyCompanyAll(companyname);
        List<JfTEVSupData> jfTEVSupDataList = new ArrayList<>();
        for (int i = 0; i < userList.size(); i++) {
            Integer stationNumber = 0;
            List<JfTevdatainfo> jfTevdatainfoList = this.jfTevdatainfoDao.queryByUserId(userList.get(i).getId());
            Map<String, String> map = new HashMap<>();
            for (JfTevdatainfo jfTevdatainfo : jfTevdatainfoList) {
                String key = jfTevdatainfo.getStationname();
                String value = map.get(key);
                if (value != null) {
                    map.put(key, key + "!" + key);
                } else {
                    map.put(key, key);
                    stationNumber += 1;
                }
            }
            JfTEVSupData jfTEVSupData = new JfTEVSupData();
            jfTEVSupData.setCompanyName(userList.get(i).getCompanyName());
            jfTEVSupData.setStationNumber(stationNumber.toString());
            jfTEVSupDataList.add(jfTEVSupData);
        }


        //封装数据
        List<JfTEVSupData> jfTEVSupDatas = new ArrayList<>();
        JfTEVSupData jfTEVSupData = null;
        Integer stationNumber = 0;
        for (int i = 0; i < jfTEVSupDataList.size(); i++) {
            jfTEVSupData = new JfTEVSupData();
            if (jfTEVSupDataList.size() == 1) {
                jfTEVSupData.setCompanyName(jfTEVSupDataList.get(i).getCompanyName());
                jfTEVSupData.setStationNumber(jfTEVSupDataList.get(i).getStationNumber());
                jfTEVSupDatas.add(jfTEVSupData);

            } else {
                if (i == 0) {
                    if (jfTEVSupDataList.get(0).getCompanyName().equals(jfTEVSupDataList.get(1).getCompanyName())) {
                        stationNumber += Integer.valueOf(jfTEVSupDataList.get(i).getStationNumber());
                    } else {
                        jfTEVSupData.setCompanyName(jfTEVSupDataList.get(i).getCompanyName());
                        jfTEVSupData.setStationNumber(stationNumber.toString());
                        jfTEVSupDatas.add(jfTEVSupData);
                        stationNumber = 0;
                    }
                } else if (i == jfTEVSupDataList.size() - 1) {
                    if (jfTEVSupDataList.get(i).getCompanyName().equals(jfTEVSupDataList.get(i-1).getCompanyName())) {
                        stationNumber += Integer.valueOf(jfTEVSupDataList.get(i).getStationNumber());
                        jfTEVSupData.setCompanyName(jfTEVSupDataList.get(i).getCompanyName());
                        jfTEVSupData.setStationNumber(stationNumber.toString());
                        jfTEVSupDatas.add(jfTEVSupData);
                    } else {
                        jfTEVSupData.setCompanyName(jfTEVSupDataList.get(i).getCompanyName());
                        jfTEVSupData.setStationNumber(stationNumber.toString());
                        jfTEVSupDatas.add(jfTEVSupData);
                        stationNumber = 0;
                    }
                } else {
                    if (jfTEVSupDataList.get(i).getCompanyName().equals(jfTEVSupDataList.get(i+1).getCompanyName())) {
                        stationNumber += Integer.valueOf(jfTEVSupDataList.get(i).getStationNumber());
                    } else {
                        jfTEVSupData.setCompanyName(jfTEVSupDataList.get(i).getCompanyName());
                        jfTEVSupData.setStationNumber(stationNumber.toString());
                        jfTEVSupDatas.add(jfTEVSupData);
                        stationNumber = 0;
                    }
                }
            }
        }

        return jfTEVSupDatas;
    }

    /**
     * 根据公司名称删除所有数据
     * @param id
     * @param companyname
     * @return
     */
    @Override
    public JfTevdatainfo deleteSupOne(String id, String companyname) {

        List<Integer> userIds = this.userDao.findUserId(companyname);

        for (Integer  userId: userIds) {
            this.userDao.deleteById(userId);
            this.jfTevdatainfoDao.queryByUserId(userId);
        }
        return null;
    }
}

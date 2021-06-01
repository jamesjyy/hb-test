package cn.best.approveservice.service.impl;

import cn.best.approveservice.common.utils.StringUtil;
import cn.best.approveservice.dao.DzDatainfoDao;
import cn.best.approveservice.dao.UserDao;
import cn.best.approveservice.entity.*;
import cn.best.approveservice.dao.DzDataDao;
import cn.best.approveservice.service.DzDataService;
import cn.best.approveservice.service.DzDatainfoService;
import com.github.pagehelper.PageHelper;
import org.apache.shiro.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

/**
 * (DzData)表服务实现类
 *
 * @author makejava
 * @since 2021-04-08 10:17:55
 */
@Service("dzDataService")
public class DzDataServiceImpl implements DzDataService {
    @Resource
    private DzDataDao dzDataDao;

    @Resource
    private DzDatainfoDao dzDatainfoDao;

    @Resource
    private UserDao userDao;


    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @Override

    public List<DzData> queryById(Integer id) {
        return this.dzDataDao.queryById(id);
    }

    /**
     * 查询多条数据
     *
     * @return 对象列表
     */
    @Override
    public List<DzDataSup> queryAllByLimit(String companyname, Integer pageNum, Integer pageSize) {
        //分页条件
        PageHelper.startPage(pageNum, pageSize);
        DzDataSup dzDataSup = null;
        List<DzDataSup> dzDataSupList = new ArrayList<>();
        //查询所有Userid
        List<Integer> resultList = new ArrayList<>();
        Map<String, String> dataMap = new HashMap<>();

        //得到所有公司名称
        List<User> userList = userDao.queryDzUserAll(companyname);

        if (userList.size() == 0) {
            return dzDataSupList;
        }

        for (User user : userList) {
            Integer loopNameNub = 0;
            Integer loopnameErrNum = 0;
            //根据查询到的对应的公司名称封装数据
            dzDataSup = new DzDataSup();
//            dzDataSup.setUserId(user.getId());
            dzDataSup.setCompanyname(user.getCompanyName());
            List<Integer> userIdList = userDao.findUserId(user.getCompanyName());
            for (Integer userId : userIdList) {

                //查询出来一条线路下面所有杆塔对应所有数据
                List<DzDatainfo> dataInfos = dzDatainfoDao.queryInfo(userId);
                for (int i1 = 0; i1 < dataInfos.size(); i1++) {

                    String loopnameKey = dataInfos.get(i1).getLoopname();
                    String value = dataMap.get(loopnameKey);
                    if (value != null) {
                        dataMap.put(loopnameKey, value + ";" + dataInfos.get(i1).getResult());
                    } else {
                        dataMap.put(loopnameKey, dataInfos.get(i1).getResult());
                        loopNameNub += 1;
                    }
                }
                dzDataSup.setLinenumber(loopNameNub);
            }


            Set<String> strings = dataMap.keySet();
            for (String key : strings) {
                String value = dataMap.get(key);
                String[] results = value.split(";");
                for (String result : results) {
                    String[] splits = result.split(",");
                    for (int i2 = 0; i2 < splits.length; i2++) {
                        String[] splitResult = splits[i2].split("/");
                        for (String s1 : splitResult) {
                            Integer integer = Integer.valueOf(splitResult[1]);
                            resultList.add(integer);
                            break;
                        }
                    }
                }
                //判断是否有异常
                for (int i3 = 0; i3 < resultList.size(); i3++) {
                    if (resultList.get(i3) <= 500) {
                        loopnameErrNum += 1;
                        break;
                    }
                }
                //清空List里面的数据,释放数据
                resultList.clear();
            }

            dzDataSup.setErrnumber(loopnameErrNum);
            dzDataSupList.add(dzDataSup);

        }
        return dzDataSupList;
    }


    /**
     * 二级页面查询
     *
     * @param loopidtime
     * @param pageNum
     * @param pageSize
     * @return
     */
    @Override
    public List<DzDataMag> queryDzAnalyseAll(Integer userid, String loopidtime, String companyname, Integer pageNum, Integer pageSize) {

        PageHelper.startPage(pageNum, pageSize);
        HashMap<String, String> map = new HashMap<>();
        List<Integer> userIdList = userDao.findUserId(companyname);
        List<DzDataMag> dzDataMagList = new ArrayList<>();
        for (Integer userId : userIdList) {
            List<DzDatainfo> dzDatainfoList = dzDatainfoDao.queryAnalyseAll(userId, loopidtime);
            for (int i = 0; i < dzDatainfoList.size(); i++) {
                String key = dzDatainfoList.get(i).getLoopname();
                String value = map.get(key);//南阳
                String aa = dzDatainfoList.get(i).getTowernum() + "@" + dzDatainfoList.get(i).getResult();
                if (value != null) {
                    map.put(key, value + "!" + aa);
                } else {
                    map.put(key, aa);
                }
            }
        }

//map:"驻马店" -> "0003@01/0618,02/0698,03/0724  !  0003@01/1093,02/0946,03/0875,04/0831
        //得到,线路一共是多少条,并且数据都可以展示
        //线路对应的干他数量是多少


        HashMap<String, String> map2 = new HashMap<>();
//        Integer count = 0;
        Set set = map.keySet();
        for (Object o : set) {
            HashMap<String, String> map1 = new HashMap<>();
            //得到对应的线路
            Integer count = 0;
            String value = map.get(o);
            String[] split = value.split("!");
//
            for (int i = 0; i < split.length; i++) {
                String s = split[i];
                String[] split1 = s.split("@");
                String s1 = split1[0];  //0003
                String s2 = map1.get(s1);
                if (s2 != null) {
                    //key杆塔号
                    map1.put(s1, s2 + ";" + split1[1]);
                } else {
                    //key杆塔号
                    map1.put(s1, split1[1]);
                    count++;
                    String s3 = o.toString();  //驻马店
                    map2.put(s3, count + "");
                }
            }


            Set<String> strings1 = map1.keySet();
            Integer ssss = 0;

//            map1:"0004" -> "01/1093,02/0849,03/0671 ; 01/1093,02/0849,03/0671,

            aa:
            for (String key : strings1) {
                String loopNameValue = map1.get(key);
                String[] resultSplit = loopNameValue.split(";");
                for (int i = 0; i < resultSplit.length; i++) {
                    String[] split1 = resultSplit[i].split(",");
                    for (int i1 = 0; i1 < split1.length; i1++) {

                        String[] split2 = split1[i1].split("/");
                        Integer integer1 = Integer.valueOf(split2[i1]);
                        if (integer1 <= 500) {
                            ssss++;
                            continue aa;
                        }

                    }
                }
            }
            map2.put(o.toString(), map2.get(o.toString()) + "," + ssss + "");
        }


        Set<String> stringSet = map2.keySet();
        for (String s : stringSet) {
            DzDataMag dzDataMag = new DzDataMag();
            String s1 = map2.get(s);
            String[] split = s1.split(",");
            dzDataMag.setLoopidtime(s);
            dzDataMag.setTowerNumber(Integer.valueOf(split[0]));
            dzDataMag.setErrnumber(Integer.valueOf(split[1]));
            dzDataMagList.add(dzDataMag);
        }
        return dzDataMagList;
    }


    @Override
    public boolean deleteManById(Integer id, String companyname, String loopidtime) {

        //根据ID和线路名称来进行逐级删除
        List<Integer> userIds = this.userDao.findUserId(companyname);
        for (Integer userId : userIds) {
            this.dzDatainfoDao.deleteByLoopId(userId, loopidtime);
            this.dzDataDao.deleteByUserId(userId, loopidtime);
        }
        return true;
    }


//    /**
//     * 查询数据杆塔详情
//     *
//     * @param loopidtime
//     * @param towernum
//     * @param pageNum
//     * @param pageSize
//     * @return
//     */
//    @Override
//    public Map<String, Object> queryDzChildAll(String userid, String companyname ,String loopidtime, String role, String towernum, Integer pageNum, Integer pageSize) {
//
//        //开启分页
//        PageHelper.startPage(pageNum, pageSize);
//        Map<String, Object> mapData = new HashMap<>();
//        List<DzDataChild> dzDataChildList = new ArrayList<>();
//        HashMap<String, String> map = new HashMap<>();
//
//        if (role != "" && Integer.valueOf(role) == 3) {
//            List<DzDatainfo> dzDatainfoList = dzDatainfoDao.queryDzChildAll(userid, loopidtime, towernum);
//            for (int i = 0; i < dzDatainfoList.size(); i++) {
//                String tower = dzDatainfoList.get(i).getTowernum();
//                String key = dzDatainfoList.get(i).getLoopname();
//                String value = map.get(key);
//                if (value != null) {
//                    map.put(key, value + "," + tower);
//                } else {
//                    map.put(key, tower);
//                }
//            }
//            Set<String> strings = map.keySet();
//            for (String tower : strings) {
//                String value = map.get(tower);
//                String[] split = value.split(",");
//                Map<String, String> towernumMap = new HashMap<>();
//                for (int i = 0; i < split.length; i++) {
//                    String towernumKey = split[i];
//                    String towernumValue = towernumMap.get(towernumKey);
//                    if (towernumValue != null) {
//                        towernumMap.put(towernumKey, towernumKey);
//                    } else {
//                        towernumMap.put(towernumKey, towernumKey);
//                        DzDataChild dzDataChild = new DzDataChild();
//                        dzDataChild.setLoopname(tower);
//                        dzDataChild.setTowernum(towernumKey);
//                        dzDataChildList.add(dzDataChild);
//                    }
//                }
//            }
//            mapData.put("dzDataChildList", dzDataChildList);
//            mapData.put("total", dzDataChildList.size());
//            return mapData;
//        } else {
//            List<Integer> userIdList = userDao.qureyChildUserId(userid, companyname ,loopidtime);
//            for (Integer userId : userIdList) {
//                List<DzDatainfo> dzDatainfoList = dzDatainfoDao.queryDzChildAll(userId.toString(), loopidtime, towernum);
//                for (int i = 0; i < dzDatainfoList.size(); i++) {
//                    String tower = dzDatainfoList.get(i).getTowernum();
//                    String key = dzDatainfoList.get(i).getLoopname();
//                    String value = map.get(key);
//                    if (value != null) {
//                        map.put(key, value + "," + tower);
//                    } else {
//                        map.put(key, tower);
//                    }
//                }
//            }
//            Set<String> strings = map.keySet();
//            for (String tower : strings) {
//                String value = map.get(tower);
//                String[] split = value.split(",");
//                Map<String, String> towernumMap = new HashMap<>();
//                for (int i = 0; i < split.length; i++) {
//                    String towernumKey = split[i];
//                    String towernumValue = towernumMap.get(towernumKey);
//                    if (towernumValue != null) {
//                        towernumMap.put(towernumKey, towernumKey);
//                    } else {
//                        towernumMap.put(towernumKey, towernumKey);
//                        DzDataChild dzDataChild = new DzDataChild();
//                        dzDataChild.setLoopname(tower);
//                        dzDataChild.setTowernum(towernumKey);
//                        dzDataChildList.add(dzDataChild);
//                    }
//                }
//            }
//            mapData.put("dzDataChildList", dzDataChildList);
//            mapData.put("total", dzDataChildList.size());
//            return mapData;
//        }
//    }


    /**
     * 查询数据杆塔详情
     *
     * @param loopidtime
     * @param towernum
     * @param pageNum
     * @param pageSize
     * @return
     */
    @Override
    public Map<String, Object> queryDzChildAll(String userid, String companyname, String loopidtime, String role, String towernum, Integer pageNum, Integer pageSize) {

        //开启分页
        PageHelper.startPage(pageNum, pageSize);
        Map<String, Object> mapData = new HashMap<>();
        List<DzDataChild> dzDataChildList = new ArrayList<>();
        HashMap<String, String> map = new HashMap<>();
        if (role != "" && Integer.valueOf(role) == 3) {
            List<DzDatainfo> dzDatainfoList = dzDatainfoDao.queryDzChildAll(userid, loopidtime, towernum);
            for (int i = 0; i < dzDatainfoList.size(); i++) {
                String tower = dzDatainfoList.get(i).getTowernum();
                String key = dzDatainfoList.get(i).getLoopname();
                String value = map.get(key);
                if (value != null) {
                    map.put(key, value + "," + tower);
                } else {
                    map.put(key, tower);
                }
            }


            Set<String> strings = map.keySet();
            for (String tower : strings) {
                String value = map.get(tower);
                String[] split = value.split(",");
                Map<String, String> towernumMap = new HashMap<>();
                for (int i = 0; i < split.length; i++) {
                    String towernumKey = split[i];
                    String towernumValue = towernumMap.get(towernumKey);
                    if (towernumValue != null) {
                        towernumMap.put(towernumKey, towernumKey);
                    } else {
                        List<DzDatainfo> dzDatainfoLs = dzDatainfoDao.queryDzChildAll(userid, tower, towernumKey);
                        HashMap<String, String> mapPhans = new HashMap<>();
                        //排序
                        Comparator<DzDatainfo> byHeight = Comparator.comparing(DzDatainfo::getPhase);
                        Comparator<DzDatainfo> byWeight = Comparator.comparing(DzDatainfo::getStrand);
                        dzDatainfoLs.sort(byHeight.thenComparing(byWeight));

                        for (int i1 = 0; i1 < dzDatainfoLs.size(); i1++) {
                            //key = 相别
                            String key = dzDatainfoLs.get(i1).getPhase();
                            String valuePhans = map.get(key);
                            String strands = dzDatainfoLs.get(i1).getStrand().toString();
                            if (valuePhans != null) {
                                mapPhans.put(key, valuePhans + "," + "第" + strands + "串");
                            } else {
                                mapPhans.put(key, "第" + strands + "串");
                            }
                        }

                        Set<String> stringsKey = mapPhans.keySet();
                        List<String> bunchData = new ArrayList<>();
                        List<DzDataTowerAll> dzDataTowerAllList = new ArrayList<>();
                        for (String key : stringsKey) {
                            DzDataTowerAll dzDataTowerAll = new DzDataTowerAll();
                            dzDataTowerAll.setPhase(key);
                            String bunchKey = mapPhans.get(key);
                            String[] bunchSplit = bunchKey.split(",");
                            bunchData.clear();
                            for (String s : bunchSplit) {
                                bunchData.add(s);
                            }
                            dzDataTowerAll.setBunch(bunchData);
                            dzDataTowerAllList.add(dzDataTowerAll);
                        }
                        towernumMap.put(towernumKey, towernumKey);
                        DzDataChild dzDataChild = new DzDataChild();
                        dzDataChild.setLoopname(tower);
                        dzDataChild.setTowernum(towernumKey);
                        dzDataChild.setPhase(dzDataTowerAllList);
                        dzDataChildList.add(dzDataChild);


                    }
                }
            }
            mapData.put("dzDataChildList", dzDataChildList);
            mapData.put("total", dzDataChildList.size());
            return mapData;
        } else {
            List<Integer> userIdList = userDao.qureyDzChildUserId(userid, companyname, loopidtime);
            for (Integer userId : userIdList) {
                List<DzDatainfo> dzDatainfoList = dzDatainfoDao.queryDzChildAll(userId.toString(), loopidtime, towernum);
                for (int i = 0; i < dzDatainfoList.size(); i++) {
                    String tower = dzDatainfoList.get(i).getTowernum();
                    String key = dzDatainfoList.get(i).getLoopname();
                    String value = map.get(key);
                    if (value != null) {
                        map.put(key, value + "," + tower);
                    } else {
                        map.put(key, tower);
                    }
                }
            }
            Set<String> strings = map.keySet();
            for (String tower : strings) {
                String value = map.get(tower);
                String[] split = value.split(",");
                Map<String, String> towernumMap = new HashMap<>();
                for (int i = 0; i < split.length; i++) {
                    String towernumKey = split[i];
                    String towernumValue = towernumMap.get(towernumKey);
                    if (towernumValue != null) {
                        towernumMap.put(towernumKey, towernumKey);
                    } else {


                        List<DzDatainfo> dzDatainfoLs = dzDatainfoDao.queryDzChildAll(userid, tower, towernumKey);
                        HashMap<String, String> mapPhans = new HashMap<>();
                        //排序
                        Comparator<DzDatainfo> byHeight = Comparator.comparing(DzDatainfo::getPhase);
                        Comparator<DzDatainfo> byWeight = Comparator.comparing(DzDatainfo::getStrand);
                        dzDatainfoLs.sort(byHeight.thenComparing(byWeight));

                        for (int i1 = 0; i1 < dzDatainfoLs.size(); i1++) {
                            //key = 相别
                            String key = dzDatainfoLs.get(i1).getPhase();
                            String valuePhans = mapPhans.get(key);
                            String strands = dzDatainfoLs.get(i1).getStrand().toString();
                            if (valuePhans != null) {
                                mapPhans.put(key, valuePhans + "," + "第" + strands + "串");
                            } else {
                                mapPhans.put(key, "第" + strands + "串");
                            }
                        }
                        Set<String> stringsKey = mapPhans.keySet(); //1


                        List<DzDataTowerAll> dzDataTowerAllList = new ArrayList<>();

                        DzDataTowerAll dzDataTowerAll = null;
                        for (String key : stringsKey) {
                            List<String> bunchData = new ArrayList<>();
                            dzDataTowerAll = new DzDataTowerAll();
                            dzDataTowerAll.setPhase(key);
                            String bunchKey = mapPhans.get(key);
                            String[] bunchSplit = bunchKey.split(",");

                            for (String s : bunchSplit) {
                                bunchData.add(s);//第几串
                            }
                            dzDataTowerAll.setBunch(bunchData);
                            dzDataTowerAllList.add(dzDataTowerAll);
                        }
                        towernumMap.put(towernumKey, towernumKey);
                        DzDataChild dzDataChild = new DzDataChild();
                        dzDataChild.setLoopname(tower);
                        dzDataChild.setTowernum(towernumKey);
                        dzDataChild.setPhase(dzDataTowerAllList);
                        dzDataChildList.add(dzDataChild);
                    }
                }
            }
            mapData.put("dzDataChildList", dzDataChildList);
            mapData.put("total", dzDataChildList.size());
            return mapData;
        }
    }


    /**
     * 查询下拉框数据
     *
     * @param userid
     * @param loopidtime
     * @param towernum
     * @return
     */
    @Override
    public List<DzDataTowerAll> queryChildPhase(String userid, String companyname, String loopidtime, String towernum, String role) {
        List<DzDataTowerAll> dzDataTowerAllList = new ArrayList<>();
        HashMap<String, String> map = new HashMap<>();

        Integer roleInt = Integer.valueOf(role);
        if (roleInt == 3) {
            List<DzDatainfo> dzDatainfoList = dzDatainfoDao.queryDzChildAll(userid, loopidtime, towernum);
            //排序
            Comparator<DzDatainfo> byHeight = Comparator.comparing(DzDatainfo::getPhase);
            Comparator<DzDatainfo> byWeight = Comparator.comparing(DzDatainfo::getStrand);
            dzDatainfoList.sort(byHeight.thenComparing(byWeight));

            for (int i = 0; i < dzDatainfoList.size(); i++) {
                //key = 相别
                String key = dzDatainfoList.get(i).getPhase();
                String value = map.get(key);
                String strands = dzDatainfoList.get(i).getStrand().toString();
                if (value != null) {
                    map.put(key, value + "," + "第" + strands + "串");
                } else {
                    map.put(key, "第" + strands + "串");
                }
            }

        } else {
            List<Integer> userIdList = userDao.queryChildPhase(companyname);
            for (Integer userId : userIdList) {
                List<DzDatainfo> dzDatainfoList = dzDatainfoDao.queryDzChildAll(userId.toString(), loopidtime, towernum);
                //排序
                Comparator<DzDatainfo> byHeight = Comparator.comparing(DzDatainfo::getPhase);
                Comparator<DzDatainfo> byWeight = Comparator.comparing(DzDatainfo::getStrand);
                dzDatainfoList.sort(byHeight.thenComparing(byWeight));

                for (int i = 0; i < dzDatainfoList.size(); i++) {
                    //key = 相别
                    String key = dzDatainfoList.get(i).getPhase();
                    String value = map.get(key);
                    String strands = dzDatainfoList.get(i).getStrand().toString();
                    if (value != null) {
                        map.put(key, value + "," + "第" + strands + "串");
                    } else {
                        map.put(key, "第" + strands + "串");
                    }
                }
            }
        }


        Set<String> strings = map.keySet();
        for (String key : strings) {
            DzDataTowerAll dzDataTowerAll = new DzDataTowerAll();
            dzDataTowerAll.setPhase("第" + key + "相别");
//            dzDataTowerAll.setBunch(map.get(key));
            dzDataTowerAllList.add(dzDataTowerAll);
        }

        return dzDataTowerAllList;
    }

    @Override
    public List<DzDataAnalyse> queryDzAnalyseAlls(Integer valueOf) {
        return null;
    }


    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(Integer id, String companyname) {

        User user = this.userDao.queryById(id);
        List<Integer> list = this.userDao.queryCompanyNameAll(user.getCompanyName());
        for (Integer userId : list) {

            this.dzDatainfoDao.deleteByUserId(userId);
            this.dzDataDao.deleteById(userId);
        }
        return true;

    }

    /**
     * 新增数据
     *
     * @param dzData 实例对象
     * @return 实例对象
     */
    @Override
    public DzData insert(DzData dzData) {
        this.dzDataDao.insert(dzData);
        return dzData;
    }

    /**
     * 修改数据
     *
     * @param dzData 实例对象
     * @return 实例对象
     */
    @Override
    public List<DzData> update(DzData dzData) {
        this.dzDataDao.update(dzData);
        return this.queryById(dzData.getId());
    }


//
//    /**
//     * 故障列表查询
//     *
//     * @return
//     */

    public List<DzDataAnalyse> queryDzAnalyseAll(String userid, String companyname, String loopname, String towernum) {
        DzDataAnalyse dzDataAnalyse;
        Integer stateValue = null;
        List<DzDataAnalyse> dzDataAnalyses = new ArrayList<>();
        //通过查询id,来查询对应的塔杆和线路来查询后台,返回一个List集合,绕后进行判断

        String strand = "";
        List<DzDatainfo> dataInfos = new ArrayList<>();
        List<Integer> userIdList = userDao.queryChildPhase(companyname);
        for (Integer id : userIdList) {
            List<DzDatainfo> datainfoList = dzDatainfoDao.queryDzBunch(id.toString(), loopname, towernum, strand);
            for (DzDatainfo dzDatainfo : datainfoList) {
                dataInfos.add(dzDatainfo);
            }
        }

        //List<DzDatainfo> dataInfos = dzDatainfoDao.queryAnalyseAll(id);
        List<DzDataAnalyse> dzDataAnalyseList = new ArrayList<>();
        //封装数据
        for (DzDatainfo dzDatainfo : dataInfos) {
            String result = dzDatainfo.getResult();
            //数据拆分
            String[] split = result.split(",");
            for (String s : split) {
                String[] splitResult = s.split("/");
                for (String s1 : splitResult) {

                    dzDataAnalyse = new DzDataAnalyse();
                    dzDataAnalyse.setId(dzDatainfo.getId());
                    dzDataAnalyse.setPhase(dzDatainfo.getPhase());
                    dzDataAnalyse.setStrand(dzDatainfo.getStrand());
                    dzDataAnalyse.setLoopname(dzDatainfo.getLoopname());
                    dzDataAnalyse.setTowernum(dzDatainfo.getTowernum());
                    dzDataAnalyse.setTime(dzDatainfo.getTime());
                    dzDataAnalyse.setSlice(splitResult[0]);
                    dzDataAnalyse.setMeasured(splitResult[1]);

                    //转换成Int类型的数据进行判断
                    if (splitResult[1] != null) {
                        String stateStr = splitResult[1].replaceAll("\\s", "");
                        stateValue = Integer.parseInt(stateStr);
                    }
                    dzDataAnalyseList.add(dzDataAnalyse);
                    break;
                }
            }
        }

        for (int i = 0; i < dzDataAnalyseList.size(); i++) {

            DzDataAnalyse dzData = dzDataAnalyseList.get(i);
            int anInt1 = Integer.parseInt(dzData.getMeasured());
            if (anInt1 <= 500) {
                dzData.setStatus("故障");
            } else {
                dzData.setStatus("良好");
            }
        }
        for (DzDataAnalyse dataAnalyse : dzDataAnalyseList) {
            if (dataAnalyse.getStatus().equals("故障")) {
                dzDataAnalyses.add(dataAnalyse);
            }
        }
        return dzDataAnalyses;
    }


    /**
     * 根据userId,线路名称,杆塔号来进行删除
     *
     * @param loopidtime
     */
    @Override
    public void deleteOne(String userid, String companyname, String loopidtime,String towernum) {

        //根据ID和线路名称来进行逐级删除
        List<Integer> userIds = this.userDao.findUserId(companyname);
        for (Integer userId : userIds) {
            this.dzDatainfoDao.deleteByTowernumId(userId, loopidtime,towernum);

            this.dzDataDao.deleteByUserId(userId, loopidtime);
        }
    }

    /**
     * 查询串号
     *
     * @param companyname
     * @param loopidtime
     * @param towernum
     * @param bunch
     * @param role
     * @return
     */
    @Override
    public List<DzDataTowerAll> queryChildBunch(String userid, String companyname, String loopidtime, String towernum, String bunch, String role) {

        String strand = bunch.substring(1, 2);
        List<DzDataTowerAll> dzDataTowerAllList = new ArrayList<>();
        HashMap<String, String> map = new HashMap<>();

        Integer roleInt = Integer.valueOf(role);
        if (roleInt == 3) {
            List<DzDatainfo> dzDatainfoList = dzDatainfoDao.queryDzBunch(userid, loopidtime, towernum, strand);
            //排序
            Comparator<DzDatainfo> byHeight = Comparator.comparing(DzDatainfo::getPhase);
            Comparator<DzDatainfo> byWeight = Comparator.comparing(DzDatainfo::getStrand);
            dzDatainfoList.sort(byHeight.thenComparing(byWeight));

            for (int i = 0; i < dzDatainfoList.size(); i++) {
                //key = 相别
                String key = dzDatainfoList.get(i).getPhase();
                String value = map.get(key);
                String strands = dzDatainfoList.get(i).getStrand().toString();
                if (value != null) {
                    map.put(key, value + "," + "第" + strands + "串");
                } else {
                    map.put(key, "第" + strands + "串");
                }
            }

        } else {
            List<Integer> userIdList = userDao.queryChildPhase(companyname);
            for (Integer userId : userIdList) {
                List<DzDatainfo> dzDatainfoList = dzDatainfoDao.queryDzBunch(userId.toString(), loopidtime, towernum, strand);
                //排序
                Comparator<DzDatainfo> byHeight = Comparator.comparing(DzDatainfo::getPhase);
                Comparator<DzDatainfo> byWeight = Comparator.comparing(DzDatainfo::getStrand);
                dzDatainfoList.sort(byHeight.thenComparing(byWeight));

                for (int i = 0; i < dzDatainfoList.size(); i++) {
                    //key = 相别
                    String key = dzDatainfoList.get(i).getPhase();
                    Integer strand1 = dzDatainfoList.get(i).getStrand();
                    String string = strand1.toString();
                    String jointStrand = "第" + string + "串";
                    DzDataTowerAll dzDataTowerAll = new DzDataTowerAll();
//                    dzDataTowerAll.setBunch(jointStrand);
                    dzDataTowerAllList.add(dzDataTowerAll);
                }
            }
        }

        return dzDataTowerAllList;
    }

    /**
     * 相限列表查询
     *
     * @param id
     * @return
     */
    @Override
    public List<DzDatainfo> queryResult(Integer id) {
        //查询dzData数据库得到线路名称和塔杆号
        List<DzData> dzData = this.dzDataDao.queryById(id);
        String loopidtime = null;
        String towernum = null;
        for (DzData dzDatum : dzData) {
            loopidtime = dzDatum.getLoopidtime();
            towernum = dzDatum.getTowernum();
        }

        //通过线路名称和塔杆号查询对应的相别和result数据
        List<DzDatainfo> dzDatainfoList = this.dzDatainfoDao.queryInfoLT(loopidtime, towernum);
        return dzDatainfoList;
    }


    //    /**
//     * 权限列表查询
//     *
//     * @param id
//     * @return
//     */
    @Override
    public DzChartData queryChart(String userid, String companyname, String loopname, String towernum) {


        String strand = "";
        List<DzDatainfo> datainfos = new ArrayList<>();
        List<Integer> userIdList = userDao.queryChildPhase(companyname);
        for (Integer id : userIdList) {
            List<DzDatainfo> datainfoList = dzDatainfoDao.queryDzBunch(id.toString(), loopname, towernum, strand);
            for (DzDatainfo dzDatainfo : datainfoList) {
                datainfos.add(dzDatainfo);
            }
        }

        //通过查询id,来查询对应的塔杆和线路来查询后台,返回一个List集合,绕后进行判断
//        List<DzDatainfo> dataInfos = dzDatainfoDao.queryInfoAll(id);
        //1.封装一个片号,对应就得封装一个没串的总数,再封装一个每串上面的故障总数,中间都是使用,隔开封装成不同的字段
        List<DzDataAnalyse> dzDataAnalyseList = this.queryDzAnalyseAll(userid, companyname, loopname, towernum);

        String resultStr = "";
        //片号
        String slicesStr = "";
//        每一串的总片数
        String datanumStr = "";
//        每串上的故障数量
        String datalingStr = "";
        DzChartData dzChartData = new DzChartData();

        //封装数据
        for (DzDatainfo dzDatainfo : datainfos) {
            String result = dzDatainfo.getResult();
            //数据拆分
            String[] split = result.split(",");
            //得到对应串的多少片
            Integer length = split.length;
            if (datainfos.size() == 1) {
                resultStr += dzDatainfo.getPhase() + dzDatainfo.getStrand() + "/" + length;
            } else {
                resultStr += dzDatainfo.getPhase() + dzDatainfo.getStrand() + "/" + length + ",";
            }

            slicesStr += dzDatainfo.getPhase() + dzDatainfo.getStrand() + ",";
            datanumStr += length + ",";

            //封装数据
            dzChartData.setId(dzDatainfo.getId());
            dzChartData.setPhase(dzDatainfo.getPhase());

            dzChartData.setLoopname(dzDatainfo.getLoopname());
            dzChartData.setTowernum(dzDatainfo.getTowernum());
            dzChartData.setTime(dzDatainfo.getTime());

        }


        if (resultStr.indexOf(",") != -1) {
            resultStr = resultStr.substring(0, resultStr.length() - 1);
            dzChartData.setResult(resultStr);
        } else {
            dzChartData.setResult(resultStr);
        }

        if (slicesStr.indexOf(",") != -1) {
            slicesStr = slicesStr.substring(0, slicesStr.length() - 1);
            dzChartData.setSlices(slicesStr);
        } else {
            dzChartData.setSlices(slicesStr);
        }

        if (datanumStr.indexOf(",") != -1) {
            datanumStr = datanumStr.substring(0, datanumStr.length() - 1);
            dzChartData.setDatanum(datanumStr);
        } else {
            dzChartData.setDatanum(datanumStr);
        }
        Integer dataling = 0;
        String[] split = slicesStr.split(",");
        for (int i = 0; i < split.length; i++) {
            dataling = 0;
            for (int i1 = 0; i1 < dzDataAnalyseList.size(); i1++) {
                String strandSlices = dzDataAnalyseList.get(i1).getPhase() + dzDataAnalyseList.get(i1).getStrand();
                if (split[i].equals(strandSlices)) {
                    dataling = dataling + 1;
                } else if (i1 == dzDataAnalyseList.size() - 1) {
                    datalingStr += dataling + ",";
                }
                if (split[i].equals(strandSlices) && i1 == dzDataAnalyseList.size() - 1) {
                    datalingStr += dataling + ",";
                }
            }
        }

        if (datalingStr.indexOf(",") != -1) {
            datalingStr = datalingStr.substring(0, datalingStr.length() - 1);
            dzChartData.setDataling(datalingStr);
        } else {
            dzChartData.setDataling(datalingStr);
        }

        return dzChartData;
    }

    /**
     * 杆塔图表数据查询
     *
     * @param id
     * @return
     */
    @Override
    public List<String> queryTower(String id, String companyname, String role, String loopname, String towernum, String phaseRqs, String bunchRqs) {


        TowerList towerList = null;
        //需要给前台返回的数据

        String measureds = "";

        //存入对应的串号和数据值
        List<TowerList> towerLists = new ArrayList<>();
        //存入一整串测量值
        String measured = "";
        List<String> strings = new ArrayList<>();

        List<Integer> userIdList = userDao.qureyDzChildUserId("", companyname, loopname);
        for (Integer userId : userIdList) {
//        根据userId, 线路名称 , 相别 , 串号 来进行查询
            List<DzDatainfo> dzDatainfoList = dzDatainfoDao.queryTowerData(userId, loopname, towernum, phaseRqs, bunchRqs);

            for (DzDatainfo dataInfo : dzDatainfoList) {
                towerList = new TowerList();
                String result = dataInfo.getResult();
                //数据拆分
                String[] split = result.split(",");
                //["01/0234","02/0456"]
                String phase = "";
                String strand = "";
                for (int i1 = 0; i1 < split.length; i1++) {
                    String[] splitResult = split[i1].split("/");
                    for (String s1 : splitResult) {
                        if (i1 == split.length - 1) {
                            measured += splitResult[1];
                            phase = dataInfo.getPhase();
                            strand = Integer.toString(dataInfo.getStrand());
                            break;
                        } else {
                            measured += splitResult[1] + ",";
                            phase = dataInfo.getPhase();
                            strand = Integer.toString(dataInfo.getStrand());
                            break;
                        }
                    }
                }
                towerList.setPhaseStrand(phase + strand);
                towerList.setResult(measured);
                towerLists.add(towerList);
                measured = "";
            }

        }


        //对List进行排序
        Collections.sort(towerLists, new Comparator<TowerList>() {

            @Override
            public int compare(TowerList o1, TowerList o2) {
                return o1.getPhaseStrand().compareTo(o2.getPhaseStrand());
            }

            @Override
            public Comparator<TowerList> reversed() {
                return null;
            }
        });


        //循环并封装,返回给前台
        for (int i = 0; i < towerLists.size(); i++) {
            if (towerLists.size() != 1) {
                if (i == 0) {
                    if (towerLists.get(i).getPhaseStrand().charAt(0) == towerLists.get(i + 1).getPhaseStrand().charAt(0)) {
                        measureds += towerLists.get(i + 1).getResult() + "-";
                        continue;
                    }
                } else if (i == towerLists.size() - 1) {
                    if (towerLists.get(i).getPhaseStrand().charAt(0) == towerLists.get(i - 1).getPhaseStrand().charAt(0)) {
                        measureds += towerLists.get(i).getResult();
                        strings.add(measureds);
                        continue;
                    }
                } else {
                    if (towerLists.get(i).getPhaseStrand().charAt(0) == towerLists.get(i - 1).getPhaseStrand().charAt(0)) {
                        measureds += towerLists.get(i).getResult() + "-";
                        continue;
                    } else if (towerLists.get(i).getPhaseStrand().charAt(0) == towerLists.get(i + 1).getPhaseStrand().charAt(0)) {
                        measureds += towerLists.get(i).getResult() + "-";
                        continue;
                    } else {
                        String substring = measureds.substring(0, measureds.length() - 1);
                        strings.add(substring);
                        measureds = "";
                        continue;
                    }
                }
            }
            strings.add(towerLists.get(i).getResult());
        }
        return strings;
    }
}

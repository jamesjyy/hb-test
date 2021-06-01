package cn.best.approveservice.controller;

import cn.best.approveservice.common.utils.Base64Utils;
import cn.best.approveservice.entity.JfDatainfo;
import cn.best.approveservice.entity.JfTevdatainfo;
import cn.best.approveservice.response.R;

import cn.best.approveservice.service.JfDatainfoService;
import cn.best.approveservice.service.JfTevdatainfoService;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;


@RestController
@RequestMapping("/test")
public class TestController extends BaseController{
    @Autowired
    private JfDatainfoService jfDatainfoService;
    @Autowired
    private JfTevdatainfoService jfTevdatainfoService;
    private static Logger logger;
    @RequestMapping(value = "/accept", method = RequestMethod.POST,produces="application/json;charset=utf-8")
    @ResponseBody
    public R acceptHttpPost(@RequestBody String desJson) throws Exception{

        //接受推送过来的数据
        System.out.println("入参数据:"+desJson);
      //  System.out.println("接受入参FILE:"+file.getSize()+"otherdesc:"+otherDesc);
        String quanImages="";
       // Map<String, Object> data = new HashMap<>();
        JSONObject json = JSONObject.parseObject(desJson);
        String quancount = json.getString("quancount");
        for (int i=0;i<Integer.parseInt(quancount); i++){
             json.getString("quanImages" + 1);
            Base64Utils.base64StrToImage(json.getString("quanImages" + 1),"G:/pic/temp/"+i+".jpg");

        }

        String quanImages0 = json.getString("quanImages0");

        Base64Utils.base64StrToImage(quanImages0,"G:/pic/temp/002.jpg");

        //数据类型0 ae 1 tev
        String type = json.getString("type");
        JfDatainfo jfDatainfo=new JfDatainfo();
        //检测名称
        String name = json.getString("name");
        //站点名称
        String stationName = json.getString("E_name");
        //站点编码
        String stationCode = json.getString("E_num");
        //天气
        String weather = json.getString("weather");
        //测点名称
        String point_name = json.getString("point_name");
        //测点编码
        String point_num = json.getString("point_num");
        //温度
        String temperature = json.getString("temperature");
        //湿度
        String humidity = json.getString("humidity");
        //经度
        String longitude = json.getString("longitude");
        //纬度
        String latitude = json.getString("latitude");
        //
        String time = json.getString("time");
        //0AE类型数据   1TEV数据类型

            //增益
            String gain = json.getString("gain");
            //平均值
            String avedb = json.getString("avedb");
            //最大值
            String max_value = json.getString("max_value");
            //特征数据
            String tdata_value = json.getString("tdata_value");
            //相位数据
            String xdata_value = json.getString("xdata_value");
            //飞行数据
            String fdata_value = json.getString("fdata_value");
            //事实数据
            String qdata_value = json.getString("qdata_value");
            String distance = json.getString("distance");
            //仪器厂家
            String deviceHome = json.getString("deviceHome");
            //水平档距
            String levelDistance = json.getString("levelDistance");
            //塔干种类
            String poleType = json.getString("poleType");
            //接地电阻
            String groundResistance = json.getString("groundResistance");
            //交叉跨越
            String lineDirection = json.getString("lineDirection");
            //检测解脱
            String testResult = json.getString("testResult");
            //缺陷类型
            String harmType = json.getString("harmType");
            //全景图
           // String quanImages = json.getString("quanImages");
            //近景图
            String jinImages = json.getString("jinImages");
            //缺陷图
            String queImages = json.getString("queImages");
            //infraredImages
            String infraredImages = json.getString("infraredImages");
            //初始化jf信息数据入库
            JfDatainfo datainfo=new JfDatainfo();
            datainfo.setTemperature(temperature);
            datainfo.setStationname(stationName);
            datainfo.setStationnumber(stationCode);
            datainfo.setHumidity(humidity);
            datainfo.setMaxdb(max_value);
            datainfo.setAvedb(avedb);
            datainfo.setDeviceHome(deviceHome);
            datainfo.setTestResult(testResult);
            datainfo.setTime(time);
            datainfo.setHarmType(harmType);
            datainfo.setLevelDistance(levelDistance);
            datainfo.setLineDirection(lineDirection);
            datainfo.setPoleType(poleType);
            datainfo.setGroundResistance(groundResistance);
            datainfo.setFdata_value(fdata_value);
            datainfo.setQdata_value(qdata_value);
            datainfo.setTdata_value(tdata_value);
            datainfo.setXdata_value(xdata_value);
            datainfo.setLongitude(longitude);
            datainfo.setLatitude(latitude);
            datainfo.setInfraredImages(infraredImages);
           // datainfo.setWholeimg(quanImages);
            datainfo.setBadimg(queImages);
            datainfo.setNumberimg(jinImages);
            jfDatainfoService.insert(datainfo);


            String tevCount = json.getString("tevCount");
            String tevMax = json.getString("tevMax");
            String tevPhaseData = json.getString("tevPhaseData");
            JfTevdatainfo tevdatainfo=new JfTevdatainfo();
            tevdatainfo.setStationname(stationName);
            tevdatainfo.setStationnumber(stationCode);
            tevdatainfo.setCheckstation(point_name);
            tevdatainfo.setChecknumber(point_num);
            tevdatainfo.setBackupthree(temperature);
            tevdatainfo.setBackupfour(humidity);
            tevdatainfo.setBackupfive(tdata_value);
            tevdatainfo.setBackupsix(harmType);
            jfTevdatainfoService.insert(tevdatainfo);


        return R.ok();

    }








    @RequestMapping(value = "/acceptaeHttpPost", method = RequestMethod.POST,produces="application/json;charset=utf-8")
    @ResponseBody
    public R acceptaeHttpPost(@RequestBody String desJson){

        //接受推送过来的数据
        System.out.println("入参数据:"+desJson);
        //  System.out.println("接受入参FILE:"+file.getSize()+"otherdesc:"+otherDesc);

        JSONObject json = JSONObject.parseObject(desJson);
        //数据类型0 ae 1 tev
        String type = json.getString("type");
        JfDatainfo jfDatainfo=new JfDatainfo();
        //检测名称
        String name = json.getString("name");
        //站点名称
        String stationName = json.getString("E_name");
        //站点编码
        String stationCode = json.getString("E_num");
        //天气
        String weather = json.getString("weather");
        //测点名称
        String point_name = json.getString("point_name");
        //测点编码
        String point_num = json.getString("point_num");
        //温度
        String temperature = json.getString("temperature");
        //湿度
        String humidity = json.getString("humidity");
        //经度
        String longitude = json.getString("longitude");
        //纬度
        String latitude = json.getString("latitude");
        //
        String time = json.getString("time");
        //0AE类型数据   1TEV数据类型

        //增益
        String gain = json.getString("gain");
        //平均值
        String avedb = json.getString("avedb");
        //最大值
        String max_value = json.getString("max_value");
        //特征数据
        String tdata_value = json.getString("tdata_value");
        //相位数据
        String xdata_value = json.getString("xdata_value");
        //飞行数据
        String fdata_value = json.getString("fdata_value");
        //事实数据
        String qdata_value = json.getString("qdata_value");
        String distance = json.getString("distance");
        //仪器厂家
        String deviceHome = json.getString("deviceHome");
        //水平档距
        String levelDistance = json.getString("levelDistance");
        //塔干种类
        String poleType = json.getString("poleType");
        //接地电阻
        String groundResistance = json.getString("groundResistance");
        //交叉跨越
        String lineDirection = json.getString("lineDirection");
        //检测解脱
        String testResult = json.getString("testResult");
        //缺陷类型
        String harmType = json.getString("harmType");
        //全景图
        String quanImages = json.getString("quanImages");
        //近景图
        String jinImages = json.getString("jinImages");
        //缺陷图
        String queImages = json.getString("queImages");
        //infraredImages
        String infraredImages = json.getString("infraredImages");
        //初始化jf信息数据入库
        JfDatainfo datainfo=new JfDatainfo();
        datainfo.setTemperature(temperature);
        datainfo.setStationname(stationName);
        datainfo.setStationnumber(stationCode);
        datainfo.setHumidity(humidity);
        datainfo.setMaxdb(max_value);
        datainfo.setAvedb(avedb);
        datainfo.setDeviceHome(deviceHome);
        datainfo.setTestResult(testResult);
        datainfo.setTime(time);
        datainfo.setHarmType(harmType);
        datainfo.setLevelDistance(levelDistance);
        datainfo.setLineDirection(lineDirection);
        datainfo.setPoleType(poleType);
        datainfo.setGroundResistance(groundResistance);
        datainfo.setFdata_value(fdata_value);
        datainfo.setQdata_value(qdata_value);
        datainfo.setTdata_value(tdata_value);
        datainfo.setXdata_value(xdata_value);
        datainfo.setLongitude(longitude);
        datainfo.setLatitude(latitude);
        datainfo.setInfraredImages(infraredImages);
        datainfo.setWholeimg(quanImages);
        datainfo.setBadimg(queImages);
        datainfo.setNumberimg(jinImages);
        jfDatainfoService.insert(datainfo);


        String tevCount = json.getString("tevCount");
        String tevMax = json.getString("tevMax");
        String tevPhaseData = json.getString("tevPhaseData");
        JfTevdatainfo tevdatainfo=new JfTevdatainfo();
        tevdatainfo.setStationname(stationName);
        tevdatainfo.setStationnumber(stationCode);
        tevdatainfo.setCheckstation(point_name);
        tevdatainfo.setChecknumber(point_num);
        tevdatainfo.setBackupthree(temperature);
        tevdatainfo.setBackupfour(humidity);
        tevdatainfo.setBackupfive(tdata_value);
        tevdatainfo.setBackupsix(harmType);
        jfTevdatainfoService.insert(tevdatainfo);


        return R.ok();

    }
}
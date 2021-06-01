package cn.best.approveservice.controller;

import cn.best.approveservice.common.utils.GetLocation;
import cn.best.approveservice.common.utils.UUIDGenerator;
import cn.best.approveservice.entity.JfDatainfo;
import cn.best.approveservice.response.Response;
import cn.best.approveservice.service.JfDatainfoService;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * (JfDatainfo)表控制层
 *
 * @author makejava
 * @since 2021-04-20 14:02:07
 */
@RestController
@RequestMapping("jfDatainfo")
public class JfDatainfoController extends BaseController {
    /**
     * 服务对象
     */
    @Autowired
    private JfDatainfoService jfDatainfoService;

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping("selectOne")
    public JfDatainfo selectOne(Integer id) {
        return this.jfDatainfoService.queryById(id);
    }

    @RequestMapping(value = "/queryAllByLimit")
    @ResponseBody
    public Response queryAllByLimit(JfDatainfo jfDatainfo, @RequestParam(defaultValue = "1") Integer pageNum
            , @RequestParam(defaultValue = "10") Integer pageSize) {
        List<JfDatainfo> list = jfDatainfoService.queryAllByLimit(jfDatainfo, pageNum, pageSize);
        Map<String, Object> data = new HashMap<>();
        data.put("list", list);
        data.put("total", ((Page) list).getTotal());
        Response response = new Response();
        return response.success(data);
    }

    @RequestMapping("/deleteOne")
    public void deleteById(@RequestParam("id") Integer id) {
        this.jfDatainfoService.deleteById(id);
    }

    @RequestMapping(value = "/update")
    @ResponseBody
    public Response upload(@RequestParam("numberimgfile") MultipartFile[] numberimgfile, @RequestParam("wholeimgfile") MultipartFile[] wholeimgfile, @RequestParam("badimgfile") MultipartFile[] badimgfile, @RequestParam("id") Integer id, @RequestParam("time") String time,
                           @RequestParam("stationname") String stationname, @RequestParam("stationnumber") String stationnumber, @RequestParam("temperature") String temperature,
                           @RequestParam("humidity") String humidity,
                           @RequestParam("arrnumberimg") String[] arrnumberimg,
                           @RequestParam("arrwholeimg") String[] arrwholeimg,
                           @RequestParam("arrbadimg") String[] arrbadimg,
                           @RequestParam("strnumberflg") String strnumberflg,
                           @RequestParam("strwholeflg") String strwholeflg,
                           @RequestParam("strbadflg") String strbadflg) throws Exception {
        Response response = new Response();
        Map<String, Object> data = new HashMap<>();
        JfDatainfo jfDatainfo = new JfDatainfo();
        String a = "static/content/image/";
        String paths = "/approveWeb/web-inf/static/content/image";
        JfDatainfo one = jfDatainfoService.queryById(id);
        File file = new File("");
        String filePath = file.getCanonicalPath() + paths;

        //编号照片
        String strnumberimg = "";
        String strremovenumberimg = "";

        if (numberimgfile.length > 0) {

            for (int i = 0; i < numberimgfile.length; i++) {
                String uuid = UUIDGenerator.getUUID().toUpperCase();
                String[] strArray = numberimgfile[i].getOriginalFilename().split("\\.");
                int suffixIndex = strArray.length - 1;
                String fileType = strArray[suffixIndex];
                String filename = uuid + "." + fileType;
                strnumberimg += "," + a + filename;
                String ff = filePath + File.separator + filename;
                numberimgfile[i].transferTo(Paths.get(ff));
            }
            if (arrnumberimg.length == 1) {

                for (int bianla = 0; bianla < arrnumberimg.length; bianla++) {
                    int arrcountnumber = arrnumberimg[bianla].indexOf("static");
                    strremovenumberimg += arrnumberimg[bianla].substring(arrcountnumber);
                }
            }
            if (arrnumberimg.length > 1) {
                for (int bianla = 0; bianla < arrnumberimg.length; bianla++) {
                    int arrcountnumber = arrnumberimg[bianla].indexOf("static");
                    strremovenumberimg += "," + arrnumberimg[bianla].substring(arrcountnumber);
                }
            }
            if (arrnumberimg.length == 0 && strnumberflg.equals("true")) {
                strremovenumberimg = "";
                strnumberimg = strnumberimg.substring(1);
            }
            if (arrnumberimg.length == 0 && strnumberflg.equals("false")) {
                strremovenumberimg = one.getNumberimg();
            }
            jfDatainfo.setNumberimg(strremovenumberimg + strnumberimg);
        } else {
            if (arrnumberimg.length == 1) {
                for (int bianla = 0; bianla < arrnumberimg.length; bianla++) {
                    int arrcountwhole = arrnumberimg[bianla].indexOf("static");
                    strnumberimg += arrnumberimg[bianla].substring(arrcountwhole);
                }
                jfDatainfo.setNumberimg(strnumberimg);
            } else {
                for (int bianla = 0; bianla < arrnumberimg.length; bianla++) {
                    int arrcountwhole = arrnumberimg[bianla].indexOf("static");
                    strnumberimg += "," + arrnumberimg[bianla].substring(arrcountwhole);
                }
                jfDatainfo.setNumberimg(strnumberimg);
            }
        }
        //整体照片
        String strwholeimg = "";
        String strremovewholeimg = "";
        if (wholeimgfile.length > 0) {
            for (int i = 0; i < wholeimgfile.length; i++) {
                String uuid = UUIDGenerator.getUUID().toUpperCase();
                String[] strArray = wholeimgfile[i].getOriginalFilename().split("\\.");
                int suffixIndex = strArray.length - 1;
                String fileType = strArray[suffixIndex];
                String filename = uuid + "." + fileType;
                strwholeimg += "," + a + filename;
                String ff = filePath + File.separator + filename;
                wholeimgfile[i].transferTo(Paths.get(ff));
            }
            if (arrwholeimg.length == 1) {
                for (int bianla = 0; bianla < arrwholeimg.length; bianla++) {
                    int arrcountnumber = arrwholeimg[bianla].indexOf("static");
                    strremovewholeimg += arrwholeimg[bianla].substring(arrcountnumber);
                }
            }
            if (arrwholeimg.length > 1) {
                for (int bianla = 0; bianla < arrwholeimg.length; bianla++) {
                    int arrcountnumber = arrwholeimg[bianla].indexOf("static");
                    strremovewholeimg += "," + arrwholeimg[bianla].substring(arrcountnumber);
                }
            }
            if (arrwholeimg.length == 0 && strwholeflg.equals("true")) {
                strremovewholeimg = "";
                strwholeimg = strwholeimg.substring(1);
            }
            if (arrwholeimg.length == 0 && strwholeflg.equals("false")) {
                strremovewholeimg = one.getWholeimg();
            }
            jfDatainfo.setWholeimg(strremovewholeimg + strwholeimg);

        } else {
            if (arrwholeimg.length == 1) {
                for (int bianla = 0; bianla < arrwholeimg.length; bianla++) {
                    int arrcountwhole = arrwholeimg[bianla].indexOf("static");
                    strwholeimg += arrwholeimg[bianla].substring(arrcountwhole);
                }
                jfDatainfo.setWholeimg(strwholeimg);
            } else {
                for (int bianla = 0; bianla < arrwholeimg.length; bianla++) {
                    int arrcountwhole = arrwholeimg[bianla].indexOf("static");
                    strwholeimg += "," + arrwholeimg[bianla].substring(arrcountwhole);
                }
                jfDatainfo.setWholeimg(strwholeimg);
            }
        }

        //不良照片
        String strbadimg = "";
        String strremovebadimg = "";
        if (badimgfile.length > 0) {
            for (int i = 0; i < badimgfile.length; i++) {
                String uuid = UUIDGenerator.getUUID().toUpperCase();
                String[] strArray = badimgfile[i].getOriginalFilename().split("\\.");
                int suffixIndex = strArray.length - 1;
                String fileType = strArray[suffixIndex];
                String filename = uuid + "." + fileType;
                strbadimg += "," + a + filename;
                String ff = filePath + File.separator + filename;
                badimgfile[i].transferTo(Paths.get(ff));
            }
            if (arrbadimg.length == 1) {
                for (int bianla = 0; bianla < arrbadimg.length; bianla++) {
                    int arrcountbad = arrbadimg[bianla].indexOf("static");
                    strremovebadimg += arrbadimg[bianla].substring(arrcountbad);
                }
            }
            if (arrbadimg.length > 1) {
                for (int bianla = 0; bianla < arrbadimg.length; bianla++) {
                    int arrcountbad = arrbadimg[bianla].indexOf("static");
                    strremovebadimg += "," + arrbadimg[bianla].substring(arrcountbad);
                }
            }
            if (arrbadimg.length == 0 && strbadflg.equals("true")) {
                strremovebadimg = "";
                strbadimg = strbadimg.substring(1);
            }
            if (arrbadimg.length == 0 && strbadflg.equals("false")) {
                strremovebadimg = one.getBadimg();
            }
            jfDatainfo.setBadimg(strremovebadimg + strbadimg);
        } else {
            if (arrbadimg.length == 1) {
                for (int bianla = 0; bianla < arrbadimg.length; bianla++) {
                    int arrcountwhole = arrbadimg[bianla].indexOf("static");
                    strbadimg += arrbadimg[bianla].substring(arrcountwhole);
                }
                jfDatainfo.setBadimg(strbadimg);
            } else {
                for (int bianla = 0; bianla < arrbadimg.length; bianla++) {
                    int arrcountwhole = arrbadimg[bianla].indexOf("static");
                    strbadimg += "," + arrbadimg[bianla].substring(arrcountwhole);
                }
                jfDatainfo.setBadimg(strbadimg);
            }
        }
        jfDatainfo.setId(id);
        jfDatainfo.setTime(time);
        jfDatainfo.setStationname(stationname);
        jfDatainfo.setStationnumber(stationnumber);
        jfDatainfo.setTemperature(temperature);
        jfDatainfo.setHumidity(humidity);
        int count = jfDatainfoService.update(jfDatainfo);
        if (!(count == 0)) {
            data.put("code", 1);
        }
        return response.success(data);
    }

    @RequestMapping(value = "/searchBreakDownList")
    @ResponseBody
    public Map<String, Object> searchBreakDownList(HttpServletRequest request) {
        Map<String, String> params = new HashMap<>();
        params.put("kind", request.getParameter("kind"));
        params.put("state", request.getParameter("state"));
        params.put("lineNameInput", request.getParameter("lineNameInput"));
        params.put("lineNoInput", request.getParameter("lineNoInput"));
        params.put("flawInput", request.getParameter("flawInput"));
        params.put("startTime", request.getParameter("startTime"));
        params.put("endTime", request.getParameter("endTime"));
        params.put("area", request.getParameter("area"));
        params.put("userId", request.getParameter("userId"));
        params.put("index", request.getParameter("index"));
        params.put("pageSize", request.getParameter("pageSize"));
        params.put("currentPage", request.getParameter("currentPage"));
        List<JfDatainfo> data = jfDatainfoService.searchBreakDownList(params);
        Map<String, Object> result = new HashMap<>();
        result.put("data", data);
        result.put("totalCount", ((Page) data).getTotal());
        return result;
    }

    @RequestMapping(value = "/selectCompanyName")
    @ResponseBody
    public Map<String, Object> selectCompanyName(HttpServletRequest request) {
        String companyName = request.getParameter("companyName");
        Integer currentPage = Integer.valueOf(request.getParameter("currentPage"));
        Integer pageSize = Integer.valueOf(request.getParameter("pageSize"));

        List<Map> data = jfDatainfoService.selectCompanyName(companyName, currentPage, pageSize);
        Map<String, Object> result = new HashMap<>();
        result.put("data", data);
        result.put("totalCount", ((Page) data).getTotal());
        return result;
    }

    @RequestMapping(value = "/selectAreaName")
    @ResponseBody
    public Map<String, Object> selectAreaName(HttpServletRequest request) throws IOException {
        String companyName = request.getParameter("companyName");
        String areaName = request.getParameter("area");
        Integer currentPage = Integer.valueOf(request.getParameter("currentPage"));
        Integer pageSize = Integer.valueOf(request.getParameter("pageSize"));

        List<Map> data = jfDatainfoService.selectAreaName(companyName, areaName, currentPage, pageSize);
        Map<String, Object> result = new HashMap<>();
        result.put("data", data);
        result.put("totalCount", ((Page) data).getTotal());
        return result;
    }

    @RequestMapping(value = "/breakDownSubmit")
    @ResponseBody
    public String breakDownSubmit(@RequestParam("entiretyImges")MultipartFile[] entiretyImges,
                                  @RequestParam("badnessImges")MultipartFile[] badnessImges,
                                  @RequestParam("NoImges")MultipartFile[] NoImges,
                                  @RequestParam("reEntiretyImges")String[] reEntiretyImges,
                                  @RequestParam("reNoImges")String[] reNoImges,
                                  @RequestParam("reBadnessImges")String[] reBadnessImges,
                                  @RequestParam()Map params,
                                  HttpServletRequest request) {
//        Date date = new Date();
//        SimpleDateFormat ft = new SimpleDateFormat ("yyyy-MM-dd hh:mm:ss");
//        String now = ft.format(date);
        JfDatainfo jfDatainfo = new JfDatainfo();
        String entiretyString = "";
        String NoString = "";
        String badnessString = "";
        String resEntiretyImges = params.get("resEntiretyImges").toString();
        String resNoImges = params.get("resNoImges").toString();
        String resBadnessImges = params.get("resBadnessImges").toString();

        jfDatainfoService.fileDelete(reEntiretyImges, reNoImges, reBadnessImges);

        if (entiretyImges.length > 0) {
            for (int i = 0; i < entiretyImges.length; i++) {
                entiretyString = jfDatainfoService.breakDownUpload(entiretyImges[i], request, params.get("id").toString());
                if (null != resEntiretyImges && !"".equals(resEntiretyImges)) {
                    resEntiretyImges += "," + entiretyString;
                } else {
                    if (i == 0) {
                        resEntiretyImges += entiretyString;
                    } else {
                        resEntiretyImges += "," + entiretyString;
                    }
                }
            }
        }
        if (NoImges.length > 0) {
            for (int i = 0; i < NoImges.length; i++) {
                NoString = jfDatainfoService.breakDownUpload(NoImges[i], request, params.get("id").toString());
                if (null != resNoImges && !"".equals(resNoImges)) {
                    resNoImges += "," + NoString;
                } else {
                    if (i == 0) {
                        resNoImges += NoString;
                    } else {
                        resNoImges += "," + NoString;
                    }
                }
            }
        }
        if (badnessImges.length > 0) {
            for (int i = 0; i < badnessImges.length; i++) {
                badnessString = jfDatainfoService.breakDownUpload(badnessImges[i], request, params.get("id").toString());
                if (null != resBadnessImges && !"".equals(resBadnessImges)) {
                    resBadnessImges += "," + badnessString;
                } else {
                    if (i == 0) {
                        resBadnessImges += badnessString;
                    } else {
                        resBadnessImges += "," + badnessString;
                    }
                }
            }
        }

        jfDatainfo.setBadimg(resBadnessImges);
        jfDatainfo.setNumberimg(resNoImges);
        jfDatainfo.setWholeimg(resEntiretyImges);
        jfDatainfo.setId(Integer.valueOf(params.get("id").toString()));
        jfDatainfo.setStationname(params.get("stationname").toString());
        jfDatainfo.setStationnumber(params.get("stationnumber").toString());

        jfDatainfoService.update(jfDatainfo);
        return "success";
    }

    @RequestMapping(value = "/mapBase64")
    @ResponseBody
    public String mapBase64(HttpServletRequest request) {
        String base64 = jfDatainfoService.mapBase64(request.getParameter("mapSrc"));
        return base64.replaceAll("\r|\n","");
    }
}

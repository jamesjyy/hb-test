package cn.best.approveservice.controller;

import cn.best.approveservice.entity.JfDatainfo;
import cn.best.approveservice.entity.JfTEVSupData;
import cn.best.approveservice.entity.JfTevdatainfo;
import cn.best.approveservice.response.Response;
import cn.best.approveservice.service.JfDatainfoService;
import cn.best.approveservice.service.JfTevdatainfoService;
import com.github.pagehelper.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.xml.crypto.Data;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * (JfTevdatainfo)表控制层
 *
 * @author makejava
 * @since 2021-04-23 10:36:42
 */
@RestController
@RequestMapping("jfTevdatainfo")
public class JfTevdatainfoController {
    /**
     * 服务对象
     */
    @Autowired
    private JfTevdatainfoService jfTevdatainfoService;

    @Autowired
    private JfDatainfoService jfDatainfoService;

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping("selectOne")
    public JfTevdatainfo selectOne(Integer id) {
        return this.jfTevdatainfoService.queryById(id);
    }


    /**
     * 删除公司下面的所有数据
     *
     * @return 单条数据
     */
    @RequestMapping("/deleteSupOne")
    public JfTevdatainfo deleteSupOne(HttpServletRequest request) {
        String id = request.getParameter("id");
        String companyname = request.getParameter("companyname");
        return this.jfTevdatainfoService.deleteSupOne(id,companyname);
    }


    /**
     * 二级页面
     * @param userid        用户ID
     * @param companyname  公司名称
     * @param rolelist     权限
     * @param pageNum
     * @param pageSize
     * @return
     */
    @RequestMapping(value = "/queryAllByLimit")
    @ResponseBody
    public Response queryAllByLimit(@RequestParam("id") Integer userid,
                                    @RequestParam("companyname") String companyname,
                                    @RequestParam("rolelist") String rolelist,
                                    @RequestParam(defaultValue = "1") Integer pageNum,
                                    @RequestParam(defaultValue = "10") Integer pageSize) {
        List<JfTevdatainfo> list = jfTevdatainfoService.queryAllByLimit(userid, companyname,rolelist,pageNum, pageSize);
        Map<String, Object> data = new HashMap<>();
        data.put("list", list);
        data.put("total", list.size());
        Response response = new Response();
        return response.success(data);
    }

    @RequestMapping("/deleteOne")
    public void deleteById(@RequestParam("id") Integer id) {
        this.jfTevdatainfoService.deleteById(id);
    }


    /**
     * 更新数据
     *
     * @param params
     * @param imgs
     * @param request
     * @return
     */
    @RequestMapping("/update")
    @ResponseBody
    public Response updateById(@RequestParam() Map params, @RequestParam("img") MultipartFile[] imgs, HttpServletRequest request) {


        String img = "";
        String imgRear = "";
        String imgsRear = params.get("img").toString();

        if (imgs.length > 0) {
            for (int i = 0; i < imgs.length; i++) {
                img = jfDatainfoService.breakDownUpload(imgs[i], request, params.get("id").toString());
                if (null != imgsRear && !"".equals(imgsRear)) {
                    imgsRear += "," + img;
                } else {
                    if (i == 0) {
                        imgsRear += img;
                    } else {
                        imgsRear += "," + img;
                    }
                }
            }
        }

        String[] splits = imgsRear.split(",");
        for (int i = 0; i < splits.length; i++) {
            String[] split = splits[i].split("8082/");
            if (i == splits.length - 1) {
                imgRear += split[1];
            } else {
                imgRear += split[1] + ",";
            }
        }


        JfTevdatainfo jfTevdatainfo = new JfTevdatainfo();
        jfTevdatainfo.setId(Integer.valueOf(params.get("id").toString()));
        jfTevdatainfo.setImg(imgRear);
        jfTevdatainfo.setTime((String) params.get("time"));
        jfTevdatainfo.setStationname((String) params.get("stationname"));
        jfTevdatainfo.setStationnumber((String) params.get("stationnumber"));
        jfTevdatainfo.setCheckstation((String) params.get("checkstation"));
        jfTevdatainfo.setChecknumber((String) params.get("checknumber"));
        jfTevdatainfo.setCountnumber(Integer.valueOf(params.get("countnumber").toString()));
        jfTevdatainfo.setPeakvalue((String) params.get("peakvalue"));

        jfTevdatainfoService.update(jfTevdatainfo);

        Response response = new Response();
        Map<String, Object> data = new HashMap<>();
        data.put("code", "1");
        return response.success(data);
    }

    /***
     * 查询报告页面的数据
     * @param id
     */
    @RequestMapping("/queryReportData")
    public Response queryReportData(@RequestParam("id") Integer id) {
//        根据传递过来的iD来进行查询数据库的数据
        JfTevdatainfo jfTevdatainfo = this.jfTevdatainfoService.queryReportData(id);
        Map<String, Object> data = new HashMap<>();
        data.put("jfTevdate", jfTevdatainfo);
        Response response = new Response();
        return response.success(data);
    }


    /***
     * 查询分析页面的数据
     */

    @RequestMapping("/queryAnalyseData")

    public Response queryAnalyseData(@RequestParam("id") Integer id) {
//        HttpServletRequest request
//        String id = request.getParameter("id");

        Map<String, List> data = this.jfTevdatainfoService.queryAnalyseData(id);
        Response response = new Response();
        return response.success(data);
    }


//


    @RequestMapping("/queryRedactData")
    public Response queryRedactData(@RequestParam("id") Integer id) {
//        根据传递过来的iD来进行查询数据库的数据
        JfTevdatainfo jfTevdatainfo = this.jfTevdatainfoService.queryRedactData(id);
        Map<String, Object> data = new HashMap<>();
        data.put("list", jfTevdatainfo);
        Response response = new Response();
        return response.success(data);
    }

    /**
     * 查询公司名称和站点数量views
     *
     * @param userid
     * @param pageNum
     * @param pageSize
     * @return
     */
    @RequestMapping("/views")
    public Response querySupData(@RequestParam("id") Integer userid,
                                 @RequestParam("companyname") String companyname,
                                 @RequestParam(defaultValue = "1") Integer pageNum
            , @RequestParam(defaultValue = "10") Integer pageSize) {
//        根据传递过来的iD来进行查询数据库的数据
        List<JfTEVSupData> list = this.jfTevdatainfoService.querySupData(userid, companyname, pageNum, pageSize);
        Map<String, Object> data = new HashMap<>();
        data.put("list", list);
        data.put("total", list.size());
        Response response = new Response();
        return response.success(data);
    }
}

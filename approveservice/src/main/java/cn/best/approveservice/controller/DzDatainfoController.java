package cn.best.approveservice.controller;

import cn.best.approveservice.entity.DyDatainfo;
import cn.best.approveservice.entity.DzDatainfo;
import cn.best.approveservice.entity.User;
import cn.best.approveservice.response.Response;
import cn.best.approveservice.service.DzDatainfoService;
import com.github.pagehelper.Page;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * (DzDatainfo)表控制层
 *
 * @author makejava
 * @since 2021-04-22 16:25:25
 */
@RestController
@RequestMapping("dzDatainfo")
public class DzDatainfoController {
    /**
     * 服务对象
     */
    @Resource
    private DzDatainfoService dzDatainfoService;

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping("selectOne")
    public DzDatainfo selectOne(Integer id) {
        return this.dzDatainfoService.queryById(id);
    }


    @RequestMapping(value = "/queryAllByLimit")
    @ResponseBody
    public Response queryAllByLimit(DzDatainfo dzDatainfo, @RequestParam(defaultValue = "1") Integer pageNum
            , @RequestParam(defaultValue = "10") Integer pageSize) {
        List<DzDatainfo> list = dzDatainfoService.queryAllByLimit(dzDatainfo, pageNum, pageSize);
        Map<String, Object> data = new HashMap<>();
        data.put("list", list);
        data.put("total", ((Page) list).getTotal());
        Response response = new Response();
        return response.success(data);
    }

    @RequestMapping(value = "/DzAdminqueryAllByLimit")
    @ResponseBody
    public Response AdminqueryAllByLimit(User user, @RequestParam(defaultValue = "1") Integer pageNum
            , @RequestParam(defaultValue = "10") Integer pageSize) {
        List<User> list = dzDatainfoService.DzAdminqueryAllByLimit(user, pageNum, pageSize);
        Map<String, Object> data = new HashMap<>();
        data.put("list", list);
        data.put("total", ((Page) list).getTotal());
        Response response = new Response();
        return response.success(data);
    }

    @RequestMapping(value = "/PtuserqueryAllByLimit")
    @ResponseBody
    public Response PtuserqueryAllByLimit(User user, @RequestParam(defaultValue = "1") Integer pageNum
            , @RequestParam(defaultValue = "10") Integer pageSize) {
        List<User> list = dzDatainfoService.PtuserqueryAllByLimit(user, pageNum, pageSize);
        Map<String, Object> data = new HashMap<>();
        data.put("list", list);
        data.put("total", ((Page) list).getTotal());
        Response response = new Response();
        return response.success(data);
    }


    @RequestMapping(value = "/dzqueryloopAllByLimit")
    @ResponseBody
    public Response queryloopAllByLimit(String companyName, String loopname, @RequestParam(defaultValue = "1") Integer pageNum, @RequestParam(defaultValue = "10") Integer pageSize) {
        List<DzDatainfo> list = dzDatainfoService.dzqueryloopAllByLimit(companyName, loopname, pageNum, pageSize);
        Map<String, Object> data = new HashMap<>();
        data.put("list", list);
        data.put("total", ((Page) list).getTotal());
        Response response = new Response();
        return response.success(data);
    }

    //根据id删除数据
    @RequestMapping(value = "/deletebyid")
    @ResponseBody
    public Response deleteByid(@RequestParam("id") Integer id) {
        Boolean flg = dzDatainfoService.deleteById(id);
        Map<String, Object> data = new HashMap<>();
        data.put("flg", flg);
        Response response = new Response();
        return response.success(data);
    }

    /**
     * 电阻数据上传保存方法
     *
     * @param request
     * @return
     * @throws ParseException
     */
    @RequestMapping(value = "/upLoad")
    @ResponseBody
    public Map<String, Object> upLoad(@RequestBody Map request) throws ParseException {
        Map<String, Object> params = new HashMap();

        if (null != request.get("detectionDate") && !request.get("detectionDate").equals("")) {
            String date = request.get("detectionDate").toString().replace("Z", " UTC");
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS Z");
            SimpleDateFormat defaultFormat = new SimpleDateFormat("yyyy-MM-dd");
            Date detectionDate = format.parse(date);
            params.put("detectionDate", defaultFormat.format(detectionDate));
        }

        params.put("line", request.get("line"));
        params.put("userId", request.get("userId"));
        List<Map> fileList = (List) request.get("fileList");

        Map<String, Object> data = new HashMap<>();
        dzDatainfoService.saveUpload(params, fileList);
        return data;
    }

    /**
     * 电阻文件上传
     *
     * @param file
     * @param request
     * @return
     * @throws IOException
     */
    @RequestMapping("/upLoadFile")
    @ResponseBody
    public Map uploadFile(@RequestParam("file") MultipartFile file, HttpServletRequest request) throws IOException {
        return dzDatainfoService.uploadFile(file, request);
    }

}

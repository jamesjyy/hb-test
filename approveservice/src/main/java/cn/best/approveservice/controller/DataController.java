package cn.best.approveservice.controller;

import cn.best.approveservice.entity.DataInfo;
import cn.best.approveservice.service.DataInfoService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Api(tags = "上传文件管理")
@RestController
@RequestMapping("/datainfo")
public class DataController {
    @Autowired
    private DataInfoService dataInfoService;

    @RequestMapping(value = "/views")
    @ResponseBody
    public Map<String, Object> checkviews() {
        Map<String, Object> data = new HashMap<>();
        Integer offset = 1;
        Integer limit = 2;
        List<DataInfo> list = dataInfoService.queryAllByLimit(offset, limit);
        data.put("list", list);
        return data;
    }

    @RequestMapping(value = "/upLoad")
    @ResponseBody
    public Map<String, Object> upLoad(@RequestBody Map request) throws ParseException {
        Map<String, Object> params = new HashMap();

        if (null != request.get("detectionDate") && !request.get("detectionDate").equals("")) {
            String date = request.get("detectionDate").toString().replace("Z", " UTC");
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS Z");
            SimpleDateFormat defaultFormat  = new SimpleDateFormat("yyyy-MM-dd");
            Date detectionDate = format.parse(date);
            params.put("detectionDate", defaultFormat.format(detectionDate));
        }

        params.put("line", request.get("line"));
        List<Map> fileList = (List) request.get("fileList");

        Map<String, Object> data = new HashMap<>();
        dataInfoService.saveUpload(params, fileList);
        return data;
    }

    @RequestMapping("/upLoadFile")
    @ResponseBody
    public Map uploadFile(@RequestParam("file") MultipartFile file, HttpServletRequest request) throws IOException {
        return dataInfoService.uploadFile(file, request);
    }
}


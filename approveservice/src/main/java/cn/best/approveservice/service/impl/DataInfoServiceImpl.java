package cn.best.approveservice.service.impl;

import cn.best.approveservice.dao.DataInfoDao;
import cn.best.approveservice.dao.DyDataDao;
import cn.best.approveservice.dao.DyDatainfoDao;
import cn.best.approveservice.entity.DataInfo;
import cn.best.approveservice.entity.DyData;
import cn.best.approveservice.entity.DyDatainfo;
import cn.best.approveservice.service.DataInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class DataInfoServiceImpl implements DataInfoService {

    @Autowired
    private DataInfoDao dataInfoDao;

    @Autowired
    private DyDatainfoDao dyDatainfoDao;

    @Autowired
    private DyDataDao dyDataDao;

    @Override
    public List<DataInfo> queryAllByLimit(int offset, int limit) {
        List<DataInfo> list = dataInfoDao.queryAllByLimit(offset, limit);
        return list;
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
                dyDataInfo.setUserid("toString");
                dyDataInfo.setResult(null != fileList.get(i).get("dataText") ? fileList.get(i).get("dataText").toString() : null);
                dyDatainfoDao.insertData(dyDataInfo);

                dyData.setLoopidtime(null != param.get("line") ? param.get("line").toString() : null);
                dyData.setTime(null != param.get("detectionDate") ? param.get("detectionDate").toString() : null);
                dyData.setTowernum(null != fileList.get(i).get("tower") ? fileList.get(i).get("tower").toString() : null);
                dyData.setPhase(null != fileList.get(i).get("phase") ? fileList.get(i).get("phase").toString() : null);
                dyData.setUserid("toString");
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
            String filePath = ResourceUtils.getURL("classpath:").getPath() + "static/files";
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
                String url = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + "/target/classes/static/files/" + file.getOriginalFilename();
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

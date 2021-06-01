package cn.best.approveservice.service;

import cn.best.approveservice.dao.DataInfoDao;
import cn.best.approveservice.entity.DataInfo;
import org.apache.ibatis.annotations.Param;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;
import java.util.Map;

public interface DataInfoService {

    List<DataInfo> queryAllByLimit(@Param("offset") int offset, @Param("limit") int limit);

    void saveUpload(Map param, List<Map> fileList);

    Map uploadFile(MultipartFile file, HttpServletRequest request) throws IOException;

}

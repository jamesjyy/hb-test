package cn.best.approveservice.dao;
import cn.best.approveservice.entity.DataInfo;
import cn.best.approveservice.entity.DyDatainfo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import java.util.List;
@Repository
public interface DataInfoDao {

    List<DataInfo> queryAllByLimit(@Param("offset") int offset, @Param("limit") int limit);    /**
     * 新增数据
     *
     * @param datainfo 实例对象
     * @return 影响行数
     */
    int insert(DyDatainfo datainfo);

}

package cn.best.approveservice.controller;

import cn.best.approveservice.entity.*;
import cn.best.approveservice.response.Response;
import cn.best.approveservice.service.DyDataService;
import com.github.pagehelper.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * (DyData)表控制层
 *
 * @author makejava
 * @since 2021-04-08 10:17:56
 */
@RestController
@RequestMapping("dyData")
public class DyDataController {

    @Autowired
    private Response response;

    /**
     * 服务对象
     */
    @Resource
    private DyDataService dyDataService;

    /**
     * 查询超管第一层级数据
     */
    @RequestMapping("/views")
    @ResponseBody
    public Response queryAll(
                             @RequestParam("companyname") String companyname
            , @RequestParam(defaultValue = "1") Integer pageNum
            , @RequestParam(defaultValue = "10") Integer pageSize) {
//
//        ,  @RequestParam("companyname") String companyname
        List<DyDataSup> list = dyDataService.queryAllByLimit( companyname, pageNum, pageSize);

        Map<String, Object> data = new HashMap<>();
        data.put("list", list);
        data.put("total", list.size());
        Response response = new Response();
        return response.success(data);

    }

    /**
     * 根据id进行删除,超管的删除,根据超管的id删除对应的所有数据
     *
     * @param
     */
    @RequestMapping("/deleteSupOne")
    public void deleteById(HttpServletRequest request) {
        String id = request.getParameter("id");
        String companyname = request.getParameter("companyname");
        this.dyDataService.deleteById(Integer.valueOf(id), companyname);
    }

    /**
     * 根据userid进行删除,超管的删除,根据超管的id删除对应的所有数据
     */
    @RequestMapping("/deleteManOne")
    public void deleteMagById(HttpServletRequest request) {
        String id = request.getParameter("id");
        String companyname = request.getParameter("companyname");
        String loopidtime = request.getParameter("loopidtime");
        this.dyDataService.deleteManById(Integer.valueOf(id), companyname,loopidtime);
    }

    /**
     * 根据userid进行删除,超管的删除,根据超管的id删除对应的所有数据
     */
    @RequestMapping("/deleteOne")
    public void deleteOne(HttpServletRequest request) {
        String id = request.getParameter("id");
        String loopidtime = request.getParameter("loopidname");
        String companyname = request.getParameter("companyname");
        String towernum = request.getParameter("towernum");
        this.dyDataService.deleteOne(id, companyname,loopidtime,towernum);
    }


    /**
     * 普通用户列表查询
     *
     * @return
     */
    @RequestMapping("/queryMagData")
    //相当于dyData表中的UserId
    //userId,
    public Response queryDyAnalyseAll(@RequestParam("id") String userid
                                      //线路名称
            , @RequestParam("loopidtime") String loopidtime
                                      //公司名称
            , @RequestParam("companyname") String companyname

            , @RequestParam(defaultValue = "1") Integer pageNum
            , @RequestParam(defaultValue = "10") Integer pageSize) {
//        System.out.println(id);
//        传递过来当前对应的公司的id也就是userId,通过UserId来进行查询
        List<DyDataMag> list = dyDataService.queryDyAnalyseAll(Integer.valueOf(userid), loopidtime, companyname, pageNum, pageSize);
        Map<String, Object> data = new HashMap<>();
        if (list == null) {
            return response.failure();
        }
        data.put("list", list);
        data.put("total", list.size());
        return response.success(data);
    }


    /**
     * 第三级杆塔数据列表查询
     *
     * @return
     */
    @RequestMapping("/queryChildData")
    //相当于dyData表中的UserId

    public Response queryChildData(@RequestParam("id") String userid
            , @RequestParam("companyname") String companyname
            , @RequestParam("loopidtime") String loopidtime
            , @RequestParam("role") String role
            , @RequestParam("towernum") String towernum
            , @RequestParam(defaultValue = "1") Integer pageNum
            , @RequestParam(defaultValue = "10") Integer pageSize) {
//        传递过来当前对应的公司的id也就是userId,通过UserId来进行查询

        Map<String, Object> data = dyDataService.queryDyChildAll(userid, companyname , loopidtime,role, towernum, pageNum, pageSize);
        return response.success(data);
    }


    /**
     * 三级页面查询下拉框数据
     *
     * @return
     */
    @RequestMapping("/queryChildPhase")
    //相当于dyData表中的UserId

    public Response queryChildPhase(@RequestParam("id") String userid
            , @RequestParam("companyname") String companyname
            , @RequestParam("loopidtime") String loopidtime
            , @RequestParam("towernum") String towernum
            , @RequestParam("role") String role

    ) {
//        传递过来当前对应的公司的id也就是userId,通过UserId来进行查询
        List<DyDataTowerAll> list = dyDataService.queryChildPhase(userid, companyname ,loopidtime, towernum,role);
        Map<String, List> data = new HashMap<>();
        data.put("list", list);
        return response.success(data);
    }



        /**
         * 三级页面查询下拉框数据/串号
         *
         * @return
         */
        @RequestMapping("/queryChildBunch")
        //相当于dyData表中的UserId

        public Response queryChildBunch(
                @RequestParam("id") String userid,
                @RequestParam("companyname") String companyname
                , @RequestParam("loopidtime") String loopidtime
                , @RequestParam("towernum") String towernum
                , @RequestParam("bunch") String bunch
                , @RequestParam("role") String role

    ) {
//        传递过来当前对应的公司的id也就是userId,通过UserId来进行查询
            List<DyDataTowerAll> list = dyDataService.queryChildBunch( userid,companyname ,loopidtime, towernum,bunch,role);
            Map<String, List> data = new HashMap<>();
            data.put("list", list);
            return response.success(data);
        }


        /**
         * 相限列表查询
         *
         * @param
         * @return
         */
    @RequestMapping("/queryResult")
    public Response queryResult(HttpServletRequest request) {
//        System.out.println(id);
        String id = request.getParameter("id");
        List<DyDatainfo> list = dyDataService.queryResult(Integer.valueOf(id));
        Map<String, Object> data = new HashMap<>();
        if (list == null) {
            return response.failure();
        }
        data.put("list", list);
        return response.success(data);
    }

    /**
     * 图表转换数据柱状图数据查询
     *
     * @param request
     * @return
     */
    @RequestMapping("/queryChart")
    public Response queryChart(HttpServletRequest request) {
//        公司名称,线路,杆塔号
        String id = request.getParameter("id");
        String companyname = request.getParameter("companyname");
        String loopname = request.getParameter("loopidname");
        String towernum = request.getParameter("towernum");
        DyChartData list = dyDataService.queryChart(id,companyname,loopname,towernum);
        Map<String, Object> data = new HashMap<>();
        if (list == null) {
            return response.failure();
        }
        data.put("list", list);
        return response.success(data);

    }


//    /**
//     * 故障列表查询
//     *
//     * @param request
//     * @return
//     */
//    @RequestMapping("/analyseDataAll")
//    public Response analyseDataAll(HttpServletRequest request) {
////        System.out.println(id);
//        String id = request.getParameter("id");
//        List<DyDataAnalyse> list = dyDataService.queryDyAnalyseAlls(Integer.valueOf(id));
//        Map<String, Object> data = new HashMap<>();
//        if (list == null) {
//            return response.failure();
//        }
//        data.put("list", list);
//        return response.success(data);
//
//    }


    /**
     * 杆塔图数据查询
     *
     * @param request
     * @return
     */
    @RequestMapping("/queryTower")
    public Response queryTower(HttpServletRequest request) {
//        System.out.println(id);
        String id = request.getParameter("id");
        String companyname = request.getParameter("companyname");
        String role = request.getParameter("role");
        String loopidname = request.getParameter("loopidname");
        String towernum = request.getParameter("towernum");
        String phaseRqs = request.getParameter("phaseRqs");
        String bunchRqs = request.getParameter("bunchRqs");
        List<String> list = dyDataService.queryTower(id ,companyname, role , loopidname ,towernum ,phaseRqs,bunchRqs);

        Map<String, Object> data = new HashMap<>();
        if (list == null) {
            return response.failure();
        }
        data.put("list", list);
        return response.success(data);
    }

}

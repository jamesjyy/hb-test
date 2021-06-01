package cn.best.approveservice.common.utils;

import cn.best.approveservice.entity.Permission;
import com.alibaba.fastjson.JSONObject;

import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 根据权限数据构建登录用户左侧菜单数据
 * </p>
 * @author gzy
 */
public class MemuHelper {

    /**
     * 构建菜单
     * @param treeNodes
     * @return
     */
    public static List<JSONObject> bulid(List<Permission> treeNodes) {
        List<JSONObject> childrens= new ArrayList<>();
        JSONObject oneMeuns = new JSONObject();
        List<JSONObject> meuns = new ArrayList<>();
        if(treeNodes.size() == 1) {
            Permission topNode = treeNodes.get(0);
            //左侧一级菜单
            List<Permission> oneMeunList = topNode.getChildren();
            for(Permission one :oneMeunList) {
                JSONObject oneMeun = new JSONObject();
                oneMeun.put("target", one.getTarget());
                oneMeun.put("href", one.getComponent());
                oneMeun.put("redirect", "noredirect");
                oneMeun.put("name", "name_"+one.getId());
                oneMeun.put("hidden", false);


                oneMeun.put("title", one.getName());
                oneMeun.put("icon", one.getIcon());

                childrens.add(oneMeun);
                List<JSONObject> children = new ArrayList<>();

                //一级下面的子菜单二级菜单
                List<Permission> twoMeunList = one.getChildren();
                for(Permission two :twoMeunList) {
                    JSONObject twoMeun = new JSONObject();
                    twoMeun.put("target", two.getTarget());
                    twoMeun.put("href", two.getComponent());
                    twoMeun.put("name", "name_"+two.getId());
                    twoMeun.put("hidden", false);
                    twoMeun.put("icon",two.getIcon());


                    twoMeun.put("title", two.getName());


                    children.add(twoMeun);
                    oneMeun.put("child",children);
                    //二级下面的子菜单三级菜单
                    List<JSONObject> children2 = new ArrayList<>();
                    List<Permission> threeMeunList = two.getChildren();
                    for(Permission three :threeMeunList) {
                        if(StringUtils.isEmpty(three.getComponent())) continue;

                        JSONObject threeMeun = new JSONObject();
                        threeMeun.put("target", three.getTarget());
                        threeMeun.put("href", three.getComponent());
                        threeMeun.put("name", "name_"+three.getId());
                        threeMeun.put("hidden", true);
                        threeMeun.put("icon",three.getIcon());


                        threeMeun.put("title", three.getName());

                        children2.add(threeMeun);
                        twoMeun.put("child",children2);
                    }
                }
                oneMeuns.put("child", childrens);
                meuns.add(oneMeuns);
            }
        }
        return meuns;
    }
}

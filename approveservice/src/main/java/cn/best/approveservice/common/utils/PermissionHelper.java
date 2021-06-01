package cn.best.approveservice.common.utils;



import cn.best.approveservice.entity.Permission;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * <p>
 * 根据权限数据构建菜单数据
 * </p>
 *
 * @author gzy
 */
public class PermissionHelper {

    /**
     * 使用递归方法建菜单
     * @param treeNodes
     * @return
     */
    public static List<Permission> bulid(List<Permission> treeNodes) {
        List<Permission> trees = new ArrayList<>();
        Permission permission=new Permission();
        if (treeNodes.get(0).getPid().equals("1")){
            permission.setPid("0");
            permission.setId("1");
            permission.setName("全部系统");
            treeNodes.add(0,permission);
        }
        //既不是顶级菜单也不是系统菜单
        if(!treeNodes.get(0).getPid().equals("1")&&!treeNodes.get(0).getPid().equals("0")){
            permission.setPid("0");
            permission.setId("1");
            permission.setName("全部系统");
            treeNodes.add(0,permission);
            Permission permission2=new Permission();
            permission2.setPid("1");
            permission2.setId(treeNodes.get(2).getPid());
            permission2.setName("绝缘子云分析系统");
            treeNodes.add(1,permission2);
        }
        for (Permission treeNode : treeNodes) {
            if ("0".equals(treeNode.getPid())||"1".equals(treeNode.getPid())) {
                if ("0".equals(treeNode.getPid())){
                    treeNode.setLevel(1);
                }else {
                    treeNode.setLevel(2);
                }

                trees.add(findChildren(treeNode,treeNodes));
                break;
            }
        }

        return trees;
    }

    /**
     * 递归查找子节点
     * @param treeNodes
     * @return
     */
    public static Permission findChildren(Permission treeNode,List<Permission> treeNodes) {
        treeNode.setChildren(new ArrayList<Permission>());

        for (Permission it : treeNodes) {
            if(treeNode.getId().equals(it.getPid())) {
                int level = treeNode.getLevel() + 1;
                it.setLevel(level);
                if (treeNode.getChildren() == null) {
                    treeNode.setChildren(new ArrayList<>());
                }
                treeNode.getChildren().add(findChildren(it,treeNodes));
            }
        }
        return treeNode;
    }
}

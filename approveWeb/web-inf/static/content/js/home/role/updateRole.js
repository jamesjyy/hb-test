(function () {
    "use strict";
    let layer,form,miniTab,zTree;
    let auths = [];//系统所有权限列表
    let authGroup = [];//权限分组列表
    let menuList = [];//菜单树列表
    layui.use(['layer','form','miniTab'],function(){
        layer = layui.layer;
        form = layui.form;
        miniTab = layui.miniTab;
        $.when(
            $.get(gpub_Base_Server_Request_Url+"/auth/getAuthListAll"),
            $.get(gpub_Base_Server_Request_Url+"/authGroup/getAuthGroupListAll"),
            $.get(gpub_Base_Server_Request_Url+"/menu/getMenuListAll?num="+Math.random())
        ).done(function (response1,response2,response3) {
            if(!response1[0].msg.success){
                layer.alert(response1[0].msg.message,{icon:2,title:"错误提示"},function () {
                    miniTab.deleteCurrentByIframe();
                });
                return;
            }
            if(!response2[0].msg.success){
                layer.alert(response2[0].msg.message,{icon:2,title:"错误提示"},function () {
                    miniTab.deleteCurrentByIframe();
                });
                return;
            }
            if(!response3[0].msg.success){
                layer.alert(response3[0].msg.message,{icon:2,title:"错误提示"},function () {
                    miniTab.deleteCurrentByIframe();
                });
                return;
            }
            auths = response1[0].data;
            authGroup = response2[0].data;
            menuList = response3[0].data
            menuList.forEach(function (menu) {
                if(menu.enabled == '0'){
                    menu.menuname = menu.menuname+' [已禁用]';
                }
            });
            init();
        }).fail(function (xhr,editor,result) {
            layer.alert("无法正常加载初始化数据！HTTP状态码："+xhr.status,{title:"错误提示",icon:2});
        });
    });
    function init() {
        initZtree();
        initAuth();
        initEvent();
    }
    //初始化事件
    function initEvent() {
        $("#cancalAdd").click(function () {//关闭当前Tab
            miniTab.deleteCurrentByIframe();
        });
        form.on("submit(addRoleFilter)",function (data) {//表单提交
            let field = data.field;
            let keys = Object.keys(field);
            let param = {roleName:field.roleName,authIdList:"",isShow:1,roleId:field.roleId};
            let list = [],menuIdList=[];
            keys.forEach(function (currentValue,index) {
                if(currentValue.startsWith("auth")){
                    list.push(field[currentValue]);
                }
            });
            param.authIdList = list.join(",");
            //读取选中的菜单列表
            getTreeChecked().forEach(function (node) {
                menuIdList.push(node.id);
            });
            param.menuList = menuIdList.join(",");
            layer.confirm("该角色具有"+ list.length +"种权限，确定要保存该角色信息吗？",{title:"提交确认",icon:3},function (index) {
                layer.close(index);
                let load_index = layer.load(3);
                $.post(gpub_Base_Server_Request_Url + "/role/updateRoleById",param).done(function (response) {
                    layer.close(load_index);
                    if(response.msg.success){
                        layer.msg("修改角色成功！",{icon:1},function () {
                            $("#cancalAdd").click();
                        });
                    }
                    else{
                        layer.alert("修改角色失败！失败原因："+response.msg.message,{icon:2});
                    }
                }).fail(function (error) {
                    layer.close(load_index);
                    layer.alert("提交数据出现问题！HTTP状态码："+error.status,{icon:2,title:"错误提示"});
                });
            });
        });
    }
    //初始化权限列表多选框
    function initAuth() {
        $.each(authGroup,function (index,value) {
            $("#auths").append('<div class="layui-input-block">\n' +
                '                        <div class="authGroup">\n' +
                '                            <div class="ghead">'+ value.groupName +'</div>\n' +
                '                            <div class="gcontent" id="group'+ value.id +'"></div>\n' +
                '                        </div>\n' +
                '                    </div>');
        });
        $.each(auths,function (index,value) {
            $("#group"+value.groupId).append('<input type="checkbox" name="auth'+value.id +'" value="'+ value.id +'" title="'+value.name+'">');
        });
        form.render();
        let load_index = layer.load(3);
        $.get(gpub_Base_Server_Request_Url+"/role/getRoleById/"+getQuery("roleId")).done(function (response) {
            layer.close(load_index);
            console.log(response);
            if(response.msg.success){
                let roleInfo = response.data.roleInfo,menusIdList = response.data.menuInfo;
                var data = {roleName:roleInfo.roleName,roleId:roleInfo.roleId};
                roleInfo.authIdList.split(",").forEach(function (value) {
                    data['auth'+value] = true;
                });
                form.val("myform",data);
                menusIdList.forEach(function (id) {//加载时自动勾选选中的菜单
                    let node = zTree.getNodeByParam('id',id,null);
                    if(node){
                        zTree.checkNode(node,true,true,false);
                    }
                    else{
                        console.error("zTree找不到ID="+id+"的节点！");
                    }
                });
            }else{
                layer.alert(response.msg.message,{icon:2,title:"错误提示"});
            }
        }).fail(function (error) {
            layer.close(load_index);
            layer.alert("加载角色详情出现问题！HTTP状态码："+error.status,{title:'错误提示',icon:2});
        });
    }

    //初始化菜单树
    function initZtree() {
        let setting = {
            data:{
                key:{
                    name:'menuname',
                    childred:'childred',
                    url:'nourl',
                    isParent:'menuType'
                },
                simpleData:{
                    enable:true,
                    idKey:'id',
                    pIdKey: 'parentid',
                }
            },
            check:{
                enable:true,
                chkStyle:'checkbox'
            },
            view:{
                fontCss:function (treeId,treeNode) {
                    let style = {};
                    if(!treeNode.isParent && treeNode.enabled=='0'){
                        style.color = 'red';
                    }
                    return style;
                }
            }
        }
        zTree = $.fn.zTree.init($("#menuTree"),setting,menuList);
        zTree.expandAll(true);
        //禁止未启用菜单勾选
        let nodeList = zTree.getNodesByParam('enabled','0',null);
        nodeList.forEach(function (node) {
            zTree.setChkDisabled(node,true,false,false);
        });

    }

    //获取ztree选中叶子节点
    function getTreeChecked() {
        let nodes = zTree.getCheckedNodes(),result = [];
        nodes.forEach(function (value) {
            if(!value.menuType){//排除菜单目录勾选
                result.push(value)
            }
        });
        return result;
    }
})();

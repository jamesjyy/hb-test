let role;//当前登陆用户所属角色
let auth;//当前登陆用户具有权限
//检查是否具有相关权限
function checkHaveAuth(authId) {
    let list = role.authIdList.split(",");
    return list.indexOf(authId+"") > -1;
}
(function () {
    "use strict";
    let layer,element,form,table;
    let miniTab;
    layui.use(['layer','element','form','table','miniTab'],function () {
        layer = layui.layer;
        element = layui.element;
        form = layui.form;
        table = layui.table;
        miniTab = layui.miniTab;
        $.when($.get(gpub_Base_Server_Request_Url+"/role/getLoginUserRole"),$.get(gpub_Base_Server_Request_Url+"/auth/getLoginUserAuth")).done(function (res1,res2) {
            role = res1[0].data;
            auth = res2[0].data;
            init();
        }).fail(function () {
            layer.alert("发生错误：无法正常加载初始化数据！",{icon:2,title:"错误提示"});
        });
    });
    function init() {
        miniTab.listen();
        initAuth();
        creTable();//初始化数据表格
        creToolEvent();//建立表格行工具按钮事件
        $('#searchbtn').click(function () {
            let roleName = $("input[name='roleName'][type='text']").val();
            search(roleName);
        });
        $("#addRole").click(function () {
            miniTab.create({});
            console.log("点击添加按钮");
        });
        $("#flash").click(function () {
            table.reload('mytbId');
        });
        $("#delRole").click(function () {
            var checks = getChecks();
            if(checks.length<1){
                layer.msg("请选取数据后执行操作！");
                return;
            }
            layer.confirm("确定要删除"+checks.length+"条数据吗？",{icon:3,title:"角色删除确认"},function (index) {
                layer.close(index);
                delRole(checks);
            });
        });
    }
    function initAuth() {
        $("#delRole").mask(14,role);
        $("#addRole").mask(12,role);
        $("#searchbtn,input[name='roleName'],#flash").mask(11,role);
    }
    //删除角色数据
    function delRole(roleIds) {
        let load_index = layer.load(3);
        $.post(gpub_Base_Server_Request_Url+"/role/deleteRoleById",{roleIds:roleIds.join(",")}).done(function (response) {
            layer.close(load_index);
            if(response.msg.message){
                layer.msg("删除角色成功！",{icon:1},function () {table.reload('mytbId');});
            }else{
                layer.alert("删除角色发生错误，请检查！",{icon:2});
            }
        }).fail(function (error) {
            layer.close(load_index);
            if(error.status == 401){
                layer.alert("抱歉，您暂无权限使用角色删除功能！",{title:"权限不足提示",icon:4});
                return;
            }
            layer.alert("处理请求发生错误！状态码："+error.status,{title:"错误提示",icon:2});
        });
    }
    //初始化角色表格
    function creTable() {
        table.render({
            elem:"#mytable",
            id:'mytbId',
            height:"full-170",
            url:gpub_Base_Server_Request_Url+"/role/getRoleListByPage",
            page:true,
            cols:[[
            {
                type:"checkbox",fixed:"left",align:"center"},
                {field:'roleName',title:"角色名称",align:"center"},
                {field:'authIdList',title:"权限数量",align:"center",templet(d){
                    return d['authIdList'].split(",").length;
                }},
                {title:"可用操作",toolbar:"#mytoolbar",align:"center"}
            ]],
            text:{none:'暂无数据'},
            request:{
                pageName:"pageNum",
                limitName:"pageSize"
            },
            parseData:function(response){
                if(response.msg && response.msg.success === false){
                    layer.alert(response.msg.message,{icon:2,title:"错误提示"});
                    return;
                }
                var obj = {
                    code:response.msg.success,
                    data:response.data.data,
                    msg:response.msg.message,
                    sum:response.data.sum
                };
                return obj;
            },
            response:{
                statusName:"code"
                , statusCode:true
                , msgName:"msg"
                , countName:"sum"
                , dataName:"data"
            }
        });
    }
    //获取表格选中行ID列表
    function getChecks() {
        let checks = table.checkStatus("mytbId");
        let param = [];
        for(let i=0;i<checks.data.length;i++){
            param.push(checks.data[i].roleId);
        }
        return param;
    }
    //建立行工具按钮响应事件
    function creToolEvent() {
        table.on("tool(mytableFilter)",function (obj) {
            let data = obj.data;
            switch (obj.event) {
                case 'edit':
                    miniTab.openNewTabByIframe({href:"role/updateRole.html?roleId="+data.roleId,title:"编辑角色【"+data.roleName+"】"});
                    break;
                case 'del':
                    layer.confirm("确定要删除角色【"+data.roleName+"】吗？",{icon:3,title:"角色删除确认"},function (index) {
                        layer.close(index);
                        delRole([data.roleId]);
                    });
                    break;
            }
        });
    }
    function search(roleName) {
        table.reload('mytbId',{where:{"roleName":roleName}});
    }
})();


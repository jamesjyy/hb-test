let role;//当前登陆用户所属角色
let auth;//当前登陆用户具有权限
//检查是否具有相关权限
function checkHaveAuth(authId) {
    let list = role.authIdList.split(",");
    return list.indexOf(authId+"") > -1;
}
(function () {
    "use strict";
    let layer,form,iconPickerFa;
    let zTree;
    let menuList = [];//菜单树列表
    layui.use(['layer','form','iconPickerFa'],function () {
        layer = layui.layer;
        form = layui.form;
        iconPickerFa = layui.iconPickerFa;
        iconPickerFa.render({
            elem:'#myiconPicker',
            search: false,
            page: true,
            limit: 20,
            cellWidth: '50px',
            url:'.././static/content/plugin/layuimini/lib/font-awesome-4.7.0/less/variables.less',
            click:function (data) {
                console.log(data);
            },
            success:function (data) {

            }
        });
        before(init);
    });

    function before(callback) {
        $.when(
            $.get(gpub_Base_Server_Request_Url+"/menu/getMenuListAll?num="+Math.random()),
            $.get(gpub_Base_Server_Request_Url+"/role/getLoginUserRole"),
            $.get(gpub_Base_Server_Request_Url+"/auth/getLoginUserAuth")
        ).done(function (result1,result2,result3) {
            console.log(arguments);
            for(let i=0;i<arguments.length;i++){
                if(arguments[i][0].msg.success == false){
                    layer.alert(arguments[i][0].msg.message,{title:"错误提示",icon:2});
                    if(i == 1){
                        return;
                    }
                }
            }
            menuList = result1[0].data;
            role = result2[0].data;
            auth = result3[0].data;
            callback();
        }).fail(function (xhr,editor,result) {
            console.log(xhr);
            layer.alert("无法正常加载初始化数据！HTTP状态码："+xhr.status,{title:"错误提示",icon:2});
        });
    }

    function reload(){
        let index_load = layer.load(3);
        $.get(gpub_Base_Server_Request_Url + "/menu/getMenuListAll?num="+Math.random()).done(function (res1) {
            layer.close(index_load);
            if(res1.msg.success){
                res1.data.forEach(function (value) {
                    console.log(value);
                });
                menuList = res1.data;
                initZtree();
            }
            else {
                layer.alert(res1.msg.message,{title:"错误提示",icon:2});
            }
        }).fail(function (xhr,editor,result) {
            layer.close(index_load);
            layer.alert("无法刷新菜单树数据！HTTP状态码："+xhr.status,{title:"错误提示",icon:2});
        });
    }

    function init() {
        initZtree();//初始化zTree菜单树
        initEvent();//初始化事件
        initAuth();
    }

    //初始化权限限制
    function initAuth(){
        $('#addMenu').mask(20,role);
        $('#enMenu,#unMenu').mask(21,role);
        $('#menuTree,#flash').mask(19,role);
    }

    function initZtree() {
        if(zTree){
            zTree.destroy();
        }
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
                    // console.log(treeId,treeNode);
                    let style = {};
                    if(!treeNode.isParent && treeNode.enabled=='0'){
                        style.color = 'red';
                    }
                    return style;
                },
            },
            callback:{
                onClick:function (event, treeId, treeNode) {
                    if(!treeNode.parentid){//顶级菜单不弹出修改框
                        $("#menuDetail").hide();
                        return;
                    }
                    $("#menuDetail").show();
                    $('#mysub').mask(16,role);
                    if(treeNode.menuType){
                        $("#menu_enabled,#form_url").hide();
                    }
                    else{
                        $("#menu_enabled,#form_url").show();
                    }
                    let param = {
                        menuname:treeNode.menuname,
                        url:treeNode.url,
                        enabled:treeNode.enabled == '1',
                        id:treeNode.id,
                        orderno:treeNode.orderno
                    };
                    if(treeNode.menuicon){
                        param.menuicon = treeNode.menuicon;
                        iconPickerFa.checkIcon('iconPicker',treeNode.menuicon);
                    }
                    else{
                        param.menuicon = 'fa-500px';
                        iconPickerFa.checkIcon('iconPicker','fa-500px');
                    }
                    let pNode = treeNode.getParentNode();
                    if(pNode){
                        param.parentDir = pNode.menuname;
                    }
                    else {
                        param.parentDir = "无父级目录";
                    }
                    param.menuType = treeNode.menuType?"菜单目录":"实际菜单";
                    form.val("myform",param);
                },
                beforeClick:function (treeId, treeNode, clickFlag) {
                    return true;
                }
            }
        };
        zTree = $.fn.zTree.init($("#menuTree"),setting,menuList);
        zTree.expandAll(true);
    }

    function initEvent() {
        $("#enMenu,#unMenu").click(function () {
            let ids=[];
            getTreeChecked().forEach(function (value) {
                ids.push(value.id);
            });
            if(ids.length<1){
                layer.alert("您未勾选菜单项目或者勾选的项目不包含实际菜单！",{icon:2,title:"错误提示"});
                return;
            }
            let load_index = layer.load(3);
            let enabled = $(this).text().trim()=='启用';
            $.post(gpub_Base_Server_Request_Url + "/menu/updateMenuEnableBatch",{enabled:enabled?1:0,ids:ids.join(",")}).done(function (response) {
                layer.close(load_index);
                if(response.msg.success){
                    layer.msg((enabled?'启用':'禁用')+"选中菜单成功！",{icon:1},function () {
                        $("#flash").click();
                    });
                }
                else{
                    layer.alert(response.msg.message,{icon:2,title:"错误提示"});
                }
            }).fail(function (xhr,editor,result) {
                layer.close(load_index);
                layer.alert("修改菜单状态出现问题！HTTP状态码："+xhr.status,{title:"错误提示",icon:2});
            });
        });

        $("#flash").click(function () {
            $("#menuDetail").hide();
            reload();
        });

        $('#addMenu').click(function () {
            let selects = zTree.getSelectedNodes();
            console.log(selects);
            if(selects.length<1){
                layer.alert("请点击菜单标题选中（非勾选）后再进行操作！",{icon:7,title:"选择提示"});
                return;
            }
            if(selects.length>1){
                layer.alert("您选择的菜单过多，请单选！",{icon:2,title:"选择提示"});
                return;
            }
            if(selects[0].menuType == false){
                layer.alert("只有菜单目录才能创建子菜单！",{icon:2,title:"错误提示"});
                return;
            }
            layer.confirm("要创建菜单目录还是实际菜单？",{btn:['实际菜单','菜单目录','取消'],title:"选择菜单类型",icon:3},function () {
                inner(false);
            },function () {
                inner(true);
            },function () {
            });
            function inner(menuType){
                layer.prompt({formType:0,title:"请输入"+ (menuType?"菜单目录":"菜单") +"标题"},function (value,index,elem) {
                    layer.close(index);
                    console.log(value);
                    let load_index = layer.load(3);
                    $.post(gpub_Base_Server_Request_Url + "/menu/addMenu",{
                        parentid:selects[0].id,
                        menuname:value.trim(),
                        businesstype:selects[0].businesstype,
                        menuType:menuType,
                        menuicon:'fa-500px',
                        enabled:menuType?1:0,
                        orderno:0
                    }).done(function (response) {
                        layer.close(load_index);
                        if(response.msg.success){
                            layer.msg("创建菜单成功！",{icon:1},function () {
                                $("#flash").click();
                            });
                        }
                        else{
                            layer.alert(response.msg.message,{icon:2,title:"错误提示"});
                        }
                    }).fail(function (xhr,editor,result) {
                        layer.close(load_index);
                        layer.alert("无法正常提交菜单数据！HTTP状态码："+xhr.status,{title:"错误提示",icon:2});
                    });
                });
            }
        });

        //取消修改菜单按钮
        $("#cancelEdit").click(function () {
            $("#menuDetail").hide();
        });

        //菜单修改提交
        form.on("submit(mysub)",function (data) {
            let field = data.field;
            field.enabled = field.enabled?1:0;//layui开关类型组件做特殊转换
            delete field.menuType;//菜单类型不能修改，故删除
            console.log(field);
            let load_index = layer.load(3);
            $.post(gpub_Base_Server_Request_Url + "/menu/updateMenuById",field).done(function (response) {
                layer.close(load_index);
                if(response.msg.success){
                    $("#menuDetail").hide();
                    reload();
                    layer.msg("修改菜单成功！",{icon:1});
                }else{
                    layer.alert(response.msg.message,{icon:2,title:"错误提示"});
                }
            }).fail(function (xhr,editor,result) {
                layer.close(load_index);
                layer.alert("保存菜单数据出现问题！HTTP状态码："+xhr.status,{title:"错误提示",icon:2});
            });

        });

        form.verify({
            nullcheck:function (value,item) {
                let param = form.val('myform');
                if(param.menuType != '菜单目录' && value.trim()==''){
                    return "必填项不能为空！";
                }
            }
        });
    }

    //获取ztree选中叶子节点
    function getTreeChecked() {
        let nodes = zTree.getCheckedNodes(),result = [];
        nodes.forEach(function (value) {
            if(!value.menuType){
                result.push(value)
            }
        });
        return result;
    }
})();

layui.use('upload', function(){
    var upload = layui.upload;
    //多图片上传
    upload.render({
        elem: '#uploadImg'
        ,url: gpub_Base_Server_Request_Url+'/upFiles/upload' //改成您自己的上传接口
        ,multiple: true
        //,exts:'txt'
        //,auto:'false'
        //,bindAction:'#saveData'//绑定上传按钮
        ,before: function(obj){
            //预读本地文件示例，不支持ie8
            obj.preview(function(index, file, result){
                $('#addImg').append('<img src="'+ result +'" alt="'+ file.name +'" class="layui-upload-img" style="height: 100px;width: 100px">')
            });
        }
        ,done: function(res){
            $("#upfilesid").val(upfilesid);
            //上传完毕
        }
    });
});
var demo1='';//多选下拉框实例
layui.use(['table','form','layer'], function(){
    var table=layui.table,
        layer=layui.layer,
        form=layui.form;

    $.BindDicByClass({
        boundObjects: [
            { Object: 'sel_dept', DicPchar: "dept", IsBindCode: true, FirstOption: 1 },
            { Object: 'roleId', DicPchar: "role", IsBindCode: true, FirstOption: 1 }
        ],
        isDefDicData:false,
        callback:function () {
            form.render('select');
        }
    });
    $.get(gpub_Base_Server_Request_Url+'/role/getRoleByAll',function (response) {
        demo1= xmSelect.render({
            el: '#roleIds',
            language: 'zn',
            data:response.data,
            prop: {
                name: 'roleName',
                value: 'roleId',
                selected: 'selected',
                disabled: 'disabled'
            }
        });
    })

    var tabCols = [[
        {field:'loginname',align:'center',title: '登录名',sort:false}
        ,{field:'username',align:'center',title: '姓名',sort:false}
        ,{field:'mobilephone',align:'center',title: '手机号',sort:false}
        ,{field:'enabled',align:'center',title: '启用状态',sort:true,
            templet:function (d) {
                if (d.enabled == "1") {return '<span style="color:#666666;">启用</span>'}
                else if (d.enabled == "0") {return '<span style="color:red;">禁用</span>'}
                else{return '';}
            }
        }
        ,{field:'depName',align:'center',title: '部门',sort:false}
        ,{field:'isworkuser',align:'center',title: '是否是值班人员',sort:true,
            templet:function (d) {
                if (d.isworkuser == "1") {return '<span style="color:#666666;">是</span>'}
                else if (d.isworkuser == "0") {return '<span style="color:red;">否</span>'}
                else{return '';}
            }
        }
        ,{field:'job',align:'center',title: '职务',sort:false}
        ,{field:'jobno',align:'center',title: '工号',sort:true}
        //,{field:'roleName',align:'center',title: '角色',sort:true}
        ,{width:190,title:'操作',align:'center',toolbar: '#checkBar'}
    ]];
    table.render({
        elem: '#boardTable'
        ,id:'checkReload'
        ,height:"full"
        ,url:gpub_Base_Server_Request_Url+"/users/getUserinfoListByPage"
        ,request:{
            pageName:"pageNum",
            limitName:"pageSize"
        }
        ,parseData:function (res) {
            return {
                "code": 0, //解析接口状态
                "msg": res.msg.message, //解析提示文本
                "count": res.data.total, //解析数据长度
                "data": res.data.list //解析数据列表
            };
        }
        ,limit :10
        ,page: true
        ,limits : [10,20,30,40,50]
        ,cols: tabCols
    });

    //查询按钮触发事件
    $('#btn_sel').on("click",function () {
        var username=$("#sel_userName").val(),//登陆名
            chinese=$("#sel_chinese").val(),//姓名
            depid=$("#sel_dept").val(),//部门id
            enabled=$("#sel_enabled").val();
        table.reload('checkReload',{
            url:gpub_Base_Server_Request_Url+"/users/getUserinfoListByPage",
            where:{
                loginname:username,
                username:chinese,
                depid:depid,
                enabled:enabled
            }
        })
    })

    //刷新按钮触发事件
    $("#fresh").on("click",function () {
        $("#sel_userName").val("")
        $("#sel_chinese").val("")
        $("#sel_dept").val("")
        $("#sel_enabled").val("")
        table.reload('checkReload',{
                url:gpub_Base_Server_Request_Url+"/users/getUserinfoListByPage",
                where:{}
            }
        )

    })
    //新增按钮触发事件
    var index;
    $("#addbtn").on("click",function () {
        //form.val("addForm",{username:"",password:"", chinese:"", tel:"", jobStatus:"", isoutworker:"", outworkerGroup:"",memo:"",department:"",roleid:""});
        $("#addForm")[0].reset();//重置表单，
        form.val("addForm",{id:""});
        layui.form.render();
        demo1.setValue([]);//初始化
        $('#addImg').html("");//清除图片
        $("#titleName").text("新增用户");
        index=layer.open({
            type: 1,
            skin: 'layui-layer-rim',
            area: ["100%", '100%'],
            content: $("#addUser"),
            title: false,
            anim: 2,
            closeBtn: false,
            shadeClose: false //点击遮罩关闭层
            //title:"新增用户",
        });
    })
    //新增保存功能
    form.on("submit(sub)",function (data) {
        var titleName=$("#titleName").html();
        var url;
        if(titleName=="新增用户"){
            url="insertUserinfo";
        }else{
            url="updateUserinfoById";
        }
        data.field.roleIds=demo1.getValue().map(function(v){return v.roleId;}).join(",");
        $.post(gpub_Base_Server_Request_Url + "/users/"+url,data.field).done(function (response) {
            if(response.msg.success){
                layer.msg("保存成功！",{icon:1},function () {
                    layer.close(index);
                    $("#fresh").click();
                });
            }
            else{
                layer.alert("保存失败！失败原因："+response.msg.message,{icon:2});
            }
        }).fail(function (error) {
            layer.close(index);
            layer.alert("提交数据出现问题！HTTP状态码："+error.status,{icon:2,title:"错误提示"});
        });
    })
    $("#cacel").on("click",function () {
        layer.close(index);
    })
    $("#close").on("click",function () {
        layer.close(index);
    })
    table.on("tool(boardTable)",function (obj) {
        var event=obj.event;
        var data=obj.data;
        switch (event) {
            case "a_sel":
                $("#lookForm")[0].reset();//重置表单，
                layui.form.render();
                form.val("lookForm",{
                    "loginnameLook":data.loginname,
                    "usernameLook":data.username,
                    "jobnoLook":data.jobno,
                    "jobLook":data.job,
                    "mobilephoneLook":data.mobilephone,
                    "checknumLook":data.checknum,
                    "roleIdLook":data.tbSysRoleList.map(function(v){return v.roleName;}).join(",")
                });
                $("#demo3").html("");//清除图片
                if(data.upfilesPath!=null){
                    $('#demo3').append('<img src="'+ data.upfilesPath +'" alt="'+ data.upfilesName +'" class="layui-upload-img" style="height: 100px;width: 100px">');
                }
                index=layer.open({
                    type: 1,
                    skin: 'layui-layer-rim',
                    area: ["100%", '100%'],
                    content: $("#lookUser"),
                    title: false,
                    anim: 2,
                    closeBtn: false,
                    shadeClose: false //点击遮罩关闭层
                });
                break;
            case "a_change"://编辑用户
                $("#addForm")[0].reset();//重置表单，
                layui.form.render();
                index=layer.open({
                    type: 1,
                    skin: 'layui-layer-rim',
                    area: ["100%", '100%'],
                    content: $("#addUser"),
                    title: false,
                    anim: 2,
                    closeBtn: false,
                    shadeClose: false //点击遮罩关闭层
                });
                $("#titleName").text("编辑用户");
                form.val("addForm",data);
                demo1.setValue(data.tbSysRoleList.map(function(v){return v.roleId;}));
                $('#addImg').html("");
                if(data.upfilesPath!=null){
                    $('#addImg').append('<img src="'+ data.upfilesPath +'" alt="'+ data.upfilesName +'" class="layui-upload-img" style="height: 100px;width: 100px">');
                }
                break;
            default:
                var enabled=0;
                var msg="禁用";
                if(event=="trueDetail"){//启用
                    enabled=1;
                    msg="启用";
                }
                layer.confirm("确定"+msg+"该账户吗？",{icon: 3,title:'警告'},function (index) {
                    $.post(gpub_Base_Server_Request_Url + "/users/updateUserinfoById",{id:data.id,enabled:enabled}).done(function (response) {
                        if(response.msg.success){
                            $("#fresh").click();
                            layer.msg(msg+"成功！",{icon:1});
                        }
                        else{
                            layer.alert(msg+"失败！失败原因："+response.msg.message,{icon:2});
                        }
                    }).fail(function (error) {
                        layer.alert("提交数据出现问题！HTTP状态码："+error.status,{icon:2,title:"错误提示"});
                    });
                    layer.close(index);
                })
        }
    })
    form.verify({
        //全英文或英文和数字组合
        loginname: function(value, item){ //value：表单的值、item：表单的DOM对象
            // if(!new RegExp("^[a-zA-Z0-9_\u4e00-\u9fa5\\s·]+$").test(value)){
            //     return '用户名不能有特殊字符';
            // }
            // if(/(^\_)|(\__)|(\_+$)/.test(value)){
            //     return '用户名首尾不能出现下划线\'_\'';
            // }
            if(/^\d+\d+\d$/.test(value)){
                return '登陆名不能全为数字';
            }
            if(!/^[0-9a-zA-Z]+$/.test(value)){
                return '登陆名必须全英文或英文和数字组合';
            }
        }
        ,password: [//6到12位
            /^[\S]{6,12}$/
            ,'密码必须6到12位，且不能出现空格'
        ],
        username:[//只能是中文
            /^[\u4e00-\u9fa5]+$/,'用户名只能是中文'
        ],
        num:function(value, item){
            //只能是数字或英文和数字
            if(/^[a-zA-Z]+$/.test(value)){
                return '考勤号或工号不能全英文';
            }
            if(!/^[0-9a-zA-Z]+$/.test(value)){
                return '考勤号或工号必须全数字或英文和数字组合';
            }
        }
    });
})

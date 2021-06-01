layui.use(['table','tree','form'], function(){
    var table = layui.table;
    var tree=layui.tree;
    var form=layui.form;



    //加载table表格
    table.render({
        elem: '#dataDicTable'
        ,url:gpub_Base_Server_Request_Url+'/dataDic/dataList'
        ,parseData:function (res) {
            if(res.data!=null)
            return {
                "code": res.data.status, //解析接口状态   （好像必须是0 才可以）
                "msg": res.data.message, //解析提示文本
                "count": res.data.total, //解析数据长度
                "data": res.data.item //解析数据列表
            };
        }
        ,id: 'checkReload'
        ,width:960
        ,cols: [[
            {field:'pId',align:'center', width:60, title: 'ID', sort: true,hide:true}
            ,{field: 'index',align:'center', width: 60, title: '序号',templet: function (d) { return d.LAY_TABLE_INDEX+1; } }
            ,{field:'pType',align:'center', width:150, title: '字典分类名称'}
            ,{field:'pName',align:'center', title: '参数名称'}
            ,{field:'pValue',align:'center', width:100, title: '参数值'}
            ,{field:'pStatus',align:'center', width:70,title: '状态',
                templet: function (d){
                    if(d.pStatus==0){
                        return '<span style="color:#dd2c42;font-weight: bold;">禁用</span>'
                    }else{
                        return '<span style="color:#008000;font-weight: bold;">启用</span>'
                    }
                }
            } //minWidth：局部定义当前单元格的最小宽度，layui 2.2.1 新增
            /*,{field:'pPid',align:'center', width:80,title: '父级ID'}*/
            ,{field:'inTime',align:'center',width:150, title: '入库时间'}
            ,{ title:'操作',align:'center',toolbar: '#checkBar'}
        ]]
    });

    //加载树形组件
    var dataDicSel;
    var dataDicChar;
    $.ajax({type:"GET",dataType: "json",url:gpub_Base_Server_Request_Url+'/dataDic/dataTree',
        success:function (res) {
        var dataDic=res.data;
        var dataTree=[];
        if(dataDic!=null){
            for(var i=0;i<dataDic.length;i++){
            dataTree.push({title:dataDic[i].pType,id:dataDic[i].pId,field:dataDic[i].pChar})
            }
        }
        tree.render({
            elem:'#treeData',
            data:dataTree
            ,id:'treeData'
            ,click: function(obj){
                $('.rightDiv').css('display','block');
                // console.log(obj);
                $('.layui-tree-set .layui-tree-txt').removeClass('treeNodeSel');
                $('.layui-tree-txt',obj.elem).addClass('treeNodeSel');
                var DataDic=obj.data;
                dataDicSel=DataDic.title;
                dataDicChar=DataDic.field;
                // console.log(dataDicSel+","+dataDicChar+",")
                $('#span_dataTypeName').text(dataDicSel);
                table.reload('checkReload', {
                    url: gpub_Base_Server_Request_Url+'/dataDic/dataList?_p='+Math.random()
                    ,where: {pType:DataDic.field} //设定异步数据接口的额外参数
                });
            }
        });
        setTimeout(function () {$('.layui-tree-txt:first').trigger('click');},1000);
        // 在源码中tree.js
        // //树节点解析  回调
        // Tree.prototype.tree = function(elem, children){
    }})

    //搜索框数据重载
    var active = {
        reload: function(){
            var pType = $('#pType');
            //执行重载
            table.reload('checkReload', {
                page: {
                    curr: 1 //重新从第 1 页开始
                }
                ,where: {
                    pType: pType.val()
                }
            }, 'data');
        }
    };

    $('#reloadBtn').on('click', function(){
        var type = $(this).data('type');
        active[type] ? active[type].call(this) : '';
    });
    $('.statusReload').on('click', function(){
        var type = $(this).val();
        table.reload('checkReload', {
            where: {
                pStatus: type
            }
        }, 'data');
    });
    //新增字典分类名称
    $('#addDataTree').on('click',function () {
        $("#addTreeForm")[0].reset();
        form.render();
        layer.open({
            type:1,
            area:['500px','320px'],
            skin: 'layui-layer-rim',
            title: '新增字典分类',
            btn:['保存','取消'],
            closeBtn:false,
            content:$('#addTreeBox'),
            yes:function (index, layero) {
                var addForm=form.val("addTreeForm");
                if(addForm.pChar==null||addForm.pChar==''){
                    layer.msg("字典分类标识不能为空")
                }else if(addForm.pType==null||addForm.pType==''){
                    layer.msg("字典分类名称不能为空")
                }else{
                    layer.confirm('<div style="padding-left: 20px;padding-top:8px">您确定要进行操作吗</div>',{icon: 3, time: 5000},function () {
                        layer.load();
                        reloadTree(addForm.pChar,addForm.pType);
                        layer.closeAll();
                        layer.msg("添加成功");
                    })
                }
            }
        })
    })
    function reloadTree(pChar,pType){
        $.get(gpub_Base_Server_Request_Url+'/dataDic/dataTree',function (res) {
            var dataDic=res.data;
            var dataTree=[];
            if(dataDic!=null){
                for(var i=0;i<dataDic.length;i++){
                    dataTree.push({title:dataDic[i].pType,id:dataDic[i].pId,field:dataDic[i].pChar})
                }
            }
            dataTree.push({title:pType,field:pChar})
            tree.reload('treeData', {
                //新的参数
                data:dataTree
            });
        })
    }

    //新增子字典分类名称
    $('#addData').on('click',function () {
        $("#pCharSel").hide();
        $("#pValueSel").hide();
        $("#charId").empty();
        $("#valueId").empty();
        $("#addForm")[0].reset();
        form.val("addForm", {
            "pStatus": 1
            ,"pType":dataDicSel
            ,"pChar":dataDicChar
            ,"pCharIn":0
        });
        form.render();
        var userName=$("#userName").val();
        var area=['650px','388px'];
        if(userName=="admin"){
            area=['650px','588px'];
            $("#pCharSel").show();
        }
        if(dataDicSel=='板件类型'){
            $('#addBox .layui-word-aux').show();
        }else{
            $('#addBox .layui-word-aux').hide();
        }
        layer.open({
            type:1,
            area:area,
            skin: 'layui-layer-rim',
            title: '新增参数',
            btn:['保存','取消'],
            closeBtn:false,
            content:$('#addBox'),
            yes:function (index, layero) {
                var addForm=form.val("addForm");
                if(addForm.pName==null||addForm.pName==''){
                    layer.msg("参数名称不能为空")
                }else if(addForm.pValue==null||addForm.pValue==''){
                    layer.msg("参数值不能为空")
                }else if(addForm.pCharIn==1&&(addForm.valueId==""||addForm.valueId==null||addForm.charId==""||addForm.charId==null)){
                    layer.msg("请选择父级参数的参数类型和参数值")
                } else{
                    layer.confirm('<div style="padding-left: 20px;padding-top:8px">您确定要进行操作吗</div>',{icon: 3, time: 5000},function () {
                        layer.load();
                        $.post(gpub_Base_Server_Request_Url+'/dataDic/addData',{'pChar':addForm.pChar,'pStatus':addForm.pStatus,'pName':addForm.pName,'pType':addForm.pType,'pValue':addForm.pValue,'pPchar':addForm.charId,'pPvalue':addForm.valueId,'pCharIn':addForm.pCharIn},function(res){
                            if(res.msg.success){
                                table.reload('checkReload', {
                                    url: gpub_Base_Server_Request_Url+'/dataDic/dataList?_p='+Math.random()
                                });
                                layer.closeAll();
                                layer.msg("添加成功");
                            }else {
                                layer.closeAll();
                                layer.msg("添加失败");
                            }
                        })
                    })
                }
            }
        })
    })


    //级联选择父级参数
    function initSel(statusIndex){
        if (statusIndex==0){
            $("#pValueSel").hide();
            $("#charId").empty();
            $("#valueId").empty();
        }else if(statusIndex==1){
            getPChar();
            $("#pValueSel").show();
        }
    }
    function getPChar() {
        $.get(gpub_Base_Server_Request_Url+"/dataDic/dataTree",function (resData) {
            var data=resData.data;
            $("#charId").empty();
            $('#charId').append(new Option("请选择",""));
            $.each(data, function (index, item) {
                if(item.pType!=dataDicSel){
                    $('#charId').append(new Option(item.pType, item.pChar));// 下拉菜单里添加元素
                }
            });
            $("#valueId").empty();
            form.render("select","addForm");//重新渲染 固定写法
        })
    }
    function getPValue(charData){
        $.get(gpub_Base_Server_Request_Url+"/dataDic/dataList?_p="+Math.random(),{ pType: charData},function (resData) {
            var data=resData.data;
            $("#valueId").empty();//清空下拉框的值
            if(data!=null){
                $.each(data, function (index, item) {
                    $('#valueId').append(new Option(item.pName, item.pValue));// 下拉菜单里添加元素
                });
            }
            form.render("select","addForm");//重新渲染 固定写法
        })
    }
    form.on('select(charIdFilter)', function(data){
        getPValue(data.value);
    });
    form.on('radio(pCharIn)', function(data){
        initSel(data.value);
    });

    //监听工具列触发事件
    table.on('tool(dataDicTable)', function(obj){
        var dataDic=obj.data;
        /*updateBtn*/
        if(obj.event==="falseDetail"){
            layer.confirm('<div style="padding-left: 20px;padding-top:8px">您确定要进行操作吗</div>',{icon: 3, time: 5000},function () {
                $.post(gpub_Base_Server_Request_Url+'/dataDic/statusTransition',{flag:0,id:dataDic.pId},function (res) {
                    if(res.msg.success){
                        layer.msg("禁用成功");
                        table.reload('checkReload', {
                            url: gpub_Base_Server_Request_Url+'/dataDic/dataList'
                        });
                    }
                })
            })
        }else if(obj.event==="trueDetail"){
            layer.confirm('<div style="padding-left: 20px;padding-top:8px">您确定要进行操作吗</div>',{icon: 3, time: 5000},function () {
                $.post(gpub_Base_Server_Request_Url+'/dataDic/statusTransition',{flag:1,id:dataDic.pId},function (res) {
                    if(res.msg.success){
                        layer.msg("启用成功");
                        table.reload('checkReload', {
                            url: gpub_Base_Server_Request_Url+'/dataDic/dataList'
                        });
                    }
                })
            })
        }else{
            form.val("example", {
                "pName": dataDic.pName
                ,"pStatus": dataDic.pStatus
                ,"pId":dataDic.pId
            });
            var updataPvalue=dataDic.pValue;
            $("#p_value").html(updataPvalue);
            layer.open({
                type:1,
                area:['500px','320px'],
                skin: 'layui-layer-rim',
                title: '修改参数',
                btn:['保存','取消'],
                closeBtn:false,
                content:$('#checkBox'),
                yes:function (index, layero) {
                    layer.confirm('<div style="padding-left: 20px;padding-top:8px">您确定要进行操作吗</div>',{icon: 3, time: 5000},function () {
                        var data1 = form.val("example");
                        $.post(gpub_Base_Server_Request_Url+'/dataDic/updateData',{'pStatus':data1.pStatus,'pName':data1.pName,'pId':data1.pId},function(res){
                            if(res.msg.success){
                                layer.closeAll();
                                layer.msg("修改成功");
                                table.reload('checkReload', {
                                    url: gpub_Base_Server_Request_Url+'/dataDic/dataList'
                                });
                            }
                        })
                    })
                }
            })
        }
    })
});

var gpub_Base_Server_Request_Url="http://127.0.0.1:60383/api";
if(location.href.indexOf("http://localhost")>=0||location.href.indexOf("http://127.0.0.1")>=0){gpub_Base_Server_Request_Url="http://localhost:8082";}
$(function () {
    $.ajaxSetup({
        type:"post",
        dataType: "json",
        xhrFields: {withCredentials: true},
        crossDomain: true,
        beforeSend: function (xhr) {
            xhr.setRequestHeader("x-token",localStorage.getItem("sessionId"))
        },
        dataFilter:function (data,type) {
            if(type == 'json'){//防止不是json数据时报错，比如请求其他格式的文件
                var res=JSON.parse(data);
                if(res.msg!=undefined){
                    if(!res.msg.success){
                        if(res.msg.message=="非法请求"){
                            window.parent.location="../login.html";
                        }
                        else{
                            //返回信息为空时触发事件，若一分钟内请求同一个api次数超过十次，返回失败
                        }
                    }
                }
            }
            return data;
        }
    })
})

jQuery.BindDicByClass = function (options) {
    var defaults = {
        boundObjects: [], //required,里面对象属性有:Object(对象id),DicPchar(参数名称),IsBindCode(是否绑定编码，1：绑定Value值为编码，0：绑定Value值为名称),FirstOption(0:不绑定首个个option，1：--请选择-- 2：--全部--)
        isDefDicData:true, //是否默认字典表数据获取，字典之外的获取为false
        callback: null//回调函数
    }
    var opts = $.extend(defaults, options);
    defaults = []; //clear
    var col_name = [];//列名
    var valField = 'pValue', textField = 'pName';
    var valueField = valField,getUrl=gpub_Base_Server_Request_Url+'/common/getDicForBind';
    if(!opts.isDefDicData){getUrl=gpub_Base_Server_Request_Url+'/common/exceptDicBind';}
    $(opts.boundObjects).each(function (i, bf) {
        var aryidx = col_name.indexOf(bf.DicPchar);
        if(aryidx<0) col_name.push(bf.DicPchar);
        $('#' + bf.Object).html('');
        if (bf.FirstOption != 0) {
            var firstOption = '-请选择-';
            if (bf.FirstOption === 2) {firstOption = '-全部-';}
            $('#' + bf.Object).append($('<option></option>').val("").html(firstOption));
        }
    });
    $.post(getUrl+"?pchars=" + col_name.join(','), null, function (ret) {
        var data=ret.data;
        //无返回
        if (data === null || data === "-1") { return false; }
        if (data === "") { if (opts.callback) opts.callback.call();  return false; }
        $(data).each(function (j, item) {
            $(opts.boundObjects).each(function (i, bf) {
                valueField = !bf.IsBindCode ? textField : valField;
                //查询下拉框板件类型格式改变为：板件类型名称（板件类型值），非查询下拉框绑定时下拉框为隐藏，下拉框以原来格式展示
                if(bf.DicPchar=="board_type"&&$('#'+bf.Object).parent().css('display')!='none'){
                    if (bf.DicPchar === item.pChar) {$('#' + bf.Object).append(new Option(item[textField]+"("+item[valField]+")", item[valueField]));}
                }else{
                    if (bf.DicPchar === item.pChar) {$('#' + bf.Object).append(new Option(item[textField], item[valueField]));}
                }
            });
        });
        //回调
        if (opts.callback) opts.callback.call();
    });
}


/*将code替换成name，如：板件状态
从下拉框中取值并返回编码对应的名称..
selectId:   下拉框的id
code        需要替换的编码
isContain  是否按包含查询判断
*/
function dataCodeToName(selectId, code,isContain) {
    if(code===''||code==undefined || code==null)return '';
    code =code.toString();
    var optionObj = $('#' + selectId+' option');
    var retName='';
    optionObj.each(function (){
        var txt = $(this).text(),optval=$(this).val();
        if(optval===code){
            retName=txt;return false;//jquery中 跳出循环为return false，相当于break， continue为return ture
        }
        if(isContain)if(code.indexOf(optval)>=0){retName=txt;return false;}
    });
    return retName;
}

/**
 * jQuery插件，用于遮挡无权限操作元素，例如按钮
 * 用法：$("button").mask(11,role,{color:"red"});
 */
(function ($) {
    "use strict";
    $.fn.mask = function (authId,role,option) {
        if(!authId){
            throw new Error("必须指定该元素需要的权限ID！");
        }
        if(!role){
            throw new Error("必须指定当前用户角色信息！");
        }
        this.each(function (index) {
            if(role.authIdList.split(",").indexOf(authId+"") == -1){
                if($(this).data("__mask") == 'enable'){//如果已经进行遮罩，则直接返回
                    return;
                }
                var _option = {
                    "height":$(this).outerHeight()+"px"
                    , "width":$(this).outerWidth()+"px"
                    , "left":$(this).position().left+"px"
                    , "top":$(this).position().top+"px"
                    , "font-size":"20px"
                    , "background-color":"rgba(0,0,0,0.4)"
                    , "color":"white"
                    , "display":"flex"
                    , "justify-content":"center"
                    , "align-items":"center"
                    , "z-index":100
                    , "margin":0
                    , "padding":0
                    , "position":"absolute"
                    , "margin-left":$(this).css("margin-left")
                }
                $.extend(_option,option);
                $("<div><i class=\"Hui-iconfont Hui-iconfont-suoding\"></i></div>").css(_option).appendTo($(this).parent());
                $(this).data("__mask","enable");
            }
        });
        return this;
    }
})(jQuery);

//获取地址栏指定参数值
function getQuery(name) {
    var doubleList = window.location.search.substring(1).split("&");
    for(var i=0;i<doubleList.length;i++){
        var double = doubleList[i].split("=");
        if(double[0] == name){
            return double[1];
        }
    }
    return null;
}

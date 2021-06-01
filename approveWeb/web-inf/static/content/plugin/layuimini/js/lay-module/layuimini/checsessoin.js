var gPub_CheckIsSessionFlag=true,gPublocalStorageItemCheckSessionName='CheckIsSessionOpertoLogOut';
//页面10分钟无操作提示重新登陆
var gpub_Oper_OldTime = new Date().getTime();
var gpub_Oper_NewTime = new Date().getTime();
var gpub_Oper_OutTime = 20 * 60 * 1000; //设置超时时间： 20分钟
// $(function(){
//     /* 鼠标移动事件 */
// });
/* 定时器  判断每5秒是否长时间未进行页面操作 */
var gPub_CheckIsNotOperTimer= window.setInterval(CheckIsOutTime, 5000);
function CheckIsOutTime(){
    gpub_Oper_NewTime = new Date().getTime(); //更新未进行操作的当前时间
    if(gpub_Oper_NewTime - gpub_Oper_OldTime > gpub_Oper_OutTime){ //判断是否超时不操作
        // console.log("时间到，退出登录");
        localStorage.setItem(gPublocalStorageItemCheckSessionName, true);
        openDialogToLogout('系统检测到您长时间未使用系统,请您重新登录。');
    }
}
var gPub_CheckIsSessionOutTimer;
function openDialogToLogout(msg) {
    window.clearTimeout(gPub_CheckIsSessionOutTimer);
    window.clearTimeout(gPub_CheckIsNotOperTimer);
    window.sessionStorage.setItem("lockcms",true);
    //如果正在锁屏 则不再跳出锁屏页面
    lockPage();
}

function lockPage(){
    layer.open({
        title : false,
        type : 1,skin:'lock-layer',
        content : '	<div class="admin-header-lock" id="lock-box">'+
            '<div class="admin-header-lock-img"><img src="/static/content/layadmin/images/logo.png"/></div>'+
            '<div class="admin-header-lock-name" id="lockUserName">'+g_pub_SystemName+'</div>'+
            '<div class="input_btn">'+
            '<input type="password" class="admin-header-lock-input layui-input" autocomplete="off" placeholder="请输入密码解锁.." name="lockPwd" id="lockPwd" />'+
            '<button class="layui-btn" id="unlock">解锁</button>'+
            '</div>'+
            '<p>请输入您的登录密码，否则不会解锁成功哦！！！</p>'+
            '</div>',
        closeBtn : 0,
        shade : 0.9
    })
    $(".admin-header-lock-input").focus();
}
layui.define(["jquery"], function (exports) {
    var $ = layui.$,
        layer = layui.layer;
    gPub_CheckIsSessionOutTimer=setInterval(function(){checksessoin();}, 40000);
    var gPublocalStorageItemCheckSession = localStorage.getItem(gPublocalStorageItemCheckSessionName);
    if(!(gPublocalStorageItemCheckSession==null || gPublocalStorageItemCheckSession==undefined)){if(gPublocalStorageItemCheckSession=='true'){openDialogToLogout('系统检测到您的会话已超时或长时间未操作,请您重新登录操作。');}}

    function checksessoin() {
        $.ajax({
            type: "POST",
            url: "/common/getSession?sid="+Math.random(),
            success: function (msg) {
                var msgObj = msg;// eval('(' + msg + ')');
                var dataStr = msgObj.data;
                if (dataStr == "false") {
                    localStorage.setItem(gPublocalStorageItemCheckSessionName, true);
                    openDialogToLogout('系统检测到您的会话已超时,请重新登录操作。');
                }
            }
        });
    }
    // 判断是否显示锁屏
    if(window.sessionStorage.getItem("lockcms") == "true"){
        lockPage();
    }
    // 解锁
    $("body").on("click","#unlock",function(){
        if($(this).siblings(".admin-header-lock-input").val() == ''){
            layer.msg("请输入解锁密码！");
            $(this).siblings(".admin-header-lock-input").focus();
        }else{
            var passWord=$(this).siblings(".admin-header-lock-input").val();
            var passwordCrypto=gpubCryptoStr.Encrypt(passWord);
            $.post("/home/LoginCheck",{password:passwordCrypto,username:$("#nameByUserInfo").val()},function (dataRes) {
                var verify=dataRes.code;
                if(verify==200){
                    //解锁成功执行...
                    window.sessionStorage.setItem("lockcms",false);
                    $(this).siblings(".admin-header-lock-input").val('');
                    localStorage.setItem(gPublocalStorageItemCheckSessionName, false);
                    gPub_CheckIsSessionOutTimer=setInterval(function(){checksessoin();}, 40000);
                    gPub_CheckIsNotOperTimer= window.setInterval(CheckIsOutTime, 5000);
                    layer.closeAll("page");
                }else{
                    layer.msg("密码错误，请重新输入！");
                    $(this).siblings(".admin-header-lock-input").val('').focus();
                }
            })
        }
    });
    $(document).on('keydown', function() {
        if(event.keyCode == 13) {
            $("#unlock").click();
        }
    });

    var checsessoin = {
        // 渲染步骤条
        init: function () {
            /* 鼠标移动事件 */
            $(window).mousemove(function(){
                gpub_Oper_OldTime = new Date().getTime(); //鼠标移入重置停留的时间
                //console.log(gpub_Oper_OldTime);
            });
        }
    };
    checsessoin.init();
    exports("checsessoin", checsessoin);
});


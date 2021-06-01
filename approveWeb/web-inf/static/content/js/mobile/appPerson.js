$(function () {
    var meuns = ['修改密码', '安全退出'];
    var cancel = '取消';
    hui('.icon').click(function(){
        hui.actionSheet(meuns, cancel, function(e){
            // hui.toast(e.innerHTML);
        });
    });
})
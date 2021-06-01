//获取当前地址的params参数
export function getRequestParams() {
  var url = location.search; //获取url中"?"符后的字串
  var theRequest = new Object();
  if (url.indexOf("?") != -1) {
    var str = url.substr(1);
    var strs = str.split("&");
    for(var i = 0; i < strs.length; i ++) {
      theRequest[strs[i].split("=")[0]]=unescape(strs[i].split("=")[1]);
    }
  }
  return theRequest;
}
export const CommonArryOper={
  ArrySearch:function (Arry,qProp,keyword){
    return Arry.filter((item,i)=>{if(item[qProp].includes(keyword)){
      return item}
    });
  },
  ArryIndex:function (Arry,qProp,keyword) {
    var index = Arry.findIndex(item =>{
      if(item[qProp] == keyword){return true;}
    })
    return index;
  },
  ArryDel:function (Arry,qProp,keyword) {
    var index = this.ArryIndex(Arry,qProp,keyword);
    Arry.splice(index, 1);
    return Arry;
  },
  ArryRefrSeqNum:function (Arry,qProp) {
    Arry.forEach((item,i) => {
      item[qProp]=(i+1);
    });
  },
  ArryEdit:function (Arry,qProp,editItem) {
    var index = this.ArryIndex(Arry,qProp,editItem[qProp]);
    var item = Arry[index];
    for (let key in item) {item[key] = editItem[key];}
    return Arry;
  }
}

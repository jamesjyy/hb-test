<template>

  <div v-if="aaa"></div>
</template>

<script>
import user from "../network/role/user";

export default {

  data() {
    return {
      aaa: localStorage.getItem("rolelist"),
      user:JSON.parse(localStorage.getItem("user"))

    };
  },
  methods: {
    initData() {
      //aaa = 用户
      // var beiyongtwo = this.aaa.beiyongtwo;
      if (this.aaa == 1) {
        //超管
        window.location.href = './NoruserManage.html?' + this.user.id + "";
      }else if (this.aaa == 3) {
        //子用户
        window.location.href = '';
        // window.location.href = './DzDataInfo.html?' + this.aaa.id + "";
      }else {

        //debugger

        console.log("打印一下用戶id:"+this.user.id)
        //管理员
        this.id=this.user.id;
        window.location.href = './CompanyManage.html?'+ this.id + "";
      }
    }
  },
  created() {
    this.initData();
  }
}
</script>
<style>

</style>

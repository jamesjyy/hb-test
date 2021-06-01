<template>

  <div v-if="aaa"></div>
</template>

<script>
  export default {

    data() {
      return {
        aaa: localStorage.getItem("rolelist"),
        user: JSON.parse(localStorage.getItem("user"))
      };
    },
    methods: {
      initData() {
        //aaa = 用户
        // var beiyongtwo = this.aaa.beiyongtwo;
        // this.aaa = 2;
        if (this.aaa == 1) {
          //超管
          window.location.href = './DyDataSuper.html?' + this.user.id + ","+this.user.companyName;
        }else if (this.aaa == 2) {
          //管理员
          window.location.href = './DyDataManage.html?'+ this.user.id + ","+this.user.companyName;
        }else {
          //子用户
          window.location.href = './DyDataChild.html?' + this.user.id + ","+this.user.companyName;
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

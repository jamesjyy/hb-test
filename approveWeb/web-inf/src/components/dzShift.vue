<template>

  <div v-if="role"></div>
</template>

<script>
  export default {

    data() {
      return {
        role: localStorage.getItem("rolelist"),
        user: JSON.parse(localStorage.getItem("user")),
        loopidtime: ''
      };
    },
    methods: {
      initData() {

        var strings = this.role.split(",");
        var roleString = strings[0];
        if (roleString == 1) {
          //超管
          window.location.href = './DzDataSuper.html?' + this.user.id + ","+this.user.companyName +"," +roleString;
        }else if (roleString == 3) {
          //子用户
          window.location.href = './DzDataChild.html?' + this.user.id + ","+this.user.companyName + ","+ this.loopidtime + +roleString;
        }else {
          //管理员
          window.location.href = './DzDataManage.html?'+ this.user.id + ","+this.user.companyName +"," +roleString;

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

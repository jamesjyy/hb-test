<template>

  <div v-if="role"></div>
</template>

<script>
  export default {

    data() {
      return {
        role: localStorage.getItem("rolelist"),
        user: JSON.parse(localStorage.getItem("user"))
      };
    },
    methods: {
      initData() {
        var strings = this.role.split(",");
        var roleString = strings[0];
        if (roleString == 1) {
          //超管
          window.location.href = './jfTEVSuper.html?' + this.user.id + ","+this.user.companyName + ","+ roleString;
        } else {
          //    用户/子用户,根据用户权限不同返回的数据不同
          window.location.href = './Jftevdata.html?'+ this.user.id + ","+this.user.companyName + ","+ roleString;

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

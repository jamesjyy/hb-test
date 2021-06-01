<template>
  <div id="app">
    <router-view />
  </div>
</template>

<script>
export default {
  name: "App",
  methods: {
    isMobile() {
      let flag = navigator.userAgent.match(
        /(phone|pad|pod|iPhone|iPod|ios|iPad|Android|Mobile|BlackBerry|IEMobile|MQQBrowser|JUC|Fennec|wOSBrowser|BrowserNG|WebOS|Symbian|Windows Phone)/i
      );
      return flag;
    },
  },
  created: function () {
    var sessionId = localStorage.getItem("sessionId");
    console.log(sessionId == null || sessionId == "");
    console.log(typeof sessionId);
    if (sessionId === null || sessionId === "null" || sessionId === "") {
      setTimeout(function () {
        window.location = "./login.html";
      }, 10);
    }
    mounted: {
      if (this.isMobile) {
        alert("移动端");
        this.$router.replace("/pc_index");
      } else {
        alert("pc端");
        this.$router.replace("/m_index");
      }
    }
  },
};
</script>

<style>
#app {
  font-family: "Avenir", Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: left;
  color: #2c3e50;
  margin-top: 60px;
}
</style>

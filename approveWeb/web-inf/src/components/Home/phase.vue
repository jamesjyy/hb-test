<template>
  <div class="phase">
    <h3>相位动态</h3>
    <div class="box">
      <div class="canvas" id="main3"></div>
      <div class="color"></div>
    </div>
  </div>
</template>
<script>

export default ({
  props: ["phase"],
  data() {
    return {};
  },
  mounted() {
    // this.initEchart3();
  },
  methods: {
  },
  created() {
  }
});
</script>
<style lang="less" scoped>
.phase {
  background: #0a0f43;
  padding-left: 10px;

  h3 {
    text-align: center;
    padding: 22px 0 15px;
    font-size: 16px;
    color: #00fffc;
    font-weight: normal;
    padding-right: 40px;
  }

  .canvas {
    width: 410px;
    height: 180px;
    margin-bottom: 30px;
    display: inline-block;
    vertical-align: top;
  }

  .color {
    display: inline-block;
    vertical-align: top;
    height: 180px;
    width: 10px;
    background: linear-gradient(to top,
    #270000 0%,
    #9a0000 35%,
    #ca8000 65%,
    #f9e600 95%);
  }
}
</style>

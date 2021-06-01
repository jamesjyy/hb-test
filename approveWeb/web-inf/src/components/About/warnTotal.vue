<template>
  <div class="warnTotal">
    <div class="box">
      <div class="echart" id="main3"></div>
      <div class="center">
        <h2>698</h2>
        <p>累计警告次数</p>
      </div>
    </div>

    <ul class="sybmal">
      <li>
        <div class="a1">
          <div class="circle ae"></div>
          <p>AE</p>
        </div>
        <div class="a2">301</div>
        <div class="a3">30.55%</div>
      </li>
      <li>
        <div class="a1">
          <div class="circle tev"></div>
          <p>TEV</p>
        </div>
        <div class="a2">301</div>
        <div class="a3">30.55%</div>
      </li>
      <li>
        <div class="a1">
          <div class="circle hfct"></div>
          <p>HFCT</p>
        </div>
        <div class="a2">301</div>
        <div class="a3">30.55%</div>
      </li>
      <li>
        <div class="a1">
          <div class="circle uhf"></div>
          <p>UHF</p>
        </div>
        <div class="a2">301</div>
        <div class="a3">30.55%</div>
      </li>
    </ul>
  </div>
</template>
<script>
// import { defineComponent } from "vue";
import * as echarts from 'echarts'

export default ({
  data() {
    return {};
  },
  mounted() {
    this.initEchart2();
  },
  methods: {
    initEchart2() {
      this.charts = echarts.init(document.getElementById("main3"));
      // 绘制图表
      this.charts.setOption({
        series: [
          {
            name: "访问来源",
            type: "pie",
            radius: ["70%", "100%"],
            label: {
              show: false,
              position: "center",
            },
            hoverAnimation: false,
            labelLine: {
              show: false,
            },
            data: [
              { value: 1048, name: "AE" },
              { value: 735, name: "TEV" },
              { value: 580, name: "HFCT" },
              { value: 484, name: "UHT" },
            ],
            itemStyle: {
              normal: {
                color: function (params) {
                  //自定义颜色
                  var colorList = ["#00fffc", "#00d884", "#6656ff", "#ffae00"];
                  return colorList[params.dataIndex];
                },
              },
            },
          },
        ],
      });
    },
  },
});
</script>
<style lang="less" scoped>
.warnTotal {
  background: #0a0f43;
  width: 100%;
  height: 180px;
  padding-left: 15px;
  box-sizing: border-box;
  .box {
    width: 180px;
    height: 180px;
    display: inline-block;
    vertical-align: middle;
    position: relative;
    .center {
      position: absolute;
      top: calc(50% - 45px);
      left: calc(50% - 45px);
      width: 90px;
      height: 90px;
      border-radius: 50%;
      color: #00fffc;
      box-sizing: border-box;
      padding-top: 10px;
      h2 {
        text-align: center;
        font-size: 34px;
        margin-bottom: 5px;
      }
      p {
        text-align: center;
        font-size: 12px;
        opacity: 0.8;
      }
    }
  }
  .echart {
    width: 100%;
    height: 100%;
    background: url("../../assets/huan.png") no-repeat 32px 32px;
  }
  .sybmal {
    display: inline-block;
    vertical-align: middle;
    list-style: none;
    line-height: 1.5;
    margin-left: 20px;
    li {
      width: 218px;
      height: 34px;
      border: 1px solid rgba(0, 255, 252, 0.1);
      padding: 0 15px;
      box-sizing: border-box;
      line-height: 34px;
      margin: 10px 0;
      & > div {
        display: inline-block;
        vertical-align: middle;
        font-size: 16px;
        color: #00deff;
        box-sizing: border-box;

        text-align: center;
      }
      .a1 {
        text-align: left;
        width: 38%;
        * {
          display: inline-block;
          vertical-align: middle;
        }
        div {
          width: 10px;
          height: 10px;
          background-color: rgba(0, 222, 255, 0.15);
          border: solid 3px #00fffc;
          border-radius: 50%;
          margin-right: 7px;
        }
        .tev {
          border-color: #00d884;
        }
        .hfct {
          border-color: #6656ff;
        }
        .uhf {
          border-color: #ffae00;
        }
      }
      .a2 {
        width: 26%;
        margin: 0 2%;
      }
      .a3 {
        width: 32%;
      }
    }
  }
}
</style>

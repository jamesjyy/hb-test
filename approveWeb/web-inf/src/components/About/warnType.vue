<template>
  <div class="warnType">
    <div class="echart" id="main2"></div>
    <ul class="sybmal">
      <li>
        <div class="circle ae"></div>
        <p>PM2.5</p>
      </li>
      <li>
        <div class="circle tev"></div>
        <p>温度</p>
      </li>
      <li>
        <div class="circle hfct"></div>
        <p>湿度</p>
      </li>
      <li>
        <div class="circle uhf"></div>
        <p>水浸</p>
      </li>
    </ul>
  </div>
</template>
<script>
// import { defineComponent } from "vue";
import * as echarts from 'echarts'

export default ({
  data() {
    return {
      charts:''
    };
  },
  mounted() {
    this.initEchart2();
  },
  methods: {
    initEchart2() {
      this.charts = echarts.init(document.getElementById("main2"));
      // 绘制图表
      this.charts.setOption({
        grid: {
          top: "6%",
          left: "3%",
          right: "4%",
          bottom: "3%",
          containLabel: true,
        },

        series: [
          {
            name: "面积模式",
            type: "pie",
            radius: [0, 90],
            center: ["50%", "50%"],
            roseType: "area",
            labelLine: {
              show: false,
            },
            label: {
              show: false,
            },
            data: [
              { value: 40, name: "rose 1" },
              { value: 38, name: "rose 2" },
              { value: 32, name: "rose 3" },
              { value: 30, name: "rose 4" },
            ],
            itemStyle: {
              emphasis: {
                shadowBlur: 10,
                shadowOffsetX: 0,
                shadowColor: "rgba(0, 0, 0, 0.5)",
              },
              normal: {
                color: function (params) {
                  //自定义颜色
                  var colorList = [
                    "#38e2e6",
                    "#7263ff",
                    "#00d884",
                    "#0e9cff"
                  ];
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
.warnType {
  background: #0a0f43;
  width: 100%;
  height: 190px;
  .echart {
    width: 220px;
    height: 100%;
    display: inline-block;
    vertical-align: middle;
  }
  .sybmal {
    display: inline-block;
    vertical-align: middle;
    list-style: none;
    line-height: 1.5;
    margin-left: 50px;
    li {
      * {
        display: inline-block;
        vertical-align: middle;
      }
      div {
        width: 20px;
        height: 10px;
        background: #38e2e6;
        border-radius: 2px;
        margin-right: 7px;
      }
      .tev {
        background: #7263ff;
      }
      .hfct {
        background: #00d884;
      }
      .hfct {
        background: #0e9cff;
      }
      p {
        font-size: 14px;
        color: #00deff;
      }
    }
  }
}
</style>

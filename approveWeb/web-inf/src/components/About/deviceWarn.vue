<template>
  <div class="deviceWarn">
    <div class="echart" id="main1"></div>
    <ul class="sybmal">
      <li>
        <div class="circle ae"></div>
        <p>AE</p>
      </li>
      <li>
        <div class="circle tev"></div>
        <p>TEV</p>
      </li>
      <li>
        <div class="circle hfct"></div>
        <p>HFCT</p>
      </li>
      <li>
        <div class="circle uhf"></div>
        <p>UHF</p>
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
      charts: '',
    };
  },
  mounted() {
    this.initEchart2();
  },
  methods: {
    initEchart2() {
      this.charts = echarts.init(document.getElementById("main1"));
      // 绘制图表
      this.charts.setOption({
        tooltip: {
          trigger: "axis",
        },
        grid: {
          top: "5%",
          left: "3%",
          right: "4%",
          bottom: "3%",
          containLabel: true,
        },
        xAxis: {
          type: "category",
          boundaryGap: false,
          data: ["周一", "周二", "周三", "周四", "周五", "周六", "周日"],
          axisLabel: {
            show: true,
            textStyle: {
              color: "#00deff", //这里用参数代替了
            },
          },
          splitLine: {
            //网格线
            lineStyle: {
              type: "dashed", //设置网格线类型 dotted：虚线   solid:实线
              color: "#66698b",
            },
            show: true, //隐藏或显示
            axisTick: {
              show: false, //不显示坐标轴刻度线
            },
          },
        },
        yAxis: {
          type: "value",
          axisLabel: {
            show: true,
            textStyle: {
              color: "#00deff", //这里用参数代替了
            },
          },
          splitLine: {
            //网格线
            lineStyle: {
              type: "dotted", //设置网格线类型 dotted：虚线   solid:实线
              color: "#66698b",
            },
            show: true, //隐藏或显示
            axisTick: {
              show: false, //不显示坐标轴刻度线
            },
          },
        },
        series: [
          {
            name: "AE",
            type: "line",
            data: [3, 35, 20, 30, 5, 28, 10],
            itemStyle: {
              normal: {
                lineStyle: {
                  color: "#00fffc",
                },
              },
            },
          },
          {
            name: "TEV",
            type: "line",
            data: [20, 5, 36, 24, 15, 7, 31],
            itemStyle: {
              normal: {
                lineStyle: {
                  color: "#00d884",
                },
              },
            },
          },
          {
            name: "HFCT",
            type: "line",
            data: [15, 23, 1, 15, 28, 5, 34],
            itemStyle: {
              normal: {
                lineStyle: {
                  color: "#6656ff",
                },
              },
            },
          },
          {
            name: "UHF",
            type: "line",
            data: [32, 3, 30, 16, 5, 30, 20],
            itemStyle: {
              normal: {
                lineStyle: {
                  color: "#ffae00",
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
.deviceWarn {
  background: #0a0f43;
  width: 100%;
  height: 170px;
  .echart {
    width: 320px;
    height: 100%;
    display: inline-block;
    vertical-align: middle;
  }
  .sybmal {
    display: inline-block;
    vertical-align: middle;
    list-style: none;
    line-height: 1.5;
    margin-left: 30px;
    li {
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
        // box-sizing: border-box;
      }
      .tev {
        border-color: #00d884;
      }
      .hfct {
        border-color: #6656ff;
      }
      .hfct {
        border-color: #ffae00;
      }
      p {
        font-size: 14px;
        color: #00deff;
      }
    }
  }
}
</style>

<template>
  <div class="deviceNum" id="main"></div>
</template>
<script>
// import { defineComponent } from "vue";
import * as echarts from "echarts";
export default ({
  data() {
    return {
      charts: '',
    };
  },
  mounted() {
    this.initEchart1();
  },
  methods: {
    initEchart1() {
      this.charts = echarts.init(document.getElementById("main"));
      // 绘制图表
      this.charts.setOption({
        grid: {
          top: 0,
          left: "3%",
          right: "4%",
          bottom: "3%",
          containLabel: true,
        },
        padding: [5, 10],
        xAxis: {
          type: "value",
          boundaryGap: [0, 0.01],
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
          type: "category",
          data: ["金水区", "高新区", "惠济区", "管城区", "中原区", "二七区"],
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
        series: [
          {
            type: "bar",
            data: [182, 234, 290, 104, 131, 630],
            barWidth: 20, //柱图宽度
            itemStyle: {
              normal: {
                color: echarts.graphic.LinearGradient(
                  1,
                  0,
                  0,
                  0,
                  [
                    {
                      offset: 1,
                      color: "#0b2765", // 0% 处的颜色
                    },
                    {
                      offset: 0,
                      color: "#00deff", // 100% 处的颜色
                    },
                  ],
                  false
                ),
              },
            },
            showBackground: true,

            backgroundStyle: {
              color: "rgba(0,222,255,0.15)",
            },
          },
        ],
      });
    },
  },
});
</script>
<style lang="less" scoped>
.deviceNum {
  background: #0a0f43;
  width: 100%;
  height: 486px;
}
</style>

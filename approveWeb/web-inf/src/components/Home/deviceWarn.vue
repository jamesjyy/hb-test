<template>
  <div class="deviceWarn">
    <label><img src="@/assets/label.png" alt=""/>设备数量统计</label>
    <div class="about" id="main"></div>
  </div>
</template>
<script>
import * as echarts from "echarts";

export default ({
  props: ['device'],
  data() {
    return {
      soucedata: []
    };
  },
  mounted() {
    this.$nextTick(function () {
      this.getData()
    })
  },
  methods: {
    initEchart1() {
      var myChart = echarts.init(document.getElementById("main"));
      // 绘制图表
      myChart.setOption({
        grid: {
          top: "5%",
          right: "2%",
          left: "5%",
          bottom: "10%",
        },
        legend: {
          padding: [5, 10],
        },
        tooltip: {},
        xAxis: {
          type: "category",
          data: ["AE", "TEV", "HFCT", "UTF"],
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
          },
        },
        yAxis: {
          type: "value",
          max: 40,
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
            barWidth: 20, //柱图宽度
            itemStyle: {
              normal: {
                color: echarts.graphic.LinearGradient(
                  0,
                  1,
                  0,
                  0,
                  [
                    {
                      offset: 0,
                      color: "#0b2765", // 0% 处的颜色
                    },
                    {
                      offset: 1,
                      color: "#00deff", // 100% 处的颜色
                    },
                  ],
                  false
                ),
              },
            },
            data: this.soucedata,
            showBackground: true,

            backgroundStyle: {
              color: "rgba(0,222,255,0.15)",
            },
          },
        ],
      });
    },
    getData() {
      var jsons = this.device
      this.soucedata = [];
      if (jsons.aecount == undefined || jsons.aecount == "" || jsons.aecount == null) {
        this.soucedata.push(0)
      } else {
        // var sumae = 0;
        // for (var i = 0; i < jsons.ae.length; i++) {
        //   if (!jsons.ae[i].statuscode == 0) {
        //     sumae++;
        //   }
        // }
        // this.soucedata.push(sumae)
        this.soucedata.push(jsons.aecount)
      }

      if (jsons.tevcount == undefined || jsons.tevcount == "" || jsons.tevcount == null) {
        this.soucedata.push(0)
      } else {
        // var sumtev = 0;
        // for (var i = 0; i < jsons.tev.length; i++) {
        //   if (!jsons.tev[i].statuscode == 0) {
        //     sumtev++;
        //   }
        // }
        // this.soucedata.push(sumtev)
        this.soucedata.push(jsons.tevcount)
      }

      if (jsons.hfctcount == undefined || jsons.hfctcount == "" || jsons.hfctcount == null) {
        this.soucedata.push(0)
      } else {
        // var sumhfct = 0;
        // for (var i = 0; i < jsons.hfct.length; i++) {
        //   if (!jsons.hfct[i].statuscode == 0) {
        //     sumhfct++;
        //   }
        // }
        // this.soucedata.push(sumhfct)
        this.soucedata.push(jsons.hfctcount)
      }
      if (jsons.uhfcount == undefined || jsons.uhfcount == "" || jsons.uhfcount == null) {
        this.soucedata.push(0)
      } else {
        // var sumhuhf = 0;
        // for (var i = 0; i < jsons.uhf.length; i++) {
        //   if (!jsons.uhf[i].statuscode == 0) {
        //     sumhuhf++;
        //   }
        // }
        // this.soucedata.push(sumhuhf)
        this.soucedata.push(jsons.uhfcount)
      }
      this.initEchart1()
    }
  },
  created() {

  }
})
;
</script>
<style lang="less" scoped>
.deviceWarn {
  background: #0a0f43;
  height: 310px;
  background: url(../../assets/left-box.png) no-repeat 0 0;
  background-size: 100% 100%;
  margin-top: 20px;
  text-align: left;
  padding: 32px 30px 0;
  box-sizing: border-box;

  label {
    font-size: 18px;
    color: #00fffc;

    img {
      margin-right: 20px;
    }
  }

  .about {
    margin-top: 20px;
    width: 100%;
    height: 208px;
  }
}
</style>

<template>
  <div class="aMap" id="map"></div>
</template>
<script>
// import { defineComponent } from "vue";
import '@/assets/zhengzhou.js'
import * as echarts from "echarts";
export default ({
  data() {
    return {
      charts: '',
    };
  },
  mounted() {
    this.initMap();
  },
  methods: {
    initMap() {
      this.charts  = echarts.init(document.getElementById("map"));
      // 绘制图表
      this.charts.setOption({
        tooltip: {
          trigger: "item",
          formatter: "{b}",
        },
        series: [
          {
            name: "郑州",
            type: "map",
            mapType: "郑州",
            roam: false,
            top: "10%",
            zoom: 1.2,
            x: "10%",
            selectedMode: "single", //multiple多选
            itemStyle: {
              normal: {
                label: {
                  show: true,
                  textStyle: {
                    color: "#231816",
                  },
                },
                areaStyle: { color: "#B1D0EC" },
                color: "#B1D0EC",
                borderColor: "#dadfde", //区块的边框颜色
              },
              emphasis: {
                //鼠标hover样式
                label: {
                  show: true,
                  textStyle: {
                    color: "#fa4f04",
                  },
                },
                areaStyle: { color: "red" },
              },
            },
            data: [
              {
                name: "中原区",
                itemStyle: {
                  normal: {
                    areaColor: "#13d5ff",
                    borderColor: "#edce00",
                  },
                },
              },
              {
                name: "二七区",
                itemStyle: {
                  normal: {
                    areaColor: "#13d5ff",
                    borderColor: "#0abcee",
                  },
                },
              },
              {
                name: "管城回族区",
                itemStyle: {
                  normal: {
                    areaColor: "#92d050",
                    borderColor: "#1ca9f2",
                  },
                },
              },
              {
                name: "金水区",
                itemStyle: {
                  normal: {
                    areaColor: "#88ddf5",
                    borderColor: "#88ddf5",
                  },
                },
              },
              {
                name: "上街区",
                itemStyle: {
                  normal: {
                    areaColor: "#13d5ff",
                    borderColor: "#45b5ef",
                  },
                },
              },
              {
                name: "惠济区",
                itemStyle: {
                  normal: {
                    areaColor: "#13d5ff",
                    borderColor: "#45b5ef",
                  },
                },
              },
              {
                name: "中牟县",
                itemStyle: {
                  normal: {
                    areaColor: "#45b5ef",
                    borderColor: "#45b5ef",
                  },
                },
              },
              {
                name: "巩义市",
                itemStyle: {
                  normal: {
                    areaColor: "#ffd811",
                    borderColor: "#45b5ef",
                  },
                },
              },
              {
                name: "荥阳市",
                itemStyle: {
                  normal: {
                    areaColor: "#ffa312",
                    borderColor: "#45b5ef",
                  },
                },
              },
              {
                name: "新密市",
                itemStyle: {
                  normal: {
                    areaColor: "#92d050",
                    borderColor: "#1ca9f2",
                  },
                },
              },
              {
                name: "新郑市",
                itemStyle: {
                  normal: {
                    areaColor: "#88ddf5",
                    borderColor: "#88ddf5",
                  },
                },
              },
              {
                name: "登封市",
                itemStyle: {
                  normal: {
                    areaColor: "#13d5ff",
                    borderColor: "#45b5ef",
                  },
                },
              }
            ],
          },
        ],
      });
    },
  },
});
</script>
<style lang="less" scoped>
.aMap {
  background: #0a0f43;
  width: 100%;
  height: 500px;
}
</style>

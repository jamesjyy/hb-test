<template>
  <div class="Dataview">
    <div class="main-echarts">
      <div class="sunjays_echarts" id="echartsa" v-bind:style="{ width: 80 }"></div>
      <div class="sunjays_echarts" id="echartsb" v-bind:style="{ width: 120 }"></div>
      <div class="sunjays_echarts" id="echartsc" v-bind:style="{ width: 120 }"></div>
    </div>
  </div>
</template>

<script>
import {request} from '../../src/network/request.js';
import * as echarts from 'echarts';
export default {
  dataz() {
    return {
      // 分页
      pageNum: 1,
      currentPage: 1,
      pageCount: 1000,
      pageSize: 10,
      viewform: '',
      viewsformVisible: false,
      dialogVisible: false,
      floorRange: [],
      dialogTitle:'',
      iframeUrl: '',
      displayOptions: [],
      tableData: [],
      xAxis:[],
      seriesa:[],
      seriesas:[],
      seriesb:[],
      seriesbs:[],
      seriesc:[],
      seriescs:[],
    }
  },
  methods: {
    // 曲线图
    curveecharts() {
      this.$nextTick(() => {
        var myChart = echarts.init(document.getElementById('echartsa'));
        var option;

        option = {
          xAxis: {
            type: 'category',
            data: ['Mon', 'Tue', 'Wed', 'Thu', 'Fri', 'Sat', 'Sun']
          },
          yAxis: {
            type: 'value'
          },
          series: [{
            data: [120, {
              value: 200,
              itemStyle: {
                color: '#a90000'
              }
            }, 150, 80, 70, 110, 130],
            type: 'bar'
          }]
        };
        myChart.setOption(option);
      });
    },
  },
  created() {
    // this.curveecharts();
    // this.initData();
  }
}
</script>
<style>

</style>

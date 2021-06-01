<template>
  <!--  <div>-->
  <!--    <div class="print" ref="print" id="print">-->
  <!--      <div id="report" class="reports" style="width: 1200px;height: 620px;"></div>-->
  <!--    </div>-->
  <!--  </div>-->


  <div class="echartsStyle">
    <div id="Chartmain" class="reports"
         style="height: 400px; -webkit-tap-highlight-color: transparent; user-select: none; position: relative;"
         _echarts_instance_="ec_1621818985160">
      <div
        style="position: relative; width: 880px; height: 400px; padding: 0px; margin: 0px; border-width: 0px; cursor: default;">
        <canvas data-zr-dom-id="zr_0" width="880" height="400"
                style="position: absolute; left: 0px; top: 0px; width: 880px; height: 400px; user-select: none; -webkit-tap-highlight-color: rgba(0, 0, 0, 0); padding: 0px; margin: 0px; border-width: 0px;">
        </canvas>
      </div>
    </div>
  </div>

</template>

<script>
  var padDate = function (value) {
    return value < 10 ? '0' + value : value
  }
  import Print from '@/network/print/print.js'
  import Vue from 'vue'

  Vue.use(Print)

  import {request} from '@/network/request.js'
  import {initData} from '@/network/jftevdatainfo/jftevdatainfo'
  import * as echarts from 'echarts'
  import Qs from 'qs'
  import docxtemplater from 'docxtemplater'
  import PizZip from 'pizzip'
  import JSZipUtils from 'jszip-utils'
  import {saveAs} from 'file-saver'

  export default {
    data() {
      return {
        charts: '',
        //折线图幅值
        peakvalueList: [],
        //温度数据
        temperatureList: [],
        //传递过来的id
        rowId: location.search,
        myChart: '',
        // tevPhaseData: [],
        // tevPhaseData: '',

      }
    },
    methods: {
      draw() {
        setTimeout(() => {
          var chartDom = document.getElementById('Chartmain');
          var x_myChart = echarts.init(chartDom);


          //数据
          var tevPhaseData = '288,31.652628|299,60.652628';


          tevPhaseData = tevPhaseData.split("|");//将字符串拆成数组
          //幅值+度值,(分贝值:31.652628)也相当于度值
          var x_scatterdata = [];
          for (var i = 0; i < tevPhaseData.length; i++) {
            var dian = [];
            if (tevPhaseData[i] == "") {
              continue;
            } else {
              var m = tevPhaseData[i].split(',');
              for (var j = 0; j < m.length; j++) {
                dian.push(m[j]);
              }
              x_scatterdata.push(dian);
            }
          }
          //页面上面的曲线,曲线是用画出来的中间都是按照,形式隔开350,227.2467511415939,351,231.98052211393644,
          var xlinedata = [];
          for (var line_i = 0; line_i < 361; line_i++) {
            xlinedata.push([line_i, 275 * Math.sin(line_i * Math.PI / 180) + 275]);
            //x_scatterdata.push([i,Math.floor(Math.random()*(0-551) + 551)]);
          }
          // 指定图表的配置项和数据
          var xoption = {
            visualMap: {
              show: true,
              type: 'continuous',
              dimension: 2,
              seriesIndex: 1,
              min: 0,
              max: 64,
              orient: 'vertical',
              right: 25,
              top: 'center',
              calculable: false,
              itemHeight: 285,
              //hoverLink:false,
              inRange: {
                color: ['#000000', '#FF0000', '#FF6347', '#FFA500', '#FFD700', '#FFFF00']
              }
            },
            tooltip: {},

            xAxis: {
              name: "°(度)",
              nameLocation: 'middle',
              nameGap: 30,
              type: 'value',
              min: 0,
              max: 360,
              minInterval: 20,
              interval: 20
            },
            yAxis: [{
              name: "幅值(dB)",
              nameLocation: 'middle',
              nameGap: 40,
              type: 'value',
              min: 0,
              max: 550,
              minInterval: 55,
              interval: 55
            }, {
              type: 'value',
              min: 0,
              max: 64,
              minInterval: 4,
              interval: 4,
              position: 'right',
              offset: 5,
              lineStyle: {
                color: '#1E90FF'
              },
              splitLine: {
                show: false
              }

            }]
            ,
            series: [{
              name: '分贝',
              type: 'scatter',
              symbolSize: 6,
              data: x_scatterdata,
              itemStyle: {
                color: 'rgb(255, 0, 0)',
                borderType: 'solid',
                borderWidth: 0
              }
            }, {
              name: '度',
              type: 'line',
              showSymbol: false,
              clip: true,
              data: xlinedata,
              lineStyle: {
                color: 'rgb(25, 25, 112)',
                width: 1,
                type: 'solid'
              }
            }]
          };
          // 使用刚指定的配置项和数据显示图表。
          var x_scatterdata2 = x_scatterdata;
          x_scatterdata = [];
          x_myChart.setOption(xoption);
        },500);
      },


      //     //折线
      //
      //     draw() {
      //       setTimeout(() => {
      //         // this.keyb = key
      //         var chartDom = document.getElementById('report');
      //         this.myChart = echarts.init(chartDom);
      //         var option;
      //         option = {
      //           title: {
      //             text: 'TEV分析',
      //             subtext: '幅值(db)',
      //             // subtext: 'From ExcelHome',
      //             // sublink: 'http://e.weibo.com/1341556070/AjwF2AgQm'
      //           },
      //           visualMap: {
      //             show: true,
      //             type: 'continuous',
      //             dimension: 3,
      //             seriesIndex: 1,
      //             min: 0,
      //             max: 64,
      //             orient: 'vertical',
      //             right: 0,
      //             top: 'center',
      //             calculable: false,
      //             itemHeight: 510,
      //             //hoverLink:false,
      //             inRange: {
      //               color: ['#000000', '#FF0000', '#FF6347', '#FFA500', '#FFD700', '#FFFF00']
      //             }
      //           },
      //           xAxis: {
      //             type: 'category',
      //             //温度数据
      //             data: this.temperatureList,
      //             name: "°(度)",
      //           },
      //           yAxis: [{
      //             type: 'value',
      //           }, {
      //             min: 0,
      //             max: 64,
      //             minInterval: 4,
      //             interval: 4,
      //             position: 'right',
      //             offset: 5,
      //             lineStyle: {
      //               color: '#1E90FF'
      //             },
      //             splitLine: {
      //               show: false
      //             }
      //           }],
      //           series: [{
      //             name: "幅值(dB)",
      //             //幅值
      //             data: this.peakvalueList,
      //             type: 'line',
      //             smooth: true
      //           }]
      //         };
      //         // option &&
      //         this.myChart.setOption(option);
      //       }, 1000);
      //     },
      //查看分析
      viewclick() {
        if (this.rowId.indexOf("?") != -1) {
          this.rowId = this.rowId.replace("?", "");
        }

        request({
          method: 'get',
          url: 'jfTevdatainfo/queryAnalyseData',
          params: {
            id: this.rowId
          }
        }).then(res => {

          //得到散点图数据
          // this.tevPhaseData = res.data.data.list;

          //幅值数据
          this.peakvalueList = res.data.data.peakvalueDate;
          //温度数据
          this.temperatureList = res.data.data.temperatureDate;

        });
        this.draw();

      },
    },
    created() {
      // this.handleSizeChange(10);
      this.viewclick();
      // this.draw();
    },
  }
</script>
<style>
  .reports {
    margin-left: 100px;
  }


  .el-dialog .el-dialog__body {
    flex: 1;
    overflow: auto;
  }

  .echartsStyle{
    width: 1000px;
    height: 500px;
  }

</style>

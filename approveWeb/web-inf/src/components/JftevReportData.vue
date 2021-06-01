<template>
  <div class="tablesup">

    <div class="classButton">
      <el-row :gutter="20">

        <el-col :span="10">
          <div class="grid-content">
            <el-button type="primary" @click="outclick()">打印</el-button>
          </div>
        </el-col>

        <el-col :span="10">
          <div class="grid-content">
            <el-button type="primary" @click="wordDownload()">下载word</el-button>
          </div>
        </el-col>
      </el-row>
    </div>


    <div class="reportPrint" id="reportPrint" ref="reportPrint">
      <table class="tableClass">
        <tr>
          <td class="tdClass" align="center" colspan="8">
            <span class="spanClass">TEV检验报告</span>
          </td>
        </tr>
        <tr>
          <td class="tdClass" align="center" width="243" colspan="2">
            检测日期
          </td>
          <td class="tdClass" align="center" width="243" colspan="2">
            {{ this.superTableData.time }}
          </td>
          <td class="tdClass" align="center" width="242" colspan="2">
            检测人员
          </td>
          <td class="tdClass" align="center" width="242" colspan="2">
            {{ this.superTableData.backupone}}
          </td>
        </tr>
        <tr>
          <td class="tdClass" align="center" width="243" colspan="2">
            站点名称
          </td>
          <td class="tdClass" align="center" width="243" colspan="2">
            {{ this.superTableData.stationname }}
          </td>
          <td class="tdClass" align="center" width="242" colspan="2">
            站点编号
          </td>
          <td class="tdClass" align="center" width="242" colspan="2">
            {{ this.superTableData.stationnumber }}
          </td>
        </tr>
        <tr>
          <td class="tdClass" align="center" width="243" colspan="2">
            测点名称
          </td>
          <td class="tdClass" align="center" width="243" colspan="2">
            {{ this.superTableData.checkstation }}
          </td>
          <td class="tdClass" align="center" width="242" colspan="2">
            测点编号
          </td>
          <td class="tdClass" align="center" width="242" colspan="2">
            {{ this.superTableData.checknumber }}
          </td>
        </tr>
        <tr>
          <td class="tdClass" align="center" width="243" colspan="2">
            设备种类
          </td>
          <td class="tdClass" align="center" width="243" colspan="2">局部放电超声波检测仪</td>
          <td class="tdClass" align="center" width="242" colspan="2">
            缺陷程度
          </td>
          <td class="tdClass" align="center" width="242" colspan="2">
            {{ this.superTableData.backupsix }}
          </td>
        </tr>
        <tr>
          <td class="tdClass" align="center" width="90" colspan="1">
            距离(m)
          </td>
          <td class="tdClass" align="center" width="153" colspan="1">
            {{ this.superTableData.backuptwo }}
          </td>
          <td class="tdClass" align="center" width="90" colspan="1">
            温度(℃)
          </td>
          <td class="tdClass" align="center" width="153" colspan="1">
            {{ this.superTableData.backupthree }}
          </td>
          <td class="tdClass" align="center" width="90" colspan="1">
            湿度(%)
          </td>
          <td class="tdClass" align="center" width="152" colspan="1">
            {{ this.superTableData.backupfour }}
          </td>
          <td class="tdClass" align="center" width="90" colspan="1">
            地域特征
          </td>
          <td class="tdClass" align="center" width="152" colspan="1">
            {{ this.superTableData.backupfive }}
          </td>
        </tr>
        <tr>
          <td class="tdClass" align="center" width="90" colspan="2">峰值</td>
          <td class="tdClass" align="center" width="153" colspan="6">
            {{ this.superTableData.peakvalue }}
          </td>
        </tr>
        <tr>
          <td class="tdClass" align="center" width="90" colspan="1">
            诊断建议
          </td>
          <td class="tdClass" style="padding-left: 10px" align="left" width="880" colspan="7"
          >
            暂无建议
          </td>
        </tr>

        <!--        折线图-->
        <tr>
          <td class="tdClass" align="center" width="970" colspan="8">

            <!--            <div class="print" ref="print" id="print">-->
            <!--              <div id="report" class="reports" style="width: 900px;height: 400px;"></div>-->
            <!--            </div>-->

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

          </td>
        </tr>
        <tr>
          <td class="tdClass" align="center" width="486" colspan="8">
            照片
          </td>
        </tr>

        <tr v-for="(imges, id) in this.srcList" v-bind:key="id">
          <td
            class="tdClass"
            align="center"
            width="486"
            height="320px"
            colspan="8"
          >
            <el-image :src="imges">
              <div slot="error" class="image-slot" id="img">
                <i class="el-icon-picture-outline"></i>
              </div>
            </el-image>
          </td>
        </tr>

      </table>
    </div>

  </div>
</template>

<script>
  import {request} from '@/network/request.js';
  import * as echarts from "echarts";
  import JSZipUtils from "jszip-utils";
  import PizZip from "pizzip";
  import docxtemplater from "docxtemplater";
  import {saveAs} from "file-saver";
  import Print from '@/network/print/breakDownPrint.js'
  import Vue from 'vue'

  Vue.use(Print)

  export default {
    data() {
      return {
        // disabledflg: false

        //当前表格数据
        superTableData: {},
        rowId: location.search,
        //返回的照片url数组,循环遍历
        srcList: [],
        //折线图幅值数据
        peakvalueList: [],
        //温度数据
        temperatureList: [],
        img1: '',
        srcImg: '',
        srcStr: '',
        scatterdata: [],
        xlinedata: [],
        mapBase64: null,
        mapSrc: null,
        imgword: [],
        wordImgeList: [],
      }
    },
    methods: {
      initData() {
        if (this.rowId.indexOf("?") != -1) {
          this.rowId = this.rowId.substr(1)
        }
        request({
          method: 'get',
          url: 'jfTevdatainfo/queryReportData',
          params: {
            id: this.rowId
          }
        }).then((res) => {
          this.superTableData = res.data.data.jfTevdate
          //幅值数据
          var peakvalue = this.superTableData.peakvalue.split(",");
          for (let i = 0; i < peakvalue.length; i++) {
            this.peakvalueList.push(peakvalue[i])
          }
          //温度数据
          var backupthree = this.superTableData.backupthree.split(",");
          for (let i = 0; i < backupthree.length; i++) {
            this.temperatureList.push(backupthree[i])
          }
          var img = this.superTableData.img;
          var strings = img.split(",");
          var imgPath = 'http://localhost:8082/';
          for (let i = 0; i < strings.length; i++) {
            this.srcList.push(imgPath + strings[i])
          }


        });
        this.draw();
      },
      draw() {
        setTimeout(() => {
          var chartDom = document.getElementById('Chartmain');
          var x_myChart = echarts.init(chartDom);
          var tevPhaseData = '288,31.652628|299,60.652628';
          tevPhaseData = tevPhaseData.split("|");//将字符串拆成数组
          //幅值+度值,(分贝值:31.652628)也相当于度值

          for (var i = 0; i < tevPhaseData.length; i++) {
            var dian = [];
            if (tevPhaseData[i] == "") {
              continue;
            } else {
              var m = tevPhaseData[i].split(',');
              for (var j = 0; j < m.length; j++) {
                dian.push(m[j]);
              }
              this.scatterdata.push(dian);
            }
          }
          //页面上面的曲线,曲线是用画出来的中间都是按照,形式隔开350,227.2467511415939,351,231.98052211393644,

          for (var line_i = 0; line_i < 361; line_i++) {
            this.xlinedata.push([line_i, 275 * Math.sin(line_i * Math.PI / 180) + 275]);
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
              data: this.scatterdata,
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
              data: this.xlinedata,
              lineStyle: {
                color: 'rgb(25, 25, 112)',
                width: 1,
                type: 'solid'
              }
            }]
          };
          // 使用刚指定的配置项和数据显示图表。
          var x_scatterdata2 = this.scatterdata;
          this.scatterdata = [];
          x_myChart.setOption(xoption);
        }, 500);
      },
      outclick() {
        this.$print(this.$refs.reportPrint)
      },
      wordDownload() {
        // var key = this.keyb
        let myChart1 = echarts.init(document.getElementById('Chartmain'));
        var time = this.superTableData.time;   //检测时间
        var stationname = this.superTableData.stationname;    // * 站点名称
        var stationnumber = this.superTableData.stationnumber;       // * 站点编号
        var checkstation = this.superTableData.checkstation;       // * 测点名称
        var checknumber = this.superTableData.checknumber;         // * 测点编号
        var peakvalue = this.superTableData.peakvalue;      // * 峰值,,传递幅值的字符串,中间按照","来进行隔开
        var img1 = this.superTableData.img;           // * 照片
        var backupone = this.superTableData.backupone;     // * 检测人员
        var backuptwo = this.superTableData.backuptwo;     //距离
        var backupthree = this.superTableData.backupthree;  // * 温度,,温度的字符串,中间按照","隔开
        var backupfour = this.superTableData.backupfour;  // * 湿度
        var backupfive = this.superTableData.backupfive;  // * 地域特征
        var backupsix = this.superTableData.backupsix; // * 缺陷程度

        let docxname = "TEV检验报告" + ".docx";

        myChart1.setOption({
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
            data: this.scatterdata,
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
            data: this.xlinedata,
            lineStyle: {
              color: 'rgb(25, 25, 112)',
              width: 1,
              type: 'solid'
            }
          }]
        });
        // 获取echart图片
        // ECHARTS图表的图片并转为base64格式
        this.img1 = myChart1.getDataURL({
          pixelRatio: 2,      // 导出的图片分辨率比例，默认为 1。
          backgroundColor: '#fff'   // 导出的图片背景色，默认使用 option 里的 backgroundColor
        });

        //这里要引入处理图片的插件，下载docxtemplater后，引入的就在其中了
        var ImageModule = require('docxtemplater-image-module-free');
        var fs = require("fs");
        const expressions = require("angular-expressions");
        let docxsrc = "../../static/JfTEVWord.docx";
        let _this = this;
        let imgword = JSON.parse(JSON.stringify(_this.imgword))
        // 读取并获得模板文件的二进制内容
        JSZipUtils.getBinaryContent(docxsrc, function (error, content) {
          if (error) {
            throw error;
          }
          ;
          expressions.filters.size = function (input, width, height) {
            return {
              data: input,
              size: [width, height],
            };
          };

          // 图片处理
          let opts = {}
          opts = {centered: false};
          opts.getImage = (chartId) => {
            return _this.base64DataURLToArrayBuffer(chartId);
          }

          opts.getSize = function (img, tagValue, tagName) {
            //自定义指定图像大小
            return [600, 400];
          }
          // 创建一个PizZip实例，内容为模板的内容
          let zip = new PizZip(content);
          // 创建并加载docxtemplater实例对象
          let doc = new docxtemplater();
          doc.attachModule(new ImageModule(opts));
          doc.loadZip(zip);
          // 设置模板变量的值
          doc.setData({
            time: time,//检测时间
            stationname: stationname,// * 站点名称
            stationnumber: stationnumber,// * 站点编号
            checkstation: checkstation,// * 测点名称
            checknumber: checknumber,// * 测点编号
            peakvalue: peakvalue,// * 峰值,,传递幅值的字符串,中间按照","来进行隔开
            backupone: backupone,// * 检测人员
            backuptwo: backuptwo,//距离
            backupthree: backupthree,// * 温度,,温度的字符串,中间按照","隔开
            backupfour: backupfour,// * 湿度
            backupfive: backupfive,     // * 地域特征
            backupsix: backupsix,       // * 缺陷程度
            image1: _this.img1,      //折线图
            badnessImges: imgword,// * 照片
          });

          try {
            // 用模板变量的值替换所有模板变量
            doc.render();
          } catch (error) {
            // 抛出异常
            let e = {
              message: error.message,
              name: error.name,
              stack: error.stack,
              properties: error.properties
            };

            throw error;
          }

          // 生成一个代表docxtemplater对象的zip文件（不是一个真实的文件，而是在内存中的表示）
          let out = doc.getZip().generate({
            type: "blob",
            mimeType:
              "application/vnd.openxmlformats-officedocument.wordprocessingml.document"
          });
          // 将目标文件对象保存为目标类型的文件，并命名
          saveAs(out, docxname);
        });
      },
      // 导出echarts图片，格式转换
      base64DataURLToArrayBuffer(dataURL) {
        const base64Regex = /^data:image\/(png|jpg|svg|svg\+xml);base64,/;
        if (!base64Regex.test(dataURL)) {
          return false;
        }
        const stringBase64 = dataURL.replace(base64Regex, "");
        let binaryString;
        if (typeof window !== "undefined") {
          binaryString = window.atob(stringBase64);
        } else {
          binaryString = new Buffer(stringBase64, "base64").toString("binary");
        }
        const len = binaryString.length;
        const bytes = new Uint8Array(len);
        for (let i = 0; i < len; i++) {
          const ascii = binaryString.charCodeAt(i);
          bytes[i] = ascii;
        }
        return bytes.buffer;
      }, angularParser(tag) {

        const expr = expressions.compile(tag.replace(/’/g, "'"));
        return {
          get(scope) {
            return expr(scope);
          },
        };
      },
      reportOpen() {
        //初始化方法时候,加延时任务,可以使this.superTableData.img;有值
        setTimeout(() => {
          this.report = true;
          this.wordImgeList = null;
          // reportNoImges  当前行的图片url
          var img = this.superTableData.img;
          var imgPath = 'http://localhost:8082/';
          var imgSplit = [];
          if (img != null && img != '') {
            var strings = img.split(',');
            for (let i = 0; i < strings.length; i++) {
              imgSplit.push(imgPath + strings[i] );
            }
          }

          this.wordImgeList = imgSplit;
          // this.wordImgeList.forEach(item => {
          imgSplit.forEach(item => {
            let no = {
              src: ''
            };
            this.getBase64(item, res => {
              no.src = res
              this.imgword.push(no)
            })
          });
        }, 500);
      },    // 根据url转换base64
      getBase64(url, callback) {
        let Img = new Image()
        let dataURL = ''
        Img.onload = function () {
          let canvas = document.createElement('canvas')
          let width = Img.width
          let height = Img.height
          canvas.width = width
          canvas.height = height
          canvas.getContext('2d').drawImage(Img, 0, 0, width, height)
          dataURL = canvas.toDataURL('image/png')
          return callback ? callback(dataURL) : null
        }
        Img.setAttribute('crossOrigin', 'anonymous')
        Img.src = url
      },
    },

    created() {
      this.initData();
      this.reportOpen();
    },
  }
</script>
<style>


  .tableClass {

    width: 787px;
    height: 1100px;
    font-size: 15px;
    border-top: 1px solid #999;
    border-left: 1px solid #999;
    background-color: #fff;
    border-spacing: 0; /* 去掉边框间隙 */
    font-family: 华文中宋

  }

  .classButton {
    margin-left: 100px
  }


  .tdClass {
    border-bottom: 1px solid #999;
    border-right: 1px solid #999;
    font-size: 16px;
    padding: 10px 0;
  }

  .tablesup {
    margin-left: 300px
  }

  .spanClass {
    font-size: 26px;
  }

  .demo-image .block,
  .demo-image__error .block,
  .demo-image__placeholder .block {
    padding: 30px 0;
    text-align: center;
    border-right: 1px solid #eff2f6;
    display: inline-block;
    width: 320px;
    box-sizing: border-box;
    vertical-align: top;
  }

  .grid-content {

    width: 30%;
    height: 50px;

  }

</style>



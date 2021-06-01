<template>
  <div class="DataAnalyse">
    <div class="tableth">

      <el-button size="small" class="tubiao" @click="show2">杆塔图</el-button>
      <el-button size="small" class="tubiao" @click="show1">柱状图</el-button>

      <div class="FileList" ref="print" id="print">
        <div>
          <!--          杆塔图-->
          <!--          加上@click="window.location.href = './DyDataCollect.html?'" 跳转页面 -->
          <div id="div2" v-show="towerflg">
            <canvas id="diagonal" style="border:1px solid;background:rgba(107, 175, 237,1);position: relative; "
                    width="1350px" height="900px">
            </canvas>
          </div>
          <!--          //柱状图-->
          <div class="buttonto" id="div1" v-show="histogramflg">
            <el-button type="primary" class="print" @click="outclick()">打印</el-button>
            <el-button type="primary" class="download" @click="wordclick()">word下载</el-button>
            <!--          <el-button type="primary" @click="downloadlick()">下载</el-button>-->
            <div id="report">
              <div class="hbh2">
                <el-row :span="24" class="el-row-row">
                  <el-col :span="1.5" class="loopnamea">
                    <div>回路为</div>
                  </el-col>
                  <el-col :span="1.5" class="loopnameb" style="color: red">
                    <div>{{ loopnameb }}</div>
                  </el-col>
                  <el-col :span="1.5" class="loopnamec">
                    <div>的第</div>
                  </el-col>
                  <el-col :span="1.5" class="number" style="color: red">
                    <div>{{ number }}</div>
                  </el-col>
                  <el-col :span="1.5" class="loopnamee">
                    <div>塔杆上</div>
                  </el-col>
                  <el-col :span="5.5" class="loopnamee">
                    <div>的绝缘子电压分析</div>
                  </el-col>
                </el-row>
              </div>

              <div id="gzreport" class="reports" style="width: 900px;height: 420px;"></div>

              <div class="hbh3">
                <el-row :span="24" class="el-row-row" v-if="flg">
                  <el-col :span="1.5" class="loopnamea">
                    <div>经分析,在</div>
                  </el-col>
                  <el-col :span="1.5" class="loopnameb" style="color: red">
                    <div>{{ time }}</div>
                  </el-col>
                  <el-col :span="1.5" class="loopnamec">
                    <div>时检测回路为</div>
                  </el-col>
                  <el-col :span="1.5" class="loopnamed" style="color: red">
                    <div>{{ loopnameb }}</div>
                  </el-col>
                  <el-col :span="1.5" class="loopnamee">
                    <div>上的第</div>
                  </el-col>
                  <el-col :span="1.5" class="loopnamed" style="color: red">
                    <div>{{ number }}</div>
                  </el-col>
                  <el-col :span="1.5" class="loopnamee">
                    <div>杆塔的故障结果如上图所示请及时更换!</div>
                  </el-col>
                </el-row>

                <el-row :span="24" class="el-row-rows" v-if="flgs">
                  <el-col :span="24" class="loopnamee">
                    <div>经过分析,绝缘子电压暂无异常</div>
                  </el-col>
                </el-row>
              </div>
            </div>
          </div>
        </div>

      </div>
    </div>
  </div>
</template>


<script>
  import Print from '@/network/print/print.js'
  import {drawDiagonal} from '@/network/dyDataAnalyse/cancas.js'

  Vue.use(Print)
  import {request} from '@/network/request.js';
  import {initData} from '@/network/dyDataAnalyse/dyDataAnalyse.js';
  import * as echarts from 'echarts';
  import Qs from 'qs'
  import JSZipUtils from "jszip-utils";
  import PizZip from "pizzip";
  import docxtemplater from "docxtemplater";
  import {saveAs} from "file-saver";
  import Vue from "vue";


  export default {
    data() {
      return {
        quaflat: "0",
        img1: '',
        tableData: [],

        chartData: "",
        results: '',
        slicess: '',
        datanums: '',
        datalings: '',
        histogramData: [],
        //杆塔数据
        towerAll: [],
        bid: decodeURI(location.search),
        // pians: [],
        activeName: '1',//展开name为1的折叠板

        //图表转换
        towerflg: false,
        histogramflg: false,

        charts: '',
        istag: true,
        viewsformVisible: false,
        // dialogVisible: false,
        // dialogVisiblerr: false,
        floorRange: [],
        dialogTitle: '',
        iframeUrl: '',
        imgform: '',
        img: '',
        mainWinth: '',
        bianliang1: '',
        bianliang2: '',
        flg: '',
        flgs: '',
        id: '',
        //变量
        tableDatabl: [],
        loopnameb: '',
        keyId: '',
        number: '',
        xiang: '',
        time: '',
        chuan: '',
        pians: [],
        pian: '',
        celiangzhi: '',
        zhuangtai: '',
        chuanh: '',
        imageListBase: [],
        displayOptions: [],
        score: '3500',
        source: [['测量值']],


        userid: '',
        loopidname: '',
        towernum: '',
        phaseRqs: '',
        bunchRqs: '',
        companyName: '',
        role: '',


      };
    },
    methods: {
      initData() {
        if (this.bid.indexOf("?") != -1) {
          this.bid = this.bid.substr(1)
        }
        var strings = this.bid.split(",");
        for (var i = 0; i < strings.length; i++) {
          this.userid = strings[0];
          this.companyName = strings[1];
          this.role = strings[2];
          this.loopidname = strings[3];
          this.towernum = strings[4];
          this.phaseRqs = strings[5];
          this.bunchRqs = strings[6];
        }
        // request({
        //   method: 'get',
        //   url: 'dyData/analyseDataAll',
        //   params: {
        //     id: this.userid
        //   }
        // }).then(res => {
        //   this.tableData = res.data.data.list;
        //   for (let i = 0; i < this.tableData.length; i++) {
        //     this.tableDatabl[i] = this.tableData[i]
        //     this.id = this.tableData[i].id;
        //   }
        //   if (this.id == null || this.id == '') {
        //     this.flg = false
        //     this.flgs = true
        //   } else {
        //     this.flg = true
        //     this.flgs = false
        //   }
        // });
      },

      skipClick() {
        setTimeout(() => {
          // var tab = [];

          request({
            method: 'get',
            url: 'dyData/queryChart',
            params: {
              id: this.userid,
              companyname: this.companyName,
              loopidname: this.loopidname,
              towernum: this.towernum,
            }
          }).then(ss => {
            // setTimeout(()=>{   //设置延迟执行
            this.chartData = '',
              this.chartData = ss.data.data.list;

            this.keyId = this.chartData.id
            //线路名称
            this.loopnameb = this.chartData.loopname;
            //塔杆号
            this.number = this.chartData.towernum
            this.time = this.chartData.time
            this.results = this.chartData.result;
            this.slicess = this.chartData.slices;
            this.datanums = this.chartData.datanum;
            this.datalings = this.chartData.dataling;
            var arr = this.results.split(",");
            for (let j = 0; j < arr.length; j++) {
              let arr1 = []
              let arr2 = arr[j].split('/')
              arr1[0] = '第' + arr2[0] + '串'
              arr1[1] = arr2[1]
              arr[j] = arr1
            }
            this.results = arr
            // this.draw("report");
            this.draws(this.slicess, this.datanums, this.datalings);
            this.xiangbie();
          });
        }, 500);
      },
      show1() {
        this.towerflg = false;
        this.histogramflg = true;
        // document.getElementById("div1").style.display = "block";
        // document.getElementById("div2").style.display = "none";
      },
      show2() {
        this.towerflg = true;
        this.histogramflg = false;
        // document.getElementById("div2").style.display = "block";
        // document.getElementById("div1").style.display = "none";
      }, skipDatainfo() {
        window.location.href = './DyDataInfo.html?';
      }, xiangbie(row) {
        this.userid == "";
        request({
          method: 'get',
          url: 'dyData/queryTower',
          params: {
            id: this.userid,
            companyname: this.companyName,
            role: this.role,
            loopidname: this.loopidname,
            towernum: this.towernum,
            phaseRqs: this.phaseRqs,
            bunchRqs: this.bunchRqs
          }
        }).then(res => {
          this.towerAll = res.data.data.list;
          //相别
          drawDiagonal(this.towerAll, this.loopnameb, this.number);
        });
      },
      getlisttable(flat) {
        this.quaflat = flat
      },
      //折叠面板
      handleChange(val) {
      },
      draws(slicess, datanums, datalings) {
        // var aaa = '3,6,9';//串号
        // var bbb = '2,4,6';//总量
        // var ccc = '1,3,5';//故障数量
        var data = slicess;//串号
        var datanum = datanums;//总量
        var dataling = datalings;//故障数量
        var ex = 'false';
        // 基于准备好的dom，初始化echarts实例
        var myChart = echarts.init(document.getElementById('gzreport'))
        // var data = 'A1';
        // var datanum =' 18 ';
        // var dataling =' 0 ';
        data = data.split(",");//将字符串拆成数组,  串号
        datanum = datanum.split(",");//将字符串拆成数组,
        dataling = dataling.split(",");//将字符串拆成数组
        // console.log(data,datanum,dataling);
        // console.log(data);//控制台打印输出
        // var data = [550, 2000, 3006, 1000, 1000, 2500,1700, 2000, 3006, 1000, 1000, 2500,1700,550, 2000, 3006, 1000, 1000, 2500,1700, 2000];
        var option = getOption(data, datanum, dataling);

        // 把获取参数封装成方法
        function getOption(data, datanum, dataling) {

          var option = {
            backgroundColor: '#fff',
            legend: {
              bottom: 5,
              textStyle: {
                color: '#000',
                fontSize: 18,
              },
              data: ['故障值', '总量']
            },
            grid: {
              left: '3%',
              right: '4%',
              bottom: '10%',
              containLabel: true
            },
            tooltip: {
              show: "true",
              trigger: 'axis',
              axisPointer: { // 坐标轴指示器，坐标轴触发有效
                type: 'shadow' // 默认为直线，可选为：'line' | 'shadow'
              }
            },
            toolbox: {
              show: true,
              feature: {
                mark: {show: true},
                dataZoom: {show: true},
                dataView: {show: true, readOnly: false},
                magicType: {show: true, type: ['line', 'bar']},
                saveAsImage: {show: true}
              }
            },
            xAxis: [{
              type: 'category',
              name: '串号',
              axisTick: {
                show: true
              },
              axisLine: {
                show: true,
                lineStyle: {
                  color: '#000',
                }
              },
              nameTextStyle: {
                fontSize: 13,
              },
              data: data    //片号
            }, {
              type: 'category',
              axisLine: {
                show: false
              },
              axisTick: {
                show: false
              },
              axisLabel: {
                show: false
              },
              splitArea: {
                show: true
              },
              splitLine: {
                show: true
              },
              data: data
            },

            ],
            yAxis: {
              type: 'value',
              name: '绝缘子片数',
              axisTick: {
                show: true
              },
              axisLine: {
                show: false,
                lineStyle: {
                  color: '#000',

                }
              },
              splitLine: {
                show: false
              },
              nameTextStyle: {
                fontSize: 18,
              }
            },
            series: [{
              name: '总量',
              type: 'bar',
              XAxisIndex: 1,
              itemStyle: {
                normal: {
                  show: true,
                  color: '#277ace',
                  barBorderRadius: 0, //圆角
                  borderWidth: 0,
                  borderColor: '#333',
                }
              },
              barGap: '-100%',
              barCategoryGap: '60%',
              barWidth: '自适应',
              data: datanum//绝缘子片数
            }, {
              name: '故障值',
              type: 'bar',
              itemStyle: {
                normal: {
                  show: true,
                  color: '#5de3e1',
                  barBorderRadius: 0, //圆角
                  borderWidth: 0,
                  borderColor: '#333',
                }
              },
              barGap: '-100%',
              barCategoryGap: '60%',
              barWidth: '自适应',
              data: dataling//零值个数
              // data: [0, 1, 2, 1, 2 , 1, 1, 1, 2, 1,1]

            }

            ]
          };

          return option;
        }

        // 使用刚指定的配置项和数据显示图表。
        myChart.setOption(getOption(data, datanum, dataling));
      },

      //打印
      outclick() {
        this.$print(this.$refs.print)
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
      },

      wordclick() {
        // var key = this.keyb
        let myChart1 = echarts.init(document.getElementById('gzreport'))
        let docxname = this.loopnameb + this.number + ".docx";
        let numberb = this.number;
        let xianlu = this.loopnameb;
        for (let i = 0; i < this.tableDatabl.length; i++) {
          //相别
          this.tableDatabl[i] = {
            phase: this.tableDatabl[i].phase,
            // 串号
            strand: this.tableDatabl[i].strand,
            //片号
            slice: this.tableDatabl[i].slice,
            //测量值
            measured: this.tableDatabl[i].measured,
            //状态
            status: this.tableDatabl[i].status,
          }
        }
        myChart1.setOption({
          title: {
            text: '绝缘子分析',
            subtext: '片',
            // subtext: 'From ExcelHome',
            // sublink: 'http://e.weibo.com/1341556070/AjwF2AgQm'
          },
          tooltip: {
            trigger: 'axis',
            position: function (pt) {
              return [pt[0], '10%']
            },
          },
          toolbox: {
            show: true,
            feature: {
              dataZoom: {
                yAxisIndex: 'none',
              },
              magicType: {show: true, type: ['line', 'bar']},
              dataView: {show: true, title: '数据视图', readOnly: true},
              restore: {},
              saveAsImage: {
                show: true,
                excludeComponents: ['toolbox'],
                pixelRatio: 2,
              },
            },
            iconStyle: {
              // normal: {
              color: 'white', //设置颜色
              // },
            },
          },
          legend: {},

          xAxis: {type: 'category'},
          yAxis: {},
          grid: {bottom: 30},
          series: [
            {
              name: '片数',
              type: 'bar', //表示这个是柱状图
              barCategoryGap: '30%',
              itemStyle: {color: '#81cebe'}, //定义颜色
              data: this.results
            },
          ],
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
        let docxsrc = "../../static/dyfaultwordout.docx";
        let _this = this;

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

          function angularParser(tag) {
            const expr = expressions.compile(tag.replace(/’/g, "'"));
            return {
              get(scope) {
                return expr(scope);
              },
            };
          }

          // 图片处理
          let opts = {}
          opts = {centered: false};
          opts.getImage = (chartId) => {
            this.base64DataURLToArrayBuffer(chartId);
            return this.base64DataURLToArrayBuffer(chartId);
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
            image1: _this.img1,
            number: numberb,
            loopnameb: xianlu,
            table: _this.tableDatabl,
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
      }
    }, skipDyDataInfo() {
      window.location.href = './DyDataInfo.html?'
    },
    created() {
      this.initData();
      this.skipClick();
      this.towerflg = true;
      this.histogramflg = false;
    },
  }
</script>
<style>

  .tableth {
    margin-top: 50px;
    margin-left: 50px;
  }

  .dyFault {
    text-align: center;
    margin-top: 50px;
    left: 50px;
    font-size: 20px;
  }

  .sta {
    colou: red;
  }

  .reports {
    margin-left: 100px;
  }

  .hbh2 {
    align-content: center;
    margin-left: 350px;
  }

  .hbh3 {
    margin-left: 50px;
  }

  .el-row-row {
    text-align: center;
    font-weight: bold;
    font-size: 25px;
  }

  .el-row-rows {
    left: 300px;
    font-weight: bold;
    font-size: 25px;
  }

  .reports {
    margin-left: 100px;
  }

  .el-dialog {
    flex: 1;
    overflow: auto;
  }

  .buttonto {
    margin-left: 100px;
    /*margin-bottom: 50px;*/
  }

  .tubiao {
    margin-left: 50px;
    margin-top: 50px;
    font-size: 20px;
    /*color: #fff;*/
    /*background-color: #FC813B ;*/
    /*border-color: #FC813B;*/
  }

  .print {
    margin-left: 1200px;
  }

  .download {
    margin-left: 1400px;
  }

</style>

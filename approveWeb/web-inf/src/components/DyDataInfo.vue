<template>
  <div class="DataInfo">
    <el-row :span="24">
      <el-col :span="1.5" class="towernums">
        <div class="ganta">杆塔号:</div>
      </el-col>
      <el-col :span="1.5" style="margin-right: 50px">
        <el-input placeholder="请输入杆塔号查询" v-model="searchData.towernum"></el-input>
      </el-col>

      <el-col :span="2" class="loopnames" v-if="false">
        <div>线路名称:</div>
      </el-col>
      <el-col :span="2.2" v-if="false">
        <el-input placeholder="请输入线路名称查询" v-model="searchData.loopname"></el-input>
      </el-col>
      <el-col :span="2" class="checkDate">
        <span>日期:</span>
      </el-col>

      <el-col :span="1.5">
        <el-date-picker
          v-model="searchData.searchDate"
          type="daterange"
          range-separator="至"
          start-placeholder="开始日期"
          end-placeholder="结束日期"
          align="right"
          value-format="yyyy-MM-dd"
        ></el-date-picker>
      </el-col>
      <el-col :span="2" style="text-align: center">
        <el-button type="primary" icon="el-icon-search" @click="search">查询</el-button>
      </el-col>
    </el-row>
    <div class="tableth">
      <el-table :data="tableData" border style="width: 81%">
        <el-table-column sortable prop="id" label="ID" width="150" align="center"></el-table-column>
        <el-table-column sortable prop="loopname" label="线路" width="130" align="center"></el-table-column>
        <el-table-column sortable prop="towernum" label="杆塔号" width="120" align="center"></el-table-column>
        <el-table-column sortable prop="phase" label="相别" width="130" align="center"></el-table-column>
        <el-table-column sortable prop="strand" label="串号" width="130" align="center"></el-table-column>
        <el-table-column sortable prop="result" label="结果" width="162" align="center">
          <template slot-scope="scope">
            <el-select
              size="mini"
              align="center"
              v-model="scope.row.selected"
              placeholder="请选择"
              @change="display(scope,$event)"
            >
              <el-option
                v-for="(item,index) in scope.row.result"
                :key="index"
                :label="item.join(':')"
                :value="index"
              ></el-option>
            </el-select>
          </template>
        </el-table-column>
        <el-table-column sortable label="分析结果" width="200" align="center">
          <template slot-scope="scope">
            <el-button type="text" size="small" @click="viewclick(scope)">查看分析</el-button>
          </template>
        </el-table-column>
        <el-table-column sortable prop="time" label="检查时间" width="160" align="center"></el-table-column>
        <el-table-column sortable prop="userid" label="用户id" v-if="false" width="300"></el-table-column>
        <el-table-column sortable label="操作" width="150" align="center">
          <template slot-scope="scope">
            <el-button type="text" size="small" @click="deleteclick(scope.row)">删除</el-button>
          </template>
        </el-table-column>
      </el-table>
    </div>
    <div class="block">
      <el-pagination
        @size-change="handleSizeChange"
        @current-change="handleCurrentChange"
        :current-page="currentPage"
        :page-sizes="[10, 20, 30, 40]"
        :page-size="pageSize"
        layout="total, sizes, prev, pager, next, jumper"
        :total="totalCount"
      ></el-pagination>
    </div>
    <el-dialog
      :visible.sync="dialogVisible"
      :close-on-click-modal="false"
      width="80%"
      style="align-content: center"
    >
      <div class="print" ref="print" id="print">
        <div class="buttonto">
          <el-button type="primary" @click="outclick()">打印</el-button>
          <el-button type="primary" @click="wordclick()">word下载</el-button>
          <!--          <el-button type="primary" @click="downloadlick()">下载</el-button>-->
        </div>
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
              <div>塔杆上的第</div>
            </el-col>
            <el-col :span="1.5" class="xiang" style="color: red">
              <div>{{ xiang }}</div>
            </el-col>
            <el-col :span="5.5" class="loopnamee">
              <div>相上传的绝缘子电压分析</div>
            </el-col>
          </el-row>
        </div>
        <div id="report" class="reports" style="width: 1200px;height: 620px;"></div>
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
              <div>杆塔上的第</div>
            </el-col>
            <el-col :span="1.5" class="loopnamed" style="color: red">
              <div>{{ xiang }}</div>
            </el-col>
            <el-col :span="1.5" class="loopnamee">
              <div>相上的第</div>
            </el-col>
            <el-col :span="1.5" class="loopnamed" style="color: #ff0000">
              <div>{{ chuan }}</div>
            </el-col>
            <el-col :span="1.5" class="loopnamee">
              <div>串上的</div>
            </el-col>
            <el-col :span="1.5" class="loopnamed" style="color: #ff0000">
              <div>{{ pian }}</div>
            </el-col>
            <el-col :span="5.5" class="loopnamee">
              <div>接近零值,请及时更换!</div>
            </el-col>
          </el-row>
          <el-row :span="24" class="el-row-row" v-if="flgs">
            <el-col :span="24" class="loopnamee">
              <div>经过分析,绝缘子电压暂无异常</div>
            </el-col>
          </el-row>
        </div>
      </div>
    </el-dialog>
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
import {initData} from '@/network/dydatainfo/dydatainfo'
import * as echarts from 'echarts'
import Qs from 'qs'
import docxtemplater from 'docxtemplater'
import PizZip from 'pizzip'
import JSZipUtils from 'jszip-utils'
import {saveAs} from 'file-saver'

export default {
  data() {
    return {
      // 分页
      pageNum: 1,
      pageCount: 1000,
      pageSize: 0,
      //当前页码
      currentPage: 1,
      //查询的页码
      start: 1,
      //默认数据总数
      totalCount: 0,
      charts: '',
      istag: true,
      viewsformVisible: false,
      dialogVisible: false,
      dialogVisiblerr: false,
      floorRange: [],
      dialogTitle: '',
      iframeUrl: '',
      imgform: '',
      img1: '',
      mainWinth: '',
      bianliang1: '',
      bianliang2: '',
      flg: '',
      flgs: '',
      keyb: '',
      //变量
      loopnameb: '',
      number: '',
      xiang: '',
      time: '',
      chuan: '',
      pians: [],
      pian: '',
      echartUrl1: '',
      tableDatabl: [],
      imageListBase: [],
      displayOptions: [],
      //搜索条件
      searchData: {
        loopname: '',
        towernum: '',
        searchDate: [],
      },

      tableData: [],
      score: '3500',
      source: [['测量值']],
    }
  },
  methods: {
    handleSelectionChange(val) {
      // this.upditionalSelection = val;
    },
    //每页显示数据量变更
    handleSizeChange(val) {
      this.pagesize = val
      this.search()
    },
    //页码变更
    handleCurrentChange(val) {
      this.currentPage = val
      this.search()
    },
    display(scope, i) {
      let obj = scope.row
      obj.selected = obj.result[i].join(':')
      this.$set(this.tableData, scope.$index, obj)
    },
    handleClick(row) {
    },
    //查询
    search: function () {
      var initjson = this.initmehod()
      console.log(initjson)
      // if (initjson =="" || initjson == undefined){
      //   this.searchData.loopname=""
      // }
        this.searchData.loopname = initjson.loopname
      let strsearchDate = ''
      if (this.searchData.searchDate != null) {
        strsearchDate = this.searchData.searchDate.join(',')
      }
      this.initData({
        loopname: this.searchData.loopname,
        searchDate: strsearchDate,
        towernum: this.searchData.towernum,
        pageNum: this.currentPage,
        pageSize: this.pagesize,
      })
    },
    initmehod() {
      this.urlcompanyname = decodeURI(location.search); //获取url中"?"符后的字串 ('?modFlag=business&role=1')
      var theRequest = new Object();
      if (this.urlcompanyname.indexOf("?") != -1) {
        var str = this.urlcompanyname.substr(1); //substr()方法返回从参数值开始到结束的字符串；
        var strs = str.split("&");
        for (var i = 0; i < strs.length; i++) {
          theRequest[strs[i].split("=")[0]] = (strs[i].split("=")[1]);
        }
        return theRequest //此时的theRequest就是我们需要的参数；
      }
    },
    //柱状图
    draw(id, key) {
      this.keyb = key
      this.charts = echarts.init(document.getElementById(id))
      var option = {
        title: {
          text: '绝缘子分析',
          subtext: '兆欧',
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
            restore: {show: false},
            magicType: {show: true, type: ['line', 'bar']},
            dataView: {show: true, title: '数据视图', readOnly: true},

            saveAsImage: {
              show: true,
              excludeComponents: ['toolbox'],
              pixelRatio: 2,
            },
          },
          iconStyle: {
            normal: {
              color: 'white', //设置颜色
            },
          },
        },
        legend: {},
        // tooltip: {},
        // dataset: {
        //   dimensions: ['name', '测量值'],
        //   source: this.tableData[key].result   //连接数据
        //   // source: this.source.push(tableData[0].result)   //连接数据
        // },
        xAxis: {type: 'category'},
        yAxis: {
          //这个地方如果需要在Y轴定义最大值就放开,如果需要根据数据自适应的话,就注释掉
          // type: "value",
          // max: this.score,
          // maxInterval: this.score * 0.2,
          // minInterval: 1,
          // splitNumber: 4
        },
        grid: {bottom: 30},
        series: [
          {
            name: '测量值',
            type: 'bar', //表示这个是柱状图
            barCategoryGap: '30%',
            itemStyle: {color: '#81cebe'}, //定义颜色
            data: this.tableData[key].result,
          },
        ],
      }
      this.charts.setOption(option)
    },
    //查看分析
    viewclick(scope) {
      this.dialogVisible = true;
      (this.loopnameb = ''),
        (this.number = ''),
        (this.xiang = ''),
        (this.time = ''),
        (this.chuan = ''),
        (this.pian = ''),
        (this.loopnameb = scope.row.loopname)
      this.number = scope.row.towernum
      this.xiang = scope.row.phase
      this.time = scope.row.time
      this.chuan = scope.row.strand
      var arrb = []
      this.pians = []
      this.tableDatabl = []
      arrb = scope.row.result
      // //电阻的故障值判定

      // for (var a = 0; a < arrb.length; a++) {
      //   if (arrb[a][1] <= 500) {
      //     this.pians.push(arrb[a])
      //   }
      // }
      // var a = ''
      // for (var i = 0; i < this.pians.length; i++) {
      //   var b = this.pians[i][0]
      //   var band = b.indexOf('的')
      //   a = a + b.substring(0, band)
      // }

      //电压的故障值判定
      for (var a = 1; a < arrb.length - 1; a++) {
        if (arrb[a][1] <= arrb[a - 1][1] / 2 && arrb[a][1] <= arrb[a + 1][1] / 2) {
          this.pians.push(arrb[a][0]);
          this.tableDatabl.push(arrb[a])
        }
      }
      if (arrb[0][1] <= arrb[1][1] / 2) {
        this.pians.push(arrb[0]);
      }
      if (arrb[arrb.length - 1][1] <= arrb[arrb.length - 2][1] / 2) {
        this.pians.push(arrb[arrb.length - 1]);
      }
      var a = ''
      for (var i = 0; i < this.pians.length; i++) {
        var b = this.pians[i]
        var band = b.indexOf('的')
        a = a + b.substring(0, band)
      }
      this.pian = a
      if (this.pian == null || this.pian == '') {
        this.flg = false
        this.flgs = true
      } else {
        this.flg = true
        this.flgs = false
      }
      this.$nextTick(function () {
        this.draw('report', scope.$index)
      })
    },
    //删除数据
    deleteclick(row) {
      this.datas = new FormData()
      this.datas.append('id', row.id)
      this.$confirm('此操作将永久删除该文件, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning',
      })
        .then(() => {
          request({
            method: 'post',
            url: 'dyDatainfo/deletebyid',
            data: this.datas,
          }).then((res) => {
            if (res.data.flg == false) {
              this.$message.error('删除失败，请重试')
            } else {
              this.$message({
                message: '删除成功',
                type: 'success',
              })
              this.handleSizeChange(10)
            }
          })
        })
        .catch(() => {
          this.$message({
            type: 'info',
            message: '已取消删除',
          })
        })
    },
    initData: function (data) {
      const _this = this
      initData(Qs.stringify(data)).then((res) => {
        let list = res.data.data.list
        for (var i = 0; i < list.length; i++) {
          let arr = list[i].result.split(',')
          for (let j = 0; j < arr.length; j++) {
            let arr1 = []
            let arr2 = arr[j].split('/')
            arr1[0] = '第' + Number(arr2[0]) + '片的值'
            arr1[1] = arr2[1]
            arr[j] = arr1
          }
          list[i].result = arr
          list[i].selected = list[i].result[0].join(':')
        }
        _this.tableData = list
        _this.totalCount = res.data.data.total
        _this.loading = false
      })
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
      var key = this.keyb
      let myChart1 = echarts.init(document.getElementById('report'))
      let docxname = this.number + '' + this.xiang + '' + this.chuan + ".docx";
      let numberb = this.number;
      let xiangb = this.xiang;
      let chuanb = this.chuan;
      let timeb = this.time;
      for (var i = 0; i < this.tableDatabl.length; i++) {
        var band = this.tableDatabl[i][0].indexOf('的')
        this.tableDatabl[i] = {
          pian: this.tableDatabl[i][0].substring(0, band),
          celiangzhi: this.tableDatabl[i][1],
          status: "故障",
        }
      }
      // 绘制图表
      myChart1.setOption({
        title: {
          text: '绝缘子分析',
          subtext: '兆欧',
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
            normal: {
              color: 'white', //设置颜色
            },
          },
        },
        legend: {},
        // tooltip: {},
        // dataset: {
        //   dimensions: ['name', '测量值'],
        //   source: this.tableData[key].result   //连接数据
        //   // source: this.source.push(tableData[0].result)   //连接数据
        // },
        xAxis: {type: 'category'},
        yAxis: {
          //这个地方如果需要在Y轴定义最大值就放开,如果需要根据数据自适应的话,就注释掉
          // type: "value",
          // max: this.score,
          // maxInterval: this.score * 0.2,
          // minInterval: 1,
          // splitNumber: 4
        },
        grid: {bottom: 30},
        series: [
          {
            name: '测量值',
            type: 'bar', //表示这个是柱状图
            barCategoryGap: '30%',
            itemStyle: {color: '#81cebe'}, //定义颜色
            data: this.tableData[key].result,
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
      let docxsrc = "../../static/dywordout.docx";
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

          table: _this.tableDatabl,
          image1: _this.img1,
          number: numberb,
          xiang: xiangb,
          chuan: chuanb,
          time: timeb
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
  },
  created() {
    this.handleSizeChange(10)
  },
}
</script>
<style>
.DataInfo {
  margin-left: 80px;
  margin-top: 50px;
}

.block {
  margin-top: 20px;
}

.towernums {
  /*margin-right: 30px;*/
  text-align: right;
  padding: 9px;
}

.checkDate {
  text-align: right;
  padding: 9px;
}

.loopnames {
  text-align: right;
  padding: 9px;
}

.tableth {
  margin-top: 30px;
}

.ganta {
  margin-right: 65px;
}

.hbh2 {
  align-content: center;

  margin-left: 350px;
}

.hbh3 {
  margin-left: 50px;
}

.entityone {
  background: red;
}

.el-row-row {
  text-align: center;
  font-weight: bold;
  font-size: 25px;
}

.reports {
  margin-left: 100px;
}

.el-dialog {
  display: flex;
  flex-direction: column;
  margin: 0 !important;
  position: absolute;
  top: 50%;
  left: 50%;
  transform: translate(-50%, -50%);
  max-height: calc(100% - 20px);
  max-width: calc(100% - 30px);
}

.el-dialog .el-dialog__body {
  flex: 1;
  overflow: auto;
}

.buttonto {
  margin-left: 1120px;
  /*margin-bottom: 50px;*/
}
</style>

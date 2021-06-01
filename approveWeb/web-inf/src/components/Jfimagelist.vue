<template>
  <div class="DataInfo">
    <el-row :span="24">
      <el-col :span="1.5" class="towernums">
        <div class="ganta">站点名称:</div>
      </el-col>
      <el-col :span="1.2">
        <el-input placeholder="请输入站点名称查询" v-model="searchData.stationname"></el-input>
      </el-col>
      <el-col :span="1.5" class="loopnames">
        <div>站点编号:</div>
      </el-col>
      <el-col :span="1.2">
        <el-input placeholder="请输入站点编号查询" v-model="searchData.stationnumber"></el-input>
      </el-col>
      <el-col :span="1.5" class="loopnames">
        <div>缺陷程度:</div>
      </el-col>
      <el-col :span="1.2">
        <el-input placeholder="请输入缺陷程度查询" v-model="searchData.defectlevel"></el-input>
      </el-col>
      <el-col :span="1.5" class="checkDate">
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
      <el-col :span="3" style="text-align: center">
        <el-button type="primary" icon="el-icon-search" @click="search">查询</el-button>
      </el-col>
    </el-row>
    <el-dialog
      title="多图谱列表数据修改"
      :visible.sync="formdialog"
      width="80%"
      :close-on-click-modal="false">
      <el-form :model="editForm" ref="editForm" v-loading="loading">
        <el-row :span="24">
          <el-col :span="8">
            <el-form-item label="检测日期:" prop="time">
              <el-input v-model="editForm.time" style=" width: 250px"></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="站点名称:" prop="stationname">
              <el-input v-model="editForm.stationname" style="width: 250px"></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="站点编号:" prop="stationnumber">
              <el-input v-model="editForm.stationnumber" style="width: 250px"></el-input>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row :span="24">
          <el-col :span="8">
            <el-form-item label="温度:" prop="temperature">
              <el-input v-model="editForm.temperature" style="margin-left: 28px;width: 250px"></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="湿度:" prop="humidity">
              <el-input v-model="editForm.humidity" style="margin-left: 28px;width: 250px"></el-input>
            </el-form-item>
          </el-col>
        </el-row>
        <el-form-item label="编号照片" :label-width="editformLabelWidth" prop="numberimg">
          <el-input v-model="editForm.numberimg" v-if="false"></el-input>
          <el-upload
            action="ss"
            ref="numberimguploadimg"
            list-type="picture-card"
            :auto-upload="false"
            :data="editForm"
            :multiple="true"
            :file-list="editForm.numberimg"
            :on-preview="numberimghandlePictureCardPreview"
            :on-remove="numberimghandleRemove"
            :on-change="numberimghandleChange"
            :http-request="numberimguploadFile"
            :before-upload="beforeAvatarUpload">
            <i class="el-icon-plus"></i>
          </el-upload>
          <el-dialog :visible.sync="adoptFormdialogVisible">
            <img width="100%" :src="adoptFormdialogImageUrl" alt>
          </el-dialog>
        </el-form-item>
        <el-form-item label="整体照片" :label-width="editformLabelWidth" prop="wholeimg">
          <el-input v-model="editForm.wholeimg" v-if="false"></el-input>
          <el-upload
            action="ss"
            ref="wholeimguploadimg"
            list-type="picture-card"
            :auto-upload="false"
            :data="editForm"
            :multiple="true"
            :file-list="editForm.wholeimg"
            :on-preview="wholeimghandlePictureCardPreview"
            :on-remove="wholeimghandleRemove"
            :on-change="wholeimghandleChange"
            :http-request="wholeimguploadFile"
            :before-upload="beforeAvatarUpload">
            <i class="el-icon-plus"></i>
          </el-upload>
          <el-dialog :visible.sync="adoptFormdialogVisible">
            <img width="100%" :src="adoptFormdialogImageUrl" alt="">
          </el-dialog>
        </el-form-item>
        <el-form-item label="不良照片" :label-width="editformLabelWidth" prop="badimg">
          <el-input v-model="editForm.badimg" v-if="false"></el-input>
          <el-upload
            action="ss"
            ref="badimguploadimg"
            list-type="picture-card"
            :auto-upload="false"
            :data="editForm"
            :multiple="true"
            :file-list="editForm.badimg"
            :on-preview="badimghandlePictureCardPreview"
            :on-remove="badimghandleRemove"
            :on-change="badimghandleChange"
            :http-request="badimguploadFile"
            :before-upload="beforeAvatarUpload">
            <i class="el-icon-plus"></i>
          </el-upload>
          <el-dialog :visible.sync="adoptFormdialogVisible">
            <img width="100%" :src="adoptFormdialogImageUrl" alt="">
          </el-dialog>
        </el-form-item>
        <div class="buttonse">
          <el-button @click="resetForm('editForm')">取消</el-button>
          <el-button type="primary" @click="editSubmitFun('editForm')">提交</el-button>
        </div>
      </el-form>
    </el-dialog>
    <div class="tableth">
      <el-table :data="tableData" border style="width: 89%">
        <el-table-column sortable prop="id" label="ID" width="80" align="center"></el-table-column>
        <el-table-column sortable prop="time" label="检测日期" width="120" align="center"></el-table-column>
        <el-table-column sortable prop="stationname" label="站点名称" width="105" align="center"></el-table-column>
        <el-table-column sortable prop="stationnumber" label="站点编号" width="105" align="center"></el-table-column>
        <el-table-column sortable prop="temperature" label="温度" width="80" align="center"></el-table-column>
        <el-table-column sortable prop="humidity" label="湿度" width="80" align="center"></el-table-column>
        <el-table-column prop="numberimg" label="编号照片" width="120" align="center">
          <template slot-scope="scope">
            <img :src="'../../'+tableData[scope.$index].numberimg.split(',')[0]" min-width="70" height="70"/>
          </template>
        </el-table-column>
        <el-table-column prop="wholeimg" label="整体照片" width="120" align="center">
          <template slot-scope="scope">
            <img :src="'../../'+tableData[scope.$index].wholeimg.split(',')[0]" min-width="70" height="70"/>
          </template>
        </el-table-column>
        <el-table-column prop="badimg" label="不良照片" width="120" align="center">
          <template slot-scope="scope">
            <img :src="'../../'+tableData[scope.$index].badimg.split(',')[0]" min-width="70" height="70"/>
          </template>
        </el-table-column>
        <el-table-column prop="audio" label="音频" width="330" align="center">
          <template slot-scope="scope">
            <audio controls="controls">
              <source :src="scope.row.audio" type="audio/ogg">
            </audio>
          </template>
        </el-table-column>
        <el-table-column label="分析" width="90" align="center">
          <template slot-scope="scope">
            <el-button type="text" size="small" @click="viewclick(scope.row)">分析</el-button>
            <el-button type="text" size="small" @click="reportclick(scope)">报告</el-button>
          </template>
        </el-table-column>
        <el-table-column label="操作" width="100" align="center">
          <template slot-scope="scope">
            <el-button type="text" size="small" @click="updaterow(scope.row)">编辑</el-button>
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
        <el-row :span="24">
          <el-col :span="12">
            <div id="report" class="reports" style="width: 600px;height:400px;"></div>
          </el-col>
          <el-col :span="12">
            <div id="active" class="actives" style="width: 600px;height:400px;"></div>
          </el-col>
        </el-row>
        <el-row :span="24">
          <el-col :span="8">
            <el-button type="primary" style="width: 460px" @click="search">相位动态</el-button>
          </el-col>
          <el-col :span="8">
            <el-button type="primary" style="width: 460px" @click="search">飞行动态</el-button>
          </el-col>
          <el-col :span="8">
            <el-button type="primary" style="width: 460px" @click="search">特征动态</el-button>
          </el-col>
        </el-row>
        <el-row :span="24">
          <el-col :span="12">
            <div id="actives" class="actives" style="width: 600px;height:400px;"></div>
          </el-col>
          <el-col :span="12">
            <div id="activess" class="actives" style="width: 600px;height:400px;"></div>
          </el-col>
        </el-row>
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
import ecStat from 'echarts-stat';

Vue.use(Print)
import {request} from '@/network/request.js'
import {initData} from '@/network/Jfdatainfo/jfdatainfo'
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
      editformLabelWidth: '80px',
      charts: '',
      istag: true,
      viewsformVisible: false,
      adoptFormdialogVisible: false,
      adoptFormdialogImageUrl: '',
      formdialog: false,
      showUpload: true,
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
      list: [],
      lengthimg: '',
      echartUrl1: '',
      tableDatabl: [],
      imageListBase: [],
      displayOptions: [],
      //搜索条件
      searchData: {
        stationname: '',
        stationnumber: '',
        defectlevel: '',
        searchDate: [],
      },
      //删除图片
      arrnumberimg: [],
      strnumberflg: "false",
      arrwholeimg: [],
      strwholeflg: "false",
      arrbadimg: [],
      strbadflg: "false",
      //初始化默认显示第一张图片
      numberimgs: [],
      numberimgss: [],
      wholeimgs: [],
      wholeimgss: [],
      badimgs: [],
      badimgss: [],
      //编辑form
      editForm: {
        id: '',
        //检测日期
        time: '',
        //站点名称
        stationname: '',
        //站点编号
        stationnumber: '',
        //温度
        temperature: '',
        //湿度
        humidity: '',
        //编号照片
        numberimg: [],
        //整体照片
        wholeimg: [],
        //不良照片
        badimg: [],
        //音频
        audio: ''
      },
      loading: true,
      formDate: '',
      tableData: [],
      score: '3500',
      source: [['测量值']],
    }
  },
  methods: {
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
    //添加
    editSubmitFun(formName) {
      if (this.editForm.numberimg.length == 0 || this.editForm.wholeimg.length == 0 || this.editForm.badimg.length == 0) {
        this.$message.error("请最少选取一张图片")
        return
      }
      this.formDate = new FormData();
      this.formDate.append("id", this.editForm.id)
      this.formDate.append("time", this.editForm.time)
      this.formDate.append("stationname", this.editForm.stationname)
      this.formDate.append("stationnumber", this.editForm.stationnumber)
      this.formDate.append("temperature", this.editForm.temperature)
      this.formDate.append("humidity", this.editForm.humidity)
      this.formDate.append("imgs", this.editForm.numberimg)
      this.formDate.append("arrnumberimg", this.arrnumberimg)
      this.formDate.append("arrwholeimg", this.arrwholeimg)
      this.formDate.append("arrbadimg", this.arrbadimg)
      this.formDate.append("strnumberflg", this.strnumberflg)
      this.formDate.append("strwholeflg", this.strwholeflg)
      this.formDate.append("strbadflg", this.strbadflg)
      this.$refs.numberimguploadimg.submit();
      this.$refs.wholeimguploadimg.submit();
      this.$refs.badimguploadimg.submit();
      this.$refs[formName].validate((valid) => {
        if (valid) {
          request({
            method: 'post',
            url: 'jfDatainfo/update',
            headers: {'Content-Type': 'multipart/form-data'},
            data: this.formDate
          }).then(res => {
            if (res.data.data.code == 1) {
              this.$message.success("更新成功");
              this.$refs[formName].resetFields();
              this.formdialog = false
              this.handleCurrentChange(10)
            } else {
              this.$message.error("更新失败");
              this.$refs[formName].resetFields();
            }
          });
        } else {
          this.$message.error({
            message: '请输入必填项',
            offset: 300,
            center: true,
          });
        }
      });
    },
    // 重写upload上传方法
    numberimguploadFile(file) {
      this.formDate.append('numberimgfile', file.file);
    },
    // 重写upload上传方法

    wholeimguploadFile(file) {

      this.formDate.append('wholeimgfile', file.file);

    },
    // 重写upload上传方法
    badimguploadFile(file) {
      this.formDate.append('badimgfile', file.file);
    },
    numberimghandlePictureCardPreview(file) {
      this.adoptFormdialogImageUrl = file.url;
      this.adoptFormdialogVisible = true;
    },
    wholeimghandlePictureCardPreview(file) {
      this.adoptFormdialogImageUrl = file.url;
      this.adoptFormdialogVisible = true;
    },
    badimghandlePictureCardPreview(file) {
      this.adoptFormdialogImageUrl = file.url;
      this.adoptFormdialogVisible = true;
    },
    numberimghandleRemove(file, fileList) {
      this.strnumberflg = "true"
      this.editForm.numberimg = fileList;
      this.arrnumberimg = []
      for (var i = 0; i < fileList.length; i++) {
        if (fileList[i].status == "success") {
          this.arrnumberimg.push(fileList[i].url)
        }
      }
    },
    wholeimghandleRemove(file, fileList) {
      this.strwholeflg = "true"
      this.editForm.wholeimg = fileList;
      this.arrwholeimg = []
      for (var i = 0; i < fileList.length; i++) {
        if (fileList[i].status == "success") {
          this.arrwholeimg.push(fileList[i].url)
        }
      }
    },
    badimghandleRemove(file, fileList) {
      this.strbadflg = "true"
      this.editForm.badimg = fileList;
      this.arrbadimg = []
      for (var i = 0; i < fileList.length; i++) {
        if (fileList[i].status == "success") {
          this.arrbadimg.push(fileList[i].url)
        }
      }
    },
    numberimghandleChange(file, fileList) {
      this.editForm.numberimg = fileList;
    },
    wholeimghandleChange(file, fileList) {
      this.editForm.wholeimg = fileList;
    },
    badimghandleChange(file, fileList) {
      this.editForm.badimg = fileList;
    },
    resetForm(formName) {
      this.$refs[formName].resetFields();
      this.formdialog = false
    },
    //限制为图片
    beforeAvatarUpload(file) {
      var testmsg = file.name.substring(file.name.lastIndexOf(".") + 1);
      const extension =
        testmsg === "jpg" ||
        testmsg === "JPG" ||
        testmsg === "png" ||
        testmsg === "PNG" ||
        testmsg === "bpm" ||
        testmsg === "BPM";
      const isLt50M = file.size / 1024 / 1024 < 10;
      if (!extension) {
        this.$message({
          message: "上传图片只能是jpg / png / bpm格式!",
          type: "error"
        });
        return false; //必须加上return false; 才能阻止
      }
      if (!isLt50M) {
        this.$message({
          message: "上传文件大小不能超过 10MB!",
          type: "error"
        });
        return false;
      }
      return extension || isLt50M;
    },
    handleClick(row) {
    },
    updaterow(row) {
      this.editForm.id = row.id
      this.editForm.time = row.time
      this.editForm.stationname = row.stationname
      this.editForm.stationnumber = row.stationnumber
      this.editForm.temperature = row.temperature
      this.editForm.humidity = row.humidity

      //数据初始化
      this.editForm.numberimg = []
      this.editForm.wholeimg = []
      this.editForm.badimg = []
      this.numberimgs = []
      this.wholeimgs = []
      this.badimgs = []
      var a = 'http://localhost:60383/'
      var arrb = this.counts(row.numberimg)
      var arrc = this.counts(row.wholeimg)
      var arrd = this.counts(row.badimg)
      var arrbb = []
      var arrcc = []
      var arrdd = []
      //编号照片
      for (var i = 0; i < arrb.length; i++) {
        arrbb.push(a + arrb[i])
      }
      for (var i = 0; i < arrbb.length; i++) {
        arrbb[i] = {
          url: arrbb[i]
        }
      }
      this.editForm.numberimg = arrbb
      //整体图片
      for (var i = 0; i < arrc.length; i++) {
        arrcc.push(a + arrc[i])
      }
      for (var i = 0; i < arrcc.length; i++) {
        arrcc[i] = {
          url: arrcc[i]
        }
      }
      this.editForm.wholeimg = arrcc

      //不良图片
      for (var i = 0; i < arrd.length; i++) {
        arrdd.push(a + arrd[i])
      }
      for (var i = 0; i < arrdd.length; i++) {
        arrdd[i] = {
          url: arrdd[i]
        }
      }
      this.editForm.badimg = arrdd
      this.formdialog = true;

    },
    //计数
    counts(src) {
      let arr = []
      var a = 'http://localhost:60383/'
      for (var i = 0; i < src.length; i++) {
        arr = src.split(',')
      }
      return arr
    },
    //查询
    search: function () {
      let strsearchDate = ''
      if (this.searchData.searchDate != null) {
        strsearchDate = this.searchData.searchDate.join(',')
      }
      this.initData({
        stationname: this.searchData.stationname,
        stationnumber: this.searchData.stationnumber,
        searchDate: strsearchDate,
        defectlevel: this.searchData.defectlevel,
        pageNum: this.currentPage,
        pageSize: this.pagesize,
      })
    }
    ,
//柱状图
    draw(id, key) {
      this.keyb = key
      var chartDom = document.getElementById('report');
      var myChart = echarts.init(chartDom);
      var activeChart = echarts.init(document.getElementById('active'));
      var activeCharts = echarts.init(document.getElementById('actives'));
      var activeChartss = echarts.init(document.getElementById('activess'));
      echarts.registerTransform(ecStat.transform.regression);

      var optiont;
      var option2;
      var option3;
      var option4;
      var base = +new Date(1988, 9, 3);
      var oneDay = 24 * 3600 * 1000;

      var data = [[base, Math.random() * 300]];

      for (var i = 1; i < 20000; i++) {
        var now = new Date(base += oneDay);
        data.push([
          [now.getFullYear(), now.getMonth() + 1, now.getDate()].join('/'),
          Math.round((Math.random() - 0.5) * 20 + data[i - 1][1])
        ]);
      }

      optiont = {
        tooltip: {
          trigger: 'axis',
          position: function (pt) {
            return [pt[0], '10%'];
          }
        },
        title: {
          left: 'center',
          text: '大数据量面积图',
        },
        toolbox: {
          feature: {
            dataZoom: {
              yAxisIndex: 'none'
            },
            restore: {},
            saveAsImage: {}
          }
        },
        xAxis: {
          type: 'time',
          boundaryGap: false
        },
        yAxis: {
          type: 'value',
          boundaryGap: [0, '100%']
        },
        dataZoom: [{
          type: 'inside',
          start: 0,
          end: 20
        }, {
          start: 0,
          end: 20
        }],
        series: [
          {
            name: '模拟数据',
            type: 'line',
            smooth: true,
            symbol: 'none',
            areaStyle: {},
            data: data
          }
        ]
      };

      var option2;

      var hours = ['12a', '1a', '2a', '3a', '4a', '5a', '6a',
        '7a', '8a', '9a', '10a', '11a',
        '12p', '1p', '2p', '3p', '4p', '5p',
        '6p', '7p', '8p', '9p', '10p', '11p'];
      var days = ['有效值', '局部最大值', '频率成分1', '频率成分2'];
      var datas = [];
      option2 = {
        tooltip: {
          position: 'top'
        },
        title: [],
        singleAxis: [],
        series: []
      };

      days.forEach(function (day, idx) {
        option2.title.push({
          left: 300,
          textBaseline: 'middle',
          top: (idx * 100 / 4 + 9) + '%',
          text: day
        });
        option2.singleAxis.push({
          left: 150,
          type: 'category',
          boundaryGap: false,
          data: hours,
          top: (idx * 100 / 4 + 5) + '%',
          height: (100 / 4 - 10) + '%',
          axisLabel: {
            interval: 2
          }
        });
        option2.series.push({
          singleAxisIndex: idx,
          coordinateSystem: 'singleAxis',
          type: 'scatter',
          data: [],
          symbolSize: function (dataItem) {
            return dataItem[1] * 4;
          }
        });
      });

      datas.forEach(function (dataItem) {
        option2.series[dataItem[0]].data.push([dataItem[1], dataItem[2]]);
      });
      // 指定图表的配置项和数据
      var option;
      var datass = [
        [96.24, 11.35],
        [33.09, 85.11],
        [57.60, 36.61],
        [36.77, 27.26],
        [20.10, 6.72],
        [45.53, 36.37],
        [110.07, 80.13],
        [72.05, 20.88],
        [39.82, 37.15],
        [48.05, 70.50],
        [0.85, 2.57],
        [51.66, 63.70],
        [61.07, 127.13],
        [64.54, 33.59],
        [35.50, 25.01],
        [226.55, 664.02],
        [188.60, 175.31],
        [81.31, 108.68]
      ];

      option3 = {
        dataset: [{
          source: datass
        }, {
          transform: {
            type: 'ecStat:regression',
            config: {method: 'polynomial', order: 3}
          }
        }],
        title: {
          text: '18 companies net profit and main business income (million)',
          subtext: 'By ecStat.regression',
          sublink: 'https://github.com/ecomfe/echarts-stat',
          left: 'center',
          top: 16
        },
        tooltip: {
          trigger: 'axis',
          axisPointer: {
            type: 'cross'
          }
        },
        xAxis: {
          splitLine: {
            lineStyle: {
              type: 'dashed'
            }
          },
          splitNumber: 20
        },
        yAxis: {
          min: -40,
          splitLine: {
            lineStyle: {
              type: 'dashed'
            }
          }
        },
        series: [{
          name: 'scatter',
          type: 'scatter'
        }, {
          name: 'line',
          type: 'line',
          smooth: true,
          datasetIndex: 1,
          symbolSize: 0.1,
          symbol: 'circle',
          label: {show: true, fontSize: 16},
          labelLayout: {dx: -20},
          encode: {label: 2, tooltip: 1}
        }]
      };

      // 指定图表的配置项和数据
      var originalData = [
        [3.275154, 2.957587],
        [-3.344465, 2.603513],
        [0.355083, -3.376585],
        [1.852435, 3.547351],
        [-2.078973, 2.552013],
        [-0.993756, -0.884433],
        [2.682252, 4.007573],
        [-3.087776, 2.878713],
        [-1.565978, -1.256985],
        [2.441611, 0.444826],
        [-0.659487, 3.111284],
        [-0.459601, -2.618005],
        [2.17768, 2.387793],
        [-2.920969, 2.917485],
        [-0.028814, -4.168078],
        [3.625746, 2.119041],
        [-3.912363, 1.325108],
        [-0.551694, -2.814223],
        [2.855808, 3.483301],
        [-3.594448, 2.856651],
        [0.421993, -2.372646],
        [1.650821, 3.407572],
        [-2.082902, 3.384412],
        [-0.718809, -2.492514],
        [4.513623, 3.841029],
        [-4.822011, 4.607049],
        [-0.656297, -1.449872],
        [1.919901, 4.439368],
        [-3.287749, 3.918836],
        [-1.576936, -2.977622],
        [3.598143, 1.97597],
        [-3.977329, 4.900932],
        [-1.79108, -2.184517],
        [3.914654, 3.559303],
        [-1.910108, 4.166946],
        [-1.226597, -3.317889],
        [1.148946, 3.345138],
        [-2.113864, 3.548172],
        [0.845762, -3.589788],
        [2.629062, 3.535831],
        [-1.640717, 2.990517],
        [-1.881012, -2.485405],
        [4.606999, 3.510312],
        [-4.366462, 4.023316],
        [0.765015, -3.00127],
        [3.121904, 2.173988],
        [-4.025139, 4.65231],
        [-0.559558, -3.840539],
        [4.376754, 4.863579],
        [-1.874308, 4.032237],
        [-0.089337, -3.026809],
        [3.997787, 2.518662],
        [-3.082978, 2.884822],
        [0.845235, -3.454465],
        [1.327224, 3.358778],
        [-2.889949, 3.596178],
        [-0.966018, -2.839827],
        [2.960769, 3.079555],
        [-3.275518, 1.577068],
        [0.639276, -3.41284]
      ];


      var DIM_CLUSTER_INDEX = 2;
      var DATA_DIM_IDX = [0, 1];
      var CENTER_DIM_IDX = [3, 4];

// See https://github.com/ecomfe/echarts-stat
      var step = ecStat.clustering.hierarchicalKMeans(originalData, {
        clusterCount: 6,
        outputType: 'single',
        outputClusterIndexDimension: DIM_CLUSTER_INDEX,
        outputCentroidDimensions: CENTER_DIM_IDX,
        stepByStep: true
      });

      var colorAll = [
        '#bbb', '#37A2DA', '#e06343', '#37a354', '#b55dba', '#b5bd48', '#8378EA', '#96BFFF'
      ];
      var ANIMATION_DURATION_UPDATE = 1500;

      function renderItemPoint(params, api) {
        var coord = api.coord([api.value(0), api.value(1)]);
        var clusterIdx = api.value(2);
        if (clusterIdx == null || isNaN(clusterIdx)) {
          clusterIdx = 0;
        }
        var isNewCluster = clusterIdx === api.value(3);

        var extra = {
          transition: []
        };
        var contentColor = colorAll[clusterIdx];

        return {
          type: 'circle',
          x: coord[0],
          y: coord[1],
          shape: {
            cx: 0,
            cy: 0,
            r: 10
          },
          extra: extra,
          style: {
            fill: contentColor,
            stroke: '#333',
            lineWidth: 1,
            shadowColor: contentColor,
            shadowBlur: isNewCluster ? 12 : 0,
            transition: ['shadowBlur', 'fill']
          }
        };
      }

      function renderBoundary(params, api) {
        var xVal = api.value(0);
        var yVal = api.value(1);
        var maxDist = api.value(2);
        var center = api.coord([xVal, yVal]);
        var size = api.size([maxDist, maxDist]);

        return {
          type: 'ellipse',
          shape: {
            cx: isNaN(center[0]) ? 0 : center[0],
            cy: isNaN(center[1]) ? 0 : center[1],
            rx: isNaN(size[0]) ? 0 : size[0] + 15,
            ry: isNaN(size[1]) ? 0 : size[1] + 15
          },
          extra: {
            renderProgress: ++targetRenderProgress,
            enterFrom: {
              renderProgress: 0
            },
            transition: 'renderProgress'
          },
          style: {
            fill: null,
            stroke: 'rgba(0,0,0,0.2)',
            lineDash: [4, 4],
            lineWidth: 4
          }
        };
      }

      function makeStepOption(option, data, centroids) {
        var newCluIdx = centroids ? centroids.length - 1 : -1;
        var maxDist = 0;
        for (var i = 0; i < data.length; i++) {
          var line = data[i];
          if (line[DIM_CLUSTER_INDEX] === newCluIdx) {
            var dist0 = Math.pow(line[DATA_DIM_IDX[0]] - line[CENTER_DIM_IDX[0]], 2);
            var dist1 = Math.pow(line[DATA_DIM_IDX[1]] - line[CENTER_DIM_IDX[1]], 2);
            maxDist = Math.max(maxDist, dist0 + dist1);
          }
        }
        var boundaryData = centroids
          ? [
            [
              centroids[newCluIdx][0],
              centroids[newCluIdx][1],
              Math.sqrt(maxDist)
            ]
          ]
          : [];

        option.options.push({
          series: [{
            type: 'custom',
            encode: {
              tooltip: [0, 1]
            },
            renderItem: renderItemPoint,
            data: data
          }, {
            type: 'custom',
            renderItem: renderBoundary,
            animationDuration: 3000,
            silent: true,
            data: boundaryData
          }]
        });
      }

      var targetRenderProgress = 0;

      option = {
        timeline: {
          top: 'center',
          right: 50,
          height: 300,
          width: 10,
          inverse: true,
          autoPlay: false,
          playInterval: 2500,
          symbol: 'none',
          orient: 'vertical',
          axisType: 'category',
          label: {
            formatter: 'step {value}',
            position: 10
          },
          checkpointStyle: {
            animationDuration: ANIMATION_DURATION_UPDATE
          },
          data: []
        },
        baseOption: {
          animationDurationUpdate: ANIMATION_DURATION_UPDATE,
          tooltip: {},
          xAxis: {
            type: 'value'
          },
          yAxis: {
            type: 'value'
          },
          series: [{
            type: 'scatter'
          }]
        },
        options: []
      };

      makeStepOption(option, originalData);
      option.timeline.data.push('0');
      for (var i = 1, stepResult; !(stepResult = step.next()).isEnd; i++) {
        makeStepOption(
          option,
          echarts.util.clone(stepResult.data),
          echarts.util.clone(stepResult.centroids)
        );
        option.timeline.data.push(i + '');
      }

      activeChart.setOption(option2);
      activeCharts.setOption(option3);
      activeChartss.setOption(option);
      option && myChart.setOption(optiont);
      /*窗口自适应，关键代码*/
      setTimeout(function () {
        window.onresize = function () {
          myChart.resize();
          activeChart.resize();
          activeCharts.resize();
          activeChartss.resize();

        }
      }, 200)

    }
    ,
//查看分析
    viewclick(scope) {
      this.dialogVisible = true;
      this.$nextTick(function () {
        this.draw('report', scope.$index)
      })

    }
    ,
    reportclick(row) {
      console.log(row)
    }
    ,
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
            url: 'jfDatainfo/deleteOne',
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
        }).catch(() => {
        this.$message({
          type: 'info',
          message: '已取消删除',
        })
      })
    }
    ,
    initData: function (data) {
      const _this = this
      initData(Qs.stringify(data)).then((res) => {
        let list = res.data.data.list
        this.lengthimg = res.data.data.list.length
        _this.tableData = list
        _this.totalCount = res.data.data.total
        _this.loading = false

      })

    }
    ,
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
    }
    ,

    wordclick() {
      var key = this.keyb
      let myChart1 = echarts.init(document.getElementById('report'))
      let docxname = this.number + '' + this.xiang + '' + this.chuan + ".docx";
      let numberb = this.number;
      let xiangb = this.xiang;
      let chuanb = this.chuan;
      let timeb = this.time;
      for (var i = 0; i < this.tableDatabl.length; i++) {
        this.tableDatabl[i] = {
          pian: this.tableDatabl[i][1],
          celiangzhi: this.tableDatabl[i][0],
          status: "故障",
        }
      }
      // 绘制图表
      myChart1.setOption({
        title: {
          text: '绝缘子分析',
          subtext: '兆欧',
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
        xAxis: {type: 'category'},
        yAxis: {},
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
    },
    companylist(){
      var a= localStorage.getItem("rolelist");
      if (a == 1) {
        // location.href = "/Companylist.html"
        this.handleSizeChange(10)
      } else (
        this.handleSizeChange(10)
      )
    }
  },
  created() {
   this.companylist()
  }
  ,
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

.el-row-row {
  text-align: center;
  font-weight: bold;
  font-size: 25px;
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

.buttonse {
  margin-left: 600px;
}
</style>

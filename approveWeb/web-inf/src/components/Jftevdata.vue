<template>
  <div class="Jftevdata">
    <div class="tableth">
      <el-table :data="tableData" border style="width: 80%">
        <el-table-column sortable prop="id" label="ID" width="110" align="center"></el-table-column>
        <el-table-column sortable prop="time" label="检测日期" width="130" align="center"></el-table-column>
        <el-table-column sortable prop="stationname" label="站点名称" width="130" align="center"></el-table-column>
        <el-table-column sortable prop="stationnumber" label="站点编号" width="130" align="center"></el-table-column>
        <el-table-column sortable prop="checkstation" label="测点名称" width="130" align="center"></el-table-column>
        <el-table-column sortable prop="checknumber" label="测点编号" width="130" align="center"></el-table-column>
        <el-table-column sortable prop="countnumber" label="计数" width="130" align="center"></el-table-column>
        <el-table-column sortable prop="peakvalue" label="峰值" width="130" align="center"></el-table-column>
        <el-table-column prop="img" label="照片" width="160" align="center">
          <template slot-scope="scope">
            <img :src="scope.row.img" min-width="80" height="80"/>
          </template>
        </el-table-column>

        <el-table-column sortable label="操作" width="140" align="center">
          <template slot-scope="scope">
            <el-button type="text" size="small" @click="viewclick(scope.row)">分析</el-button>
            <el-button type="text" size="small" @click="reportclick(scope.row)">报告</el-button>
            <el-button type="text" size="small" @click="deleteclick(scope.row)">删除</el-button>
            <el-button type="text" size="small" @click="compileclick(scope.row)">编辑</el-button>
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
      title="TEV数据汇总"
      :visible.sync="formdialog"
      width="80%"
      :close-on-click-modal="false">
      <el-form :model="updateuserform" ref="updateuserform" v-loading="loading">
        <el-row :span="24">
          <el-col :span="8">
            <el-form-item label="检测日期:" prop="time">
              <el-input v-model="updateuserform.time" style=" width: 250px"></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="站点名称:" prop="stationname">
              <el-input v-model="updateuserform.stationname" style="width: 250px"></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="站点编号:" prop="stationnumber">
              <el-input v-model="updateuserform.stationnumber" style="width: 250px"></el-input>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row :span="24">
          <el-col :span="8">
            <el-form-item label="测点名称:" prop="checkstation">
              <el-input v-model="updateuserform.checkstation" style="margin-left: 28px;width: 250px"></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="测点编号:" prop="checknumber">
              <el-input v-model="updateuserform.checknumber" style="margin-left: 28px;width: 250px"></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="计数:" prop="countnumber">
              <el-input v-model="updateuserform.countnumber" style="margin-left: 28px;width: 250px"></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="峰值:" prop="peakvalue">
              <el-input v-model="updateuserform.peakvalue" style="margin-left: 28px;width: 250px"></el-input>
            </el-form-item>
          </el-col>
        </el-row>
        <el-form-item label="照片" :label-width="editformLabelWidth" prop="numberimg">
          <el-input v-model="updateuserform.imgs" v-if="false"></el-input>
          <!--          http://localhost:60383/static/content/image/ll.jpg-->
          <!--          auto-upload	是否在选取文件后立即进行上传-->
          <!--          multiple	是否支持多选文件-->
          <!--          show-file-list	是否显示已上传文件列表-->
          <!--          on-preview	点击文件列表中已上传的文件时的钩子	function(file)	—	—-->
          <!--          on-remove	文件列表移除文件时的钩子	function(file, fileList)	—	—-->
          <!--          on-change	文件状态改变时的钩子，添加文件、上传成功和上传失败时都会被调用-->
          <!--          http-request	覆盖默认的上传行为，可以自定义上传的实现-->
          <!--          before-upload	上传文件之前的钩子，参数为上传的文件，若返回 false 或者返回 Promise 且被 reject，则停止上传。-->
          <el-upload
            action="ss"
            ref="numberimguploadimg"
            list-type="picture-card"
            :auto-upload="false"
            :data="updateuserform"
            :multiple="true"
            :file-list="updateuserform.imgs"
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
        <div class="buttonse">
          <el-button @click="resetForm('editForm')">取消</el-button>
          <el-button type="primary" @click="updateuserforms('updateuserform')">提交</el-button>
        </div>
      </el-form>
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
  // import audio from 'vue-mobile-audio'
  //
  // Vue.use(audio)
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
        echartUrl1: '',
        tableDatabl: [],
        imageListBase: [],
        displayOptions: [],

        tableData: [],
        score: '3500',
        source: [['测量值']],

        //弹框
        arrdelectimg: [],
        arrdelectflg: false,

        dialogVisibles: false,
        dialogTitle: '',
        updateuserform: {
          id: '',
          time: '',
          stationname: '',
          stationnumber: '',
          checkstation: '',
          checknumber: '',
          countnumber: '',
          peakvalue: '',
          imgs: [],
        },

        updateformDate: {},
        formLabelWidth: '25%',
        adoptFormdialogVisible: false,
        adoptFormdialogImageUrl: '',
        formDate: '',
        editformLabelWidth: '80px',
        arrnumberimg: [],
        formdialog: false,
        loading: true,
        scopeData: [],

        userData: decodeURI(location.search),
        userid: '',
        rolelist: '',
        companyname: '',
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
        //待添加

      },

      //查看分析
      viewclick(scope) {
        //传递值
        this.scopeData.push(scope.id)
        this.scopeData.push(scope.stationname)
        window.location.href = './JftevAnalyseData.html?' + scope.id;
      },
      //查看报告
      reportclick(row) {
        window.location.href = './JftevReportData.html?' + row.id;
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
              url: 'jfTevdatainfo/deleteOne',
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
      },
      compileclick: function (scope) {


        //把数据库里面的额数据进行回显
        this.dialogVisibles = true;
        this.formdialog = true,
          // alert("可以编辑")
          //给数据赋值


          request({
            method: 'get',
            url: 'jfTevdatainfo/queryRedactData',
            params: {
              id: scope.id
            }
          }).then((res) => {

            this.updateformDate = res.data.data.list
            this.updateuserform.id = this.updateformDate.id;
            this.updateuserform.time = this.updateformDate.time;
            this.updateuserform.stationname = this.updateformDate.stationname;
            this.updateuserform.stationnumber = this.updateformDate.stationnumber;
            this.updateuserform.checkstation = this.updateformDate.checkstation;
            this.updateuserform.checknumber = this.updateformDate.checknumber;
            this.updateuserform.countnumber = this.updateformDate.countnumber;
            this.updateuserform.peakvalue = this.updateformDate.peakvalue;
            var strings = this.updateformDate.img.split(",");

            var arr = [];
            var a = 'http://localhost:8082/';
            for (let i = 0; i < strings.length; i++) {
              arr.push(a + strings[i])
            }
            for (var i = 0; i < arr.length; i++) {
              arr[i] = {
                url: arr[i]
              }
            }
            this.updateuserform.imgs = arr;
          });
      },
      // 重写upload上传方法
      numberimguploadFile(file) {


        this.formDate = new FormData();
        this.formDate.append('numberimgfile', file.file);
      },
      //	点击文件列表中已上传的文件时的钩子
      numberimghandlePictureCardPreview(file) {
        this.adoptFormdialogImageUrl = file.url;
        this.adoptFormdialogVisible = true;
      },
      //文件列表移除文件时的钩子,原方法
      // numberimghandleRemove(file, fileList) {
      //   this.updateuserform.imgs = fileList;
      //   this.arrdelectimg = [];
      //   this.arrdelectflg = true;
      //   for (var i = 0; i < fileList.length; i++) {
      //     if (fileList[i].status == "success") {
      //       this.arrdelectimg.push(fileList[i].url)
      //     }
      //   }
      // },
      //文件列表移除文件时的钩子
      numberimghandleRemove(file, fileList) {
        //fileList是剩下几张图片的url,赋值给updateuserform.imgs,重新展示
        this.updateuserform.imgs = fileList;
        this.arrdelectimg = [];
        this.arrdelectflg = true;
        if (file.status == "success") {
          this.arrdelectimg.push(file.url)
        }
      },
      // 文件状态改变时的钩子，添加文件、上传成功和上传失败时都会被调用
      numberimghandleChange(file, fileList) {

        if (file.raw.type.indexOf("image") != -1) {
          this.updateuserform.imgs = fileList;
        } else {
          this.$message.error("只能上传图片！");
          fileList.splice(fileList.indexOf(file), 1);
        }
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
      updateuserforms(formName) {

        let resEntiretyImges = [];

        //根据页面上面不同的数据进行返回,根据原来的ID覆盖掉原来的数据

        if (this.updateuserform.imgs.length == 0) {
          this.$message.error("请最少选取一张图片")
          return
        }

        let formDate = new FormData();
        // this.formDate = new FormData();
        // this.formDate.append("id", this.updateuserform.id)
        // this.formDate.append("time", this.updateuserform.time)
        // this.formDate.append("stationname", this.updateuserform.stationname)
        // this.formDate.append("stationnumber", this.updateuserform.stationnumber)
        // this.formDate.append("checkstation", this.updateuserform.checkstation)
        // this.formDate.append("checknumber", this.updateuserform.checknumber)
        // this.formDate.append("countnumber", this.updateuserform.countnumber)
        // this.formDate.append("peakvalue", this.updateuserform.peakvalue)
        // this.formDate.append("imgs", this.updateuserform.imgs)


        formDate = new FormData();
        formDate.append("id", this.updateuserform.id)
        formDate.append("time", this.updateuserform.time)
        formDate.append("stationname", this.updateuserform.stationname)
        formDate.append("stationnumber", this.updateuserform.stationnumber)
        formDate.append("checkstation", this.updateuserform.checkstation)
        formDate.append("checknumber", this.updateuserform.checknumber)
        formDate.append("countnumber", this.updateuserform.countnumber)
        formDate.append("peakvalue", this.updateuserform.peakvalue);

        // formDate.append("img", JSON.stringify(this.updateuserform.imgs));

        this.updateuserform.imgs.forEach((item) => {
          if (item.status != "success") {
            formDate.append("img", item.raw);
          } else {
            resEntiretyImges.push(item.url);
          }
        });

        formDate.append("img", resEntiretyImges);

        //删除的照片
        formDate.append("arrdelectimg", this.arrdelectimg)


        //对form表单进行校验
        this.$refs.numberimguploadimg.submit();
        this.$refs[formName].validate((valid) => {
          if (valid) {
            request({
              method: 'post',
              url: 'jfTevdatainfo/update',
              // headers: {'Content-Type': 'multipart/form-data'},
              data: formDate,
              // img: resEntiretyImges,
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
        location.reload(true);
      },
      //点击取消键,直接关闭弹框
      resetForm() {
        this.formdialog = false
      },
      //超管用户页面
      initData() {
        //查询后台返回数据进行幅值
        if (this.userData.indexOf("?") != -1) {
          this.userData = this.userData.substr(1)
        }
        var strings = this.userData.split(",");
        this.userid = strings[0];
        this.companyname = strings[1];
        this.rolelist = strings[2];
        request({
          method: 'get',
          url: 'jfTevdatainfo/queryAllByLimit',
          params: {
            //相当于dyData表中的UserId
            id: this.userid,
            companyname: this.companyname,
            rolelist: this.rolelist,
            pageNum: this.currentPage,
            pageSize: this.pagesize
          }
        }).then(res => {
          let list = res.data.data.list
          this.tableData = list
          this.totalCount = res.data.data.total
          this.loading = false
        });
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
        let docxsrc = "../../static/peopleout.docx";
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
      this.handleSizeChange(10);
      this.initData();
    },
  }
</script>
<style>
  .Jftevdata {
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

  /*.ganta {*/
  /*  margin-right: 65px;*/
  /*}*/

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

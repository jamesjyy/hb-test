<template>
  <div class="Data">
    <el-row :span="24">
      <el-col :span="1.5" class="towernums">
        <div class="ganta">杆塔号:</div>
      </el-col>
      <el-col :span="1.5" style="margin-right: 50px">
        <el-input placeholder="请输入杆塔号查询" v-model="searchData.towernum"></el-input>
      </el-col>
      <el-col :span="2" class="loopidtime">
        <div>线路名称:</div>
      </el-col>
      <el-col :span="2.2">
        <el-input placeholder="请输入线路名称查询" v-model="searchData.loopidtime"></el-input>
      </el-col>
      <el-col :span="2" class="checkDate">
        <span>日期:</span>
      </el-col>
      <el-col :span="1.5">
        <el-date-picker
          v-model="searchData.time"
          type="daterange"
          range-separator="至"
          start-placeholder="开始日期"
          end-placeholder="结束日期"
          align="right"
          value-format="yyyy-MM-dd">
        </el-date-picker>
      </el-col>
      <el-col :span="2" style="text-align: center">
        <el-button type="primary" icon="el-icon-search" @click="search">查询</el-button>
      </el-col>
    </el-row>
    <div class="tableth">
      <el-table
        :data="tableData"
        border
        style="width: 82%">
        <el-table-column prop="towernum" label="杆塔号" width="180" align="center"></el-table-column>
        <el-table-column prop="time" label="检查时间" width="250" align="center"></el-table-column>
        <el-table-column prop="time" label="相别" width="250" align="center"></el-table-column>
        <el-table-column prop="time" label="串号" width="250" align="center"></el-table-column>

        <el-table-column prop="loopidtime" label="片值" width="250" align="center">
<!--          做成下拉框-->
          <template slot-scope="scope">
            <!--级联选择器  v-model="cascaderValue"-->
            <el-cascader  :options="options" @focus="handleChange(scope.row)"></el-cascader>

          </template>
        </el-table-column>

        <el-table-column prop="time" label="状态" width="250" align="center"></el-table-column>

        <el-table-column label="操作" width="160" align="center">
          <template slot-scope="scope">
            <el-button type="text" size="small" @click="viewclick(scope.row)">查看分析</el-button>
          </template>

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
        :page-sizes="[10, 20, 50, 100]"
        :page-size="pageSize"
        layout="total, sizes, prev, pager, next, jumper"
        :total="totalCount">

      </el-pagination>
    </div>
  </div>
</template>

<script>
  import {request} from '@/network/request.js';
  import {initData} from '@/network/dyData/dyData.js';
  import * as echarts from 'echarts';
  import Qs from 'qs'

  let id = 0;
  export default {

    data() {
      return {

        // cascaderValue: [],
        strand: "",
        pageNum: 1,
        pageCount: 1000,
        pageSize: 0,
        //当前页码
        currentPage: 1,
        //查询的页码
        start: 1,
        //默认数据总数
        totalCount: 0,
        viewsformVisible: false,
        dialogVisible: false,
        floorRange: [],
        dialogTitle: '',
        iframeUrl: '',
        imgform: '',
        mainWinth: '',
        //搜索条件
        searchData: {
          loopidtime: '',
          towernum: '',
          searchDate: [],
        },
        tableData: [],
        queryResult: [],
        selectedOptions: [],
        defaultParams: [],
        //级联选择器
        cascaderKey: 1,
        options: [{
          value: '',
          label: '',
          children: [
            { // 二级选择器
            value: '',
            label: '',
          }
          ]
          // 第一个选项
          // {
          //   value: '',
          //   label: '',
          //   children: [{
          //     value: '',
          //     label: ''
          //   }]
          // }
        }],

        //  为级联选择器准备封装的数据类型
        town: [], // 封装所有一级选择
        viliage: [], // 等同于当前下面的二级目录
        viliageste: [], //相当于三级目录
        //options: [], // 总数据
        select: { // 一级选择
          value: '',
          label: '',
          children: []
        },
        childrenSelect: { // 二级选择
          value: '',
          label: '',
          childrentre: []
        },
        childrenSelecttre: {  //三级选择器
          value: '',
          label: ''
        }

      };
    },
    methods: {
      showSelect() {
      },
      //删除数据
      deleteclick(row) {

        this.id = new FormData();
        //this.id.append("id",row.id)

        this.datas = new FormData()
        this.datas.append("id", row.id)

        this.$confirm('此操作将永久删除该文件, 是否继续?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          request({
            method: 'post',
            url: 'dyData/deleteOne',
            data: this.datas
          }).then(res => {
            if (res.data.flg == false) {
              this.$message.error("删除失败，请重试");
            } else {
              this.$message({
                message: "删除成功",
                type: 'success'
              });
              //删除后调用查看方法,刷新页面
              this.initData();
            }
          });
        }).catch(() => {
          this.$message({
            type: 'info',
            message: '已取消删除'
          });
        });
      },  //查询
      search: function () {
        let strsearchDate = ""
        if (this.searchData.searchDate != null) {
          strsearchDate = this.searchData.searchDate.join(',')

        }
        this.initData({
          //赋值,将查询的条件值传递给后台
          loopidtime: this.searchData.loopidtime,
          searchDate: strsearchDate,
          towernum: this.searchData.towernum,
          pageNum: this.currentPage,
          pageSize: this.pagesize
        });
      },

      handleSelectionChange(val) {
        // this.upditionalSelection = val;
      },
      //每页显示数据量变更
      handleSizeChange(val) {
        this.pagesize = val;
        this.search();
      },
      //页码变更
      handleCurrentChange(val) {
        this.currentPage = val;
        this.search();
      },
      handleClick() {
      },
      //级联选择器1
      handleChange(row) {
        // this.cascaderKey = 1;
        // this.cascaderKey += 1;
        request({
          method: 'get',
          url: 'dyData/queryResult',
          params: {
            id: row.id
          }
        }).then(res => {
          // setTimeout(() => {
          // }, 1000);
          // if (res.data.data.list == null &&res.data.data.list == "") {
          //   alert('操作过于频繁,请稍等后再试')
          // }

            this.defaultParams = res.data.data.list;
            let that = this;
            //循环之前先置空
            that.viliage = [];
            this.options = [];
            //循环得到每一个对象
            let arr = this.defaultParams;
            for (let i = 0; i < arr.length; i++) {
              let strand = arr[i].strand;
              let phase = arr[i].phase;
              let strandPhase = "第" + phase + "" + strand + "串上的值为:";
              //先填充最外层的数据
              that.select = {
                value: strandPhase,
                label: strandPhase
              }

              String.prototype.replaceAll = function (FindText, RepText) {
                return this.replace(new RegExp(FindText, "g"), RepText);
              }
              var str = arr[i].result;
              str = str.replaceAll('/', '片值:');
              str = str.replaceAll(',', '       ,      ');
              that.childrenSelect = {
                value: str,
                label: str,
              }
              //置空
              that.viliage = [];
              that.viliage.push(that.childrenSelect);
              that.select.children = that.viliage;
              // 把对象填充对options
              //this.options = [];
              // this.level++;
              this.level = 2;
              this.options.push(that.select);
            }
        });
      },
      viewclick(row) {
        //跳转到另一个页面
        window.location.href = './DyDataAnalyse.html?' + row.id + ""
      },
      initData: function (data) {
        setTimeout(() => {
          const _this = this
          initData(Qs.stringify(data)).then(res => {
            _this.tableData = res.data.data.list;
            _this.totalCount = res.data.data.total;
            _this.loading = false
          });
        }, 1500);
      },
      // 注灰量折线图
      curveechartsstrength() {
        this.$nextTick(() => {
          var chartDom = document.getElementById('echartsa');
          var myChart = echarts.init(chartDom);
          var optiona;
          optiona = {
            tooltip: {
              trigger: 'axis',
              axisPointer: {            // 坐标轴指示器，坐标轴触发有效
                type: 'shadow'        // 默认为直线，可选为：'line' | 'shadow'
              }
            },
            grid: {
              left: '3%',
              right: '4%',
              bottom: '3%',
              containLabel: true
            },
            xAxis: [
              {
                type: 'category',
                data: ['Mon', 'Tue', 'Wed', 'Thu', 'Fri', 'Sat', 'Sun'],
                axisTick: {
                  alignWithLabel: true
                }
              }
            ],
            yAxis: [
              {
                type: 'value'
              }
            ],
            series: [
              {
                name: '直接访问',
                type: 'bar',
                barWidth: '60%',
                data: [10, 52, 200, 334, 390, 330, 220]
              }
            ]
          };
          myChart.setOption(optiona);
        });
      },
    },
    created() {
      this.handleSizeChange(10)
      this.initData({pageNum: this.currentPage, pageSize: this.pagesize});
    }
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

  .loopidtime {
    text-align: right;
    padding: 9px;
  }

  .tableth {
    margin-top: 30px;
  }

  .ganta {
    margin-right: 65px;
  }
</style>

<template>
  <div class="Data">
    <el-row :span="24">

      <el-col :span="2" style="text-align: center" v-show="roleFlg">
        <el-button type="primary" icon="el-icon-arrow-left" @click="upOneLevel() ">返回上一级</el-button>
      </el-col>

      <el-col :span="1.5" class="towernums">
        <div class="ganta">杆塔号:</div>
      </el-col>
      <el-col :span="1.5" style="margin-right: 50px">
        <el-input placeholder="请输入杆塔号查询" v-model="searchData.towernum"></el-input>
      </el-col>
      <el-col :span="2" class="checkDate">
        <span>日期:</span>
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

        <el-table-column prop="loopname" label="线路名称" width="180" align="center" v-show="loopFlg"></el-table-column>
        <el-table-column prop="towernum" label="杆塔号" width="180" align="center"></el-table-column>
        <!--        <el-table-column prop="time" label="检测时间" width="250" align="center"></el-table-column>-->

        <el-table-column prop="Phase" label="相别" width="250" align="center">
          <template slot-scope="scope">
            <!--            @change="display(value)"-->
            <el-select v-model="scope.row.selected"
                       @change="display(scope.row,scope.row.selected,scope.$index)"
                       placeholder="请选择相别">
              <el-option
                v-for="(item,index) in scope.row.phase"
                :key="index"
                :label="item.phase"
                :value="index">
              </el-option>
            </el-select>
          </template>
        </el-table-column>


        <el-table-column prop="bunch" label="串号" width="250" align="center">
          <template slot-scope="scope">
            <el-select v-model="scope.row.querySelect"
                       placeholder="请选择串号"
                       @change="displayBunch(scope.row,scope.$index)">
<!--              <div-->
<!--                v-for="(item2,index2) in scope.row.phase[index3]"-->
<!--                :key="index2"-->
<!--                :label="item2"-->
<!--                :value="item2">-->
                <el-option
                  v-for="(item1,indexs1) in scope.row.phase[scope.row.selected ? scope.row.selected : 0].bunch"
                  :key="indexs1"
                  :label="item1"
                  :value="item1">
                </el-option>
<!--              </div>-->
            </el-select>
          </template>
        </el-table-column>

        <el-table-column label="操作" width="160" align="center">
          <template slot-scope="scope">
            <el-button type="text" size="small" @click="deleteclick(scope.row)" v-show="deleteFlg">删除</el-button>
            <el-button type="text" size="small" @click="analyseclick(scope.row)">查看详情</el-button>
            <el-button type="text" size="small" @click="detailsclick(scope.row)">查看分析</el-button>
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
  import * as echarts from 'echarts';
  import Qs from 'qs'

  let id = 0;
  export default {

    data() {
      return {
        flg: true,
        // cascaderValue: [],
        strand: "",
        pageNum: 1,
        pageCount: 1000,
        pageSize: 0,
        index3: 0,
        //当前页码
        currentPage: 1,
        //查询的页码
        start: 1,
        //默认数据总数
        totalCount: 0,
        //搜索条件
        searchData: {
          towernum: '',
          searchDate: [],
        },
        tableData: [],
        defaultParams: [],
        //  下拉框

        PhaseOptions: [{
          value: '',
          label: ''
        }],

        PhaseOptionlist: [{}],

        PhaseOptionsData: {
          value: '',
          label: ''
        },
        value: '',
        values: '',

        bunchOptions: [{
          value: '',
          label: ''
        }],

        // bunchs: [],
        bunchs: [],

        bunchsData: {
          values: ''
        },
        bunchOptionList: [{
          value: '',
          label: ''
        }],

        bunchOptionsData: {
          value: '',
          label: ''
        },

        //接收上个页面传递过来的数据
        // location.search
        qureyCondition: decodeURI(location.search),
        userid: '',
        loopidname: '',
        towernum: '',
        phase: [],
        PhaseData: {
          value: '',
          lable: '',
        },
        phaseRqs: '',
        bunchRqs: '',

        role: '',
        deleteFlg: true,
        roleFlg: true,
        loopFlg: false,
        companyName: '',
      };
    },
    methods: {
      //删除数据
      deleteclick(row) {
        this.id = new FormData();
        this.$confirm('此操作将永久删除该文件, 是否继续?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          request({
            method: 'post',
            url: 'dyData/deleteOne',
            params: {
              id: this.userid,
              companyname: this.companyName,
              loopidname: row.loopname,
              towernum: row.towernum
            }
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

        //赋值,将查询的条件值传递给后台
        searchDate: strsearchDate,
          //杆塔名称

          this.towernum = this.searchData.towernum + "",
          this.currentPage = this.currentPage,
          this.pageSize = this.pagesize
        this.initData();
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
      analyseclick(row) {
        //跳转到另一个页面,+参数
        // var str = this.userid + "," + this.loopidname + "," + row.towernum + "," + this.phaseRqs + "," + this.bunchRqs;
        location.href = './DyDataInfo.html?loopname=' + encodeURI(row.loopname);

      },
      detailsclick(row) {
        //跳转到另一个页面,+参数
        var str = this.userid + "," +this.companyName+","+  this.role +"," + this.loopidname + "," + row.towernum + "," + this.phaseRqs + "," + this.bunchRqs;
        // var str = this.userid
        window.location.href = './DyDataAnalyse.html?' + str;
      },
      initData: function () {
        if (this.qureyCondition.indexOf("?") != -1) {
          this.qureyCondition = this.qureyCondition.substr(1)
        }
        var str = this.qureyCondition;
        var strings = str.split(",");
        this.userid = strings[0];
        this.companyName = strings[1];
        this.loopidname = strings[2];
        this.role = strings[3];
        if (this.role == "3") {
          this.deleteFlg = false;
          this.roleFlg = false;
          this.loopidname = '';
          this.loopFlg = true;
        }
        if (this.userid == null && this.userid =='') {
          this.userid = "";
        }
        //查询后台返回数据进行幅值
        request({
          method: 'get',
          url: 'dyData/queryChildData',
          params: {
            //相当于dyData表中的UserId
            id: this.userid,
            companyname: this.companyName,
            loopidtime: this.loopidname,
            role: this.role,
            towernum: this.searchData.towernum,
            pageNum: this.currentPage,
            pageSize: this.pagesize
          }
        }).then(res => {
            //三级数据,得到table表中的数据,得到下拉框的数据,
            //先把当前行的数据都进行赋值,根据不同的下拉框所需的数据不同来进行拆分显示,根据以及下拉框的数据进行判断二级的数据显示
            this.tableData = res.data.data.dyDataChildList;
            this.totalCount = res.data.data.total;
          }
        );
      },
      //查询对应的相别
      // displayPhase(row) {
      //   request({
      //     method: 'get',
      //     url: 'dyData/queryChildPhase',
      //     params: {
      //       //相当于dyData表中的UserId
      //       id: this.userid,
      //       companyname: this.companyName,
      //       loopidtime: row.loopname,
      //       towernum: row.towernum,
      //       role: this.role
      //     }
      //   }).then(res => {
      //       //三级数据,得到table表中的数据,得到下拉框的数据,
      //       //先把当前行的数据都进行赋值,根据不同的下拉框所需的数据不同来进行拆分显示,根据以及下拉框的数据进行判断二级的数据显示
      //       this.PhaseOptions = res.data.data.list;
      //     }
      //   );
      // },
      //  通过传递过来的象限(value),来查询对应的串号

      display(row, phase,index) {

        // row.querySelect = null;
        this.bunchs = [];
        this.phaseRqs = phase;

        this.index3 =  phase
        // this.index3 = index;

        // //需要一个数组
        // var datas = this.PhaseOptions;
        // for (let i = 0; i < datas.length; i++) {
        //   if (phase === datas[i].phase) {
        //     var bunch = datas[i].bunch;
        //     // this.bunchs = bunch.split(",");    //可以试试
        //     var bunchList = [];
        //
        //     var strings = bunch.split(",");
        //     for (var j = 0; j < strings.length; j++) {
        //       var bunchsData = {
        //         values: ''
        //       };
        //       bunchsData.values = strings[j];
        //       bunchList.push(bunchsData);
        //     }
        //     this.bunchs = bunchList;
        //
        //   }
        // }
      },
      disBunch(row) {
        if (this.userid == null && this.userid =='') {
          this.userid = "";
        }
        request({
          method: 'get',
          url: 'dyData/queryChildBunch',
          params: {
            //相当于dyData表中的UserId
            id: this.userid,
            companyname: this.companyName,
            loopidtime: row.loopname,
            towernum: row.towernum,
            bunch: row.selected,
            role: this.role
          }
        }).then(res => {
            //三级数据,得到table表中的数据,得到下拉框的数据,
            //先把当前行的数据都进行赋值,根据不同的下拉框所需的数据不同来进行拆分显示,根据以及下拉框的数据进行判断二级的数据显示
             var list = res.data.data.list;
          var bunchList = [];
          for (let i = 0; i <list.length; i++) {
            var bunch1 = list[i].bunch;
            bunchList.push(bunch1)
          }
          this.bunchs = bunchList;
          }
        );
      },
      displayBunch(row, index) {

        this.bunchs = null;
        // row.querySelect = null;
      },
      // 返回上一级的方法
      upOneLevel() {
        // window.history.back();
        window.history.go(-1);  //返回上一页
      }
    },
    created() {
      this.handleSizeChange(10)
      // this.initData();
    }
  }
</script>
<style>


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

  .tableth {
    margin-top: 30px;
  }

  .ganta {
    margin-right: 65px;
  }
</style>

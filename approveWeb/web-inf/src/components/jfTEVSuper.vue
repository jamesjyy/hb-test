<template>
  <div class="Data">
    <el-row :span="24">

    </el-row>

    <div>
      <div>
        <el-row :span="24">
          <el-col :span="1.5" class="towernums">
            <div class="ganta">公司名称:</div>
          </el-col>
          <el-col :span="1.5" style="margin-right: 50px">
            <el-input placeholder="请输入公司名称查询" v-model="searchData.companyname"></el-input>
          </el-col>
          <el-col :span="2" style="text-align: center">
            <el-button type="primary" icon="el-icon-search" @click="search">查询</el-button>
          </el-col>
        </el-row>
        <div class="tableth">


          <template>
            <el-table :data="tableData" stripe style="width: 100%">
              <el-table-column prop="companyName" label="公司名称" width="350"></el-table-column>
              <el-table-column prop="stationNumber" label="站点数量" width="350"></el-table-column>
              <el-table-column  label="操作">
                <template slot-scope="scope">
                  <el-button type="text" size="small" @click="viewclick(scope.row)">查看分析</el-button>
                  <el-button type="text" size="small" @click="deleteclick(scope.row)">删除</el-button>
                </template>
              </el-table-column>
            </el-table>
          </template>


<!--                    <el-table-->
<!--                      :data="tableData"-->
<!--                      border-->
<!--                      style="width: 82%">-->
<!--                      <el-table-column prop="companyName" label="公司名称" width="280" align="center"></el-table-column>-->
<!--                      <el-table-column prop="stationNumber" label="站点数量" width="280" align="center"></el-table-column>-->

<!--                      <el-table-column label="操作" width="260" align="center">-->
<!--                        <template slot-scope="scope">-->
<!--                          <el-button type="text" size="small" @click="viewclick(scope.row)">查看分析</el-button>-->
<!--                          <el-button type="text" size="small" @click="deleteclick(scope.row)">删除</el-button>-->
<!--                        </template>-->
<!--                      </el-table-column>-->

<!--                    </el-table>-->
        </div>
      </div>
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
        // cascaderValue: [],
        strand: "",
        pageNum: 1,
        pageCount: 1000,
        pageSize: 0,
        //当前页码
        currentPage: 1,
        //查询的页码
        start: 1,
        //数据显示
        tableData: [],
        //默认数据总数
        totalCount: 0,

        //搜索条件
        searchData: {
          loopidtime: '',
          companyname: '',
          searchDate: [],
        },

        manTableData: [],
        userData: decodeURI(location.search),
        userid: '',
        rolelist: '',
        companyname: '',
        companynames: '',

      };
    },
    methods: {
      showSelect() {
      },
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
            url: 'jfTevdatainfo/deleteSupOne',
            params: {
              id: this.userid,
              companyname: this.companyname
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
      },

      //查询,超管页面传递给后台的数据
      search: function () {
        //赋值,将查询的条件值传递给后台
        this.companynames = this.searchData.companyname,

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
          url: 'jfTevdatainfo/views',
          params: {
            //相当于dyData表中的UserId
            id: this.userid,
            companyname: this.companynames,
            pageNum: this.currentPage,
            pageSize: this.pagesize
          }
        }).then(res => {
            //一级数据
            this.tableData = res.data.data.list;
            this.totalCount = res.data.data.total

          }
        );
      },
      //跳转页面
      viewclick(row) {
        //传递userId
        window.location.href = './Jftevdata.html?' +this.userid +","+ row.companyName + "," +this.rolelist

      }
    },
    created() {
      this.handleSizeChange(10)
      this.initData();
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


















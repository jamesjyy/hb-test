<template>
  <div class="Data">
    <el-row :span="24">

    </el-row>

    <div>
      <div>
        <el-row :span="24">
          <el-col :span="2" style="text-align: center" v-show="roleFlg">
            <el-button type="primary" icon="el-icon-arrow-left" @click="upOneLevel()">返回上一级</el-button>
          </el-col>
          <el-col :span="1.5" class="towernums">
            <div class="ganta">线路:</div>
          </el-col>
          <el-col :span="1.5" style="margin-right: 50px">
            <el-input placeholder="请输入线路名称查询" v-model="searchData.loopidtime"></el-input>
          </el-col>
          <el-col :span="2" style="text-align: center">
            <el-button type="primary" icon="el-icon-search" @click="searchMag">查询</el-button>
          </el-col>
        </el-row>

        <div class="tableth">
          <el-table
            :data="manTableData"
            border
            style="width: 82%">
            <el-table-column prop="loopidtime" label="线路" width="180" align="center"></el-table-column>
            <el-table-column prop="towerNumber" label="杆塔数量" width="180" align="center"></el-table-column>
            <el-table-column prop="errnumber" label="异常数量" width="250" align="center"></el-table-column>
            <el-table-column label="操作" width="160" align="center">
              <template slot-scope="scope">
                <!--          传递的数据是UserId+loopIdTime(线路名称)-->
                <el-button type="text" size="small" @click="viewclickMag(scope.row)">查看分析</el-button>
                <!--          传递的数据是UserId,然后删除数据库里面所有的数据-->
                <el-button type="text" size="small" @click="deleteclickMag(scope.row)">删除</el-button>
              </template>
            </el-table-column>

          </el-table>
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
          companyname: '',
          searchDate: [],
        },
        loopidtimes: '',
        tableData: [],
        queryResult: [],
        selectedOptions: [],
        defaultParams: [],

        //图表转换
        superFlg: true,
        manageFlg: false,

        //接收数据使用
        superTableData: [],
        manTableData: [],
        userData: decodeURI(location.search),

        useridSup: '',
        companyname: '',
        userid: '',
        rolelist: '',
        roleFlg: true,
      };
    },
    methods: {
      deleteclickMag(row) {
        this.id = new FormData();
        this.$confirm('此操作将永久删除该文件, 是否继续?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          request({
            method: 'post',
            url: 'dyData/deleteManOne',
            params: {
              id: this.userid,
              companyname: this.companyname,
              loopidtime: row.loopidtime,
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

      //用户页面传递给后台的数据
      searchMag: function () {
        //赋值,将查询的条件值传递给后台
        // loopidtime相当于后台接收的数据
        // this.searchData.loopidtime,相当于给前台的数据进行赋值
        this.loopidtimes = this.searchData.loopidtime;
        this.initData();
      },

      handleSelectionChange(val) {
        // this.upditionalSelection = val;
      },
      //每页显示数据量变更
      handleSizeChange(val) {
        this.pagesize = val;

      },
      //页码变更
      handleCurrentChange(val) {
        this.currentPage = val;

      },
      viewclickMag(row) {
        var str = ""+ "," + this.companyname + "," + row.loopidtime + "," + this.rolelist;
        //跳转页面
        window.location.href = './DyDataChild.html?' + str;
      },
      initData() {
        //查询后台返回数据进行幅值
        if (this.userData.indexOf("?") != -1) {
          this.userData = this.userData.substr(1)
        }
        var strings = this.userData.split(",");
        this.userid = strings[0];
        this.companyname = strings[1];
        this.rolelist = strings[2];

        if (this.rolelist == 1) {
          this.roleFlg = true;
        } else {
          this.roleFlg = false;
        }

        request({
          method: 'get',
          url: 'dyData/queryMagData',
          params: {
            //相当于dyData表中的UserId
            id: this.userid,
            // userId: this.userId,点击查询进行搜索
            loopidtime: this.loopidtimes,
            companyname: this.companyname,
            pageNum: this.currentPage,
            pageSize: this.pagesize
          }
        }).then(res => {
            //二级数据
            this.manTableData = res.data.data.list;
            this.totalCount = res.data.data.total
          }
        );
      }, upOneLevel() {
        window.history.go(-1);  //返回上一页
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

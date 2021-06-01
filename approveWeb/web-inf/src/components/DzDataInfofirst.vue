<template>
  <div class="companylist">
    <el-row :span="24" v-if="searflg">
      <el-col :span="1.5" class="loopnames">
        <div>公司名称:</div>
      </el-col>
      <el-col :span="3">
        <el-input placeholder="请输入公司名称查询" v-model="searchData.companyName"></el-input>
      </el-col>

      <el-col :span="2" style="text-align: center">
        <el-button type="primary" icon="el-icon-search" @click="search">查询</el-button>
      </el-col>
    </el-row>
    <div class="tableth">
      <el-table :data="tableData" border style="width: 80%">
        <el-table-column sortable type="index" label="序号" width="150" align="center"></el-table-column>
        <el-table-column sortable prop="companyName" label="公司名称" align="center"></el-table-column>
        <el-table-column sortable prop="countnumber" label="线路数量" align="center"></el-table-column>
        <el-table-column sortable label="操作" width="300" align="center">
          <template slot-scope="scope">
            <el-button type="text" size="small" @click="showinfo(scope.row)">查看详情</el-button>
            <el-button type="text" size="small" @click="deleteclick(scope.row)">删除</el-button>
          </template>
        </el-table-column>
      </el-table>
    </div>
    <div class="block" v-if="pagehelperflg">
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
  </div>
</template>

<script>

import {AdmininitData} from '@/network/dzdatainfo/dzdatainfo'
import {PtuserinitData} from '@/network/dzdatainfo/dzdatainfo'

import Qs from 'qs'

export default {
  data() {
    return {
      // 分页
      pageNum: 1,
      pageCount: 1000,
      pageSize: 0,
      //当前页码
      currentPage: 1,
      values: '',
      value: 'tb_log',
      tableData: [],
      //查询的页码
      start: 1,
      //默认数据总数
      totalCount: 0,
      rolelist: [],
      userid: "",
      pagehelperflg: true,
      //搜索条件
      searchData: {
        companyName: "",
      },
      userlist: "",
      searflg:""
    }
  },
  methods: {


    handleSelectionChange(val) {
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
    showinfo(row){
      location.href="/DzDataInfoloopinfo.html?companyName="+encodeURI(row.companyName)
    },
    //查询
    search: function () {
      this.rolelist = localStorage.getItem("rolelist")
      this.userlist = JSON.parse(localStorage.getItem("user"))
      this.userid = this.userlist.id
      if (this.rolelist[0] == 1) {
        this.searflg=true
        this.AdmininitData({
          companyName: this.searchData.companyName,
          pageNum: this.currentPage,
          pageSize: this.pagesize,
        })
      }
      if (this.rolelist[0] == 2) {
        this.pagehelperflg=false
        this.searflg=false
        this.PtuserinitData({
          id: this.userid,
          pageNum: this.currentPage,
          pageSize: this.pagesize,
        })
      }
    },
    //
    AdmininitData: function (data) {
      AdmininitData(Qs.stringify(data)).then((res) => {
        this.tableData=res.data.data.list
        this.totalCount = res.data.data.total

      })
    },
    PtuserinitData: function (data) {
      PtuserinitData(Qs.stringify(data)).then((res) => {
        this.tableData=res.data.data.list
        this.totalCount = res.data.data.total
      })
    },

  },
  created() {

    this.handleSizeChange(10)

  }
  ,
}
</script>
<style>
.companylist {
  margin-left: 40px;
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
  /*margin-right: 65px;*/
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

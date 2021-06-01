<template>
  <div class="companylist">
    <el-row :span="24" v-if="searflg">
      <el-col :span="1.5" class="loopnames">
        <div>线路名称:</div>
      </el-col>
      <el-col :span="3">
        <el-input placeholder="请输入线路名称查询" v-model="searchData.loopname"></el-input>
      </el-col>

      <el-col :span="2" style="text-align: center">
        <el-button type="primary" icon="el-icon-search" @click="search">查询</el-button>
      </el-col>
    </el-row>
    <div class="tableth">
      <el-table :data="tableData" border style="width: 80%">
        <el-table-column sortable type="index" label="序号" width="150" align="center"></el-table-column>
        <el-table-column sortable prop="loopname" label="线路名称" align="center"></el-table-column>
        <el-table-column sortable prop="towernumbercount" label="杆塔数量" align="center"></el-table-column>
        <el-table-column sortable prop="towerstatus" label="异常" align="center"></el-table-column>

        <el-table-column sortable label="操作" width="300" align="center">
          <template slot-scope="scope">
            <el-button type="text" size="small" @click="showinfo(scope.row)">查看详情</el-button>
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
  </div>
</template>

<script>

import {loopinitData} from '@/network/dzdatainfo/dzdatainfo'

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
      //搜索条件
      searchData: {
        loopname: "",
      },
      userlist: "",
      searflg: "",
      urlcompanyname: "",
      companynamename: ""
    }
  },
  methods: {

    showinfo(row){
      location.href="/DzDataInfo.html?loopname="+encodeURI(row.loopname)
    },
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

    //查询
    search: function () {
      this.searflg = true
      var initjson = this.initmehod()
      this.loopinitData({
        companyName: initjson.companyName,
        loopname:this.searchData.loopname,
        pageNum: this.currentPage,
        pageSize: this.pagesize,
      })
    },

    loopinitData: function (data) {

      loopinitData(Qs.stringify(data)).then((res) => {
        this.tableData = res.data.data.list
        this.totalCount = res.data.data.total

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
  },
  created() {
    this.handleSizeChange(10)
  },
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

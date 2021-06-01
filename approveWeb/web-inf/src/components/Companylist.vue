<template>
  <div class="companylist">
    <el-row :span="24">
      <el-col :span="1.5" class="towernums">
        <div class="ganta">公司名称:</div>
      </el-col>
      <el-col :span="1.5" style="margin-right: 50px">
        <el-input placeholder="请输入公司名称查询" v-model="searchData.companyName"></el-input>
      </el-col>
      <el-col :span="2" style="text-align: center">
        <el-button type="primary" icon="el-icon-search" @click="search">查询</el-button>
      </el-col>
    </el-row>
    <div class="tableth">
      <el-table :data="tableData" border style="width: 80%">
        <el-table-column sortable type="index" label="序号" width="150" align="center"></el-table-column>
        <el-table-column sortable prop="companyName" label="公司名称" width="130" align="center"></el-table-column>
        <el-table-column sortable prop="address" label="公司地址" width="120" align="center"></el-table-column>
        <el-table-column sortable prop="name" label="公司账号" width="130" align="center"></el-table-column>
        <el-table-column sortable prop="phone" label="公司电话" width="130" align="center"></el-table-column>
        <el-table-column sortable prop="appid" label="设备种类" width="162" align="center">
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

        <el-table-column sortable label="操作" width="150" align="center">
          <template slot-scope="scope">
            <el-button type="text" size="small" @click="deleteclick(scope.row)">查看详情</el-button>
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
import {request} from '@/network/request.js'
import {initData} from '@/network/companylist/companylist'
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
        companyName: '',
      },
      tableData: [],
      score: '3500',
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
    //查询
    search: function () {
      this.initData({
        companyName: this.searchData.companyName,
        pageNum: this.currentPage,
        pageSize: this.pagesize,
      })
    },
    //删数据
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

      })
    },
    //打印
  },
  created() {
    this.handleSizeChange(10)
  },
}
</script>
<style>
.companylist {
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
